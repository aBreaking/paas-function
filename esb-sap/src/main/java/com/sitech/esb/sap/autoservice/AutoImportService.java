package com.sitech.esb.sap.autoservice;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sitech.esb.hb.*;
import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sitech.esb.manager.PlatformConfigInfo;

import com.sitech.esb.util.IOUtil;
import com.sitech.esb.util.AppSrvConstant;

public class AutoImportService {
	private static final Logger log = Logger.getLogger("autojob");
	/** srvInfo类 */
	private SrvInfo srvInfoApp = null;
	private String  hasSoapResult = "";

	/**
	 * 导入服务主程序
	 * @param srvNamesTemp   临时服务名
	 * @param isCovered      是否覆盖标志
	 * @param session        会话
	 * @return String
	 * throws Exception
	 */
	public String importService(Workbook book,String srvNamesTemp , String isCovered, Session session) {
		log.info("begin import service "+ srvNamesTemp);
		long beginTime = System.currentTimeMillis();

		String [] srvNames = srvNamesTemp.split(";");
		SrvInfoDAO serviceDAO = new SrvInfoDAO();
		StringBuffer resultSB = new StringBuffer();

		for(int i = 0; i < srvNames.length; i ++) {
			String srvName = (srvNames[i].split(":"))[0];
			String result = "";
			int srvCount = serviceDAO.findCountByProperty("srvName",srvName,session);
			if(isCovered.equals("1") && srvName!=null && srvCount > 0) {
				result = importAndCoverService(srvName , session, book);
			} else {
				result = importServiceBySrvName(srvName , session, book);
				if(result!=null && result.length()>0){
					resultSB.append(result).append("\n");
					break;
				}
			}
			if(!result.trim().equals("")) {
				resultSB.append(result).append("\n");
			}
		}
		book.close();
		long endTime = System.currentTimeMillis();
		log.info("service of" + srvNamesTemp + " import succeeded. take up :" + (endTime - beginTime));
		return resultSB.toString()+hasSoapResult;
	}

	public String getHasSoapResult(){
		return hasSoapResult;
	}
	/**
	 * 导入服务
	 * @param srvName
	 * @param session
	 * @param book
	 * @return String
	 */
	public String importServiceBySrvName(String srvName , Session session, Workbook book) {
		long beginTime = System.currentTimeMillis();
		String result = "";
		try {
			Sheet [] sheets = book.getSheets();
			for(int i=0; i<sheets.length; i++) {
				Sheet sheet = sheets[i];
				//除去标题头，从第firstServiceRow行才能读取服务
				int firstServiceRow = getContentFirstRowIndex(sheet);
				int sheetRows = sheet.getRows();
				String sheetName = sheet.getName();
				for(int j=firstServiceRow; j<sheetRows; j++) {
					Cell cell = sheet.getCell(1, j);
					String content = cell.getContents();
					if (StringUtils.isBlank(content)){
						continue;
					}
					if(StringUtils.isNotBlank(content) && content.equals(srvName)) {
						if (sheetName.indexOf("Hsf")!=-1){
							result = this.importAtomHsfService(sheet, cell, srvName, session);
						}else if(sheetName.indexOf("String")!=-1){
							result = this.importComStringService(sheet, cell, srvName, session);
						}else if(sheetName.indexOf("http")!=-1){
							result = this.importHttp(sheet, cell, srvName, session);
						}else if(sheetName.indexOf("TuxedoJSON")!=-1){
							result = this.importAtomTxdoJSONService(sheet, cell, srvName, session);
						}else if(sheetName.indexOf("Tuxedo")!=-1){
							result = this.importComTuxedoService(sheet, cell, srvName, session);
						}else if(sheetName.indexOf("SOAP")!=-1){
							result = this.importSoapService(sheet, cell, srvName, session);
						}else{
							String msg = "the sheetName of "+ sheetName +" is incorrect,only support : Hsf,String,http,TuxedoJSON,Tuxedo,SOAP";
							log.error(msg);
							throw new SapException(msg);
						}
					}
				}
			}
			srvInfoApp = null;
			session.flush();
		} catch(Exception e) {
			log.info("An exception occurred while importing the service " + srvName, e);
			return "An exception occurred while importing the service " + srvName;
		}

		long endTime = System.currentTimeMillis();
		log.info("service "+srvName +" import completed , takes " + (endTime-beginTime));
		return result;
	}

	/**
	 *  覆盖导入
	 *  修改原来覆盖方式，改为先删除存在的服务再插入新的服务
	 *  原来方式如果只存在主表则更新时出错
	 *  数据库中存在该服务，导入和覆盖服务
	 * @param srvName        服务名
	 * @param session        session
	 * @param book           工作簿
	 * @return String
	 */
	public String importAndCoverService(String srvName, Session session, Workbook book) {
		long beginTime = System.currentTimeMillis();
		String result = "";
		SrvInfo srvInfo = null;
		SrvInfo srvInfoTemp = null;
		SrvInfoDAO srvInfoDAO = new SrvInfoDAO();
		List listTemp = null;
		long srvIdTemp = -1;
		Connection conn = null;
		Statement stmt = null;
		Transaction tx = null;

		SrvInfo srvInfoCheck = null;
		List listCheckTemp = null;
		long srvIdCheckTemp = -1;
		String srvNameCheck = null;
		try{
			conn = session.connection();
			Sheet [] sheets = book.getSheets();
			for(int i=0; i<sheets.length; i++) {
				Sheet sheet = sheets[i];
				//除去标题头，从第firstServiceRow行才能读取服务
				int firstServiceRow = getContentFirstRowIndex(sheet);
				int sheetRows = sheet.getRows();
				for(int j=firstServiceRow; j<sheetRows; j++) {
					Cell cell1 = sheet.getCell(1, j);
					String content1 = cell1.getContents();
					if (StringUtils.isBlank(content1)){
						continue;
					}
					if(content1!=null && content1.equals(srvName)) {
						listTemp = srvInfoDAO.findByProperty("srvName", srvName, session);
						if(null != listTemp && !listTemp.isEmpty()){
							srvInfo = (SrvInfo)listTemp.get(0);
							srvInfoTemp = srvInfo;
							//srvInfoApp = srvInfo;
							srvIdTemp = srvInfo.getSrvId().longValue();
						}else{
							srvIdTemp = -1;
						}

						try{
							srvIdTemp = srvInfo.getSrvId().longValue();
							srvInfoDAO.delete(srvInfo, session);
							session.flush();
						}catch(RuntimeException e){
							log.info("Hibernate 删除已存在服务“" + srvName + "”失败，将采用JDBC删除",e);
							try{
								session.flush();
								stmt = conn.createStatement();
								conn.setAutoCommit(false);
								stmt.addBatch("delete from SRV_RELATION where srv_parent_id=" + srvIdTemp +"or srv_sub_id="+srvIdTemp);
								stmt.addBatch("delete from SRV_CATEG_REL where srv_id=" + srvIdTemp);
								stmt.addBatch("delete from SRV_EVENT where srv_id=" + srvIdTemp);
								stmt.addBatch("delete from EVENT_SRV where srv_id=" + srvIdTemp);
								stmt.addBatch("delete from APPSRV where srv_id=" + srvIdTemp);
								stmt.addBatch("delete from SRV_ERRINFO where srv_id=" + srvIdTemp);
								stmt.addBatch("delete from SRV_INPUT where srv_id=" + srvIdTemp);
								stmt.addBatch("delete from SRV_OUTPUT where srv_id=" + srvIdTemp);
								stmt.addBatch("delete from SRV_COMMPROTL where srv_id=" + srvIdTemp);
								stmt.addBatch("delete from SRVINFO where srv_id=" + srvIdTemp);
								stmt.executeBatch();
								conn.commit();
								session.flush();
							}catch(RuntimeException ex){
								log.error("导入失败：删除已存在服务" + srvName + "出错" ,ex);
								try{
									tx.rollback();
								}catch(Exception exc){
									log.error("删除已存在服务“" + srvName + "“回滚失败",exc);
								}
								throw ex;
							}finally{
								if(stmt != null)stmt.close();
								if(conn != null)conn.setAutoCommit(true);
							}
						}
						result = importServiceBySrvName(srvName, session, book);

					}
				}
			}
		} catch(Exception e) {
			log.info("导入并覆盖服务" + srvName + "时出现异常", e);
			return "导入并覆盖服务" + srvName + "时出现异常";
		}finally{
			//DO NOTHING
		}
		long endTime = System.currentTimeMillis();
		log.info("导入并覆盖服务" + srvName + ", 耗时" + (endTime-beginTime));
		return result;
	}

	private String importHttp(Sheet sheet, Cell cell, String srvName, Session session) {
		log.debug("调用 importHttpService, 导入服务"+ srvName);
		String result = "";
		SrvType srvType = new SrvTypeDAO().findById(Long.valueOf(5), session);
		result = importServiceModule(sheet, cell, srvName, session, srvType);
		return result;
	}

	private String importAtomTuxedoService(Sheet sheet, Cell cell, String srvName, Session session) {
		log.debug("调用 importAtomTuxedoService, 导入服务"+ srvName);
		String result = "";
		SrvType srvType = new SrvTypeDAO().findById(Long.valueOf(0), session);
		result = importServiceModule(sheet, cell, srvName, session, srvType);
		return result;
	}

	//TODO 这里有个bug，执行到某一行就执行不动了，操，完全懵逼，只有追加日志吧
	private String importServiceModule(Sheet sheet , Cell cell ,String srvName ,Session session ,SrvType srvType){
		log.info("debug --> importServiceModule");
		String result = "";
		//复合服务逻辑使用
		String comLogic = "";
		Map<String,SrvStatus> srvStatusMap = PlatformConfigInfo.srvStatusMap;
		Map srvTypeMap = PlatformConfigInfo.srvTypeMap; //服务类型信息
		Map siTxdoRouteTypeMap = PlatformConfigInfo.siTxdoRouteTypeMap;//加载路由类型信息
		Map txdoSystemMap = PlatformConfigInfo.txdoSystemMap; //加载txdo系统(CRM BOSS)
		// soap系统不缓存最初数据源数据  liuc 0513
		Map soaphttpSystemMap = PlatformConfigInfo.soaphttpSystemMap;//加载SOAP系统信息(hsf zk_mmc)
		Map srvTimeoutCfgMap = PlatformConfigInfo.srvTimeoutCfgMap; //加载服务超时配置信息

		Range [] ranges = sheet.getMergedCells();//合并单元格数组
		Range currentRange = null;
		int currentCol = cell.getColumn();
		int cellInitRow = cell.getRow();

		for(int j=0; j<ranges.length; j++) {
			Range range = ranges[j];
			if(range.getTopLeft().getColumn()== currentCol &&
					range.getTopLeft().getRow()<= cell.getRow() &&
					cell.getRow() <= range.getBottomRight().getRow()) {
				currentRange = range;
				break;
			}
		}

		int cellEndRow = 0;
		if(currentRange!=null){
			cellEndRow = currentRange.getBottomRight().getRow();
		}

		//补全空字段
		AppInfo appInfo = null;
		String srvPubInfo = "";
		String businessUuid = "-1";
		String srvUuid = "-1";

		SrvInfoDAO srvInfoDAO = new SrvInfoDAO();
		// liuc 0510
		SrvInfo srvInfo = null;

		/* 创建实例对象 */
		switch (srvType.getSrvType().intValue()) {
			case -1://复合服务
				srvInfo = new SiTxdComSrvInfo();
				srvInfo.setSrvType((SrvType)srvTypeMap.get(String.valueOf(AppSrvConstant.SRVTYPE_SITXD_COM)));
				break;
			case 0://原子服务_tuxedo
				srvInfo = new SiTxdSrvInfo();
				srvInfo.setSrvType((SrvType)srvTypeMap.get(String.valueOf(AppSrvConstant.SRVTYPE_SITECH_TXDO)));
				break;
			case 1://原子服务—soap
				srvInfo = new AgentSoapSrvInfo();
				srvInfo.setSrvType((SrvType)srvTypeMap.get(String.valueOf(AppSrvConstant.SRVTYPE_SOAP)));
				break;
			case 2://自定义soap
				srvInfo = new SelfSoapSrvInfo();
				srvInfo.setSrvType((SrvType)srvTypeMap.get(String.valueOf(AppSrvConstant.SRVTYPE_SOAP_COM)));
				break;
			case 3://tongeasy服务
				srvInfo = new TongEASYSrvInfo();
				srvInfo.setSrvType((SrvType)srvTypeMap.get(String.valueOf(AppSrvConstant.SRVTYPE_TONGEASY)));
				break;
			case 4://复合服务XML
				srvInfo = new XmlComInfo();
				srvInfo.setSrvType((SrvType)srvTypeMap.get(String.valueOf(AppSrvConstant.SRVTYPE_XMLSTR_COM)));
				break;
			case 8://原子服务_utype
				srvInfo = new SiUtypeWsSrvInfo();
				srvInfo.setSrvType((SrvType)srvTypeMap.get(String.valueOf(AppSrvConstant.SRVTYPE_SIUTYPEWS)));
				break;
			case 9://原始utype0
				srvInfo = new Utype0Ws();
				srvInfo.setSrvType((SrvType)srvTypeMap.get(String.valueOf(AppSrvConstant.SRVTYPE_UTYPE0)));
				break;
			case 5://原子服务 http
				srvInfo = new HttpSrvinfo();
				srvInfo.setSrvType((SrvType)srvTypeMap.get(String.valueOf(AppSrvConstant.SRVTYPE_ATOMHTTP)));
				break;
			case 13://原子服务TuxedoJSON
				srvInfo = new TxdoJsonSrvInfo();
				srvInfo.setSrvType((SrvType)srvTypeMap.get(String.valueOf(AppSrvConstant.SRVTYPE_TXDO_JSON)));
				break;
			case 14://复合服务String
				srvInfo = new StrComSrvInfo();
				srvInfo.setSrvType((SrvType)srvTypeMap.get(String.valueOf(AppSrvConstant.SRVTYPE_SOAP_STRING_COM)));
				break;
			case 15://复合服务RestString
				srvInfo = new StrComRestSrvInfo();
				srvInfo.setSrvType((SrvType)srvTypeMap.get(String.valueOf(AppSrvConstant.SRVTYPE_REST_STRING_COM)));
				break;
			case 16://原子服务Hsf
				srvInfo = new HsfSrvinfo();
				srvInfo.setSrvType((SrvType)srvTypeMap.get(String.valueOf(AppSrvConstant.SRVTYPE_HSF))); //设置服务类型
				break;
			default:
				result = "服务类型无效！";
				break;
		}
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		srvInfo.setSrvModifyTime(format.format(date));
		List excelColmList = new ArrayList();//tuxedo原子服务一级字段list
		List excelInputList = new ArrayList();//tuxedo原子服务入参list
		List excelOutputList = new ArrayList();//tuxedo原子服务出参list
		List excelErrorList = new ArrayList();//tuxedo原子服务错误代码list

		ExcelStyleDao excelStyleDao = new ExcelStyleDao();

		//当前service的共有多少个字段
		excelColmList = excelStyleDao.getExcelColm(Long.valueOf(0),srvType);
		excelInputList = excelStyleDao.getExcelColm(Long.valueOf(1),srvType);
		excelOutputList = excelStyleDao.getExcelColm(Long.valueOf(2),srvType);
		excelErrorList = excelStyleDao.getExcelColm(Long.valueOf(3),srvType);

		ExcelStyle excelStyle = null;
		String funName = null;
		String styleFlag = null;

		//输入参数,输出参数,错误代码
		ExcelStyle inputExl = null;
		ExcelStyle outputExl = null;
		ExcelStyle errExl = null;

		ExcelStyleDao excelDao = new ExcelStyleDao();

		List inputExlList = excelDao.findByPropertys(new String[]{"colm_style_type","COLM_STYLE_FLAG","COLM_STYLE_DISPLAY"},
				new Object[]{srvType,"1","1"}, session);

		if(inputExlList.size()>0){
			inputExl = (ExcelStyle)inputExlList.get(0);
		}

		List outputExlList = excelDao.findByPropertys(new String[]{"colm_style_type","COLM_STYLE_FLAG","COLM_STYLE_DISPLAY"},
				new Object[]{srvType,"2","1"}, session);

		if(outputExlList.size()>0){
			outputExl = (ExcelStyle)outputExlList.get(0);
		}

		List errExlList = excelDao.findByPropertys(new String[]{"colm_style_type","COLM_STYLE_FLAG","COLM_STYLE_DISPLAY"},
				new Object[]{srvType,"3","1"}, session);

		if(errExlList.size()>0){
			errExl = (ExcelStyle)errExlList.get(0);
		}

		for(int i=0;i<excelColmList.size();i++){
			excelStyle = (ExcelStyle)excelColmList.get(i);
			funName = excelStyle.getColm_style_default();
			styleFlag = excelStyle.getColm_style_flag();
			if ((funName == null || "".equals(funName))){
				continue;
			}
			if(funName != null && "num".equals(funName) && "N".equals(styleFlag)){
				continue;
			}

			try {
				Class clazz = Class.forName("com.sitech.esb.hb.SrvInfo");
				String methodName = "set"+funName.substring(0,1).toUpperCase()+funName.substring(1);
				if("isSwsdlJar".equals(funName)){
					methodName = "set"+funName;
				}
				Method method = null;
				if("setSrvType".equals(methodName)){
					continue;
				}else {
					method = clazz.getDeclaredMethod(methodName, Class.forName(excelStyle.getColm_cont_type()));
				}
				excelForeach(inputExl,outputExl,errExl,
						excelStyle,method,srvInfo,sheet,
						i,excelInputList,excelOutputList,excelErrorList,
						cellInitRow ,methodName,siTxdoRouteTypeMap,txdoSystemMap,soaphttpSystemMap,srvTimeoutCfgMap,Class.forName(excelStyle.getColm_cont_type()));

			} catch (Exception e) {
				log.error("liuc",e);
			}
		}
		//保持原有的应用关系
		srvInfo.setAppInfo(appInfo);
		srvInfo.setSrvPubInfo(srvPubInfo);
		srvInfo.setBusinessUuid(businessUuid);
		srvInfo.setSrvUuid(srvUuid);
		srvInfo.setSrvType(srvType);

		//导入Excel内容在数据库中不存在
		if(srvInfoApp==null){
			srvInfoApp = srvInfo;
		}
		TxdoSystem txdoSystem = null;
		AppInfo appOld = null;
		SrvTimeoutCfg srvTimeoutCfg = null;
		Long isCheck = null;
		String retCodeJpath = null;
		String retMsgJpath = null;
		Long isdumpCoremsg = null;
		Long needSaveInParam = null;
		Long needSaveOutParam = null;
		Long validFlag = null;
		Long needInparamCheck = null;
		Long isSwsdlJar = null;
		//如果是覆盖操作，首先取得源srvInfo信息
		if(srvInfoApp!=null){
			txdoSystem = srvInfoApp.getTxdoSystem();
			appOld = srvInfoApp.getAppInfo();
			srvTimeoutCfg = srvInfoApp.getSrvTimeoutCfg();
			isCheck = srvInfoApp.getIsCheck();
			retCodeJpath = srvInfoApp.getRetCodeJpath();
			retMsgJpath = srvInfoApp.getRetMsgJpath();
			isdumpCoremsg = srvInfoApp.getIsdumpCoremsg();
			needSaveInParam = srvInfoApp.getNeedSaveInParam();
			needSaveOutParam = srvInfoApp.getNeedSaveOutParam();
			validFlag = srvInfoApp.getValidFlag();
			needInparamCheck = srvInfoApp.getNeedInparamCheck();
			isSwsdlJar = srvInfoApp.getisSwsdlJar();
		}

		//处理服务状态，不管是覆盖还是不覆盖，全部置成发布中，以方便实现服务自动发布
		SrvStatus srvStatusTemp = srvStatusMap.get(String.valueOf(AppSrvConstant.SRV_PUBLISHING));
		log.info("liuc: srvStatusTemp is "+srvStatusTemp);
		srvInfo.setSrvStatus(srvStatusTemp);

		//应用信息
		if(appOld!=null){
			srvInfo.setAppInfo(appOld);
		}

		//设置默认路由类型
		if(srvInfo.getSiTxdoRouteType() == null){
			SiTxdoRouteType routeType = new SiTxdoRouteType();
			routeType.setRouteTypeName("默认路由");
			routeType.setRouteType(Long.valueOf(0));
			srvInfo.setSiTxdoRouteType(routeType);
		}

		//设置超时时间
		if(srvTimeoutCfg==null){
			srvTimeoutCfg = new SrvTimeoutCfg();
			srvTimeoutCfg.setAlertTime(Long.valueOf(2));
			srvTimeoutCfg.setAverageTime(Long.valueOf(4));
			srvTimeoutCfg.setCfgId(Long.valueOf(2));
			srvTimeoutCfg.setRecDuration(Long.valueOf(5));
			srvTimeoutCfg.setTimeDuration(Long.valueOf(8));
		}
		srvInfo.setSrvTimeoutCfg(srvTimeoutCfg);
		//设置其它信息
		if(isCheck==null){
			isCheck = Long.valueOf(0);
		}
		srvInfo.setIsCheck(isCheck);
		//设置返回路径
		if(retCodeJpath==null){
			retCodeJpath = "$.ROOT.RETURN_CODE";
		}
		srvInfo.setRetCodeJpath(retCodeJpath);
		if(retMsgJpath==null){
			retMsgJpath = "$.ROOT.RETURN_MSG";
		}
		srvInfo.setRetMsgJpath(retMsgJpath);
		if(needSaveInParam==null){
			needSaveInParam = Long.valueOf(0);
		}
		srvInfo.setNeedSaveInParam(needSaveInParam);
		if(needSaveOutParam==null){
			needSaveOutParam = Long.valueOf(0);
		}
		srvInfo.setNeedSaveOutParam(needSaveOutParam);
		if(isdumpCoremsg==null){
			isdumpCoremsg = Long.valueOf(0);
		}
		srvInfo.setIsdumpCoremsg(isdumpCoremsg);
		if(validFlag==null){
			validFlag = Long.valueOf(1);
		}
		srvInfo.setValidFlag(validFlag);
		if(needInparamCheck==null){
			needInparamCheck = Long.valueOf(0);
		}
		srvInfo.setNeedInparamCheck(needInparamCheck);
		//设置TxdoSystem信息
		if(txdoSystem==null){
			txdoSystem = new TxdoSystem();
			txdoSystem.setTxdoSystemId(Long.valueOf(1));
			txdoSystem.setTxdoSystemName("NGCRM");
			txdoSystem.setWtcApPolicy("ON_STARTUP");
			txdoSystem.setSystemFlag(Long.valueOf(1));
			txdoSystem.setWtcBlockTime(Long.valueOf(60));
			txdoSystem.setValidFlag(Long.valueOf(1));
		}
		srvInfo.setTxdoSystem(txdoSystem);
		//为soap服务设置初始值
		if(isSwsdlJar==null){
			isSwsdlJar = Long.valueOf(0);
		}
		srvInfo.setisSwsdlJar(isSwsdlJar);
		Long isUnifySrv = srvInfo.getIsUnifySrv();
		if(isUnifySrv == null){
			srvInfo.setIsUnifySrv(Long.valueOf(0));
		}
		//by liwei, 短厅好几个服务老是出现imparamsDemo字段为空bug，先手动加上
		if(srvInfo.getInparamsDemo()==null){
			log.info(srvName+" inparamsDemo is null,so add {}");
			srvInfo.setInparamsDemo("{}");
		}
		//执行保存操作
		try{
			session.save(srvInfo);
		}catch (Exception e){
			log.error("liuc:save srvinfo error",e);
		}
		ok:
		for(int i=0;i<excelColmList.size();i++){
			excelStyle = (ExcelStyle)excelColmList.get(i);
			//入参
			if (null != inputExl && excelStyle.getColm_style_position().compareTo(inputExl.getColm_style_position())==0){

				int position = excelStyle.getColm_style_position().intValue();
				//xx < out < err
				if(inputExl != null && outputExl != null && errExl != null
						&& inputExl.getColm_style_position().compareTo(outputExl.getColm_style_position())<0
						&& inputExl.getColm_style_position().compareTo(errExl.getColm_style_position())<0){
					position = excelStyle.getColm_style_position().intValue();
				}//xx > out < err
				else if(inputExl != null && outputExl != null && errExl != null
						&& inputExl.getColm_style_position().compareTo(outputExl.getColm_style_position())>0
						&& inputExl.getColm_style_position().compareTo(errExl.getColm_style_position())<0){
					position = excelStyle.getColm_style_position().intValue()+excelOutputList.size()-1;
				}//xx < out > err
				else if(inputExl != null && outputExl != null && errExl != null
						&& inputExl.getColm_style_position().compareTo(outputExl.getColm_style_position())<0
						&& inputExl.getColm_style_position().compareTo(errExl.getColm_style_position())>0){
					position = excelStyle.getColm_style_position().intValue()+excelErrorList.size()-1;
				}//xx > out > err
				else if(inputExl != null && outputExl != null && errExl != null
						&& inputExl.getColm_style_position().compareTo(outputExl.getColm_style_position())>0
						&& inputExl.getColm_style_position().compareTo(errExl.getColm_style_position())>0){
					position = excelStyle.getColm_style_position().intValue()+excelOutputList.size()+excelErrorList.size()-2;
				}
				for(int j=cellInitRow; j<=cellEndRow; j++) {
					//判断多个参数个数  如果一行都没有值就跳出继续start
					List list = new ArrayList();
					for(int n=0;n<excelInputList.size();n++){
						String val = sheet.getCell(position+n, j).getContents();
						if(val != null && !"".equals(val)){
							list.add(val);
						}
					}
					if(list.size()<1){
						continue ok;
					}
					//判断多个参数个数  如果一行都没有值就跳出继续end
					SrvInput srvInput = new SrvInput();
					try {
						Class clazz = Class.forName("com.sitech.esb.hb.SrvInput");
						srvInput.setSrvInfo(srvInfo);
						for(int w=0;w<excelInputList.size();w++){
							ExcelStyle style = (ExcelStyle)excelInputList.get(w);
							String fieldName = style.getColm_style_default();
							String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
							Method[] methods = clazz.getDeclaredMethods();
							for(int n=0;n<methods.length;n++){
								if(methods[n].getName().equals(methodName)){
									String val = sheet.getCell(position+w, j).getContents();

									if("setIsFixedValue".equals(methodName)	|| "setInputNull".equals(methodName) || "setIsFixedVisible".equals(methodName)){
										if("N".equals(val)){
											methods[n].invoke(srvInput, new Long("0"));
										}else {
											methods[n].invoke(srvInput, new Long("1"));
										}

									}else if ("setInputNum".equals(methodName)){
										methods[n].invoke(srvInput, new Long(val));
									}else if("setFixedValue".equals(methodName)){
										Method mth = clazz.getMethod("setIsFixedValue", Long.class);
										if(!"".equals(val)){
											mth.invoke(srvInput, new Long("1"));
										}else {
											mth.invoke(srvInput, new Long("0"));
										}
									}else{
										methods[n].invoke(srvInput, val);
									}
								}
							}
						}
					} catch (Exception e) {
						log.error("liuc-for-ok",e);
					}
					SrvInputDAO srvInputDAO = new SrvInputDAO();
					if(null == srvInput.getIsFixedValue()){
						srvInput.setIsFixedValue(new Long("0"));
					}
					if(null == srvInput.getIsFixedVisible()){
						srvInput.setIsFixedVisible(new Long("0"));
					}
					srvInputDAO.save(srvInput, session);
				}
				continue;
			}
			//出参
			if (null != outputExl && excelStyle.getColm_style_position().compareTo(outputExl.getColm_style_position())==0){
				int position = excelStyle.getColm_style_position().intValue();
				if(inputExl != null && outputExl != null && errExl != null
						&& outputExl.getColm_style_position().compareTo(inputExl.getColm_style_position())<0
						&& outputExl.getColm_style_position().compareTo(errExl.getColm_style_position())<0){
					position = excelStyle.getColm_style_position().intValue();
				}//xx > out < err
				else if(inputExl != null && outputExl != null && errExl != null
						&& outputExl.getColm_style_position().compareTo(inputExl.getColm_style_position())>0
						&& outputExl.getColm_style_position().compareTo(errExl.getColm_style_position())<0){
					position = excelStyle.getColm_style_position().intValue()+excelInputList.size()-1;
				}//xx < out > err
				else if(inputExl != null && outputExl != null && errExl != null
						&& outputExl.getColm_style_position().compareTo(inputExl.getColm_style_position())<0
						&& outputExl.getColm_style_position().compareTo(errExl.getColm_style_position())>0){
					position = excelStyle.getColm_style_position().intValue()+excelErrorList.size()-1;
				}//xx > out > err
				else if(inputExl != null && outputExl != null && errExl != null
						&& outputExl.getColm_style_position().compareTo(inputExl.getColm_style_position())>0
						&& outputExl.getColm_style_position().compareTo(errExl.getColm_style_position())>0){
					position = excelStyle.getColm_style_position().intValue()+excelInputList.size()+excelErrorList.size()-2;
				}
				for(int j=cellInitRow; j<=cellEndRow; j++) {
					//判断多个参数个数  如果一行都没有值就跳出继续start
					List list = new ArrayList();
					for(int n=0;n<excelOutputList.size();n++){
						String val = sheet.getCell(position+n, j).getContents();
						if(val != null && !"".equals(val)){
							list.add(val);
						}
					}
					if(list.size()<1){
						continue ok;
					}
					//判断多个参数个数  如果一行都没有值就跳出继续end
					SrvOutput srvOutput = new SrvOutput();
					try {
						Class clazz = Class.forName("com.sitech.esb.hb.SrvOutput");
						srvOutput.setSrvInfo(srvInfo);
						for(int w=0;w<excelOutputList.size();w++){
							ExcelStyle style = (ExcelStyle)excelOutputList.get(w);
							String fieldName = style.getColm_style_default();
							String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
							Method[] methods = clazz.getDeclaredMethods();
							for(int n=0;n<methods.length;n++){
								if(methods[n].getName().equals(methodName)){
									String val = sheet.getCell(position+w, j).getContents();
									if("setOutputId".equals(methodName) || "setOutputNum".equals(methodName)){
										methods[n].invoke(srvOutput, new Long(val));
									}else{
										methods[n].invoke(srvOutput, val);
									}
								}
							}
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					SrvOutputDAO srvOutputDAO = new SrvOutputDAO();
					srvOutputDAO.save(srvOutput, session);
				}
				continue;
			}
			//错误代码
			if (null != errExl && excelStyle.getColm_style_position().compareTo(errExl.getColm_style_position())==0){
				int position = excelStyle.getColm_style_position().intValue();

				if(inputExl != null && outputExl != null && errExl != null
						&& errExl.getColm_style_position().compareTo(inputExl.getColm_style_position())<0
						&& errExl.getColm_style_position().compareTo(outputExl.getColm_style_position())<0){
					position = excelStyle.getColm_style_position().intValue();
				}//xx > out < err
				else if(inputExl != null && outputExl != null && errExl != null
						&& errExl.getColm_style_position().compareTo(inputExl.getColm_style_position())>0
						&& errExl.getColm_style_position().compareTo(outputExl.getColm_style_position())<0){
					position = excelStyle.getColm_style_position().intValue()+excelInputList.size()-1;
				}//xx < out > err
				else if(inputExl != null && outputExl != null && errExl != null
						&& errExl.getColm_style_position().compareTo(inputExl.getColm_style_position())<0
						&& errExl.getColm_style_position().compareTo(outputExl.getColm_style_position())>0){
					position = excelStyle.getColm_style_position().intValue()+excelOutputList.size()-1;
				}//xx > out > err
				else if(inputExl != null && outputExl != null && errExl != null
						&& errExl.getColm_style_position().compareTo(inputExl.getColm_style_position())>0
						&& errExl.getColm_style_position().compareTo(outputExl.getColm_style_position())>0){
					position = excelStyle.getColm_style_position().intValue()+excelInputList.size()+excelOutputList.size()-2;
				}

				for(int j=cellInitRow; j<=cellEndRow; j++) {
					//判断多个参数个数  如果一行都没有值就跳出继续start
					List list = new ArrayList();
					for(int n=0;n<excelErrorList.size();n++){
						String val = sheet.getCell(position+n, j).getContents();
						if(val != null && !"".equals(val)){
							list.add(val);
						}
					}
					if(list.size()<1){
						continue ok;
					}
					//判断多个参数个数  如果一行都没有值就跳出继续end
					SrvErrinfo srvErrinfo = new SrvErrinfo();
					try {
						Class clazz = Class.forName("com.sitech.esb.hb.SrvErrinfo");
						srvErrinfo.setSrvInfo(srvInfo);
						for(int w=0;w<excelErrorList.size();w++){
							ExcelStyle style = (ExcelStyle)excelErrorList.get(w);
							String fieldName = style.getColm_style_default();
							String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
							Method[] methods = clazz.getDeclaredMethods();
							for(int n=0;n<methods.length;n++){
								if(methods[n].getName().equals(methodName)){
									String val = sheet.getCell(position+w, j).getContents();
									methods[n].invoke(srvErrinfo, val);
								}
							}
						}
					} catch (Exception e) {
						log.error("liuc:",e);
					}
					SrvErrinfoDAO srvErrinfoDAO = new SrvErrinfoDAO();
					srvErrinfoDAO.save(srvErrinfo, session);
				}
				continue;
			}
		}

		Long srvInfoType = srvInfo.getSrvType().getSrvType();
		//=========================================
		//复合服务
		if(srvInfoType.compareTo(Long.valueOf(-1)) ==0  || srvInfoType.compareTo(Long.valueOf(4)) == 0 || srvInfoType.compareTo(Long.valueOf(2)) == 0
				|| srvInfoType.compareTo(Long.valueOf(14))==0){
			//复合服务逻辑与服务关系
			List comLogicList = new ArrayList();
			if(comLogic!=null && comLogic.length()>0)  {
				Pattern patternComSoap2 = Pattern.compile("newWsReqObj\\(\"(.+?)\"");
				Matcher matcherComSoap2 = patternComSoap2.matcher(comLogic);
				while(matcherComSoap2.find()){
					comLogicList.add(matcherComSoap2.group(1));
				}

				Pattern patternComSoap1 = Pattern.compile("newWsReqObj\\(\'(.+?)\'");
				Matcher matcherComSoap1 = patternComSoap1.matcher(comLogic);
				while(matcherComSoap1.find()){
					comLogicList.add(matcherComSoap1.group(1));
				}

				Pattern patternComSi2 = Pattern.compile("epCall\\(\"(.+?)\"");
				Matcher matcherComSi2 = patternComSi2.matcher(comLogic);
				while(matcherComSi2.find()){
					comLogicList.add(matcherComSi2.group(1));
				}

				Pattern patternComSi1 = Pattern.compile("epCall\\(\'(.+?)\'");
				Matcher matcherComSi1 = patternComSi1.matcher(comLogic);
				while(matcherComSi1.find()){
					comLogicList.add(matcherComSi1.group(1));
				}
			}
			SrvRelationDAO srvRelaitonDAO = new SrvRelationDAO();
			if(null!=comLogicList && comLogicList.size()>0){
				Set srvSet = new HashSet(comLogicList);
				comLogicList.clear();
				comLogicList.addAll(srvSet);
			}
			if(comLogicList.size()>0) {
				boolean comLogicValid = true;
				String errorSrvName ="";
				for(int i=0; i<comLogicList.size(); i++) {
					if(srvInfoDAO.findCountByProperty("srvName", comLogicList.get(i), session)<1) {
						comLogicValid = false ;
						errorSrvName = (String)comLogicList.get(i);
						break;
					}
				}
				log.info("liuc:errorSrvName is "+errorSrvName);
				if(comLogicValid) {
					for(int i=0; i<comLogicList.size(); i++) {
						SrvInfo subSrv = (SrvInfo)(srvInfoDAO.findBySrvName(comLogicList.get(i), session).get(0));
						if(subSrv!= null) {
							SrvRelation srvRelation = new SrvRelation(srvInfo, subSrv, new Long(i+1));
							srvRelaitonDAO.save(srvRelation, session);
						}
					}
				} else {
					srvInfo.setSrvPubInfo("复合服务配置错误，服务" + errorSrvName + "不存在，无法调用，请检查复合服务逻辑！");
					srvInfo.setSrvStatus(srvStatusMap.get(String.valueOf(AppSrvConstant.SRV_CONFIG_ERROR)));
				}
			}
		}

		//=========================================
		//服务分类关系,需先判断数据库中是否有公共分类
		Set set = null;
		SrvCategDAO srvCategDAO = new SrvCategDAO();
		SrvCateg srvCateg = srvCategDAO.findById(new Long(1), session);
		if(srvCateg != null) {
			set = srvInfo.getSrvCategs();
			set.add(srvCateg);
		}
		return result;
	}

	private void excelForeach(ExcelStyle inputExl,ExcelStyle outputExl,ExcelStyle errExl,
							  ExcelStyle excelStyle,Method method,Object srvInfo,Sheet sheet,
							  int i,List excelInputList,List excelOutputList,List excelErrorList,
							  int cellInitRow ,String methodName,
							  Map siTxdoRouteTypeMap,Map txdoSystemMap,Map soaphttpSystemMap,Map srvTimeoutCfgMap,Class clazz) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException{
		if(null != inputExl && null != outputExl && null != errExl  && null != excelStyle
				&& (excelStyle.getColm_style_position().compareTo(inputExl.getColm_style_position())>0)
				&& (excelStyle.getColm_style_position().compareTo(outputExl.getColm_style_position())<0
				&& (excelStyle.getColm_style_position().compareTo(errExl.getColm_style_position())<0))){

			doServiceField(method, srvInfo, sheet, i+excelInputList.size()-1, excelInputList,
					excelOutputList, excelErrorList, cellInitRow, methodName,
					siTxdoRouteTypeMap, txdoSystemMap ,soaphttpSystemMap,srvTimeoutCfgMap,clazz);

		}else if(null != inputExl && null != outputExl && null != errExl  && null != excelStyle
				&& (excelStyle.getColm_style_position().compareTo(outputExl.getColm_style_position())>0)
				&& (excelStyle.getColm_style_position().compareTo(inputExl.getColm_style_position())<0)
				&& (excelStyle.getColm_style_position().compareTo(errExl.getColm_style_position())<0)){

			doServiceField(method, srvInfo, sheet, i+excelOutputList.size()-1, excelInputList,
					excelOutputList, excelErrorList, cellInitRow, methodName,
					siTxdoRouteTypeMap, txdoSystemMap ,soaphttpSystemMap,srvTimeoutCfgMap,clazz);

		}else if(null != inputExl && null != outputExl && null != errExl  && null != excelStyle
				&& (excelStyle.getColm_style_position().compareTo(errExl.getColm_style_position())>0)
				&& (excelStyle.getColm_style_position().compareTo(outputExl.getColm_style_position())<0)
				&& (excelStyle.getColm_style_position().compareTo(inputExl.getColm_style_position())<0)){
			doServiceField(method, srvInfo, sheet, i+excelErrorList.size()-1, excelInputList,
					excelOutputList, excelErrorList, cellInitRow, methodName,
					siTxdoRouteTypeMap, txdoSystemMap ,soaphttpSystemMap,srvTimeoutCfgMap,clazz);

		}else if(null != inputExl && null != outputExl && null != errExl  && null != excelStyle
				&& (excelStyle.getColm_style_position().compareTo(inputExl.getColm_style_position())>0)
				&& (excelStyle.getColm_style_position().compareTo(outputExl.getColm_style_position())>0)
				&& (excelStyle.getColm_style_position().compareTo(errExl.getColm_style_position())<0)){

			doServiceField(method, srvInfo, sheet, i+excelInputList.size()-1+excelOutputList.size()-1,
					excelInputList, excelOutputList, excelErrorList, cellInitRow, methodName,
					siTxdoRouteTypeMap, txdoSystemMap ,soaphttpSystemMap,srvTimeoutCfgMap,clazz);

		}else if(null != inputExl && null != outputExl && null != errExl  && null != excelStyle
				&& (excelStyle.getColm_style_position().compareTo(inputExl.getColm_style_position())>0)
				&& (excelStyle.getColm_style_position().compareTo(errExl.getColm_style_position())>0)
				&& (excelStyle.getColm_style_position().compareTo(outputExl.getColm_style_position())<0)){

			doServiceField(method, srvInfo, sheet, i+excelInputList.size()-1+excelErrorList.size()-1,
					excelInputList, excelOutputList, excelErrorList, cellInitRow, methodName,
					siTxdoRouteTypeMap, txdoSystemMap ,soaphttpSystemMap,srvTimeoutCfgMap,clazz);

		}else if(null != inputExl && null != outputExl && null != errExl  && null != excelStyle
				&& (excelStyle.getColm_style_position().compareTo(errExl.getColm_style_position())>0)
				&& (excelStyle.getColm_style_position().compareTo(outputExl.getColm_style_position())>0)
				&& (excelStyle.getColm_style_position().compareTo(inputExl.getColm_style_position())<0)){

			doServiceField(method, srvInfo, sheet, i+excelErrorList.size()-1+excelOutputList.size()-1,
					excelInputList, excelOutputList, excelErrorList, cellInitRow, methodName,
					siTxdoRouteTypeMap, txdoSystemMap ,soaphttpSystemMap,srvTimeoutCfgMap,clazz);

		}else if(null != inputExl && null != outputExl && null != errExl  && null != excelStyle
				&& (excelStyle.getColm_style_position().compareTo(inputExl.getColm_style_position())>0)
				&& (excelStyle.getColm_style_position().compareTo(outputExl.getColm_style_position())>0)
				&& (excelStyle.getColm_style_position().compareTo(errExl.getColm_style_position())>0)){

			doServiceField(method, srvInfo, sheet, i+excelInputList.size()-1+excelOutputList.size()-1+excelErrorList.size()-1,
					excelInputList, excelOutputList, excelErrorList, cellInitRow, methodName,
					siTxdoRouteTypeMap, txdoSystemMap ,soaphttpSystemMap,srvTimeoutCfgMap,clazz);

		}else if(null != inputExl && null != outputExl && null != errExl  && null != excelStyle
				&& (excelStyle.getColm_style_position().compareTo(inputExl.getColm_style_position())<0)
				&& (excelStyle.getColm_style_position().compareTo(outputExl.getColm_style_position())<0)
				&& (excelStyle.getColm_style_position().compareTo(errExl.getColm_style_position())<0)){

			doServiceField(method, srvInfo, sheet, i, excelInputList, excelOutputList, excelErrorList,
					cellInitRow, methodName, siTxdoRouteTypeMap, txdoSystemMap ,soaphttpSystemMap,srvTimeoutCfgMap,clazz);

		}//只有错误 代码的时候   在错误代码后面
		else if(null == inputExl && null == outputExl && null != errExl  && null != excelStyle
				&& (excelStyle.getColm_style_position().compareTo(errExl.getColm_style_position())>0) ){
			doServiceField(method, srvInfo, sheet, i+excelErrorList.size()-1, excelInputList,
					excelOutputList, excelErrorList, cellInitRow, methodName,
					siTxdoRouteTypeMap, txdoSystemMap ,soaphttpSystemMap,srvTimeoutCfgMap,clazz);
		}//只有错误 代码的时候   在错误代码前面
		else if(null == inputExl && null == outputExl && null != errExl  && null != excelStyle
				&& (excelStyle.getColm_style_position().compareTo(errExl.getColm_style_position()) < 0) ){
			doServiceField(method, srvInfo, sheet, i, excelInputList,
					excelOutputList, excelErrorList, cellInitRow, methodName,
					siTxdoRouteTypeMap, txdoSystemMap ,soaphttpSystemMap,srvTimeoutCfgMap,clazz);
		}
		//新增判断逻辑,错误代码为空
		else if(excelStyle!=null && errExl==null){
			doServiceField(method, srvInfo, sheet, i, excelInputList,
					excelOutputList, excelErrorList, cellInitRow, methodName,
					siTxdoRouteTypeMap, txdoSystemMap ,soaphttpSystemMap,srvTimeoutCfgMap,clazz);
		}
	}


	private void doServiceField(Method method,Object srvInfo,Sheet sheet,
								int i,List excelInputList,List excelOutputList,List excelErrorList,
								int cellInitRow ,String methodName,
								Map siTxdoRouteTypeMap,Map txdoSystemMap,Map soaphttpSystemMap,Map srvTimeoutCfgMap,Class clazz) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException{
		if("com.sitech.esb.hb.SiTxdoRouteType".equals(clazz.getName())){
			Set set = siTxdoRouteTypeMap.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				SiTxdoRouteType siTxdoRouteType = (SiTxdoRouteType)siTxdoRouteTypeMap.get(it.next());
				if(siTxdoRouteType.getRouteTypeName().equals(sheet.getCell(i, cellInitRow).getContents())) {
					method.invoke(srvInfo, siTxdoRouteType);
				}
			}
		}else if ("com.sitech.esb.hb.TxdoSystem".equals(clazz.getName())){
			Set set = txdoSystemMap.keySet();
			Iterator it = set.iterator();
			it = set.iterator();
			while (it.hasNext()) {
				TxdoSystem txdoSystem = (TxdoSystem)txdoSystemMap.get(it.next());
				if(txdoSystem.getTxdoSystemName().equals(sheet.getCell(i, cellInitRow).getContents())) {
					method.invoke(srvInfo, txdoSystem);
				}
			}
		}else if("com.sitech.esb.hb.SoaphttpSystem".equals(clazz.getName())){
			//解决当txdo/json类型的服务导入也会走这段
			boolean flag = true;
			String content = sheet.getCell(i, cellInitRow).getContents();
			if(content!=null && !"".equals(content)){
				Set set = soaphttpSystemMap.keySet();
				Iterator it = set.iterator();
				while (it.hasNext()) {
					SoaphttpSystem soaphttpSystem = (SoaphttpSystem)soaphttpSystemMap.get(it.next());
					if(soaphttpSystem.getSystemName().equals(sheet.getCell(i, cellInitRow).getContents())) {
						flag= false;
						method.invoke(srvInfo, soaphttpSystem);
					}
				}
				//当SOAP系统不存在返回具体池名称 add by liuc
				if(flag){
					String poolCfg = AutoHibernateSessionFactory.getThreadConfigFile();
					hasSoapResult+= poolCfg+"上没有("+sheet.getCell(i, cellInitRow).getContents()+")SOAP系统;";
				}
			}
		}else if("com.sitech.esb.hb.SrvTimeoutCfg".equals(clazz.getName())){
			Set set = srvTimeoutCfgMap.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				SrvTimeoutCfg srvTimeoutCfg = (SrvTimeoutCfg)srvTimeoutCfgMap.get(it.next());
				if(srvTimeoutCfg.getCfgId().toString().equals(sheet.getCell(i, cellInitRow).getContents())) {
					method.invoke(srvInfo, srvTimeoutCfg);
				}
			}
		}
		else if ("java.lang.Long".equals(clazz.getName())){
			String temp = sheet.getCell(i, cellInitRow).getContents();
			if(temp==null || "".equals(temp)){
				temp = "-1";
			}else if("Y".equals(temp.trim())){
				temp = "1";
			}else if("N".equals(temp.trim())){
				temp = "0";
			}else if("不验证".equals(temp.trim())){
				temp = "0";
			}else if("只验证入参".equals(temp.trim())){
				temp = "1";
			}else if("只验证出参".equals(temp.trim())){
				temp = "10";
			}else if("出入参都验证".equals(temp.trim())){
				temp = "11";
			}
			method.invoke(srvInfo, new Long(temp));
		}//二进制字段
		else if("java.sql.Clob".equals(clazz.getName()) ){
			method.invoke(srvInfo, Hibernate.createClob(sheet.getCell(i, cellInitRow).getContents()));
		}else{
			//利用反射机制，将excel表格中字段设置到srvInfo实体对象中
			String content = sheet.getCell(i, cellInitRow).getContents();
			if(StringUtils.isNotBlank(content)){
				method.invoke(srvInfo, content);
			}
		}
	}

	private String importComTuxedoService(Sheet sheet, Cell cell, String srvName, Session session) {
		log.debug("调用 importComTuxedoService, 导入服务"+ srvName);
		String result = "";
		SrvType srvType = new SrvTypeDAO().findById(Long.valueOf(-1), session);
		importServiceModule(sheet, cell, srvName, session, srvType);
		return result;
	}
	//遍历服务，得到excel中的服务名和中文名
	public String getServiceNameFromExcel(Workbook book) {
		StringBuffer serviceNameStr = new StringBuffer();
		StringBuffer serviceNameSameStr = new StringBuffer();
		try {
			Sheet [] sheets = book.getSheets();

			for(int i=0; i<sheets.length; i++) {
				Sheet sheet = sheets[i];
				int firstServiceRow = getContentFirstRowIndex(sheet);
				int sheetRows = sheet.getRows();
				//检测是否出现相同服务名
				// 这代码简直在拉低我的智商!!!——liwei
				String preName = "test";
				for(int j=firstServiceRow; j<sheetRows; j++) {
					Cell cell1 = sheet.getCell(1, j);
					Cell cell2 = sheet.getCell(2, j);
					String content1 = cell1.getContents();
					if (StringUtils.isBlank(content1)){
						continue;
					}
					String content2 = cell2.getContents();
					if(preName.equals(content1)){
						serviceNameSameStr.append("has the same service +"+content1);
						break;
					}else{
						preName = content1;
					}

					if(content1!=null && !content1.trim().equals("")) {
						serviceNameStr.append( content1+ ":" + content2 + ";");
					}
				}
			}
			book.close();
		} catch (Exception e) {
			log.error("执行 getServiceNameFromExcel 时出现HibernateException异常", e);
		}
		if(!"".equals(serviceNameSameStr.toString())){

			return serviceNameSameStr.toString();
		}else{
			return serviceNameStr.toString();
		}
	}
	/**
	 * 验证导入服务中文名
	 * add by liuc
	 * @param
	 * @return
	 */

	private boolean checkChName(String CName){
		boolean flag = true;
		Pattern pp = Pattern.compile("[\u4e00-\u9fa5\\w]+");
		Matcher mm = pp.matcher(CName);
		if(!mm.matches()){
			flag = false;
		}
		return flag;
	}
	//除去标题头，从第firstServiceRow行才能读取服务
	private int getContentFirstRowIndex(Sheet sheet){
		int firstServiceRow = 1;
		if(sheet.getName().equals(IOUtil.sheetAtomTuxedoName)) {
			firstServiceRow = 2;
		}
		if(sheet.getName().equals(IOUtil.sheetComTuxedoName)) {
			firstServiceRow = 2;
		}
		if(sheet.getName().equals(IOUtil.sheetUtypeName)) {
			firstServiceRow = 2;
		}
		if(sheet.getName().equals(IOUtil.sheetComXMLName)) {
			firstServiceRow = 2;
		}
		if(sheet.getName().equals(IOUtil.sheetSoapName)) {
			firstServiceRow = 2;
		}
		if(sheet.getName().equals(IOUtil.sheetAtomTongEASYName)){
			firstServiceRow = 2;
		}
		if(sheet.getName().equals(IOUtil.sheetAtomUtype10Name)){
			firstServiceRow = 2;
		}
		if(sheet.getName().equals(IOUtil.sheetAtomTxdoJSONName)){
			firstServiceRow = 2;
		}
		return firstServiceRow;
	}
	/**
	 * 原子服务utype
	 * */
	private String importAtomUtypeService(Sheet sheet, Cell cell, String srvName, Session session) {
		log.debug("调用 importAtomUtypeService: 导入utype服务"+ srvName);
		String result = "";
		SrvType srvType = new SrvTypeDAO().findById(Long.valueOf(AppSrvConstant.SRVTYPE_SIUTYPEWS), session);
		importServiceModule(sheet, cell, srvName, session, srvType);
		return result;
	}
	/**
	 * 导入原子soap服务
	 * */
	private String importSoapService(Sheet sheet, Cell cell, String srvName, Session session) {
		log.debug("调用 importSoapService, 导入服务"+ srvName);
		String result = "";
		SrvType srvType = new SrvTypeDAO().findById(Long.valueOf(AppSrvConstant.SRVTYPE_SOAP), session);
		importServiceModule(sheet, cell, srvName, session, srvType);
		return result;
	}
	/**
	 * 导入自定义soap服务
	 * */
	private String importComSoapService(Sheet sheet, Cell cell, String srvName, Session session) {
		log.debug("调用 importComSoapService, 导入服务"+ srvName);
		String result = "";
		SrvType srvType = new SrvTypeDAO().findById(Long.valueOf(AppSrvConstant.SRVTYPE_SOAP_COM), session);
		importServiceModule(sheet, cell, srvName, session, srvType);
		return result;
	}

	/**
	 * 复合服务XML
	 * */
	private String importComXMLService(Sheet sheet, Cell cell, String srvName, Session session) {
		log.debug("调用 importComXMLService, 导入服务"+ srvName);
		String result = "";
		SrvType srvType = new SrvTypeDAO().findById(Long.valueOf(AppSrvConstant.SRVTYPE_XMLSTR_COM), session);
		importServiceModule(sheet, cell, srvName, session, srvType);
		return result;
	}
	/**
	 * tongeasy原子服务
	 * */
	private String importAtomTongEASYService(Sheet sheet, Cell cell, String srvName, Session session) {
		log.debug("调用 importAtomTongEASYService, 导入服务"+ srvName);
		String result = "";
		SrvType srvType = new SrvTypeDAO().findById(Long.valueOf(AppSrvConstant.SRVTYPE_TONGEASY), session);
		importServiceModule(sheet, cell, srvName, session, srvType);
		return result;
	}
	/**
	 * 原子服务utype0
	 * */

	private String importAtomUtype10(Sheet sheet, Cell cell, String srvName, Session session) {
		log.debug("调用 importAtomUtype10Service, 导入服务"+ srvName);
		String result = "";
		SrvType srvType = new SrvTypeDAO().findById(Long.valueOf(AppSrvConstant.SRVTYPE_UTYPE0), session);
		importServiceModule(sheet, cell, srvName, session, srvType);
		return result;
	}

	/**
	 * 原子服务_txdoJSON
	 * */
	private String importAtomTxdoJSONService(Sheet sheet, Cell cell, String srvName, Session session) {
		log.debug("调用importAtomTxdoJSONService: 导入txdoJSON服务"+ srvName);
		String result = "";
		SrvType srvType = new SrvTypeDAO().findById(Long.valueOf(AppSrvConstant.SRVTYPE_TXDO_JSON), session);
		importServiceModule(sheet, cell, srvName, session, srvType);
		return result;
	}
	/**
	 * 复合服务_String
	 * */
	private String importComStringService(Sheet sheet, Cell cell, String srvName, Session session) {
		log.debug("调用importComStringService: 导入复合服务_String"+ srvName);
		String result = "";
		SrvType srvType = new SrvTypeDAO().findById(Long.valueOf(AppSrvConstant.SRVTYPE_SOAP_STRING_COM), session);
		importServiceModule(sheet, cell, srvName, session, srvType);
		return result;
	}
	/**
	 * 复合服务_Rest_String
	 */
	private String importComRestStringService(Sheet sheet, Cell cell, String srvName, Session session) {
		log.debug("调用importComRestStringService: 导入复合服务_Rest_String"+ srvName);
		String result = "";
		SrvType srvType = new SrvTypeDAO().findById(Long.valueOf(AppSrvConstant.SRVTYPE_REST_STRING_COM), session);
		importServiceModule(sheet, cell, srvName, session, srvType);
		return result;
	}
	/**
	 * 原子服务_Hsf
	 */
	private String importAtomHsfService(Sheet sheet, Cell cell, String srvName, Session session) {
		log.info("调用importAtomHsfService: 导入原子服务_Hsf"+ srvName);
		String result = "";
		SrvType srvType = new SrvTypeDAO().findById(Long.valueOf(AppSrvConstant.SRVTYPE_HSF), session);
		log.info("SrvType -->findById");
		result = importServiceModule(sheet, cell, srvName, session, srvType);
		log.info("调用importAtomHsfService: 导入原子服务_Hsf完成--> " + result);
		return result;
	}
	private Workbook getWorkbookByFileName(String fileName){
		Workbook ww = null;
		InputStream is = null;
		if(fileName.endsWith(".xls")){

		}else if(fileName.endsWith("xlsx")){

		}
		return ww;
	}
}

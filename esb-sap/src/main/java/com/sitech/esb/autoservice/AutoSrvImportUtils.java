package com.sitech.esb.autoservice;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.sitech.esb.sap.SapException;
import jxl.Workbook;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.sitech.esb.manager.PlatformConfigInfo;
import com.sitech.esb.hb.*;
import com.sitech.esb.util.AppSrvConstant;

/**
 *  自动服务同步
 * @author liuch
 * @author liwei
 */
public class AutoSrvImportUtils {

    /**
     * 日志记录器，封装了下log4j
     */
    private static final Logger log = Logger.getLogger("autojob");

    /**
     * 服务同步的标志，当该值不为0时，表示还有服务同步的操作
     */
    private static AtomicInteger loadsession = new AtomicInteger();

    /**
     * 单池的服务同步操作
     * @param workbook 要发布的服务
     * @param poolKey 池的key
     * @param hbXml 该池的数据源hibernate配置文件
     * @param poolAddress 该池其中一个esb实例的地址
     * @return
     * @throws Exception
     */
    public  String autoSrvImp(Workbook workbook,String poolKey,String hbXml,String poolAddress) throws Exception{
        String srvResult = "success";
        Session ss  = null;
        try {
            AutoImportService importService = new AutoImportService();
            //同步服务，设置重置session标志
            loadsession.incrementAndGet();

            //考虑所有xls导入完成在发布应用 目前是操作一个xls 就把发布服务整个流程走一遍
            ss  = AutoHibernateSessionFactory.load(hbXml);
            PlatformConfigInfo.load(ss);
            String srvnametmp = importService.getServiceNameFromExcel(workbook);
            //覆盖服务
            log.info(poolKey+" begin import "+srvnametmp);

            //默认覆盖服务
            srvResult = importService.importService(workbook,srvnametmp, "1", ss);
            log.info(poolKey+"service import completed, return ->"+srvResult);
            log.info(poolKey+"service begin sleep 10000 ms");
            Thread.sleep(10000);// 保留原来的sleep，因为没得esb自动检测应用服务发布的时间点是10秒
            log.info("service sleep complete ,"+poolKey+" begin app bind service");
            boolean isNewApp = autoApp(ss,srvnametmp);
            log.info(poolKey+" app bind service complete,begin sleep 10000");
            Thread.sleep(10000); //保留原来的sleep，因为没得esb自动检测应用服务发布的时间点是10秒

            if(isNewApp){
                //每天只生成一个应用,对应只加载一次缓存,反正加载缓存对服务调用产生影响
                log.info(poolKey+" has a new app, so refresh cache");
                AutoReFreshTask  refrtask = new AutoReFreshTask(poolAddress);
                Thread trThread = new Thread(refrtask,poolKey);
                trThread.start();
            }
            return srvResult;
        } catch (Exception e) {
            log.error("sap has a exception",e);
            throw new SapException(e.getMessage());
        }finally{
            AutoHibernateSessionFactory.closeSession(ss);
            log.info("sap is complete,close session ");
            //通过该标记为判断如果没有其他线程在进行服务发布了，就把默认的数据源池信息重置
            if(loadsession.decrementAndGet() < 1){
                Session initsession  = AutoHibernateSessionFactory.load(AutoHibernateSessionFactory.CONFIG_FILE_LOCATION);
                PlatformConfigInfo.load(initsession);
                log.info("reset session to init session(current esb session)");
            }
        }

    }

    /**
     * 将服务挂到应用中去
     * 应用一天就一个，如果当天还没发布应用，则会先创建一个应用
     * @param ss
     * @param srvnametmp
     * @return 是否新创建了应用
     * @throws Exception 应用发布失败
     */
    public boolean autoApp(Session ss,String srvnametmp) throws Exception{
        //是不是新建立的应用
        boolean isNewApp = false;

        String appName = getAppName();
        String appChName = appName;
        log.info("应用名:"+appName);
        int flag = checkAAppname(ss, appName);
        Transaction tx = ss.beginTransaction();
        try {
            if(flag>0){
                log.info("当前应用已存在，不新建应用:"+appName+":"+appChName);
            }else{
                log.info("新建当天的应用:"+appName+":"+appChName);
                //针对同一个应用当天只刷新一次缓存,每天第一次新建应用设置refreshall为true 0520
                isNewApp = true;
                String appDescribe = "";
                AppInfo app = new AppInfo(appName, appDescribe);
                Map appfunCategMap = PlatformConfigInfo.appfunCategMap;
                Map  appStatusMap = PlatformConfigInfo.appStatusMap;
                AppStatus appStatus = (AppStatus)appStatusMap.get(String.valueOf(AppSrvConstant.APP_STOPED));
                app.setAppStatus(appStatus);
                app.setAppFunctionCateg((AppFunCateg)appfunCategMap.get(String.valueOf(3)));//默认协同类
                app.setFlowThreshold(Long.valueOf("-1"));
                //发布环境不需要IP验证0，生产环境需要1
                app.setNeedIpValid(new Long("1"));
                app.setNeedUserPwd(new Long("0"));
                app.setNeedWseeSign(new Long("0"));
                app.setNeedOprSrvRight(new Long("0"));
                app.setEndUsrFlowThreshold(new Long("-1"));
                app.setAppChname(appChName);
                ss.save(app);
            }
            log.info("app published complete-->"+appName+",begin bind service-->"+srvnametmp);
            addAppSrv(ss, srvnametmp, appName);
            log.info("app bind service complete");
            if(flag == 0){ //新应用要绑定接入端
                log.info("服务绑定完成-->开始绑定接入端");
                addAllAppClient(ss,appName);
                log.info("接入端绑定完成-->启动应用");
                startApp(ss,appName);
            }
            tx.commit();
            return isNewApp;
        } catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            log.error("应用接入服务失败",e);
            throw new SapException(e);
        }

    }
    /**
     * 获取应用名
     * @return
     */
    private static String getAppName(){
        Date date = new Date();
        SimpleDateFormat sft = new SimpleDateFormat("yyyyMMdd");
        String appname = "AUTO_SYN_" + sft.format(date);
        return appname;
    }

    /**
     * 检查应用名是否存在 true?不新建应用:新建应用
     */
    private int checkAAppname(Session ss,String appName){
        AppInfoDAO appInfoDAO = new AppInfoDAO();
        int flag = appInfoDAO.findCountByProperty("appName", appName, ss);
        return flag;
    }
    /**
     * 服务绑定应用
     * @param ss
     * @param srvnames
     */
    private void addAppSrv(Session ss,String srvnames,String appName){
        SrvInfoDAO srvInfoDAO = new SrvInfoDAO();
        AppInfoDAO appInfoDAO = new AppInfoDAO();
        AppInfo app = appInfoDAO.findByInstanceAppName(appName, ss);
        long appId = app.getAppId();
        log.info("获取服务列表");
        List<SrvInfo> srvInfos = new ArrayList<SrvInfo>();
        //srvname:chname;
        String[] srvnameArray = srvnames.split(";");
        String srvnamestmp = "";
        for(int j = 0;j< srvnameArray.length;j++){
            srvnamestmp +=srvnameArray[j].split(":")[0];
            if(j != srvnameArray.length - 1){
                srvnamestmp += ",";
            }
        }
        srvInfos = srvInfoDAO.getSrvList(srvnamestmp,ss);
        if(srvInfos!=null && srvInfos.size() > 0){
            log.info(app.getAppName()+" bind those service :"+srvnamestmp+",size is "+srvInfos.size());
            for (SrvInfo service : srvInfos) {
                log.info("begin execute "+service.getSrvName());
                execute(service.getSrvId(),appId,ss);
            }
        }
    }
    private void execute(long srvIdTemp,long appIdTemp,Session s) {
        AppInfoDAO appInfoDAO = new AppInfoDAO();
        SrvInfoDAO srvInfoDAO = new SrvInfoDAO();
        //应用状态
        Map appStatusMap = PlatformConfigInfo.appStatusMap;
        Map srvStatusMap = PlatformConfigInfo.srvStatusMap;
        SrvInfo srvInfo = srvInfoDAO.findById(srvIdTemp, s);
        AppInfo appInfo = appInfoDAO.findById(appIdTemp, s);
        int max = appInfo.getSrvInfos().size();
        if(max>0) {
            max = appInfo.getSrvInfos().size() + 1;
        } else if(max==0) {
            max = 1;
        }
        srvInfo.setAppInfo(appInfo);
        srvInfo.setSrvOrder(new Long(max));
        String srvName = srvInfo.getSrvName();
        Long appStatusId = appInfo.getAppStatus().getAppStatusId();
        log.info("execute srvName is "+srvName+" and appStatusId is "+appStatusId);
        if(appStatusId.equals(new Long(AppSrvConstant.APP_STARTED))){
            srvInfo.setSrvStatus((SrvStatus)srvStatusMap.get(String.valueOf(AppSrvConstant.SRV_STARTING)));
            log.info(srvName+" execute SRV_STARTING");
        }
        if(appStatusId.equals(new Long(AppSrvConstant.APP_STARTED)) ||
                appStatusId.equals(new Long(AppSrvConstant.APP_START_ERROR))) {
            appInfo.setAppStatus((AppStatus)appStatusMap.get(String.valueOf(AppSrvConstant.APP_STARTING)));
            log.info(srvName+" execute APP_STARTING");

        }
        if(appStatusId.equals(new Long(AppSrvConstant.APP_STOPING)) ||
                appStatusId.equals(new Long(AppSrvConstant.APP_STOP_ERROR))) {
            appInfo.setAppStatus((AppStatus)appStatusMap.get(String.valueOf(AppSrvConstant.APP_STOPING)));
            log.info(srvName+" execute APP_STOPING");
        }

    }
    /**
     * 添加所有接入端
     * @param ss
     * @param appName
     */
    private void addAllAppClient(Session ss,String appName){
        Set clientApps = null;
        AppInfo app = null;
        AppInfoDAO appDao = new AppInfoDAO();
        app = appDao.findByInstanceAppName(appName, ss);
        clientApps = app.getClientApps();
        ClientInfoDAO clientInfoDAO = new ClientInfoDAO();
        ClientAppRightDAO clientAppRightDAO = new ClientAppRightDAO();
        List clientInfoList = clientInfoDAO.findAllValid(ss);
        Iterator it3 = clientApps.iterator();
        while(it3.hasNext()) {
            ClientAppRight clientAppRight = (ClientAppRight)it3.next();
            ClientInfo clientInfo = clientAppRight.getClientInfo();
            clientInfoList.remove(clientInfo);
        }
        Long flowThreshold = new Long("-1");
        Long validFlag = new Long("1");
        Long endUsrDayFlowThrsd = new Long("-1");
        Long isEndUsrLoginValidate = new Long("0");
        Long isEndUsrSmsValidate = new Long("0");
        Iterator it2 = clientInfoList.iterator();
        while (it2.hasNext()) {
            ClientInfo clientInfo = (ClientInfo) it2.next();
            ClientAppRight clientAppRight = new ClientAppRight(clientInfo, app, flowThreshold,  validFlag);
            clientAppRight.setEndUsrDayFlowThrsd(endUsrDayFlowThrsd);
            clientAppRight.setIsEndUsrLoginValidate(isEndUsrLoginValidate);
            clientAppRight.setIsEndUsrSmsValidate(isEndUsrSmsValidate);
            //clientAppRightDAO.save(clientAppRight, ss);
            ss.save(clientAppRight);
        }
    }
    /**
     * 启动应用
     * @param ss
     * @param appName
     */
    private void startApp(Session ss,String appName){
        AppInfoDAO appInfoDAO = new AppInfoDAO();
        AppInfo appInfo = null;
        Map appStatusMap = PlatformConfigInfo.appStatusMap;
        AppStatus appStatus = null;
        appInfo = appInfoDAO.findByInstanceAppName(appName, ss);
        appStatus = (AppStatus)appStatusMap.get(String.valueOf(AppSrvConstant.APP_STARTING));
        appInfo.setAppStatus(appStatus);
    }


}
package com.sitech.esb.autoservice;

import java.text.SimpleDateFormat;
import java.util.*;

import jxl.Workbook;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import com.sitech.esb.manager.PlatformConfigInfo;
import com.sitech.esb.hb.*;
import com.sitech.esb.util.AppSrvConstant;

/**
 *  自动服务同步
 * @author liuch
 * @author liwei
 */
public class LiucServiceImport {

    /**
     * 日志记录器，封装了下log4j
     */
    private static final Logger log = Logger.getLogger("autojob");

    private Session session;

    public LiucServiceImport(Session session) {
        this.session = session;
    }

    private AppInfoDAO appInfoDAO = new AppInfoDAO();

    private SrvInfoDAO srvInfoDAO = new SrvInfoDAO();

    public void liuc(Workbook workbook,String poolAddress) throws Exception{
        //发布服务
        List<String> list = publishService(workbook);
        Thread.sleep(10000);// 保留原来的sleep，因为没得esb自动检测应用服务发布的时间点是10秒
        //发布应用
        String appName = publishApp();
        Thread.sleep(10000); //保留原来的sleep，因为没得esb自动检测应用服务发布的时间点是10秒
        //应用关联服务
        appWithServices(appName,list);
        //应用挂接入端
        appWithClient(appName);
        //启动应用
        startApp(appName);
        //刷新缓存
        refreshCache(poolAddress);
    }

    /**
     * 服务发布，只是发布的空客服务，还无法使用，需要关联应用才能使用
     * @param workbook
     * @return 已发布空壳服务名列表
     */
    public List<String> publishService(Workbook workbook){
        List<String> serviceList = new ArrayList<String>();
        AutoImportService importService = new AutoImportService();
        String serviceNameFromExcel = importService.getServiceNameFromExcel(workbook);
        importService.importService(workbook,serviceNameFromExcel, "1", session);
        //srvname:chname;
        String[] srvnameArray = serviceNameFromExcel.split(";");
        String srvnamestmp = "";
        for(int j = 0;j< srvnameArray.length;j++){
            srvnamestmp +=srvnameArray[j].split(":")[0];
            if(j != srvnameArray.length - 1){
                serviceList.add(srvnamestmp);
            }
        }
        return serviceList;
    }

    public void refreshCache(String esbIpPort){
        AutoRefreshTask  refreshTask = new AutoRefreshTask(esbIpPort);
        new Thread(refreshTask).start();
    }

    public String publishApp(String appName){

        int flag = appInfoDAO.findCountByProperty("appName", appName, session);
        if (flag==0){
            createNewApp(appName);
        }

        AppInfo app = appInfoDAO.findByInstanceAppName(appName, session);
        return app.getAppName();
    }

    public String publishApp(){
        String defaultAppName ;
        Date date = new Date();
        SimpleDateFormat sft = new SimpleDateFormat("yyyyMMdd");
        defaultAppName = "AUTO_SYN_" + sft.format(date);
        return publishApp(defaultAppName);
    }


    //应用关联接入端
    public void appWithClient(String appName){
        AppInfo app = appInfoDAO.findByInstanceAppName(appName, session);
        Set clientApps = app.getClientApps();
        ClientInfoDAO clientInfoDAO = new ClientInfoDAO();
        List clientInfoList = clientInfoDAO.findAllValid(session);
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
            session.save(clientAppRight);
        }
    }

    //启动应用
    public void startApp(String appName){
        Map appStatusMap = PlatformConfigInfo.appStatusMap;
        AppInfo appInfo = appInfoDAO.findByInstanceAppName(appName, session);
        AppStatus appStatus = (AppStatus)appStatusMap.get(String.valueOf(AppSrvConstant.APP_STARTING));
        appInfo.setAppStatus(appStatus);
    }

    public void createNewApp(String appName){
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
        app.setAppChname(appName);
        session.save(app);
    }


    //应用关联服务
    public void appWithServices(String appName,List<String> srvList){
        for (String srvName : srvList) {
            appWithService(appName,srvName);
        }
    }

    private void appWithService(String appName,String srvName) {

        //应用状态
        Map appStatusMap = PlatformConfigInfo.appStatusMap;
        Map srvStatusMap = PlatformConfigInfo.srvStatusMap;

        SrvInfo srvInfo = srvInfoDAO.findInstanceBySrvName(srvName,session);
        AppInfo appInfo = appInfoDAO.findByInstanceAppName(appName, session);
        int max = appInfo.getSrvInfos().size();
        if(max>0) {
            max = appInfo.getSrvInfos().size() + 1;
        } else if(max==0) {
            max = 1;
        }
        srvInfo.setAppInfo(appInfo);
        srvInfo.setSrvOrder(new Long(max));
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

}
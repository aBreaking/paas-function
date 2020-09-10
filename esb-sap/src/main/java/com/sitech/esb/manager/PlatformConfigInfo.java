package com.sitech.esb.manager;

import com.sitech.esb.hb.*;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.servlet.http.HttpServlet;
import java.util.*;

public class PlatformConfigInfo extends HttpServlet {
    
	public static Map appfunCategMap = new HashMap();
	
	public static Map appStatusMap =  new HashMap();
	
	public static Map srvStatusMap =  new HashMap();
	
	public static Map srvTypeMap =  new HashMap();
	
	public static Map siTxdoRouteTypeMap =  new HashMap();

	public static Map srvTimeoutCfgMap =  new HashMap();
	
	public static Map txdoSystemMap = new HashMap();

	public static Map soaphttpSystemMap = new TreeMap();

    public static void load(Session s){
        loadAppFunCateg(s);
        loadAppStatus(s);
        loadSrvStatus(s);
        loadSrvType(s);
        loadSiTxdoRouteType(s);
        loadSrvTimeoutCfg(s);
        loadTxdoSystem(s);
        loadSoaphttpSystem(s);
    }

    public static void loadAppFunCateg(Session s)  {
        if(appfunCategMap.size()>0) {
            appfunCategMap.clear();
        }
        String queryString  = "from AppFunCateg as model order by model.appFunctionName";
        Query queryObject = s.createQuery(queryString);
        List list = queryObject.list();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            AppFunCateg obj = (AppFunCateg)it.next();
            appfunCategMap.put(obj.getAppFuncatId().toString(), obj);
        }
    }

    public static void loadAppStatus(Session s)  {
        if(appStatusMap.size()>0) {
            appStatusMap.clear();
        }
        String queryString  = "from AppStatus";
        Query queryObject = s.createQuery(queryString);
        List list = queryObject.list();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            AppStatus obj = (AppStatus)it.next();
            appStatusMap.put(obj.getAppStatusId().toString(), obj);
        }
    }
    
	public static void loadSrvTimeoutCfg(Session s)  {
		
		if(srvTimeoutCfgMap.size()>0) {
			srvTimeoutCfgMap.clear();
		}
		String queryString  = "from SrvTimeoutCfg as model order by model.cfgId";
		Query queryObject = s.createQuery(queryString);
		List srvTimeoutCfgList = queryObject.list();
		Iterator it = srvTimeoutCfgList.iterator();
		while(it.hasNext()) {
			SrvTimeoutCfg obj = (SrvTimeoutCfg)it.next();
			srvTimeoutCfgMap.put(obj.getCfgId().toString(), obj);
		}
	}

    public static void loadSoaphttpSystem(Session s)  {
        if(soaphttpSystemMap.size()>0) {
            soaphttpSystemMap.clear();
        }
        String queryString  = "from SoaphttpSystem model order by model.systemName";
        Query queryObject = s.createQuery(queryString);
        List soaphttpSystemList = queryObject.list();
        Iterator it = soaphttpSystemList.iterator();
        while(it.hasNext()) {
            SoaphttpSystem obj = (SoaphttpSystem)it.next();
            soaphttpSystemMap.put(obj.getSoaphttpSystemId().toString(), obj);
        }
    }

	public static Object getById(String id, Map map) {
		Set keySet = map.keySet();
		Iterator it = keySet.iterator();
		while(it.hasNext()) {
			String key = (String)it.next();
			if(key.equals(id)) {
				return map.get(key);
			}
		}
		return null;
	}

    public static void loadSrvStatus(Session s)  {
        
        if(srvStatusMap.size()>0) {
            srvStatusMap.clear();
        }
        String queryString  = "from SrvStatus";
        Query queryObject = s.createQuery(queryString);
        List list = queryObject.list();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            SrvStatus obj = (SrvStatus)it.next();
            srvStatusMap.put(obj.getSrvStatusId().toString(), obj);
        }
    }

    public static void loadSrvType(Session s)  {
        
        if(srvTypeMap.size()>0) {
            srvTypeMap.clear();
        }
        String queryString  = "from SrvType as model where model.validFlag=1 order by model.srvTypeName";
        Query queryObject = s.createQuery(queryString);
        List srvTypeList = queryObject.list();
        Iterator it = srvTypeList.iterator();
        while(it.hasNext()) {
            SrvType obj = (SrvType)it.next();
            srvTypeMap.put(obj.getSrvType().toString(), obj);
        }
    }


    public static void loadSiTxdoRouteType(Session s)  {
        
        if(siTxdoRouteTypeMap.size()>0) {
            siTxdoRouteTypeMap.clear();
        }
        String queryString  = "from SiTxdoRouteType as model where model.validFlag=1 order by model.routeType";
        Query queryObject = s.createQuery(queryString);
        List siTxdoRouteTypeList = queryObject.list();
        Iterator it = siTxdoRouteTypeList.iterator();
        while(it.hasNext()) {
            SiTxdoRouteType obj = (SiTxdoRouteType)it.next();
            siTxdoRouteTypeMap.put(obj.getRouteType().toString(), obj);
        }
    }

    public static void loadTxdoSystem(Session s)  {
        //
        if(txdoSystemMap.size()>0) {
            txdoSystemMap.clear();
        }
        String queryString  = "from TxdoSystem model where model.validFlag=1 order by model.txdoSystemName";
        Query queryObject = s.createQuery(queryString);
        List txdoSystemList = queryObject.list();
        Iterator it = txdoSystemList.iterator();
        while(it.hasNext()) {
            TxdoSystem obj = (TxdoSystem)it.next();
            txdoSystemMap.put(obj.getTxdoSystemId().toString(), obj);
        }
    }


}
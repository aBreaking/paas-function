package com.sitech.esb.hb;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.sitech.esb.util.AppSrvConstant;

/**
 * Data access object (DAO) for domain model class ClientInfo.
 * @see com.sitech.esb.hb.ClientInfo
 * @author MyEclipse - Hibernate Tools
 */
public class ClientInfoDAO extends BaseHibernateDAO {

    private static final Logger log = Logger.getLogger(ClientInfoDAO.class);

	//property constants
	public static final String CLIENT_LOGIN = "clientLogin";
	public static final String USER_PWD = "userPwd";
	public static final String CLIENT_NAME = "clientName";
	public static final String VALID_FLAG = "validFlag";
	public static final String FLOW_THRESHOLD = "flowThreshold";
	public static final String PRIORITY_VALUE = "priorityValue";
	public static final String CLIENT_DESCRIBE = "clientDescribe";
	
	public static final String ADMIN = "admin";
    
    public void save(ClientInfo transientInstance, Session session) {
        log.debug("saving ClientInfo instance");
        try {
            session.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(ClientInfo persistentInstance, Session session) {
        log.debug("deleting ClientInfo instance");
        try {
            session.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public ClientInfo findById( java.lang.Long id, Session session) {
        log.debug("getting ClientInfo instance with id: " + id);
        try {
            ClientInfo instance = (ClientInfo) session
                    .get("com.sitech.esb.hb.ClientInfo", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(ClientInfo instance, Session session) {
        log.debug("finding ClientInfo instance by example");
        try {
            List results = session
                    .createCriteria("com.sitech.esb.hb.ClientInfo")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value, Session session) {
      log.debug("finding ClientInfo instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from ClientInfo as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = session.createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}
    public List findByPropertyno(String propertyName, Object value, Session session) {
        
        try {
           String queryString = " from  ClientInfo as model where model.validFlag=1";
           Query queryObject = session.createQuery(queryString);
  
  		 return queryObject.list();
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}
    public int findCountByProperty(String propertyName, Object value, Session session) {
        log.debug("finding ClientInfo instance count with property: " + propertyName
              + ", value: " + value);
        try {
           String queryString = "select count(model.clientId) from ClientInfo as model where model." 
           						+ propertyName + "= ?";
           Query queryObject = session.createQuery(queryString);
  		   queryObject.setParameter(0, value);
  		   int count = (Integer.valueOf(queryObject.uniqueResult().toString())).intValue();
  		   return count;
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}
    
	public ClientInfo findByUserName(Object userName, Session session) {
		List list = findByProperty(CLIENT_LOGIN, userName, session);
		ClientInfo u = null;
		if(list!=null && !list.isEmpty()) {
			u = (ClientInfo)list.get(0);
		}
		return u;
	}

	public List findByClientLogin(Object clientLogin, Session session) {
		return findByProperty(CLIENT_LOGIN, clientLogin, session);
	}
	
	public List findByUserPwd(Object userPwd, Session session) {
		return findByProperty(USER_PWD, userPwd, session);
	}
	
	public List findByClientName(Object clientName, Session session) {
		return findByProperty(CLIENT_NAME, clientName, session);
	}
	
	public List findByValidFlag(Object validFlag, Session session) {
		return findByProperty(VALID_FLAG, validFlag, session);
	}
	
	public List findByFlowThreshold(Object flowThreshold, Session session) {
		return findByProperty(FLOW_THRESHOLD, flowThreshold, session);
	}
	
	public List findByPriorityValue(Object priorityValue, Session session) {
		return findByProperty(PRIORITY_VALUE, priorityValue, session);
	}
	
	public List findByClientDescribe(Object clientDescribe, Session session) {
		return findByProperty(CLIENT_DESCRIBE, clientDescribe, session);
	}
	
    public ClientInfo merge(ClientInfo detachedInstance, Session session) {
        log.debug("merging ClientInfo instance");
        try {
            ClientInfo result = (ClientInfo) session
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ClientInfo instance, Session session) {
        log.debug("attaching dirty ClientInfo instance");
        try {
            session.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(ClientInfo instance, Session session) {
        log.debug("attaching clean ClientInfo instance");
        try {
            session.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List findAll(Session session) {
    	log.debug("finding All ClientInfo instance");
          try {
             String queryString = "from ClientInfo as model order by model.clientName";
             Query queryObject = session.createQuery(queryString);
    		 return queryObject.list();
          } catch (RuntimeException re) {
             log.error("find by property name failed", re);
             throw re;
          }
    }
    
    public List findAllOfTree(Session session) {
    	log.debug("finding All ClientInfo instance");
          try {
             String queryString = "select new ClientInfo(model.clientId, model.clientName) from ClientInfo as model order by model.clientName";
             Query queryObject = session.createQuery(queryString);
    		 return queryObject.list();
          } catch (RuntimeException re) {
             log.error("find by property name failed", re);
             throw re;
          }
    }
    
    public List findAllValid(Session session) {
    	log.debug("finding All ClientInfo instance");
          try {
             String queryString = "from ClientInfo as model where model.validFlag=1 order by model.clientName";
             Query queryObject = session.createQuery(queryString);
    		 return queryObject.list();
          } catch (RuntimeException re) {
             log.error("find by property name failed", re);
             throw re;
          }
    }
    
    public List findLikeByProperty(String propertyName, Object value, Session session) {
        log.debug("finding ClientInfo instance with property: " + propertyName
              + ", value: " + value);
        try {
        
         String queryString = "from ClientInfo as model where upper(model." 
           						+ propertyName + ") like :name order by model.clientLogin";
         Query queryObject = session.createQuery(queryString);
  		 queryObject.setParameter("name", "%" + ((String)value).toUpperCase() + "%");
  		 /*
         String queryString = "from ClientInfo as model where model." 
					+ propertyName + " like %"+ value + "%";
         Query queryObject = session.createQuery(queryString);	
          */
  		 return queryObject.list();
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}
    
    public List getApps(ClientInfo clientInfo, Session session) {
	      try {
	         String queryString = "select clientApps.appInfo from ClientInfo as model join model.clientApps clientApps " +
	         		"where clientApps.clientInfo=:clientInfo and clientApps.appInfo.appStatus.appStatusId<>:appStatusId and clientApps.validFlag=1 order by clientApps.appInfo.appId";
	         Query queryObject = session.createQuery(queryString);
	         queryObject.setParameter("clientInfo", clientInfo);
	         queryObject.setParameter("appStatusId", new Long(AppSrvConstant.APP_REMOVING));
			 return queryObject.list();
	      } catch (RuntimeException re) {
	         log.error("find by property name failed", re);
	         throw re;
	      } 
    }

    //viewTheClient.jsp未用
    public List getAppsOfClient(ClientInfo clientInfo, Session session) {
	      try {
	         String queryString = "select new AppInfo(clientApps.appInfo.appId, clientApps.appInfo.appName, clientApps.appInfo.appStatus, clientApps.appInfo.appFunctionCateg) " +
	         					  " from ClientInfo as model join model.clientApps clientApps " +
	         					  " where clientApps.clientInfo=:clientInfo and clientApps.appInfo.appStatus.appStatusId<>:appStatusId and clientApps.validFlag=1 order by clientApps.appInfo.appId";
	         Query queryObject = session.createQuery(queryString);
	         queryObject.setParameter("clientInfo", clientInfo);
	         queryObject.setParameter("appStatusId", new Long(AppSrvConstant.APP_REMOVING));
			 return queryObject.list();
	      } catch (RuntimeException re) {
	         log.error("find by property name failed", re);
	         throw re;
	      } 
    }
    
    public List getAppsOfTree(ClientInfo clientInfo, Session session) {
	      try {
	         String queryString = "select new AppInfo(clientApps.appInfo.appId, clientApps.appInfo.appName) from ClientInfo as model join model.clientApps clientApps " +
	         		"where clientApps.clientInfo=:clientInfo and clientApps.appInfo.appStatus.appStatusId<>:appStatusId and clientApps.validFlag=1 order by clientApps.appInfo.appId";
	         Query queryObject = session.createQuery(queryString);
	         queryObject.setParameter("clientInfo", clientInfo);
	         queryObject.setParameter("appStatusId", new Long(AppSrvConstant.APP_REMOVING));
			 return queryObject.list();
	      } catch (RuntimeException re) {
	         log.error("find by property name failed", re);
	         throw re;
	      } 
  }
    
    public List getApps1(ClientInfo clientInfo, Session session) {
	      try {
	         String queryString = "select clientApps from ClientInfo as model join model.clientApps clientApps " +
	         		"where clientApps.clientInfo=:clientInfo and clientApps.appInfo.appStatus.appStatusId<>:appStatusId order by clientApps.appInfo.appId";
	         Query queryObject = session.createQuery(queryString);
	         queryObject.setParameter("clientInfo", clientInfo);
	         queryObject.setParameter("appStatusId", new Long(AppSrvConstant.APP_REMOVING));
			 return queryObject.list();
	      } catch (RuntimeException re) {
	         log.error("find by property name failed", re);
	         throw re;
	      } 
    }
}
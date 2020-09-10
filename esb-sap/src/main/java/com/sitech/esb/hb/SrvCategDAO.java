package com.sitech.esb.hb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.sitech.esb.util.AppSrvConstant;

/**
 * Data access object (DAO) for domain model class SrvCateg.
 * @see com.sitech.esb.hb.SrvCateg
 * @author MyEclipse - Hibernate Tools
 */
public class SrvCategDAO extends BaseHibernateDAO {

    private static final Logger log = Logger.getLogger(SrvCategDAO.class);

	//property constants
	public static final String SRV_DIMEN_ID = "srvDimenId";
	public static final String SRV_CATEG_NAME = "srvCategName";

    
    public void save(SrvCateg transientInstance, Session session) {
        log.debug("saving SrvCateg instance");
        try {
        	session.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(SrvCateg persistentInstance, Session session) {
        log.debug("deleting SrvCateg instance");
        try {
        	session.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public SrvCateg findById( java.lang.Long id, Session session) {
        log.debug("getting SrvCateg instance with id: " + id);
        try {
            SrvCateg instance = (SrvCateg) session
                    .get("com.sitech.esb.hb.SrvCateg", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(SrvCateg instance, Session session) {
        log.debug("finding SrvCateg instance by example");
        try {
            List results = session
                    .createCriteria("com.sitech.esb.hb.SrvCateg")
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
      log.debug("finding SrvCateg instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from SrvCateg as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = session.createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}
    
    public List findAll(Session session) {
        try {
           String queryString = "from SrvCateg as model order by model.srvCategName";
           Query queryObject = session.createQuery(queryString);
           return queryObject.list();
        } catch (RuntimeException re) {
           log.error("find all", re);
           throw re;
        }
  	}

	public List findBySrvDimenId(Object srvDimenId, Session session) {
		return findByProperty(SRV_DIMEN_ID, srvDimenId, session);
	}
	
	public List findBysrvCategName(Object srvCategName, Session session) {
		return findByProperty(SRV_CATEG_NAME, srvCategName, session);
	}
	
    public SrvCateg merge(SrvCateg detachedInstance, Session session) {
        log.debug("merging SrvCateg instance");
        try {
            SrvCateg result = (SrvCateg) session
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SrvCateg instance, Session session) {
        log.debug("attaching dirty SrvCateg instance");
        try {
            session.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(SrvCateg instance, Session session) {
        log.debug("attaching clean SrvCateg instance");
        try {
            session.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List getSrvInfosNoDeleted(SrvCateg srvCateg, Session session) {
        log.debug("finding SrvCateg instance with property: ");
        try {
           String queryString = "select srvInfos from SrvCateg as model join model.srvInfos as srvInfos " +
           		"where model=:srvCateg and srvInfos.srvStatus.srvStatusId<>:srvStatusId order by srvInfos.srvName";
           Query queryObject = session.createQuery(queryString);
  		   queryObject.setParameter("srvCateg", srvCateg);
		   queryObject.setParameter("srvStatusId", new Long(AppSrvConstant.SRV_REMOVING));
  		   List list = queryObject.list();
  		   return list;
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}
    
    public List getSrvInfosNoDeletedOfTree(SrvCateg srvCateg, Session session, int srvLanguageType) {
        log.debug("finding SrvCateg instance with property: ");
        try {
           //按何种字段排序
           String orderStr = " order by srvInfos.srvName"; 
           if(srvLanguageType==1) {
        	   orderStr = " order by srvInfos.srvChName";
           }
           String queryString = "select new SrvInfo(srvInfos.srvId, srvInfos.srvName, srvInfos.srvChName, srvInfos.srvType) from SrvCateg as model join model.srvInfos as srvInfos " +
           		"where model=:srvCateg and srvInfos.srvStatus.srvStatusId<>:srvStatusId " + orderStr;
           Query queryObject = session.createQuery(queryString);
  		   queryObject.setParameter("srvCateg", srvCateg);
  		   queryObject.setParameter("srvStatusId", new Long(AppSrvConstant.SRV_REMOVING));
  		   List list = queryObject.list();
  		   return list;
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}
    
    public List findSrvByCategs(List srvCategs, Session session, int srvLanguageType) {
        boolean isExsit = true;
    	  List list = new ArrayList();
    	  List listFirst = new ArrayList();
    	  List resultFirst = new ArrayList();
    	  for(int i=0; i<srvCategs.size(); i++) {
    		//list.add(((AppCateg)AppCategs.get(i)).getApps());
    		  list.add(getSrvInfosNoDeletedOfTree((SrvCateg)srvCategs.get(i), session, srvLanguageType));
    	  }
    	  if(list!=null && !list.isEmpty()) {
    		//listFirst = new ArrayList((Set)list.get(0));
    		listFirst = new ArrayList((List)list.get(0));
    	  }
    	  if(listFirst!=null && !listFirst.isEmpty()) {
    		  for(int i=0; i<listFirst.size(); i++) {
    			 for(int j=1; j<list.size(); j++) {
    		  		  if(!isExsit(((SrvInfo)listFirst.get(i)), (List)list.get(j))) {
    		  			isExsit = false;
    		  			break;
    		  		  }
    			 }
    			 if(isExsit) {
    				 resultFirst.add(listFirst.get(i));
    			 }
    			 if(!isExsit) {
    				 isExsit = true;
    			 }
    		  }
    	  }
    	  return resultFirst;
     }
      
      private boolean isExsit(SrvInfo srvInfo, List list) {
  		try {
  			Iterator it = list.iterator();
  			while(it.hasNext()) {
  				if(srvInfo.equals(it.next())) {
  					return true;
  				}
  			}
  		} catch(Exception e) {
  			log.error("查看服务" + srvInfo.getSrvName() + "是否存在" , e);
  		}
  		return false;
  	}
      
}
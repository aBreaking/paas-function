package com.sitech.esb.hb;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class SrvType.
 * @see com.sitech.esb.hb.SrvType
 * @author MyEclipse - Hibernate Tools
 */
public class SrvTypeDAO extends BaseHibernateDAO {

    private static final Logger log = Logger.getLogger(SrvTypeDAO.class);

	//property constants
	public static final String SRV_TYPE_NAME = "srvTypeName";
	public static final String SRV_TYPE_DESC = "srvTypeDesc";

    
    public void save(SrvType transientInstance, Session session) {
        log.debug("saving SrvType instance");
        try {
            session.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(SrvType persistentInstance, Session session) {
        log.debug("deleting SrvType instance");
        try {
            session.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public SrvType findById( java.lang.Long id, Session session) {
        log.debug("getting SrvType instance with id: " + id);
        try {
            SrvType instance = (SrvType) session
                    .get("com.sitech.esb.hb.SrvType", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(SrvType instance, Session session) {
        log.debug("finding SrvType instance by example");
        try {
            List results = session
                    .createCriteria("com.sitech.esb.hb.SrvType")
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
      log.debug("finding SrvType instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from SrvType as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = session.createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}
    
    public List getAll(Session session) {
		log.debug("finding all SrvType instance");
	      try {
	         String queryString = "from SrvType as model where model.validFlag=1 order by model.srvTypeName desc";
	         Query queryObject = session.createQuery(queryString);
			 return queryObject.list();
	      } catch (RuntimeException re) {
	         log.error("find by property name failed", re);
	         throw re;
	      }
    }

	public List findBySrvTypeName(Object srvTypeName, Session session) {
		return findByProperty(SRV_TYPE_NAME, srvTypeName, session);
	}
	
	public List findBySrvTypeDesc(Object srvTypeDesc, Session session) {
		return findByProperty(SRV_TYPE_DESC, srvTypeDesc, session);
	}
	
    public SrvType merge(SrvType detachedInstance, Session session) {
        log.debug("merging SrvType instance");
        try {
            SrvType result = (SrvType) session
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SrvType instance, Session session) {
        log.debug("attaching dirty SrvType instance");
        try {
            session.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(SrvType instance, Session session) {
        log.debug("attaching clean SrvType instance");
        try {
            session.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
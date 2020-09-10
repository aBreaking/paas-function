package com.sitech.esb.hb;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class SrvCommProtl.
 * @see com.sitech.esb.hb.SrvCommProtl
 * @author MyEclipse - Hibernate Tools
 */
public class SrvCommProtlDAO extends BaseHibernateDAO {

    private static final Logger log = Logger.getLogger(SrvCommProtlDAO.class);

	//property constants
	public static final String SRV_ID = "srvId";
	public static final String COMM_ID = "commId";
	public static final String SRV_ADDR = "srvAddr";
	public static final String SRV_PORT = "srvPort";

    
    public void save(SrvCommProtl transientInstance, Session session) {
        log.debug("saving SrvCommProtl instance");
        try {
            session.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(SrvCommProtl persistentInstance, Session session) {
        log.debug("deleting SrvCommProtl instance");
        try {
            session.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public SrvCommProtl findById( java.lang.Long id, Session session) {
        log.debug("getting SrvCommProtl instance with id: " + id);
        try {
            SrvCommProtl instance = (SrvCommProtl) session
                    .get("com.sitech.esb.hb.SrvCommProtl", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(SrvCommProtl instance, Session session) {
        log.debug("finding SrvCommProtl instance by example");
        try {
            List results = session
                    .createCriteria("com.sitech.esb.hb.SrvCommProtl")
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
      log.debug("finding SrvCommProtl instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from SrvCommProtl as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = session.createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findBySrvId(Object srvId, Session session) {
		return findByProperty(SRV_ID, srvId, session);
	}
	
	public List findByCommId(Object commId, Session session) {
		return findByProperty(COMM_ID, commId, session);
	}
	
	public List findBySrvAddr(Object srvAddr, Session session) {
		return findByProperty(SRV_ADDR, srvAddr, session);
	}
	
	public List findBySrvPort(Object srvPort, Session session) {
		return findByProperty(SRV_PORT, srvPort, session);
	}
	
    public SrvCommProtl merge(SrvCommProtl detachedInstance, Session session) {
        log.debug("merging SrvCommProtl instance");
        try {
            SrvCommProtl result = (SrvCommProtl) session
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SrvCommProtl instance, Session session) {
        log.debug("attaching dirty SrvCommProtl instance");
        try {
            session.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(SrvCommProtl instance, Session session) {
        log.debug("attaching clean SrvCommProtl instance");
        try {
            session.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
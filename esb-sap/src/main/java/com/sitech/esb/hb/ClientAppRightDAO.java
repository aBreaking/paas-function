package com.sitech.esb.hb;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for ClientAppRight entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.sitech.esb.hb.ClientAppRight
  * @author MyEclipse Persistence Tools 
 */

public class ClientAppRightDAO extends BaseHibernateDAO  {
    private static final Logger log = Logger.getLogger(ClientAppRightDAO.class);


    
    public void save(ClientAppRight transientInstance, Session session) {
        log.debug("saving ClientAppRight instance");
        try {
        	session.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(ClientAppRight persistentInstance, Session session) {
        log.debug("deleting ClientAppRight instance");
        try {
            session.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public ClientAppRight findById( java.lang.Long id, Session session) {
        log.debug("getting ClientAppRight instance with id: " + id);
        try {
            ClientAppRight instance = (ClientAppRight) session
                    .get("com.sitech.esb.hb.ClientAppRight", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(ClientAppRight instance, Session session) {
        log.debug("finding ClientAppRight instance by example");
        try {
            List results = session
                    .createCriteria("com.sitech.esb.hb.ClientAppRight")
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
      log.debug("finding ClientAppRight instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from ClientAppRight as model where model." 
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
		log.debug("finding all ClientAppRight instances");
		try {
			String queryString = "from ClientAppRight";
	         Query queryObject = session.createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public ClientAppRight merge(ClientAppRight detachedInstance, Session session) {
        log.debug("merging ClientAppRight instance");
        try {
            ClientAppRight result = (ClientAppRight) session
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ClientAppRight instance, Session session) {
        log.debug("attaching dirty ClientAppRight instance");
        try {
            session.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(ClientAppRight instance, Session session) {
        log.debug("attaching clean ClientAppRight instance");
        try {
            session.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
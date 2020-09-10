package com.sitech.esb.hb;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SrvErrinfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sitech.esb.hb.SrvErrinfo
 * @author MyEclipse Persistence Tools
 */

public class SrvErrinfoDAO extends BaseHibernateDAO {
	private static final Logger log = Logger.getLogger(SrvErrinfoDAO.class);
	// property constants
	public static final String SRV_ID = "srvInfo";
	public static final String SRVERR_CODE = "srverrCode";
	public static final String SRVERR_MSG = "srverrMsg";

	public void save(SrvErrinfo transientInstance, Session session) {
		log.debug("saving SrvErrinfo instance");
		try {
			session.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SrvErrinfo persistentInstance, Session session) {
		log.debug("deleting SrvErrinfo instance");
		try {
			session.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SrvErrinfo findById(java.lang.Long id, Session session) {
		log.debug("getting SrvErrinfo instance with id: " + id);
		try {
			SrvErrinfo instance = (SrvErrinfo) session.get(
					"com.sitech.esb.hb.SrvErrinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SrvErrinfo instance, Session session) {
		log.debug("finding SrvErrinfo instance by example");
		try {
			List results = session.createCriteria(
					"com.sitech.esb.hb.SrvErrinfo").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value, Session session) {
		log.debug("finding SrvErrinfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SrvErrinfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySrvId(SrvInfo srvInfo, Session session) {
		return findByProperty("srvInfo", srvInfo, session);
	}

	public List findBySrverrCode(Object srverrCode, Session session) {
		return findByProperty(SRVERR_CODE, srverrCode, session);
	}

	public List findBySrverrMsg(Object srverrMsg, Session session) {
		return findByProperty(SRVERR_MSG, srverrMsg, session);
	}

	public List findAll(Session session) {
		log.debug("finding all SrvErrinfo instances");
		try {
			String queryString = "from SrvErrinfo";
			Query queryObject = session.createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SrvErrinfo merge(SrvErrinfo detachedInstance, Session session) {
		log.debug("merging SrvErrinfo instance");
		try {
			SrvErrinfo result = (SrvErrinfo) session.merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SrvErrinfo instance, Session session) {
		log.debug("attaching dirty SrvErrinfo instance");
		try {
			session.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SrvErrinfo instance, Session session) {
		log.debug("attaching clean SrvErrinfo instance");
		try {
			session.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}
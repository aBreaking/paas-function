package com.sitech.esb.hb;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.sitech.esb.util.AppSrvConstant;

/**
 * A data access object (DAO) providing persistence and search support for
 * SrvRelation entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sitech.esb.hb.SrvRelation
 * @author MyEclipse Persistence Tools
 */

public class SrvRelationDAO extends BaseHibernateDAO {
	private static final Logger log = Logger.getLogger(SrvRelationDAO.class);
	// property constants
	public static final String SRV_PARENT_ID = "parentSrv";
	public static final String SRV_SUB_ID = "subSrv";
	public static final String SUBSRV_ORDER = "subsrvOrder";

	public void save(SrvRelation transientInstance, Session session) {
		log.debug("saving SrvRelation instance");
		try {
			session.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SrvRelation persistentInstance, Session session) {
		log.debug("deleting SrvRelation instance");
		try {
			session.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SrvRelation findById(java.lang.Long id, Session session) {
		log.debug("getting SrvRelation instance with id: " + id);
		try {
			SrvRelation instance = (SrvRelation) session.get(
					"com.sitech.esb.hb.SrvRelation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SrvRelation instance, Session session) {
		log.debug("finding SrvRelation instance by example");
		try {
			List results = session.createCriteria(
					"com.sitech.esb.hb.SrvRelation").add(
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
		log.debug("finding SrvRelation instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SrvRelation as model where model."
					+ propertyName + "= ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySrvParentId(Object srvParentId, Session session) {
		return findByProperty(SRV_PARENT_ID, srvParentId, session);
	}

	public List findBySrvSubId(Object srvSubId, Session session) {
		return findByProperty(SRV_SUB_ID, srvSubId, session);
	}

	public List findBySubsrvOrder(Object subsrvOrder, Session session) {
		return findByProperty(SUBSRV_ORDER, subsrvOrder, session);
	}

	public List findAll(Session session) {
		log.debug("finding all SrvRelation instances");
		try {
			String queryString = "from SrvRelation as model";
			Query queryObject = session.createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List getParentServices(SrvInfo srvInfo, Session session) {
		log.debug("finding SrvRelation instances");
		try {
			String queryString = "from SrvRelation as r where r.subSrv=:subSrv order by r.subsrvOrder";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter("subSrv", srvInfo);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List getParentServicesNoDeleted(SrvInfo srvInfo, Session session) {
		log.debug("finding SrvRelation instances");
		try {
			String queryString = "from SrvRelation as r where r.subSrv=:subSrv and r.parentSrv.srvStatusId<>:srvStatusId  order by r.subsrvOrder";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter("subSrv", srvInfo);
			queryObject.setParameter("srvStatusId", new Long(AppSrvConstant.SRV_REMOVING));
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List getSubServices(SrvInfo srvInfo, Session session) {
		log.debug("finding all SrvRelation instances");
		try {
			String queryString = "from SrvRelation as r where r.parentSrv=:parentSrv order by r.subsrvOrder";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter("parentSrv", srvInfo);
			List list = queryObject.list();
			return list;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List getSubServicesNoDeleted(SrvInfo srvInfo, Session session) {
		log.debug("finding all SrvRelation instances");
		try {
			String queryString = "from SrvRelation as r where r.parentSrv=:parentSrv and r.subSrv.srvStatusId<>:srvStatusId order by r.subsrvOrder";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter("parentSrv", srvInfo);
			queryObject.setParameter("srvStatusId", new Long(AppSrvConstant.SRV_REMOVING));
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List getSubServicesNoDeletedOfTree(SrvInfo srvInfo, Session session) {
		log.debug("finding all SrvRelation instances");
		try {
			String queryString = "select new SrvInfo(r.subSrv.srvId, r.subSrv.srvName, r.subSrv.srvChName, r.subSrv.srvType) " +
					" from SrvRelation as r where r.parentSrv=:parentSrv order by r.subsrvOrder";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter("parentSrv", srvInfo);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public SrvRelation merge(SrvRelation detachedInstance, Session session) {
		log.debug("merging SrvRelation instance");
		try {
			SrvRelation result = (SrvRelation) session.merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SrvRelation instance, Session session) {
		log.debug("attaching dirty SrvRelation instance");
		try {
			session.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SrvRelation instance, Session session) {
		log.debug("attaching clean SrvRelation instance");
		try {
			session.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public int findCountByProperty(String propertyName, Object value, Session session) {
        log.debug("finding SrvRelation instance count with property: " + propertyName
              + ", value: " + value);
        try {
           String queryString = "select count(model.srvRelId) from SrvRelation as model where model." 
           						+ propertyName + "= ?";
           Query queryObject = session.createQuery(queryString);
  		   queryObject.setParameter(0, value);
  		 return ((Integer)queryObject.uniqueResult()).intValue();
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}
	
	public int findMaxOrderByProperty(String propertyName, Object value, Session session) {
        log.debug("finding SrvRelation instance count with property: " + propertyName
              + ", value: " + value);
        try {
           String queryString = "select max(model.subsrvOrder) from SrvRelation as model where model." 
           						+ propertyName + "= ?";
           Query queryObject = session.createQuery(queryString);
  		   queryObject.setParameter(0, value);
  		 return ((Long)queryObject.uniqueResult()).intValue();
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}
}
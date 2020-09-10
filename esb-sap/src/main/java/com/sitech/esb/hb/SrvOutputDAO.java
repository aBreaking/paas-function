package com.sitech.esb.hb;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SrvOutput entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sitech.esb.hb.SrvOutput
 * @author MyEclipse Persistence Tools
 */

public class SrvOutputDAO extends BaseHibernateDAO {
	private static final Logger log = Logger.getLogger(SrvOutputDAO.class);
	// property constants
	public static final String SRV_ID = "srvInfo";
	public static final String OUTPUT_NAME = "outputName";
	public static final String OUTPUT_TYPE = "outputType";
	public static final String OUTPUT_WIDTH = "outputWidth";
	public static final String OUTPUT_NUM = "outputNum";
	public static final String OUTPUT_DESC = "outputDesc";
	public static final String OUTPUT_VALUE = "outputValue";

	public void save(SrvOutput transientInstance, Session session) {
		log.debug("saving SrvOutput instance");
		try {
			session.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SrvOutput persistentInstance, Session session) {
		log.debug("deleting SrvOutput instance");
		try {
			session.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SrvOutput findById(java.lang.Long id, Session session) {
		log.debug("getting SrvOutput instance with id: " + id);
		try {
			SrvOutput instance = (SrvOutput) session.get(
					"com.sitech.esb.hb.SrvOutput", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SrvOutput instance, Session session) {
		log.debug("finding SrvOutput instance by example");
		try {
			List results = session.createCriteria(
					"com.sitech.esb.hb.SrvOutput")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value, Session session) {
		log.debug("finding SrvOutput instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SrvOutput as model where model."
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
			log.debug("finding SrvOutput instance with property: " + srvInfo
					+ ", value: " + srvInfo);
			try {
				String queryString = "from SrvOutput as model where model.srvInfo= ? order by model.outputNum";
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameter(0, srvInfo);
				return queryObject.list();
			} catch (RuntimeException re) {
				log.error("find by property name failed", re);
				throw re;
			}
		}

	public List findByOutputName(Object outputName, Session session) {
		return findByProperty(OUTPUT_NAME, outputName, session);
	}

	public List findByOutputType(Object outputType, Session session) {
		return findByProperty(OUTPUT_TYPE, outputType, session);
	}

	public List findByOutputWidth(Object outputWidth, Session session) {
		return findByProperty(OUTPUT_WIDTH, outputWidth, session);
	}

	public List findByOutputNum(Object outputNum, Session session) {
		return findByProperty(OUTPUT_NUM, outputNum, session);
	}

	public List findByOutputDesc(Object outputDesc, Session session) {
		return findByProperty(OUTPUT_DESC, outputDesc, session);
	}

	public List findByOutputValue(Object outputValue, Session session) {
		return findByProperty(OUTPUT_VALUE, outputValue, session);
	}

	public List findAll(Session session) {
		log.debug("finding all SrvOutput instances");
		try {
			String queryString = "from SrvOutput";
			Query queryObject = session.createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SrvOutput merge(SrvOutput detachedInstance, Session session) {
		log.debug("merging SrvOutput instance");
		try {
			SrvOutput result = (SrvOutput) session.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SrvOutput instance, Session session) {
		log.debug("attaching dirty SrvOutput instance");
		try {
			session.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SrvOutput instance, Session session) {
		log.debug("attaching clean SrvOutput instance");
		try {
			session.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public int findCountByProperty(String propertyName, Object value, Session session) {
        try {
           String queryString = "select count(model.outputId) from SrvOutput as model where model." 
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
        try {
           String queryString = "select max(model.outputNum) from SrvOutput as model where model." 
           						+ propertyName + "= ?";
           Query queryObject = session.createQuery(queryString);
  		   queryObject.setParameter(0, value);
  		 return ((Long)queryObject.uniqueResult()).intValue();
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}
	
	public int[] findOutputNumBySrv(SrvInfo srvInfo, Session session) {
		try {
	           String queryString = "select model.outputNum from SrvOutput as model where model.srvInfo = ?";
	           Query queryObject = session.createQuery(queryString);
	  		   queryObject.setParameter(0, srvInfo);
	  		   List list = queryObject.list();
	  		   int [] outputNums = null;
	  		   if(list!=null && !list.isEmpty()) {
	  			   outputNums = new int[list.size()];
	  			   int i = 0;
	  			   java.util.Iterator it = list.iterator();
	  			   while(it.hasNext()) {
	  				   Long obj = (Long)it.next();
	  				   outputNums[i] = obj.intValue();
	  				   i++;
	  			   }
	  		   }
	  		   return outputNums;
	        } catch (RuntimeException re) {
	           log.error("find by property name failed", re);
	           throw re;
	        }
	}
	
	public int[] noOutputNumsBySrv(SrvInfo srvInfo, Session session) {
		 int [] result = null;
		 int [] outputNums = findOutputNumBySrv(srvInfo, session);
		 if(outputNums!=null) {
			   result = com.sitech.esb.util.JMath.findNums(outputNums);
		   }
		 return result;
	}
	
	public List findBySrvAndNum(SrvInfo srvInfo, Long outputNum, String sort, Session session) {
		log.debug("finding all SrvOutput instances");
		try {
			String queryString = "from SrvOutput as model where model.srvInfo=? and model.outputNum>=? order by model.outputNum " + sort;
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, srvInfo);
			queryObject.setParameter(1, outputNum);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findBySrvAndNum1(SrvInfo srvInfo, Long outputNum, String sort, Session session) {
		log.debug("finding all SrvOutput instances");
		try {
			String queryString = "from SrvOutput as model where model.srvInfo=? and model.outputNum>? order by model.outputNum " + sort;
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, srvInfo);
			queryObject.setParameter(1, outputNum);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public void updateBySrvAndNum(SrvInfo srvInfo, Long outputNum, String sort, Session session) {
		log.debug("finding all SrvInput instances");
		try {
			String queryString = "update SrvOutput as model set model.outputNum=model.outputNum+1 where model.srvInfo=? and model.outputNum>=? order by model.outputNum " + sort;
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, srvInfo);
			queryObject.setParameter(1, outputNum);
			queryObject.executeUpdate();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public void updateBySrvAndNum1(SrvInfo srvInfo, Long outputNum, String sort, Session session) {
		log.debug("finding all SrvInput instances");
		try {
			String queryString = "update SrvOutput as model set model.outputNum=model.outputNum-1 where model.srvInfo=? and model.outputNum>? order by model.outputNum " + sort;
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, srvInfo);
			queryObject.setParameter(1, outputNum);
			queryObject.executeUpdate();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}
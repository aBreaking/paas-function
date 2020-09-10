package com.sitech.esb.hb;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SrvInput entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sitech.esb.hb.SrvInput
 * @author MyEclipse Persistence Tools
 */

public class SrvInputDAO extends BaseHibernateDAO {
	private static final Logger log = Logger.getLogger(SrvInputDAO.class);
	// property constants
	public static final String SRV_ID = "srvInfo";
	public static final String INPUT_NAME = "inputName";
	public static final String INPUT_TYPE = "inputType";
	public static final String INPUT_WIDTH = "inputWidth";
	public static final String INPUT_NUM = "inputNum";
	public static final String INPUT_DESC = "inputDesc";
	public static final String INPUT_VALUE = "inputValue";

	public void save(SrvInput transientInstance, Session session) {
		log.debug("saving SrvInput instance");
		try {
			session.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SrvInput persistentInstance, Session session) {
		log.debug("deleting SrvInput instance");
		try {
			session.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SrvInput findById(java.lang.Long id, Session session) {
		log.debug("getting SrvInput instance with id: " + id);
		try {
			SrvInput instance = (SrvInput) session.get(
					"com.sitech.esb.hb.SrvInput", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SrvInput instance, Session session) {
		log.debug("finding SrvInput instance by example");
		try {
			List results = session.createCriteria(
					"com.sitech.esb.hb.SrvInput").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value, Session session) {
		log.debug("finding SrvInput instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SrvInput as model where model."
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
		log.debug("finding SrvInput instance with property: " + srvInfo
				+ ", value: " + srvInfo);
		try {
			String queryString = "from SrvInput as model where model.srvInfo= ? order by model.inputNum";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, srvInfo);
			List list = queryObject.list();
			return list;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List findSrvInputsVisible(SrvInfo service, Session session) {
    	log.debug("finding All AppInfo instance");
          try {
         	  //String queryString = "from SrvInput as model where model.srvInfo=" + service + " and ((model.isFixedValue=0) or (model.isFixedValue=1 and model.isFixedVisible=1)) order by model.inputNum";
         	  String queryString = "from SrvInput as model where model.srvInfo=?  and ((model.isFixedValue=0) or (model.isFixedValue=1 and model.isFixedVisible=1)) order by model.inputNum";
         	  Query queryObject = session.createQuery(queryString);
         	 queryObject.setParameter(0, service);
     		  return queryObject.list();
          } catch (RuntimeException re) {
             log.error("find by property name failed", re);
             throw re;
          }
    }
	
	public List findSrvInputsNoFixed(SrvInfo service, Session session) {
    	log.debug("finding All AppInfo instance");
          try {
         	  //String queryString = "from SrvInput as model where model.srvInfo=" + service + " and ((model.isFixedValue=0) or (model.isFixedValue=1 and model.isFixedVisible=1)) order by model.inputNum";
         	  String queryString = "from SrvInput as model where model.srvInfo=?  and model.isFixedValue=0 order by model.inputNum";
         	  Query queryObject = session.createQuery(queryString);
         	 queryObject.setParameter(0, service);
     		  return queryObject.list();
          } catch (RuntimeException re) {
             log.error("find by property name failed", re);
             throw re;
          }
    }

	public List findByInputName(Object inputName, Session session) {
		return findByProperty(INPUT_NAME, inputName, session);
	}

	public List findByInputType(Object inputType, Session session) {
		return findByProperty(INPUT_TYPE, inputType, session);
	}

	public List findByInputWidth(Object inputWidth, Session session) {
		return findByProperty(INPUT_WIDTH, inputWidth, session);
	}

	public List findByInputNum(Object inputNum, Session session) {
		return findByProperty(INPUT_NUM, inputNum, session);
	}

	public List findByInputDesc(Object inputDesc, Session session) {
		return findByProperty(INPUT_DESC, inputDesc, session);
	}

	public List findByInputValue(Object inputValue, Session session) {
		return findByProperty(INPUT_VALUE, inputValue, session);
	}

	public List findAll(Session session) {
		log.debug("finding all SrvInput instances");
		try {
			String queryString = "from SrvInput";
			Query queryObject = session.createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SrvInput merge(SrvInput detachedInstance, Session session) {
		log.debug("merging SrvInput instance");
		try {
			SrvInput result = (SrvInput) session.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SrvInput instance, Session session) {
		log.debug("attaching dirty SrvInput instance");
		try {
			session.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SrvInput instance, Session session) {
		log.debug("attaching clean SrvInput instance");
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
           String queryString = "select count(model.inputId) from SrvInput as model where model." 
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
           String queryString = "select max(model.inputNum) from SrvInput as model where model." 
           						+ propertyName + "= ?";
           Query queryObject = session.createQuery(queryString);
  		   queryObject.setParameter(0, value);
  		 return ((Long)queryObject.uniqueResult()).intValue();
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}
	
	public int[] findInputNumBySrv(SrvInfo srvInfo, Session session) {
		try {
	           String queryString = "select model.inputNum from SrvInput as model where model.srvInfo = ?";
	           Query queryObject = session.createQuery(queryString);
	  		   queryObject.setParameter(0, srvInfo);
	  		   List list = queryObject.list();
	  		   int [] inputNums = null;
	  		   if(list!=null && !list.isEmpty()) {
	  			   inputNums = new int[list.size()];
	  			   int i = 0;
	  			   java.util.Iterator it = list.iterator();
	  			   while(it.hasNext()) {
	  				   Long obj = (Long)it.next();
	  				   inputNums[i] = obj.intValue();
	  				   i++;
	  			   }
	  		   }
	  		  return inputNums;
	        } catch (RuntimeException re) {
	           log.error("find by property name failed", re);
	           throw re;
	        }
	}
	
	public int[] noInputNumsBySrv(SrvInfo srvInfo, Session session) {
		 int [] result = null;
		 int [] inputNums = findInputNumBySrv(srvInfo, session);
		 if(inputNums!=null) {
			   result = com.sitech.esb.util.JMath.findNums(inputNums);
		   }
		 return result;
	}
	
	public List findBySrvAndNum(SrvInfo srvInfo, Long inputNum, String sort, Session session) {
		log.debug("finding all SrvInput instances");
		try {
			String queryString = "from SrvInput as model where model.srvInfo=? and model.inputNum>=? order by model.inputNum " + sort;
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, srvInfo);
			queryObject.setParameter(1, inputNum);
			List list = queryObject.list();
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findBySrvAndNum1(SrvInfo srvInfo, Long inputNum, String sort, Session session) {
		log.debug("finding all SrvInput instances");
		try {
			String queryString = "from SrvInput as model where model.srvInfo=? and model.inputNum>? order by model.inputNum " + sort;
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, srvInfo);
			queryObject.setParameter(1, inputNum);
			List list = queryObject.list();
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public void updateBySrvAndNum(SrvInfo srvInfo, Long inputNum, String sort, Session session) {
		log.debug("finding all SrvInput instances");
		try {
			String queryString = "update SrvInput as model set model.inputNum=model.inputNum+1 where model.srvInfo=? and model.inputNum>=? order by model.inputNum " + sort;
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, srvInfo);
			queryObject.setParameter(1, inputNum);
			queryObject.executeUpdate();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public void updateBySrvAndNum1(SrvInfo srvInfo, Long inputNum, String sort, Session session) {
		log.debug("finding all SrvInput instances");
		try {
			String queryString = "update SrvInput as model set model.inputNum=model.inputNum-1 where model.srvInfo=? and model.inputNum>? order by model.inputNum " + sort;
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, srvInfo);
			queryObject.setParameter(1, inputNum);
			queryObject.executeUpdate();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public void cleanSrvInputs(SrvInfo srvInfo, Session s) {
    	org.hibernate.Transaction tx = null;
	    try{
	    	Set set = srvInfo.getSrvInputs();
	    	tx = s.getTransaction();
	        Iterator it = set.iterator();
	        while(it.hasNext()) {
	        	SrvInput srvInput = (SrvInput)it.next();
	        	srvInfo.getSrvInputs().remove(srvInput);
	        	this.delete(srvInput, s);
	        }
	        tx.commit();
	    } catch (org.hibernate.HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			if(s!=null) {
				s.close();
			}
		}
    }
}
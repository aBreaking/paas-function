package com.sitech.esb.hb;

import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

/**
 * @see com.sitech.esb.hb.ExcelStyle
 * @author weigp@20110516
 */
public class ExcelStyleDao extends BaseHibernateDAO {

    private static final Logger log = Logger.getLogger(ExcelStyleDao.class);
    
    public void save(ExcelStyle transientInstance, Session session) {
        log.debug("saving ExcelStyle instance");
        try {
        	Transaction t = session.beginTransaction();
            session.save(transientInstance);
            t.commit();
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(ExcelStyle persistentInstance, Session session) {
        log.debug("deleting ExcelStyle instance");
        try {
        	Transaction t = session.beginTransaction();
            session.delete(persistentInstance);
            t.commit();
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
	
    public void update(ExcelStyle transientInstance, Session session) {
    	log.debug("saving ExcelStyle instance");
        try {
        	Transaction t = session.beginTransaction();
            session.update(transientInstance);
            t.commit();
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public ExcelStyle findById( java.lang.Long id, Session session) {
        log.debug("getting ExcelStyle instance with id: " + id);
        try {
        	ExcelStyle instance = (ExcelStyle) session
                    .get("com.sitech.esb.hb.ExcelStyle", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public void deleteById( java.lang.Long id, Session session) {
        log.debug("getting ExcelStyle instance with id: " + id);
        try {
        	String sql = "delete ExcelStyle where id = ?";
        	Query queryObject = session.createQuery(sql);
   		 	queryObject.setParameter(0, id);
            queryObject.executeUpdate();
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(ExcelStyle instance, Session session) {
        log.debug("finding ExcelStyle instance by example");
        try {
            List results = session
                    .createCriteria("com.sitech.esb.hb.ExcelStyle")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByPropertys(String[] pNames, Object[] values, Session session){
    	List arrayList = new ArrayList();
    	log.debug("finding ExcelStyle instance with properties");
    	try {
	    	String queryString = "from ExcelStyle where 1=1 and COLM_STYLE_POSITION != -1 ";
	    	if(pNames != null && values != null && 
	    			pNames.length >0 && values.length >0 && pNames.length == values.length){
	    		for(int i=0;i<pNames.length;i++){
	    			queryString += " and "+pNames[i] + " = ? ";
	    		}
	    	}
	    	queryString += " order by COLM_STYLE_POSITION";
	    	Query queryObj = session.createQuery(queryString);
	    	//ע�����
	    	for(int i=0;i<pNames.length;i++){
    			queryObj.setParameter(i, values[i]);
    		}
	    	arrayList = queryObj.list();
	    	return arrayList;
    	} catch (RuntimeException re) {
            log.error("find by propertys failed", re);
            throw re;
         }
    }

    
    public List findByProperty(String propertyName, Object value, Session session) {
      log.debug("finding ExcelStyle instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from ExcelStyle as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = session.createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

    public ExcelStyle merge(ExcelStyle detachedInstance, Session session) {
        log.debug("merging ExcelStyle instance");
        try {
        	Transaction t = session.beginTransaction();
        	ExcelStyle result = (ExcelStyle) session
                    .merge(detachedInstance);
        	t.commit();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ExcelStyle instance, Session session) {
        log.debug("attaching dirty ExcelStyle instance");
        try {
        	Transaction t = session.beginTransaction();
            session.saveOrUpdate(instance);
            t.commit();
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(ExcelStyle instance, Session session) {
        log.debug("attaching clean ExcelStyle instance");
        try {
            session.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List getExcelColm(Long styleLong,SrvType srvType,Session session){
    	return findByPropertys(new String[]{"COLM_STYLE_PARENTID","COLM_STYLE_TYPE"}, new Object[]{styleLong,srvType} , session);
    }
    
}
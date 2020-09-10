package com.sitech.esb.hb;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import org.hibernate.criterion.Example;

public class SoaphttpSystemAppendDAO extends BaseHibernateDAO{
	private static final Logger log = Logger.getLogger(SoaphttpSystemAppendDAO.class);
	
	public void save(SoaphttpSystemAppend transientInstance,Session session){
		log.debug("saving SoaphttpSystemAppend instance");
		try{
			session.save(transientInstance);
			log.debug("save successful");
		}catch (RuntimeException re) {
			log.error("save failed",re);
			throw re;
		}
	}
	public void delete(SoaphttpSystemAppend persistentInstance,Session session){
		log.debug("deleting SoaphttpSystem instance");
		try{
			session.delete(persistentInstance);
			log.debug("delete successful");
		}catch(RuntimeException re){
			log.error("delete failed",re);
			throw re;
		}
	}
	public SoaphttpSystemAppend findById(java.lang.Long id,Session session){
		log.debug("getting SoaphttpSystemAppend instance with id:"+id);
		try{
			SoaphttpSystemAppend instance = (SoaphttpSystemAppend)session.get("com.sitech.esb.hb.SoaphttpSystemAppend",
					id);
			return instance;
		}catch(RuntimeException re){
			log.error("get failed",re);
			throw re;
		}
	}
	public List findByExample(SoaphttpSystemAppend instance,Session session){
		log.debug("finding SoaphttpSystem instance by example");
		try{
			List results = session.createCriteria("com.sitech.esb.hb.SoaphttpSystemAppend").add(
					Example.create(instance)).list();
			log.debug("find by example successful,result size:"+results.size());
			return results;
		}catch(RuntimeException re){
			log.error("find by example failed",re);
			throw re;
		}
	}
	public List findByProperty(String propertyName,Object value,Session session){
		log.debug("finding SoaphttpSystemAppend instance with property:"+propertyName+",value:"+value);
		try{
			String queryString ="from SoaphttpSystemAppend as model where model."+propertyName+"=?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		}catch(RuntimeException re){
			log.error("find by property name failed",re);
			throw re;
		}
	}
	public SoaphttpSystemAppend merge(SoaphttpSystemAppend detachedInstance,Session session){
		log.debug("merging SoaphttpSystemAppend instance");
		try{
			SoaphttpSystemAppend result = (SoaphttpSystemAppend)session.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		}catch (RuntimeException re) {
			log.error("merge failed",re);
			throw re;
		}
	}
	public void attachDirty(SoaphttpSystemAppend instance,Session session){
		log.debug("attaching dirty SoaphttpSystemAppend instance");
		try{
			session.saveOrUpdate(instance);
			log.debug("attach successful");
		}catch(RuntimeException re){
			log.error("attach failed",re);
			throw re;
		}
	}
	
	public void attachClean(SoaphttpSystemAppend instance,Session session){
		log.debug("attaching clean SoaphttpSystemAppend instance");
		try{
			session.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		}catch(RuntimeException re){
			log.error("attach failed",re);
			throw re;
		}
	}
	public static void main(String []args){
		
		
	}
}
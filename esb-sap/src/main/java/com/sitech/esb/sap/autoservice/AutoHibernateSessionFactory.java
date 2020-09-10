package com.sitech.esb.sap.autoservice;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.WeakHashMap;

/**
 * 创建hibernate session，解决线程并发的问题
 * @author created by liuc ,rewrited by liwei_paas
 * @date 2020/4/17
 */
public class AutoHibernateSessionFactory {


    /** 
     * Location of hibernate.cfg.xml file.
     * Location should be on the classpath as Hibernate uses  
     * #resourceAsStream style lookup for its configuration file. 
     * The default classpath location of the hibernate config file is 
     * in the default package. Use #setConfigFile() to update 
     * the location of the configuration file for the current session.   
     */
    public static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";

	/**
	 * for liuc : 这里不应该是根据当前线程来缓存session，而是应该根据配置文件configFile -> String 来做缓存。因为可能会有多个线程都同样的configFile，进一步创建同样的session
	 * by liwei : 用它来缓存线程正在操作的configFile
	 */
	private static final ThreadLocal<String> threadLocal = new ThreadLocal();
	/**
	 * by liwei
	 * 采用软引用的hashmap来做缓存，机制其实跟ThreadLocal差不多，key 都是weak类型的key，只不过，应该对configFile做缓存，而不是当前对象
	 * 需要注意WeakHashMap 并不是线程安全的
	 */
	private static final WeakHashMap<String,Session> SESSION_CACHE = new WeakHashMap();

    private String configFile = CONFIG_FILE_LOCATION;

    private AutoHibernateSessionFactory(String configFile) {
    	this.configFile = configFile;
		threadLocal.set(configFile);

    }

    public static Session load(String configFile){
		AutoHibernateSessionFactory factory = new AutoHibernateSessionFactory(configFile);
		return factory.getSession();
	}
	
	/**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    private Session getSession() throws HibernateException {
        Session session = SESSION_CACHE.get(configFile);
		if (session == null || !session.isOpen()) {
			Configuration configuration = new Configuration();//add by liuc
			configuration.configure(configFile);
			synchronized (SESSION_CACHE){
				//加锁+双重校验，防止在获取锁后其他线程已经创建完毕该session了
				if (session == null || !session.isOpen()) {
					SessionFactory sessionFactory = configuration.buildSessionFactory();
					session = (sessionFactory != null) ? sessionFactory.openSession() : null;
					SESSION_CACHE.put(configFile,session);
				}
			}
		}
        return session;
    }

	/**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession(Session session) throws HibernateException {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

	public static String getThreadConfigFile() {
		String s = threadLocal.get();
		return s == null?CONFIG_FILE_LOCATION:s;
	}
}

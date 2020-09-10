package com.sitech.esb.sap.autoservice;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.WeakHashMap;

/**
 * ����hibernate session������̲߳���������
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
	 * for liuc : ���ﲻӦ���Ǹ��ݵ�ǰ�߳�������session������Ӧ�ø��������ļ�configFile -> String �������档��Ϊ���ܻ��ж���̶߳�ͬ����configFile����һ������ͬ����session
	 * by liwei : �����������߳����ڲ�����configFile
	 */
	private static final ThreadLocal<String> threadLocal = new ThreadLocal();
	/**
	 * by liwei
	 * ���������õ�hashmap�������棬������ʵ��ThreadLocal��࣬key ����weak���͵�key��ֻ������Ӧ�ö�configFile�����棬�����ǵ�ǰ����
	 * ��Ҫע��WeakHashMap �������̰߳�ȫ��
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
				//����+˫��У�飬��ֹ�ڻ�ȡ���������߳��Ѿ�������ϸ�session��
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

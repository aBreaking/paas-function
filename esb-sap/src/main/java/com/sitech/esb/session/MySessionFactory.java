package com.sitech.esb.session;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.Document;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author liwei_paas
 * @date 2020/9/11
 */
public class MySessionFactory {

    /**
     * 数据源连接池
     * FIXME 考虑是否需要一个过期淘汰策略的算法，不然会一直保存着一个数据源的连接
     */
    private static final Map<String,Session> SESSION_MAP = new ConcurrentHashMap<>();

    private ApplicationCfgYmlParser ymlParser = new ApplicationCfgYmlParser();
    private HibernateCfgXmlParser xmlParser = new HibernateCfgXmlParser();

    public Session getSession(String poolKey) throws Exception {
        if (SESSION_MAP.containsKey(poolKey)){
            return SESSION_MAP.get(poolKey);
        }
        Map<String, Map<String, String>> dataSourceConfig = ymlParser.getDataSourceConfig();
        Map<String, String> map = dataSourceConfig.get(poolKey);
        Document document = xmlParser.parseSF(map);
        Configuration configuration = new Configuration();
        configuration.configure(document);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        SESSION_MAP.put(poolKey,session);
        return session;
    }

}

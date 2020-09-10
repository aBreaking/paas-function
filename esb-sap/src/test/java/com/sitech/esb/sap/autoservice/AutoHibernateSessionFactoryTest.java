package com.sitech.esb.sap.autoservice;

import jxl.Workbook;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @{USER}
 * @{DATE}
 */
public class AutoHibernateSessionFactoryTest {

    @Test
    public void test01(){
        Session session = AutoHibernateSessionFactory.load("hibernate.cfg.xml");
        SQLQuery query = session.createSQLQuery("select * from alarm_src");
        List list = query.list();
        System.out.println(list.size());
    }
    @Test
    public void testSap() throws Exception {
        String file = "D:\\esb-srv-demo.xls";
        EsbPoolConfig esbPoolConfig = new EsbPoolConfig();
        String poolKey = "whatever";
        String hbXml = "hibernate.cfg.xml";
        String poolAddress = "172.18.231.104:51000";
        esbPoolConfig.setHbXml(hbXml);
        esbPoolConfig.setPoolAddress(poolAddress);
        esbPoolConfig.setPoolKey(poolKey);
        SapInvoker invoker = new SapInvoker();
        invoker.setWorkbook(Workbook.getWorkbook(new FileInputStream(file)));
        invoker.setConfig(esbPoolConfig);
        String call = invoker.call();
        System.out.println(call);
    }
}

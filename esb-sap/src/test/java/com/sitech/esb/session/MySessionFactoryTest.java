package com.sitech.esb.session;

import com.sitech.esb.CommonTest;
import org.hibernate.Session;
import org.junit.Test;

/**
 *
 * @author liwei_paas
 * @date 2020/9/11
 */
public class MySessionFactoryTest {

    @Test
    public void testSession() throws Exception {
        MySessionFactory sessionFactory = new MySessionFactory();
        Session bj = sessionFactory.getSession("bj104");
        Session localhost = sessionFactory.getSession("localhost");
        CommonTest.testSession(bj);
        CommonTest.testSession(localhost);
    }
}

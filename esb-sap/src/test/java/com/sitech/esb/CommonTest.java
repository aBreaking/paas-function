package com.sitech.esb;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.List;

/**
 *
 * @author liwei_paas
 * @date 2020/9/11
 */
public class CommonTest {

    public static int testSession(Session session){
        SQLQuery query = session.createSQLQuery("select * from srvinfo");
        List list = query.list();
        System.out.println(list.size());
        return list.size();
    }
}

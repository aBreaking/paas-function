package com.abreaking.easyjpa.dao;


import com.abreaking.easyjpa.common.TestUser;
import com.abreaking.easyjpa.config.DruidDataSourceConfiguration;
import com.abreaking.easyjpa.exec.Executor;
import com.abreaking.easyjpa.exec.JdbcExecutor;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 *
 * @author liwei_paas
 * @date 2020/6/18
 */
public class CurdDaoTest {

    @Test
    public void testSelect() throws Exception {
        DruidPooledConnection connection = new DruidDataSourceConfiguration().druidDataSource().getConnection();
        Executor executor = new JdbcExecutor(connection);

        CurdDaoImpl dao = new CurdDaoImpl(executor);
        TestUser testUser = new TestUser();
        testUser.setUserName("zhangsan");
        List list = dao.select(testUser);
        System.out.println(list);
    }

    @Test
    public void testUpdate() throws Exception {
        DruidPooledConnection connection = new DruidDataSourceConfiguration().druidDataSource().getConnection();
        Executor executor = new JdbcExecutor(connection);

        CurdDaoImpl dao = new CurdDaoImpl(executor);
        TestUser testUser = new TestUser();
        testUser.setUserId(1);
        System.out.println(dao.select(testUser));
        testUser.setBirthday(new Date());
        System.out.println(dao.update(testUser));
        System.out.println(dao.select(testUser));

    }


    @Test
    public void testInsert() throws Exception {
        DruidPooledConnection connection = new DruidDataSourceConfiguration().druidDataSource().getConnection();
        Executor executor = new JdbcExecutor(connection);

        CurdDaoImpl dao = new CurdDaoImpl(executor);
        TestUser testUser = new TestUser();
        testUser.setUserId(5);
        testUser.setBirthday(new Date());
        testUser.setUserName("rose");
        dao.insert(testUser);
    }

    @Test
    public void testDelete() throws Exception {
        DruidPooledConnection connection = new DruidDataSourceConfiguration().druidDataSource().getConnection();
        Executor executor = new JdbcExecutor(connection);

        CurdDaoImpl dao = new CurdDaoImpl(executor);
        TestUser testUser = new TestUser();
        testUser.setUserName("lisi");
        int delete = dao.delete(testUser);
        System.out.println(delete);
    }

}

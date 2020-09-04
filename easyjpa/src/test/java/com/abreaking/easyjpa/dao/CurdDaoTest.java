package com.abreaking.easyjpa.dao;


import com.abreaking.easyjpa.common.TestUser;
import com.abreaking.easyjpa.config.DruidDataSourceConfiguration;
import com.abreaking.easyjpa.exec.Executor;
import com.abreaking.easyjpa.exec.JdbcExecutor;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import java.util.List;

/**
 *
 * @author liwei_paas
 * @date 2020/6/18
 */
public class CurdDaoTest {

    @Test
    public void test01() throws Exception {
        DruidPooledConnection connection = new DruidDataSourceConfiguration().druidDataSource().getConnection();
        Executor executor = new JdbcExecutor(connection);

        CurdDaoImpl dao = new CurdDaoImpl<>(executor);
        TestUser testUser = new TestUser();
        testUser.setUserId(1);
        List list = dao.select(testUser);
        System.out.println(list);
    }

}

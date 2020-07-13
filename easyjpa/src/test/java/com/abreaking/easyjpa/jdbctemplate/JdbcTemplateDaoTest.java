package com.abreaking.easyjpa.jdbctemplate;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.sql.Types;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:*.xml"})
public class JdbcTemplateDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DruidDataSource druidDataSource;

    @Test
    public void test01(){
    }

    @Test
    public void test(){
        Object[] values = new Object[]{1};
        int[] types = new int[]{Types.INTEGER};
        List<String> list = jdbcTemplate.queryForList("select ip from client_info where id = ?", values, types, String.class);
        System.out.println(list);
    }


}

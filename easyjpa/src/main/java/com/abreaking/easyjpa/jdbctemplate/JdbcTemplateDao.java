package com.abreaking.easyjpa.jdbctemplate;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @{USER}
 * @{DATE}
 */
@Component
public class JdbcTemplateDao {

    @Resource
    JdbcTemplate jdbcTemplate;

    public void exec(){
        jdbcTemplate.execute("select * from user");
    }
}

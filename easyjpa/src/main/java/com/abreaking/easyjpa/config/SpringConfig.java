package com.abreaking.easyjpa.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.annotation.Resource;

/**
 * 整合spring配置
 * @author liwei_paas 
 * @date 2019/12/23
 */
@Configuration
public class SpringConfig {

    @Resource
    DruidDataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource);
    }
}

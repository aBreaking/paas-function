package com.abreaking.easyjpa.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.jdbc.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author liwei_paas
 * @date 2019/11/1
 */
@Configuration
public class DruidDataSourceConfiguration {
    final String driver = "com.mysql.jdbc.Driver";
    final String jdbcurl = "jdbc:mysql://localhost:3306/test";
    final String username = "root";
    final String paasword = "mysqladmin";

    @Bean
    public DruidDataSource druidDataSource() throws Exception {
        Class.forName(driver);
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriver(new Driver());
        druidDataSource.setUrl(jdbcurl);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(paasword);
        return druidDataSource;
    }
}

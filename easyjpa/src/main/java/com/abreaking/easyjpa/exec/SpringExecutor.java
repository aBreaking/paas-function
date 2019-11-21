package com.abreaking.easyjpa.exec;

import com.abreaking.easyjpa.mapping.JpaRowMapper;
import com.abreaking.easyjpa.mapping.SpringJdbcJpaRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 直接使用spring的jdbc的执行器方式
 * @author liwei_paas
 * @date 2019/11/21
 */
@Component
public class SpringExecutor implements Executor{

    @Resource
    JdbcTemplate jdbcTemplate;

    @Override
    public <T> T queryForObject(String preparedSql, Object[] values, int[] types, Class<T> obj) {
        return jdbcTemplate.queryForObject(preparedSql,values,types,obj);
    }

    @Override
    public Object queryForObject(String preparedSql, Object[] values, int[] types, JpaRowMapper mapper) {
        return jdbcTemplate.queryForObject(preparedSql,values,types,new SpringJdbcJpaRowMapper(mapper));
    }

    @Override
    public List<?> queryForList(String preparedSql, Object[] values, int[] types, JpaRowMapper mapper) {
        return null;
    }

    @Override
    public <T> List<T> queryForList(String preparedSql, Object[] values, int[] types, Class<T> obj) {
        return null;
    }

    @Override
    public void update(String preparedSql, String[] args) {

    }
}

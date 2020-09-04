package com.abreaking.easyjpa.spring;

import com.abreaking.easyjpa.mapper.JpaRowMapper;
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
public class SpringExecutor  {

    @Resource
    JdbcTemplate jdbcTemplate;

    
    public <T> T queryForObject(String preparedSql, Object[] values, int[] types, Class<T> obj) {
        return jdbcTemplate.queryForObject(preparedSql,values,types,obj);
    }

    
    public Object queryForObject(String preparedSql, Object[] values, int[] types, JpaRowMapper mapper) {
        return jdbcTemplate.queryForObject(preparedSql,values,types,new SpringJdbcJpaRowMapper(mapper));
    }

    
    public List<?> queryForList(String preparedSql, Object[] values, int[] types, JpaRowMapper mapper) {
        return null;
    }

    
    public <T> List<T> queryForList(String preparedSql, Object[] values, int[] types, Class<T> obj) {
        return null;
    }

    
    public void update(String preparedSql, String[] args) {

    }
}

package com.abreaking.easyjpa.spring;

import com.abreaking.easyjpa.mapper.JpaRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 拿来主义，这里直接使用spring jdbc的那一套映射操作
 * @author liwei
 */
public class SpringJdbcJpaRowMapper implements Serializable,RowMapper {

    private JpaRowMapper rowMapper;

    public SpringJdbcJpaRowMapper(JpaRowMapper jpaRowMapper){
        this.rowMapper = jpaRowMapper;
    }

    @Override
    public RowMapper mapRow(ResultSet rs, int rowNumber) throws SQLException {
        BeanPropertyRowMapper beanPropertyRowMapper = new BeanPropertyRowMapper(rowMapper.getClass());
        return (RowMapper) beanPropertyRowMapper.mapRow(rs,rowNumber);
    }

}

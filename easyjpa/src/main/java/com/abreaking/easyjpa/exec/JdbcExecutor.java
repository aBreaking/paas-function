package com.abreaking.easyjpa.exec;

import com.abreaking.easyjpa.mapper.RowMapper;
import com.abreaking.easyjpa.mapper.impl.DefaultJpaRowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author liwei_paas
 * @date 2020/7/6
 */
public class JdbcExecutor implements Executor{

    private Connection connection;


    public JdbcExecutor(Connection connection){
        this.connection = connection;
    }

    @Override
    public <T> List<T> queryForList(String preparedSql, Object[] values, int[] types,RowMapper rowMapper) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(preparedSql);
        for (int i = 0; i < values.length; i++) {
            setValue(ps,i+1,types[i],values[i]);
        }
        ResultSet rs = ps.executeQuery();
        List<T> list = new ArrayList<>();
        int i =0;
        while (rs.next()){
            Object o = rowMapper.mapRow(rs, i);
            i++;
            list.add((T) o);
        }
        return list;
    }

    @Override
    public void update(String preparedSql, String[] args) {

    }

    private static void setValue(PreparedStatement ps, int paramIndex, int sqlType,Object inValue) throws SQLException {

        if (sqlType == Types.VARCHAR || sqlType == Types.LONGVARCHAR ) {
            ps.setString(paramIndex, inValue.toString());
        }else if (sqlType == Types.NVARCHAR || sqlType == Types.LONGNVARCHAR) {
            ps.setNString(paramIndex, inValue.toString());
        }else if (sqlType == Types.DECIMAL || sqlType == Types.NUMERIC) {
            ps.setObject(paramIndex, inValue, sqlType);
        }else if (sqlType == Types.BOOLEAN) {
            if (inValue instanceof Boolean) {
                ps.setBoolean(paramIndex, (Boolean) inValue);
            }
            else {
                ps.setObject(paramIndex, inValue, Types.BOOLEAN);
            }
        }else if (sqlType == Types.DATE) {
            if (inValue instanceof java.util.Date) {
                if (inValue instanceof java.sql.Date) {
                    ps.setDate(paramIndex, (java.sql.Date) inValue);
                }
                else {
                    ps.setDate(paramIndex, new java.sql.Date(((java.util.Date) inValue).getTime()));
                }
            }
            else if (inValue instanceof Calendar) {
                Calendar cal = (Calendar) inValue;
                ps.setDate(paramIndex, new java.sql.Date(cal.getTime().getTime()), cal);
            }
            else {
                ps.setObject(paramIndex, inValue, Types.DATE);
            }
        }else if (sqlType == Types.TIME) {
            if (inValue instanceof java.util.Date) {
                if (inValue instanceof java.sql.Time) {
                    ps.setTime(paramIndex, (java.sql.Time) inValue);
                }
                else {
                    ps.setTime(paramIndex, new java.sql.Time(((java.util.Date) inValue).getTime()));
                }
            }
            else if (inValue instanceof Calendar) {
                Calendar cal = (Calendar) inValue;
                ps.setTime(paramIndex, new java.sql.Time(cal.getTime().getTime()), cal);
            }
            else {
                ps.setObject(paramIndex, inValue, Types.TIME);
            }
        }
        else if (sqlType == Types.TIMESTAMP) {
            if (inValue instanceof java.util.Date) {
                if (inValue instanceof java.sql.Timestamp) {
                    ps.setTimestamp(paramIndex, (java.sql.Timestamp) inValue);
                }
                else {
                    ps.setTimestamp(paramIndex, new java.sql.Timestamp(((java.util.Date) inValue).getTime()));
                }
            }
            else if (inValue instanceof Calendar) {
                Calendar cal = (Calendar) inValue;
                ps.setTimestamp(paramIndex, new java.sql.Timestamp(cal.getTime().getTime()), cal);
            }
            else {
                ps.setObject(paramIndex, inValue, Types.TIMESTAMP);
            }
        }else {
            ps.setObject(paramIndex, inValue, sqlType);
        }
    }
}

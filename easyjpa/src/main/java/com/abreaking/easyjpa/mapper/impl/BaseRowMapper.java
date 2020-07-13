package com.abreaking.easyjpa.mapper.impl;

import com.abreaking.easyjpa.mapper.RowMapper;
import com.abreaking.easyjpa.util.NameUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;

/**
 *
 * @author liwei_paas
 * @date 2020/7/13
 */
public class BaseRowMapper implements RowMapper {
    public Class obj;

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Object t;
        try {
            t = obj.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            return null;
        }
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            //columnName -> fieldName
            String fieldName = NameUtil.deunderscoreName(columnName);
            try {
                Field field = obj.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object o = getResultSetValue(rs, i, field.getType());
                field.set(t,o);
            } catch (Exception e) {
                continue;
            }
        }
        return t;
    }

    public static Object getResultSetValue(ResultSet rs, int index, Class<?> requiredType) throws SQLException {
        if (String.class == requiredType) {
            return rs.getString(index);
        }
        else if (boolean.class == requiredType || Boolean.class == requiredType) {
            return rs.getBoolean(index);
        }
        else if (byte.class == requiredType || Byte.class == requiredType) {
            return rs.getByte(index);
        }
        else if (short.class == requiredType || Short.class == requiredType) {
            return rs.getShort(index);
        }
        else if (int.class == requiredType || Integer.class == requiredType) {
            return rs.getInt(index);
        }
        else if (long.class == requiredType || Long.class == requiredType) {
            return rs.getLong(index);
        }
        else if (float.class == requiredType || Float.class == requiredType) {
            return rs.getFloat(index);
        }
        else if (double.class == requiredType || Double.class == requiredType ||
                Number.class == requiredType) {
            return rs.getDouble(index);
        }
        else if (BigDecimal.class == requiredType) {
            return rs.getBigDecimal(index);
        }
        else if (java.sql.Date.class == requiredType) {
            return rs.getDate(index);
        }
        else if (java.sql.Time.class == requiredType) {
            return rs.getTime(index);
        }
        else if (java.sql.Timestamp.class == requiredType || java.util.Date.class == requiredType) {
            return rs.getTimestamp(index);
        }
        else if (byte[].class == requiredType) {
            return rs.getBytes(index);
        }
        else if (Blob.class == requiredType) {
            return rs.getBlob(index);
        }
        else if (Clob.class == requiredType) {
            return rs.getClob(index);
        }
        else  {
            throw new RuntimeException("error type");
        }
    }


}

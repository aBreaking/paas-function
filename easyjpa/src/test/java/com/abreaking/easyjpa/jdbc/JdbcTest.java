package com.abreaking.easyjpa.jdbc;

import com.abreaking.easyjpa.config.DruidDataSourceConfiguration;
import com.abreaking.easyjpa.mapper.ClassMapper;
import com.abreaking.easyjpa.mapper.ObjectMapper;
import com.abreaking.easyjpa.mapper.RowMapper;
import com.abreaking.easyjpa.matrix.Matrix;
import com.abreaking.easyjpa.matrix.impl.AxisColumnMatrix;
import com.abreaking.easyjpa.util.SqlUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @{USER}
 * @{DATE}
 */
public class JdbcTest {

    public static Connection getConnection() throws Exception{
        DruidDataSourceConfiguration configuration = new DruidDataSourceConfiguration();
        DruidDataSource source = configuration.druidDataSource();
        DruidPooledConnection druidPooledConnection = source.getConnection();
        Connection connection = druidPooledConnection.getConnection();
        return connection;
    }

    @Test
    public void select() throws Exception {
        String sql = "select * from user where id = ?";
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,1001);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while (rs.next()){
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rsmd.getColumnName(i);
                System.out.println(columnName);
                if (columnName.equals("age")){
                    System.out.println(rs.getInt(i));
                }else {
                    System.out.println(rs.getString(i));
                }
            }
        }
    }

    @Test
    public void selectMapper() throws Exception{
        final User user = new User();
        user.setId(1001);
        RowMapper rowMapper = new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    //columnName -> property
                    try {
                        Field field = user.getClass().getDeclaredField(columnName);
                        field.setAccessible(true);
                        Object o = getResultSetValue(rs, i, field.getType());
                        field.set(user,o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return user;
            }
        };
        ObjectMapper objectMapper = new ObjectMapper() {
            @Override
            public Matrix matrix() {
                AxisColumnMatrix matrix = new AxisColumnMatrix();
                matrix.put("id",SqlUtil.getSqlType(Integer.class),user.getId());
                return matrix;
            }
        };
        ClassMapper classMapper = new ClassMapper() {
            @Override
            public String tableName() {
                return "user";
            }

            @Override
            public Matrix columnType() {
                return null;
            }
        };
        Matrix condition = objectMapper.matrix();
        String[] columns = condition.columns();
        int[] types = condition.types();
        Object[] values = condition.values();
        StringBuilder builder = new StringBuilder();
            for (int i = 0; i < columns.length; i++) {
            builder.append(" and ");
            builder.append(columns[i]);
            builder.append(" =? ");
        }
        String sql = "select * from "+classMapper.tableName()+" where 1=1";
        sql+=builder.toString();
        PreparedStatement ps = getConnection().prepareStatement(sql);
        for (int i = 0; i < columns.length; i++) {
            int paramIndex = i+1;
            setValue(ps,paramIndex,types[i],values[i]);
        }
        ResultSet rs = ps.executeQuery();
        List<User> list = new ArrayList<>();
        while (rs.next()){
            User o = (User) rowMapper.mapRow(rs, 0);
            list.add(o);
        }
        System.out.println(list);
    }

    @Test
    public void insert() throws Exception {
        String sql = "insert into user value (1001,?)";
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1,"hello");

        int i = ps.executeUpdate();
        System.out.println(i);
    }

    @Test
    public void update() throws Exception {
        String sql = "update user set name = 'world' where id=1001";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println(i);
    }

    @Test
    public void delete() throws Exception {
        String sql = "delete from user where id=1001";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = preparedStatement.executeUpdate();
        System.out.println(i);
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

    public class User{
        int id;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

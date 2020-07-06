/*
package com.abreaking.easyjpa.mapper.impl;

import com.sitech.paas.common.annotation.DateTable;
import com.sitech.paas.common.jpa.constraint.Column;
import com.sitech.paas.common.jpa.constraint.Pk;
import com.sitech.paas.common.jpa.constraint.Table;
import com.sitech.paas.common.jpa.mapper.JpaRowMapper;
import com.sitech.paas.common.jpa.matrix.MyColumnMatrix;
import com.sitech.paas.common.jpa.matrix.impl.ColumnAxisMatrix;
import com.sitech.paas.common.util.ReflectUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.StringUtils;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;


*
 *  实体jpa的映射关系的实现，实体的映射直接使用spring 的 {@link BeanPropertyRowMapper}
 * @author liwei_paas 
 * @date 2019/8/28


public class EntityJpaRowMapper implements JpaRowMapper {

    protected final Object entity;

    private final Class mapperClass;

    public EntityJpaRowMapper(Object entity) {
        this.entity = entity;
        this.mapperClass = entity.getClass();
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        BeanPropertyRowMapper beanPropertyRowMapper = new BeanPropertyRowMapper(mapperClass);
        return beanPropertyRowMapper.mapRow(rs,rowNum);
    }

    @Override
    public String table() {
        if (mapperClass.isAnnotationPresent(Table.class)){
            Table table = (Table) mapperClass.getAnnotation(Table.class);
            String value = table.value().trim();
            return value.length()==0?defaultTableName(mapperClass):value;
        }
        if (mapperClass.isAnnotationPresent(DateTable.class)){
            return ReflectUtil.getCurrentTableName(mapperClass);
        }
        return defaultTableName(mapperClass);
    }

    @Override
    public MyColumnMatrix matrix() {
        Field[] fields = mapperClass.getDeclaredFields();
        MyColumnMatrix colMatrix = new ColumnAxisMatrix(fields.length);
        for (Field field : fields) {
            build(colMatrix,field);
        }
        return colMatrix;
    }

    @Override
    public MyColumnMatrix pk() {
        Field[] fields = mapperClass.getDeclaredFields();
        MyColumnMatrix matrix = new ColumnAxisMatrix();
        for (Field field:fields){
            if (field.isAnnotationPresent(Pk.class)){
                build(matrix,field);
            }
        }
        return matrix;
    }

*
     * 默认表名
     * @return


    protected String defaultTableName(Class clazz){
        String simpleName = clazz.getSimpleName();
        String underscoreName = underscoreName(simpleName);
        //后缀不要带Vo之类的
        if (underscoreName.endsWith("vo")||underscoreName.endsWith("po")){
            underscoreName = underscoreName.substring(0,underscoreName.length()-2);
        }
        if (underscoreName.endsWith("_")){
            underscoreName = underscoreName.substring(0,underscoreName.length()-1);
        }
        return underscoreName;
    }

    protected void build(MyColumnMatrix matrix,Field field){
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(entity);
            if (fieldValue == null){
                return;
            }

            String colName = "";
            int type = Types.NULL;

            if (field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                colName = column.value();
                type = column.type();
            }
            colName = colName.isEmpty()?underscoreName(field.getName()):colName;
            type = type == Types.NULL?getType(field):type;

            matrix.put(colName,type,fieldValue);
        } catch (Exception e) {
            return;
        }

    }

    private int getType(Field field){
        Class<?> fieldType = field.getType();
        int type;
        //字段类型处理，这里处理三种：String,Number,Date
        if (String.class.isAssignableFrom(fieldType)){
            type = Types.VARCHAR;
        }else if (Date.class.isAssignableFrom(fieldType)){
            type = Types.TIMESTAMP;
        }else if (Number.class.isAssignableFrom(fieldType)){
            type = Types.NUMERIC;
        }else{
            //其他类型的，默认按照字符串进行处理
            type = Types.VARCHAR;
        }
        return type;
    }

    private String getterMethodName(String fieldName){
        String firstWord = fieldName.substring(0, 1);
        return "get"+firstWord.toUpperCase()+fieldName.substring(1);
    }

    private String underscoreName(String name) {
        if (!StringUtils.hasLength(name)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(name.substring(0, 1).toLowerCase());
        for (int i = 1; i < name.length(); i++) {
            String s = name.substring(i, i + 1);
            String slc = s.toLowerCase();
            if (!s.equals(slc)) {
                result.append("_").append(slc);
            }
            else {
                result.append(s);
            }
        }
        return result.toString();
    }


}
*/

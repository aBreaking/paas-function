package com.abreaking.easyjpa.mapping;

import com.abreaking.easyjpa.constraint.Column;
import com.abreaking.easyjpa.constraint.Id;
import com.abreaking.easyjpa.matrix.ColumnMatrix;
import com.abreaking.easyjpa.matrix.Matrix;
import com.abreaking.easyjpa.matrix.impl.AxisColumnMatrix;
import com.abreaking.easyjpa.util.NameUtil;
import com.abreaking.easyjpa.util.ReflectUtil;
import com.abreaking.easyjpa.util.SqlUtil;
import org.apache.commons.lang.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Types;
import java.util.Map;


/**
 * 通用的JPA 实体-表  映射类
 * 通过它你就不需要再麻烦的一一映射 属性->列名 了。
 * 如果你想继承该类，那么你得需要遵守以下的规则：
 *      1、规范化的命名你的字段还有你的类名，驼峰式，比如：你数据库某个表的字段叫做 aaa_bbb_ccc，那么你的字段名就应该是aaaBbbCcc，类名跟表名也是一样的，类名允许跟个后缀 vo 或者 po
 *      2、类名跟表名的映射你也可以使用 DateTable注解，或者你去重写defaultTableName()这个方法。
 *      3、每个表都强烈建议有个自然主键，会被自动分配，默认是自增。这在你做增删改查很有用。默认主键名叫做 id 。。  或者你自己重写primaryProperty()方法，指定是哪个字段（注意是字段）
 * 目前规则很简单，也就是尽量去遵守java 开发的相关规范就OK了。
 *
 * 而后，一些增删改查的通用方法，see {@CommonJpaService} or {@CommonJpaDao} 。 sql规范的数据库（比如oracle、mysql都支持）
 *
 * @author liwei_paas
 */
public class JpaRowMapper {


    /**
     * 默认表名
     * @return
     */
    public String tableName(){
        return ReflectUtil.getTableName(this.getClass());
    }

    public Matrix emptyMatrix(){
        return matrix(false);
    }

    public Matrix matrix(){
        return matrix(true);
    }

    /**
     * 将对象有值的属性作为matrix
     * @param isNeedFieldValue 是否需要值为空的字段
     * @return
     */
    protected Matrix matrix(boolean isNeedFieldValue){
        Field[] fields = this.getClass().getDeclaredFields();
        Map<String, Method> methodMap = ReflectUtil.poGetterMethodsMap(this.getClass());
        ColumnMatrix colMatrix = new AxisColumnMatrix(methodMap.size());
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            String fieldName = field.getName();
            if (!methodMap.containsKey(fieldName)){
                //如果该字段不是 getter setter字段，就不用再继续了
                continue;
            }

            String columnName = null;
            int type = Types.NULL;
            Object value = null;
            //如果是需要字段的值
            if (isNeedFieldValue){
                //先看字段的值是否为空,如果该字段值为空，就不用继续了
                try {
                    value = field.get(this);
                    if (value == null){
                        continue;
                    }
                } catch (IllegalAccessException e) {
                    continue;
                }
            }

            //判断是否主键注解
            if (field.isAnnotationPresent(Id.class)) {
                Id id = field.getAnnotation(Id.class);
                columnName = id.value();
                type = id.type();
            }
            if (field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                columnName = column.value();
                type = column.type();
            }
            //列名及为空的话就采用，默认操作
            if (StringUtils.isEmpty(columnName)){
                columnName = NameUtil.underscoreName(fieldName);
            }
            if (type == Types.NULL){
                type = SqlUtil.getSqlType(field.getType());
            }
            colMatrix.put(columnName,type,value);
        }
        return colMatrix;
    }



}

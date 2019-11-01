package com.abreaking.easyjpa.constraint;

import java.lang.annotation.*;

/**
 * 日期表名。
 * 指定了时间format格式，那么该表名是动态的。
 * 固定的表名，用于 实体-表 的映射
 * @author liwei_paas 
 * @date 2019/8/28
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DateTable {
    String prefix() default "";

    /**
     * 日期格式。不指定的话就是固定表名
     * @return
     */
    String format() default "";
}

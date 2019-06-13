package com.sitech.paas.inparam.jdbc;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

    String colName();
    int size();
}

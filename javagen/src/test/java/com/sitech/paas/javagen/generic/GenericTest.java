package com.sitech.paas.javagen.generic;


import org.junit.Test;

import java.lang.reflect.Method;

public class GenericTest {

    @Test
    public void test() throws ClassNotFoundException, NoSuchMethodException {
        String className = "com.sitech.paas.javagen.generic.GenericTest.UserService";
        String id = "101";
        String methodName = "getUser";
        String inputType = "java.lang.String";
        Class<?> aClass = Class.forName(className);
        Method method = aClass.getMethod(methodName, Class.forName(inputType));

    }

    public class User{
    }

    public interface UserService{
        User getUser(String id);
    }
}

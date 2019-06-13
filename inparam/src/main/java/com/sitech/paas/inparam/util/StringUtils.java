package com.sitech.paas.inparam.util;

public class StringUtils {

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

}

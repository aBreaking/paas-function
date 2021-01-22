package com.sitech.esb.jssh.util;

import com.sitech.esb.jssh.runner.JsshConfiguration;

import java.io.*;

/**
 * 
 * @author liwei_paas 
 * @date 2021/1/22
 */
public class ResourceUtil {

    public static String getJarConfigFilePath(String file){
        String path =  JsshConfiguration.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(0, path.lastIndexOf("/"));
        return path+File.separator+file;
    }

    public static String getClasspathFilePath(String file){
        return JsshConfiguration.class.getClassLoader().getResource(file).getPath();
    }

}

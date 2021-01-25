package com.sitech.esb.jbeat.util;

import com.sitech.esb.jbeat.runner.JBeatConfiguration;

import java.io.*;

/**
 * 
 * @author liwei_paas 
 * @date 2021/1/22
 */
public class ResourceUtil {

    public static String getJarConfigFilePath(String file){
        String path =  JBeatConfiguration.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(0, path.lastIndexOf("/"));
        return path+File.separator+file;
    }

    public static String getClasspathFilePath(String file){
        return JBeatConfiguration.class.getClassLoader().getResource(file).getPath();
    }

}

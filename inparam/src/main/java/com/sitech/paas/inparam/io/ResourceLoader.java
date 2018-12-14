package com.sitech.paas.inparam.io;

import java.io.File;
import java.net.URL;

public class ResourceLoader {
    public static Resource getRelativeResource(String location){
        URL resource = ResourceLoader.class.getClassLoader().getResource(location);
        return new RelativeResource(resource);
    }
    public static Resource getAbsoluteResource(String location){
        return new AbsoluteResource(location);
    }

    //获取到某个跟jar包在同一级目录下的文件
    public static Resource getResourceOnJarLocaiton(Class mainClass,String location){
        String path = mainClass.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(0, path.lastIndexOf("/"));
        String locationPath = path+File.separator+location;
        return getAbsoluteResource(locationPath);
    }

}

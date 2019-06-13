package com.sitech.paas.inparam.resource;

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
    public static Resource getResourceOnJarLocation(Class mainClass,String location){
        return new JarLocationResource(mainClass,location);
    }

}

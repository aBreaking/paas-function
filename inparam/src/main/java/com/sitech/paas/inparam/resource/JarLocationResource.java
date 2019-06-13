package com.sitech.paas.inparam.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * 跟jar包在同一目录下的资源
 */
public class JarLocationResource implements Resource{

    Class mainClass;
    String location;
    public JarLocationResource(Class mainClass,String location){
        this.location = location;
        this.mainClass = mainClass;
    }

    public InputStream getInputStream() throws IOException {
        String path = mainClass.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(0, path.lastIndexOf("/"));
        String locationPath = path+File.separator+location;
        AbsoluteResource resource = new AbsoluteResource(locationPath);
        return resource.getInputStream();
    }
}

package com.sitech.esb.mouse.io;

import java.net.URL;

public class ResourceLoader {
    public  Resource getRelativeResource(String location){
        URL resource = this.getClass().getClassLoader().getResource(location);
        return new RelativeResource(resource);
    }
    public Resource getAbsoluteResource(String location){
        return new AbsoluteResource(location);
    }
}

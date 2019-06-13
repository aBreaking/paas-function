package com.sitech.paas.inparam.resource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AbsoluteResource implements Resource {
    String file;
    public AbsoluteResource(String file){
        this.file = file;
    }

    public InputStream getInputStream() throws IOException {
        return new BufferedInputStream(new FileInputStream(file));
    }
}

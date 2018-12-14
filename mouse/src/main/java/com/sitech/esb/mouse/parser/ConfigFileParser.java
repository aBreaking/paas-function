package com.sitech.esb.mouse.parser;

import com.sitech.esb.mouse.io.Resource;

import java.io.IOException;
import java.util.Properties;

public class ConfigFileParser implements Parser<Properties> {
    @Override
    public Properties parse(Resource resource) throws IOException {
        Properties properties = new Properties();
        properties.load(resource.getInputStream());
        return properties;
    }
}

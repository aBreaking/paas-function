package com.sitech.esb.mouse.parser;

import com.sitech.esb.mouse.io.Resource;

import java.io.IOException;

public interface Parser<T> {
    /**
     * 将resource内容解析到某个对象里面去
     * @param resource
     */
    T parse(Resource resource) throws IOException;
}

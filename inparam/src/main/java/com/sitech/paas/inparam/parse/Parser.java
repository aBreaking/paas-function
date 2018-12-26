package com.sitech.paas.inparam.parse;

import com.sitech.paas.inparam.resource.Resource;

/**
 * 资源解析器，用于解析指定的资源
 */
public interface Parser {

    /**
     * 解析某个资源
     * @param resource
     */
    void parse(Resource resource);

}

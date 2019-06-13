package com.sitech.esb.jsoneye;

import java.io.IOException;
import java.util.Map;

/**
 * result的结果输出
 * @author liwei_paas
 * @date 2019/6/12
 */
public interface ResultOutput {
    void output(Map<String,Result> map) throws IOException;
}

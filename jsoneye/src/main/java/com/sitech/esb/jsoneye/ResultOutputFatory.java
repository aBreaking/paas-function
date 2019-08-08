package com.sitech.esb.jsoneye;

import com.sitech.esb.jsoneye.result.ResultOutputConsole;

/**
 * 指定输出的方法
 * @author liwei_paas
 * @date 2019/8/8
 */
public class ResultOutputFatory {

    public static ResultOutput get(String i){
        return new ResultOutputConsole();
    }
}

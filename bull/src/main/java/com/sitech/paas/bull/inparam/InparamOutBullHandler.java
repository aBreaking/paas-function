package com.sitech.paas.bull.inparam;

import com.sitech.paas.inparam.handler.AbstractFileWriteHandler;

/**
 * 输出到某个文件中去
 */
public class InparamOutBullHandler extends AbstractFileWriteHandler<String> {


    public InparamOutBullHandler(String outFile) {
        super(outFile);
    }

    //直接将原inparam输出
    @Override
    public String content(String str) {
        return str;
    }

    @Override
    public String title() {
        return null;
    }


}

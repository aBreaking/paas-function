package com.sitech.paas.bull.call;

import com.sitech.paas.inparam.handler.AbstractFileWriteHandler;

/**
 * 将调用结果写入csv文件中去
 */
public class EsbCallResultCsvHandler extends AbstractFileWriteHandler<EsbResult> {

    public EsbCallResultCsvHandler(String csvFile) {
        super(csvFile);
    }

    @Override
    public String title() {
        String title = "srv_name,conditions,call_begintime,phone_no,esb_retcode,retcode,retmsg";
        return title;
    }

    @Override
    public String content(EsbResult esbResult) {
        StringBuilder builder = new StringBuilder();
        builder.append(esbResult.getSrvName());
        builder.append(",");
        builder.append(esbResult.getConditions());
        builder.append(",");
        builder.append(esbResult.getCallBegintime());
        builder.append(",");
        builder.append(esbResult.getPhoneNo());
        builder.append(",");
        builder.append(esbResult.getEsbretcode());
        builder.append(",");
        builder.append(esbResult.getRetCode());
        builder.append(",");
        builder.append(esbResult.getRetmsg());
        return builder.toString();
    }


}

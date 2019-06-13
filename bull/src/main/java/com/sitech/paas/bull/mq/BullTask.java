package com.sitech.paas.bull.mq;

import com.sitech.paas.bull.call.EsbCaller;
import com.sitech.paas.bull.call.EsbResult;
import com.sitech.paas.bull.inparam.InparamOut;
import com.sitech.paas.inparam.handler.Handler;

import java.util.ArrayList;
import java.util.List;

public class BullTask implements Runnable {

    //esb
    EsbCaller esbCaller ;

    Handler handler;
    //每次调用多少次之后，才将服务保存下去
    int count ;

    public BullTask(EsbCaller esbCaller,Handler handler,int count ){
        this.esbCaller = esbCaller;
        this.handler = handler;
        this.count = count;
    }


    @Override
    public void run() {
        handler.preHand();
        List<Object> list = new ArrayList<>();
        InparamOut inparamOut = null;
        int i = 0;
        int total = 0;
        while ((inparamOut = Bull.queue.poll())!=null){
            //执行esb的调用，返回调用结果
            EsbResult result = esbCaller.call(inparamOut.getSrvName(), inparamOut.getJsonPin());
            result.setPhoneNo(inparamOut.getPhoneNo());
            result.setCallBegintime(inparamOut.getCallBeginTime());

            result.setConditions(inparamOut.getCondition());
            list.add(result);
            i++;
            if(i<count){
                continue;
            }
            handler.hand(list);
            total+=list.size();
            System.out.println("保存了"+total+"条数据");
            list.clear();
            i=0;
        }
        handler.hand(list);
        total+=list.size();
        System.out.println("保存了"+total+"条数据");

        handler.postHand();

    }
}

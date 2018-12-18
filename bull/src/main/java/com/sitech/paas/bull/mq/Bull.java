package com.sitech.paas.bull.mq;

import com.sitech.paas.bull.inparam.Condition;
import com.sitech.paas.bull.inparam.InparamOut;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Bull {
    /**
     * 使用队列，将inparam文件解析出来的需要被调用的srv、报文，添加到队列中去。供esb调用
     */
    public final static Queue<InparamOut> queue = new ConcurrentLinkedDeque();

    public final static List<Condition> CONDITION_PARAMS = new ArrayList<>();
}

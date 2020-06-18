package com.sitech.paas.javagen.brm;

import com.sitech.ac.rdc.re.api.common.service.CommonServiceProcessor;
import com.sitech.ac.rdc.re.api.common.service.InitService;
import com.sitech.paas.javagen.facade.Exposable;

/**
 * 业务规则引擎 的调用接口
 * 它应该是单例的，仅当用到它才能实例化
 *
 * //FIXME 还有一点，brm属于外部接入系统，需要考虑外部接入的统一接口形式 . 并且外部接入json有其他的标准，应该有指定json解析的格式
 * @author liwei_paas
 * @date 2020/3/16
 */
public class BreCaller implements Exposable {

    //单例
    private static volatile BreCaller instance;

    /**
     * 双重校验获取该对象的实例对象
     * @return
     */
    public static BreCaller getInstance(){
        if (instance==null){
            synchronized (instance){
                if(instance==null){
                    instance = new BreCaller();
                }
            }
        }
        return instance;
    }

    private BreCaller(){
        //第一次实例化时，先加载brm里面的配置
        InitService service = new InitService();
        service.init();
    }

    public String call(String breServiceName,String param){
        CommonServiceProcessor commonServiceProcessor = new CommonServiceProcessor();
        Object result = commonServiceProcessor.executeBusGroupScript(breServiceName, param, null);
        if (result==null){
            return null;
        }else{
            return result.toString();
        }
    }

    @Override
    public String howInstance() {
        return "BreCaller.getInstance()";
    }
}

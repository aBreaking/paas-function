package com.sitech.esb.sap;

import com.sitech.esb.autoservice.AutoSrvImportUtils;
import jxl.Workbook;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务同步的操作
 * 封装liuchong之前的操作
 * @author liwei_paas 
 * @date 2020/4/7
 */
public class SapInvoker implements Callable {

    protected Workbook workbook;
    protected EsbPoolConfig config;

    private String invokeKey;
    private SapLogger logger;
    /**
     * 存储服务同步的返回信息
     */
    public static final Map<String,String> INVOKE_STATE = LruCache.syncInstance(128);

    /**
     * 单线程池，用于异步任务的执行
     * 提交到这里的服务同步的任务，都将会放到线程池的队列里面等候执行
     */
    public static final ExecutorService SAP_TASK_POOL = Executors.newSingleThreadExecutor();

    /**
     * 同步调用，会阻塞。
     * @return 返回服务同步完成后的结果
     * @throws Exception
     */
    public String call() throws Exception{
        if (config==null){
            throw new SapException("no esb pool configuration data,please check the name of poolkey is correct");
        }
        AutoSrvImportUtils liuc = new AutoSrvImportUtils();
        if (logger==null){
            logger = SapLogger.getLogger(SapLogger.class.getSimpleName());
        }
        String ret;
        try{
            logger.info("begin esb service auto import,EsbPoolConfig is : "+config);
            ret = liuc.autoSrvImp(workbook, config.getPoolKey(), config.getHbXml(), config.getPoolAddress());
            logger.info("service import completed:"+ret);
        }catch (Exception e){
            ret = e.getMessage();
            logger.error("service import failed",e);
        }
        //将执行结果放到内存中去
        if (invokeKey!=null){
            INVOKE_STATE.put(invokeKey,ret);
        }
        return ret;
    }

    /**
     * 异步调用，先返回一个可用于查询的key，后台自动进行服务同步操作
     * 同步后的结果数据会先保存在缓存中，通过返回的key可进行查询
     * @return 同步结果查询key
     */
    public String asyncInvoke(){
        final String invokeKey = generateInvokeKey(config.getPoolKey());
        this.setInvokeKey(invokeKey);
        this.setLogger(SapLogger.getLogger(invokeKey));
        logger.info("starts asynchronous service import,the invokeKey of current operation is "+invokeKey);
        /**
         * AutoHibernateSessionFactory#setConfigFile(java.lang.String)这个方法应该是线程冲突了，liuc写了个鸡儿
         * 他为什么要将AutoHibernateSessionFactory 创建session方法整成全局的？
         *
         * 一个解决方案只有考虑通过单线程池的方式来提交任务了，线程池数量固定1，任务队列衍生，将当前SapInvoker ，submit(this)
         */

        SAP_TASK_POOL.submit(this);
        return invokeKey;
    }

    /**
     * 通过计算获取一个key。指定前缀+时间戳来定义该key
     * 该key用于查询保存在缓存中服务同步的返回信息，主要用于异步的查询
     * @param prefix 指定前缀
     * @return
     */
    protected String generateInvokeKey(String prefix){
        long l = System.currentTimeMillis();
        String suffix = String.valueOf(l/1000);
        return prefix+"-"+suffix;
    }

    /**
     * 一个LRU缓存的简单的实现
     * 需要注意：默认的构造方法是线程不安全的，你可以使用syncInstance获取到一个线程安全的缓存对象
     * @author liwei_paas
     * @date 2020/4/8
     */
    private static class LruCache extends LinkedHashMap{
        
        private int maxSize;
        
        public LruCache(int maxSize) {
            this.maxSize = maxSize;
        }

        /**
         * 直接实例化的缓存是线程不安全的，所以建议通过该方法获取线程安全的对象
         * @param maxSize
         * @return
         */
        public static Map syncInstance(int maxSize){
            Map cache = new LruCache(maxSize);
            return Collections.synchronizedMap((Map<Object, Object>) cache);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size()>maxSize;
        }
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public EsbPoolConfig getConfig() {
        return config;
    }

    public void setConfig(EsbPoolConfig config) {
        this.config = config;
    }

    public String getInvokeKey() {
        return invokeKey;
    }

    public void setInvokeKey(String invokeKey) {
        this.invokeKey = invokeKey;
    }

    public SapLogger getLogger() {
        return logger;
    }

    public void setLogger(SapLogger logger) {
        this.logger = logger;
    }
}

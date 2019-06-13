package com.sitech.pass.tiger;

import com.sitech.hsf.common.Constants;
import com.sitech.hsf.common.URL;
import com.sitech.hsf.config.ApplicationConfig;
import com.sitech.hsf.config.ReferenceConfig;
import com.sitech.hsf.config.RegistryConfig;
import com.sitech.hsf.registry.RegistryService;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class TigerRunner {

    static Logger logger = LoggerFactory.getLogger(TigerRunner.class);

    public static String applicationName  ;
    public static String address ;
    public static String protocol;

    public static void main(String args[]) throws Exception {
        applicationName = args!=null&&args.length>0?args[0]:"crm6.0";
        address      = args!=null&&args.length>1?args[1]:"10.113.161.103:8541";
        /*applicationName = args!=null&&args.length>0?args[0]:"yyy";
        address      = args!=null&&args.length>1?args[1]:"172.21.11.64:2180";*/
        protocol     = args!=null&&args.length>2?args[2]:"zookeeper";

        long first = System.currentTimeMillis();
        zkclient();
        long second = System.currentTimeMillis();
        System.err.println("zkclient总用时："+(second-first));

        if (args!=null&&args.length>3){
            hsfAdmin();
        }

    }

    public static List<String> ipList = new ArrayList<>();

    public static void zkclient() throws  UnsupportedEncodingException {
        Set<String> ipSetTemp = new HashSet<>();
        String dubboNode = "/dubbo";

        long aTime = System.currentTimeMillis();
        ZkClient zkClient = new ZkClient(new ZkConnection(address), 10000);
        zkClient.addAuthInfo("digest", ("hsf"+":"+"Stq@2018").getBytes());
        System.out.println("开始取/duboo下面所有的数据");
        List<String> serviceList = zkClient.getChildren(dubboNode);
        long bTime = System.currentTimeMillis();
        System.out.println("取数时间："+(bTime-aTime)+",取得"+serviceList.size()+"条数据");
        System.out.println("开始计算所有的ip集合");
        for (String service : serviceList){
            String node = dubboNode+"/"+service+"/providers";
            List<String> providerUrlList = zkClient.getChildren(node);
            for (String providerUrlStr : providerUrlList){
                String ip = parse2Ip(URLDecoder.decode(providerUrlStr,"UTF-8"));
                if(!ipSetTemp.contains(ip)){
                    ipSetTemp.add(ip);
                }
            }
        }
        ipList.clear();
        ipList.addAll(ipSetTemp);
        long cTime = System.currentTimeMillis();
        System.out.println("解析ip用时："+(cTime-bTime)+",ip set数量"+ipList.size());
    }

    private static String parse2Ip(String url){
        int i = url.indexOf("://");
        url = url.substring(i+3,url.length());
        String[] split = url.split("/");
        return split[0];
    }
    /**
     * zk注册中心的信息
     * @return
     */
    public static RegistryService getRegistryService() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName(applicationName);

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(address);
        registry.setProtocol(protocol);

        ReferenceConfig<RegistryService> reference = new ReferenceConfig(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(RegistryService.class);
        RegistryService registryService = reference.get();
        return registryService;
    }


    /**
     * 计算拿到cache的时间
     */
    public static void hsfAdmin() throws Exception {
        long aTime= System.currentTimeMillis();
        RegistryServerSync sync = new RegistryServerSync();
        sync.init();
        ConcurrentMap<String, ConcurrentMap<String, Map<Long, URL>>> cache = sync.getCache();

        List<String> ipListTemp = new ArrayList<>();
        ConcurrentMap<String, Map<Long, URL>> providers = cache.get(Constants.PROVIDERS_CATEGORY);
        long bTime= System.currentTimeMillis();
        System.out.println(bTime-aTime);
        for (String key : providers.keySet()){
            Map<Long, URL> urlMap = providers.get(key);
            for(Long id : urlMap.keySet()){
                ipListTemp.add(urlMap.get(id).getIp());
            }
        }
        ipList.clear();
        ipList.addAll(ipListTemp);
        System.out.println("hsdAdmin的方法，获取到ipList数量为："+ipList.size());

    }
}

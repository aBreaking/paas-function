package com.sitech.paas.hadoop;

import com.sitech.paas.crawler.WebCrawler;
import com.sitech.paas.parse.HtmlFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * habse region server 数据获取
 * @author liwei_paas
 * @date 2019/10/15
 */
public class HBaseRegionServerMonitor {

    static Logger logger = LoggerFactory.getLogger(HBaseRegionServerMonitor.class);

    private static final String HBASE_RS_REQUEST_STATUS_URI = "/rs-status#regionRequestStats";

    /**
     * 到tr层的选择器
     */
    private static final String HBASE_RS_TR_SELECTOR = "#tab_regionRequestStats > table > tbody > tr";

    /**
     * 所有hhbase主机地址 IP:port
     */
    private List<String> rsAddrList;
    /**
     * 因为region server的页面都是hbase的子页面，所以，用户名密码都是一样的，即hbase的页面的认证
     */
    private String user;
    private String password;

    public HBaseRegionServerMonitor(List<String> rsAddrList){
        this.rsAddrList = rsAddrList;
    }

    public HBaseRegionServerMonitor(List<String> rsAddrList,String user,String password){
        this(rsAddrList);
        this.user = user;
        this.password = password;
    }


    public Collection<RegionStat> hear(){
        int addrSize = rsAddrList.size();
        ExecutorService threadPool = Executors.newFixedThreadPool(addrSize);
        CountDownLatch countDownLatch = new CountDownLatch(addrSize);
        final ConcurrentHashMap<String,RegionStat> nameRegionMap = new ConcurrentHashMap<>();

        for (int i = 0; i < addrSize; i++) {
            final String url = "http://"+rsAddrList.get(i)+HBASE_RS_REQUEST_STATUS_URI;
            threadPool.submit(()->{
                try{
                    WebCrawler webCrawler = user==null?new WebCrawler(url):new WebCrawler(url, user, password);
                    Html html = webCrawler.html();
                    parse(html,nameRegionMap);
                }catch (Exception e){
                    logger.error("爬取"+url+"地址下得hbase region server数据失败",e);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("未知中断",e);
        }
        return nameRegionMap.values();
    }

    /**
     * 解析html页面，找出region的读写量
     * @param html
     * @return
     */
    protected void parse(Html html,Map<String,RegionStat> container){
        HtmlFinder htmlFinder = new HtmlFinder(html);
        Selectable trs = htmlFinder.findSelectable(HBASE_RS_TR_SELECTOR);
        List<Selectable> nodes = trs.nodes();
        for (int i=1;i<nodes.size();i++){
            Selectable tr = nodes.get(i);
            Selectable td = tr.xpath("//td");
            if (td.toString().equals("null")){
                continue;
            }
            List<Selectable> tdNodes = td.nodes();
            String regionNameStr = HtmlFinder.findText(tdNodes.get(0));
            String readCountStr = HtmlFinder.findText(tdNodes.get(1));
            String writeCountStr = HtmlFinder.findText(tdNodes.get(2));

            String regionName = regionNameStr.substring(0,regionNameStr.indexOf(","));
            int readCount = Integer.parseInt(readCountStr);
            int writeCount = Integer.parseInt(writeCountStr);
            RegionStat regionStat = new RegionStat();
            if (container.containsKey(regionName)){
                regionStat = container.get(regionName);
                readCount += regionStat.getReadCount();
                writeCount += regionStat.getWriteCount();
            }
            regionStat.setRegionName(regionName);
            regionStat.setReadCount(readCount);
            regionStat.setWriteCount(writeCount);

            container.put(regionName,regionStat);
        }
    }


    public class RegionStat{
        String regionName;
        int readCount = 0;
        int writeCount = 0;

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public int getWriteCount() {
            return writeCount;
        }

        public void setWriteCount(int writeCount) {
            this.writeCount = writeCount;
        }
    }


}

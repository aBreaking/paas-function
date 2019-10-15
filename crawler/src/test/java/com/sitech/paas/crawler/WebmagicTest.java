package com.sitech.paas.crawler;

import com.sitech.paas.hadoop.HBaseRegionServerMonitor;
import com.sitech.paas.parse.HtmlFinder;
import org.jsoup.nodes.Element;
import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * webmagic test
 * @author liwei_paas
 * @date 2019/10/14
 */
public class WebmagicTest {

    private String url = "https://www.abreaking.com";
    private String name = "";
    private String pwd = "";

    @Test
    public void test04(){
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"172.21.11.66:14004","172.21.11.67:14004");
        String usr = "e3base";
        String pwd = "xbase";
        HBaseRegionServerMonitor monitor = new HBaseRegionServerMonitor(list,usr,pwd);
        Collection<HBaseRegionServerMonitor.RegionStat> collection = monitor.hear();
        System.out.println(collection);
    }


    @Test
    public void test03(){
        String url = "http://172.21.11.67:14004/rs-status#regionRequestStats";
        String usr = "e3base";
        String pwd = "xbase";
        WebCrawler crawler = new WebCrawler(url,usr,pwd);
        Html html = crawler.html();
        HtmlFinder finder = new HtmlFinder(html);
        String selector = "#tab_regionRequestStats > table > tbody > tr";
        Selectable tr = finder.findSelectable(selector);
        List<Selectable> nodes = tr.nodes();
        Selectable td = nodes.get(1);
        Selectable firstChild = td.xpath("//td");
        String regionName = HtmlFinder.findText(firstChild.nodes().get(0));
        Selectable readCount = firstChild.nodes().get(1);
        System.out.println();
    }


    @Test
    public void test01(){
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        Request request = new Request(url);
        Task task = Site.me().setCharset("utf-8").toTask();
        Page page = httpClientDownloader.download(request, task);
        Html html = page.getHtml();
        System.out.println(html);
        Selectable selectable = html.$("#main #home h1");
        List<Selectable> nodes = selectable.nodes();
        Selectable xpath = nodes.get(0).xpath("allText()");
        System.out.println();
    }


    @Test
    public void test02(){
        String gitUrl = "http://172.21.11.73:14002/master-status?filter=all";
        String usr = "e3base";
        String pwd = "xbase";
        WebCrawler webCrawler = new WebCrawler(gitUrl, usr, pwd);
        Html html = webCrawler.html();
        System.out.println(html);

        WebCrawler crawler = new WebCrawler("http://blog.abreaking.com");
        System.out.println(crawler.html());

    }


}

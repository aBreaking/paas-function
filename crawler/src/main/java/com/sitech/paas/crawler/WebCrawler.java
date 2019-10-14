package com.sitech.paas.crawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;

/**
 * 爬虫工具，专爬页面
 * @author liwei_paas
 * @date 2019/10/14
 */
public class WebCrawler {

    private HttpClientDownloader httpClientDownloader;
    private Request request;
    private Task task;

    private static final String HTML_CHARSET = "utf-8";
    private static final int CONNECT_TIME_OUT = 3000;
    private static final int RETRY_TIMES = 3;

    public WebCrawler(String url){
        this(url,defaultTask().task());
    }

    public WebCrawler(String url,String user,String password){
        this(url,defaultTask().setAuth(user,password).task());
    }

    public WebCrawler(String url,Task task){
        this.httpClientDownloader = new HttpClientDownloader();
        request = new Request(url);
        this.task = task;
    }


    public Page page(){
        return httpClientDownloader.download(request,task);
    }

    public Html html(){
        return page().getHtml();
    }

    private static TaskBuilder defaultTask(){
        TaskBuilder taskBuilder = new TaskBuilder();
        taskBuilder.setTimeOut(CONNECT_TIME_OUT)
                .setRetryTimes(RETRY_TIMES).setCharset(HTML_CHARSET);
        return taskBuilder;
    }


    static class TaskBuilder{
        private Site site = Site.me();

        public Task task(){
            return site.toTask();
        }

        @Deprecated
        public void setSite(){
            this.site = site;
        }

        public TaskBuilder setAuth(String user,String pwd){
            site.setUsernamePassword(user,pwd);
            return this;
        }

        public TaskBuilder setTimeOut(int timeOut){
            site.setTimeOut(timeOut);
            return this;
        }

        public TaskBuilder setRetryTimes(int retryTimes){
            site.setRetryTimes(retryTimes);
            return this;
        }

        public TaskBuilder setCharset(String charset){
            site.setCharset("utf-8");
            return this;
        }
    }
}

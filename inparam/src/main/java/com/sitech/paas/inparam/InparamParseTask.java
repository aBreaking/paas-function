package com.sitech.paas.inparam;

import com.sitech.paas.inparam.handler.Handler;
import com.sitech.paas.inparam.io.Resource;
import com.sitech.paas.inparam.io.ResourceLoader;
import com.sitech.paas.inparam.parse.FileParser;
import com.sitech.paas.inparam.parse.Parser;
import com.sitech.paas.inparam.parse.ZipParser;
import com.sitech.paas.inparam.resovler.Resolver;

import java.io.File;
import java.util.zip.ZipFile;

public class InparamParseTask implements Runnable{

    String inparamFileDir ;  //inparam.log文件所在目录，会解析该目录下所有的入参文件
    int count = 1000 ;        //每次解析多少数据
    private Handler handler;
    private Resolver resolver;
    String fileName; //如果有指定的文件
    public InparamParseTask(String inparamFileDir,Handler handler,Resolver resolver){
        this.inparamFileDir = inparamFileDir;
        this.resolver = resolver;
        this.handler = handler;
    }
    public InparamParseTask(String inparamFileDir,Handler handler,Resolver resolver,int count) {
        this(inparamFileDir,handler,resolver);
        this.count = count;
    }
    public InparamParseTask(String inparamFileDir,Handler handler,Resolver resolver,String fileName){
        this(inparamFileDir,handler,resolver);
        this.fileName = fileName;
    }
    public void run() {
        handler.preHand();
        try {
            File fileDir = new File(inparamFileDir);
            if(fileDir.isDirectory()){
                String[] list = fileDir.list();
                for(String s:list){
                    if(!s.startsWith("inParam.log")){
                        continue;
                    }
                    String location = inparamFileDir+File.separator+s;
                    System.out.println("开始解析文件:"+location);
                    Resource resource= ResourceLoader.getAbsoluteResource(location);
                    //默认是文件解析器
                    Parser parser =  new FileParser(resolver,handler,count);
                    if(s.endsWith("zip")){
                        //如果是zip包，使用zip解析器
                        parser = new ZipParser(new ZipFile(location),inparamFileDir,parser);
                    }
                    //解析并保存到数据库中去
                    parser.parse(resource);
                    System.out.println(location+"解析保存完毕，数据已保存");
                }
            }
            Thread.sleep(1000);
        }catch(InterruptedException ie) {
            throw new RuntimeException("Interrupted",ie);
        }catch(Exception e) {
            System.out.println("出错啦"+e.getCause());
            e.printStackTrace();
        }finally {
            handler.postHand();
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

package com.sitech.paas.inparam.parse;
import com.sitech.paas.inparam.handler.Handler;
import com.sitech.paas.inparam.resource.Resource;
import com.sitech.paas.inparam.resovler.Resolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FileParser<T> implements Parser {
    private List<T> dataList = new ArrayList<T>();
    private int count; //每次读存多少数据

    private Resolver<T> resolver;
    private Handler handler;
    public FileParser(Resolver resolver, Handler handler, int count) {
        this.handler = handler;
        this.resolver = resolver;
        this.count = count;
    }
    public void parse(Resource resource) {
        InputStream inputStream = null;
        BufferedReader reader = null;
        int totalSize = 0;
        try {
            inputStream = resource.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String inparam = "";
            int index = 0 ;

            while(true){
                inparam = reader.readLine();
                if(inparam==null){
                    break;
                }
                //如果是子服务,直接略过
                if(inparam.startsWith("\t")){
                    continue;
                }

                //这里开始处理
                //怎么去解析inparam
                T resolveResult = resolver.resolve(inparam);
                if(resolveResult == null){  //如果没有解析的结果，那么就不要该条记录
                    continue;
                }
                index++; //用这个来计数，count条数据就提交一次
                dataList.add(resolveResult);

                if(index==count){

                    //每解析了n条数据，就保存
                    handler.hand(dataList);

                    totalSize+=dataList.size();
                    dataList.clear();

                    index = 0 ;
                    System.out.println("保存了"+totalSize+"条数据");
                }

            }
            if(!dataList.isEmpty()) {

                handler.hand(dataList);

                totalSize+=dataList.size();
                dataList.clear();

                System.out.println("保存了"+totalSize+"条数据");
            }

        } catch (IOException e) {
            //指定路径有误.
            System.out.println("指定参数路径有误");
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("该inparam文件总共保存了"+totalSize+"条数据");
            try {
                reader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

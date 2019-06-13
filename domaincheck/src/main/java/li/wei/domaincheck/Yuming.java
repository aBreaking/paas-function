package li.wei.domaincheck;

import li.wei.domaincheck.resource.Resource;
import li.wei.domaincheck.resource.ResourceLoader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Yuming {

    public final static String url = "https://checkapi.aliyun.com/check/checkdomain";
    public final static String all_domain_file = "abcde.txt";

    public final static StringBuffer stringBuffer = new StringBuffer();

    //aganl
    public static void main(String args[]) throws Exception {
        Resource resource = ResourceLoader.getRelativeResource(all_domain_file);
        InputStreamReader reader = new InputStreamReader(resource.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(reader);

        int maxPoolSize = 8;
        ExecutorService pool = Executors.newFixedThreadPool(maxPoolSize);

        bufferedReader.readLine(); //第一行已经完了，可以不用读了

        for (int i=0;i<5;i++){

            pool.submit(()->{
                try {
                    String line = bufferedReader.readLine();
                    String[] split = "".split(",");
                    for (String s : split){
                        if (s.trim()!=null){
                            if (DomainCheck.isDomainUsable(s.trim())){
                                System.out.println(s);
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }




}

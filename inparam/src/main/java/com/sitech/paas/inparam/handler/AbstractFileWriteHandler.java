package com.sitech.paas.inparam.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public abstract class AbstractFileWriteHandler<T> implements Handler<T> {
    /**
     * 将符合条件的inparam输出到这个文件中去
     */
    String outFile ;

    public AbstractFileWriteHandler(String outFile){
        this.outFile = outFile;
    }
    PrintWriter printWriter;
    public void preHand() {
        System.out.println("准备写入文件："+outFile);
        File file = new File(this.outFile);
        if(file.exists()){
            file.delete();
            System.out.println(outFile+"存在，已经被删除了");
        }
        try {
            file.createNewFile();
            System.out.println("新创建了文件："+outFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try {
            this.printWriter = new PrintWriter(new FileWriter(file,true));
            if(null != title()){
                //可能会有标题呢
                printWriter.println(title());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hand(List<T> list){
        for (T t : list){
            printWriter.println(content(t));
        }
    }

    public void postHand() {
        try {
            if(printWriter!=null){
                printWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract String content(T t);
    public abstract String title();
}

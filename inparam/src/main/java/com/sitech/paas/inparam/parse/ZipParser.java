package com.sitech.paas.inparam.parse;

import com.sitech.paas.inparam.resource.Resource;
import com.sitech.paas.inparam.resource.ResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * zip 文件解析器，
 * 使用一个包装模式，
 * 会先将zip文件解压，而后通过其他parser解析加压后的文件
 */
public class ZipParser<T> implements Parser {
    private String dest;
    private ZipFile zipFile;
    Parser parser;
    public ZipParser(ZipFile zipFile, String dest,Parser parser){
        this.zipFile = zipFile;
        this.dest = dest;
        this.parser = parser;
    }

    public void parse(Resource resource){
        //解压文件
        File file = null;
        try {
            //先将文件解压到当前目录
            file = decompress(zipFile, dest);
            System.out.println("解压了文件"+zipFile.getName()+"-->"+file.getAbsolutePath());
            resource= ResourceLoader.getAbsoluteResource(file.getAbsolutePath());
            //使用其他解析器进行解析，一般是fileParser进行解析
            parser.parse(resource);
        } catch (Exception e) {
            System.out.println("解压文件失败");
            e.printStackTrace();
        }finally {
            if(file.exists()){
                file.delete();
                System.out.println(file.getAbsolutePath()+"文件解析保存完毕，被删除了");
            }
        }
    }

    /**
     * 将zip文件解压
     * @param zf
     * @param dest
     * @return
     * @throws Exception
     */
    private static File decompress(ZipFile zf, String dest) throws Exception {

        Enumeration entries = zf.entries();
        ZipEntry entry = null;
        while (entries.hasMoreElements()) {
            entry = (ZipEntry) entries.nextElement();

            // 表示文件

            File f = new File(dest + File.separator + entry.getName());

            f.createNewFile();

            // 将压缩文件内容写入到这个文件中

            InputStream is = zf.getInputStream(entry);


            FileOutputStream fos = new FileOutputStream(f);

            int count;

            byte[] buf = new byte[8192];

            while ((count = is.read(buf)) != -1) {

                fos.write(buf, 0, count);

            }

            is.close();

            fos.close();
            return f;
        }

        return null;

    }
}

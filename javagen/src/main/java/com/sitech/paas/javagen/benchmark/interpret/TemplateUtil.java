package com.sitech.paas.javagen.benchmark.interpret;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 生成对应模板的代码
 * @author liwei_paas
 * @date 2020/3/24
 */
public class TemplateUtil {

    /**
     * 生成代码
     *
     * @return 数据
     */
    public static void generatorCode(OutputStream outputStream, Map<String,Object> map,List<String> templates)
    {
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        // 生成代码
        generator(zip,map,templates);
        IOUtils.closeQuietly(zip);
    }

    /**
     * 生成代码
     */
    public static void generator(ZipOutputStream zip,Map<String,Object> map,List<String> templates){

        VelocityInitializer.initVelocity();
        VelocityContext context = new VelocityContext();
        context.put("author", "liwei_paas");
        map.forEach((k,v)->context.put(k,v));
        String filePrefix = "main/java/com/sitech/paas/javagen/demo/";
        for (String template : templates){
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "utf-8");
            tpl.merge(context, sw);
            try{
                String tname = template.substring(template.indexOf("/") + 1, template.length());
                if (tname.endsWith(".vm")){
                    tname = tname.substring(0,tname.lastIndexOf("."));
                }
                String filename = filePrefix + tname;
                // 添加到zip
                zip.putNextEntry(new ZipEntry(filename));
                IOUtils.write(sw.toString(), zip, "utf-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

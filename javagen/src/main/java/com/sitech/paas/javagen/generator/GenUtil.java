package com.sitech.paas.javagen.generator;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成
 *
 */
public class GenUtil {

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

        //VelocityContext context = GenUtils.getVelocityContext(table); ->
        // java对象数据传递到模板文件vm
        VelocityContext context = new VelocityContext();
        context.put("author", "liwei_paas");
        map.forEach((k,v)->context.put(k,v));

        // 获取模板列表
        //List<String> templates = getTemplates();
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

    public static List<String> getTemplates()
    {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/Main.java.vm");
        templates.add("vm/util/CommonUtil.java.vm");
        templates.add("vm/util/ServiceCaller.java.vm");
        return templates;
    }
}

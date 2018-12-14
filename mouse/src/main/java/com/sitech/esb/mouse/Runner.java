package com.sitech.esb.mouse;

import com.sitech.esb.mouse.filter.DefaultFilter;
import com.sitech.esb.mouse.filter.Filter;
import com.sitech.esb.mouse.filter.FilterFactory;
import com.sitech.esb.mouse.filter.PqFilter;
import com.sitech.esb.mouse.io.Resource;
import com.sitech.esb.mouse.io.ResourceLoader;
import com.sitech.esb.mouse.jdbc.JdbcHelper;
import com.sitech.esb.mouse.jdbc.JdbcOperator;
import com.sitech.esb.mouse.parser.ConfigFileParser;
import com.sitech.esb.mouse.parser.HostFileParser;
import com.sitech.esb.mouse.parser.Parser;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

public class Runner {
    static ResourceLoader loader = new ResourceLoader();

    static Parser parser ;

    public static void main(String args[]) throws Exception {
        /*String dataFile = "test_gc.txt";
        String mouseFile = "mouse.properties";*/
        String mouseFile  = args[0];
        String dataFile= args[1];
        Resource monitorResource = loader.getAbsoluteResource(dataFile); //数据监控的文件
        Resource mouseResource = loader.getAbsoluteResource(mouseFile); //系统配置文件

        //解析系统配置文件
        parser = new ConfigFileParser();
        Properties properties = (Properties) parser.parse(mouseResource);
        //是否需要过滤器,配置文件中指定或者命令中第三个参数
        String filterStr = args.length>2?args[2]:properties.getProperty("filter");
        Filter filter = FilterFactory.getFilter(filterStr);
        //解析该文件
        parser = new HostFileParser(filter);
        List<String> values = (List<String>)parser.parse(monitorResource);

        //解析完毕后保存到数据库中去。
        Connection connection = null;
        try{
            connection = JdbcHelper.getConnection(properties);
            //表名，配置文件或者命令的第4个参数来获取
            String tableName = args.length>3?args[3]:properties.getProperty("table.name");
            if(tableName==null){
                System.out.println("请指定下表名");
                throw new IllegalArgumentException("表名为空");
            }
            //列名，配置文件或者命令中第五个参数及以后
            String[] cols = args.length>4?cutArray(args,4):properties.getProperty("table.column").split(",");
            JdbcOperator jdbcOperator = new JdbcOperator(connection,tableName,cols);

            if(!jdbcOperator.isTableExist()){
                jdbcOperator.createTable();
            }
            jdbcOperator.insertBatch(values);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(connection!=null){
                JdbcHelper.releaseConnection(connection);
            }
        }
    }

    /**
     * 截取字符串，从第size个位置之后，也就说，前面size个元素都不要了
     * @param array
     * @param size
     * @return
     */
    private static String[] cutArray(String[] array,int size){
        String[] cols = new String[array.length-size];
        for (int i = size; i < array.length; i++) {
            cols[i-size] = array[i];
        }
        for (String s : cols){
            System.out.println(s);
        }
        return cols;
    }
}

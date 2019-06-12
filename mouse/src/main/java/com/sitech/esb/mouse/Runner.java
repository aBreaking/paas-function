package com.sitech.esb.mouse;

import com.sitech.esb.mouse.filter.Filter;
import com.sitech.esb.mouse.filter.FilterFactory;
import com.sitech.esb.mouse.jdbc.JdbcHelper;
import com.sitech.esb.mouse.jdbc.JdbcOperator;
import com.sitech.esb.mouse.parser.HostFileParser;
import com.sitech.esb.mouse.parser.Parser;
import com.sitech.esb.mouse.resource.Resource;
import com.sitech.esb.mouse.resource.ResourceLoader;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

/**
 * region分裂：java -jar . . . HDP_HBASE_UTR_STATUS_TEMP
 */
public class Runner {

    static String  COMMAND[] ;
    /**如果有在配置文件中配置，那么
     * arg0: mouse配置文件
     * arg1:数据文件
     * arg2: 数据过滤器
     * arg3: 表名
     * arg4: 外键
     * arg5: 第5个参数及以后，为数据文件中每个监控指标的字段名，也是保存到数据库中的列名
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        //hbase 租户告警的参数
        /*if(args==null){
            args = new String[]{"D:\\workspace\\demo\\mouse\\src\\main\\resources\\mouse.properties",
                    "D:\\workspace\\demo\\mouse\\src\\main\\resources\\hbase-ur-data.txt",
                    ".","hdp_hbase_utr_status_temp",
                    "hadoop_id=10","user_name:32","table_name:128","CURRENT_REGION_NUM:int"};
        }*/
        COMMAND = args;
        String dataFile = "checkIo.txt";
        String mouseFile = "mouse.properties";
        Resource configResource = isDefault(0)?ResourceLoader.getResourceOnJarLocation(Runner.class,mouseFile):
                ResourceLoader.getAbsoluteResource(args[0]);
        Resource mouseDataResource = isDefault(1)?ResourceLoader.getResourceOnJarLocation(Runner.class,dataFile):
                ResourceLoader.getAbsoluteResource(args[1]);
        Properties properties = new Properties();
        properties.load(configResource.getInputStream());
        //数据过滤器
        String filterStr = isDefault(2)?properties.getProperty("filter"):args[2];
        //表名
        String tableName = isDefault(3)?properties.getProperty("table.name"):args[3];
        //外键
        String foreignKey =  isDefault(4)?properties.getProperty("foreign.key"):args[4];

        //表字段及字段大小
        String[] dataCols = args.length>5?cutArray(args,5):properties.getProperty("table.column").split(",");

        //是否需要过滤器,配置文件中指定或者命令中第三个参数
        Filter filter = FilterFactory.getFilter(filterStr);

        //解析该文件
        Parser parser = new HostFileParser(filter,dataCols.length-1);
        List<String> insertSqlValues = (List<String>)parser.parse(mouseDataResource);

        //解析完毕后保存到数据库中去。
        Connection connection = null;
        try{
            connection = JdbcHelper.getConnection(properties);
            JdbcOperator jdbcOperator = new JdbcOperator(connection);

            //表名，配置文件或者命令的第4个参数来获取
            if(tableName==null){
                System.out.println("请指定下表名");
                throw new IllegalArgumentException("表名为空");
            }
            //外键
            if(foreignKey!=null){
                String[] split = foreignKey.split("=");
                String foreignName = split[0];
                String foreignValue = split.length>1?split[1]:null;
                jdbcOperator.setForeignName(foreignName);
                jdbcOperator.setForeignValue(foreignValue);
            }
            //列名，配置文件或者命令中第五个参数及以后


            jdbcOperator.setTableName(tableName);

            if(!jdbcOperator.isTableExist()){
                jdbcOperator.createTable(dataCols);
            }

            jdbcOperator.insertBatch(insertSqlValues);
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

    private static boolean isDefault(int size){
        if(COMMAND.length<=size){
            return true;
        }
        String defaultValue = ".";
        if(COMMAND.length>size){
            if(COMMAND[size].equals(defaultValue)){
                return true;
            }
            if(COMMAND[size].equals("default")){
                return true;
            }
            if(COMMAND[size].equals("null")){
                return true;
            }
        }
        return false;
    }
}

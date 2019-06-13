package com.sitech.paas.inparam.inparam;

import com.sitech.paas.inparam.jdbc.JdbcOperate;
import com.sitech.paas.inparam.util.StringUtils;
import com.sitech.paas.inparam.handler.Handler;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 将批量解析的Inparam保存到数据库里面去
 */
public class InparamHandler implements Handler<Inparam> {

    JdbcOperate dao;
    private Properties properties;
    private String specifiedTableName;
    private String tempTableName;
    public InparamHandler(Properties properties,String specifiedTableName){
        this(properties);
        this.specifiedTableName = specifiedTableName;
    }
    public InparamHandler(Properties properties){
        this.properties = properties;
        this.dao = new JdbcOperate();
    }

    /**
     * 初始化数据库连接
     * 创建表、索引等方法
     */
    public void preHand() {

        dao.openConnection(properties.getProperty("jdbc.url"),properties.getProperty("jdbc.username"),properties.getProperty("jdbc.password"));
        dao.setTableName(getTableName());
        if(dao.isTableExist()){
            dao.dropTable();
        }
        dao.createTable();
        dao.addColumn(getArgs());
        dao.createIndex(getIndexName(),getIndexList());
    }

    public void hand(List<Inparam> inparams) {
        dao.saveAll(inparams);
    }

    public void postHand() {
        dao.releaseConnection();
    }

    private String getTableName(){
        if (StringUtils.isBlank(this.specifiedTableName)){
            int maxSize = 12;
            String args[] = getArgs();
            StringBuilder builder = new StringBuilder();
            for (String s :args){
                String[] split = s.split("_");
                for (String _s : split){
                    String firstAlp = _s.substring(0, 1);
                    builder.append(firstAlp);
                }
            }
            //oracle表名有长度限制
            String tempTable = builder.length()>12?builder.substring(0,11):builder.toString();
            String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date());
            this.tempTableName = "INPARAM_"+tempTable.toUpperCase() ;
            return tempTableName+"_"+yyyyMMdd;
        }
        return this.specifiedTableName;
    }

    private String getIndexName(){
        return StringUtils.isBlank(this.specifiedTableName)?tempTableName+"_INDEX":specifiedTableName+"_INDEX";
    }

    private String[] getArgs(){
        return properties.getProperty("args").split(",");
    }

    private List<String> getIndexList(){
        List<String> indexList = new ArrayList<String>();
        indexList.add("SRV_NAME");
        indexList.add("CALL_BEGINTIME");
        indexList.add("ROUTE_KEY");
        indexList.add("CHANNEL_ID");
        indexList.add("ROUTE_VALUE");
        indexList.add("CLIENT_IP");
        indexList.add("USERNAME");
        indexList.add("RETCODE");
        String args[] = getArgs();
        for (String s: args){
            indexList.add(s);
        }
        return indexList;
    }
}

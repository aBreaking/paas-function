package com.abreaking.easyjpa.util;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author liwei
 * 分页及条件
 *
 */
public class Pagination<T> {
    private int pageSize = 10;
    private int pageNum = 1;
    private String orderByColumn;
    private String isAsc = "asc";
    private int total;
    //返回的结果
    private List<T>  results;

    public Pagination(){}
    public Pagination(int pageSize, int pageNum, String orderByColumn, String isAsc) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.orderByColumn = orderByColumn;
        this.isAsc = isAsc;
    }


    /**
     * 添加mysql的分页，排序
     * @param sql
     * @return
     */
    public String decorateMysqlSql(String sql){
        StringBuffer buffer = new StringBuffer();
        String order = this.getOrderByColumn();
        int pageSize = this.getPageSize();
        int pageNum = this.getPageNum();
        int startNum = (pageNum-1)*pageSize;

        buffer.append(sql);
        if(StringUtils.isNotEmpty(order)){
            buffer.append(" order by ");
            buffer.append(toColumnName(order));
            buffer.append(" ");
            buffer.append(this.getIsAsc());
        }
        buffer.append(" limit ");
        buffer.append(startNum);
        buffer.append(",");
        buffer.append(pageSize);
        return buffer.toString();
    }


    /**
     * oracle的分页排序
     * @param sql
     * @return
     */
    public String decorateOracleSql(String sql){
        StringBuffer buffer = new StringBuffer();
        String order = this.getOrderByColumn();
        int pageSize = this.getPageSize();
        int pageNum = this.getPageNum();
        int endNum = pageNum*pageSize;
        int startNum = (pageNum-1)*pageSize;

        buffer.append("SELECT * FROM (SELECT TMP_PAGE.*,  ROWNUM ROW_ID FROM  ( ");
        buffer.append(sql);

        if(StringUtils.isNotEmpty(order)){
            buffer.append(" order by ");
            buffer.append(toColumnName(order));
            buffer.append(" ");
            buffer.append(this.getIsAsc());
        }
        buffer.append(")TMP_PAGE WHERE ROWNUM <= ");
        buffer.append(endNum);
        buffer.append(")WHERE ROW_ID >");
        buffer.append(startNum);
        return buffer.toString();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public String toColumnName(String s){
        char[] chars = s.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : chars){
            if(Character.isUpperCase(c)){
                builder.append("_");
                builder.append(Character.toLowerCase(c));
            }else{
                builder.append(c);
            }
        }
        return builder.toString();
    }
    //TODO
    static class Condition{
        private String name; //条件名
        private String operator; //条件判断符号
        private String value; //对应值
    }
}

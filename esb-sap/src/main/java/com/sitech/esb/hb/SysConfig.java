package com.sitech.esb.hb;



/**
 * SysConfig generated by MyEclipse - Hibernate Tools
 */

public class  SysConfig  implements java.io.Serializable {


    // Fields    

     private String paramName;
     private String paramValue;
     private String paramDesc;
     private Long paramType;


    // Constructors

    /** default constructor */
    public  SysConfig() {
    }

    
    /** full constructor */
    public  SysConfig(String paramValue, String paramDesc, Long paramType) {
        this.paramValue = paramValue;
        this.paramDesc = paramDesc;
        this.paramType = paramType;
    }
    
    public  SysConfig(String paramName, String paramValue, String paramDesc, Long paramType) {
    	this.paramName = paramName;
        this.paramValue = paramValue;
        this.paramDesc = paramDesc;
        this.paramType = paramType;
    }
   
    // Property accessors

    public String getParamName() {
        return this.paramName;
    }
    
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return this.paramValue;
    }
    
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamDesc() {
        return this.paramDesc;
    }
    
    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public Long getParamType() {
        return this.paramType;
    }
    
    public void setParamType(Long paramType) {
        this.paramType = paramType;
    }
   








}
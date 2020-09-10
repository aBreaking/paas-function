package com.sitech.esb.hb;



/**
 * SrvInput entity provides the base persistence definition of the SrvInput entity. @author MyEclipse Persistence Tools
 */

public class  SrvInput  implements java.io.Serializable {


    // Fields    

     private Long inputId;
     private SrvInfo srvInfo;
     private String inputName;
     private String inputType;
     private String inputWidth;
     private Long inputNum;
     private String inputDesc;
     private String inputValue;
     private Long inputNull;
     private Long isFixedValue;
     private Long isFixedVisible;
     private String fixedValue;
     private String sinputNos;


    // Constructors

    /** default constructor */
    public SrvInput() {
    }

	/** minimal constructor */
    public SrvInput(SrvInfo srvInfo, String inputName, String inputType, String inputWidth, Long inputNum, String inputDesc, Long inputNull, Long isFixedValue, Long isFixedVisible) {
        this.srvInfo = srvInfo;
        this.inputName = inputName;
        this.inputType = inputType;
        this.inputWidth = inputWidth;
        this.inputNum = inputNum;
        this.inputDesc = inputDesc;
        this.inputNull = inputNull;
        this.isFixedValue = isFixedValue;
        this.isFixedVisible = isFixedVisible;
    }
    
    /** full constructor */
    public SrvInput(SrvInfo srvInfo, String inputName, String inputType, String inputWidth, Long inputNum, String inputDesc, String inputValue, Long inputNull, Long isFixedValue, Long isFixedVisible, String fixedValue, String sinputNos) {
        this.srvInfo = srvInfo;
        this.inputName = inputName;
        this.inputType = inputType;
        this.inputWidth = inputWidth;
        this.inputNum = inputNum;
        this.inputDesc = inputDesc;
        this.inputValue = inputValue;
        this.inputNull = inputNull;
        this.isFixedValue = isFixedValue;
        this.isFixedVisible = isFixedVisible;
        this.fixedValue = fixedValue;
        this.sinputNos = sinputNos;
    }

   
    // Property accessors

    public Long getInputId() {
        return this.inputId;
    }
    
    public void setInputId(Long inputId) {
        this.inputId = inputId;
    }

    public SrvInfo getSrvInfo() {
        return this.srvInfo;
    }
    
    public void setSrvInfo(SrvInfo srvInfo) {
        this.srvInfo = srvInfo;
    }

    public String getInputName() {
        return this.inputName;
    }
    
    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getInputType() {
        return this.inputType;
    }
    
    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getInputWidth() {
        return this.inputWidth;
    }
    
    public void setInputWidth(String inputWidth) {
        this.inputWidth = inputWidth;
    }

    public Long getInputNum() {
        return this.inputNum;
    }
    
    public void setInputNum(Long inputNum) {
        this.inputNum = inputNum;
    }

    public String getInputDesc() {
        return this.inputDesc;
    }
    
    public void setInputDesc(String inputDesc) {
        this.inputDesc = inputDesc;
    }

    public String getInputValue() {
        return this.inputValue;
    }
    
    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public Long getInputNull() {
        return this.inputNull;
    }
    
    public void setInputNull(Long inputNull) {
        this.inputNull = inputNull;
    }

    public Long getIsFixedValue() {
        return this.isFixedValue;
    }
    
    public void setIsFixedValue(Long isFixedValue) {
        this.isFixedValue = isFixedValue;
    }

    public Long getIsFixedVisible() {
        return this.isFixedVisible;
    }
    
    public void setIsFixedVisible(Long isFixedVisible) {
        this.isFixedVisible = isFixedVisible;
    }

    public String getFixedValue() {
        return this.fixedValue;
    }
    
    public void setFixedValue(String fixedValue) {
        this.fixedValue = fixedValue;
    }

    public String getSinputNos() {
        return this.sinputNos;
    }
    
    public void setSinputNos(String sinputNos) {
        this.sinputNos = sinputNos;
    }
   








}
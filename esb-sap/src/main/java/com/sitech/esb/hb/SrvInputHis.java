package com.sitech.esb.hb;

import java.util.Date;

/**
 * SrvInputHis entity provides the base persistence definition of the
 * SrvInputHis entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SrvInputHis implements java.io.Serializable {

	// Fields

	private Long inputHisId;
	private Long opId;
	private Long inputId;
	private Date opTime;
	private String opUser;
	private String opUserIp;
	private String inputName;
	private String inputType;
	private String inputWidth;
	private Long inputNum;
	private String inputDesc;
	private String inputValue;
	private Long inputNull;
	private Long isFixedvalue;
	private String fixedvalue;
	private Long isfixedVisible;
	private String sinputNos;
	private String verNo;

	// Constructors

	/** default constructor */
	public  SrvInputHis() {
	}

	/** minimal constructor */
	public  SrvInputHis(Long opId, Long inputId, Date opTime,
			String opUser, String opUserIp, String inputName, String inputType,
			String inputWidth, Long inputNum, String inputDesc, Long inputNull,
			Long isFixedvalue, Long isfixedVisible) {
		this.opId = opId;
		this.inputId = inputId;
		this.opTime = opTime;
		this.opUser = opUser;
		this.opUserIp = opUserIp;
		this.inputName = inputName;
		this.inputType = inputType;
		this.inputWidth = inputWidth;
		this.inputNum = inputNum;
		this.inputDesc = inputDesc;
		this.inputNull = inputNull;
		this.isFixedvalue = isFixedvalue;
		this.isfixedVisible = isfixedVisible;
	}

	/** full constructor */
	public  SrvInputHis(Long opId, Long inputId, Date opTime,
			String opUser, String opUserIp, String inputName, String inputType,
			String inputWidth, Long inputNum, String inputDesc,
			String inputValue, Long inputNull, Long isFixedvalue,
			String fixedvalue, Long isfixedVisible, String sinputNos,
			String verNo) {
		this.opId = opId;
		this.inputId = inputId;
		this.opTime = opTime;
		this.opUser = opUser;
		this.opUserIp = opUserIp;
		this.inputName = inputName;
		this.inputType = inputType;
		this.inputWidth = inputWidth;
		this.inputNum = inputNum;
		this.inputDesc = inputDesc;
		this.inputValue = inputValue;
		this.inputNull = inputNull;
		this.isFixedvalue = isFixedvalue;
		this.fixedvalue = fixedvalue;
		this.isfixedVisible = isfixedVisible;
		this.sinputNos = sinputNos;
		this.verNo = verNo;
	}

	// Property accessors

	public Long getInputHisId() {
		return this.inputHisId;
	}

	public void setInputHisId(Long inputHisId) {
		this.inputHisId = inputHisId;
	}

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Long getInputId() {
		return this.inputId;
	}

	public void setInputId(Long inputId) {
		this.inputId = inputId;
	}

	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public String getOpUser() {
		return this.opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public String getOpUserIp() {
		return this.opUserIp;
	}

	public void setOpUserIp(String opUserIp) {
		this.opUserIp = opUserIp;
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

	public Long getIsFixedvalue() {
		return this.isFixedvalue;
	}

	public void setIsFixedvalue(Long isFixedvalue) {
		this.isFixedvalue = isFixedvalue;
	}

	public String getFixedvalue() {
		return this.fixedvalue;
	}

	public void setFixedvalue(String fixedvalue) {
		this.fixedvalue = fixedvalue;
	}

	public Long getIsfixedVisible() {
		return this.isfixedVisible;
	}

	public void setIsfixedVisible(Long isfixedVisible) {
		this.isfixedVisible = isfixedVisible;
	}

	public String getSinputNos() {
		return this.sinputNos;
	}

	public void setSinputNos(String sinputNos) {
		this.sinputNos = sinputNos;
	}

	public String getVerNo() {
		return this.verNo;
	}

	public void setVerNo(String verNo) {
		this.verNo = verNo;
	}

}
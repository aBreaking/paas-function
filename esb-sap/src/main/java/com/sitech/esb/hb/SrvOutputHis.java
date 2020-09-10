package com.sitech.esb.hb;

import java.util.Date;

/**
 * SrvOutputHis entity provides the base persistence definition of the
 * SrvOutputHis entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SrvOutputHis implements java.io.Serializable {

	// Fields

	private Long onputHisId;
	private Long opId;
	private Long outputId;
	private Date opTime;
	private String opUser;
	private String opUserIp;
	private String outputName;
	private String outputType;
	private String outputWidth;
	private Long outputNum;
	private String outputDesc;
	private String outputValue;
	private String verNo;

	// Constructors

	/** default constructor */
	public  SrvOutputHis() {
	}

	/** minimal constructor */
	public  SrvOutputHis(Long opId, Long outputId, Date opTime,
			String opUser, String opUserIp, String outputName,
			String outputType, String outputWidth, Long outputNum,
			String outputDesc) {
		this.opId = opId;
		this.outputId = outputId;
		this.opTime = opTime;
		this.opUser = opUser;
		this.opUserIp = opUserIp;
		this.outputName = outputName;
		this.outputType = outputType;
		this.outputWidth = outputWidth;
		this.outputNum = outputNum;
		this.outputDesc = outputDesc;
	}

	/** full constructor */
	public  SrvOutputHis(Long opId, Long outputId, Date opTime,
			String opUser, String opUserIp, String outputName,
			String outputType, String outputWidth, Long outputNum,
			String outputDesc, String outputValue, String verNo) {
		this.opId = opId;
		this.outputId = outputId;
		this.opTime = opTime;
		this.opUser = opUser;
		this.opUserIp = opUserIp;
		this.outputName = outputName;
		this.outputType = outputType;
		this.outputWidth = outputWidth;
		this.outputNum = outputNum;
		this.outputDesc = outputDesc;
		this.outputValue = outputValue;
		this.verNo = verNo;
	}

	// Property accessors

	public Long getOnputHisId() {
		return this.onputHisId;
	}

	public void setOnputHisId(Long onputHisId) {
		this.onputHisId = onputHisId;
	}

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Long getOutputId() {
		return this.outputId;
	}

	public void setOutputId(Long outputId) {
		this.outputId = outputId;
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

	public String getOutputName() {
		return this.outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public String getOutputType() {
		return this.outputType;
	}

	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	public String getOutputWidth() {
		return this.outputWidth;
	}

	public void setOutputWidth(String outputWidth) {
		this.outputWidth = outputWidth;
	}

	public Long getOutputNum() {
		return this.outputNum;
	}

	public void setOutputNum(Long outputNum) {
		this.outputNum = outputNum;
	}

	public String getOutputDesc() {
		return this.outputDesc;
	}

	public void setOutputDesc(String outputDesc) {
		this.outputDesc = outputDesc;
	}

	public String getOutputValue() {
		return this.outputValue;
	}

	public void setOutputValue(String outputValue) {
		this.outputValue = outputValue;
	}

	public String getVerNo() {
		return this.verNo;
	}

	public void setVerNo(String verNo) {
		this.verNo = verNo;
	}

}
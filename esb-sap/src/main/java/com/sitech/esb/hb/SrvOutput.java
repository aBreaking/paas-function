package com.sitech.esb.hb;

/**
 * SrvOutput entity provides the base persistence definition of the
 * SrvOutput entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SrvOutput implements java.io.Serializable {

	// Fields

	private Long outputId;
	private SrvInfo srvInfo;
	private String outputName;
	private String outputType;
	private String outputWidth;
	private Long outputNum;
	private String outputDesc;
	private String outputValue;
	

	// Constructors

	/** default constructor */
	public SrvOutput() {
	}

	/** minimal constructor */
	public SrvOutput(SrvInfo srvInfo, String outputName, String outputType,
			String outputWidth, Long outputNum, String outputDesc) {
		this.srvInfo = srvInfo;
		this.outputName = outputName;
		this.outputType = outputType;
		this.outputWidth = outputWidth;
		this.outputNum = outputNum;
		this.outputDesc = outputDesc;
	}

	/** full constructor */
	public SrvOutput(SrvInfo srvInfo, String outputName, String outputType,
			String outputWidth, Long outputNum, String outputDesc,
			String outputValue) {
		this.srvInfo = srvInfo;
		this.outputName = outputName;
		this.outputType = outputType;
		this.outputWidth = outputWidth;
		this.outputNum = outputNum;
		this.outputDesc = outputDesc;
		this.outputValue = outputValue;
	}

	// Property accessors

	public Long getOutputId() {
		return this.outputId;
	}

	public void setOutputId(Long outputId) {
		this.outputId = outputId;
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

	public SrvInfo getSrvInfo() {
		return srvInfo;
	}

	public void setSrvInfo(SrvInfo srvInfo) {
		this.srvInfo = srvInfo;
	}

}
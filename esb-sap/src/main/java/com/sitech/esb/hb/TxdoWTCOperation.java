package com.sitech.esb.hb;

/**
 * TxdoWdom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TxdoWTCOperation implements java.io.Serializable {

	// Fields

	private Long operationid;
	private Long operationtype;
	private String operation_value;
	private Long status;
	

	// Constructors

	/** default constructor */
	public TxdoWTCOperation() {
	}

	/** full constructor */
	public TxdoWTCOperation(Long operationid, Long operationtype, String value,
			Long status) {
		this.operationid = operationid;
		this.operationtype = operationtype;
		this.operation_value = value;
		this.status = status;
	}

	public Long getOperationid() {
		return operationid;
	}

	public void setOperationid(Long operationid) {
		this.operationid = operationid;
	}

	public Long getOperationtype() {
		return operationtype;
	}

	public void setOperationtype(Long operationtype) {
		this.operationtype = operationtype;
	}

	

	public String getOperation_value() {
		return operation_value;
	}

	public void setOperation_value(String operation_value) {
		this.operation_value = operation_value;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	

}
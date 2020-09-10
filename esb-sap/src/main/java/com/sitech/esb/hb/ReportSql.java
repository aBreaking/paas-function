package com.sitech.esb.hb;

/**
 * ReportSql entity provides the base persistence definition of the
 * ReportSql entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  ReportSql implements java.io.Serializable {

	// Fields

	private String sqlName;
	private String sqlPurpose;
	private String sqlContent;

	// Constructors

	/** default constructor */
	public ReportSql() {
	}

	/** full constructor */
	public ReportSql(String sqlName, String sqlPurpose,
			String sqlContent) {
		this.sqlName = sqlName;
		this.sqlPurpose = sqlPurpose;
		this.sqlContent = sqlContent;
	}

	// Property accessors

	public String getSqlName() {
		return this.sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	public String getSqlPurpose() {
		return this.sqlPurpose;
	}

	public void setSqlPurpose(String sqlPurpose) {
		this.sqlPurpose = sqlPurpose;
	}

	public String getSqlContent() {
		return this.sqlContent;
	}

	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}

}
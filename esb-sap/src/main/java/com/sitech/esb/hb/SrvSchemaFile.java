package com.sitech.esb.hb;

import java.sql.Blob;






public class SrvSchemaFile implements
		java.io.Serializable {

	// Fields
	private SrvWsdlFile srvWsdlFile;
	private Long srvId;
	private Long srvSchemaFileId;
	private Blob srvSchemaFile;
	private Long srvWsdlFileid;
	private String srvSchemaFilename;
	// Constructors


	
	public String getSrvSchemaFilename() {
		return srvSchemaFilename;
	}

	public void setSrvSchemaFilename(String srvSchemaFilename) {
		this.srvSchemaFilename = srvSchemaFilename;
	}

	/** default constructor */
	public SrvSchemaFile() {
	}

	/** full constructor */
	public SrvSchemaFile(Long srvId, Blob srvSchemaFile,SrvWsdlFile srvWsdlFile
			) {
		this.srvId = srvId;
		this.srvSchemaFile = srvSchemaFile;
		this.srvWsdlFile = srvWsdlFile;
	}

	public SrvWsdlFile getSrvWsdlFile() {
		return srvWsdlFile;
	}

	public void setSrvWsdlFile(SrvWsdlFile srvWsdlFile) {
		this.srvWsdlFile = srvWsdlFile;
	}

	public Long getSrvId() {
		return srvId;
	}

	public void setSrvId(Long srvId) {
		this.srvId = srvId;
	}

	public Long getSrvSchemaFileId() {
		return srvSchemaFileId;
	}

	public void setSrvSchemaFileId(Long srvSchemaFileId) {
		this.srvSchemaFileId = srvSchemaFileId;
	}

	public Blob getSrvSchemaFile() {
		return srvSchemaFile;
	}

	public void setSrvSchemaFile(Blob srvSchemaFile) {
		this.srvSchemaFile = srvSchemaFile;
	}

	public Long getSrvWsdlFileid() {
		return srvWsdlFileid;
	}

	public void setSrvWsdlFileid(Long srvWsdlFileid) {
		this.srvWsdlFileid = srvWsdlFileid;
	}




}
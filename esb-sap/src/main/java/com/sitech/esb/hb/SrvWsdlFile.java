package com.sitech.esb.hb;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;





public class SrvWsdlFile  implements
		java.io.Serializable {

	// Fields
	private Long srvWsdlFileid;
	private SrvInfo srvinfo;
	private Long srvId;
	private Blob srvWsdlFile;
	private String srvWsdlFileName;
	private Set srvSchemaFiles = new HashSet();
	
	// Constructors


	/** default constructor */
	public SrvWsdlFile() {
	}

	/** full constructor */
	public SrvWsdlFile(Long srvId, Blob srvWsdlFile,SrvInfo srvinfo
			) {
		this.srvId = srvId;
		this.srvWsdlFile = srvWsdlFile;
		this.srvinfo = srvinfo;
	}

	public Long getSrvWsdlFileid() {
		return srvWsdlFileid;
	}

	public void setSrvWsdlFileid(Long srvWsdlFileid) {
		this.srvWsdlFileid = srvWsdlFileid;
	}

	public SrvInfo getSrvinfo() {
		return srvinfo;
	}

	public void setSrvinfo(SrvInfo srvinfo) {
		this.srvinfo = srvinfo;
	}

	public Long getSrvId() {
		return srvId;
	}

	public void setSrvId(Long srvId) {
		this.srvId = srvId;
	}

	public Blob getSrvWsdlFile() {
		return srvWsdlFile;
	}

	public void setSrvWsdlFile(Blob srvWsdlFile) {
		this.srvWsdlFile = srvWsdlFile;
	}

	public Set getSrvSchemaFiles() {
		return srvSchemaFiles;
	}

	public void setSrvSchemaFiles(Set srvSchemaFiles) {
		this.srvSchemaFiles = srvSchemaFiles;
	}

	public String getSrvWsdlFileName() {
		return srvWsdlFileName;
	}

	public void setSrvWsdlFileName(String srvWsdlFileName) {
		this.srvWsdlFileName = srvWsdlFileName;
	}

	// Property accessors




}
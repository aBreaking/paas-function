package com.sitech.esb.hb;

import java.util.HashSet;
import java.util.Set;

/**
 * EsbcltMvRupload generated by MyEclipse Persistence Tools
 */

public class EsbcltMvRupload implements java.io.Serializable {

	// Fields

	private EsbcltMvRuploadId id;
	private Long validFlag;

	private EsbcltDownloadDir esbcltDownloadDir = null;
	private EsbcltUploadDir esbcltUploadDir = null;
	// Constructors

	/** default constructor */
	public EsbcltMvRupload() {
	}

	/** full constructor */
	public EsbcltMvRupload(EsbcltMvRuploadId id, Long validFlag) {
		this.id = id;
		this.validFlag = validFlag;
	}

	// Property accessors

	public EsbcltMvRuploadId getId() {
		return this.id;
	}

	public void setId(EsbcltMvRuploadId id) {
		this.id = id;
	}

	public Long getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(Long validFlag) {
		this.validFlag = validFlag;
	}

	public EsbcltDownloadDir getEsbcltDownloadDir() {
		return esbcltDownloadDir;
	}

	public void setEsbcltDownloadDir(EsbcltDownloadDir esbcltDownloadDir) {
		this.esbcltDownloadDir = esbcltDownloadDir;
	}

	public EsbcltUploadDir getEsbcltUploadDir() {
		return esbcltUploadDir;
	}

	public void setEsbcltUploadDir(EsbcltUploadDir esbcltUploadDir) {
		this.esbcltUploadDir = esbcltUploadDir;
	}

}
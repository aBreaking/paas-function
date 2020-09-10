package com.sitech.esb.hb;

import java.util.HashSet;
import java.util.Set;


/**
 * EsbcltUploadDir generated by MyEclipse Persistence Tools
 */

public class EsbcltUploadDir  implements java.io.Serializable {


    // Fields    

     private Long esbcltUldirId;
     private FtpServer ftpServer;
     private String remoteDirPath;
     private String localDirPath;
     private String fileNameMatch;
     private Long validFlag;
     private String describle;
     private Set esbsrvMvRuploads = new HashSet(0);
     private Set esbcltMvRuploads = new HashSet(0);

    // Constructors

    /** default constructor */
    public EsbcltUploadDir() {
    }

	/** minimal constructor */
    public EsbcltUploadDir(FtpServer ftpServer, String remoteDirPath, String localDirPath, String fileNameMatch, Long validFlag) {
        this.ftpServer = ftpServer;
        this.remoteDirPath = remoteDirPath;
        this.localDirPath = localDirPath;
        this.fileNameMatch = fileNameMatch;
        this.validFlag = validFlag;
    }
    
    /** full constructor */
    public EsbcltUploadDir(FtpServer ftpServer, String remoteDirPath, String localDirPath, String fileNameMatch, Long validFlag, String describle, Set esbsrvMvRuploads) {
        this.ftpServer = ftpServer;
        this.remoteDirPath = remoteDirPath;
        this.localDirPath = localDirPath;
        this.fileNameMatch = fileNameMatch;
        this.validFlag = validFlag;
        this.describle = describle;
        this.esbsrvMvRuploads = esbsrvMvRuploads;
    }
    public EsbcltUploadDir(FtpServer ftpServer, String remoteDirPath, String localDirPath, String fileNameMatch, Long validFlag, String describle) {
        this.ftpServer = ftpServer;
        this.remoteDirPath = remoteDirPath;
        this.localDirPath = localDirPath;
        this.fileNameMatch = fileNameMatch;
        this.validFlag = validFlag;
        this.describle = describle;
    }
   
    // Property accessors

    public Long getEsbcltUldirId() {
        return this.esbcltUldirId;
    }
    
    public void setEsbcltUldirId(Long esbcltUldirId) {
        this.esbcltUldirId = esbcltUldirId;
    }

    public FtpServer getFtpServer() {
        return this.ftpServer;
    }
    
    public void setFtpServer(FtpServer ftpServer) {
        this.ftpServer = ftpServer;
    }

    public String getRemoteDirPath() {
        return this.remoteDirPath;
    }
    
    public void setRemoteDirPath(String remoteDirPath) {
        this.remoteDirPath = remoteDirPath;
    }

    public String getLocalDirPath() {
        return this.localDirPath;
    }
    
    public void setLocalDirPath(String localDirPath) {
        this.localDirPath = localDirPath;
    }

    public String getFileNameMatch() {
        return this.fileNameMatch;
    }
    
    public void setFileNameMatch(String fileNameMatch) {
        this.fileNameMatch = fileNameMatch;
    }

    public Long getValidFlag() {
        return this.validFlag;
    }
    
    public void setValidFlag(Long validFlag) {
        this.validFlag = validFlag;
    }

    public String getDescrible() {
        return this.describle;
    }
    
    public void setDescrible(String describle) {
        this.describle = describle;
    }

    public Set getEsbsrvMvRuploads() {
        return this.esbsrvMvRuploads;
    }
    
    public void setEsbsrvMvRuploads(Set esbsrvMvRuploads) {
        this.esbsrvMvRuploads = esbsrvMvRuploads;
    }

	public Set getEsbcltMvRuploads() {
		return esbcltMvRuploads;
	}

	public void setEsbcltMvRuploads(Set esbcltMvRuploads) {
		this.esbcltMvRuploads = esbcltMvRuploads;
	}
   








}
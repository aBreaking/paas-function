package com.sitech.esb.hb;



/**
 * EsbsrvMvRupload generated by MyEclipse Persistence Tools
 */

public class EsbsrvMvRupload  implements java.io.Serializable {


    // Fields    

     private EsbsrvMvRuploadId id;
     private EsbsrvUploadDir esbsrvUploadDir;
     private EsbcltUploadDir esbcltUploadDir;
     private Long validFlag;


    // Constructors

    /** default constructor */
    public EsbsrvMvRupload() {
    }

    public EsbsrvMvRupload(EsbsrvMvRuploadId id,Long validFlag){
    	this.id = id;
    	this.validFlag = validFlag;
    }
    /** full constructor */
    public EsbsrvMvRupload(EsbsrvMvRuploadId id, EsbsrvUploadDir esbsrvUploadDir, EsbcltUploadDir esbcltUploadDir, Long validFlag) {
        this.id = id;
        this.esbsrvUploadDir = esbsrvUploadDir;
        this.esbcltUploadDir = esbcltUploadDir;
        this.validFlag = validFlag;
    }

   
    // Property accessors

    public EsbsrvMvRuploadId getId() {
        return this.id;
    }
    
    public void setId(EsbsrvMvRuploadId id) {
        this.id = id;
    }

    public EsbsrvUploadDir getEsbsrvUploadDir() {
        return this.esbsrvUploadDir;
    }
    
    public void setEsbsrvUploadDir(EsbsrvUploadDir esbsrvUploadDir) {
        this.esbsrvUploadDir = esbsrvUploadDir;
    }

    public EsbcltUploadDir getEsbcltUploadDir() {
        return this.esbcltUploadDir;
    }
    
    public void setEsbcltUploadDir(EsbcltUploadDir esbcltUploadDir) {
        this.esbcltUploadDir = esbcltUploadDir;
    }

    public Long getValidFlag() {
        return this.validFlag;
    }
    
    public void setValidFlag(Long validFlag) {
        this.validFlag = validFlag;
    }
   








}
package com.sitech.esb.hb;



/**
 * SrvStatus generated by MyEclipse - Hibernate Tools
 */

public class  SrvStatus  implements java.io.Serializable {


    // Fields    

     private Long srvStatusId;
     private String srvStatusName;
     private Long srvStatusFlag;


    // Constructors

    /** default constructor */
    public  SrvStatus() {
    }

	/** minimal constructor */
    public  SrvStatus(String srvStatusName) {
        this.srvStatusName = srvStatusName;
    }
    
    /** full constructor */
    public  SrvStatus(String srvStatusName, Long srvStatusFlag) {
        this.srvStatusName = srvStatusName;
        this.srvStatusFlag = srvStatusFlag;
    }

   
    // Property accessors

    public Long getSrvStatusId() {
        return this.srvStatusId;
    }
    
    public void setSrvStatusId(Long srvStatusId) {
        this.srvStatusId = srvStatusId;
    }

    public String getSrvStatusName() {
        return this.srvStatusName;
    }
    
    public void setSrvStatusName(String srvStatusName) {
        this.srvStatusName = srvStatusName;
    }

    public Long getSrvStatusFlag() {
        return this.srvStatusFlag;
    }
    
    public void setSrvStatusFlag(Long srvStatusFlag) {
        this.srvStatusFlag = srvStatusFlag;
    }
   








}
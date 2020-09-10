package com.sitech.esb.hb;



/**
 * SrvErrinfo entity provides the base persistence definition of the SrvErrinfo entity. @author MyEclipse Persistence Tools
 */

public class  SrvErrinfo  implements java.io.Serializable {


    // Fields    

     private Long srverrId;
     private String srverrCode;
     private String srverrMsg;
     private String mapedMsg;
     private SrvInfo srvInfo;


    // Constructors

    public String getMapedMsg() {
		return mapedMsg;
	}


	public void setMapedMsg(String mapedMsg) {
		this.mapedMsg = mapedMsg;
	}


	/** default constructor */
    public SrvErrinfo() {
    }

    
    /** full constructor */
    public SrvErrinfo(String srverrCode, String srverrMsg, SrvInfo srvInfo) {
        this.srverrCode = srverrCode;
        this.srverrMsg = srverrMsg;
        this.srvInfo = srvInfo;
    }

    /** full constructor */
    public SrvErrinfo(String srverrCode, String srverrMsg,String mapedMsg, SrvInfo srvInfo){
        this.srverrCode = srverrCode;
        this.srverrMsg = srverrMsg;
        this.mapedMsg = mapedMsg;
        this.srvInfo = srvInfo;
    }
    
    // Property accessors

    public Long getSrverrId() {
        return this.srverrId;
    }
    
    public void setSrverrId(Long srverrId) {
        this.srverrId = srverrId;
    }

    public String getSrverrCode() {
        return this.srverrCode;
    }
    
    public void setSrverrCode(String srverrCode) {
        this.srverrCode = srverrCode;
    }

    public String getSrverrMsg() {
        return this.srverrMsg;
    }
    
    public void setSrverrMsg(String srverrMsg) {
        this.srverrMsg = srverrMsg;
    }

    public SrvInfo getSrvInfo() {
        return this.srvInfo;
    }
    
    public void setSrvInfo(SrvInfo srvInfo) {
        this.srvInfo = srvInfo;
    }
   








}
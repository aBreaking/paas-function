package com.sitech.esb.hb;



/**
 * ClientAppRight entity provides the base persistence definition of the ClientAppRight entity. @author MyEclipse Persistence Tools
 */

public class  ClientAppRight  implements java.io.Serializable {


    // Fields    

     private Long clientAppId;
     private AppInfo appInfo;
     private ClientInfo clientInfo;
     private Long validFlag;
     private Long flowThreshold;
     private Long endUsrDayFlowThrsd;
     private Long isEndUsrLoginValidate;
     private Long isEndUsrSmsValidate;

    // Constructors

	/** default constructor */
    public ClientAppRight() {
    }

    
    /** full constructor */
    public ClientAppRight(ClientInfo clientInfo, AppInfo appInfo, Long flowThreshold, Long validFlag) {
        this.clientInfo = clientInfo;
        this.appInfo = appInfo;
        this.flowThreshold = flowThreshold;
        this.validFlag = validFlag;
    }

   
    // Property accessors
    public Long getValidFlag() {
        return this.validFlag;
    }
    
    public void setValidFlag(Long validFlag) {
        this.validFlag = validFlag;
    }


	public AppInfo getAppInfo() {
		return appInfo;
	}


	public void setAppInfo(AppInfo appInfo) {
		this.appInfo = appInfo;
	}


	public ClientInfo getClientInfo() {
		return clientInfo;
	}


	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}


	public Long getClientAppId() {
		return clientAppId;
	}


	public void setClientAppId(Long clientAppId) {
		this.clientAppId = clientAppId;
	}

	public Long getFlowThreshold() {
		return flowThreshold;
	}


	public void setFlowThreshold(Long flowThreshold) {
		this.flowThreshold = flowThreshold;
	}


	public Long getIsEndUsrLoginValidate() {
		return isEndUsrLoginValidate;
	}


	public void setIsEndUsrLoginValidate(Long isEndUsrLoginValidate) {
		this.isEndUsrLoginValidate = isEndUsrLoginValidate;
	}


	public Long getEndUsrDayFlowThrsd() {
		return endUsrDayFlowThrsd;
	}


	public void setEndUsrDayFlowThrsd(Long endUsrDayFlowThrsd) {
		this.endUsrDayFlowThrsd = endUsrDayFlowThrsd;
	}

    public Long getIsEndUsrSmsValidate() {
		return isEndUsrSmsValidate;
	}


	public void setIsEndUsrSmsValidate(Long isEndUsrSmsValidate) {
		this.isEndUsrSmsValidate = isEndUsrSmsValidate;
	}

}
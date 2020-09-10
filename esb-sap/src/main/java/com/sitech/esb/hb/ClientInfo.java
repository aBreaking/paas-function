package com.sitech.esb.hb;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;



/**
 * ClientInfo generated by MyEclipse - Hibernate Tools
 */
public class  ClientInfo  implements java.io.Serializable {


    // Fields    

     private Long clientId;
     private String clientName;
     private Long validFlag;
     private Long flowThreshold;
     private Long priorityValue;
     private String clientDescribe;
     private Long needUserPwd;
     private Long needOprSrvRight;
     private Long needEndUsrIp;
     private String channelId;
     
     
     private Long needIpRecode;
     
     private Set userinfos = new HashSet();
     private Set clientAddrs = new HashSet();
     private Set clientApps = new HashSet();
     private Set appFuncats = new HashSet();
//     private Set topics = new HashSet();
     
//     private Set clientFuncats = new HashSet();
    // Constructors
	/** default constructor */
    public  ClientInfo() {
    }
    public  ClientInfo(Long clientId){
    	this.clientId = clientId;
    }
    public  ClientInfo(Long clientId, String clientName) {
    	this.clientId = clientId;
    	this.clientName = clientName;
    }

	/** minimal constructor */
    public  ClientInfo(String clientName, Long validFlag) {
        this.clientName = clientName;
        this.validFlag = validFlag;
    }
    
    /** full constructor */
    public  ClientInfo(String clientName, Long validFlag, Long flowThreshold, Long priorityValue, String clientDescribe) {
        this.clientName = clientName;
        this.validFlag = validFlag;
        this.flowThreshold = flowThreshold;
        this.priorityValue = priorityValue;
        this.clientDescribe = clientDescribe;
    }

   
    // Property accessors

    public Long getClientId() {
        return this.clientId;
    }
    
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return this.clientName;
    }
    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getValidFlag() {
        return this.validFlag;
    }
    
    public void setValidFlag(Long validFlag) {
        this.validFlag = validFlag;
    }

    public Long getFlowThreshold() {
        return this.flowThreshold;
    }
    
    public void setFlowThreshold(Long flowThreshold) {
        this.flowThreshold = flowThreshold;
    }

    public Long getPriorityValue() {
        return this.priorityValue;
    }
    
    public void setPriorityValue(Long priorityValue) {
        this.priorityValue = priorityValue;
    }

    public String getClientDescribe() {
        return this.clientDescribe;
    }
    
    public void setClientDescribe(String clientDescribe) {
        this.clientDescribe = clientDescribe;
    }

	public Set getClientAddrs() {
		return clientAddrs;
	}

	public void setClientAddrs(Set clientAddrs) {
		this.clientAddrs = clientAddrs;
	}

	public Set getUserinfos() {
		return userinfos;
	}

	public void setUserinfos(Set userinfos) {
		this.userinfos = userinfos;
	}

	public void setNeedUserPwd(Long needUserPwd) {
		this.needUserPwd = needUserPwd;
	}

	public Long getNeedUserPwd() {
		return needUserPwd;
	}

	public Set getClientApps() {
		return clientApps;
	}

	public void setClientApps(Set clientApps) {
		this.clientApps = clientApps;
	}

	public Long getNeedOprSrvRight() {
		return needOprSrvRight;
	}

	public void setNeedOprSrvRight(Long needOprSrvRight) {
		this.needOprSrvRight = needOprSrvRight;
	}

	public Long getNeedEndUsrIp() {
		return needEndUsrIp;
	}

	public void setNeedEndUsrIp(Long needEndUsrIp) {
		this.needEndUsrIp = needEndUsrIp;
	}

	public Set getAppFuncats() {
		return appFuncats;
	}

	public void setAppFuncats(Set appFuncats) {
		this.appFuncats = appFuncats;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public Long getNeedIpRecode() {
		return needIpRecode;
	}
	public void setNeedIpRecode(Long needIpRecode) {
		this.needIpRecode = needIpRecode;
	}

//	public Set getTopics() {
//		return topics;
//	}
//
//	public void setTopics(Set topics) {
//		this.topics = topics;
//	}
	


}
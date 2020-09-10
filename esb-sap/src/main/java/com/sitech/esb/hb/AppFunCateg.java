package com.sitech.esb.hb;

import java.util.HashSet;
import java.util.Set;



/**
 *  generated by MyEclipse - Hibernate Tools
 */

public class  AppFunCateg  implements java.io.Serializable {


    // Fields    

     private Long appFuncatId;
     private String appFunctionName;
     private Long flowThreshold;
     private Long priorityValue;
     private Long needUserPwd;
     private Long needIpValid;
     private Long needOprSrvRight;
     
     private Set appInfos = new HashSet();
     private Set clients = new HashSet();
     
     
     
    // Constructors


	/** default constructor */
    public AppFunCateg() {
    }

	/** minimal constructor */
    public AppFunCateg(String appFunctionName) {
        this.appFunctionName = appFunctionName;
    }
    
    public AppFunCateg(Long appFuncatId, String appFunctionName) {
    	this.appFuncatId = appFuncatId;
    	this.appFunctionName = appFunctionName;
    }
    
    /** full constructor */
    public AppFunCateg(String appFunctionName, Long flowThreshold, Long priorityValue) {
        this.appFunctionName = appFunctionName;
        this.flowThreshold = flowThreshold;
        this.priorityValue = priorityValue;
    }

   
    // Property accessors

    public Long getAppFuncatId() {
        return this.appFuncatId;
    }
    
    public void setAppFuncatId(Long appFuncatId) {
        this.appFuncatId = appFuncatId;
    }

    public String getAppFunctionName() {
        return this.appFunctionName;
    }
    
    public void setAppFunctionName(String appFunctionName) {
        this.appFunctionName = appFunctionName;
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


	public Long getNeedUserPwd() {
		return needUserPwd;
	}

	public void setNeedUserPwd(Long needUserPwd) {
		this.needUserPwd = needUserPwd;
	}

	public Long getNeedIpValid() {
		return needIpValid;
	}

	public void setNeedIpValid(Long needIpValid) {
		this.needIpValid = needIpValid;
	}

	public Long getNeedOprSrvRight() {
		return needOprSrvRight;
	}

	public void setNeedOprSrvRight(Long needOprSrvRight) {
		this.needOprSrvRight = needOprSrvRight;
	}


	public Set getAppInfos() {
		return appInfos;
	}

	public void setAppInfos(Set appInfos) {
		this.appInfos = appInfos;
	}

	public Set getClients() {
		return clients;
	}

	public void setClients(Set clients) {
		this.clients = clients;
	}

}
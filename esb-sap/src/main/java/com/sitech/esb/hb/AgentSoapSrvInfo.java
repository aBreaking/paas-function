package com.sitech.esb.hb;



/**
 * SoapSrvInfo generated by MyEclipse - Hibernate Tools
 */

public class AgentSoapSrvInfo extends SoapAdapter implements java.io.Serializable {


    // Fields    

    private String srcWsdlAddr;
	private String srcAddress;

	private String nsTrans;
	private String operationTrans;
	private String reqmsgName;
	private String retmsgName;
	
	private String outXpaths;
	private String filterXpaths;
	
	private String paramsMap;
	
	private SoaphttpSystem soaphttpSystem;

    // Constructors

    /** default constructor */
    public AgentSoapSrvInfo() {
    }
    
    public AgentSoapSrvInfo(Long srvId, String srvName, String srvChName) {
    	super(srvId, srvName, srvChName);
    }
    
	// Property accessors
    public String getSrcWsdlAddr() {
        return this.srcWsdlAddr;
    }
    
    public void setSrcWsdlAddr(String srcWsdlAddr) {
        this.srcWsdlAddr = srcWsdlAddr;
    }


    public String getSrcAddress() {
        return this.srcAddress;
    }
    
    public void setSrcAddress(String srcAddress) {
        this.srcAddress = srcAddress;
    }

	public String getNsTrans() {
		return nsTrans;
	}


	public void setNsTrans(String nsTrans) {
		this.nsTrans = nsTrans;
	}


	public String getOperationTrans() {
		return operationTrans;
	}


	public void setOperationTrans(String operationTrans) {
		this.operationTrans = operationTrans;
	}


	public String getReqmsgName() {
		return reqmsgName;
	}


	public void setReqmsgName(String reqmsgName) {
		this.reqmsgName = reqmsgName;
	}


	public String getRetmsgName() {
		return retmsgName;
	}


	public void setRetmsgName(String retmsgName) {
		this.retmsgName = retmsgName;
	}

	public String getParamsMap() {
		return paramsMap;
	}


	public void setParamsMap(String paramsMap) {
		this.paramsMap = paramsMap;
	}

	public String getOutXpaths() {
		return outXpaths;
	}

	public void setOutXpaths(String outXpaths) {
		this.outXpaths = outXpaths;
	}


	public String getFilterXpaths() {
		return filterXpaths;
	}

	public void setFilterXpaths(String filterXpaths) {
		this.filterXpaths = filterXpaths;
	}

	public SoaphttpSystem getSoaphttpSystem() {
		return soaphttpSystem;
	}

	public void setSoaphttpSystem(SoaphttpSystem soaphttpSystem) {
		this.soaphttpSystem = soaphttpSystem;
	}

}
package com.sitech.esb.hb;

import java.sql.Clob;



/**
 * SoapSrvInfo generated by MyEclipse - Hibernate Tools
 */

public class SelfSoapSrvInfo extends SoapAdapter implements java.io.Serializable {
	
	//soap复合服务
	private String srcOperationName;
	private Clob comLogic;

    // Constructors

    /** default constructor */
    public SelfSoapSrvInfo() {
    }
    
    //取得服务最基本信息用
    public SelfSoapSrvInfo(Long srvId, String srvName, String srvChName) {
    	super(srvId, srvName, srvChName);
    }

	public String getSrcOperationName() {
		return srcOperationName;
	}

	public void setSrcOperationName(String srcOperationName) {
		this.srcOperationName = srcOperationName;
	}

	public Clob getComLogic() {
		return comLogic;
	}

	public void setComLogic(Clob comLogic) {
		this.comLogic = comLogic;
	}

}
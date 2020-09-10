package com.sitech.esb.hb;



/**
 * SysConfig entity. @author MyEclipse Persistence Tools
 */

public class IpPortPoolID  implements java.io.Serializable {


    // Fields    

	 private Long IpPortPoolID_id;
     private String ip;
     private Long port;
     private String poolID;


    // Constructors

    /** default constructor */
    public IpPortPoolID() {
    }
    
    public IpPortPoolID(Long IpPortPoolID_id,String ip,Long port,String poolID){
    	this.ip = ip;
    	this.IpPortPoolID_id = IpPortPoolID_id;
    	this.port = port;
    	this.poolID = poolID;
    	
    }


	public Long getIpPortPoolID_id() {
		return IpPortPoolID_id;
	}


	public void setIpPortPoolID_id(Long ipPortPoolID_id) {
		IpPortPoolID_id = ipPortPoolID_id;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}



	public String getPoolID() {
		return poolID;
	}

	public void setPoolID(String poolID) {
		this.poolID = poolID;
	}

	public Long getPort() {
		return port;
	}


	public void setPort(Long port) {
		this.port = port;
	}





}
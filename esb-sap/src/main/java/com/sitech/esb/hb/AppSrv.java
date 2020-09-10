package com.sitech.esb.hb;




public class AppSrv implements java.io.Serializable {

	// Fields

    private AppSrvId id;
    private AppInfo appinfo;
    private SrvInfo srvinfo;

	// Constructors
    public AppSrv(AppSrvId id){
    	this.id = id;
    
    }
    /** full constructor */
    public AppSrv(AppSrvId id, AppInfo appinfo, SrvInfo srvinfo) {
        this.id = id;
        this.srvinfo = srvinfo;
        this.appinfo = appinfo;
        this.srvinfo = srvinfo;
    }

	/** default constructor */
	public AppSrv() {
	}


	// Property accessors

	public AppSrvId getId() {
		return this.id;
	}

	public void setId(AppSrvId id) {
		this.id = id;
	}

	public AppInfo getAppinfo() {
		return appinfo;
	}

	public void setAppinfo(AppInfo appinfo) {
		this.appinfo = appinfo;
	}

	public SrvInfo getSrvinfo() {
		return srvinfo;
	}

	public void setSrvinfo(SrvInfo srvinfo) {
		this.srvinfo = srvinfo;
	}


}
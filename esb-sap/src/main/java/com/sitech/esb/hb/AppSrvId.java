package com.sitech.esb.hb;



/**
 * AppsrvId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AppSrvId implements java.io.Serializable {

	// Fields

    private Long srvId;
    private Long appId;
	public Long getSrvId() {
		return srvId;
	}
	public void setSrvId(Long srvId) {
		this.srvId = srvId;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}

    /** default constructor */
    public AppSrvId() {
    }

    
    /** full constructor */
    public AppSrvId(Long appId, Long srvId) {
        this.appId = appId;
        this.srvId = srvId;
    }
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AppSrvId) ) return false;
		 AppSrvId castOther = ( AppSrvId ) other; 
        
		 return ( (this.getSrvId()==castOther.getSrvId()) || ( this.getSrvId()!=null && castOther.getSrvId()!=null && this.getSrvId().equals(castOther.getSrvId()) ) )
&& ( (this.getAppId()==castOther.getAppId()) || ( this.getAppId()!=null && castOther.getAppId()!=null && this.getAppId().equals(castOther.getAppId()) ) );
  }
  
  public int hashCode() {
        int result = 17;
        
        result = 37 * result + ( getSrvId() == null ? 0 : this.getSrvId().hashCode() );
        result = 37 * result + ( getAppId() == null ? 0 : this.getAppId().hashCode() );
        return result;
  }  
}
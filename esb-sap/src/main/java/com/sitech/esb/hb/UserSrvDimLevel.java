package com.sitech.esb.hb;



/**
 * UserSrvDimLevel generated by MyEclipse - Hibernate Tools
 */

public class  UserSrvDimLevel  implements java.io.Serializable {


    // Fields    

     private Long userSrvDimId;
     private SrvDimension srvDimension;
     private Userinfo userinfo;
     private Long treeSrvLevel;

    // Constructors

    /** default constructor */
    public UserSrvDimLevel() {
    }

    
    /** full constructor */
    public UserSrvDimLevel(SrvDimension srvDimension, Userinfo userinfo, Long treeSrvLevel) {
        this.srvDimension = srvDimension;
        this.userinfo = userinfo;
        this.treeSrvLevel = treeSrvLevel;
    }

   
    // Property accessors

    public Long getUserSrvDimId() {
        return this.userSrvDimId;
    }
    
    public void setUserSrvDimId(Long userSrvDimId) {
        this.userSrvDimId = userSrvDimId;
    }

    public Long getTreeSrvLevel() {
        return this.treeSrvLevel;
    }
    
    public void setTreeSrvLevel(Long treeSrvLevel) {
        this.treeSrvLevel = treeSrvLevel;
    }


	public SrvDimension getSrvDimension() {
		return srvDimension;
	}


	public void setSrvDimension(SrvDimension srvDimension) {
		this.srvDimension = srvDimension;
	}


	public Userinfo getUserinfo() {
		return userinfo;
	}


	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}
   








}
package com.sitech.esb.hb;

import java.sql.Blob;



/**
 * SrvAccesory entity provides the base persistence definition of the SrvAccesory entity. @author MyEclipse Persistence Tools
 */

public class  SrvAccesory  implements java.io.Serializable {


    // Fields    

     private Long srvAcryid;
     private SrvInfo srvInfo;
     private String srvAcryname;
     private String srvAcrytype;
     private Blob srvAcrycontent;


    // Constructors

    /** default constructor */
    public SrvAccesory() {
    }

	/** minimal constructor */
    public SrvAccesory(SrvInfo srvInfo, String srvAcryname, Blob srvAcrycontent) {
        this.srvInfo = srvInfo;
        this.srvAcryname = srvAcryname;
        this.srvAcrycontent = srvAcrycontent;
    }
    
    /** full constructor */
    public SrvAccesory(SrvInfo srvInfo, String srvAcryname, String srvAcrytype, Blob srvAcrycontent) {
        this.srvInfo = srvInfo;
        this.srvAcryname = srvAcryname;
        this.srvAcrytype = srvAcrytype;
        this.srvAcrycontent = srvAcrycontent;
    }

   
    // Property accessors

    public Long getSrvAcryid() {
        return this.srvAcryid;
    }
    
    public void setSrvAcryid(Long srvAcryid) {
        this.srvAcryid = srvAcryid;
    }

    public String getSrvAcryname() {
        return this.srvAcryname;
    }
    
    public void setSrvAcryname(String srvAcryname) {
        this.srvAcryname = srvAcryname;
    }

    public String getSrvAcrytype() {
        return this.srvAcrytype;
    }
    
    public void setSrvAcrytype(String srvAcrytype) {
        this.srvAcrytype = srvAcrytype;
    }

	public SrvInfo getSrvInfo() {
		return srvInfo;
	}

	public void setSrvInfo(SrvInfo srvInfo) {
		this.srvInfo = srvInfo;
	}

	public Blob getSrvAcrycontent() {
		return srvAcrycontent;
	}

	public void setSrvAcrycontent(Blob srvAcrycontent) {
		this.srvAcrycontent = srvAcrycontent;
	}
   








}
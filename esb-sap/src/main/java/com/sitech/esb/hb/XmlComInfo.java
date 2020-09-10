package com.sitech.esb.hb;

import java.io.Serializable;
import java.sql.Clob;

/**
 * 
 * @author shenguang 陕西电信Web服务复合服务
 * 
 */
public class XmlComInfo extends SrvInfo implements Serializable {

	// Fields

	private Clob inXsd;
    private Clob outXsd;
    private Long isCheckOutXsd;
    private String retCodeXpath;
    private String retMsgXpath;
    private String retCodeXpath2;
	private Clob comLogic;

	// Constructors

	/** default constructor */
	public XmlComInfo() {
	}

	public Clob getInXsd() {
		return inXsd;
	}

	public void setInXsd(Clob inXsd) {
		this.inXsd = inXsd;
	}

	public Clob getOutXsd() {
		return outXsd;
	}

	public void setOutXsd(Clob outXsd) {
		this.outXsd = outXsd;
	}

	public Long getIsCheckOutXsd() {
		return isCheckOutXsd;
	}

	public void setIsCheckOutXsd(Long isCheckOutXsd) {
		this.isCheckOutXsd = isCheckOutXsd;
	}

	public String getRetCodeXpath() {
		return retCodeXpath;
	}

	public void setRetCodeXpath(String retCodeXpath) {
		this.retCodeXpath = retCodeXpath;
	}

	public String getRetMsgXpath() {
		return retMsgXpath;
	}

	public void setRetMsgXpath(String retMsgXpath) {
		this.retMsgXpath = retMsgXpath;
	}

	public String getRetCodeXpath2() {
		return retCodeXpath2;
	}

	public void setRetCodeXpath2(String retCodeXpath2) {
		this.retCodeXpath2 = retCodeXpath2;
	}

	public Clob getComLogic() {
		return comLogic;
	}

	public void setComLogic(Clob comLogic) {
		this.comLogic = comLogic;
	}

}

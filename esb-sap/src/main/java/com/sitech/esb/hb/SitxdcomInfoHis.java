package com.sitech.esb.hb;

import java.sql.Clob;

/**
 * SitxdcomInfoHis entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SitxdcomInfoHis extends SrvinfoHis implements
		java.io.Serializable {

	// Constructors
	private Long opId;
	private Clob comLogic;
	private Long usridInputno;
	private Long opridInputno;
	
	/** default constructor */
	public SitxdcomInfoHis() {
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Clob getComLogic() {
		return comLogic;
	}

	public void setComLogic(Clob comLogic) {
		this.comLogic = comLogic;
	}

	public Long getUsridInputno() {
		return usridInputno;
	}

	public void setUsridInputno(Long usridInputno) {
		this.usridInputno = usridInputno;
	}

	public Long getOpridInputno() {
		return opridInputno;
	}

	public void setOpridInputno(Long opridInputno) {
		this.opridInputno = opridInputno;
	}


}

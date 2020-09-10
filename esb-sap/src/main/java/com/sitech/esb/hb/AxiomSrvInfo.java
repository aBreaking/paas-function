package com.sitech.esb.hb;

import java.io.Serializable;
import java.sql.Clob;

public class AxiomSrvInfo extends SrvInfo implements Serializable {
	private Clob comLogic;
	private Clob wsdl;
	private String namespace;

	// Constructors

	/** default constructor */

	public Clob getComLogic() {
		return comLogic;
	}

	public void setComLogic(Clob comLogic) {
		this.comLogic = comLogic;
	}

	public Clob getWsdl() {
		return wsdl;
	}

	public void setWsdl(Clob wsdl) {
		this.wsdl = wsdl;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	

}

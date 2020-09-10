package com.sitech.esb.hb;

import java.io.Serializable;
import java.sql.Clob;

public class StrComSrvInfo extends SrvInfo implements Serializable { 
	// Fields

	private Clob comLogic;

	// Constructors

	/** default constructor */

	public Clob getComLogic() {
		return comLogic;
	}

	public void setComLogic(Clob comLogic) {
		this.comLogic = comLogic;
	}
}

package com.sitech.esb.hb;

import java.io.Serializable;
import java.sql.Clob;

public class ComSocketSrvInfo extends SrvInfo implements Serializable {
	private Clob comLogic;

	public Clob getComLogic() {
		return comLogic;
	}

	public void setComLogic(Clob comLogic) {
		this.comLogic = comLogic;
	}
	
}

package com.sitech.esb.hb;

/**
 * TxdoWdom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TxdoWTCPartionDb_id implements java.io.Serializable {

	// Fields

	private Long id;
	private String db_id_name;
	private String db_id_value;
	

	// Constructors

	/** default constructor */
	public TxdoWTCPartionDb_id() {
	}

	/** full constructor */
	public TxdoWTCPartionDb_id(Long id, String db_id_name, String db_id_value) {
		this.id = id;
		this.db_id_name = db_id_name;
		this.db_id_value = db_id_value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDb_id_name() {
		return db_id_name;
	}

	public void setDb_id_name(String db_id_name) {
		this.db_id_name = db_id_name;
	}

	public String getDb_id_value() {
		return db_id_value;
	}

	public void setDb_id_value(String db_id_value) {
		this.db_id_value = db_id_value;
	}

	

}
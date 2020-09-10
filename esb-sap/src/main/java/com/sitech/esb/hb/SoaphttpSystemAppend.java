package com.sitech.esb.hb;

/**
 * 是SoaphttpSystem的补充信息，通过id进行关联。 
 */
public class SoaphttpSystemAppend implements java.io.Serializable{
	private Long soaphttpSystemId;
	private String company;//产家
	private String engineerName;//维护人员姓名
	private String engineerContact;//维护人员联系方式
	private String reserved1;//预留字段1
	private String reserved2;//预留字段2
	private SoaphttpSystem soaphttpSystem;
	public SoaphttpSystemAppend() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SoaphttpSystemAppend(Long soaphttpSystemId, String company,
			String engineerName, String engineerContact, 
			String reserved1, String reserved2,SoaphttpSystem soaphttpSystem) {
		super();
		this.soaphttpSystemId = soaphttpSystemId;
		this.company = company;
		this.engineerName = engineerName;
		this.engineerContact = engineerContact;
		this.reserved1 = reserved1;
		this.reserved2 = reserved2;
		this.soaphttpSystem = soaphttpSystem;
	}
	public Long getSoaphttpSystemId() {
		return soaphttpSystemId;
	}
	public void setSoaphttpSystemId(Long soaphttpSystemId) {
		this.soaphttpSystemId = soaphttpSystemId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEngineerName() {
		return engineerName;
	}
	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}
	public String getEngineerContact() {
		return engineerContact;
	}
	public void setEngineerContact(String engineerContact) {
		this.engineerContact = engineerContact;
	}

	public String getReserved1() {
		return reserved1;
	}
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}
	
	public String getReserved2() {
		return reserved2;
	}
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	public SoaphttpSystem getSoaphttpSystem() {
		return soaphttpSystem;
	}
	public void setSoaphttpSystem(SoaphttpSystem soaphttpSystem) {
		this.soaphttpSystem = soaphttpSystem;
	}
	
	
}

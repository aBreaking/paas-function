package com.sitech.esb.hb;

import java.util.Date;
/**
 * û��ʹ��ԭ����OpHis�������½�OpRecords����¼���񷢲������¡�ɾ����Ӧ�õķ��������¡�ɾ����
 * Դϵͳ�ĵ����á��޸ġ�ɾ���� 
 */
public class OpRecord implements java.io.Serializable {
	private Long recordID;
	private Date opTime;//����ʱ��
	private String opUser;//�����û�
	private String opUserIP;//�û�ip
	private String serverIP;//�����ip
	private Long serverPort;//�����port
	private String opDescription;//��������
	public OpRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OpRecord(Long recordID, Date opTime, String opUser,
			String opUserIP, String serverIP, Long serverPort,
			String opDescription) {
		super();
		this.recordID = recordID;
		this.opTime = opTime;
		this.opUser = opUser;
		this.opUserIP = opUserIP;
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.opDescription = opDescription;
	}
	public Long getRecordID() {
		return recordID;
	}
	public void setRecordID(Long recordID) {
		this.recordID = recordID;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public String getOpUser() {
		return opUser;
	}
	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}
	public String getOpUserIP() {
		return opUserIP;
	}
	public void setOpUserIP(String opUserIP) {
		this.opUserIP = opUserIP;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public Long getServerPort() {
		return serverPort;
	}
	public void setServerPort(Long serverPort) {
		this.serverPort = serverPort;
	}
	public String getOpDescription() {
		return opDescription;
	}
	public void setOpDescription(String opDescription) {
		this.opDescription = opDescription;
	}
	
	
}

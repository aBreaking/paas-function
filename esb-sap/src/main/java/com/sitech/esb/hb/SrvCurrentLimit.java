package com.sitech.esb.hb;

import java.io.Serializable;
import java.util.Date;

/**
 * 限流
 */
public class SrvCurrentLimit implements Serializable{
	
	private static final long serialVersionUID = -3450088509487794427L;
	
	private Long id;
	private String target;		// 目标
	private String srvIds;		// 服务
	private Integer seconds;	// 秒数
	private Integer number;		// 次数
	private Date createDate;	// 创建时间
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public SrvCurrentLimit(){
	}
	
	public SrvCurrentLimit(Long id){
		this.id = id;
	}
	
	public SrvCurrentLimit(Long id, String target, String srvIds,
			Integer seconds, Integer number, Date createDate) {
		this.id = id;
		this.target = target;
		this.srvIds = srvIds;
		this.seconds = seconds;
		this.number = number;
		this.createDate = createDate;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getSrvIds() {
		return srvIds;
	}
	public void setSrvIds(String srvIds) {
		this.srvIds = srvIds;
	}
	public Integer getSeconds() {
		return seconds;
	}
	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
}
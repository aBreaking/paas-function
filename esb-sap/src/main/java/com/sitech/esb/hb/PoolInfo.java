package com.sitech.esb.hb;

import java.io.Serializable;

public class  PoolInfo implements Serializable {
	private Long poolId;
	private String poolName;
	private String poolChName;
	private String poolDescribe;
	public Long getPoolId() {
		return poolId;
	}
	public void setPoolId(Long poolId) {
		this.poolId = poolId;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public String getPoolChName() {
		return poolChName;
	}
	public void setPoolChName(String poolChName) {
		this.poolChName = poolChName;
	}
	public String getPoolDescribe() {
		return poolDescribe;
	}
	public void setPoolDescribe(String poolDescribe) {
		this.poolDescribe = poolDescribe;  
	}
	
	
	public  PoolInfo(Long poolId, String poolName, String poolChName, String poolDescribe) {
		super();
		this.poolId = poolId;
		this.poolName = poolName;
		this.poolChName = poolChName;
		this.poolDescribe = poolDescribe;
	}
	public  PoolInfo() {
	}
	
}

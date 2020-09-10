package com.sitech.esb.hb;

import java.io.Serializable;

import org.hibernate.Session;

public class PoolSrv implements Serializable {
	private Long poolId;
	private Long srvId;
	public Long getPoolId() {
		return poolId;
	}
	public void setPoolId(Long poolId) {
		this.poolId = poolId;
	}
	public Long getSrvId() {
		return srvId;
	}
	public void setSrvId(Long srvId) {
		this.srvId = srvId;
	}
	public PoolSrv(Long poolId, Long srvId) {
		super();
		this.poolId = poolId;
		this.srvId = srvId;
	}
	public PoolSrv() {
		super();
	}
	/*public static void main(String[] args) {
		PoolSrv poolSrv = new PoolSrv();
		poolSrv.setPoolId(new Long(1));
		poolSrv.setSrvId(new Long(2));
		Session s = HibernateSessionFactory.getSession();
		s.save(poolSrv);
	}*/
}

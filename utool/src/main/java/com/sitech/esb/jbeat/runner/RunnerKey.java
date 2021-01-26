package com.sitech.esb.jbeat.runner;

import java.util.Objects;

/**
 * 线程运行中需要用一个key 来标识该线程，用来存储运行时的上下文信息
 * @author liwei_paas
 * @date 2021/1/26
 */
public class RunnerKey {
    String pool; // 所属集群
    String hostName; //主机名
    String hostIp; //具体主机ip

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String qualifiedKeyAfterHostName(String key){
        return pool + "." + hostName + "." + key;
    }

    public String toHostName(){
        return pool + "." + hostName;
    }

    @Override
    public String toString() {
        return pool+"."+hostName+"."+hostIp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RunnerKey runnerKey = (RunnerKey) o;
        return Objects.equals(pool, runnerKey.pool) &&
                Objects.equals(hostName, runnerKey.hostName) &&
                Objects.equals(hostIp, runnerKey.hostIp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pool, hostName, hostIp);
    }
}

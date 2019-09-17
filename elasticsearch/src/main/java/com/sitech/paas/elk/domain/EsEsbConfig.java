package com.sitech.paas.elk.domain;

/**
 * @{USER}
 * @{DATE}
 */
public class EsEsbConfig {
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;
    /** es的url地址 */
    private String esUrl;
    /**  */
    private String esUser;
    /**  */
    private String esPwd;
    /** 该esb保存的哪些esb池的数据 */
    private String esbPool;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEsUrl() {
        return esUrl;
    }

    public void setEsUrl(String esUrl) {
        this.esUrl = esUrl;
    }

    public String getEsUser() {
        return esUser;
    }

    public void setEsUser(String esUser) {
        this.esUser = esUser;
    }

    public String getEsPwd() {
        return esPwd;
    }

    public void setEsPwd(String esPwd) {
        this.esPwd = esPwd;
    }

    public String getEsbPool() {
        return esbPool;
    }

    public void setEsbPool(String esbPool) {
        this.esbPool = esbPool;
    }
}

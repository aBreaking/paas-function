package com.sitech.esb.hb;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

/**
 * EjbSrvinfo generated by MyEclipse Persistence Tools
 */

public class EjbSrvinfo extends SrvInfo implements java.io.Serializable {

	// Fields

	private Long srvId;
	private String sserviceName;
	private String remoteInterfaceName;
	private String homeInterfaceName;
	private String jndiName;
	private String providerUrl;
	private String contextFactory;
	private String remoteIfsrc;
	private String homeIfsrc;
	private String jndiUser;
	private String jndiPwd;
	private Blob remoteIfcontent;
	private Blob homeIfcontent;
	private Long isInputXml;
	private Long isOutputXml;
	private String retcodeXpath;
	private String retcodeXpath2;
	private String retmsgXpath;
	private String outXpaths;
	private String filterXpaths;
	private Blob wraperContent;
	private String ejbVersion;
	private Set refClasses = new HashSet(0);
	// Constructors

	/** default constructor */
	public EjbSrvinfo() {
	}

	/** minimal constructor */
	public EjbSrvinfo(String sserviceName, String remoteInterfaceName,
			String jndiName, String providerUrl, String contextFactory,
			String remoteIfsrc, Long isInputXml, Long isOutputXml,
			String ejbVersion) {
		this.sserviceName = sserviceName;
		this.remoteInterfaceName = remoteInterfaceName;
		this.jndiName = jndiName;
		this.providerUrl = providerUrl;
		this.contextFactory = contextFactory;
		this.remoteIfsrc = remoteIfsrc;
		this.isInputXml = isInputXml;
		this.isOutputXml = isOutputXml;
		this.ejbVersion = ejbVersion;
	}

	/** full constructor */
	public EjbSrvinfo(String sserviceName, String remoteInterfaceName,
			String homeInterfaceName, String jndiName, String providerUrl,
			String contextFactory, String remoteIfsrc, String homeIfsrc,
			String jndiUser, String jndiPwd, Blob remoteIfcontent,
			Blob homeIfcontent, Long isInputXml, Long isOutputXml,
			String retcodeXpath, String retcodeXpath2, String retmsgXpath,
			String outXpaths, String filterXpaths, Blob wraperContent,
			String ejbVersion) {
		this.sserviceName = sserviceName;
		this.remoteInterfaceName = remoteInterfaceName;
		this.homeInterfaceName = homeInterfaceName;
		this.jndiName = jndiName;
		this.providerUrl = providerUrl;
		this.contextFactory = contextFactory;
		this.remoteIfsrc = remoteIfsrc;
		this.homeIfsrc = homeIfsrc;
		this.jndiUser = jndiUser;
		this.jndiPwd = jndiPwd;
		this.remoteIfcontent = remoteIfcontent;
		this.homeIfcontent = homeIfcontent;
		this.isInputXml = isInputXml;
		this.isOutputXml = isOutputXml;
		this.retcodeXpath = retcodeXpath;
		this.retcodeXpath2 = retcodeXpath2;
		this.retmsgXpath = retmsgXpath;
		this.outXpaths = outXpaths;
		this.filterXpaths = filterXpaths;
		this.wraperContent = wraperContent;
		this.ejbVersion = ejbVersion;
	}

	// Property accessors

	public Long getSrvId() {
		return this.srvId;
	}

	public void setSrvId(Long srvId) {
		this.srvId = srvId;
	}

	public String getSserviceName() {
		return this.sserviceName;
	}

	public void setSserviceName(String sserviceName) {
		this.sserviceName = sserviceName;
	}

	public String getRemoteInterfaceName() {
		return this.remoteInterfaceName;
	}

	public void setRemoteInterfaceName(String remoteInterfaceName) {
		this.remoteInterfaceName = remoteInterfaceName;
	}

	public String getHomeInterfaceName() {
		return this.homeInterfaceName;
	}

	public void setHomeInterfaceName(String homeInterfaceName) {
		this.homeInterfaceName = homeInterfaceName;
	}

	public String getJndiName() {
		return this.jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public String getProviderUrl() {
		return this.providerUrl;
	}

	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	}

	public String getContextFactory() {
		return this.contextFactory;
	}

	public void setContextFactory(String contextFactory) {
		this.contextFactory = contextFactory;
	}

	public String getRemoteIfsrc() {
		return this.remoteIfsrc;
	}

	public void setRemoteIfsrc(String remoteIfsrc) {
		this.remoteIfsrc = remoteIfsrc;
	}

	public String getHomeIfsrc() {
		return this.homeIfsrc;
	}

	public void setHomeIfsrc(String homeIfsrc) {
		this.homeIfsrc = homeIfsrc;
	}

	public String getJndiUser() {
		return this.jndiUser;
	}

	public void setJndiUser(String jndiUser) {
		this.jndiUser = jndiUser;
	}

	public String getJndiPwd() {
		return this.jndiPwd;
	}

	public void setJndiPwd(String jndiPwd) {
		this.jndiPwd = jndiPwd;
	}

	public Blob getRemoteIfcontent() {
		return this.remoteIfcontent;
	}

	public void setRemoteIfcontent(Blob remoteIfcontent) {
		this.remoteIfcontent = remoteIfcontent;
	}

	public Blob getHomeIfcontent() {
		return this.homeIfcontent;
	}

	public void setHomeIfcontent(Blob homeIfcontent) {
		this.homeIfcontent = homeIfcontent;
	}

	public Long getIsInputXml() {
		return this.isInputXml;
	}

	public void setIsInputXml(Long isInputXml) {
		this.isInputXml = isInputXml;
	}

	public Long getIsOutputXml() {
		return this.isOutputXml;
	}

	public void setIsOutputXml(Long isOutputXml) {
		this.isOutputXml = isOutputXml;
	}

	public String getRetcodeXpath() {
		return this.retcodeXpath;
	}

	public void setRetcodeXpath(String retcodeXpath) {
		this.retcodeXpath = retcodeXpath;
	}

	public String getRetcodeXpath2() {
		return this.retcodeXpath2;
	}

	public void setRetcodeXpath2(String retcodeXpath2) {
		this.retcodeXpath2 = retcodeXpath2;
	}

	public String getRetmsgXpath() {
		return this.retmsgXpath;
	}

	public void setRetmsgXpath(String retmsgXpath) {
		this.retmsgXpath = retmsgXpath;
	}

	public String getOutXpaths() {
		return this.outXpaths;
	}

	public void setOutXpaths(String outXpaths) {
		this.outXpaths = outXpaths;
	}

	public String getFilterXpaths() {
		return this.filterXpaths;
	}

	public void setFilterXpaths(String filterXpaths) {
		this.filterXpaths = filterXpaths;
	}

	public Blob getWraperContent() {
		return this.wraperContent;
	}

	public void setWraperContent(Blob wraperContent) {
		this.wraperContent = wraperContent;
	}

	public String getEjbVersion() {
		return this.ejbVersion;
	}

	public void setEjbVersion(String ejbVersion) {
		this.ejbVersion = ejbVersion;
	}

	public Set getRefClasses() {
		return refClasses;
	}

	public void setRefClasses(Set refClasses) {
		this.refClasses = refClasses;
	}

}
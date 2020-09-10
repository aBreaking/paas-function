package com.sitech.esb.hb;

import java.util.HashSet;
import java.util.Set;

/**
 * Event entity provides the base persistence definition of the Event
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  Event implements java.io.Serializable {

	// Fields

	private Long eventId;
	private String eventName;
	private Long validFlag;
	private Long needInprocess;
	private String processLogic;
	private String describle;
	private Set eventGens = new HashSet();
	private Set topics = new HashSet();
	private Set srvs = new HashSet();
	private Long logicUpdated;
	private String publicTips;
	private String srvProcessMsgLogic;
	private String srvEventDecideLogic;
	private Set srvEvents = new HashSet(0);

	// Constructors


	/** default constructor */
	public  Event() {
	}

	public String getSrvProcessMsgLogic() {
		return srvProcessMsgLogic;
	}

	public void setSrvProcessMsgLogic(String srvProcessMsgLogic) {
		this.srvProcessMsgLogic = srvProcessMsgLogic;
	}

	/** minimal constructor */
	public  Event(String eventName, Long needInprocess, Long validFlag, String processLogic) {
		this.eventName = eventName;
		this.needInprocess = needInprocess;
		this.validFlag = validFlag;
		this.processLogic = processLogic;
	}

	/** full constructor */
	public  Event(String eventName, Long needInprocess, Long validFlag, String processLogic,
			String describle, Set eventGens,Long logicUpdated,String publicTips) {
		this.eventName = eventName;
		this.needInprocess = needInprocess;
		this.validFlag = validFlag;
		this.describle = describle;
		this.processLogic =  processLogic;
		this.eventGens = eventGens;
		this.logicUpdated = logicUpdated;
		this.publicTips = publicTips;
		
	}

	// Property accessors

	public Long getLogicUpdated() {
		return logicUpdated;
	}

	public void setLogicUpdated(Long logicUpdated) {
		this.logicUpdated = logicUpdated;
	}

	public String getPublicTips() {
		return publicTips;
	}

	public void setPublicTips(String publicTips) {
		this.publicTips = publicTips;
	}

	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Long getNeedInprocess() {
		return needInprocess;
	}

	public void setNeedInprocess(Long needInprocess) {
		this.needInprocess = needInprocess;
	}

	public String getProcessLogic() {
		return processLogic;
	}

	public void setProcessLogic(String processLogic) {
		this.processLogic = processLogic;
	}

	public Long getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(Long validFlag) {
		this.validFlag = validFlag;
	}

	public String getDescrible() {
		return this.describle;
	}

	public void setDescrible(String describle) {
		this.describle = describle;
	}

	public Set getEventGens() {
		return this.eventGens;
	}

	public void setEventGens(Set eventGens) {
		this.eventGens = eventGens;
	}

	public Set getTopics() {
		return topics;
	}

	public void setTopics(Set topics) {
		this.topics = topics;
	}

	public Set getSrvs() {
		return srvs;
	}

	public void setSrvs(Set srvs) {
		this.srvs = srvs;
	}

	public String getSrvEventDecideLogic() {
		return srvEventDecideLogic;
	}

	public void setSrvEventDecideLogic(String srvEventDecideLogic) {
		this.srvEventDecideLogic = srvEventDecideLogic;
	}



	public Set getSrvEvents() {
		return srvEvents;
	}

	public void setSrvEvents(Set srvEvents) {
		this.srvEvents = srvEvents;
	}

}
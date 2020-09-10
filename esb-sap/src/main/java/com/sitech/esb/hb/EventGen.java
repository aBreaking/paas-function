package com.sitech.esb.hb;

/**
 * EventGen entity provides the base persistence definition of the
 * EventGen entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  EventGen implements java.io.Serializable {

	// Fields

	private EventGenId id;
	private Event event;
	private Long validFlag;
	private EventConsumer eventConsumer;

	// Constructors

	public EventConsumer getEventConsumer() {
		return eventConsumer;
	}

	public void setEventConsumer(EventConsumer eventConsumer) {
		this.eventConsumer = eventConsumer;
	}

	public Long getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Long validFlag) {
		this.validFlag = validFlag;
	}

	/** default constructor */
	public  EventGen() {
	}

	/** full constructor */
	public  EventGen(EventGenId id, Event event,Long validFlag,EventConsumer eventConsumer) {
		this.id = id;
		this.event = event;
		this.eventConsumer = eventConsumer;
	}

	// Property accessors

	public EventGenId getId() {
		return this.id;
	}

	public void setId(EventGenId id) {
		this.id = id;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
package com.sitech.esb.hb;

/**
 * EventTopic entity provides the base persistence definition of the
 * EventTopic entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  EventTopic implements java.io.Serializable {

	// Fields

	private EventTopicId id;

	// Constructors

	/** default constructor */
	public  EventTopic() {
	}

	/** full constructor */
	public  EventTopic(EventTopicId id) {
		this.id = id;
	}

	// Property accessors

	public EventTopicId getId() {
		return this.id;
	}

	public void setId(EventTopicId id) {
		this.id = id;
	}

}
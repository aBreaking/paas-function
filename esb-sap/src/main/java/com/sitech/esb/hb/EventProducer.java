package com.sitech.esb.hb;
// default package

import java.util.HashSet;
import java.util.Set;


/**
 * EventProducer generated by MyEclipse Persistence Tools
 */

public class  EventProducer  implements java.io.Serializable {


    // Fields    

     private Long eventProducerId;
     private String eventProducerName;
     private String eventProducerEpr;
     private String describe;
     private Set eventConsumers = new HashSet(0);


    // Constructors

    /** default constructor */
    public  EventProducer() {
    }

	/** minimal constructor */
    public  EventProducer(String eventProducerName, String eventProducerEpr) {
        this.eventProducerName = eventProducerName;
        this.eventProducerEpr = eventProducerEpr;
    }
    
    /** full constructor */
    public  EventProducer(String eventProducerName, String eventProducerEpr, String describe, Set eventConsumers) {
        this.eventProducerName = eventProducerName;
        this.eventProducerEpr = eventProducerEpr;
        this.describe = describe;
        this.eventConsumers = eventConsumers;
    }

   
    // Property accessors

    public Long getEventProducerId() {
        return this.eventProducerId;
    }
    
    public void setEventProducerId(Long eventProducerId) {
        this.eventProducerId = eventProducerId;
    }

    public String getEventProducerName() {
        return this.eventProducerName;
    }
    
    public void setEventProducerName(String eventProducerName) {
        this.eventProducerName = eventProducerName;
    }

    public String getEventProducerEpr() {
        return this.eventProducerEpr;
    }
    
    public void setEventProducerEpr(String eventProducerEpr) {
        this.eventProducerEpr = eventProducerEpr;
    }

    public String getDescribe() {
        return this.describe;
    }
    
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Set getEventConsumers() {
        return this.eventConsumers;
    }
    
    public void setEventConsumers(Set eventConsumers) {
        this.eventConsumers = eventConsumers;
    }
   








}
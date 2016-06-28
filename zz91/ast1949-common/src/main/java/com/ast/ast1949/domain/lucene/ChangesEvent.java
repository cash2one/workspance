/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-15 下午02:23:24
 */
package com.ast.ast1949.domain.lucene;

import java.io.Serializable;

/**
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class ChangesEvent implements Serializable{
	private static final long serialVersionUID = 4511508860521422090L;
    private String            changes;
    private String            subscriber;
    private Long              sequence;
    private Long 			primaryId;

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

	public void setPrimaryId(Long primaryId) {
		this.primaryId = primaryId;
	}

	public Long getPrimaryId() {
		return primaryId;
	}
}

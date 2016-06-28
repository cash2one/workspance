package com.ast.ast1949.domain.phone;

import java.util.Date;

public class PhonePpcVisit {
	private Integer id;
	private Integer targetId;
	private Integer cid;
	private Date gmtTarget;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Date getGmtTarget() {
		return gmtTarget;
	}

	public void setGmtTarget(Date gmtTarget) {
		this.gmtTarget = gmtTarget;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}

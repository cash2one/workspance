package com.zz91.ep.domain.mblog;

import java.util.Date;

public class MBlogSent {
	private Integer id;
	private Integer mblogId;
	private Integer targetId;
	private Integer topId;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public Integer getMblogId() {
		return mblogId;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public Integer getTopId() {
		return topId;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMblogId(Integer mblogId) {
		this.mblogId = mblogId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public void setTopId(Integer topId) {
		this.topId = topId;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}

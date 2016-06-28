/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-7-15
 */
package com.zz91.ep.domain.comp;

import java.io.Serializable;
import java.util.Date;

public class Favorite implements Serializable{

	private static final long serialVersionUID = -1485020001155697256L;
	
	private Integer id;
	private Integer uid;
	private Integer cid;
	private String title;
	private String url;
	private Short type;
	private Integer targetId;
	private Short delStatus;
	private Date gmtCreated;
	private Date gmtModified;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public Short getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(Short delStatus) {
		this.delStatus = delStatus;
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
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
}
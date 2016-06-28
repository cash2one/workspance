package com.zz91.ep.domain.common;

import java.util.Date;

/**
 * author:kongsj date:2013-8-3
 */
public class Video implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 17177736663482895L;

	private Integer id; // int(11) NOT NULL,
	private Integer targetId; // int(11) NOT NULL COMMENT '视频对应目标id',
	private String targetType; // char(1) NOT NULL COMMENT '视频对应类型\\n1：资讯 2：未定',
	private String content; // varchar(2000) DEFAULT '' COMMENT '视频分享地址',
	private String photoCover;//varchar(100) DEFAULT 视频封面图片
	private Date gmtCreated; // datetime DEFAULT NULL,
	private Date gmtModified; // datetime DEFAULT NULL,
	
	

	public String getPhotoCover() {
		return photoCover;
	}

	public void setPhotoCover(String photoCover) {
		this.photoCover = photoCover;
	}

	public Integer getId() {
		return id;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public String getTargetType() {
		return targetType;
	}

	public String getContent() {
		return content;
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

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}

package com.zz91.ep.domain.mblog;

import java.util.Date;

/**
 * author:fangchao date:2013-10-22
 */
public class MBlogSystem {
	private Integer id;
	private Integer fromId;// 来源id
	private Integer toId;// 目标id
	private String type;// 目标的状态
	private String isRead;// 标记有读
	private String content;// 系统发送内容
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public Integer getFromId() {
		return fromId;
	}

	public Integer getToId() {
		return toId;
	}

	public String getType() {
		return type;
	}

	public String getIsRead() {
		return isRead;
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

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public void setToId(Integer toId) {
		this.toId = toId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
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

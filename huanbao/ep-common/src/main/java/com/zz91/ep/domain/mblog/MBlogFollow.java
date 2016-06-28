package com.zz91.ep.domain.mblog;

import java.util.Date;

/**
 * author:fangchao date:2013-10-22
 */
public class MBlogFollow {
	private Integer id;
	private Integer infoId;// 关联账户id
	private Integer targetId;// 目标id
	private Integer groupId;// 组id
	private String followStatus;// 被关注状态
	private String type;// 互相关注
	private String noteName;//备注名称
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public String getFollowStatus() {
		return followStatus;
	}

	public String getType() {
		return type;
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

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public void setFollowStatus(String followStatus) {
		this.followStatus = followStatus;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getNoteName() {
		return noteName;
	}

	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}
	
}

package com.zz91.ep.domain.mblog;

import java.util.Date;

/**
 * author:fangchao date:2013-10-22
 */
public class MBlogComment {
	private Integer id;
	private Integer infoId;// 账户id
	private Integer mblogId;// 博文id
	private String targetType;// 博文类型
	private Integer targetId;// 目标id
	private String content;// ·评论内容
	private String isDelete;// 删除状态
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public Integer getMblogId() {
		return mblogId;
	}

	public String getTargetType() {
		return targetType;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public String getContent() {
		return content;
	}

	public String getIsDelete() {
		return isDelete;
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

	public void setMblogId(Integer mblogId) {
		this.mblogId = mblogId;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}

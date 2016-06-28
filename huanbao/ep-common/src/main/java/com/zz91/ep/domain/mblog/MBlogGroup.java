package com.zz91.ep.domain.mblog;

import java.util.Date;

/**
 * author:fangchao date:2013-10-22
 */
public class MBlogGroup {
	private Integer id;
	private Integer infoId;// 关联账户id
	private String groupName;// 组名称
	private String isDelete;// 删除状态
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public String getGroupName() {
		return groupName;
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

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

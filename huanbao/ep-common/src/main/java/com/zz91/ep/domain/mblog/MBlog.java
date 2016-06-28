package com.zz91.ep.domain.mblog;

import java.util.Date;

/**
 * author:fangchao date:2013-10-22
 */
public class MBlog {
	private Integer id;
	private Integer infoId;// 微交流资料id
	private String title;// 发布的话题标题
	private String content;// 发布的内容
	private String type;// 博文的类型
	private String isDelete;// 删除状态
	private Integer discussCount;// 评论数
	private Integer sentCount;// 转发的次数
	private String photoPath;//图片路径
	private Date gmtCreated;
	private Date gmtModified;
	private Integer count;//用于标记话题的讨论数
	
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Integer getId() {
		return id;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}


	public String getType() {
		return type;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public Integer getDiscussCount() {
		return discussCount;
	}

	public Integer getSentCount() {
		return sentCount;
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

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public void setType(String type) {
		this.type = type;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public void setDiscussCount(Integer discussCount) {
		this.discussCount = discussCount;
	}

	public void setSentCount(Integer sentCount) {
		this.sentCount = sentCount;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}

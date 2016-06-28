/*
 * 文件名称：CompAccount.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.comp;

import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：公司发布文章实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class CompNews implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// 编号
	private String account;// 帐号
	private Integer cid;//公司ID
	private String categoryCode;// 文章类别
	private String title;// 标题
	private String details;// 文章详细内容
	private Short pauseStatus;// 发布状态
	private Short checkStatus;// 审核状态
	private Short deleteStatus;// 删除标记
	private String checkPerson;// 审核人
	private Date gmtPublish;// 发布时间
	private Date gmtCreated;// 创建时间
	private Date gmtModified;// 更新时间
    private Integer viewCount;//浏览数
	private String tags;//标签
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Short getPauseStatus() {
		return pauseStatus;
	}

	public void setPauseStatus(Short pauseStatus) {
		this.pauseStatus = pauseStatus;
	}

	public Short getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	public Date getGmtPublish() {
		return gmtPublish;
	}

	public void setGmtPublish(Date gmtPublish) {
		this.gmtPublish = gmtPublish;
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

	public void setDeleteStatus(Short deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Short getDeleteStatus() {
		return deleteStatus;
	}
	
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	public String getTags() {
		return tags;
	}
	
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	
	public Integer getViewCount() {
		return viewCount;
	}

	

	

}
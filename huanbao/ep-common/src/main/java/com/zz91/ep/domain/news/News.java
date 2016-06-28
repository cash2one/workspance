/*
 * 文件名称：News.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.news;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：新闻实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class News implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// 编号
	private String title;// 标题
	private String titleIndex;// 短标题
	private String categoryCode;// 类型
	private String description;// 简介
	private String details;// 详细
	private String detailsQuery;// 详细（纯文本）
	private String tags;// 标签
	private String htmlPath;// 静态地址
	private String adminAccount;// 编辑者
	private String adminName;// 编辑者姓名
	private String newsSource;// 消息来源
	private String newsSourceUrl;// 来源地址
	private Integer viewCount;// 查看次数
	private Integer pauseStatus;// 发布状态
	private Date gmtPublish;// 发布时间
	private Date gmtCreated;// 创建时间
	private Date gmtModified;// 修改时间

	public String getAdminAccount() {
		return adminAccount;
	}

	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleIndex() {
		return titleIndex;
	}

	public void setTitleIndex(String titleIndex) {
		this.titleIndex = titleIndex;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDetailsQuery() {
		return detailsQuery;
	}

	public void setDetailsQuery(String detailsQuery) {
		this.detailsQuery = detailsQuery;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public String getNewsSource() {
		return newsSource;
	}

	public void setNewsSource(String newsSource) {
		this.newsSource = newsSource;
	}

	public String getNewsSourceUrl() {
		return newsSourceUrl;
	}

	public void setNewsSourceUrl(String newsSourceUrl) {
		this.newsSourceUrl = newsSourceUrl;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getPauseStatus() {
		return pauseStatus;
	}

	public void setPauseStatus(Integer pauseStatus) {
		this.pauseStatus = pauseStatus;
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

}
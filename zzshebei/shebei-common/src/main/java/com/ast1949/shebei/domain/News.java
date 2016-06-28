package com.ast1949.shebei.domain;

import java.io.Serializable;
import java.util.Date;

public class News implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String title;//资讯标题
	private String description;//描述信息
	private String categoryCode;//资讯类别
	private Short type;// 资讯/报价
	private String details;//详细信息
	private String detailsQuery;//用于信息查询（详细信息的纯文本）
	private Integer viewCount;//访问量
	private String source;//来源
	private String sourceUrl;//来源地址
	private String tags;//标签
	private Date gmtShow;//展示时间
	private String photoCover;//图片地址
	private String auth;//作者
	private Date gmtPublish;//发布时间
	private Date gmtCreated;//创建时间
	private Date gmtModified;//最后修改时间
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
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
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Date getGmtShow() {
		return gmtShow;
	}
	public void setGmtShow(Date gmtShow) {
		this.gmtShow = gmtShow;
	}
	public String getPhotoCover() {
		return photoCover;
	}
	public void setPhotoCover(String photoCover) {
		this.photoCover = photoCover;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
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

package com.ast.ast1949.domain.news;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class NewsTech extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9087584628213931121L;
	private Integer id;
	private String title;
	private String content;
	private String tags;
	private String categoryCode;
	private String isDel;
	private Date postTime;
	private Date gmtCreated;
	private Date gmtModified;
	private Integer viewCount;
	private String label;
	
	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getTags() {
		return tags;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public Date getPostTime() {
		return postTime;
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

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

}

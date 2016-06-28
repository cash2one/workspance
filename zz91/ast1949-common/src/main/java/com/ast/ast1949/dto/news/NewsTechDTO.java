package com.ast.ast1949.dto.news;

import java.io.Serializable;
import java.util.Date;

public class NewsTechDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1121517951469312074L;
	private String title;
	private String content;
	private String tags;
	private String categoryCode;
	private String isDel;
	private Date postTime;
	private Integer viewCount;
	
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
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
	public String getIsDel() {
		return isDel;
	}
	public Date getPostTime() {
		return postTime;
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
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}

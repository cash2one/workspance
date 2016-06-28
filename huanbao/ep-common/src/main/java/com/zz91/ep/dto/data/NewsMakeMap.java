package com.zz91.ep.dto.data;

import java.io.Serializable;

public class NewsMakeMap implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer title;
	private Integer titleIndex;
	private Integer description;
	private Integer details;
	private String categoryCode;
	private Integer tags;
	private Integer newsSource;
	private Integer newsSourceUrl;
	private Integer gmtPublish;
	

	public void setTitle(Integer title) {
		this.title = title;
	}
	public Integer getTitle() {
		return title;
	}
	public void setTitleIndex(Integer titleIndex) {
		this.titleIndex = titleIndex;
	}
	public Integer getTitleIndex() {
		return titleIndex;
	}
	public void setDescription(Integer description) {
		this.description = description;
	}
	public Integer getDescription() {
		return description;
	}
	public void setDetails(Integer details) {
		this.details = details;
	}
	public Integer getDetails() {
		return details;
	}
	public void setTags(Integer tags) {
		this.tags = tags;
	}
	public Integer getTags() {
		return tags;
	}
	public void setNewsSource(Integer newsSource) {
		this.newsSource = newsSource;
	}
	public Integer getNewsSource() {
		return newsSource;
	}
	public void setNewsSourceUrl(Integer newsSourceUrl) {
		this.newsSourceUrl = newsSourceUrl;
	}
	public Integer getNewsSourceUrl() {
		return newsSourceUrl;
	}
	public void setGmtPublish(Integer gmtPublish) {
		this.gmtPublish = gmtPublish;
	}
	public Integer getGmtPublish() {
		return gmtPublish;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryCode() {
		return categoryCode;
	}

}

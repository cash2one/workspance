package com.zz91.mission.domain.huzhu;

import java.io.Serializable;

public class BbsPostCaiJi implements Serializable {
	/**
	 *  互助采集 bbs_post_caiji
	 */
	private static final long serialVersionUID = 1L;
	private Integer bbsPostCategoryId;
	private Integer bbsPostAssistId;
	private String 	title;
	private String 	content;
	private String tags;
	private String category;
	public Integer getBbsPostCategoryId() {
		return bbsPostCategoryId;
	}
	public void setBbsPostCategoryId(Integer bbsPostCategoryId) {
		this.bbsPostCategoryId = bbsPostCategoryId;
	}
	public Integer getBbsPostAssistId() {
		return bbsPostAssistId;
	}
	public void setBbsPostAssistId(Integer bbsPostAssistId) {
		this.bbsPostAssistId = bbsPostAssistId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}

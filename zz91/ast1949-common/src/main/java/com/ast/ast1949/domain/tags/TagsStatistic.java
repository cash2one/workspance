package com.ast.ast1949.domain.tags;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class TagsStatistic extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String statisticCategoryCode;// stat_cat_code VARCHAR(20) '标签统计类型（点击量，使用量，搜索量）' ,
	private Integer tagId;//  `tag_id` INT(20)  '标签ID' ,
	private String tagName;// 
	private Integer score;// `score` int(12) '统计得分',
	private Integer creator;// `creator` INT(20)  '创建人' ,
	private Date gmtCreated; //gmt_created  DATETIME '创建时间' ,
	private String description;//`description` VARCHAR(100) '统计说明',

	public String getStatisticCategoryCode() {
		return statisticCategoryCode;
	}

	public void setStatisticCategoryCode(String statisticCategoryCode) {
		this.statisticCategoryCode = statisticCategoryCode;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

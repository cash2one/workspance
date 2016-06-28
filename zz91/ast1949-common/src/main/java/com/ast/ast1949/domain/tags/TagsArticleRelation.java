package com.ast.ast1949.domain.tags;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class TagsArticleRelation extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer tagId;//  `tag_id` INT(20)  '标签ID' ,
	private Integer articleId;// `article_id` INT(20)  '文章ID' ,
	private String tagName;// `tag_name` VARCHAR(45)  '标签名称' ,
	private String articleTitle;//`article_title` VARCHAR(200)  '文章标题',
	private String articleModuleCode;//`art_module_code` VARCHAR(200)  '文章所属版块' ,
	private String articleCategoryCode;//`art_cat_code` VARCHAR(200) '文章关联产品分类' ,
	private Integer creator;// `creator` INT(20)  '创建人' ,
	private Date gmtCreated; //gmt_created  DATETIME '创建时间' 
	private Date gmtModified; //gmt_modified  DATETIME '创建时间' 

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleModuleCode() {
		return articleModuleCode;
	}

	public void setArticleModuleCode(String articleModuleCode) {
		this.articleModuleCode = articleModuleCode;
	}

	public String getArticleCategoryCode() {
		return articleCategoryCode;
	}

	public void setArticleCategoryCode(String articleCategoryCode) {
		this.articleCategoryCode = articleCategoryCode;
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

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}

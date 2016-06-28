/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-30
 */
package com.ast.ast1949.domain.news;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * 新闻（资讯）
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class NewsDO extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Fields
	private Integer bbsPostId; 		//帖子编号
	private Integer titleStyleId;	//样式编号
	private String title;			//标题
	private String indexTitle;		//首页标题
	private String markCategoryCode;	//标注类别
	private String goUrl;			//跳转链接
	private String seoDetails;		//SEO描述
	private Date gmtOrder;			//排序时间
	private Date gmtPublished;		//发布时间
	private Date gmtCreated;		//创建时间
	private Date gmtModified;		//修改时间
	
	private String label;	//标注名称（此字段不记录在数据库中）

	// Constructors

//	/** default constructor */
//	public NewsDO() {
//	}
//
//	/** minimal constructor */
//	public NewsDO(Integer id) {
//		this.setId(id);
//	}
//
//	/** full constructor */
//	public NewsDO(Integer id, Integer bbsPostId, String title, String indexTitle,
//			Integer markCategoryId, Integer titleStyleId, String goUrl,
//			String seoDetails, Date gmtPublished, Date gmtOrder,
//			Date gmtCreated, Date gmtModified) {
//		this.setId(id);
//		this.bbsPostId = bbsPostId;
//		this.title = title;
//		this.indexTitle = indexTitle;
//		this.markCategoryId = markCategoryId;
//		this.titleStyleId = titleStyleId;
//		this.goUrl = goUrl;
//		this.seoDetails = seoDetails;
//		this.gmtPublished = gmtPublished;
//		this.gmtOrder = gmtOrder;
//		this.gmtCreated = gmtCreated;
//		this.gmtModified = gmtModified;
//	}

	// Property accessors
	
	public Integer getBbsPostId() {
		return this.bbsPostId;
	}

	public void setBbsPostId(Integer bbsPostId) {
		this.bbsPostId = bbsPostId;
	}

	public Integer getTitleStyleId() {
		return this.titleStyleId;
	}

	public void setTitleStyleId(Integer titleStyleId) {
		this.titleStyleId = titleStyleId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIndexTitle() {
		return this.indexTitle;
	}

	public void setIndexTitle(String indexTitle) {
		this.indexTitle = indexTitle;
	}

	public String getMarkCategoryCode() {
		return markCategoryCode;
	}

	public void setMarkCategoryCode(String markCategoryCode) {
		this.markCategoryCode = markCategoryCode;
	}

	public String getGoUrl() {
		return this.goUrl;
	}

	public void setGoUrl(String goUrl) {
		this.goUrl = goUrl;
	}

	public String getSeoDetails() {
		return this.seoDetails;
	}

	public void setSeoDetails(String seoDetails) {
		this.seoDetails = seoDetails;
	}

	public Date getGmtOrder() {
		return this.gmtOrder;
	}

	public void setGmtOrder(Date gmtOrder) {
		this.gmtOrder = gmtOrder;
	}
	
	public Date getGmtPublished() {
		return this.gmtPublished;
	}

	public void setGmtPublished(Date gmtPublished) {
		this.gmtPublished = gmtPublished;
	}

	public Date getGmtCreated() {
		return this.gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return this.gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
}

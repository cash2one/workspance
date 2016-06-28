/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-19 by Rolyer.
 */
package com.ast.ast1949.domain.price;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * 资讯/新闻实体类：News表
 * @author Rolyer (rolyer.live@gmail.com)
 */
public class PriceDO extends DomainSupport {

	
	/**
	 * 资讯/新闻实体类序列化UID
	 */
	private static final long serialVersionUID = 1L;
	
	// 字段
	//private Integer id;						//编号
	private String title;					//资讯标题
	private String indexTitle;				//首页标题
	private Integer baseTypeId;				//基本类别
	private Integer typeId;					//主类别
	private Integer assistTypeId;           //辅助类别
	private String goUrl;					//跳转地址
	private String content;					//资讯内容
	private String seoDetails;				//SEO内容介绍
	private Date gmtOrder;					//排序时间
	private Date gmtCreated;				//创建时间
	private Date gmtModified;				//修改时间
	private String isChecked;				//是否审核(0 否 1 是)
	private String isIssue;				//是否直接发布(0 否 1 是)
	private String tags;					//标签
	private String fontColor;				//字体颜色
	private String fontSize;				//字体大小
	private String isBlod;					//是否加粗(0 否 1 是)
	private Integer clickNumber;			//初始点击率
	private Integer realClickNumber;		//真实点击率
	private String isTodayReview;			//是否推荐为今日导读(0 否 1 是)
	private Integer ip;
	private String contentQuery;

	//构造函数 

	/** default constructor */
	public PriceDO() {
	}

	/** minimal constructor */
	public PriceDO(Integer id) {
		setId(id);
	}

	/** full constructor */
	public PriceDO(Integer id, String title, String indexTitle,
			Integer baseTypeId, Integer typeId, Integer assistTypeId,
			String goUrl, String content, String seoDetails, Date gmtOrder,
			Date gmtCreated, Date gmtModified, String isChecked,
			String isIssue, String tags, String fontColor, String fontSize,
			String isBlod, Integer clickNumber,Integer realClickNumber, String isTodayReview) {
		setId(id);
		this.title = title;
		this.indexTitle = indexTitle;
		this.baseTypeId = baseTypeId;
		this.typeId = typeId;
		this.assistTypeId = assistTypeId;
		this.goUrl = goUrl;
		this.content = content;
		this.seoDetails = seoDetails;
		this.gmtOrder = gmtOrder;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
		this.isChecked = isChecked;
		this.isIssue = isIssue;
		this.tags = tags;
		this.fontColor = fontColor;
		this.fontSize = fontSize;
		this.isBlod = isBlod;
		this.clickNumber = clickNumber;
		this.realClickNumber = realClickNumber;
		this.isTodayReview = isTodayReview;
	}
	
	
	//属性
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIndexTitle() {
		return indexTitle;
	}

	public void setIndexTitle(String indexTitle) {
		this.indexTitle = indexTitle;
	}

	public Integer getBaseTypeId() {
		return baseTypeId;
	}

	public void setBaseTypeId(Integer baseTypeId) {
		this.baseTypeId = baseTypeId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getAssistTypeId() {
		return assistTypeId;
	}

	public void setAssistTypeId(Integer assistTypeId) {
		this.assistTypeId = assistTypeId;
	}

	public String getGoUrl() {
		return goUrl;
	}

	public void setGoUrl(String goUrl) {
		this.goUrl = goUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSeoDetails() {
		return seoDetails;
	}

	public void setSeoDetails(String seoDetails) {
		this.seoDetails = seoDetails;
	}

	public Date getGmtOrder() {
		return gmtOrder;
	}

	public void setGmtOrder(Date gmtOrder) {
		this.gmtOrder = gmtOrder;
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

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getIsIssue() {
		return isIssue;
	}

	public void setIsIssue(String isIssue) {
		this.isIssue = isIssue;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getIsBlod() {
		return isBlod;
	}

	public void setIsBlod(String isBlod) {
		this.isBlod = isBlod;
	}

	public Integer getClickNumber() {
		return clickNumber;
	}

	public void setRealClickNumber(Integer realClickNumber) {
		this.realClickNumber = realClickNumber;
	}
	
	public Integer getRealClickNumber() {
		return realClickNumber;
	}

	public void setClickNumber(Integer clickNumber) {
		this.clickNumber = clickNumber;
	}

	public String getIsTodayReview() {
		return isTodayReview;
	}

	public void setIsTodayReview(String isTodayReview) {
		this.isTodayReview = isTodayReview;
	}

	public Integer getIp() {
		return ip;
	}

	public void setIp(Integer ip) {
		this.ip = ip;
	}

	public String getContentQuery() {
		return contentQuery;
	}

	public void setContentQuery(String contentQuery) {
		this.contentQuery = contentQuery;
	}
	
	

}
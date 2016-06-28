/*
 * 文件名称：WebsiteStatistics.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.comp;

import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：网站数据统计实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class WebsiteStatistics implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;//ID
	
	private String gmtData;//统计日期
	
	private Integer registerStp1;//只完成第一注册用户数
	
	private Integer registerStp2;//完成第二步注册用户数
	
	private Integer messageSupply;//供应信息询盘数
	
	private Integer messageBuy;//求购信息询盘数
	
	private Integer messageCompany;//公司留言数
	
	private Integer messageAdmin;//后台代发询盘信息数
	
	private Integer companyNews;//发布公司文章数
	
	private Integer supply;//发布供应信息数
	
	private Integer buy;//发布求购信息数
	
	private Integer publishCompany;//发布信息公司数
	
	private Integer publishNews;//发布文章公司数
	
	private Integer loginCount;//登录次数
	
	private Date gmtCreated;
	
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGmtData() {
		return gmtData;
	}

	public void setGmtData(String gmtData) {
		this.gmtData = gmtData;
	}

	public Integer getRegisterStp1() {
		return registerStp1;
	}

	public void setRegisterStp1(Integer registerStp1) {
		this.registerStp1 = registerStp1;
	}

	public Integer getRegisterStp2() {
		return registerStp2;
	}

	public void setRegisterStp2(Integer registerStp2) {
		this.registerStp2 = registerStp2;
	}

	public Integer getMessageSupply() {
		return messageSupply;
	}

	public void setMessageSupply(Integer messageSupply) {
		this.messageSupply = messageSupply;
	}

	public Integer getMessageBuy() {
		return messageBuy;
	}

	public void setMessageBuy(Integer messageBuy) {
		this.messageBuy = messageBuy;
	}

	public Integer getMessageCompany() {
		return messageCompany;
	}

	public void setMessageCompany(Integer messageCompany) {
		this.messageCompany = messageCompany;
	}

	public Integer getMessageAdmin() {
		return messageAdmin;
	}

	public void setMessageAdmin(Integer messageAdmin) {
		this.messageAdmin = messageAdmin;
	}

	public Integer getCompanyNews() {
		return companyNews;
	}

	public void setCompanyNews(Integer companyNews) {
		this.companyNews = companyNews;
	}

	public Integer getSupply() {
		return supply;
	}

	public void setSupply(Integer supply) {
		this.supply = supply;
	}

	public Integer getBuy() {
		return buy;
	}

	public void setBuy(Integer buy) {
		this.buy = buy;
	}

	public Integer getPublishCompany() {
		return publishCompany;
	}

	public void setPublishCompany(Integer publishCompany) {
		this.publishCompany = publishCompany;
	}

	public Integer getPublishNews() {
		return publishNews;
	}

	public void setPublishNews(Integer publishNews) {
		this.publishNews = publishNews;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
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
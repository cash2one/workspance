/**
 * @author shiqp
 * @date 2014-09-11
 */
package com.ast.ast1949.domain.analysis;

import java.util.Date;

public class AnalysisMyrcVisitor {
	private Integer companyId;// 来访公司id
	private Integer targetId;// 被访问公司id
	private String address;// 来访地区
	private String title;// 页面标题
	private String url;// 页面链接
	private Date gmtTarget;// 来访时间
	private Date gmtCreated;
	private Date gmtModified;
	private String name;// 公司名称
	private String contact;// 联系人
	private String business;// 主营业务
	private Integer telFlag;//有无来电
	private Integer inquiryFlag;//有无询盘

	public Integer getTelFlag() {
		return telFlag;
	}

	public void setTelFlag(Integer telFlag) {
		this.telFlag = telFlag;
	}

	public Integer getInquiryFlag() {
		return inquiryFlag;
	}

	public void setInquiryFlag(Integer inquiryFlag) {
		this.inquiryFlag = inquiryFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getGmtTarget() {
		return gmtTarget;
	}

	public void setGmtTarget(Date gmtTarget) {
		this.gmtTarget = gmtTarget;
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

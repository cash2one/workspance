/*
 * 文件名称：Exhibit.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.exhibit;

import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：展会信息实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class Exhibit implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// 编号
	private String name; //名称
	private String provinceCode;// 省份
	private String areaCode;// 地区编号
	private Date gmtStart;// 展会开始时间
	private Date gmtEnd;// 结束时间
	private String plateCategoryCode;// 所属板块编号
	private String industryCode;// 所属行业编号
	private String showName;// 展馆名称
	private String details;// 展会内容
	private String organizers;// 主办方
	private Date gmtPublish;// 发布时间
	private Date gmtSort;// 排序时间
	private Integer pauseStatus;// 发布状态 0 未发布 1 已发布
	private Date gmtCreated;
	private Date gmtModified;
	private String adminAccount;// 创建者帐号
	private String adminName;// 创建者名字

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Date getGmtStart() {
		return gmtStart;
	}

	public void setGmtStart(Date gmtStart) {
		this.gmtStart = gmtStart;
	}

	public Date getGmtEnd() {
		return gmtEnd;
	}

	public void setGmtEnd(Date gmtEnd) {
		this.gmtEnd = gmtEnd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPlateCategoryCode() {
		return plateCategoryCode;
	}

	public void setPlateCategoryCode(String plateCategoryCode) {
		this.plateCategoryCode = plateCategoryCode;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getOrganizers() {
		return organizers;
	}

	public void setOrganizers(String organizers) {
		this.organizers = organizers;
	}

	public Date getGmtPublish() {
		return gmtPublish;
	}

	public void setGmtPublish(Date gmtPublish) {
		this.gmtPublish = gmtPublish;
	}

	public Date getGmtSort() {
		return gmtSort;
	}

	public void setGmtSort(Date gmtSort) {
		this.gmtSort = gmtSort;
	}

	public Integer getPauseStatus() {
		return pauseStatus;
	}

	public void setPauseStatus(Integer pauseStatus) {
		this.pauseStatus = pauseStatus;
	}

	public String getAdminAccount() {
		return adminAccount;
	}

	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
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
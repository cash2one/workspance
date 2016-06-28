/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.domain;

import java.util.Date;

/**
 * @author totly created on 2011-12-10
 */
public class CrmContactStatistics implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer star1Able;
	private Integer star1Disable;
	private Integer star2Able;
	private Integer star2Disable;	
	private Integer star3Able;
	private Integer star3Disable;
	private Integer star4Able;
	private Integer star4Disable;
	private Integer star5Able;
	private Integer star5Disable;
	private Integer dragOrderCount;
	private Integer destroyOrderCount;
	private String saleAccount;
	private String saleDept;
	private String saleName;
	private Date gmtTarget;
	private Date gmtCreated;
	private Date gmtModified;
	private Integer seoCount;
	private Integer addRenewCount;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStar1Able() {
		return star1Able;
	}
	public void setStar1Able(Integer star1Able) {
		this.star1Able = star1Able;
	}
	public Integer getStar1Disable() {
		return star1Disable;
	}
	public void setStar1Disable(Integer star1Disable) {
		this.star1Disable = star1Disable;
	}
	public Integer getStar2Able() {
		return star2Able;
	}
	public void setStar2Able(Integer star2Able) {
		this.star2Able = star2Able;
	}
	public Integer getStar2Disable() {
		return star2Disable;
	}
	public void setStar2Disable(Integer star2Disable) {
		this.star2Disable = star2Disable;
	}
	public Integer getStar3Able() {
		return star3Able;
	}
	public void setStar3Able(Integer star3Able) {
		this.star3Able = star3Able;
	}
	public Integer getStar3Disable() {
		return star3Disable;
	}
	public void setStar3Disable(Integer star3Disable) {
		this.star3Disable = star3Disable;
	}
	public Integer getStar4Able() {
		return star4Able;
	}
	public void setStar4Able(Integer star4Able) {
		this.star4Able = star4Able;
	}
	public Integer getStar4Disable() {
		return star4Disable;
	}
	public void setStar4Disable(Integer star4Disable) {
		this.star4Disable = star4Disable;
	}
	public Integer getStar5Able() {
		return star5Able;
	}
	public void setStar5Able(Integer star5Able) {
		this.star5Able = star5Able;
	}
	public Integer getStar5Disable() {
		return star5Disable;
	}
	public void setStar5Disable(Integer star5Disable) {
		this.star5Disable = star5Disable;
	}
	public Integer getDragOrderCount() {
		return dragOrderCount;
	}
	public void setDragOrderCount(Integer dragOrderCount) {
		this.dragOrderCount = dragOrderCount;
	}
	public Integer getDestroyOrderCount() {
		return destroyOrderCount;
	}
	public void setDestroyOrderCount(Integer destroyOrderCount) {
		this.destroyOrderCount = destroyOrderCount;
	}
	public String getSaleAccount() {
		return saleAccount;
	}
	public void setSaleAccount(String saleAccount) {
		this.saleAccount = saleAccount;
	}
	public String getSaleDept() {
		return saleDept;
	}
	public void setSaleDept(String saleDept) {
		this.saleDept = saleDept;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
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
	public Integer getSeoCount() {
		return seoCount;
	}
	public void setSeoCount(Integer seoCount) {
		this.seoCount = seoCount;
	}
	public Integer getAddRenewCount() {
		return addRenewCount;
	}
	public void setAddRenewCount(Integer addRenewCount) {
		this.addRenewCount = addRenewCount;
	}
	
}
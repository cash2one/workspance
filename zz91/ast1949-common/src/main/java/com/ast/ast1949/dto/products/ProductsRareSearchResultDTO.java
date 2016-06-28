/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-26 by liulei.
 */
package com.ast.ast1949.dto.products;

import java.util.Date;

/**
 * @author liulei
 *
 */
public class ProductsRareSearchResultDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer productId;
	private String adminAccount;			//提交者
	private String oldOrNew;
	private String locationRequire;
	private String aroundProducts;
	private String remark;
	private String servicerAdvice;
	private String businessAdvice;
	private Date gmtCreated;
	private Date gmtModified;
	private String title;					//供求标题
	private String name;					//公司名
	private Integer matchSize;				//匹配个数
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getAdminAccount() {
		return adminAccount;
	}
	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}
	public String getOldOrNew() {
		return oldOrNew;
	}
	public void setOldOrNew(String oldOrNew) {
		this.oldOrNew = oldOrNew;
	}
	public String getLocationRequire() {
		return locationRequire;
	}
	public void setLocationRequire(String locationRequire) {
		this.locationRequire = locationRequire;
	}
	public String getAroundProducts() {
		return aroundProducts;
	}
	public void setAroundProducts(String aroundProducts) {
		this.aroundProducts = aroundProducts;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getServicerAdvice() {
		return servicerAdvice;
	}
	public void setServicerAdvice(String servicerAdvice) {
		this.servicerAdvice = servicerAdvice;
	}
	public String getBusinessAdvice() {
		return businessAdvice;
	}
	public void setBusinessAdvice(String businessAdvice) {
		this.businessAdvice = businessAdvice;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMatchSize() {
		return matchSize;
	}
	public void setMatchSize(Integer matchSize) {
		this.matchSize = matchSize;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

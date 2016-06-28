package com.ast.ast1949.dto.products;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class KeywordSearchDto extends DomainSupport {
	/**
	 * 后台 关键字搜索
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer productId;
	private Integer companyId;
	private Integer numLogin; //登录次数
	private String  companyName; //公司名字
	private String title; //产品标题
	private String productsTypeCode;//发布类型 供应:10331000 ,求购:10331001
	private String mobile; 
	private String area; //地区
	private String vip; //是否高会
	private Date gmtCreated;//发布时间

	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
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
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getNumLogin() {
		return numLogin;
	}
	public void setNumLogin(Integer numLogin) {
		this.numLogin = numLogin;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProductsTypeCode() {
		return productsTypeCode;
	}
	public void setProductsTypeCode(String productsTypeCode) {
		this.productsTypeCode = productsTypeCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getVip() {
		return vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}
}

/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-9 上午10:28:45
 */
package com.ast.ast1949.dto.products;

import com.ast.ast1949.domain.SearchSupport;

/**
 * 前台供求列表的显示项,由lucene来生成. 由于索引只接受String类型,所以所有字段都为String类型
 * 
 * @author Ryan(rxm1025@gmail.com)
 * 
 */
public class ProductsListItemForFrontDTO extends SearchSupport {
	private static final long serialVersionUID = 1L;
//	private CategoryDO categoryDO;
//	private ProductsListItemForFrontDTO nextDTO; // 排好序的下一个DTO

	private String productsTypeCode;// 信息分类 供／求
	private String buyOrSale;// 供应或求购 productsCategoryCode
	private String categoryProductsMainCode;// 供求信息分类
	private String categoryProductsAssistCode;// 供求信息辅助分类
	private String productId;// 供求ID
	private String productsTitle;// 供求标题
	private String highlightProductsTitle;// 高亮供求标题
	private String productsDetails;// 供求简介
	private String highlightProductsDetails;// 高这供求内容
	private String price;
	private String priceUnit;
	private String refreshTime;// 刷新时间,排序用
	private String displayRefreshTime;// 刷新时间，显示用
	private String companyId;// 公司ID
	private String companyName;// 公司名
	private String subDomain;// 二级域名
	private String honestExp;// 诚信指数
	private String membershipCode;// 会员等级code,排序用
	private String memberLevel;// 会员等级
	private String area;// 省市
	private String areaCode;// 省市
	private String province;
	private String provinceCode;
	private String picAddress;// 图片地址
	private String expireTime; // 过期时间
	private String checkStatus;// 审核状态 0:未审核,1:审核通过,2:审核不通过
	private String isPause; // 是否暂停发布 发布状态
	private String isDel; // 是否删除
	private String havePic;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

//	/**
//	 * @return the categoryDO
//	 */
//	public CategoryDO getCategoryDO() {
//		return categoryDO;
//	}
//
//	/**
//	 * @param categoryDO
//	 *            the categoryDO to set
//	 */
//	public void setCategoryDO(CategoryDO categoryDO) {
//		this.categoryDO = categoryDO;
//	}

	/**
	 * @return the buyOrSale
	 */
	public String getBuyOrSale() {
		return buyOrSale;
	}

	/**
	 * @param buyOrSale
	 *            the buyOrSale to set
	 */
	public void setBuyOrSale(String buyOrSale) {
		this.buyOrSale = buyOrSale;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the productsTitle
	 */
	public String getProductsTitle() {
		return productsTitle;
	}

	/**
	 * @param productsTitle
	 *            the productsTitle to set
	 */
	public void setProductsTitle(String productsTitle) {
		this.productsTitle = productsTitle;
	}

	/**
	 * @return the productsDetails
	 */
	public String getProductsDetails() {
		return productsDetails;
	}

	/**
	 * @param productsDetails
	 *            the productsDetails to set
	 */
	public void setProductsDetails(String productsDetails) {
		this.productsDetails = productsDetails;
	}

	/**
	 * @return the refreshTime
	 */
	public String getRefreshTime() {
		return refreshTime;
	}

	/**
	 * @param refreshTime
	 *            the refreshTime to set
	 */
	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}

	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId
	 *            the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the subDomain
	 */
	public String getSubDomain() {
		return subDomain;
	}

	/**
	 * @param subDomain
	 *            the subDomain to set
	 */
	public void setSubDomain(String subDomain) {
		this.subDomain = subDomain;
	}

	/**
	 * @return the memberLevel
	 */
	public String getMemberLevel() {
		return memberLevel;
	}

	/**
	 * @param memberLevel
	 *            the memberLevel to set
	 */
	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	public void setHonestExp(String honestExp) {
		this.honestExp = honestExp;
	}

	public String getHonestExp() {
		return honestExp;
	}

	/**
	 * @param categoryProductsMainCode
	 *            the categoryProductsMainCode to set
	 */
	public void setCategoryProductsMainCode(String categoryProductsMainCode) {
		this.categoryProductsMainCode = categoryProductsMainCode;
	}

	/**
	 * @return the categoryProductsMainCode
	 */
	public String getCategoryProductsMainCode() {
		return categoryProductsMainCode;
	}

	/**
	 * @param categoryProductsAssistCode
	 *            the categoryProductsAssistCode to set
	 */
	public void setCategoryProductsAssistCode(String categoryProductsAssistCode) {
		this.categoryProductsAssistCode = categoryProductsAssistCode;
	}

	/**
	 * @return the categoryProductsAssistCode
	 */
	public String getCategoryProductsAssistCode() {
		return categoryProductsAssistCode;
	}

//	/**
//	 * @param nextDTO
//	 *            the nextDTO to set
//	 */
//	public void setNextDTO(ProductsListItemForFrontDTO nextDTO) {
//		this.nextDTO = nextDTO;
//	}
//
//	/**
//	 * @return the nextDTO
//	 */
//	public ProductsListItemForFrontDTO getNextDTO() {
//		return nextDTO;
//	}

	/**
	 * @param membershipCode
	 *            the membershipCode to set
	 */
	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}

	/**
	 * @return the membershipCode
	 */
	public String getMembershipCode() {
		return membershipCode;
	}

	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}

	public String getPicAddress() {
		return picAddress;
	}

	public void setDisplayRefreshTime(String displayRefreshTime) {
		this.displayRefreshTime = displayRefreshTime;
	}

	public String getDisplayRefreshTime() {
		return displayRefreshTime;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getHighlightProductsTitle() {
		return highlightProductsTitle;
	}

	public void setHighlightProductsTitle(String highlightProductsTitle) {
		this.highlightProductsTitle = highlightProductsTitle;
	}

	public String getHighlightProductsDetails() {
		return highlightProductsDetails;
	}

	public void setHighlightProductsDetails(String highlightProductsDetails) {
		this.highlightProductsDetails = highlightProductsDetails;
	}

	public String getProductsTypeCode() {
		return productsTypeCode;
	}

	public void setProductsTypeCode(String productsTypeCode) {
		this.productsTypeCode = productsTypeCode;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getIsPause() {
		return isPause;
	}

	public void setIsPause(String isPause) {
		this.isPause = isPause;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getHavePic() {
		return havePic;
	}

	public void setHavePic(String havePic) {
		this.havePic = havePic;
	}

}

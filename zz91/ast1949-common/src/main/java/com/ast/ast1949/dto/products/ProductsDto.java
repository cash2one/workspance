/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-14
 */
package com.ast.ast1949.dto.products;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.domain.spot.SpotInfo;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-4-14
 */
public class ProductsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProductsDO products;
	private Company company;
	private CompanyAccount companyContacts;

	// products label
	private String productsTypeLabel;
	private String sourceTypeLabel;
	private String provideStatusLabel;
	private String manufactureLabel;
	private String categoryProductsMainLabel;
	private String categoryProductsAssistLabel;
	private String goodsTypeLabel;
	private String coverPicUrl;
	private String tags;
	private long postlimittime;
	private Map<String, String> tagsMap;

	// company label
	private String membershipLabel;
	private String areaLabel;
	
	/*
	 * // 关于地区信息 公司黄页最终页 private String country; // 国家 private String province;
	 * private String city;
	 */

	// search input
	private Integer[] checkStatusArray;

	private List<ProductsPicDO> productsPicList;

	// spot label
	private String isTe;
	private String isHot;
	private String isYou;
	private String isBail;
	private ProductsSpot productsSpot;
	private Integer spotSales; // 销量 (订单数与收藏数的综合)

	// spot_info
	private SpotInfo spotInfo;

	// 通用固定属性:品位
	private String grade;
	private String addShape;
	private String addTransaction;
	private String addLevel;

	// 导出询盘信息
	private Integer countInquiry;
	private String gmtInquiryStr;
	private String fromTitle;

	// 样品信息
	private Sample sample;
	private String sampleAreaLabel;
	private Integer totalSampleAmount;
	private Boolean isZSVip; //终身会员服务

	//询盘数
	private Integer inquiryCount ;
	
	public SpotInfo getSpotInfo() {
		return spotInfo;
	}

	public void setSpotInfo(SpotInfo spotInfo) {
		this.spotInfo = spotInfo;
	}

	public ProductsSpot getProductsSpot() {
		return productsSpot;
	}

	public void setProductsSpot(ProductsSpot productsSpot) {
		this.productsSpot = productsSpot;
	}

	/**
	 * @return the products
	 */
	public ProductsDO getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(ProductsDO products) {
		this.products = products;
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the companyContacts
	 */
	public CompanyAccount getCompanyContacts() {
		return companyContacts;
	}

	/**
	 * @param companyContacts
	 *            the companyContacts to set
	 */
	public void setCompanyContacts(CompanyAccount companyContacts) {
		this.companyContacts = companyContacts;
	}

	/**
	 * @return the productsTypeLabel
	 */
	public String getProductsTypeLabel() {
		return productsTypeLabel;
	}

	/**
	 * @param productsTypeLabel
	 *            the productsTypeLabel to set
	 */
	public void setProductsTypeLabel(String productsTypeLabel) {
		this.productsTypeLabel = productsTypeLabel;
	}

	/**
	 * @return the sourceTypeLabel
	 */
	public String getSourceTypeLabel() {
		return sourceTypeLabel;
	}

	/**
	 * @param sourceTypeLabel
	 *            the sourceTypeLabel to set
	 */
	public void setSourceTypeLabel(String sourceTypeLabel) {
		this.sourceTypeLabel = sourceTypeLabel;
	}

	/**
	 * @return the provideStatusLabel
	 */
	public String getProvideStatusLabel() {
		return provideStatusLabel;
	}

	/**
	 * @param provideStatusLabel
	 *            the provideStatusLabel to set
	 */
	public void setProvideStatusLabel(String provideStatusLabel) {
		this.provideStatusLabel = provideStatusLabel;
	}

	/**
	 * @return the manufactureLabel
	 */
	public String getManufactureLabel() {
		return manufactureLabel;
	}

	/**
	 * @param manufactureLabel
	 *            the manufactureLabel to set
	 */
	public void setManufactureLabel(String manufactureLabel) {
		this.manufactureLabel = manufactureLabel;
	}

	/**
	 * @return the categoryProductsMainLabel
	 */
	public String getCategoryProductsMainLabel() {
		return categoryProductsMainLabel;
	}

	/**
	 * @param categoryProductsMainLabel
	 *            the categoryProductsMainLabel to set
	 */
	public void setCategoryProductsMainLabel(String categoryProductsMainLabel) {
		this.categoryProductsMainLabel = categoryProductsMainLabel;
	}

	/**
	 * @return the categoryProductsAssistLabel
	 */
	public String getCategoryProductsAssistLabel() {
		return categoryProductsAssistLabel;
	}

	/**
	 * @param categoryProductsAssistLabel
	 *            the categoryProductsAssistLabel to set
	 */
	public void setCategoryProductsAssistLabel(
			String categoryProductsAssistLabel) {
		this.categoryProductsAssistLabel = categoryProductsAssistLabel;
	}

	/**
	 * @return the goodsTypeLabel
	 */
	public String getGoodsTypeLabel() {
		return goodsTypeLabel;
	}

	/**
	 * @param goodsTypeLabel
	 *            the goodsTypeLabel to set
	 */
	public void setGoodsTypeLabel(String goodsTypeLabel) {
		this.goodsTypeLabel = goodsTypeLabel;
	}

	/**
	 * @return the productsPicList
	 */
	public List<ProductsPicDO> getProductsPicList() {
		return productsPicList;
	}

	/**
	 * @param productsPicList
	 *            the productsPicList to set
	 */
	public void setProductsPicList(List<ProductsPicDO> productsPicList) {
		this.productsPicList = productsPicList;
	}

	/**
	 * @return the membershipLabel
	 */
	public String getMembershipLabel() {
		return membershipLabel;
	}

	/**
	 * @param membershipLabel
	 *            the membershipLabel to set
	 */
	public void setMembershipLabel(String membershipLabel) {
		this.membershipLabel = membershipLabel;
	}

	/**
	 * @return the areaLabel
	 */
	public String getAreaLabel() {
		return areaLabel;
	}

	/**
	 * @param areaLabel
	 *            the areaLabel to set
	 */
	public void setAreaLabel(String areaLabel) {
		this.areaLabel = areaLabel;
	}

	/**
	 * @return the coverPicUrl
	 */
	public String getCoverPicUrl() {
		return coverPicUrl;
	}

	/**
	 * @param coverPicUrl
	 *            the coverPicUrl to set
	 */
	public void setCoverPicUrl(String coverPicUrl) {
		this.coverPicUrl = coverPicUrl;
	}

	/**
	 * @return the checkStatusArray
	 */
	public Integer[] getCheckStatusArray() {
		return checkStatusArray;
	}

	/**
	 * @param checkStatusArray
	 *            the checkStatusArray to set
	 */
	public void setCheckStatusArray(Integer[] checkStatusArray) {
		this.checkStatusArray = checkStatusArray;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getIsTe() {
		return isTe;
	}

	public void setIsTe(String isTe) {
		this.isTe = isTe;
	}

	public String getIsHot() {
		return isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}

	public String getIsYou() {
		return isYou;
	}

	public void setIsYou(String isYou) {
		this.isYou = isYou;
	}

	public String getIsBail() {
		return isBail;
	}

	public void setIsBail(String isBail) {
		this.isBail = isBail;
	}

	public Map<String, String> getTagsMap() {
		return tagsMap;
	}

	public void setTagsMap(Map<String, String> tagsMap) {
		this.tagsMap = tagsMap;
	}

	public Integer getSpotSales() {
		return spotSales;
	}

	public void setSpotSales(Integer spotSales) {
		this.spotSales = spotSales;
	}

	public long getPostlimittime() {
		return postlimittime;
	}

	public void setPostlimittime(long postlimittime) {
		this.postlimittime = postlimittime;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAddShape() {
		return addShape;
	}

	public void setAddShape(String addShape) {
		this.addShape = addShape;
	}

	public String getAddTransaction() {
		return addTransaction;
	}

	public void setAddTransaction(String addTransaction) {
		this.addTransaction = addTransaction;
	}

	public String getAddLevel() {
		return addLevel;
	}

	public void setAddLevel(String addLevel) {
		this.addLevel = addLevel;
	}

	public Integer getCountInquiry() {
		return countInquiry;
	}

	public void setCountInquiry(Integer countInquiry) {
		this.countInquiry = countInquiry;
	}

	public String getGmtInquiryStr() {
		return gmtInquiryStr;
	}

	public void setGmtInquiryStr(String gmtInquiryStr) {
		this.gmtInquiryStr = gmtInquiryStr;
	}

	public String getFromTitle() {
		return fromTitle;
	}

	public void setFromTitle(String fromTitle) {
		this.fromTitle = fromTitle;
	}

	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}

	public String getSampleAreaLabel() {
		return sampleAreaLabel;
	}

	public void setSampleAreaLabel(String sampleAreaLabel) {
		this.sampleAreaLabel = sampleAreaLabel;
	}

	public Integer getTotalSampleAmount() {
		return totalSampleAmount;
	}

	public void setTotalSampleAmount(Integer totalSampleAmount) {
		this.totalSampleAmount = totalSampleAmount;
	}

	public Integer getInquiryCount() {
		return inquiryCount;
	}

	public void setInquiryCount(Integer inquiryCount) {
		this.inquiryCount = inquiryCount;
	}

	public Boolean getIsZSVip() {
		return isZSVip;
	}

	public void setIsZSVip(Boolean isZSVip) {
		this.isZSVip = isZSVip;
	}
}
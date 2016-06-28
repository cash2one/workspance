/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-12.
 */
package com.ast.ast1949.domain.price;

import java.util.Date;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class PriceDataDO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer 	id;					//编号
	private Integer 	priceId;			//报价编号
	private Integer 	companyPriceId;		//企业报价编号
	private String 		productName;		//产品名称
	private String 		quote;				//报价
	private String 		area;				//地区
	private String 		companyName;		//企业名称
	private Integer 	companyId;			//公司编号
	private Integer 	showIndex;			//排序
	private Date 		gmtCreated;			//创建时间
	private Date  		gmtModified;		//修改时间
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the priceId
	 */
	public Integer getPriceId() {
		return priceId;
	}
	/**
	 * @param priceId the priceId to set
	 */
	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}
	/**
	 * @return the companyPriceId
	 */
	public Integer getCompanyPriceId() {
		return companyPriceId;
	}
	/**
	 * @param companyPriceId the companyPriceId to set
	 */
	public void setCompanyPriceId(Integer companyPriceId) {
		this.companyPriceId = companyPriceId;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the quote
	 */
	public String getQuote() {
		return quote;
	}
	/**
	 * @param quote the quote to set
	 */
	public void setQuote(String quote) {
		this.quote = quote;
	}
	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the showIndex
	 */
	public Integer getShowIndex() {
		return showIndex;
	}
	/**
	 * @param showIndex the showIndex to set
	 */
	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}
	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}
	/**
	 * @param gmtCreated the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}

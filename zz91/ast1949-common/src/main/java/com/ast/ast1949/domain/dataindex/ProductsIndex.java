/**
 * 
 */
package com.ast.ast1949.domain.dataindex;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mays
 *
 */
public class ProductsIndex implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String dataIndexCode;
	private Integer productsId;
	private String account;
	private Integer companyId;
	private String productsType;
	private String title;
	private Float minPrice;
	private Float maxPrice;
	private String priceUnit;
	private Integer quantity;
	private String quantityUnit;
	private String tags;
	private String tagsAdmin;
	private Date refreshTime;
	private Date realTime;
	private String pic;
	private BigDecimal orderby;
	private Date gmtCreated;
	private Date gmtModified;
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
	 * @return the dataIndexCode
	 */
	public String getDataIndexCode() {
		return dataIndexCode;
	}
	/**
	 * @param dataIndexCode the dataIndexCode to set
	 */
	public void setDataIndexCode(String dataIndexCode) {
		this.dataIndexCode = dataIndexCode;
	}
	/**
	 * @return the productsId
	 */
	public Integer getProductsId() {
		return productsId;
	}
	/**
	 * @param productsId the productsId to set
	 */
	public void setProductsId(Integer productsId) {
		this.productsId = productsId;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
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
	 * @return the productsType
	 */
	public String getProductsType() {
		return productsType;
	}
	/**
	 * @param productsType the productsType to set
	 */
	public void setProductsType(String productsType) {
		this.productsType = productsType;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the minPrice
	 */
	public Float getMinPrice() {
		return minPrice;
	}
	/**
	 * @param minPrice the minPrice to set
	 */
	public void setMinPrice(Float minPrice) {
		this.minPrice = minPrice;
	}
	/**
	 * @return the maxPrice
	 */
	public Float getMaxPrice() {
		return maxPrice;
	}
	/**
	 * @param maxPrice the maxPrice to set
	 */
	public void setMaxPrice(Float maxPrice) {
		this.maxPrice = maxPrice;
	}
	/**
	 * @return the priceUnit
	 */
	public String getPriceUnit() {
		return priceUnit;
	}
	/**
	 * @param priceUnit the priceUnit to set
	 */
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the quantityUnit
	 */
	public String getQuantityUnit() {
		return quantityUnit;
	}
	/**
	 * @param quantityUnit the quantityUnit to set
	 */
	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}
	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	/**
	 * @return the tagsAdmin
	 */
	public String getTagsAdmin() {
		return tagsAdmin;
	}
	/**
	 * @param tagsAdmin the tagsAdmin to set
	 */
	public void setTagsAdmin(String tagsAdmin) {
		this.tagsAdmin = tagsAdmin;
	}
	/**
	 * @return the refreshTime
	 */
	public Date getRefreshTime() {
		return refreshTime;
	}
	/**
	 * @param refreshTime the refreshTime to set
	 */
	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}
	/**
	 * @return the realTime
	 */
	public Date getRealTime() {
		return realTime;
	}
	/**
	 * @param realTime the realTime to set
	 */
	public void setRealTime(Date realTime) {
		this.realTime = realTime;
	}
	/**
	 * @return the pic
	 */
	public String getPic() {
		return pic;
	}
	/**
	 * @param pic the pic to set
	 */
	public void setPic(String pic) {
		this.pic = pic;
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
	/**
	 * @return the orderby
	 */
	public BigDecimal getOrderby() {
		return orderby;
	}
	/**
	 * @param orderby the orderby to set
	 */
	public void setOrderby(BigDecimal orderby) {
		this.orderby = orderby;
	}
	
	
}

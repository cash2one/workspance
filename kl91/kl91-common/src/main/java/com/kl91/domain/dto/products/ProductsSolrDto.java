package com.kl91.domain.dto.products;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

import com.kl91.domain.DomainSupport;

public class ProductsSolrDto extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7149519993635847124L;
	
	@Field
	private Integer id;
	@Field
	private Integer cid;
	@Field
	private String productsCategoryCode;// 产品类别code
	@Field
	private String typeCode;// 供求类型'
	@Field
	private String title;// 标题'
	@Field
	private String details;// 详细
	@Field
	private String detailsQuery;// 摘要
	@Field
	private Integer checkedFlag;// 审核状态：\n0：未通过\n1：通过\n'
	@Field
	private Integer deletedFlag;// '删除状态\n0：未删除\n1：删除' ,
	@Field
	private Integer imptFlag;// 导入标志\n0:客户发布\n1:导入数据' ,
	@Field
	private Integer publishFlag;// '发布状态：\n0：暂不发布\n1：发布' ,
	@Field
	private String areaCode;// '地区' ,
	@Field
	private String priceUnit;// '价格单位' ,
	@Field
	private Integer quantity;// '数量' ,
	@Field
	private String quantityUnit;// '质量单位' ,
	@Field
	private String color;// '颜色' ,
	@Field
	private String location;// '发货地' ,
	@Field
	private String level;// '级别' ,
	@Field
	private String shape;// '形态' ,
	@Field
	private String useful;// '用途(主要应用)' ,
	@Field
	private String tags;// 标签' ,
	@Field
	private String tagsAdmin;// 后台标签' ,
	@Field
	private String picCover;// '封面图片地址' ,
	@Field
	private Integer minPrice;// '最小价格' ,
	@Field
	private Integer maxPrice;// '最大价格' ,
	@Field
	private Integer numInquiry;// 留言条数' ,
	@Field
	private Integer numView;// 查看次数' ,
	@Field
	private Integer dayExpired;// '有效期' ,
	@Field
	private Date showTime;// '供求显示日期' ,
	@Field
	private Date gmtPost;// '真实发布时间' ,
	@Field
	private Date gmtRefresh;// '刷新时间' ,
	@Field
	private Date gmtExpired;// 过期时间' ,
	@Field
	private Date gmtCheck;// '审核时间' ,
	@Field
	private Date gmtCreated;// '创建时间' ,
	@Field
	private Date gmtModified;// '修改时间' ,
	
	private String highLightTitle;
	
	private String highLightDetails;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getProductsCategoryCode() {
		return productsCategoryCode;
	}

	public void setProductsCategoryCode(String productsCategoryCode) {
		this.productsCategoryCode = productsCategoryCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDetailsQuery() {
		return detailsQuery;
	}

	public void setDetailsQuery(String detailsQuery) {
		this.detailsQuery = detailsQuery;
	}

	public Integer getCheckedFlag() {
		return checkedFlag;
	}

	public void setCheckedFlag(Integer checkedFlag) {
		this.checkedFlag = checkedFlag;
	}

	public Integer getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(Integer deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Integer getImptFlag() {
		return imptFlag;
	}

	public void setImptFlag(Integer imptFlag) {
		this.imptFlag = imptFlag;
	}

	public Integer getPublishFlag() {
		return publishFlag;
	}

	public void setPublishFlag(Integer publishFlag) {
		this.publishFlag = publishFlag;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getUseful() {
		return useful;
	}

	public void setUseful(String useful) {
		this.useful = useful;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTagsAdmin() {
		return tagsAdmin;
	}

	public void setTagsAdmin(String tagsAdmin) {
		this.tagsAdmin = tagsAdmin;
	}

	public String getPicCover() {
		return picCover;
	}

	public void setPicCover(String picCover) {
		this.picCover = picCover;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Integer getNumInquiry() {
		return numInquiry;
	}

	public void setNumInquiry(Integer numInquiry) {
		this.numInquiry = numInquiry;
	}

	public Integer getNumView() {
		return numView;
	}

	public void setNumView(Integer numView) {
		this.numView = numView;
	}

	public Integer getDayExpired() {
		return dayExpired;
	}

	public void setDayExpired(Integer dayExpired) {
		this.dayExpired = dayExpired;
	}

	public Date getShowTime() {
		return showTime;
	}

	public void setShowTime(Date showTime) {
		this.showTime = showTime;
	}

	public Date getGmtPost() {
		return gmtPost;
	}

	public void setGmtPost(Date gmtPost) {
		this.gmtPost = gmtPost;
	}

	public Date getGmtRefresh() {
		return gmtRefresh;
	}

	public void setGmtRefresh(Date gmtRefresh) {
		this.gmtRefresh = gmtRefresh;
	}

	public Date getGmtExpired() {
		return gmtExpired;
	}

	public void setGmtExpired(Date gmtExpired) {
		this.gmtExpired = gmtExpired;
	}

	public Date getGmtCheck() {
		return gmtCheck;
	}

	public void setGmtCheck(Date gmtCheck) {
		this.gmtCheck = gmtCheck;
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

	public String getHighLightTitle() {
		return highLightTitle;
	}

	public void setHighLightTitle(String highLightTitle) {
		this.highLightTitle = highLightTitle;
	}

	public String getHighLightDetails() {
		return highLightDetails;
	}

	public void setHighLightDetails(String highLightDetails) {
		this.highLightDetails = highLightDetails;
	}
	
}

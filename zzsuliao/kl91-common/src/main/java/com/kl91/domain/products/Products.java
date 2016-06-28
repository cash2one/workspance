package com.kl91.domain.products;

import java.util.Date;

import com.kl91.domain.DomainSupport;

public class Products extends DomainSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1765521162786096673L;
	
	private Integer id;
	private Integer cid;
	private String productsCategoryCode;//产品类别code
	private String typeCode;//供求类型'
	private String title;//标题' 
	private String details;//详细
	private String detailsQuery;//摘要
	private Integer checkedFlag;//审核状态：\n0：未通过\n1：通过\n'	
	private Integer deletedFlag;//'删除状态\n0：未删除\n1：删除' ,
	private Integer imptFlag;//导入标志\n0:客户发布\n1:导入数据' ,
	private Integer publishFlag;//'发布状态：\n0：暂不发布\n1：发布' ,
	private String areaCode;//'地区' ,
	private String priceUnit;//'价格单位' ,
	private Integer quantity;// '数量' ,
	private String quantityUnit;//'质量单位' ,
	private String color;//'颜色' ,
	private String location;//'发货地' ,
	private String level;//'级别' ,
	private String shape;//'形态' ,
	private String useful;//'用途(主要应用)' ,
	private String tags;//标签' ,
	private String tagsAdmin;//后台标签' ,
	private String picCover;//'封面图片地址' ,
	private Integer minPrice;//'最小价格' ,
	private Integer maxPrice;//'最大价格' ,
	private Integer numInquiry;//留言条数' ,
	private Integer numView;//查看次数' ,
	private Integer dayExpired;//'有效期' ,
	private Date show_time;//'供求显示日期' ,
	private Date gmt_post;//'真实发布时间' ,
	private Date gmt_refresh;//'刷新时间' ,
	private Date gmt_expired;//过期时间' ,
	private Date gmt_check;//'审核时间' ,
	private Date gmt_created;//'创建时间' ,
	private Date gmt_modified;//'修改时间' ,
	
	public Products() {
		super();
	}

	public Products(Integer id, Integer cid, String productsCategoryCode,
			String typeCode, String title, String details, String detailsQuery,
			Integer checkedFlag, Integer deletedFlag, Integer imptFlag,
			Integer publishFlag, String areaCode, String priceUnit,
			Integer quantity, String quantityUnit, String color,
			String location, String level, String shape, String useful,
			String tags, String tagsAdmin, String picCover, Integer minPrice,
			Integer maxPrice, Integer numInquiry, Integer numView,
			Integer dayExpired, Date showTime, Date gmtPost, Date gmtRefresh,
			Date gmtExpired, Date gmtCheck, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.cid = cid;
		this.productsCategoryCode = productsCategoryCode;
		this.typeCode = typeCode;
		this.title = title;
		this.details = details;
		this.detailsQuery = detailsQuery;
		this.checkedFlag = checkedFlag;
		this.deletedFlag = deletedFlag;
		this.imptFlag = imptFlag;
		this.publishFlag = publishFlag;
		this.areaCode = areaCode;
		this.priceUnit = priceUnit;
		this.quantity = quantity;
		this.quantityUnit = quantityUnit;
		this.color = color;
		this.location = location;
		this.level = level;
		this.shape = shape;
		this.useful = useful;
		this.tags = tags;
		this.tagsAdmin = tagsAdmin;
		this.picCover = picCover;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.numInquiry = numInquiry;
		this.numView = numView;
		this.dayExpired = dayExpired;
		this.show_time = showTime;
		this.gmt_post = gmtPost;
		this.gmt_refresh = gmtRefresh;
		this.gmt_expired = gmtExpired;
		this.gmt_check = gmtCheck;
		this.gmt_created = gmtCreated;
		this.gmt_modified = gmtModified;
	}

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

	public Date getShow_time() {
		return show_time;
	}

	public void setShow_time(Date showTime) {
		this.show_time = showTime;
	}

	public Date getGmt_post() {
		return gmt_post;
	}

	public void setGmt_post(Date gmtPost) {
		this.gmt_post = gmtPost;
	}

	public Date getGmt_refresh() {
		return gmt_refresh;
	}

	public void setGmt_refresh(Date gmtRefresh) {
		this.gmt_refresh = gmtRefresh;
	}

	public Date getGmt_expired() {
		return gmt_expired;
	}

	public void setGmt_expired(Date gmtExpired) {
		this.gmt_expired = gmtExpired;
	}

	public Date getGmt_check() {
		return gmt_check;
	}

	public void setGmt_check(Date gmtCheck) {
		this.gmt_check = gmtCheck;
	}

	public Date getGmt_created() {
		return gmt_created;
	}

	public void setGmt_created(Date gmtCreated) {
		this.gmt_created = gmtCreated;
	}

	public Date getGmt_modified() {
		return gmt_modified;
	}

	public void setGmt_modified(Date gmtModified) {
		this.gmt_modified = gmtModified;
	}
	

}

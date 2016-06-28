package com.zz91.ep.sync.domain;

import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：求购信息实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class TradeBuy implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer uid;
	private Integer cid;
	private String title;
	private String details;
	private String categoryCode;
	private String photoCover;
	private String provinceCode;
	private String areaCode;
	private Short buyType;
	private Integer quantity;
	private Integer quantityYear;
	private String quantityUntis;
	private String supplyAreaCode;
	private String useTo;
	private Date gmtConfirm;
	private Date gmtReceive;
	private Date gmtPublish;
	private Date gmtRefresh;
	private Short validDays;
	private String tagsSys;
	private String detailsQuery;
	private Integer messageCount;
	private Integer viewCount;
	private Integer favoriteCount;
	private Integer plusCount;
	private String htmlPath;
	private Short delStatus;
	private Short pauseStatus;
	private Short checkStatus;
	private String checkAdmin;
	private String checkRefuse;
	private Date gmtCheck;
	private Date gmtCreated;
	private Date gmtModified;
	private Date gmtExpired;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getCategoryCode() {
		return this.categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getPhotoCover() {
		return this.photoCover;
	}

	public void setPhotoCover(String photoCover) {
		this.photoCover = photoCover;
	}

	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Short getBuyType() {
		return this.buyType;
	}

	public void setBuyType(Short buyType) {
		this.buyType = buyType;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantityYear() {
		return this.quantityYear;
	}

	public void setQuantityYear(Integer quantityYear) {
		this.quantityYear = quantityYear;
	}

	public String getQuantityUntis() {
		return this.quantityUntis;
	}

	public void setQuantityUntis(String quantityUntis) {
		this.quantityUntis = quantityUntis;
	}

	public String getSupplyAreaCode() {
		return this.supplyAreaCode;
	}

	public void setSupplyAreaCode(String supplyAreaCode) {
		this.supplyAreaCode = supplyAreaCode;
	}

	public String getUseTo() {
		return this.useTo;
	}

	public void setUseTo(String useTo) {
		this.useTo = useTo;
	}

	public Date getGmtConfirm() {
		return this.gmtConfirm;
	}

	public void setGmtConfirm(Date gmtConfirm) {
		this.gmtConfirm = gmtConfirm;
	}

	public Date getGmtReceive() {
		return this.gmtReceive;
	}

	public void setGmtReceive(Date gmtReceive) {
		this.gmtReceive = gmtReceive;
	}

	public Date getGmtPublish() {
		return this.gmtPublish;
	}

	public void setGmtPublish(Date gmtPublish) {
		this.gmtPublish = gmtPublish;
	}

	public Date getGmtRefresh() {
		return this.gmtRefresh;
	}

	public void setGmtRefresh(Date gmtRefresh) {
		this.gmtRefresh = gmtRefresh;
	}

	public Short getValidDays() {
		return this.validDays;
	}

	public void setValidDays(Short validDays) {
		this.validDays = validDays;
	}

	public String getTagsSys() {
		return this.tagsSys;
	}

	public void setTagsSys(String tagsSys) {
		this.tagsSys = tagsSys;
	}

	public String getDetailsQuery() {
		return this.detailsQuery;
	}

	public void setDetailsQuery(String detailsQuery) {
		this.detailsQuery = detailsQuery;
	}

	public Integer getMessageCount() {
		return this.messageCount;
	}

	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
	}

	public Integer getViewCount() {
		return this.viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getFavoriteCount() {
		return this.favoriteCount;
	}

	public void setFavoriteCount(Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public Integer getPlusCount() {
		return this.plusCount;
	}

	public void setPlusCount(Integer plusCount) {
		this.plusCount = plusCount;
	}

	public String getHtmlPath() {
		return this.htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public Short getDelStatus() {
		return this.delStatus;
	}

	public void setDelStatus(Short delStatus) {
		this.delStatus = delStatus;
	}

	public Short getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckAdmin() {
		return this.checkAdmin;
	}

	public void setCheckAdmin(String checkAdmin) {
		this.checkAdmin = checkAdmin;
	}

	public String getCheckRefuse() {
		return this.checkRefuse;
	}

	public void setCheckRefuse(String checkRefuse) {
		this.checkRefuse = checkRefuse;
	}

	public Date getGmtCheck() {
		return this.gmtCheck;
	}

	public void setGmtCheck(Date gmtCheck) {
		this.gmtCheck = gmtCheck;
	}

	public Date getGmtCreated() {
		return this.gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return this.gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public void setPauseStatus(Short pauseStatus) {
		this.pauseStatus = pauseStatus;
	}

	public Short getPauseStatus() {
		return pauseStatus;
	}

	public void setGmtExpired(Date gmtExpired) {
		this.gmtExpired = gmtExpired;
	}

	public Date getGmtExpired() {
		return gmtExpired;
	}

}
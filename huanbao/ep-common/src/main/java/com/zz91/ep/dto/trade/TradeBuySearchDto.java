/*
 * 文件名称：TradeBuySearchDto.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto.trade;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：求购信息搜索结果。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class TradeBuySearchDto implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Integer id;// 供应信息ID
	
	private Integer cid;// 发布者公司ID
	
	private String title;// 标题
	
	private String areaCode;// 地区
	
	private String provinceCode;// 省份
	private String areaName;// 地区
	private String provinceName;// 省份
	
	private Integer quantity;// 采购数量
	
	private String quantityUntis;// 采购单位
	
	private Integer hasEnable;// 剩余天数
	
	private Integer validDays;// 有效天数
	
	private Date gmtRefresh;// 刷新时间
	
	private Date gmtExpired;// 过期时间
	
	private Integer messageCount;// 询盘数
	
	private String detailsQuery;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getHasEnable() {
		return hasEnable;
	}

	public void setHasEnable(Integer hasEnable) {
		this.hasEnable = hasEnable;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getQuantityUntis() {
		return quantityUntis;
	}

	public void setQuantityUntis(String quantityUntis) {
		this.quantityUntis = quantityUntis;
	}

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
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

	public Integer getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
	}

	public String getDetailsQuery() {
		return detailsQuery;
	}

	public void setDetailsQuery(String detailsQuery) {
		this.detailsQuery = detailsQuery;
	}

}
/*
 * 文件名称：TradeSupplySearchDto.java
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
 * 模块描述：供应信息搜索结果。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class TradeSupplySearchDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private Integer id;//供应信息ID
	private Integer cid;//发布者公司ID
	private String name;//发布者公司名称
	private String memberName;//发布者公司会员类型
	private String memberCode;//发布者公司会员类型
	private String photoCover;//缩略图
	private String title;// 标题
	private String areaName;//地区
	private String  provinceName;//省份
	private Double priceNum;//价格
	private String priceUnits;//价格单位
	private String detailsQuery;//详细（纯文本）
	private String provinceCode;// 省份
	private String areaCode;// 地区
	private Date gmtRegister;// 注册时间
	private String qq;
	private String mainProductSupply;//公司主营产品(供应)
	private String mainProductBuy;//公司主营产品
	private Date gmtRefresh; //刷新时间
	
	
	
	public String getMainProductSupply() {
		return mainProductSupply;
	}
	public void setMainProductSupply(String mainProductSupply) {
		this.mainProductSupply = mainProductSupply;
	}
	public Date getGmtRefresh() {
		return gmtRefresh;
	}
	public void setGmtRefresh(Date gmtRefresh) {
		this.gmtRefresh = gmtRefresh;
	}
	public String getMainProductBuy() {
		return mainProductBuy;
	}
	public void setMainProductBuy(String mainProductBuy) {
		this.mainProductBuy = mainProductBuy;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getPhotoCover() {
		return photoCover;
	}
	public void setPhotoCover(String photoCover) {
		this.photoCover = photoCover;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Double getPriceNum() {
		return priceNum;
	}
	public void setPriceNum(Double priceNum) {
		this.priceNum = priceNum;
	}
	public String getPriceUnits() {
		return priceUnits;
	}
	public void setPriceUnits(String priceUnits) {
		this.priceUnits = priceUnits;
	}
	public String getDetailsQuery() {
		return detailsQuery;
	}
	public void setDetailsQuery(String detailsQuery) {
		this.detailsQuery = detailsQuery;
	}
	public Date getGmtRegister() {
		return gmtRegister;
	}
	public void setGmtRegister(Date gmtRegister) {
		this.gmtRegister = gmtRegister;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	
}

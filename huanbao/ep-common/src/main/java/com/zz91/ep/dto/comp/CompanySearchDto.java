/*
 * 文件名称：CompanySearchDto.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto.comp;

import java.io.Serializable;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：公司信息搜索结果。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class CompanySearchDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	public Integer id;
	
	public String name;
	
	public String detailsQuery;
	
	public String mainProductBuy;
	
	public String mainProductSupply;
	
	public String areaCode;
	
	public String provinceCode;
	public String areaName;
	public String provinceName;
	
	public String industryName;
	
	public String address;
	
	public String memberCode;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetailsQuery() {
		return detailsQuery;
	}
	public void setDetailsQuery(String detailsQuery) {
		this.detailsQuery = detailsQuery;
	}
	public String getMainProductBuy() {
		return mainProductBuy;
	}
	public void setMainProductBuy(String mainProductBuy) {
		this.mainProductBuy = mainProductBuy;
	}
	public String getMainProductSupply() {
		return mainProductSupply;
	}
	public void setMainProductSupply(String mainProductSupply) {
		this.mainProductSupply = mainProductSupply;
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
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
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
}
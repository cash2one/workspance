/*
 * 文件名称：SearchCompanyDto.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto.search;

import java.io.Serializable;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：公司搜索条件。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class SearchCompanyDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String keywords;//关键字
	
	private String encodeKeywords;//关键字转码
	
	private String provinceCode;//省份code
	
	private String areaCode;//城市code

	private String industryCode;//行頁code
	
	private String businessCode;//客户类型
	
	private Integer pageNum;	//页数
	
	private Integer industryChainId; //产业id
	


	public Integer getIndustryChainId() {
		return industryChainId;
	}

	public void setIndustryChainId(Integer industryChainId) {
		this.industryChainId = industryChainId;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
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

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public void setEncodeKeywords(String encodeKeywords) {
		this.encodeKeywords = encodeKeywords;
	}

	public String getEncodeKeywords() {
		return encodeKeywords;
	}
}

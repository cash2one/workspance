/*
 * 文件名称：SearchSupplyDto.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto.search;

import java.io.Serializable;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：供应信息搜索条件。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class SearchSupplyDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String category;//类别
	
	private String categoryName; //类别名
	
	private String keywords;//关键字
	
	private String encodeKeywords;//关键字转码
	
	private String showStyle;//显示方式（noimg列表，img图文，shopwindow橱窗）
	
	private Integer group;//0：不合并；1：合并相同公司信息
	
	private String region;//地区code
	
	private Integer time;//发布时间(1天，3天，30天，60天)
	
	private Integer priceFrom;//价格-低

	private Integer priceTo;//价格-高
	
	private String propertyValue;//专业属性条件
	
	private Integer pageNum;
	
	private Boolean havePic;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getShowStyle() {
		return showStyle;
	}

	public void setShowStyle(String showStyle) {
		this.showStyle = showStyle;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(Integer priceFrom) {
		this.priceFrom = priceFrom;
	}

	public Integer getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(Integer priceTo) {
		this.priceTo = priceTo;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public Integer getGroup() {
		return group;
	}

	public void setEncodeKeywords(String encodeKeywords) {
		this.encodeKeywords = encodeKeywords;
	}

	public String getEncodeKeywords() {
		return encodeKeywords;
	}
	
	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Boolean getHavePic() {
		return havePic;
	}

	public void setHavePic(Boolean havePic) {
		this.havePic = havePic;
	}
	
}

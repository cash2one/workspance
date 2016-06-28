/*
 * 文件名称：SearchBuyDto.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto.search;

import java.io.Serializable;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：求购信息搜索条件。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class SearchBuyDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String keywords;// 关键字

	private String encodeKeywords;// 关键字转码

	private String showStyle;// 显示方式（noimg列表，img图文，shopwindow橱窗）

	private String region;// 地区code

	private Integer time;// 发布时间(1天，3天，30天，60天)
	
	private Integer validDays;// 有效期
	
	private String category;//类别
	
	private Integer pageNum;
	
	

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getEncodeKeywords() {
		return encodeKeywords;
	}

	public void setEncodeKeywords(String encodeKeywords) {
		this.encodeKeywords = encodeKeywords;
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

}

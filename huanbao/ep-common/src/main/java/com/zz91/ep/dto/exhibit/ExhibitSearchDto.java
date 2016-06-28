/*
 * 文件名称：ExhibitSearchDto.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto.exhibit;

import java.io.Serializable;
import java.util.Date;


/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：展会搜索结果。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class ExhibitSearchDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;//资讯信息ID
	
	private String name;// 标题
	
	private String plateCategoryCode;// 板块
	private String plateCategoryName;// 板块名称
	
	private String industryCode;// 行业类别
	private String industryName;// 行业类别名称
	
	private String showName;// 展馆名称
	
	private String organizers;//组织
	
	private String provinceCode;//省份CODE
	private String provinceName;//省份名称
	
	private String areaCode;//地区CODE
	private String areaName;//地区名称
	
	private Date gmtStart;// 开始时间
	
	private Date gmtEnd;// 结束时间
	
	private Date gmtPublish;// 发布时间
	
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
	public String getPlateCategoryCode() {
		return plateCategoryCode;
	}
	public void setPlateCategoryCode(String plateCategoryCode) {
		this.plateCategoryCode = plateCategoryCode;
	}
	public String getPlateCategoryName() {
		return plateCategoryName;
	}
	public void setPlateCategoryName(String plateCategoryName) {
		this.plateCategoryName = plateCategoryName;
	}
	public String getIndustryCode() {
		return industryCode;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getOrganizers() {
		return organizers;
	}
	public void setOrganizers(String organizers) {
		this.organizers = organizers;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Date getGmtStart() {
		return gmtStart;
	}
	public void setGmtStart(Date gmtStart) {
		this.gmtStart = gmtStart;
	}
	public Date getGmtEnd() {
		return gmtEnd;
	}
	public void setGmtEnd(Date gmtEnd) {
		this.gmtEnd = gmtEnd;
	}
	public Date getGmtPublish() {
		return gmtPublish;
	}
	public void setGmtPublish(Date gmtPublish) {
		this.gmtPublish = gmtPublish;
	}
	
}
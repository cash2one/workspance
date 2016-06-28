/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25 by Rolyer.
 */
package com.ast.ast1949.domain.information;

import java.util.Date;

/**
 * 走势图数值
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class ChartDataDO implements java.io.Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer 	id;					//编号
	private Integer		chartInfoId;		//信息编号
	private Integer 	chartCategoryId;	//类别编号
	private String 		name;				//分段名称
	private Float 		value;			 	//属性值
	private Date 		gmtCreated;			//创建时间
	private Date		gmtModified;		//修改时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getChartInfoId() {
		return chartInfoId;
	}
	public void setChartInfoId(Integer chartInfoId) {
		this.chartInfoId = chartInfoId;
	}
	public Integer getChartCategoryId() {
		return chartCategoryId;
	}
	public void setChartCategoryId(Integer chartCategoryId) {
		this.chartCategoryId = chartCategoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
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
}

/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-31 by Rolyer.
 */
package com.ast.ast1949.domain.information;

import java.util.Date;

/**
 * 走势图信息
 * @author Rolyer(rolyer.live@gmail.com)
 */
public class ChartsInfoDO implements java.io.Serializable{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer 	id;					//编号
	private String 		title;				//标题
	private Integer 	chartCategoryId;	//类别
	private Date 		gmtDate;			//报价日期
    private Date 		gmtCreated;			//创建时间
    private Date 		gmtModified;		//修改时间
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getChartCategoryId() {
		return chartCategoryId;
	}
	public void setChartCategoryId(Integer chartCategoryId) {
		this.chartCategoryId = chartCategoryId;
	}
	public Date getGmtDate() {
		return gmtDate;
	}
	public void setGmtDate(Date gmtDate) {
		this.gmtDate = gmtDate;
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

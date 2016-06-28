/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25 by Rolyer.
 */
package com.ast.ast1949.domain.information;

import java.util.Date;

/**
 * 走势图类别
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class ChartCategoryDO implements java.io.Serializable {

	/**
	 *  序列化
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer 	id;				//编号
	private Integer 	parentId;		//父编号
	private String 		name;			//名称
	private String 		setting;		//分段设置
	private Integer 	showIndex;		//排序
	private String 		showInHome;		//在首页显示，默认为0。
	private Integer 	relevanceId;	//关联编号
	private Date 		gmtCreated;		//创建时间
	private Date 		gmtModified;	//修改时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSetting() {
		return setting;
	}
	public void setSetting(String setting) {
		this.setting = setting;
	}
	public Integer getShowIndex() {
		return showIndex;
	}
	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}
	/**
	 * @return the showInHome
	 */
	public String getShowInHome() {
		return showInHome;
	}
	/**
	 * @param showInHome the showInHome to set
	 */
	public void setShowInHome(String showInHome) {
		this.showInHome = showInHome;
	}
	public Integer getRelevanceId() {
		return relevanceId;
	}
	public void setRelevanceId(Integer relevanceId) {
		this.relevanceId = relevanceId;
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

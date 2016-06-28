/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-30
 */
package com.ast.ast1949.domain.news;

import java.util.Date;

/**
 * 新闻模块
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class NewsModuleDO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields
	
	private Integer id;				//编号
	private Integer parentId;		//父编号
	private String 	name;			//模块名称
	private String 	checked;		//审核
	private String 	url;			//跳转地址
	private Date 	gmtCreated;		//创建时间
	private Date 	gmtModified;	//修改时间

	// Constructors

	/** default constructor */
	public NewsModuleDO() {
	}

	/** minimal constructor */
	public NewsModuleDO(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public NewsModuleDO(Integer id, Integer parentId, String name,
			String checked, String url, Date gmtCreated, Date gmtModified) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.checked = checked;
		this.url = url;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChecked() {
		return this.checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getGmtCreated() {
		return this.gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return this.gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}

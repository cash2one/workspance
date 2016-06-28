/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-19 by Rolyer.
 */
package com.ast.ast1949.domain.price;

import java.util.Date;

/**
 * 新闻类别实体类：news_category表
 * @author Rolyer (rolyer.live@gmail.com)
 */

public class PriceCategoryDO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields
	private Integer id; 				//编号
	private Integer parentId;			//父节点
	private String name;				//类别名称
	private String goUrl;				//链接地址
	private Integer showIndex;			//排序
	private Date gmtCreated;			//创建时间
	private Date gmtModified;			//修改时间
	private Boolean isDelete;				//是否删除
	private String pinyin;              //拼音

	
	
	// Constructors


	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	/** default constructor */
	public PriceCategoryDO() {
	}

	/** minimal constructor */
	public PriceCategoryDO(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public PriceCategoryDO(Integer id, Integer parentId, String name,
			String goUrl, Integer showIndex, Date gmtCreated, Date gmtModified) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.goUrl = goUrl;
		this.showIndex = showIndex;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	
	// Property accessors
	
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

	public String getGoUrl() {
		return goUrl;
	}

	public void setGoUrl(String goUrl) {
		this.goUrl = goUrl;
	}

	public Integer getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
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

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}


}
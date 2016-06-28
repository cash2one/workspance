/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-30
 */
package com.ast.ast1949.domain.news;

import java.util.Date;

/**
 * 新闻模块关联
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class NewsRelateModuleDO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields
	
	private Integer id;			//编号
	private Integer newsId;		//新闻编号
	private Integer moduleId;	//模块编号
	private String tradeId;		//类别编号
	private Date gmtCreated;	//创建时间
	private Date gmtModified;	//修改时间

	// Constructors

	/** default constructor */
	public NewsRelateModuleDO() {
	}

	/** minimal constructor */
	public NewsRelateModuleDO(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public NewsRelateModuleDO(Integer id, Integer newsId, Integer moduleId,
			String tradeId, Date gmtCreated, Date gmtModified) {
		this.id = id;
		this.newsId = newsId;
		this.moduleId = moduleId;
		this.tradeId = tradeId;
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

	public Integer getNewsId() {
		return this.newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public Integer getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
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

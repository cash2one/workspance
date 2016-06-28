package com.kl91.domain.products;

import java.util.Date;

import com.kl91.domain.DomainSupport;

public class ProductsCategory extends DomainSupport{
	
	/**
	 * 供求类别
	 * 
	 */
	private static final long serialVersionUID = 8378620657613591636L;
	
	private Integer id;
	private String name;//类别名称
	private String code;//类别编号
	private Integer orderby;//排列顺序
	private String tags;
	private Date gmtCreated;
	private Date gmtModified;
	
	public ProductsCategory() {
	}

	public ProductsCategory(Integer id, String name, String code,
			Integer orderby, String tags, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.orderby = orderby;
		this.tags = tags;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getOrderBy() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
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

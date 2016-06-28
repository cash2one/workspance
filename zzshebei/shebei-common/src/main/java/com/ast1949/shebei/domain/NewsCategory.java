package com.ast1949.shebei.domain;

import java.io.Serializable;
import java.util.Date;

public class NewsCategory implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String categoryCode;//类别
	private String name;//类别名称
	private String keywords;//关键字
	private Date gmtCreated;//创建时间
	private Date gmtModified;//最后修改时间
	public NewsCategory(){
		
	}
	public NewsCategory(Integer id, String categoryCode, String name,
			String keywords, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.categoryCode = categoryCode;
		this.name = name;
		this.keywords = keywords;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
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

package com.zz91.ep.domain.comp;

import java.io.Serializable;
import java.util.Date;

public class CompTags implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer parentId;
	private String code;
	private String name;
	private String keyword;
	private Short sort;
	private Short showIndex;
	private Date gmtCreated;
	private Date gmtModified;
	public CompTags() {
		super();
	}
	public CompTags(Integer id, Integer parentId, String code, String name,
			String keyword, Short sort, Short showIndex, Date gmtCreated,
			Date gmtModified) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.code = code;
		this.name = name;
		this.keyword = keyword;
		this.sort = sort;
		this.showIndex = showIndex;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Short getSort() {
		return sort;
	}
	public void setSort(Short sort) {
		this.sort = sort;
	}
	public Short getShowIndex() {
		return showIndex;
	}
	public void setShowIndex(Short showIndex) {
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

}

package com.ast.ast1949.domain.news;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class CategoryNewsTechDO extends DomainSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String label;
	private String code;
	private String isDel;
	private Date gmtCreated;
	private Date gmtModified;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
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

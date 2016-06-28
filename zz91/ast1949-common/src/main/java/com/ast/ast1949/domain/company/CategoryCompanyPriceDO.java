/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-25
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

/**
 * @author yuyonghui
 *
 */
public class CategoryCompanyPriceDO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String label;
    private String isDel;
    private Date gmtCreated;
    private Date  gmtModified;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
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

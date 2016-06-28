/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.domain;

import java.util.Date;

/**
 * @author totly created on 2011-12-10
 */
public class SysArea implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String code;
    private String name;
    private Short leaf;
    private String details;
    private Date gmtCreated;
    private Date gmtModified;

    public SysArea(){};
    
    public SysArea(Integer id, String code, String name, Short leaf,
			String details, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.leaf = leaf;
		this.details = details;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Short getLeaf() {
        return leaf;
    }
    public void setLeaf(Short leaf) {
        this.leaf = leaf;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
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
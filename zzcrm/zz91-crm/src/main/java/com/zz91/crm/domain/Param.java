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
public class Param implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String types;
    private String name;
    private String key;
    private String value;
    private Short sort;
    private Short isuse;
    private Date gmtCreated;
    private Date gmtModified;
    
    public Param() {
    	super();
	}

	public Param(Integer id, String types, String name, String key,
			String value, Short sort, Short isuse, Date gmtCreated,
			Date gmtModified) {
		super();
		this.id = id;
		this.types = types;
		this.name = name;
		this.key = key;
		this.value = value;
		this.sort = sort;
		this.isuse = isuse;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
    
	public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTypes() {
        return types;
    }
    public void setTypes(String types) {
        this.types = types;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Short getSort() {
        return sort;
    }
    public void setSort(Short sort) {
        this.sort = sort;
    }
    public Short getIsuse() {
        return isuse;
    }
    public void setIsuse(Short isuse) {
        this.isuse = isuse;
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
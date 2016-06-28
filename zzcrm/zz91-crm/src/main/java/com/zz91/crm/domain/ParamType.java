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
public class ParamType implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String key;
    private String name;
    private Date gmtCreated;
    private Date gmtModified;
    
    public ParamType() {
		super();
	}
    
	public ParamType(Integer id, String key, String name, Date gmtCreated,
			Date gmtModified) {
		super();
		this.id = id;
		this.key = key;
		this.name = name;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
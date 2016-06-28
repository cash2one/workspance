/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-17 上午09:55:09
 */
package com.ast.ast1949.domain.tags;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * 标签信息
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class TagsInfoDO extends DomainSupport{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String typeId;//'0：默认，1：数量标签，2：颜色标签',
	private Date gmtCreated;
	private Date gmtModified;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}
	/**
	 * @param gmtCreated the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}

/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-8-12
 */
package com.zz91.tags.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-8-12
 */
public class TagsCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	private String code;
	private String indexKey;
	private String name;
	private Date gmtCreated;
	private Date gmtModified;
	
	public TagsCategory(String code, String indexKey, String name,
			Date gmtCreated, Date gmtModified) {
		super();
		this.code = code;
		this.indexKey = indexKey;
		this.name = name;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	public TagsCategory() {
		super();
	}
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the indexKey
	 */
	public String getIndexKey() {
		return indexKey;
	}
	/**
	 * @param indexKey the indexKey to set
	 */
	public void setIndexKey(String indexKey) {
		this.indexKey = indexKey;
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

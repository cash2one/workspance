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
public class TagsCategoryCollection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String categoryIndexKey;
	private String categoryCode;
	private String tags;
	private String tagsEncode;
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
	 * @return the categoryIndexKey
	 */
	public String getCategoryIndexKey() {
		return categoryIndexKey;
	}
	/**
	 * @param categoryIndexKey the categoryIndexKey to set
	 */
	public void setCategoryIndexKey(String categoryIndexKey) {
		this.categoryIndexKey = categoryIndexKey;
	}
	/**
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}
	/**
	 * @param categoryCode the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	/**
	 * @return the tagsEncode
	 */
	public String getTagsEncode() {
		return tagsEncode;
	}
	/**
	 * @param tagsEncode the tagsEncode to set
	 */
	public void setTagsEncode(String tagsEncode) {
		this.tagsEncode = tagsEncode;
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

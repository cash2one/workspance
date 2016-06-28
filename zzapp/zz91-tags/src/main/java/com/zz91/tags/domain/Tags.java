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
public class Tags implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String tags;
	private String tagsEncode;
	private Integer clickCount;
	private Integer searchCount;
	private Integer citedCount;
	private Date gmtCreated;
	private Date gmtModified;
	private String keywords;
	
	/**
	 * @return the id
	 */
	public Tags(String tags, String tagsEncode, Integer clickCount,
			Integer searchCount, Integer citedCount, Date gmtCreated,
			Date gmtModified) {
		super();
		this.tags = tags;
		this.tagsEncode = tagsEncode;
		this.clickCount = clickCount;
		this.searchCount = searchCount;
		this.citedCount = citedCount;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	public Tags() {
		super();
	}

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
	 * @return the clickCount
	 */
	public Integer getClickCount() {
		return clickCount;
	}
	/**
	 * @param clickCount the clickCount to set
	 */
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	/**
	 * @return the searchCount
	 */
	public Integer getSearchCount() {
		return searchCount;
	}
	/**
	 * @param searchCount the searchCount to set
	 */
	public void setSearchCount(Integer searchCount) {
		this.searchCount = searchCount;
	}
	/**
	 * @return the citedCount
	 */
	public Integer getCitedCount() {
		return citedCount;
	}
	/**
	 * @param citedCount the citedCount to set
	 */
	public void setCitedCount(Integer citedCount) {
		this.citedCount = citedCount;
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

	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	
}

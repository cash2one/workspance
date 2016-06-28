/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-24
 */
package com.ast.ast1949.domain.information;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuyonghui
 * 
 */
public class ExhibitDO implements Serializable {

	/**
	 * 线下展会
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String areaCode;
	private String plateCategoryCode;
	private String exhibitCategoryCode;
	private String content;
	private Date startTime;
	private Date endTime;
	private Date gmtCreated;
	private Date gmtModified;
	private String photoCover;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPlateCategoryCode() {
		return plateCategoryCode;
	}

	public void setPlateCategoryCode(String plateCategoryCode) {
		this.plateCategoryCode = plateCategoryCode;
	}

	public String getExhibitCategoryCode() {
		return exhibitCategoryCode;
	}

	public void setExhibitCategoryCode(String exhibitCategoryCode) {
		this.exhibitCategoryCode = exhibitCategoryCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getPhotoCover() {
		return photoCover;
	}

	public void setPhotoCover(String photoCover) {
		this.photoCover = photoCover;
	}

}

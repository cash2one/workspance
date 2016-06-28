package com.ast.ast1949.domain.company;

import java.util.Date;

public class CategoryGardenDO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String shorterName;
	private String industryCode;
	private String areaCode;
	private String gardenTypeCode;
	private Date gmtCreated;
	private Date gmtModified;

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

	public String getShorterName() {
		return shorterName;
	}

	public void setShorterName(String shorterName) {
		this.shorterName = shorterName;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getGardenTypeCode() {
		return gardenTypeCode;
	}

	public void setGardenTypeCode(String gardenTypeCode) {
		this.gardenTypeCode = gardenTypeCode;
	}
	// public Date getGmtCreated() {
	// return gmtCreated;
	// }
	// public void setGmtCreated(Date gmtCreated) {
	// this.gmtCreated = gmtCreated;
	// }
	// public Date getGmtModified() {
	// return gmtModified;
	// }
	// public void setGmtModified(Date gmtModified) {
	// this.gmtModified = gmtModified;
	// }

}

package com.ast.ast1949.domain.yuanliao;

import java.util.Date;

/**
 * @date 2015-08-22
 * @author shiqp
 */
public class YuanliaoPic {
	private Integer id;
	private Integer yuanliaoId;
	private String picAddress;
	private Integer isDel;
	private Integer checkStatus;
	private Integer isDefault;
	private String checkPerson;
	private String unpassReason;
	private Date checkTime;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getYuanliaoId() {
		return yuanliaoId;
	}

	public void setYuanliaoId(Integer yuanliaoId) {
		this.yuanliaoId = yuanliaoId;
	}

	public String getPicAddress() {
		return picAddress;
	}

	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	public String getUnpassReason() {
		return unpassReason;
	}

	public void setUnpassReason(String unpassReason) {
		this.unpassReason = unpassReason;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
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

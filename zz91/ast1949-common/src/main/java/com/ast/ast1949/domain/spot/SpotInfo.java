package com.ast.ast1949.domain.spot;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class SpotInfo extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3375992851728352681L;

	private Integer id;
	private Integer spotId;
	private String shape;
	private String transaction;
	private String level;
	private String address;
	private String addressCode;
	private String zip;
	private String tel;
	private String mobile;
	private Date gmtCreated;
	private Date gmtModified;
	
	public Integer getId() {
		return id;
	}

	public Integer getSpotId() {
		return spotId;
	}

	public String getTransaction() {
		return transaction;
	}

	public String getAddress() {
		return address;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public String getZip() {
		return zip;
	}

	public String getTel() {
		return tel;
	}

	public String getMobile() {
		return mobile;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSpotId(Integer spotId) {
		this.spotId = spotId;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}

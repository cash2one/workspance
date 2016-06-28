package com.ast.ast1949.domain.spot;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * author:kongsj date:2013-5-20
 */
public class SpotTrust extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5387064604221667728L;

	private Integer id; //
	private String mobile; // VARCHAR(100) NOT NULL COMMENT '手机' ,
	private String title; // VARCHAR(200) NULL COMMENT '采购产品标题' ,
	private String useful; // VARCHAR(200) NULL COMMENT '产品用于' ,
	private String contact;
	private String quantity; // VARCHAR(200) NULL COMMENT '采购数量' ,
	private String area; // VARCHAR(200) NULL COMMENT '地区' ,
	private String isChecked; // CHAR(1) NOT NULL DEFAULT '0' COMMENT
								// '审核：\\n0：待审核\\n1：审核通过\\n2：退回' ,
	private String isDelete; // CHAR(1) NULL DEFAULT '0' COMMENT
								// '是否删除\\n0：未删除\\n1：删除' ,
	private Date gmtCreated; // DATETIME NULL ,
	private Date gmtModified; // DATETIME NULL ,

	public Integer getId() {
		return id;
	}

	public String getMobile() {
		return mobile;
	}

	public String getTitle() {
		return title;
	}

	public String getUseful() {
		return useful;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getArea() {
		return area;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public String getIsDelete() {
		return isDelete;
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

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUseful(String useful) {
		this.useful = useful;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}

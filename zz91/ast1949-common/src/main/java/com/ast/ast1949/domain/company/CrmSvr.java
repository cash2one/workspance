package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class CrmSvr extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private Integer id;
	private String code;
	private String name;
	private String remark;
	private Integer unitPrice;
	private String units;
	private Date gmtCreated;
	private Date gmtModified;
	
	public CrmSvr() {
		super();
	}
	public CrmSvr( String code, String name, String remark,
			Integer unitPrice, String units, Date gmtCreated, Date gmtModified) {
		super();
		this.code = code;
		this.name = name;
		this.remark = remark;
		this.unitPrice = unitPrice;
		this.units = units;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
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

package com.zz91.ep.domain.comp;


import java.util.Date;

public class CompBusinessCategory implements java.io.Serializable{
	
	/**
	 * 公司业务类型实体
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String code;
	private Date gmtCreated;
	private Date gmtModified;

	public CompBusinessCategory(Integer id, String name, String code,
			Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	public CompBusinessCategory() {
		super();
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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

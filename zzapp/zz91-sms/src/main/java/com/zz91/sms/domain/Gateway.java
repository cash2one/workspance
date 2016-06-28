package com.zz91.sms.domain;

import java.util.Date;

public class Gateway implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String code;//网关code
	private String titles;//网关名称
	private Integer enabled;//是否启用,0:未启用,1:启用
	private String serialNo;//网关序列号
	private String serialPas;//网关序列号查询密码，与序列号一起，用于查询网关账户信息
	private String apiJar;//重新包装后的网关jar包位置
	private String apiClasspath;//依赖的网关jar包名称
	private String docs;//网关文档（简），包括网关介绍等信息，便于开发人员了解网关信息
	private Date gmtCreated;
	private Date gmtModified;

	public Gateway() {
		super();
	}

	public Gateway(Integer id, String code, String titles, Integer enabled,
			String serialNo, String serialPas, String apiJar,String apiClasspath, String docs,
			Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.apiClasspath=apiClasspath;
		this.code = code;
		this.titles = titles;
		this.enabled = enabled;
		this.serialNo = serialNo;
		this.serialPas = serialPas;
		this.apiJar = apiJar;
		this.apiClasspath = apiClasspath;
		this.docs = docs;
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

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getSerialPas() {
		return serialPas;
	}

	public void setSerialPas(String serialPas) {
		this.serialPas = serialPas;
	}

	public String getApiJar() {
		return apiJar;
	}

	public void setApiJar(String apiJar) {
		this.apiJar = apiJar;
	}

	public String getDocs() {
		return docs;
	}

	public void setDocs(String docs) {
		this.docs = docs;
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

	public String getApiClasspath() {
		return apiClasspath;
	}

	public void setApiClasspath(String apiClasspath) {
		this.apiClasspath = apiClasspath;
	}

}

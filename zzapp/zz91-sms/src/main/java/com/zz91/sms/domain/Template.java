package com.zz91.sms.domain;

import java.io.Serializable;
import java.util.Date;

public class Template implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String code;//模板code
	private String titles;//模板名称
	private String content;//模板内容
	private String signed;//短信签名
	private Date gmtCreated;
	private Date gmtModified;

	public Template(Integer id, String code, String titles, String content,
			String signed, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.code = code;
		this.titles = titles;
		this.content = content;
		this.signed = signed;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	public Template() {
		super();
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSigned() {
		return signed;
	}

	public void setSigned(String signed) {
		this.signed = signed;
	}

	public Date getCmtGreated() {
		return gmtCreated;
	}

	public void setCmtGreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}

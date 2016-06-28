package com.ast.feiliao91.domain.company;

import java.io.Serializable;
import java.util.Date;

public class FeedBack implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer companyId;// 投诉人
	private Integer targetcompanyid;// 被投诉人
	private String detail; // 内容
	private Integer isdel;// 是否删除0未删除1删除
	private Date gmtcreated;
	private Date gmtmodified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getTargetcompanyid() {
		return targetcompanyid;
	}

	public void setTargetcompanyid(Integer targetcompanyid) {
		this.targetcompanyid = targetcompanyid;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public Date getGmtcreated() {
		return gmtcreated;
	}

	public void setGmtcreated(Date gmtcreated) {
		this.gmtcreated = gmtcreated;
	}

	public Date getGmtmodified() {
		return gmtmodified;
	}

	public void setGmtmodified(Date gmtmodified) {
		this.gmtmodified = gmtmodified;
	}

}

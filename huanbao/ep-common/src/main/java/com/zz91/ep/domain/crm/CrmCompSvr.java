/*
 * 文件名称：CrmCompSvr.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.crm;

import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：公司服务开通记录表：用于记录某公司开通了哪些服务，服务是否还有效等信息。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class CrmCompSvr implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer cid;
	private Integer crmSvrId;
	private String applyGroup;
	private Short signedStatus;
	private String memberCode;
	private Date gmtPreStart;
	private Date gmtPreEnd;
	private Date gmtSigned;
	private Date gmtStart;
	private Date gmtEnd;
	private Short applyStatus;
	private Short saleCategory;
	private String remark;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getCrmSvrId() {
		return this.crmSvrId;
	}

	public void setCrmSvrId(Integer crmSvrId) {
		this.crmSvrId = crmSvrId;
	}

	public String getApplyGroup() {
		return this.applyGroup;
	}

	public void setApplyGroup(String applyGroup) {
		this.applyGroup = applyGroup;
	}

	public Short getSignedStatus() {
		return this.signedStatus;
	}

	public void setSignedStatus(Short signedStatus) {
		this.signedStatus = signedStatus;
	}

	public String getMemberCode() {
		return this.memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public Date getGmtPreStart() {
		return this.gmtPreStart;
	}

	public void setGmtPreStart(Date gmtPreStart) {
		this.gmtPreStart = gmtPreStart;
	}

	public Date getGmtPreEnd() {
		return this.gmtPreEnd;
	}

	public void setGmtPreEnd(Date gmtPreEnd) {
		this.gmtPreEnd = gmtPreEnd;
	}

	public Date getGmtSigned() {
		return this.gmtSigned;
	}

	public void setGmtSigned(Date gmtSigned) {
		this.gmtSigned = gmtSigned;
	}

	public Date getGmtStart() {
		return this.gmtStart;
	}

	public void setGmtStart(Date gmtStart) {
		this.gmtStart = gmtStart;
	}

	public Date getGmtEnd() {
		return this.gmtEnd;
	}

	public void setGmtEnd(Date gmtEnd) {
		this.gmtEnd = gmtEnd;
	}

	public Short getApplyStatus() {
		return this.applyStatus;
	}

	public void setApplyStatus(Short applyStatus) {
		this.applyStatus = applyStatus;
	}

	public Short getSaleCategory() {
		return this.saleCategory;
	}

	public void setSaleCategory(Short saleCategory) {
		this.saleCategory = saleCategory;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getGmtCreated() {
		return this.gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return this.gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
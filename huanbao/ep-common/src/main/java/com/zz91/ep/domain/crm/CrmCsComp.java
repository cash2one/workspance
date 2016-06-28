/*
 * 文件名称：CrmCsComp.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.crm;

import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：客服和公司关联表。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class CrmCsComp implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String saleStaff;
	private String saleStaffName;
	private Integer cid;
	private Integer delStatus;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSaleStaff() {
		return saleStaff;
	}

	public void setSaleStaff(String saleStaff) {
		this.saleStaff = saleStaff;
	}

	public String getSaleStaffName() {
		return saleStaffName;
	}

	public void setSaleStaffName(String saleStaffName) {
		this.saleStaffName = saleStaffName;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
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
/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-9.
 */
package com.ast.ast1949.domain.bbs;

import java.util.Date;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
public class ReportsDO {
	/*
	 * 序列化 字段
	 */
	private Integer id;
	private Integer reportId; //举报信息的ID
	private String reportType;//举报信息类型
	private String reportReason;//举报理由
	private String reportAccount;//举报人帐号
	private String reportName;//举报人姓名
	private Date gmtReportTime;//举报时间
	private String ip;//举报人IP
	private String checkstate;//处理状态
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportReason() {
		return reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	public String getReportAccount() {
		return reportAccount;
	}

	public void setReportAccount(String reportAccount) {
		this.reportAccount = reportAccount;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Date getGmtReportTime() {
		return gmtReportTime;
	}

	public void setGmtReportTime(Date gmtReportTime) {
		this.gmtReportTime = gmtReportTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCheckstate() {
		return checkstate;
	}

	public void setCheckstate(String checkstate) {
		this.checkstate = checkstate;
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

	public ReportsDO(Integer id, Integer reportId, String reportType,
			String reportReason, String reportAccount, String reportName,
			Date gmtReportTime, String ip, String checkstate, Date gmtCreated,
			Date gmtModified) {
		super();
		this.id = id;
		this.reportId = reportId;
		this.reportType = reportType;
		this.reportReason = reportReason;
		this.reportAccount = reportAccount;
		this.reportName = reportName;
		this.gmtReportTime = gmtReportTime;
		this.ip = ip;
		this.checkstate = checkstate;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	public ReportsDO() {
		super();
		// TODO Auto-generated constructor stub
	}


	
}

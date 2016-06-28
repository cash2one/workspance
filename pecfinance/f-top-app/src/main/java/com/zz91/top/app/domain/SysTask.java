/**
 * 
 */
package com.zz91.top.app.domain;

import java.util.Date;

/**
 * @author mays
 *
 */
public class SysTask extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer sid;
	private String accessToken;
	private String taskId;
	private Date gmtReadStart;
	private Date gmtReadEnd;
	private String readFields;
	private Date gmtSubmit;
	private Integer status;
	private String tbError;
	private String tbErrorDiscription;
	private String method;
	private String nick;
	private String checkCode;
	private String downloadUrl;
	
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Date getGmtReadStart() {
		return gmtReadStart;
	}
	public void setGmtReadStart(Date gmtReadStart) {
		this.gmtReadStart = gmtReadStart;
	}
	public Date getGmtReadEnd() {
		return gmtReadEnd;
	}
	public void setGmtReadEnd(Date gmtReadEnd) {
		this.gmtReadEnd = gmtReadEnd;
	}
	public String getReadFields() {
		return readFields;
	}
	public void setReadFields(String readFields) {
		this.readFields = readFields;
	}
	public Date getGmtSubmit() {
		return gmtSubmit;
	}
	public void setGmtSubmit(Date gmtSubmit) {
		this.gmtSubmit = gmtSubmit;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTbError() {
		return tbError;
	}
	public void setTbError(String tbError) {
		this.tbError = tbError;
	}
	public String getTbErrorDiscription() {
		return tbErrorDiscription;
	}
	public void setTbErrorDiscription(String tbErrorDiscription) {
		this.tbErrorDiscription = tbErrorDiscription;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
	
}

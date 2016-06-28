/**
 * 
 */
package com.zz91.ep.domain.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mays (mays@asto.com.cn)
 *
 * Created at 2012-12-20
 */
public class Feedback implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String details;
	private String screenshot;
	private String project;
	private Integer uid;
	private Integer cid;
	private String email;
	private Date gmtCreated;
	private Date gmtModified;
	private String responseStatus;
	private String resolveRemark;
	private Date gmtResponse;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getScreenshot() {
		return screenshot;
	}
	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getResolveRemark() {
		return resolveRemark;
	}
	public void setResolveRemark(String resolveRemark) {
		this.resolveRemark = resolveRemark;
	}
	public Date getGmtResponse() {
		return gmtResponse;
	}
	public void setGmtResponse(Date gmtResponse) {
		this.gmtResponse = gmtResponse;
	}
	
	
}

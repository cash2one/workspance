/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-10
 */
package com.ast.ast1949.domain.site;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-3-10
 */
public class FeedbackDo extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer companyId;
	private String account;
	private String email;
	private Integer category;
	private String title;
	private String content;
	private String replyContent;
	private Date gmtCreated;
	private Date gmtReply;
	private Date gmtModified;
	private String checkStatus;
	private String checkPerson;
	/**
	 * 
	 */
	public FeedbackDo() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param companyId
	 * @param account
	 * @param email
	 * @param category
	 * @param title
	 * @param content
	 * @param replyContent
	 * @param gmtCreated
	 * @param gmtReply
	 * @param gmtModified
	 * @param checkStatus
	 * @param checkPerson
	 */
	public FeedbackDo(Integer companyId, String account, String email,
			Integer category, String title, String content,
			String replyContent, Date gmtCreated, Date gmtReply,
			Date gmtModified, String checkStatus, String checkPerson) {
		super();
		this.companyId = companyId;
		this.account = account;
		this.email = email;
		this.category = category;
		this.title = title;
		this.content = content;
		this.replyContent = replyContent;
		this.gmtCreated = gmtCreated;
		this.gmtReply = gmtReply;
		this.gmtModified = gmtModified;
		this.checkStatus = checkStatus;
		this.checkPerson = checkPerson;
	}
	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the replyContent
	 */
	public String getReplyContent() {
		return replyContent;
	}
	/**
	 * @param replyContent the replyContent to set
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}
	/**
	 * @param gmtCreated the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	/**
	 * @return the gmtReply
	 */
	public Date getGmtReply() {
		return gmtReply;
	}
	/**
	 * @param gmtReply the gmtReply to set
	 */
	public void setGmtReply(Date gmtReply) {
		this.gmtReply = gmtReply;
	}
	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	/**
	 * @return the checkStatus
	 */
	public String getCheckStatus() {
		return checkStatus;
	}
	/**
	 * @param checkStatus the checkStatus to set
	 */
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	/**
	 * @return the checkPerson
	 */
	public String getCheckPerson() {
		return checkPerson;
	}
	/**
	 * @param checkPerson the checkPerson to set
	 */
	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}
	
	
}

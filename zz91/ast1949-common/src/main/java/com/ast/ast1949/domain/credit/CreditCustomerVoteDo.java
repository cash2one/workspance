package com.ast.ast1949.domain.credit;

import java.io.Serializable;
import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class CreditCustomerVoteDo extends DomainSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer fromCompanyId;// 评价者公司ID
	private String fromAccount;// 评价者账号名
	private Integer toCompanyId;// 被评价公司ID
	private String status;// 0:好评;1:中评;2:差评
	private String content;// 评价内容
	private String replyContent;//对评价做出的解释
	private String checkStatus;// 0:待审核;1:已审核;2:审核未通过
	private String checkPerson;
	private Date gmtCreated;
	private Date gmtModified;

	public CreditCustomerVoteDo() {

	}

	/**
	 * @param fromCompanyId
	 * @param fromAccount
	 * @param toCompanyId
	 * @param toVote
	 * @param status
	 * @param content
	 * @param checkStatus
	 * @param checkPerson
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public CreditCustomerVoteDo(Integer fromCompanyId, String fromAccount,
			Integer toCompanyId, String status, String content,
			String replyContent, String checkStatus, String checkPerson,
			Date gmtCreated, Date gmtModified) {
		super();
		this.fromCompanyId = fromCompanyId;
		this.fromAccount = fromAccount;
		this.toCompanyId = toCompanyId;
		this.status = status;
		this.content = content;
		this.replyContent = replyContent;
		this.checkStatus = checkStatus;
		this.checkPerson = checkPerson;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the fromCompanyId
	 */
	public Integer getFromCompanyId() {
		return fromCompanyId;
	}

	/**
	 * @param fromCompanyId
	 *            the fromCompanyId to set
	 */
	public void setFromCompanyId(Integer fromCompanyId) {
		this.fromCompanyId = fromCompanyId;
	}

	/**
	 * @return the fromAccount
	 */
	public String getFromAccount() {
		return fromAccount;
	}

	/**
	 * @param fromAccount
	 *            the fromAccount to set
	 */
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	/**
	 * @return the toCompanyId
	 */
	public Integer getToCompanyId() {
		return toCompanyId;
	}

	/**
	 * @param toCompanyId
	 *            the toCompanyId to set
	 */
	public void setToCompanyId(Integer toCompanyId) {
		this.toCompanyId = toCompanyId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the checkStatus
	 */
	public String getCheckStatus() {
		return checkStatus;
	}

	/**
	 * @param checkStatus
	 *            the checkStatus to set
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
	 * @param checkPerson
	 *            the checkPerson to set
	 */
	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}

	/**
	 * @param gmtCreated
	 *            the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * @param gmtModified
	 *            the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the replyContent
	 */
	public String getReplyContent() {
		return replyContent;
	}

	/**
	 * @param replyContent
	 *            the replyContent to set
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

}

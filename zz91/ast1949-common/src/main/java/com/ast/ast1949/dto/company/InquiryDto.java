package com.ast.ast1949.dto.company;

import java.io.Serializable;
import java.util.Date;

import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.Inquiry;

/**
 * 留言／询盘信息
 * 
 * @author Mays (x03570227@gmail.com)
 * 
 */
public class InquiryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Inquiry inquiry;

	private String groupName;

	private String senderAccount;
	private String receiverAccount;
	private String senderContact;
	private String receiverContact;
	private String senderCompanyName;
	private String receiverCompanyName;
	private Date sendTime;

	private String companyName;

	private BbsUserProfilerDO profiler;

	public BbsUserProfilerDO getProfiler() {
		return profiler;
	}

	public void setProfiler(BbsUserProfilerDO profiler) {
		this.profiler = profiler;
	}

	/**
	 * @return the inquiry
	 */
	public Inquiry getInquiry() {
		return inquiry;
	}

	/**
	 * @param inquiry
	 *            the inquiry to set
	 */
	public void setInquiry(Inquiry inquiry) {
		this.inquiry = inquiry;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the senderAccount
	 */
	public String getSenderAccount() {
		return senderAccount;
	}

	/**
	 * @param senderAccount
	 *            the senderAccount to set
	 */
	public void setSenderAccount(String senderAccount) {
		this.senderAccount = senderAccount;
	}

	/**
	 * @return the receiverAccount
	 */
	public String getReceiverAccount() {
		return receiverAccount;
	}

	/**
	 * @param receiverAccount
	 *            the receiverAccount to set
	 */
	public void setReceiverAccount(String receiverAccount) {
		this.receiverAccount = receiverAccount;
	}

	/**
	 * @return the senderCompanyName
	 */
	public String getSenderCompanyName() {
		return senderCompanyName;
	}

	/**
	 * @param senderCompanyName
	 *            the senderCompanyName to set
	 */
	public void setSenderCompanyName(String senderCompanyName) {
		this.senderCompanyName = senderCompanyName;
	}

	/**
	 * @return the receiverCompanyName
	 */
	public String getReceiverCompanyName() {
		return receiverCompanyName;
	}

	/**
	 * @param receiverCompanyName
	 *            the receiverCompanyName to set
	 */
	public void setReceiverCompanyName(String receiverCompanyName) {
		this.receiverCompanyName = receiverCompanyName;
	}

	/**
	 * @return the sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime
	 *            the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * @return the senderContact
	 */
	public String getSenderContact() {
		return senderContact;
	}

	/**
	 * @param senderContact
	 *            the senderContact to set
	 */
	public void setSenderContact(String senderContact) {
		this.senderContact = senderContact;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getReceiverContact() {
		return receiverContact;
	}

	public void setReceiverContact(String receiverContact) {
		this.receiverContact = receiverContact;
	}

}

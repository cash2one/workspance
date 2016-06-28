package com.zz91.mail.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.Message.RecipientType;

import com.zz91.util.lang.StringUtils;

/**
 * 封装 Email信息
 * 
 * @author liuwb
 * 
 */
public class EMailMessage {
	private Message.RecipientType recipientType;
	//	private EMailAddress sender; // 发信人邮箱信息
	private String formEmail;
	private String password;
	private String smtpHost;

	private List<String> toEmails; // 收信人地址
	private List<String> attachedFileList;//邮件附件
	private EMailHtmlContent mailHtmlContent;//邮件内容
	private String mailStringContent;
	private String subject; // 邮件主题
	private Date sendTime; // 发送时间
	private Date gmtCreated; // 创建时间
	private Date gmtModified; // 修改时间

	public EMailMessage() {}

	public EMailMessage(RecipientType recipientType, String formEmail, String password,
			String smtpHost) {
		this.recipientType = recipientType;
		this.formEmail = formEmail;
		this.password = password;
		this.smtpHost = smtpHost;
	}

	public Message.RecipientType getRecipientType() {
		return recipientType;
	}

	public void setRecipientType(Message.RecipientType recipientType) {
		this.recipientType = recipientType;
	}

	public List<String> getToEmails() {
		return toEmails;
	}

	public void addToEmail(String toEmail) {
		if (toEmails == null) {
			toEmails = new ArrayList<String>();
		}
		toEmails.add(toEmail);
	}

	public void setToEmails(List<String> toEmails) {
		this.toEmails = toEmails;
	}

	public EMailHtmlContent getMailHtmlContent() {
		return mailHtmlContent;
	}

	public void setMailHtmlContent(EMailHtmlContent mailHtmlContent) {
		this.mailHtmlContent = mailHtmlContent;
	}

	public String getMailStringContent() {
		return mailStringContent;
	}

	public void setMailStringContent(String mailStringContent) {
		this.mailStringContent = mailStringContent;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
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

	public String getFormEmail() {
		return formEmail;
	}

	public void setFormEmail(String formEmail) {
		this.formEmail = formEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public List<String> getAttachedFileList() {
		return attachedFileList;
	}

	public void addAttachedFile(String fileName) {
		if (attachedFileList == null) {
			attachedFileList = new ArrayList<String>();
		}
		attachedFileList.add(fileName);
	}

	public void setAttachedFileList(List<String> attachedFileList) {
		this.attachedFileList = attachedFileList;
	}

	public String isAvalible() {
		String msg = null;
		if (StringUtils.isEmpty(formEmail)) {
			msg = "没有指定发送人邮件地址！";
		}
		if (toEmails == null || toEmails.size() <= 0) {
			msg = "没有指定收件人邮件地址！";
		}
		if (StringUtils.isEmpty(smtpHost)) {
			msg = "smtp服务器地址无效！";
		}
		if (!(recipientType.equals(Message.RecipientType.TO)
				|| recipientType.equals(Message.RecipientType.CC) || recipientType
				.equals(Message.RecipientType.BCC))) {
			msg = "邮件发送类型出错！";
		}
		return msg;
	}
}

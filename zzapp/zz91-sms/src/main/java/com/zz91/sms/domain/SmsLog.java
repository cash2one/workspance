package com.zz91.sms.domain;

import java.io.Serializable;
import java.util.Date;

public class SmsLog implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5471248479763047738L;
	private Integer id;
	private String templateCode;//模板code
	private String receiver;
	private Integer sendStatus;
	private Date gmtSend;//指定发送时间
	private String gatewayCode;//发送网关在code
	private Integer priority;
	private Date gmtCreated;
	private Date gmtModified;
	private String content;
	private String smsParameter;
	private String gmtSendStr;
	
	public String getGmtSendStr() {
		return gmtSendStr;
	}
	public void setGmtSendStr(String gmtSendStr) {
		this.gmtSendStr = gmtSendStr;
	}
	public SmsLog() {
	}
	public SmsLog(Integer id, String templateCode, String receiver,
			Integer sendStatus, Date gmtSend, String gatewayCode,
			Integer priority, Date gmtCreated, Date gmtModified, String content) {
		super();
		this.id = id;
		this.templateCode = templateCode;
		this.receiver = receiver;
		this.sendStatus = sendStatus;
		this.gmtSend = gmtSend;
		this.gatewayCode = gatewayCode;
		this.priority = priority;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
		this.content = content;
	}
	public SmsLog(Integer id, String templateCode, String receiver,
			Integer sendStatus, Date gmtSend, String gatewayCode,
			Integer priority, Date gmtCreated, Date gmtModified, String content,String smsParameter) {
		super();
		this.id = id;
		this.templateCode = templateCode;
		this.receiver = receiver;
		this.sendStatus = sendStatus;
		this.gmtSend = gmtSend;
		this.gatewayCode = gatewayCode;
		this.priority = priority;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
		this.content = content;
		this.smsParameter=smsParameter;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Integer getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}
	public Date getGmtSend() {
		return gmtSend;
	}
	public void setGmtSend(Date gmtSend) {
		this.gmtSend = gmtSend;
	}
	public String getGatewayCode() {
		return gatewayCode;
	}
	public void setGatewayCode(String gatewayCode) {
		this.gatewayCode = gatewayCode;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSmsParameter() {
		return smsParameter;
	}
	public void setSmsParameter(String smsParameter) {
		this.smsParameter = smsParameter;
	}

}

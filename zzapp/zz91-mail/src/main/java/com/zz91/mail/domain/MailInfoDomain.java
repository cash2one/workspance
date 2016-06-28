/**
 * Project name: zz91-mail
 * File name: MailDomain.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.mail.domain;

import java.util.Date;
import java.util.Map;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-10-26
 */
public class MailInfoDomain {

    private Integer id;
    private String sender;// 发送者
    private String receiver;// 接收者
    private String emailTitle;// email标题
    private String templateId;// 模板编号
    private Date gmtCreated;
    private Date gmtModified;
    private String content;// 正文内容
    private Date gmtPost;// 发送时间
    private Integer sendStatus; // 发送状态
    private String accountCode;
    private String sendName;
    private String sendPassword;
    private String sendHost;
    Map<String, Object> emailParameter; // 组装正文参数
    private Boolean isDebug;// 开关,true：打印邮件信息,false：直接发送邮件
    private Integer priority;
    private String nickname;

    @Deprecated
    private Boolean isImmediate;// 是否立即发送

    public MailInfoDomain(Integer id, String sender, String receiver, String emailTitle, String templateId, Date gmtCreated,
            Date gmtModified, Map<String, Object> emailParameter, String content, 
            Date gmtPost, Boolean isImmediate, Integer sendStatus,Integer priority) {
        super();
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.emailTitle = emailTitle;
        this.templateId = templateId;
        this.gmtCreated = gmtCreated;
        this.gmtModified = gmtModified;
        this.content = content;
        this.gmtPost = gmtPost;
        this.sendStatus = sendStatus;
        this.emailParameter=emailParameter;
        this.priority=priority;
    }
    public MailInfoDomain(Integer id, String sender, String receiver, String emailTitle, String templateId, Date gmtCreated,
            Date gmtModified,String content, 
            Date gmtPost, Integer sendStatus,Integer priority) {
        super();
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.emailTitle = emailTitle;
        this.templateId = templateId;
        this.gmtCreated = gmtCreated;
        this.gmtModified = gmtModified;
        this.content = content;
        this.gmtPost = gmtPost;
        this.sendStatus = sendStatus;
        this.priority=priority;
    }

    public MailInfoDomain() {
    	super();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getEmailTitle() {
        return emailTitle;
    }

    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getGmtPost() {
        return gmtPost;
    }

    public void setGmtPost(Date gmtPost) {
        this.gmtPost = gmtPost;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Map<String, Object> getEmailParameter() {
        return emailParameter;
    }

    public void setEmailParameter(Map<String, Object> emailParameter) {
        this.emailParameter = emailParameter;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendPassword() {
        return sendPassword;
    }

    public void setSendPassword(String sendPassword) {
        this.sendPassword = sendPassword;
    }

    public String getSendHost() {
        return sendHost;
    }

    public void setSendHost(String sendHost) {
        this.sendHost = sendHost;
    }

    public Boolean getIsDebug() {
        return isDebug;
    }

    public void setIsDebug(Boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * @return the isImmediate
     */
    public Boolean getIsImmediate() {
        return isImmediate;
    }

    /**
     * @param isImmediate
     *            the isImmediate to set
     */
    public void setIsImmediate(Boolean isImmediate) {
        this.isImmediate = isImmediate;
    }

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

    
}

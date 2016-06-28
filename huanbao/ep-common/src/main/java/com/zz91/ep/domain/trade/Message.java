/*
 * 文件名称：Message.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.trade;

import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：询盘消息实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class Message implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// 编号
	private Integer uid;// 发送者用户编号
	private Integer cid;// 发送者公司编号
	private Integer targetUid;// 接受者公司编号
	private Integer targetCid;// 接收者公司编号
	private Integer targetId;// 信息编号
	private Short targetType;// 消息类型
	private String title;// 标题
	private String details;// 内容
	private Short readStatus;// 查看标记
	private Short replyStatus;// 回复标记
	private String replyDetails;// 回复内容
	private Date gmtReply;// 回复时间
	private Date gmtCreated;// 创建时间
	private Date gmtModified;// 修改时间
	private Short delReceiveStatus;// 接收方删除标记
	private Short delSendStatus;// 发送方删除标记

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getTargetUid() {
		return this.targetUid;
	}

	public void setTargetUid(Integer targetUid) {
		this.targetUid = targetUid;
	}

	public Integer getTargetCid() {
		return this.targetCid;
	}

	public void setTargetCid(Integer targetCid) {
		this.targetCid = targetCid;
	}

	public Integer getTargetId() {
		return this.targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public Short getTargetType() {
		return this.targetType;
	}

	public void setTargetType(Short targetType) {
		this.targetType = targetType;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Short getReadStatus() {
		return this.readStatus;
	}

	public void setReadStatus(Short readStatus) {
		this.readStatus = readStatus;
	}

	public Short getReplyStatus() {
		return this.replyStatus;
	}

	public void setReplyStatus(Short replyStatus) {
		this.replyStatus = replyStatus;
	}

	public String getReplyDetails() {
		return this.replyDetails;
	}

	public void setReplyDetails(String replyDetails) {
		this.replyDetails = replyDetails;
	}

	public Date getGmtReply() {
		return this.gmtReply;
	}

	public void setGmtReply(Date gmtReply) {
		this.gmtReply = gmtReply;
	}

	public Date getGmtCreated() {
		return this.gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return this.gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Short getDelSendStatus() {
		return delSendStatus;
	}

	public void setDelSendStatus(Short delSendStatus) {
		this.delSendStatus = delSendStatus;
	}

	public void setDelReceiveStatus(Short delReceiveStatus) {
		this.delReceiveStatus = delReceiveStatus;
	}

	public Short getDelReceiveStatus() {
		return delReceiveStatus;
	}

}
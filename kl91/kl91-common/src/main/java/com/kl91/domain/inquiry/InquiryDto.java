package com.kl91.domain.inquiry;

import java.util.Date;

import com.kl91.domain.DomainSupport;

public class InquiryDto extends DomainSupport {

	/**
	 * 盘询
	 */
	private static final long serialVersionUID = 8087548949751044943L;

	private Integer id;
	private Integer fromCid;// '发送询盘公司ID
	private Integer toCid;// 接收询盘公司ID
	private Integer targetId;// 询盘对应ID\n公司：公司ID\n供求：供求ID'
	private Integer targetType;// '询盘对象的类型 \n0:供求信息\n1:公司'
	private String title;// 询盘标题
	private String content;// 询盘内容
	private String contentReply;// 回复内容
	private Integer isFromTrash;// 发送方删除状态：\n0:未删除\n1:删除\n2:彻底删除
	private Integer isToTrash;// 接收方删除状态：\n0:未删除\n1:删除\n2:彻底删除'
	private Integer isFromView;// '发送方询盘阅读情况\n0：未读\n1：已读'
	private Integer isToView;// 接收方询盘阅读情况\n0：未读\n1：已读'
	private Date gmtPost;// 发送时间
	private Date gmtReply;// 回复时间
	private Date gmtCreated;
	private Date gmtModified;
	private String fromCname;
	private String toCname;

	public InquiryDto() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFromCid() {
		return fromCid;
	}

	public void setFromCid(Integer fromCid) {
		this.fromCid = fromCid;
	}

	public Integer getToCid() {
		return toCid;
	}

	public void setToCid(Integer toCid) {
		this.toCid = toCid;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentReply() {
		return contentReply;
	}

	public void setContentReply(String contentReply) {
		this.contentReply = contentReply;
	}

	public Integer getIsFromTrash() {
		return isFromTrash;
	}

	public void setIsFromTrash(Integer isFromTrash) {
		this.isFromTrash = isFromTrash;
	}

	public Integer getIsToTrash() {
		return isToTrash;
	}

	public void setIsToTrash(Integer isToTrash) {
		this.isToTrash = isToTrash;
	}

	public Integer getIsFromView() {
		return isFromView;
	}

	public void setIsFromView(Integer isFromView) {
		this.isFromView = isFromView;
	}

	public Integer getIsToView() {
		return isToView;
	}

	public void setIsToView(Integer isToView) {
		this.isToView = isToView;
	}

	public Date getGmtPost() {
		return gmtPost;
	}

	public void setGmtPost(Date gmtPost) {
		this.gmtPost = gmtPost;
	}

	public Date getGmtReply() {
		return gmtReply;
	}

	public void setGmtReply(Date gmtReply) {
		this.gmtReply = gmtReply;
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

	public String getFromCname() {
		return fromCname;
	}

	public void setFromCname(String fromCname) {
		this.fromCname = fromCname;
	}

	public String getToCname() {
		return toCname;
	}

	public void setToCname(String toCname) {
		this.toCname = toCname;
	}

}

package com.ast.ast1949.domain.bbs;

import com.ast.ast1949.domain.DomainSupport;

public class BbsScore extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2229997388189147455L;

	private Integer id;
	private Integer bbsPostId;
	private Integer bbsReplyId;
	private Integer qaPostId;
	private Integer qaReplyId;
	private Integer companyId;
	private String companyAccount;
	private Integer score;
	private String remark;
	private Integer postType;
	private Integer replyType;
	private String gmtCreated;
	private String gmtModified;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBbsPostId() {
		return bbsPostId;
	}
	public void setBbsPostId(Integer bbsPostId) {
		this.bbsPostId = bbsPostId;
	}
	public Integer getBbsReplyId() {
		return bbsReplyId;
	}
	public void setBbsReplyId(Integer bbsReplyId) {
		this.bbsReplyId = bbsReplyId;
	}
	public Integer getQaPostId() {
		return qaPostId;
	}
	public void setQaPostId(Integer qaPostId) {
		this.qaPostId = qaPostId;
	}
	public Integer getQaReplyId() {
		return qaReplyId;
	}
	public void setQaReplyId(Integer qaReplyId) {
		this.qaReplyId = qaReplyId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyAccount() {
		return companyAccount;
	}
	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getPostType() {
		return postType;
	}
	public void setPostType(Integer postType) {
		this.postType = postType;
	}
	public Integer getReplyType() {
		return replyType;
	}
	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
	}
	public String getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(String gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public String getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}
}

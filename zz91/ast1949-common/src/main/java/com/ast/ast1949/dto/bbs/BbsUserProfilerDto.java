package com.ast.ast1949.dto.bbs;

import java.io.Serializable;

import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;

public class BbsUserProfilerDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1354709982720849586L;

	private BbsUserProfilerDO bbsUserProfiler; // 论坛信息

	private Integer usedScore; // 使用积分
	private Integer laveScore; // 剩余积分
	private Integer totalPost; // 总发贴数
	private Integer totalReply; // 总回复数
	private Integer totalQA; // 总提问数
	private Integer totalReplyQA; // 总回答数

	private String account; // 帐号
	private String contact; // 联系人
	private String name; // 公司名
	public BbsUserProfilerDO getBbsUserProfiler() {
		return bbsUserProfiler;
	}
	public void setBbsUserProfiler(BbsUserProfilerDO bbsUserProfiler) {
		this.bbsUserProfiler = bbsUserProfiler;
	}
	public Integer getUsedScore() {
		return usedScore;
	}
	public void setUsedScore(Integer usedScore) {
		this.usedScore = usedScore;
	}
	public Integer getLaveScore() {
		return laveScore;
	}
	public void setLaveScore(Integer laveScore) {
		this.laveScore = laveScore;
	}
	public Integer getTotalPost() {
		return totalPost;
	}
	public void setTotalPost(Integer totalPost) {
		this.totalPost = totalPost;
	}
	public Integer getTotalReply() {
		return totalReply;
	}
	public void setTotalReply(Integer totalReply) {
		this.totalReply = totalReply;
	}
	public Integer getTotalQA() {
		return totalQA;
	}
	public void setTotalQA(Integer totalQA) {
		this.totalQA = totalQA;
	}
	public Integer getTotalReplyQA() {
		return totalReplyQA;
	}
	public void setTotalReplyQA(Integer totalReplyQA) {
		this.totalReplyQA = totalReplyQA;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

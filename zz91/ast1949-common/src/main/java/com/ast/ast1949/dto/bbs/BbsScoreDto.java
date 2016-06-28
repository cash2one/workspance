package com.ast.ast1949.dto.bbs;

import java.io.Serializable;

import com.ast.ast1949.domain.bbs.BbsScore;

public class BbsScoreDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1244027004769674854L;

	private BbsScore bbsScore;

	private Integer bbsPostId;
	private String title;
	private String content;
	private Integer visitedCount;
	private Integer replyCount;

	private Integer scoreType;
	
	private Integer companyId;

	public Integer getScoreType() {
		return scoreType;
	}

	public void setScoreType(Integer scoreType) {
		this.scoreType = scoreType;
	}

	public BbsScore getBbsScore() {
		return bbsScore;
	}

	public void setBbsScore(BbsScore bbsScore) {
		this.bbsScore = bbsScore;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getVisitedCount() {
		return visitedCount;
	}

	public void setVisitedCount(Integer visitedCount) {
		this.visitedCount = visitedCount;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public Integer getBbsPostId() {
		return bbsPostId;
	}

	public void setBbsPostId(Integer bbsPostId) {
		this.bbsPostId = bbsPostId;
	}

}

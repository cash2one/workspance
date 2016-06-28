/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-29
 */
package com.ast.ast1949.dto.bbs;

import java.io.Serializable;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.Company;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-6-29
 */
public class PostDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BbsPostDO post;
	private BbsUserProfilerDO profiler;
	private Company company;

	private String bbsPostCategoryName;
	private String membershipLabel;
	private String postTypeName;
	private String contact;
	private String categoryName;

	private String replyCompanyId; // 回复人 companyId
	private String replyName; // 回复人
	
	private String contentIntro; // 内容缩略，不超过30个

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return the post
	 */
	public BbsPostDO getPost() {
		return post;
	}

	/**
	 * @param post
	 *            the post to set
	 */
	public void setPost(BbsPostDO post) {
		this.post = post;
	}

	/**
	 * @return the profiler
	 */
	public BbsUserProfilerDO getProfiler() {
		return profiler;
	}

	/**
	 * @param profiler
	 *            the profiler to set
	 */
	public void setProfiler(BbsUserProfilerDO profiler) {
		this.profiler = profiler;
	}

	/**
	 * @return the bbsPostCategoryName
	 */
	public String getBbsPostCategoryName() {
		return bbsPostCategoryName;
	}

	/**
	 * @param bbsPostCategoryName
	 *            the bbsPostCategoryName to set
	 */
	public void setBbsPostCategoryName(String bbsPostCategoryName) {
		this.bbsPostCategoryName = bbsPostCategoryName;
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the membershipLabel
	 */
	public String getMembershipLabel() {
		return membershipLabel;
	}

	/**
	 * @param membershipLabel
	 *            the membershipLabel to set
	 */
	public void setMembershipLabel(String membershipLabel) {
		this.membershipLabel = membershipLabel;
	}

	/**
	 * @return the postTypeName
	 */
	public String getPostTypeName() {
		return postTypeName;
	}

	/**
	 * @param postTypeName
	 *            the postTypeName to set
	 */
	public void setPostTypeName(String postTypeName) {
		this.postTypeName = postTypeName;
	}

	public String getReplyCompanyId() {
		return replyCompanyId;
	}

	public void setReplyCompanyId(String replyCompanyId) {
		this.replyCompanyId = replyCompanyId;
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

	public String getContentIntro() {
		return contentIntro;
	}

	public void setContentIntro(String contentIntro) {
		this.contentIntro = contentIntro;
	}

}

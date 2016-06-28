/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-10
 */
package com.ast.ast1949.dto.site;

import com.ast.ast1949.domain.DomainSupport;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.site.FeedbackDo;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-3-10
 */
public class FeedbackDto extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -894719345632056271L;
	
	private FeedbackDo feedback;
	private Company company;
	private CompanyAccount contact;
	private BbsUserProfilerDO bbsUserProfiler;
	
	/**
	 * @return the feedback
	 */
	public FeedbackDo getFeedback() {
		return feedback;
	}
	/**
	 * @param feedback the feedback to set
	 */
	public void setFeedback(FeedbackDo feedback) {
		this.feedback = feedback;
	}
	/**
	 * @return the bbsUserProfiler
	 */
	public BbsUserProfilerDO getBbsUserProfiler() {
		return bbsUserProfiler;
	}
	/**
	 * @param bbsUserProfiler the bbsUserProfiler to set
	 */
	public void setBbsUserProfiler(BbsUserProfilerDO bbsUserProfiler) {
		this.bbsUserProfiler = bbsUserProfiler;
	}
	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
	/**
	 * @return the contact
	 */
	public CompanyAccount getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(CompanyAccount contact) {
		this.contact = contact;
	}
}

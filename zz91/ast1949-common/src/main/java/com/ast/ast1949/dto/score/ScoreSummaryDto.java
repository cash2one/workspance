/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-1
 */
package com.ast.ast1949.dto.score;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.score.ScoreSummaryDo;


/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-3-1
 */
public class ScoreSummaryDto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private ScoreSummaryDo summary;
	private Company company;
	private CompanyAccount contact;
	
	/**
	 * 用来提供搜索条件
	 * */
	private String email;
	
	private String picturePath;
	
	/**
	 * @return the summary
	 */
	public ScoreSummaryDo getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(ScoreSummaryDo summary) {
		this.summary = summary;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the picturePath
	 */
	public String getPicturePath() {
		return picturePath;
	}
	/**
	 * @param picturePath the picturePath to set
	 */
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
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

/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-21
 */
package com.ast.ast1949.dto.credit;

import java.io.Serializable;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.credit.CreditCustomerVoteDo;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-21
 */
public class CreditCustomerVoteDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private CreditCustomerVoteDo vote;
	private Company company;
	private Integer dateDiff;
	private String fromCompanyName;
	private String toCompanyName;
	private String fromEmail;

	/**
	 * @return the vote
	 */
	public CreditCustomerVoteDo getVote() {
		return vote;
	}
	/**
	 * @param vote the vote to set
	 */
	public void setVote(CreditCustomerVoteDo vote) {
		this.vote = vote;
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
	 * @return the dateDiff
	 */
	public Integer getDateDiff() {
		return dateDiff;
	}
	/**
	 * @param dateDiff the dateDiff to set
	 */
	public void setDateDiff(Integer dateDiff) {
		this.dateDiff = dateDiff;
	}
	/**
	 * @return the fromCompanyName
	 */
	public String getFromCompanyName() {
		return fromCompanyName;
	}
	/**
	 * @param fromCompanyName the fromCompanyName to set
	 */
	public void setFromCompanyName(String fromCompanyName) {
		this.fromCompanyName = fromCompanyName;
	}
	/**
	 * @return the toCompanyName
	 */
	public String getToCompanyName() {
		return toCompanyName;
	}
	/**
	 * @param toCompanyName the toCompanyName to set
	 */
	public void setToCompanyName(String toCompanyName) {
		this.toCompanyName = toCompanyName;
	}
	/**
	 * @return the fromEmail
	 */
	public String getFromEmail() {
		return fromEmail;
	}
	/**
	 * @param fromEmail the fromEmail to set
	 */
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
}
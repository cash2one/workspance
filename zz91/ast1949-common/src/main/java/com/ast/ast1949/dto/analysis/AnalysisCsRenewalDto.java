/**
 * 
 */
package com.ast.ast1949.dto.analysis;

import java.io.Serializable;
import java.util.Date;

/**
 * @author root
 *
 */
public class AnalysisCsRenewalDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String csAccount;
	private Date gmtEnd;
	private Date gmtPreEnd;
	private Date gmtSigned;
	
	/**
	 * @return the gmtEnd
	 */
	public Date getGmtEnd() {
		return gmtEnd;
	}
	/**
	 * @param gmtEnd the gmtEnd to set
	 */
	public void setGmtEnd(Date gmtEnd) {
		this.gmtEnd = gmtEnd;
	}
	/**
	 * @return the csAccount
	 */
	public String getCsAccount() {
		return csAccount;
	}
	/**
	 * @param csAccount the csAccount to set
	 */
	public void setCsAccount(String csAccount) {
		this.csAccount = csAccount;
	}
	/**
	 * @return the gmtPreEnd
	 */
	public Date getGmtPreEnd() {
		return gmtPreEnd;
	}
	/**
	 * @param gmtPreEnd the gmtPreEnd to set
	 */
	public void setGmtPreEnd(Date gmtPreEnd) {
		this.gmtPreEnd = gmtPreEnd;
	}
	/**
	 * @return the gmtSigned
	 */
	public Date getGmtSigned() {
		return gmtSigned;
	}
	/**
	 * @param gmtSigned the gmtSigned to set
	 */
	public void setGmtSigned(Date gmtSigned) {
		this.gmtSigned = gmtSigned;
	}
	
	
}

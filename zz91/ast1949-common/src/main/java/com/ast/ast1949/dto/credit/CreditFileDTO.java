/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-25.
 */
package com.ast.ast1949.dto.credit;

import com.ast.ast1949.domain.credit.CreditFileDo;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
public class CreditFileDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CreditFileDo creditFile;
	private String companyName;
	private String categoryName;
	private String membershipCode;
	private Integer isVip;
	private Integer isNoVip;

	/**
	 * @return the creditFile
	 */
	public CreditFileDo getCreditFile() {
		return creditFile;
	}

	/**
	 * @param creditFile
	 *            the creditFile to set
	 */
	public void setCreditFile(CreditFileDo creditFile) {
		this.creditFile = creditFile;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getMembershipCode() {
		return membershipCode;
	}

	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public Integer getIsNoVip() {
		return isNoVip;
	}

	public void setIsNoVip(Integer isNoVip) {
		this.isNoVip = isNoVip;
	}

}

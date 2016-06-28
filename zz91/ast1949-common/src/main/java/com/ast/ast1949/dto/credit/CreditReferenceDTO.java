/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-24.
 */
package com.ast.ast1949.dto.credit;

import com.ast.ast1949.domain.credit.CreditReferenceDo;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class CreditReferenceDTO implements java.io.Serializable {
	/**
	 * 序列化 
	 */
	private static final long serialVersionUID = 1L;
	
	private CreditReferenceDo creditReference;
	private String creditCompanyName;
	
	/**
	 * @return the creditReference
	 */
	public CreditReferenceDo getCreditReference() {
		return creditReference;
	}
	/**
	 * @param creditReference the creditReference to set
	 */
	public void setCreditReference(CreditReferenceDo creditReference) {
		this.creditReference = creditReference;
	}
	/**
	 * @return the creditCompanyName
	 */
	public String getCreditCompanyName() {
		return creditCompanyName;
	}
	/**
	 * @param creditCompanyName the creditCompanyName to set
	 */
	public void setCreditCompanyName(String creditCompanyName) {
		this.creditCompanyName = creditCompanyName;
	}
}

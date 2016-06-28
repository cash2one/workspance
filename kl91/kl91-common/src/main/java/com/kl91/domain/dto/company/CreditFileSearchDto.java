package com.kl91.domain.dto.company;

import com.kl91.domain.DomainSupport;
import com.kl91.domain.company.CreditFile;

public class CreditFileSearchDto extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8148957485667171840L;
	
	private Integer cid;
	private CreditFile creditFile;

	public CreditFile getCreditFile() {
		return creditFile;
	}

	public void setCreditFile(CreditFile creditFile) {
		this.creditFile = creditFile;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

}

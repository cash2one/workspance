/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-7-16
 */
package com.ast.ast1949.dto.company;

import com.ast.ast1949.domain.company.MemberApplyDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class MemberApplyDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PageDto page;
	private MemberApplyDO memberApply;
	private String membershipName;
	
	/**
	 * @return the page
	 */
	public PageDto getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(PageDto page) {
		this.page = page;
	}
	/**
	 * @return the memberApply
	 */
	public MemberApplyDO getMemberApply() {
		return memberApply;
	}
	/**
	 * @param memberApply the memberApply to set
	 */
	public void setMemberApply(MemberApplyDO memberApply) {
		this.memberApply = memberApply;
	}
	/**
	 * @return the membershipName
	 */
	public String getMembershipName() {
		return membershipName;
	}
	/**
	 * @param membershipName the membershipName to set
	 */
	public void setMembershipName(String membershipName) {
		this.membershipName = membershipName;
	}
}

package com.ast.ast1949.dto.trust;

import com.ast.ast1949.domain.DomainSupport;

public class TrustCsLogSearchDto extends DomainSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8689902798060719441L;

	private String from ;
	private String to;
	private Integer star;
	private String flag; // 小计类型， 公司：C ，采购：B
	private String trustAccount;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getTrustAccount() {
		return trustAccount;
	}
	public void setTrustAccount(String trustAccount) {
		this.trustAccount = trustAccount;
	}
	
}

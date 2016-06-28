package com.zz91.ep.dto.comp;

import java.io.Serializable;

public class EsiteMemberDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String memberCode;

	private String memberName;

	private String memberYear;

	private String cssStyle;

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberYear() {
		return memberYear;
	}

	public void setMemberYear(String memberYear) {
		this.memberYear = memberYear;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

}

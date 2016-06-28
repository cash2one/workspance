package com.zz91.ep.dto.comp;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import com.zz91.ep.domain.comp.CompNews;

public class CompNewsDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private CompNews compNews;
	private String compName;
	private String memberCode;
	private Map<String,String> keyTags;
	
	
	
	
	public Map<String, String> getKeyTags() {
		return keyTags;
	}
	public void setKeyTags(Map<String, String> keyTags) {
		this.keyTags = keyTags;
	}
	public void setCompNews(CompNews compNews) {
		this.compNews = compNews;
	}
	public CompNews getCompNews() {
		return compNews;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getCompName() {
		return compName;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	
}

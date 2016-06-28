package com.ast.ast1949.domain.other;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class SubjectBaoming extends DomainSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2725755445257007594L;
     private Integer id;
     private String title;
     private String contents;
     private Date gmtcreated;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Date getGmtcreated() {
		return gmtcreated;
	}
	public void setGmtcreated(Date gmtcreated) {
		this.gmtcreated = gmtcreated;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


     
}
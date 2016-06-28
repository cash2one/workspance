package com.zz91.ep.dto.comp;

import java.io.Serializable;
import java.util.Date;


public class CompNewsAndNewsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String  title;
	private String typeCode;
	private Date gmtPublish;
	
	
	
	public Date getGmtPublish() {
		return gmtPublish;
	}
	public void setGmtPublish(Date gmtPublish) {
		this.gmtPublish = gmtPublish;
	}
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	

}

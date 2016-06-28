package com.zz91.ep.domain.common;

import java.util.Date;

public class HideInfo {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer targetId; // 对应数据类型的目标id
	private String targetType; // 对应数据的类型
	private Date gmtCreated;
	private Date gmtModified;
   
     
	public HideInfo(){
		super();
	}
	public HideInfo(Integer targetId,String targetType,Date gmtCreated,Date gmtModified) {
		super();
		this.targetId=targetId;
		this.targetType=targetType;
		this.gmtCreated=gmtCreated;
		this.gmtModified=gmtModified;
	}
     
     
	
	 
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}

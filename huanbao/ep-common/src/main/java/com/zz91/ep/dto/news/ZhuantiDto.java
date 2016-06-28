package com.zz91.ep.dto.news;

import java.io.Serializable;

import com.zz91.ep.domain.news.Zhuanti;

public class ZhuantiDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Zhuanti zhuanti;
	private String categoryName;
	private String gmtPublishStr;
	
	
	public String getGmtPublishStr() {
		return gmtPublishStr;
	}
	public void setGmtPublishStr(String gmtPublishStr) {
		this.gmtPublishStr = gmtPublishStr;
	}
	public Zhuanti getZhuanti() {
		return zhuanti;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setZhuanti(Zhuanti zhuanti) {
		this.zhuanti = zhuanti;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}

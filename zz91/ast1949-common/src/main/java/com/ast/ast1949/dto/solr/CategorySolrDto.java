package com.ast.ast1949.dto.solr;

import java.io.Serializable;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

public class CategorySolrDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Field
	private Integer id;
	@Field
	private String code;
	@Field
	private String label;
	@Field
	private String isAssist;  //辅助类别
	@Field
	private String isDel;     //是否删除
	@Field
	private Date gmtCreated;
	@Field
	private Date gmtModified;
	@Field
	private String cnspell;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getIsAssist() {
		return isAssist;
	}
	public void setIsAssist(String isAssist) {
		this.isAssist = isAssist;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
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
	public String getCnspell() {
		return cnspell;
	}
	public void setCnspell(String cnspell) {
		this.cnspell = cnspell;
	}

}

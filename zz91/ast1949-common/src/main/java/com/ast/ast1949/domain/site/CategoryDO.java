/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009-12-9 by Ryan.
 */
package com.ast.ast1949.domain.site;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * 网站通用类别
 * @author Ryan
 *
 */
public class CategoryDO extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private Integer id;// id int(11) 
	private String code;// code varchar(200) '类别编号',
	private String parentCode;// parent_code varchar(200) '父类别CODE',
	private Integer showIndex;// show_index int(4) '类别排序',
	private String label;// label varchar(200) '类别名称',
	private String abbreviation;// abbreviation varchar(45) '名称缩写或中文首字母',
	private Boolean isLeaf;// is_leaf tinyint(1) '是否是子节点',
	private Integer creator;// creator int(20) '创建人',
	private Date gmtCreated; //gmt_created  DATETIME
	private Date gmtModified;//gmt_modified DATETIME
	private String description;// description varchar(100) 类型说明',
	private String pinyin;
	
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
//	public Integer getId() {
//		return id;
//	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param showIndex the showIndex to set
	 */
	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}
	/**
	 * @return the showIndex
	 */
	public Integer getShowIndex() {
		return showIndex;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param gmtCreated the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}
	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	public Boolean getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
}

package com.ast.feiliao91.domain.common;

import java.io.Serializable;
import java.util.Date;

/**
 * category表
 * 
 * @author liujx
 *
 */
public class Category implements Serializable {
	private Integer id;
	private String code; // 属性类别编码
	private String parentcode; // 父类别属性类别编码
	private String label; // 属性类别名称
	private String pinyin; // 属性类别名称拼音
	private Integer isDel; // 删除状态：未删除 0；删除 1
	private Date gmtcreated; // 创建时间
	private Date gmtmodified; // 更新时间

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

	public String getParentcode() {
		return parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Date getGmtcreated() {
		return gmtcreated;
	}

	public void setGmtcreated(Date gmtcreated) {
		this.gmtcreated = gmtcreated;
	}

	public Date getGmtmodified() {
		return gmtmodified;
	}

	public void setGmtmodified(Date gmtmodified) {
		this.gmtmodified = gmtmodified;
	}

}

/*
 * 文件名称：TradeProperty.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.trade;

import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：专业属性实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class TradeProperty implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// 编号
	private String categoryCode;// 类别
	private String code;// code
	private String name;// 名称
	private Short inputRequired;// 是否必填
	private String vtype;// 类型
	private String vtypeReg;// 验证表达式
	private String vtypeValue;// 
	private String searchValue;
	private Short searchable;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryCode() {
		return this.categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getInputRequired() {
		return this.inputRequired;
	}

	public void setInputRequired(Short inputRequired) {
		this.inputRequired = inputRequired;
	}

	public String getVtype() {
		return this.vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	public String getVtypeReg() {
		return this.vtypeReg;
	}

	public void setVtypeReg(String vtypeReg) {
		this.vtypeReg = vtypeReg;
	}

	public String getVtypeValue() {
		return this.vtypeValue;
	}

	public void setVtypeValue(String vtypeValue) {
		this.vtypeValue = vtypeValue;
	}

	public String getSearchValue() {
		return this.searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Short getSearchable() {
		return this.searchable;
	}

	public void setSearchable(Short searchable) {
		this.searchable = searchable;
	}

	public Date getGmtCreated() {
		return this.gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return this.gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
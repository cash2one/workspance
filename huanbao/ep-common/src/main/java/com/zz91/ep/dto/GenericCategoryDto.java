/*
 * 文件名称：GenericCategoryDto.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：通用类别链表,已经根据父子关系整理。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class GenericCategoryDto<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private T categoryDomain;

	private String preCode;

	private Integer count;

	private List<String> tagsList;

	private List<GenericCategoryDto<T>> child;

	public T getCategoryDomain() {
		return categoryDomain;
	}

	public void setCategoryDomain(T categoryDomain) {
		this.categoryDomain = categoryDomain;
	}

	public String getPreCode() {
		return preCode;
	}

	public void setPreCode(String preCode) {
		this.preCode = preCode;
	}

	public void setChild(List<GenericCategoryDto<T>> child) {
		this.child = child;
	}

	public List<GenericCategoryDto<T>> getChild() {
		return child;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getCount() {
		return count;
	}

	public void setTagsList(List<String> tagsList) {
		this.tagsList = tagsList;
	}

	public List<String> getTagsList() {
		return tagsList;
	}
	
}
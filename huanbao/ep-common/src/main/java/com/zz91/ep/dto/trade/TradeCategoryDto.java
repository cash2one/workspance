package com.zz91.ep.dto.trade;

import java.util.Date;
/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：交易类别扩展。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-07-23　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
 */
public class TradeCategoryDto implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	
	private Integer id;
	
	private String code;
	
	private String name;
	
	private Integer leaf;
	
	private String tags;
	
	private Date gmtCreated;
	
	private Date gmtModified;
	
	private Integer sort;
	
	private Integer pageNum;
	
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getLeaf() {
		return leaf;
	}
	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}
	public Integer getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public String getTags() {
		return tags;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public Integer getSort() {
		return sort;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
}

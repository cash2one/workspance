package com.zz91.ep.dto.trade;

import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：子网类别扩展。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-07-24　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
 */
public class SubnetCategorySearchDto implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	
	private Integer parentId;
	
	private String code;
	
	private String name;
	
	private String keyword;
	
	private Integer sort;
	
	private Integer showIndex;
	
	private Date gmtCreated;
	
	private Date gmtModified;
	
	private Integer pageNum;
	
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getId() {
		return id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public String getKeyword() {
		return keyword;
	}
	public Integer getSort() {
		return sort;
	}
	public Integer getShowIndex() {
		return showIndex;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	
	
}

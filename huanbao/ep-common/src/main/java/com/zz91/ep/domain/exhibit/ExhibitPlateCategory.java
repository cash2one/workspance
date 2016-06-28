/*
 * 文件名称：ExhibitPlateCategory.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.exhibit;

import java.util.Date;

/**
 * 项目名称：中国环保网 
 * 模块编号：数据持久层 
 * 模块描述：展会版块类别类。 
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 * 　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class ExhibitPlateCategory implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// 编号
	private String name;// 版块名称
	private String code;// 版块code
	private Integer sort;// 排序
	private Integer showIndex;// 是否在首页显示
	private String tags;// 搜索标签
	private Date gmtCreated;// 创建时间
	private Date gmtModified;// 修改时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
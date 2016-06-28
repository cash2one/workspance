/*
 * 文件名称：TradeGroupDto.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-18 下午2:32:44
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto.trade;

import java.io.Serializable;
import java.util.List;

import com.zz91.ep.domain.trade.TradeGroup;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：用户自定义类别结构
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class TradeGroupDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer cid;
	private String name;
	private Short sort;
	private List<TradeGroup> childs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String string) {
		this.name = string;
	}

	public List<TradeGroup> getChilds() {
		return childs;
	}

	public void setChilds(List<TradeGroup> childs) {
		this.childs = childs;
	}

	public Short getSort() {
		return sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

}

/*
 * 文件名称：PageDto.java
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
 * 模块描述：用于Ext表格分页时返回的数据信息。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件 
 */
public class PageDto<E> implements Serializable{
	
	public static final String SQL_KEY="page";

	private static final long serialVersionUID = 1L;
	final static int DEFAULT_SIZE		= 20;
	final static String DEFAULT_DIR		= "desc";

	private Integer totals;  		//总数据数
	private Integer start;  		//页首
	private String sort;  			//排序字段
	private String dir; 			//desc or asc
	private Integer limit;  			//分页大小
 
	private List<E> records;// 记录集

	public PageDto() {
		this(DEFAULT_SIZE,null,DEFAULT_DIR);
	}

	public PageDto(int pageSize){
		this(pageSize,null,DEFAULT_DIR);
	}

	public PageDto(int limit, String sort, String dir){
		if(limit<=0){
			limit=DEFAULT_SIZE;
		}
		this.limit = limit;
		this.sort = sort;
		this.dir = dir;
	}


	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the records
	 */
	public List<E> getRecords() {
		return records;
	}

	/**
	 * @param records the records to set
	 */
	public void setRecords(List<E> records) {
		this.records = records;
	}

	/**
	 * @return the start
	 */
	public Integer getStart() {
		if(start==null){
			start=0;
		}
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public Integer getLimit() {
		if(limit==null){
			limit=20;
		}
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getTotals() {
		return totals;
	}

	public void setTotals(Integer totals) {
		this.totals = totals;
	}

}

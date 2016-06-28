/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author totly created on 2011-12-10
 */
public class PageDto<E> implements Serializable{

    private static final long serialVersionUID = 1L;
    public static final String SQL_KEY="page";
    private final static int DEFAULT_SIZE        = 20;
    private final static String DEFAULT_DIR        = "desc";
    
    private Integer totals;          //总数据数
    private Integer start;          //页首
    private String sort;              //排序字段
    private String dir;             //desc or asc
    private Integer limit;              //分页大小
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
    public Integer getTotals() {
        return totals;
    }
    public void setTotals(Integer totals) {
        this.totals = totals;
    }
    public Integer getStart() {
        if(start == null){
            start = 0;
        }
        return start;
    }
    public void setStart(Integer start) {
        this.start = start;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getDir() {
        return dir;
    }
    public void setDir(String dir) {
        this.dir = dir;
    }
    public Integer getLimit() {
        if(limit == null){
            limit = 20;
        }
        return limit;
    }
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    public List<E> getRecords() {
        return records;
    }
    public void setRecords(List<E> records) {
        this.records = records;
    }
}
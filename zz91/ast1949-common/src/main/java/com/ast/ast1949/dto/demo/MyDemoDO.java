/*
 *文件名：Demos.java
 *作者：luocheng
 *Email:luocheng_19881128@126.com
 *创建日期：上午11:20:59
 */
package com.ast.ast1949.dto.demo;

import java.io.Serializable;
import java.util.Date;

public class MyDemoDO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;//id号
    private String zhengshuName;//证书名称
    private String zhengshuFrom;//证书发证机构
    private char zhengshuState;//证书审核状态（0未审核1已审核）
    private Date createTeime;//登陆时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getZhengshuName() {
		return zhengshuName;
	}
	public void setZhengshuName(String zhengshuName) {
		this.zhengshuName = zhengshuName;
	}
	public String getZhengshuFrom() {
		return zhengshuFrom;
	}
	public void setZhengshuFrom(String zhengshuFrom) {
		this.zhengshuFrom = zhengshuFrom;
	}
	public char getZhengshuState() {
		return zhengshuState;
	}
	public void setZhengshuState(char zhengshuState) {
		this.zhengshuState = zhengshuState;
	}
	public Date getCreateTeime() {
		return createTeime;
	}
	public void setCreateTeime(Date createTeime) {
		this.createTeime = createTeime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static Object getProductsTypeCode() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}

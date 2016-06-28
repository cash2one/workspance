/*
 * 文件名称：IbdCategory.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-26 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.trade;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：行业买家库类别实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-06-26　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class IbdCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// ID
	private String code;// CODE
	private String name;// 名称
	private Integer isLeaf;// 是否是叶子节点
	private String downloadPath;// 下载地址
	private Integer isShow;// 是否显示
	private Date gmtCreated;// 创建时间
	private Date gmtModified;// 更新时间
	/**
	 * id的Getter方法
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * id的Setter方法
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * code的Getter方法
	 */
	public String getCode() {
		return code;
	}
	/**
	 * code的Setter方法
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * name的Getter方法
	 */
	public String getName() {
		return name;
	}
	/**
	 * name的Setter方法
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * isLeaf的Getter方法
	 */
	public Integer getIsLeaf() {
		return isLeaf;
	}
	/**
	 * isLeaf的Setter方法
	 */
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	/**
	 * downloadPath的Getter方法
	 */
	public String getDownloadPath() {
		return downloadPath;
	}
	/**
	 * downloadPath的Setter方法
	 */
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	/**
	 * isShow的Getter方法
	 */
	public Integer getIsShow() {
		return isShow;
	}
	/**
	 * isShow的Setter方法
	 */
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	/**
	 * gmtCreated的Getter方法
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}
	/**
	 * gmtCreated的Setter方法
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	/**
	 * gmtModified的Getter方法
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	/**
	 * gmtModified的Setter方法
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
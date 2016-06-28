/*
 * 文件名称：CompCategory.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.comp;

import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：推荐公司类别实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class CompCategory implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// 编号
	private String name;// 推荐类型名称
	private String code;// 推荐code
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

}
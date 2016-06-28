/*
 * 文件名称：TradeRecommend.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.trade;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：推荐供求信息实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class TradeRecommend implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer cid;
	private Integer targetId;
	private Short type;
	private Date gmtCreated;
	private Date gmtModified;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Short getType() {
		return type;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

}
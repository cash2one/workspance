package com.ast.ast1949.domain.analysis;

import java.io.Serializable;
import java.util.Date;

public class AnalysisPpcLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer cid;// 公司id
	private Integer pv;// 总访问量
	private Integer uv;// ip人数
	private Date gmtTarget;// 统计当天的时间
	private Date gmtCreated;
	private Date gmtModified;
	private Date from;// 开始时间 用于搜索
	private Date to;// 结束时间 用于搜索

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCid() {
		return cid;
	}

	public Integer getPv() {
		return pv;
	}

	public Integer getUv() {
		return uv;
	}

	public Date getGmtTarget() {
		return gmtTarget;
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

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public void setUv(Integer uv) {
		this.uv = uv;
	}

	public void setGmtTarget(Date gmtTarget) {
		this.gmtTarget = gmtTarget;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}

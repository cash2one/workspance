package com.ast.ast1949.domain.bbs;

import java.io.Serializable;
import java.util.Date;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-8-17 
 */
public class AnalysisBbsTop implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String category;
	private String title;
	private Integer targetId;
	private Integer num;
	private Integer gmtTarget;
	private Date gmtCreated;
	private Date gmtModified;
	
	public AnalysisBbsTop() {
		super();
	}
	
	public AnalysisBbsTop(Integer id,String category, String title, Integer targetId,
			Integer num, Integer gmtTarget, Date gmtCreated, Date gmtModified) {
		super();
		this.id=id;
		this.category = category;
		this.title = title;
		this.targetId = targetId;
		this.num = num;
		this.gmtTarget = gmtTarget;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getGmtTarget() {
		return gmtTarget;
	}
	public void setGmtTarget(Integer gmtTarget) {
		this.gmtTarget = gmtTarget;
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

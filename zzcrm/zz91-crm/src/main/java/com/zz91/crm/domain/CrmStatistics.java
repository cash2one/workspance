package com.zz91.crm.domain;

import java.io.Serializable;
import java.util.Date;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-2-13 
 */
public class CrmStatistics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer totals;
	private Integer seaCount;
	private Integer noActiveCount;
	private Integer selfCount;
	private Integer wasteCount;
	private Integer repeatCount;
	private Integer todayAssignCount;
	private Integer todayChooseCount;
	private Integer todayPutCount;
	private Date gmtTarget;
	private Date gmtCreated;
	private Date gmtModified;
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setTotals(Integer totals) {
		this.totals = totals;
	}
	public Integer getTotals() {
		return totals;
	}
	public void setSeaCount(Integer seaCount) {
		this.seaCount = seaCount;
	}
	public Integer getSeaCount() {
		return seaCount;
	}
	public void setNoActiveCount(Integer noActiveCount) {
		this.noActiveCount = noActiveCount;
	}
	public Integer getNoActiveCount() {
		return noActiveCount;
	}
	public void setSelfCount(Integer selfCount) {
		this.selfCount = selfCount;
	}
	public Integer getSelfCount() {
		return selfCount;
	}
	public void setWasteCount(Integer wasteCount) {
		this.wasteCount = wasteCount;
	}
	public Integer getWasteCount() {
		return wasteCount;
	}
	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}
	public Integer getRepeatCount() {
		return repeatCount;
	}
	public void setTodayAssignCount(Integer todayAssignCount) {
		this.todayAssignCount = todayAssignCount;
	}
	public Integer getTodayAssignCount() {
		return todayAssignCount;
	}
	public void setTodayChooseCount(Integer todayChooseCount) {
		this.todayChooseCount = todayChooseCount;
	}
	public Integer getTodayChooseCount() {
		return todayChooseCount;
	}
	public void setTodayPutCount(Integer todayPutCount) {
		this.todayPutCount = todayPutCount;
	}
	public Integer getTodayPutCount() {
		return todayPutCount;
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
	public void setGmtTarget(Date gmtTarget) {
		this.gmtTarget = gmtTarget;
	}
	public Date getGmtTarget() {
		return gmtTarget;
	}
}

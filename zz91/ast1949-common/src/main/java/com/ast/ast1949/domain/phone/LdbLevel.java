package com.ast.ast1949.domain.phone;

import java.util.Date;

public class LdbLevel {
	private Integer id;
	private Integer companyId;
	private Integer level;
	private double phoneRate;
	private Integer isDate;
	private double phoneCost;
	private Date gmtCreated;
	private Date gmtModified;
	
	private Integer score;
	private Integer maxExp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public double getPhoneRate() {
		return phoneRate;
	}

	public void setPhoneRate(double phoneRate) {
		this.phoneRate = phoneRate;
	}

	public Integer getIsDate() {
		return isDate;
	}

	public void setIsDate(Integer isDate) {
		this.isDate = isDate;
	}

	public double getPhoneCost() {
		return phoneCost;
	}

	public void setPhoneCost(double phoneCost) {
		this.phoneCost = phoneCost;
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
	
	public Integer getScore(){
		return Double.valueOf(this.phoneCost).intValue();
	}
	
	public Integer getMaxExp(){
		if (this.level==null) {
			this.level=0;
		}
		return (int) (Math.pow(2,this.level)*1000);
	}

	public void setScore(Integer score) {
	}

	public void setMaxExp(Integer maxExp) {
	}

}

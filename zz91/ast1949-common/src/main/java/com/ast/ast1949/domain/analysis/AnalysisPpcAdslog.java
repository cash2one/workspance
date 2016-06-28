package com.ast.ast1949.domain.analysis;

import java.util.Date;

public class AnalysisPpcAdslog {
	private Integer id;
	private String adposition;//广告位
	private Integer companyId;//公司id
	private Integer showcount;//展现量
	private Integer checkcount;//点击量
	private Date gmtCreated;
	private Integer phour;//小时
	private Date pdate;//日期
	public Integer getId() {
		return id;
	}
	public String getAdposition() {
		return adposition;
	}
	public void setAdposition(String adposition) {
		this.adposition = adposition;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getShowcount() {
		return showcount;
	}
	public void setShowcount(Integer showcount) {
		this.showcount = showcount;
	}
	public Integer getCheckcount() {
		return checkcount;
	}
	public void setCheckcount(Integer checkcount) {
		this.checkcount = checkcount;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Integer getPhour() {
		return phour;
	}
	public void setPhour(Integer phour) {
		this.phour = phour;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

}

package com.zz91.ep.domain.comp;

import java.util.Date;

public class CompanyIndustryChain implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Integer cid;
	
	private Integer chainId;
	
	private Date   gmtCreated;
	
	private Date gmtModified;
	
	private Short delStatus;

	public CompanyIndustryChain() {
		super();
		
	}

	public CompanyIndustryChain(Integer id, Integer cid, Integer chainId,
			Date gmtCreated, Date gmtModified, Short delStatus) {
		super();
		this.id = id;
		this.cid = cid;
		this.chainId = chainId;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
		this.delStatus = delStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getChainId() {
		return chainId;
	}

	public void setChainId(Integer chainId) {
		this.chainId = chainId;
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

	public Short getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Short delStatus) {
		this.delStatus = delStatus;
	}

}

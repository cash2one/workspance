/**
 * @author shiqp 日期:2014-11-24
 */
package com.ast.ast1949.domain.bbs;

import java.util.Date;

public class BbsPostTrends {
	private Integer id;
	private Integer companyId;
	private Integer bbsPostId;
	private String action;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getBbsPostId() {
		return bbsPostId;
	}

	public void setBbsPostId(Integer bbsPostId) {
		this.bbsPostId = bbsPostId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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

	public Integer getId() {
		return id;
	}

}

/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-7-10 下午03:08:35
 */
package com.zz91.crm.domain;

import java.io.Serializable;
import java.util.Date;

public class CrmTurnStarStatistics implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String saleDept;
	private String saleName;
	private String saleAccount;
	private Integer star4;
	private Integer star5;
	private Date gmtTarget;
	private Date gmtCreated;
	private Date gmtModified;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSaleDept() {
		return saleDept;
	}
	public void setSaleDept(String saleDept) {
		this.saleDept = saleDept;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getSaleAccount() {
		return saleAccount;
	}
	public void setSaleAccount(String saleAccount) {
		this.saleAccount = saleAccount;
	}
	public Integer getStar4() {
		return star4;
	}
	public void setStar4(Integer star4) {
		this.star4 = star4;
	}
	public Integer getStar5() {
		return star5;
	}
	public void setStar5(Integer star5) {
		this.star5 = star5;
	}
	public Date getGmtTarget() {
		return gmtTarget;
	}
	public void setGmtTarget(Date gmtTarget) {
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

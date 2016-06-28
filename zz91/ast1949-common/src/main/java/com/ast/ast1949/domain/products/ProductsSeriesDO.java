/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-30 by liulei.
 */
package com.ast.ast1949.domain.products;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liulei
 *
 */
public class ProductsSeriesDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;					
	private Integer companyId;			//公司ID
	private String account;				//帐号名
	private String name;				//供求系列名称
	private String seriesDetails;		//供求系列描述
	private Integer seriesOrder; 		//供求系列排序
	private Date gmtCreated;			//创建时间
	private Date gmtModified;			//修改时间
	
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSeriesDetails() {
		return seriesDetails;
	}
	public void setSeriesDetails(String seriesDetails) {
		this.seriesDetails = seriesDetails;
	}
	public Integer getSeriesOrder() {
		return seriesOrder;
	}
	public void setSeriesOrder(Integer seriesOrder) {
		this.seriesOrder = seriesOrder;
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

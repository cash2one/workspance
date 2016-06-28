package com.ast.ast1949.domain.products;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class ProductsZstExpiredDO extends DomainSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8771865751816333621L;
	
	private Integer id;
	private Integer productId;
	private Date gmtCreated;
	private Date gmtModified;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
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

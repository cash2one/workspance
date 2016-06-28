package com.ast.ast1949.domain.products;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class ProductsStar extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4260838743149934184L;
	/**
	 * 
	 */
	private Integer id;
	private Integer productsId;
	private Integer score;
	private Date gmtCreated;
	private Date gmtModified;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductsId() {
		return productsId;
	}
	public void setProductsId(Integer productsId) {
		this.productsId = productsId;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
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

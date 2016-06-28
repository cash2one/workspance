package com.ast.ast1949.domain.products;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class ProductsSpot extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5844479686852166850L;

	private Integer productId;
	private Date gmtCreated;
	private Date gmtModified;
	private String isTe;
	private String isHot;
	private String isYou;
	private String isBail;
	private Integer viewCount;

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

	public String getIsTe() {
		return isTe;
	}

	public void setIsTe(String isTe) {
		this.isTe = isTe;
	}

	public String getIsHot() {
		return isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}

	public String getIsYou() {
		return isYou;
	}

	public void setIsYou(String isYou) {
		this.isYou = isYou;
	}

	public String getIsBail() {
		return isBail;
	}

	public void setIsBail(String isBail) {
		this.isBail = isBail;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	

}

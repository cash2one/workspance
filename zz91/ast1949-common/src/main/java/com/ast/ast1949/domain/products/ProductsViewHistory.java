package com.ast.ast1949.domain.products;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kongsj
 * @date 2011-12-27
 */
public class ProductsViewHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6880241940464111307L;

	private Integer id;
	private String cookieKey;
	private Integer companyId;
	private Integer productId;
	private String productName;
	private String productPicUrl;
	private Date gmtLastView;
	private Date gmtCreated;
	private Date gmtModified;
	
	

	public ProductsViewHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public ProductsViewHistory(Integer id, String cookieKey, Integer companyId,
			Integer productId, String productName, String productPicUrl,
			Date gmtLastView, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.cookieKey = cookieKey;
		this.companyId = companyId;
		this.productId = productId;
		this.productName = productName;
		this.productPicUrl = productPicUrl;
		this.gmtLastView = gmtLastView;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCookieKey() {
		return cookieKey;
	}

	public void setCookieKey(String cookieKey) {
		this.cookieKey = cookieKey;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPicUrl() {
		return productPicUrl;
	}

	public void setProductPicUrl(String productPicUrl) {
		this.productPicUrl = productPicUrl;
	}

	public Date getGmtLastView() {
		return gmtLastView;
	}

	public void setGmtLastView(Date gmtLastView) {
		this.gmtLastView = gmtLastView;
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

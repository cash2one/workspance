/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-30 by liulei.
 */
package com.ast.ast1949.dto.products;

import java.io.Serializable;

/**
 * @author liulei
 *
 */
public class ProductsSeriesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;					
	private Integer companyId;					//公司ID
	private String account;						//帐号名
	private String name;						//供求系列名称
	private String seriesDetails;				//供求系列描述
	private Integer seriesOrder; 				//供求系列排序
	
	private Integer productsSeriesContactsId;	//供求系列关联ID
	
	private Integer productsId;					//关联供求ID
	private String title;						//供求标题

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

	public Integer getProductsSeriesContactsId() {
		return productsSeriesContactsId;
	}

	public void setProductsSeriesContactsId(Integer productsSeriesContactsId) {
		this.productsSeriesContactsId = productsSeriesContactsId;
	}

	public Integer getProductsId() {
		return productsId;
	}

	public void setProductsId(Integer productsId) {
		this.productsId = productsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

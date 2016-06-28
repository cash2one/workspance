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
public class ProductsSeriesContactsDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer productsId;			//关联供求ID
	private Integer productsSeriesId;	//关联供求系列ID
	private Date gmtCreated;			//创建时间
	private Date gmtModified;			//修改时间
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
	public Integer getProductsSeriesId() {
		return productsSeriesId;
	}
	public void setProductsSeriesId(Integer productsSeriesId) {
		this.productsSeriesId = productsSeriesId;
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

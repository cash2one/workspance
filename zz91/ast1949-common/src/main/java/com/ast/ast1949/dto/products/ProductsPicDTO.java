/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-26
 */
package com.ast.ast1949.dto.products;

import com.ast.ast1949.domain.products.ProductsAlbumsDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author yuyonghui
 * 
 */

public class ProductsPicDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PageDto page;
	private String albumName;
	private String productTitle;
	private String isCover;
	private ProductsPicDO productsPicDO;
	private ProductsDO productsDO;
	private ProductsAlbumsDO productsAlbumsDO;
	

	public PageDto getPage() {
		return page;
	}

	public void setPage(PageDto page) {
		this.page = page;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getIsCover() {
		return isCover;
	}

	public void setIsCover(String isCover) {
		this.isCover = isCover;
	}

	public ProductsPicDO getProductsPicDO() {
		return productsPicDO;
	}

	public void setProductsPicDO(ProductsPicDO productsPicDO) {
		this.productsPicDO = productsPicDO;
	}

	public ProductsDO getProductsDO() {
		return productsDO;
	}

	public void setProductsDO(ProductsDO productsDO) {
		this.productsDO = productsDO;
	}

	public ProductsAlbumsDO getProductsAlbumsDO() {
		return productsAlbumsDO;
	}

	public void setProductsAlbumsDO(ProductsAlbumsDO productsAlbumsDO) {
		this.productsAlbumsDO = productsAlbumsDO;
	}

}

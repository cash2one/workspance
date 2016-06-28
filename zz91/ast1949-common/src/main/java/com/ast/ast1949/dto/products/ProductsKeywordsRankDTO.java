/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-26.
 */
package com.ast.ast1949.dto.products;

import com.ast.ast1949.domain.products.ProductsKeywordsRankDO;
import com.ast.ast1949.dto.PageDto;

/**
 * 关键字排名
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class ProductsKeywordsRankDTO  implements java.io.Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PageDto page;
	private ProductsKeywordsRankDO productsKeywordsRank;
	private String companyName;
	private String productsTitle;
	private Boolean expire;
	private String  label;//category表中的服务名称字段统计标王等服务
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the page
	 */
	public PageDto getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(PageDto page) {
		this.page = page;
	}
	/**
	 * @return the productsKeywordsRank
	 */
	public ProductsKeywordsRankDO getProductsKeywordsRank() {
		return productsKeywordsRank;
	}
	/**
	 * @param productsKeywordsRank the productsKeywordsRank to set
	 */
	public void setProductsKeywordsRank(ProductsKeywordsRankDO productsKeywordsRank) {
		this.productsKeywordsRank = productsKeywordsRank;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the productsTitle
	 */
	public String getProductsTitle() {
		return productsTitle;
	}
	/**
	 * @param productsTitle the productsTitle to set
	 */
	public void setProductsTitle(String productsTitle) {
		this.productsTitle = productsTitle;
	}
	/**
	 * @return the expire
	 */
	public Boolean getExpire() {
		return expire;
	}
	/**
	 * @param expire the expire to set
	 */
	public void setExpire(Boolean expire) {
		this.expire = expire;
	}
	
	
}

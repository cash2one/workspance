/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-25
 */
package com.ast.ast1949.dto.price;

import java.util.List;

import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class PriceCategoryDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PriceCategoryDO priceCategoryDO;
	private String searchTitle;
	private PageDto page;
	private List<PriceCategoryDO> child;
	private List<ForPriceDTO> priceChild;
	private String parentName;
	
	private List<PriceDO> priceChildDO;
	
	public List<PriceDO> getPriceChildDO() {
		return priceChildDO;
	}
	public void setPriceChildDO(List<PriceDO> priceChildDO) {
		this.priceChildDO = priceChildDO;
	}
	public List<ForPriceDTO> getPriceChild() {
		return priceChild;
	}
	public void setPriceChild(List<ForPriceDTO> priceChild) {
		this.priceChild = priceChild;
	}
	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}
	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public List<PriceCategoryDO> getChild() {
		return child;
	}
	public void setChild(List<PriceCategoryDO> child) {
		this.child = child;
	}
	public PriceCategoryDO getPriceCategoryDO() {
		return priceCategoryDO;
	}
	public void setPriceCategoryDO(PriceCategoryDO priceCategoryDO) {
		this.priceCategoryDO = priceCategoryDO;
	}

	public PriceCategoryDO getNewsCategory() {
		return priceCategoryDO;
	}
	public void setNewsCategory(PriceCategoryDO newsCategory) {
		this.priceCategoryDO = newsCategory;
	}
	public String getSearchTitle() {
		return searchTitle;
	}
	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}
	public PageDto getPage() {
		return page;
	}
	public void setPage(PageDto page) {
		this.page = page;
	}
}

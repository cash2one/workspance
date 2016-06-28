package com.ast.ast1949.dto.price;

import java.util.List;

import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;

/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-19 by Rolyer.
 */

public class PriceDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PriceDO price;
	private String searchTitle;
	private PageDto page=new PageDto();
	// List<PriceDO> list; get set method
	private List<PriceDO> list;
	private List<PriceDTO> priceList;
	private String baseTypeName;
	private String typeName;
	private String assistTypeName;
	
	private Integer limitSize;
	private Integer typeId;
	private String typeUrl; // 类别地址
	private Integer parentId;
	private Integer assistTypeId;
	
	private Integer ip; //UV
	
	/**
	 * @return the priceList
	 */
	public List<PriceDTO> getPriceList() {
		return priceList;
	}
	/**
	 * @param priceList the priceList to set
	 */
	public void setPriceList(List<PriceDTO> priceList) {
		this.priceList = priceList;
	}
	/**
	 * @return the list
	 */
	public List<PriceDO> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<PriceDO> list) {
		this.list = list;
	}
	public Integer getAssistTypeId() {
		return assistTypeId;
	}
	public void setAssistTypeId(Integer assistTypeId) {
		this.assistTypeId = assistTypeId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getLimitSize() {
		return limitSize;
	}
	public void setLimitSize(Integer limitSize) {
		this.limitSize = limitSize;
	}
	public String getBaseTypeName() {
		return baseTypeName;
	}
	public void setBaseTypeName(String baseTypeName) {
		this.baseTypeName = baseTypeName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getAssistTypeName() {
		return assistTypeName;
	}
	public void setAssistTypeName(String assistTypeName) {
		this.assistTypeName = assistTypeName;
	}
	public PriceDO getPrice() {
		return price;
	}
	public void setPrice(PriceDO price) {
		this.price = price;
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
	public Integer getIp() {
		return ip;
	}
	public void setIp(Integer ip) {
		this.ip = ip;
	}
	public String getTypeUrl() {
		return typeUrl;
	}
	public void setTypeUrl(String typeUrl) {
		this.typeUrl = typeUrl;
	}

}

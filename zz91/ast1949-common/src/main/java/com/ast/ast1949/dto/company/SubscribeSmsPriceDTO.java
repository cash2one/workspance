package com.ast.ast1949.dto.company;

import java.io.Serializable;

import com.ast.ast1949.dto.PageDto;

public class SubscribeSmsPriceDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PageDto page;
	private String name;
	private String price;
	
	public PageDto getPage() {
		return page;
	}
	public void setPage(PageDto page) {
		this.page = page;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}

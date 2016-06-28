/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-9 下午03:40:43
 */
package com.ast.ast1949.dto.products;

import com.ast.ast1949.dto.PageDto;

/**
 * 前台搜索参数对象
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class ProductsSearchForFrontDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String keywords;//搜索关键词
	private Integer postInDays;//在几天内发布的
	private String provinceCode;//省份
	private String buyOrSale; //求购："1"；供应："2"
	private Integer mid;//categoryProductsId(main)
	private Integer aid;//categoryProductsId(assist)
	private String mc;//categoryProductsMainCode
	private String ac;//categoryProductsAssistCode
	private PageDto pageDto=new PageDto();

	/**
	 * @param pageDto the pageDto to set
	 */
	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}

	/**
	 * @return the pageDto
	 */
	public PageDto getPageDto() {
		return pageDto;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}


	/**
	 * @param mc the mc to set
	 */
	public void setMc(String mc) {
		this.mc = mc;
	}

	/**
	 * @return the mc
	 */
	public String getMc() {
		return mc;
	}

	/**
	 * @param ac the ac to set
	 */
	public void setAc(String ac) {
		this.ac = ac;
	}

	/**
	 * @return the ac
	 */
	public String getAc() {
		return ac;
	}


	/**
	 * @param postInDays the postInDays to set
	 */
	public void setPostInDays(Integer postInDays) {
		this.postInDays = postInDays;
	}

	/**
	 * @return the postInDays
	 */
	public Integer getPostInDays() {
		return postInDays;
	}


	public void setAid(Integer aId) {
		this.aid = aId;
	}

	public Integer getAid() {
		return aid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Integer getMid() {
		return mid;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setBuyOrSale(String buyOrSale) {
		this.buyOrSale = buyOrSale;
	}

	public String getBuyOrSale() {
		return buyOrSale;
	}


}

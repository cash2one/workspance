/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-8 下午05:15:56
 */
package com.ast.ast1949.dto;

/**
 * 用来设置网页头部信息和导航条选中项
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class PageHeadDTO implements java.io.Serializable {

	private static final long serialVersionUID = -4951241740626991537L;
	private String pageTitle;
	private String pageKeywords;
	private String pageDescription;
	private Integer topNavIndex;
	
	public PageHeadDTO(){
		
	}
	
	public PageHeadDTO(Integer topNavIndex){
		this.topNavIndex = topNavIndex;
	}

	public String getPageTitle() {
		if(pageTitle==null){
			return "废金属_废塑料_废纸_纺织废料_再生资源_废橡胶_${site_name}";
		}
		else{
			return pageTitle;
		}
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getPageKeywords() {
		if(pageKeywords==null){
			return "废金属,废塑料,废纸,纺织废料,再生资源,废橡胶";
		}
		else{
			return pageKeywords;
		}
	}

	public void setPageKeywords(String pageKeywords) {
		this.pageKeywords = pageKeywords;
	}

	public String getPageDescription() {
		if(pageDescription==null){
			return "${site_name}是中国最大最旺的网上废料贸易市场，这里为您精选了废金属，废塑料，废纸，废橡胶，" +
					"纺织废料，废旧设备，废电子电器，废玻璃，废皮革等各类废料供求信息、公司黄页、行业资讯、价格行情、" +
					"展会信息等";
		}
		else{
			return pageDescription;
		}
	}

	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}

	public Integer getTopNavIndex() {
		return topNavIndex;
	}

	public void setTopNavIndex(Integer topNavIndex) {
		this.topNavIndex = topNavIndex;
	}

}

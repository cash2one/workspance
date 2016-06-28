package com.ast.ast1949.domain;

public class PriceJson extends DomainSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gmt_time;
	private float min_price;
	private float max_price;
	private String title;
	private String url;
	private String price_unit;
	private String fulltitle;

	public float getMin_price() {
		return min_price;
	}
	public void setMin_price(float min_price) {
		this.min_price = min_price;
	}
	public float getMax_price() {
		return max_price;
	}
	public void setMax_price(float max_price) {
		this.max_price = max_price;
	}
	public String getGmt_time() {
		return gmt_time;
	}
	public void setGmt_time(String gmt_time) {
		this.gmt_time = gmt_time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPrice_unit() {
		return price_unit;
	}
	public void setPrice_unit(String price_unit) {
		this.price_unit = price_unit;
	}
	public String getFulltitle() {
		return fulltitle;
	}
	public void setFulltitle(String fulltitle) {
		this.fulltitle = fulltitle;
	}
	
}

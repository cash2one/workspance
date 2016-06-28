package com.ast.ast1949.dto.price;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class PriceCategoryMinDto extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer parentId;// 父类ID
	private String name;// 类型名称
	private String goUrl;
	private Integer showIndex;// 显示排序
	private Date gmtOrder;// 此类最新信息发布时间
	private String pinyin;

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoUrl() {
		return goUrl;
	}

	public void setGoUrl(String goUrl) {
		this.goUrl = goUrl;
	}

	public Integer getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}

	public Date getGmtOrder() {
		return gmtOrder;
	}

	public void setGmtOrder(Date gmtOrder) {
		this.gmtOrder = gmtOrder;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

}

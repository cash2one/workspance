package com.ast.feiliao91.domain.goods;

import java.io.Serializable;

public class GoodsAddProperties implements Serializable{
	/**
	 * 商品属性
	 */
	private static final long serialVersionUID = -5248319699613704178L;
	private Integer gid;//商品ID
    private String property;
    private String content;
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}

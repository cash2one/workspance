/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-12
 */
package com.ast.ast1949.dto.price;

import java.util.Date;


/**
 * @author yuyonghui
 *
 */
public class ForPriceDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer parentId;
	private Integer typeId;
	private String typeName;
	private String title;
	private String content;
	
	private Date gmtOrder;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getGmtOrder() {
		return gmtOrder;
	}
	public void setGmtOrder(Date gmtOrder) {
		this.gmtOrder = gmtOrder;
	}
	
}

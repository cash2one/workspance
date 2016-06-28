/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-1
 */
package com.ast.ast1949.domain.site;

import java.util.Date;

/**
 * @author yuyonghui
 *
 */
public class FriendLinkDO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String linkName;           //网站名称
	private String linkCategoryCode;  //网站类别
	private String height;            // 高度
	private String width;             // 长度
	private String picAddress;        //上传图片
	private String link;              //链接地址
	private Integer showIndex;        //排序
	private String textColor;         //文字颜色
	private String isChecked;		  //是否审核
	private Date addTime;             //添加时间
	private Date gmtCreated;          //创建时间
	private Date gmtModified;         //修改时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkCategoryCode() {
		return linkCategoryCode;
	}
	public void setLinkCategoryCode(String linkCategoryCode) {
		this.linkCategoryCode = linkCategoryCode;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}

	public String getTextColor() {
		return textColor;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public String getPicAddress() {
		return picAddress;
	}
	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Integer getShowIndex() {
		return showIndex;
	}
	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

}

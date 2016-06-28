/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23 by liulei.
 */
package com.ast.ast1949.domain.company;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liulei
 *
 */
public class EsiteConfigDo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer companyId;				//公司主键ID
	private String isFormal;				//
	private String flashindex;				//主页FLASH
	private String logoPic;					//网站logo
	private String navColumnConfig;			//网站栏目	
	private String isDefault;				//是否默认
	private String operation;				//操作情况
	private String mycolumn;					//当前栏目
	private String title;					//修改标题
	private String position;				//网站左右布局
	private String slogan;					//网站广告副标语
	private String subSlogan;				//网站广告主标语
	private String isTransparent;			//是否透明
	private String pageWidth;				//页面宽度
	private String styleContent;			//整体样式
	private String overAlllayout;			//板块位置
	private String deleteStyleId;			//
	private String customWidgets;			//
	private String cover;					//当前风格图片
	private String addstyleSelf;			//是否自定义风格
	private Date gmtCreated;				//创建时间
	private Date gmtModified;				//修改时间
	private String bannerPic;				//头部可以滚动图片
	private Integer isShow;					//是否显示上传的图片
	
	
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public String getBannerPic() {
		return bannerPic;
	}
	public void setBannerPic(String bannerPic) {
		this.bannerPic = bannerPic;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getIsFormal() {
		return isFormal;
	}
	public void setIsFormal(String isFormal) {
		this.isFormal = isFormal;
	}
	public String getFlashindex() {
		return flashindex;
	}
	public void setFlashindex(String flashindex) {
		this.flashindex = flashindex;
	}
	public String getLogoPic() {
		return logoPic;
	}
	public void setLogoPic(String logoPic) {
		this.logoPic = logoPic;
	}
	public String getNavColumnConfig() {
		return navColumnConfig;
	}
	public void setNavColumnConfig(String navColumnConfig) {
		this.navColumnConfig = navColumnConfig;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getMycolumn() {
		return mycolumn;
	}
	public void setMycolumn(String mycolumn) {
		this.mycolumn = mycolumn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	public String getSubSlogan() {
		return subSlogan;
	}
	public void setSubSlogan(String subSlogan) {
		this.subSlogan = subSlogan;
	}
	public String getIsTransparent() {
		return isTransparent;
	}
	public void setIsTransparent(String isTransparent) {
		this.isTransparent = isTransparent;
	}
	public String getPageWidth() {
		return pageWidth;
	}
	public void setPageWidth(String pageWidth) {
		this.pageWidth = pageWidth;
	}
	public String getStyleContent() {
		return styleContent;
	}
	public void setStyleContent(String styleContent) {
		this.styleContent = styleContent;
	}
	public String getOverAlllayout() {
		return overAlllayout;
	}
	public void setOverAlllayout(String overAlllayout) {
		this.overAlllayout = overAlllayout;
	}
	public String getDeleteStyleId() {
		return deleteStyleId;
	}
	public void setDeleteStyleId(String deleteStyleId) {
		this.deleteStyleId = deleteStyleId;
	}
	public String getCustomWidgets() {
		return customWidgets;
	}
	public void setCustomWidgets(String customWidgets) {
		this.customWidgets = customWidgets;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getAddstyleSelf() {
		return addstyleSelf;
	}
	public void setAddstyleSelf(String addstyleSelf) {
		this.addstyleSelf = addstyleSelf;
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

}

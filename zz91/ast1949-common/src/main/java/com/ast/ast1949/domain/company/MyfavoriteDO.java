/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-29
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.bbs.BbsPostDO;

/**
 * @author yuyonghui
 * 
 */
public class MyfavoriteDO implements java.io.Serializable {

	/**
	 * 我的篮子
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer companyId;
	private Integer contentId;// 收藏的信息ID
	private String contentTitle; // 收藏的信息的标题
	private String account;
	private String favoriteTypeCode; // 收藏夹类型
	private Date gmtCreated;// 收藏时间
	private Date gmtModified;

	private BbsPostDO bbsPostDO;

	public BbsPostDO getBbsPostDO() {
		return bbsPostDO;
	}

	public void setBbsPostDO(BbsPostDO bbsPostDO) {
		this.bbsPostDO = bbsPostDO;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getFavoriteTypeCode() {
		return favoriteTypeCode;
	}

	public void setFavoriteTypeCode(String favoriteTypeCode) {
		this.favoriteTypeCode = favoriteTypeCode;
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

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

}

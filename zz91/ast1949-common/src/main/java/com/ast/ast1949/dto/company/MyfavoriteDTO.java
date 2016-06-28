/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-29
 */
package com.ast.ast1949.dto.company;

import java.io.Serializable;
import java.util.Date;

import com.ast.ast1949.domain.company.MyfavoriteDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author yuyonghui
 * 
 */
public class MyfavoriteDTO implements Serializable {

	/**
	 * 我的篮子
	 */
	private static final long serialVersionUID = 1L;
	private PageDto pageDto = new PageDto();
	private MyfavoriteDO myfavoriteDO;

	private Integer id;
	private Integer contentId;
	private String favoriteType;
	private String title;
	private Date updateTime;
	private Date addTime;

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

	public String getFavoriteType() {
		return favoriteType;
	}

	public void setFavoriteType(String favoriteType) {
		this.favoriteType = favoriteType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public PageDto getPageDto() {
		return pageDto;
	}

	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}

	public MyfavoriteDO getMyfavoriteDO() {
		return myfavoriteDO;
	}

	public void setMyfavoriteDO(MyfavoriteDO myfavoriteDO) {
		this.myfavoriteDO = myfavoriteDO;
	}

}

/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-9
 */
package com.ast.ast1949.dto.information;

import java.io.Serializable;

import com.ast.ast1949.domain.information.WeeklyArticleDO;
import com.ast.ast1949.domain.information.WeeklyPageDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author yuyonghui
 *
 */
public class WeeklyDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String url;
	private String perdicalName;
	private WeeklyArticleDO weeklyArticleDO;
	private WeeklyPageDO weeklyPageDO;
	
	private PageDto page;
	
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
	
	public String getPerdicalName() {
		return perdicalName;
	}
	public void setPerdicalName(String perdicalName) {
		this.perdicalName = perdicalName;
	}
	public WeeklyArticleDO getWeeklyArticleDO() {
		return weeklyArticleDO;
	}
	public void setWeeklyArticleDO(WeeklyArticleDO weeklyArticleDO) {
		this.weeklyArticleDO = weeklyArticleDO;
	}
	
	public PageDto getPage() {
		return page;
	}
	public void setPage(PageDto page) {
		this.page = page;
	}

	public WeeklyPageDO getWeeklyPageDO() {
		return weeklyPageDO;
	}
	public void setWeeklyPageDO(WeeklyPageDO weeklyPageDO) {
		this.weeklyPageDO = weeklyPageDO;
	}
	
}

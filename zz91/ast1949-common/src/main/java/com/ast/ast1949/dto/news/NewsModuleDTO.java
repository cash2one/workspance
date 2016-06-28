/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 8, 2010 by Rolyer.
 */
package com.ast.ast1949.dto.news;

import com.ast.ast1949.domain.news.NewsModuleDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public class NewsModuleDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PageDto page;
	private NewsModuleDO newsModule;
	
	public PageDto getPage() {
		return page;
	}
	public void setPage(PageDto page) {
		this.page = page;
	}
	public NewsModuleDO getNewsModule() {
		return newsModule;
	}
	public void setNewsModule(NewsModuleDO newsModule) {
		this.newsModule = newsModule;
	}
}

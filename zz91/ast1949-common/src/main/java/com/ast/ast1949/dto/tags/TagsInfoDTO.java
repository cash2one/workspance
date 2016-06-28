/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-17 上午10:23:09
 */
package com.ast.ast1949.dto.tags;

import com.ast.ast1949.domain.tags.TagsInfoDO;
import com.ast.ast1949.dto.PageDto;

/**
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class TagsInfoDTO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private PageDto page=new PageDto();
	private TagsInfoDO tagsInfoDO;

	private Integer topNum;//
	private Integer clickCount;//点击次数
	private Integer useCount;//使用次数
	
	public Integer getTopNum() {
		return topNum;
	}
	public void setTopNum(Integer topNum) {
		this.topNum = topNum;
	}
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	public Integer getUseCount() {
		return useCount;
	}
	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}
	public PageDto getPage() {
		return page;
	}
	public void setPage(PageDto page) {
		this.page = page;
	}
	public TagsInfoDO getTagsInfoDO() {
		return tagsInfoDO;
	}
	public void setTagsInfoDO(TagsInfoDO tagsInfoDO) {
		this.tagsInfoDO = tagsInfoDO;
	}

}

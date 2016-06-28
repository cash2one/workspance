/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-1
 */
package com.ast.ast1949.dto.site;

import com.ast.ast1949.domain.site.FriendLinkDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author yuyonghui
 *
 */
public class FriendLinkDTO implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private FriendLinkDO friendLinkDO;
	private PageDto pageDto;
	private String linkName;   //按网站名称
	private String linkCategoryName; //按链接类别
	private Integer showIndex;

	public Integer getShowIndex() {
		return showIndex;
	}
	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}
	public FriendLinkDO getFriendLinkDO() {
		return friendLinkDO;
	}
	public void setFriendLinkDO(FriendLinkDO friendLinkDO) {
		this.friendLinkDO = friendLinkDO;
	}
	public PageDto getPageDto() {
		return pageDto;
	}
	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkCategoryName() {
		return linkCategoryName;
	}
	public void setLinkCategoryName(String linkCategoryName) {
		this.linkCategoryName = linkCategoryName;
	}

}

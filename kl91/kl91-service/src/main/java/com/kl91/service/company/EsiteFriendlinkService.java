package com.kl91.service.company;

import java.util.List;

import com.kl91.domain.company.EsiteFriendlink;
import com.kl91.domain.dto.company.EsiteFriendlinkSearchDto;

public interface EsiteFriendlinkService {

	/**
	 * 添加友情链接 使用于生意管家的友情链接管理页面。 目前最多只能添加5条友情链接记录
	 * 
	 * 
	 */
	public Integer createFriendlink(EsiteFriendlink esiteFriendlink);

	/**
	 * 修改友情链接 客户修改已经存在的链接信息。
	 */
	public Integer editFriendlink(EsiteFriendlink esiteFriendlink);

	/**
	 * 删除友情链接 用户在生意管家中删除友情链接
	 * 
	 * 
	 */
	public Integer deleteById(Integer id);

	/**
	 * 根据链接ID搜索链接详细信息 使用于修改链接和查看
	 */
	public EsiteFriendlink queryById(Integer id);

	/**
	 * 搜索友情链接列表 客户生意管家中,浏览自己添加的友情链接
	 */
	public List<EsiteFriendlink> queryFriendlink(EsiteFriendlinkSearchDto searchDto);
}

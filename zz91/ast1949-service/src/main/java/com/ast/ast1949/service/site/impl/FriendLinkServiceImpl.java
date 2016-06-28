/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-1
 */
package com.ast.ast1949.service.site.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.site.FriendLinkDO;
import com.ast.ast1949.dto.site.FriendLinkDTO;
import com.ast.ast1949.persist.site.FriendLinkDAO;
import com.ast.ast1949.service.site.FriendLinkService;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("FriendLinkService")
public class FriendLinkServiceImpl implements FriendLinkService{

	@Autowired
	FriendLinkDAO friendLinkDAO;
	public int getFriendLinkRecordCountByCondition(FriendLinkDTO friendLinkDTO) {
	    Assert.notNull(friendLinkDTO, "friendLinkDTO is not null");
		return friendLinkDAO.getFriendLinkRecordCountByCondition(friendLinkDTO);
	}

	public List<FriendLinkDTO> queryFriendLinkByCondition(
			FriendLinkDTO friendLinkDTO) {
		Assert.notNull(friendLinkDTO, "friendLinkDTO is not null");
		return friendLinkDAO.queryFriendLinkByCondition(friendLinkDTO);
	}

	public FriendLinkDO queryFriendLinkById(Integer id) {
		Assert.notNull(id, "id is not null");
		return friendLinkDAO.queryFriendLinkById(id);
	}

	public int batchDeleteFriendLinkById(int[] entities) {

		return friendLinkDAO.batchDeleteFriendLinkById(entities);
	}

	public int insertFriendLink(FriendLinkDO friendLinkDO) {
		Assert.notNull(friendLinkDO, "friendLinkDO is not null");
		return friendLinkDAO.insertFriendLink(friendLinkDO);
	}

	public int updateFriendLink(FriendLinkDO friendLinkDO) {
		Assert.notNull(friendLinkDO, "friendLinkDO is not null");
		return friendLinkDAO.updateFriendLink(friendLinkDO);
	}

	public int batchCancelCheckedFriendLinkById(int[] entities) {
		return friendLinkDAO.batchCancelCheckedFriendLinkById(entities);
	}

	public int batchCheckedFriendLinkById(int[] entities) {
		return friendLinkDAO.batchCheckedFriendLinkById(entities);
	}

}

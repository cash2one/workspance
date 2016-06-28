/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-18
 */
package com.ast.ast1949.service.company.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.EsiteFriendLinkDo;
import com.ast.ast1949.persist.company.EsiteFriendLinkDao;
import com.ast.ast1949.service.company.EsiteFriendLinkService;
import com.ast.ast1949.util.Assert;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-2-18
 */
@Component("esiteFriendLinkService")
public class EsiteFriendLinkServiceImpl implements EsiteFriendLinkService {
	
	@Autowired
	EsiteFriendLinkDao esiteFriendLinkDao;
	
	final static int MAX_FRIEND_LINK_NUM = 1000;

	@Override
	public Integer deleteFriendLinkByIdAndCompany(Integer id, Integer companyId) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(companyId, "the companyId can not be null");
		return esiteFriendLinkDao.deleteFriendLinkByCompany(id, companyId);
	}

	@Override
	public Integer insertFriendLink(EsiteFriendLinkDo friendLink) {
		Assert.notNull(friendLink, "the object friendLink can not be null");
		return esiteFriendLinkDao.insertFriendLink(friendLink);
	}

	@Override
	public boolean isFriendLinkNumOverLimit(Integer companyId) {
		Integer num = esiteFriendLinkDao.countFriendLinkNumByCompany(companyId);
		if(num!=null && num.intValue()<MAX_FRIEND_LINK_NUM){
			return true;
		}
		return false;
	}

	@Override
	public Integer updateFriendLinkById(EsiteFriendLinkDo friendLink) {
		Assert.notNull(friendLink, "the object friendLink can not be null");
		return esiteFriendLinkDao.updateFriendLinkByCompany(friendLink);
	}

	@Override
	public List<EsiteFriendLinkDo> queryFriendLinkByCompany(Integer companyId,
			Integer limit) {
		Assert.notNull(companyId, "the companyId can not be null");
		if(limit==null){
			limit=MAX_FRIEND_LINK_NUM;
		}
		return esiteFriendLinkDao.queryFriendLinkByCompany(companyId, limit);
	}

	@Override
	public EsiteFriendLinkDo queryOneFriendLinkById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return esiteFriendLinkDao.queryOneFriendLinkById(id);
	}

}

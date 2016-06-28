package com.kl91.service.company.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kl91.domain.company.EsiteFriendlink;
import com.kl91.domain.dto.company.EsiteFriendlinkSearchDto;
import com.kl91.persist.company.EsiteFriendlinkDao;
import com.kl91.service.company.EsiteFriendlinkService;

@Component("esiteFriendlinkService")
public class EsiteFriendlinkServiceImpl implements EsiteFriendlinkService{
	
	@Resource
	private EsiteFriendlinkDao esiteFriendlinkDao;

	@Override
	public Integer createFriendlink(EsiteFriendlink esiteFriendlink) {
		return esiteFriendlinkDao.insert(esiteFriendlink);
	}

	@Override
	public Integer deleteById(Integer id) {
		return esiteFriendlinkDao.deleteById(id);
	}

	@Override
	public Integer editFriendlink(EsiteFriendlink esiteFriendlink) {
		return esiteFriendlinkDao.update(esiteFriendlink);
	}

	@Override
	public EsiteFriendlink queryById(Integer id) {
		return esiteFriendlinkDao.queryById(id);
	}

	@Override
	public List<EsiteFriendlink> queryFriendlink(
			EsiteFriendlinkSearchDto searchDto) {
		
		return esiteFriendlinkDao.queryFriendlink(searchDto);
	}

}

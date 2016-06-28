package com.ast.ast1949.service.spot.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.spot.SpotInfo;
import com.ast.ast1949.persist.spot.SpotInfoDao;
import com.ast.ast1949.service.spot.SpotInfoService;
import com.zz91.util.Assert;

@Component("spotInfoService")
public class SpotInfoServiceImpl implements SpotInfoService {

	@Resource
	private SpotInfoDao spotInfoDao;

	@Override
	public Integer addOrEditOneSpotInfo(SpotInfo spotInfo) {
		
		SpotInfo obj = spotInfoDao.queryOne(spotInfo.getSpotId());
		Integer i = 0;
		if (obj != null) {
			spotInfo.setId(obj.getId());
			i = spotInfoDao.update(spotInfo);
		} else {
			i = spotInfoDao.insert(spotInfo);
		}
		return i;
	}

	@Override
	public SpotInfo queryOneSpotInfo(Integer spotId) {
		Assert.notNull(spotId, "spot id must not be null");
		return spotInfoDao.queryOne(spotId);
	}

}

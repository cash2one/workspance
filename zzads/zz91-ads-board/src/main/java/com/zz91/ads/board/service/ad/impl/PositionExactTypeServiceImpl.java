/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.service.ad.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.ad.PositionExactTypeDao;
import com.zz91.ads.board.domain.ad.PositionExactType;
import com.zz91.ads.board.service.ad.PositionExactTypeService;
import com.zz91.util.Assert;

/**
 * 广告位-精确条件接口
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("positionExactTypeService")
public class PositionExactTypeServiceImpl implements PositionExactTypeService {

	@Resource
	private PositionExactTypeDao positionExactTypeDao;

	@Override
	public Integer deletePositionExactTypeByExactTypeId(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return positionExactTypeDao.deletePositionExactTypeByExactTypeId(id);
	}

	@Override
	public Integer deletePositionExactTypeByPositionId(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return positionExactTypeDao.deletePositionExactTypeByPositionId(id);
	}

	@Override
	public Integer deletePositionExactTypeByPositionIdAndExactTypeId(Integer adPositionId,
			Integer exactTypeId) {
		Assert.notNull(adPositionId, "the adPositionId must not be null");
		Assert.notNull(exactTypeId, "the exactTypeId must not be null");

		return positionExactTypeDao.deletePositionExactTypeByPositionIdAndExactTypeId(adPositionId, exactTypeId);
	}

	@Override
	public Boolean savePositionExactType(PositionExactType positionExactAds) {
		Assert.notNull(positionExactAds, "the object of positionExactAds must not be null");
		Assert.notNull(positionExactAds.getAdPositionId(), "the adPositionId must not be null");
		Assert.notNull(positionExactAds.getExactTypeId(), "the exactTypeId must not be null");

		Boolean result = false;
		if (positionExactTypeDao.conut(positionExactAds.getAdPositionId(), positionExactAds.getExactTypeId())
				.intValue() > 0) {
			result = true;
		} else {
			positionExactTypeDao.insertPositionExactType(positionExactAds);
			result = true;
		}
		return result;
	}

}

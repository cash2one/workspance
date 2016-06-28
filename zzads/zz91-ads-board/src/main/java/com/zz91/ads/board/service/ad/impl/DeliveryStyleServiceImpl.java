/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-15.
 */
package com.zz91.ads.board.service.ad.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.ad.DeliveryStyleDao;
import com.zz91.ads.board.domain.ad.DeliveryStyle;
import com.zz91.ads.board.service.ad.DeliveryStyleService;
import com.zz91.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
@Component("deliveryStyleService")
public class DeliveryStyleServiceImpl implements DeliveryStyleService {

	@Resource
	private DeliveryStyleDao deliveryStyleDao;
	
	@Override
	public List<DeliveryStyle> queryDeliveryStyle() {
		return deliveryStyleDao.queryDeliveryStyle();
	}

	@Override
	public Integer createStyle(DeliveryStyle style) {
		Assert.notNull(style.getName(),"the name can not be null");
		return deliveryStyleDao.insertStyle(style);
	}

	@Override
	public Integer deleteStyle(Integer id) {
		//TODO
		
		return deliveryStyleDao.deleteStyle(id);
	}

	@Override
	public Integer updateStyle(DeliveryStyle style) {
		Assert.notNull(style, "the style can not be null");
		return deliveryStyleDao.updateStyle(style);
	}
}

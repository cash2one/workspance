package com.ast.ast1949.service.price.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.price.PriceTemplate;
import com.ast.ast1949.persist.price.PriceTemplateDao;
import com.ast.ast1949.service.price.PriceTemplateService;
import com.zz91.util.Assert;

/**
 * author:kongsj date:2013-5-22
 */
@Component("priceTemplateService")
public class PriceTemplateServiceImpl implements PriceTemplateService {

	@Resource
	private PriceTemplateDao priceTemplateDao;
	
	@Override
	public Integer insert(Integer priceId) {
		do {
			if (priceId == null) {
				break;
			}
			PriceTemplate priceTemplate  = priceTemplateDao.queryByPriceId(priceId);
			if(priceTemplate!=null){
				break;
			}
			priceTemplate = new PriceTemplate();
			priceTemplate.setPriceId(priceId);
			return priceTemplateDao.insert(priceTemplate);
		} while (false);
		return 0;
	}

	@Override
	public PriceTemplate queryById(Integer id) {
		Assert.notNull(id, "id must not be null");
		return priceTemplateDao.queryById(id);
	}

	@Override
	public PriceTemplate queryByPriceId(Integer priceId) {
		Assert.notNull(priceId, "priceId must not be null");
		return priceTemplateDao.queryByPriceId(priceId);
	}

}

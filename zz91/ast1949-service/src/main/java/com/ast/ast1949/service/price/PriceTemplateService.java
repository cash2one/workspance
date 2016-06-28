package com.ast.ast1949.service.price;

import com.ast.ast1949.domain.price.PriceTemplate;

/**
 *	author:kongsj
 *	date:2013-5-22
 */
public interface PriceTemplateService {
	public PriceTemplate queryByPriceId(Integer priceId);

	public PriceTemplate queryById(Integer id);

	public Integer insert(Integer priceId);
}

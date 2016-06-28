package com.ast.ast1949.persist.price;

import com.ast.ast1949.domain.price.PriceTemplate;

/**
 * author:kongsj date:2013-5-22
 */
public interface PriceTemplateDao {
	public PriceTemplate queryByPriceId(Integer priceId);

	public PriceTemplate queryById(Integer id);

	public Integer insert(PriceTemplate priceTemplate);
}

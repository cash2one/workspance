package com.ast.ast1949.persist.price.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.price.PriceTemplate;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.price.PriceTemplateDao;

/**
 *	author:kongsj
 *	date:2013-5-22
 */
@Component("priceTemplateDao")
public class PriceTemplateDaoImpl  extends BaseDaoSupport implements PriceTemplateDao{

	final static String SQL_FIX = "priceTemplate";
	@Override
	public Integer insert(PriceTemplate priceTemplate) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"),priceTemplate);
	}

	@Override
	public PriceTemplate queryById(Integer id) {
		return (PriceTemplate) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@Override
	public PriceTemplate queryByPriceId(Integer priceId) {
		return (PriceTemplate) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByPriceId"), priceId);
	}
}

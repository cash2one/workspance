package com.ast.ast1949.persist.market.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.market.ProductMarket;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.market.ProductMarketDao;

@Component("productMarketDao")
public class ProductMarketDaoImpl extends BaseDaoSupport implements ProductMarketDao {
	final static String SQL_PREFIX="productMarket";

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryProductMarketBySize(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryProductBySize"), size);
	}

	@Override
	public Integer countProducts() {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countProducts"));
	}

	@Override
	public ProductMarket queryProductMarketByProductId(Integer productId) {
		return (ProductMarket) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryProductMarketByProductId"),productId);
	}

}

package com.ast.ast1949.persist.products.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductsZstExpiredDAO;

@Component("productsZstExpiredDAO")
public class ProductsZstExpiredDAOImpl extends BaseDaoSupport implements ProductsZstExpiredDAO{

	final static String SQL_PREFIX = "productsZstExpired";
	@Override
	public Integer deleteByProductId(Integer productId) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteByProductId"), productId);
	}

}

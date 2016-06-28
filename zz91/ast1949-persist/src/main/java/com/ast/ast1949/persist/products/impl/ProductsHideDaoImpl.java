package com.ast.ast1949.persist.products.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsHide;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductsHideDao;
@Component("productsHideDao")
public class ProductsHideDaoImpl extends BaseDaoSupport implements ProductsHideDao{
	private static String sqlPreFix = "productsHide";
	@Override
	public Integer insert(ProductsHide productsHide) {
		
		return (Integer)getSqlMapClientTemplate().insert(addSqlKeyPreFix(sqlPreFix, "insert"), productsHide);
	}

	@Override
	public ProductsHide queryByProductId(Integer productId) {
		
		return (ProductsHide) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryByProductId"), productId);
	}
	
	@Override
	public Integer delete(Integer productId) {
		
		return (Integer)getSqlMapClientTemplate().delete(addSqlKeyPreFix(sqlPreFix, "delete"), productId);
	}
	
	@Override
	public Integer countByCompanyId(Integer companyId){
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "countByCompanyId"), companyId);
	}

}

/**
 * 
 */
package com.ast.ast1949.persist.dataindex.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.dataindex.ProductsIndex;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.dataindex.ProductsIndexDao;

/**
 * @author root
 *
 */
@Component("productsIndexDao")
public class ProductsIndexDaoImpl extends BaseDaoSupport implements ProductsIndexDao {

	final static String SQL_PREFIX="productsIndex";
	
	@Override
	public Integer insertIndex(ProductsIndex index) {
		
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertIndex"), index);
	}

	@Override
	public Integer deleteById(Integer id) {
		
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsIndex> queryIndex(String categoryCode,
			PageDto<ProductsIndex> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("page", page);
		root.put("dataIndexCode", categoryCode);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryIndex"), root);
	}

	@Override
	public Integer queryIndexCount(String categoryCode) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("dataIndexCode", categoryCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryIndexCount"), root);
	}

	@Override
	public Integer updateIndex(ProductsIndex index) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateIndex"), index);
	}

	@Override
	public Integer updateOrderBy(Integer id, Float orderby) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("orderby", orderby);
		
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateOrderBy"), root);
	}

}

package com.ast.ast1949.persist.sample.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.SampleRelateProduct;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.SampleRelateProductDao;

@Component("sampleRelateProductDao")
public class SampleRelateProductDaoImpl extends BaseDaoSupport implements SampleRelateProductDao{

	final static String SQL_FIX = "sampleRelateProduct";
	
	@Override
	public Integer insert(SampleRelateProduct sampleRelateProduct) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), sampleRelateProduct);
	}

	@Override
	public Integer queryByProductIdForSampleId(Integer productId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByProductIdForSampleId"), productId);
	}

	@Override
	public Integer queryBySampleIdForProductId(Integer sampleId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryBySampleIdForProductId"), sampleId);
	}

	@Override
	public Integer buildRelateByProductIdAndSampleId(Integer sampleId,Integer productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sampleId", sampleId);
		map.put("productId", productId);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "buildRelateByProductIdAndSampleId"), map);
	}

	@Override
	public Integer countAddByDate(String from, String to) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countAddByDate"), map);
	}

}

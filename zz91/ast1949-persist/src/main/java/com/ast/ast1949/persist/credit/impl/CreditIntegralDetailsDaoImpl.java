/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-27
 */
package com.ast.ast1949.persist.credit.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.credit.CreditIntegralDetailsDo;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.credit.CreditIntegralDetailsDao;

/**
 * @author yuyonghui
 * 
 */
@Component("creditIntegralDetailsDao")
public class CreditIntegralDetailsDaoImpl extends BaseDaoSupport implements
		CreditIntegralDetailsDao {

	final static String SQL_PREFIX = "creditIntegralDetails";

	@Override
	public int countIntegralByCompany(Integer companyId) {
		Integer i = (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countIntegralByCompany"),
				companyId);
		if (i != null) {
			return i.intValue();
		}
		return 0;
	}

	@Override
	public Integer countIntegralByOperationKey(Integer companyId,
			String operationKey) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("operationKey", operationKey);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countIntegralByOperationKey"),
				root);
	}

	@Override
	public Integer deleteIntegralByOperation(String operationKey,
			Integer relatedId, Integer companyId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("operationKey", operationKey);
		root.put("relatedId", relatedId);
		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PREFIX, "deleteIntegralByOperation"), root);
	}

	@Override
	public Integer insertIntegral(CreditIntegralDetailsDo detail) {

		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertIntegral"), detail);
	}

	@Override
	public Integer updateIntegral(CreditIntegralDetailsDo detail) {

		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateIntegralByOperation"), detail);
	}

	// public Integer insertCreditIntegralDetailsDO(CreditIntegralDetailsDo
	// creditIntegralDetailsDO) {
	// Assert.notNull(creditIntegralDetailsDO,
	// "creditIntegralDetailsDO is not null");
	// return
	// Integer.valueOf(getSqlMapClientTemplate().insert("creditIntegralDetails.insertCreditIntegralDetailsDO",
	// creditIntegralDetailsDO).toString());
	// }
	//
	// public CreditIntegralDetailsDo queryCreditIntegralByOperatorKey(Integer
	// companyId,
	// String opertorKey) {
	// Assert.notNull(companyId, "companyId is not null");
	// Assert.notNull(opertorKey, "opertorKey is not null");
	//	
	// Map<String, Object> map=new HashMap<String, Object>();
	// map.put("companyId", companyId);
	// map.put("opertorKey", opertorKey);
	// //return (CreditIntegralDetailsDO)
	// getSqlMapClientTemplate().queryForObject("creditIntegralDetails.queryCreditIntegralByOperatorKey",
	// map);
	// return
	// (CreditIntegralDetailsDo)getSqlMapClientTemplate().queryForObject("creditIntegralDetails.queryByComanyIDAndOperatorKey",map);
	// }
	//
	// public CreditIntegralDetailsDo queryByComanyIDAndOperatorKey(Integer
	// companyId,String operatorKey){
	// Assert.notNull(companyId, "companyId");
	// Assert.notNull(operatorKey, "operatorKey");
	// Map<String, Object> map = new HashMap<String,Object>();
	// map.put("companyId", companyId);
	// map.put("operatorKey", operatorKey);
	// return (CreditIntegralDetailsDo)
	// getSqlMapClientTemplate().queryForObject("creditIntegralDetails.queryByComanyIDAndOperatorKey",map);
	// }

}

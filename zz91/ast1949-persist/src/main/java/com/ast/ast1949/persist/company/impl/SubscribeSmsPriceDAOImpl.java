package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.SubscribeSmsPriceDO;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.SubscribeSmsPriceDAO;

@Component("subscribeSmsPriceDAO")
public class SubscribeSmsPriceDAOImpl extends BaseDaoSupport implements SubscribeSmsPriceDAO{
	
	final static String SQL_PREFIX = "subscribeSmsPrice";
	
	@Override
	public Integer addSubscribeSMS(Integer companyId, String categoryCode,
			Integer areaNodeId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("categoryCode", categoryCode);
		root.put("areaNodeId", areaNodeId);
		return (Integer)getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "addSubscribeSMS"),root);
	}

	@Override
	public Integer deleteSubscribeSMS(String categoryCode, Integer companyId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("categoryCode", categoryCode);
		root.put("companyId", companyId);
		return (Integer)getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteSubscribeSMS"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubscribeSmsPriceDO> querySubscribeSMS(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "querySubscribeSMS"),companyId);
	}

	@Override
	public Integer countSubscribeSmsByCategoryCode(String categoryCode, Integer companyId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("categoryCode", categoryCode);
		root.put("companyId", companyId);
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countSubscribeSmsByCategoryCode"),root);
	}

	@Override
	public String selectSubscribeSmsForList(Integer companyId) {
		return (String)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectSubscribeSmsForList"),companyId);
	}

	@Override
	public Integer countSubscribeSms(Integer companyId) {
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countSubscribeSms"),companyId);
	}

	@Override
	public Integer deleteSubscribeSMSPrice(Integer id, Integer companyId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("companyId", companyId);
		return (Integer)getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteSubscribeSMSPrice"),root);
	}

	@Override
	public Integer deleteSubscribeSMSByArea(String categoryCode,
			Integer areaNodeId, Integer companyId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("categoryCode", categoryCode);
		root.put("areaNodeId", areaNodeId);
		root.put("companyId", companyId);
		return (Integer)getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteSubscribeSMSByArea"),root);
	}

}

package com.zz91.ep.dao.comp.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.comp.CompanyIndustryChainDao;


@Component("companyIndustryChainDao")
public class CompanyIndustryChainDaoImpl extends BaseDao implements CompanyIndustryChainDao {
	
	final static String SQL_PREFIX="companyIndustryChain";
	
	@Override
	public Integer insertIndustryChain(Integer industryChainId, Integer cid) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("industryChainId", industryChainId);
		map.put("cid", cid);
		return (Integer)getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertIndustryChain"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryIndustryChainIdByCid(Integer cid) {
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryIndustryChainIdByCid"),cid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryIdByCid(Integer cid) {
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryIdByCid"),cid);
	}

	@Override
	public Integer updateCompanyIndustryChain(Integer id, Integer chainId,Short delStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("chainId", chainId);
		map.put("delStatus", delStatus);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCompanyIndustryChain") ,map);
	}

}

/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-11-15 下午01:37:41
 */
package com.zz91.ep.admin.dao.trade.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.CompanyIndustryChainDao;
import com.zz91.ep.domain.comp.CompanyIndustryChain;

@Component("companyIndustryChainDao")
public class CompanyIndustryChainDaoImpl extends BaseDao implements CompanyIndustryChainDao {
	
	final static String SQL_PREFIX = "companyIndustryChain";

	@Override
	public Integer insertCompIndustryChain(CompanyIndustryChain chain) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertCompIndustryChain"), chain);
	}

	@Override
	public Integer updateCompIndustryChain(CompanyIndustryChain chain) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCompIndustryChain"), chain);
	}

	@Override
	public Integer updateDelStatusByCidAndChainId(Integer cid, Integer chainId,Short delStatus) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("chainId", chainId);
		root.put("delStatus", delStatus);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDelStatusByCidAndChainId"), root);
	}

	@Override
	public Integer queryCountByCidAndChainId(Integer cid, Integer chainId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("chainId", chainId);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountByCidAndChainId"), root);
	}

}

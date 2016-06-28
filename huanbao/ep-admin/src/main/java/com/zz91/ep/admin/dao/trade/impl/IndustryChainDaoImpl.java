/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-11-9 下午01:30:11
 */
package com.zz91.ep.admin.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.IndustryChainDao;
import com.zz91.ep.domain.comp.IndustryChain;
import com.zz91.ep.dto.PageDto;

@Component("industryChainDao")
public class IndustryChainDaoImpl extends BaseDao implements IndustryChainDao {
	
	final static String SQL_PREFIX = "industryChain";

	@Override
	public Integer updateDelStatusById(Integer id,Short status) {
		Map<String,Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("status", status);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDelStatusById"), root);
	}

	@Override
	public Integer insertIndustryChain(IndustryChain chain) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertIndustryChain"), chain);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IndustryChain> queryIndustryChains(String areaCode, PageDto<IndustryChain> page,Integer cid) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("areaCode", areaCode);
		root.put("page", page);
		root.put("cid", cid);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryIndustryChains"), root);
	}
	
	@Override
	public Integer queryIndustryChainsCount(String areaCode,Integer cid) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("areaCode", areaCode);
		root.put("cid", cid);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryIndustryChainsCount"), root);
	}

	@Override
	public Integer updateIndustryChain(IndustryChain chain) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateIndustryChain"), chain);
	}

	@Override
	public Integer updateShowIndexById(Integer id,Short showIndex) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("showIndex", showIndex);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateShowIndexById"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IndustryChain> querySimpChain() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySimpChain"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IndustryChain> querySimpChainByAreaCode(String areaCode) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySimpChainByAreaCode"), areaCode);
	}

}

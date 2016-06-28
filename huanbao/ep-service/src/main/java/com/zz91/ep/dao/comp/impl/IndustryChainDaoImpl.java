package com.zz91.ep.dao.comp.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.comp.IndustryChainDao;
import com.zz91.ep.domain.comp.IndustryChain;

@Component("industryChainDao")
public class IndustryChainDaoImpl extends BaseDao implements IndustryChainDao {
	
	final static String SQL_PREFIX="industryChain";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IndustryChain> queryIndustryChains(Integer size, String areaCode) {
		
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("size", size);
		map.put("areaCode",areaCode);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryIndustryChains"), map);
	}

	@Override
	public IndustryChain queryIndustryChainById(Integer id) {
		
		return (IndustryChain)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryIndustryChainById"),id);
	}



}

package com.ast.ast1949.persist.sample.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.WeixinLookcontactlog;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.WeixinLookcontactlogDao;

@Component("weixinLookcontactlogDao")
public class WeixinLookcontactlogDaoImpl extends BaseDaoSupport implements WeixinLookcontactlogDao {

	@Override
	public Integer countClick(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject("weixin_lookcontactlog.countClick", account);
	}

	@Override
	public WeixinLookcontactlog queryById(String account, Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("companyId", companyId);
		return (WeixinLookcontactlog) getSqlMapClientTemplate().queryForObject("weixin_lookcontactlog.queryById", map);
	}
}
package com.kl91.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kl91.domain.company.EsiteFriendlink;
import com.kl91.domain.dto.company.EsiteFriendlinkSearchDto;
import com.kl91.persist.BaseDaoSupport;
import com.kl91.persist.company.EsiteFriendlinkDao;

@Component("esiteFriendlinkDao")
public class EsiteFriendlinkDaoImpl extends BaseDaoSupport implements EsiteFriendlinkDao{
	
	private static String SQL_PREFIX = "esiteFriendlink";

	@Override
	public Integer deleteById(Integer id) {
		
		return (Integer) getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "delete"),id);
	}

	@Override
	public Integer insert(EsiteFriendlink esiteFriendlink) {
		
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insert"),esiteFriendlink);
	}

	@Override
	public EsiteFriendlink queryById(Integer id) {
		
		return (EsiteFriendlink) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryById"),id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EsiteFriendlink> queryFriendlink(
			EsiteFriendlinkSearchDto searchDto) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("searchDto", searchDto);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryFriendlink"),root);
	}

	@Override
	public Integer update(EsiteFriendlink esiteFriendlink) {
		
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "update"),esiteFriendlink);
	}

}

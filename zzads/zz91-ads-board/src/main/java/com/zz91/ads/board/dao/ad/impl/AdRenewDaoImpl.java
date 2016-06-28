/**
 * @author shiqp
 * @date 2015-07-08
 */
package com.zz91.ads.board.dao.ad.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.BaseDaoSupport;
import com.zz91.ads.board.dao.ad.AdRenewDao;
import com.zz91.ads.board.domain.ad.AdRenew;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdDto;
import com.zz91.ads.board.dto.ad.AdSearchDto;

@Component("adRenewDao")
public class AdRenewDaoImpl extends BaseDaoSupport implements AdRenewDao {
	final static String SQL_PREFIX = "adRenew";

	@Override
	public Integer createRenew(AdRenew adRenew) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "createRenew"), adRenew);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdRenew> queryRenewByAdId(Pager<AdRenew> page, Integer adId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("adId", adId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryRenewByAdId"), map);
	}

	@Override
	public Integer countRenewByAdId(Integer adId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countRenewByAdId"), adId);
	}

	@Override
	public Integer updateRenew(AdRenew adRenew) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateRenew"), adRenew);
	}

	@Override
	public AdRenew queryRenewById(Integer id) {
		return (AdRenew) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryRenewById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdRenew> queryRenewByCondition(AdSearchDto adSearch, Pager<AdDto> page) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("search", adSearch);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryRenewByCondition"), map);
	}

	@Override
	public Integer countRenewByCondition(AdSearchDto adSearch) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("search", adSearch);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countRenewByCondition"), map);
	}

}

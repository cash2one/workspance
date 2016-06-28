/**
 * @author shiqp 日期:2014-11-24
 */
package com.ast.ast1949.persist.bbs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostTrends;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.bbs.BbsPostTrendsDao;
@Component("bbsPostTrendsDao")
public class BbsPostTrendsDaoImpl extends BaseDaoSupport implements BbsPostTrendsDao {
	
	final static String SQL_PREFIX = "bbsPostTrends";

	@Override
	public Integer insertBbsPostTrends(BbsPostTrends trends) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertBbsPostTrends"), trends);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostTrends> queryTrendsByCompanyId(Integer companyId, Integer size) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("companyId", companyId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryTrendsByCompanyId"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostTrends> queryListTrendsByCompanyId(Integer companyId, PageDto<BbsPostDO> page) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryListTrendsByCompanyId"), map);
	}

	@Override
	public Integer countListTrendsByCompanyId(Integer companyId, PageDto<BbsPostDO> page) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("page", page);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countListTrendsByCompanyId"), map);
	}

}

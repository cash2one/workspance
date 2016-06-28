package com.ast.ast1949.persist.sample.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.WeixinScore;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.WeixinScoreDao;

@Component("weixinScoreDao")
public class WeixinScoreDaoImpl extends BaseDaoSupport  implements WeixinScoreDao {

	public int  insert(WeixinScore record) {
		return (Integer)getSqlMapClientTemplate().insert("weixin_score.insert", record);
	}

	public int updateByPrimaryKey(WeixinScore record) {
		int rows = getSqlMapClientTemplate().update("weixin_score.updateByPrimaryKey", record);
		return rows;
	}

	public int updateByPrimaryKeySelective(WeixinScore record) {
		int rows = getSqlMapClientTemplate().update("weixin_score.updateByPrimaryKeySelective", record);
		return rows;
	}

	public WeixinScore selectByPrimaryKey(Integer id) {
		WeixinScore key = new WeixinScore();
		key.setId(id);
		WeixinScore record = (WeixinScore) getSqlMapClientTemplate().queryForObject("weixin_score.selectByPrimaryKey", key);
		return record;
	}

	public int deleteByPrimaryKey(Integer id) {
		WeixinScore key = new WeixinScore();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete("weixin_score.deleteByPrimaryKey", key);
		return rows;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WeixinScore> queryListByFilter(Map<String, Object> filterMap) {
		return getSqlMapClientTemplate().queryForList("weixin_score.queryListByFilter", filterMap);
	}

	@Override
	public Integer queryListByFilterCount(Map<String, Object> filterMap) {
		return (Integer) getSqlMapClientTemplate().queryForObject("weixin_score.queryListByFilterCount", filterMap);
	}

	@Override
	public Integer totalAvailableScore(String account) {
		Integer value = (Integer) getSqlMapClientTemplate().queryForObject("weixin_score.totalAvailableScore", account);
		if (value == null)
			value = 0;
		return value;
	}
}
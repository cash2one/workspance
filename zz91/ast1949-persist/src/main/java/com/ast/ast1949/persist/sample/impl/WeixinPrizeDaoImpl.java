package com.ast.ast1949.persist.sample.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.WeixinPrize;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.WeixinPrizeDao;

@Component("weixinPrizeDao")
public class WeixinPrizeDaoImpl extends BaseDaoSupport implements WeixinPrizeDao {

	public int insert(WeixinPrize record) {
		return (Integer)getSqlMapClientTemplate().insert("weixin_prize.insert", record);
	}

	public int updateByPrimaryKey(WeixinPrize record) {
		int rows = getSqlMapClientTemplate().update("weixin_prize.updateByPrimaryKey", record);
		return rows;
	}

	public int updateByPrimaryKeySelective(WeixinPrize record) {
		int rows = getSqlMapClientTemplate().update("weixin_prize.updateByPrimaryKeySelective", record);
		return rows;
	}

	public WeixinPrize selectByPrimaryKey(Integer id) {
		WeixinPrize key = new WeixinPrize();
		key.setId(id);
		WeixinPrize record = (WeixinPrize) getSqlMapClientTemplate().queryForObject("weixin_prize.selectByPrimaryKey", key);
		return record;
	}

	public int deleteByPrimaryKey(Integer id) {
		WeixinPrize key = new WeixinPrize();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete("weixin_prize.deleteByPrimaryKey", key);
		return rows;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeixinPrize> queryListByFilter(Map<String, Object> filterMap) {
		return getSqlMapClientTemplate().queryForList("weixin_prize.queryListByFilter", filterMap);
	}

	@Override
	public Integer queryListByFilterCount(Map<String, Object> filterMap) {
		return (Integer) getSqlMapClientTemplate().queryForObject("weixin_prize.queryListByFilterCount", filterMap);
	}
}
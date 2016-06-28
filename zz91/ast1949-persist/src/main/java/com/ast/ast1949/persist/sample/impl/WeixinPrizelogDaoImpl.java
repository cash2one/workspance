package com.ast.ast1949.persist.sample.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.WeixinPrizelog;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.WeixinPrizelogDao;

@Component("weixinPrizelogDao")
public class WeixinPrizelogDaoImpl extends BaseDaoSupport implements WeixinPrizelogDao {

	public int insert(WeixinPrizelog record) {
		return (Integer) getSqlMapClientTemplate().insert("weixin_prizelog.insert", record);
	}

	public int updateByPrimaryKey(WeixinPrizelog record) {
		int rows = getSqlMapClientTemplate().update("weixin_prizelog.updateByPrimaryKey", record);
		return rows;
	}

	public int updateByPrimaryKeySelective(WeixinPrizelog record) {
		int rows = getSqlMapClientTemplate().update("weixin_prizelog.updateByPrimaryKeySelective", record);
		return rows;
	}

	public WeixinPrizelog selectByPrimaryKey(Integer id) {
		WeixinPrizelog key = new WeixinPrizelog();
		key.setId(id);
		WeixinPrizelog record = (WeixinPrizelog) getSqlMapClientTemplate().queryForObject("weixin_prizelog.selectByPrimaryKey", key);
		return record;
	}

	public int deleteByPrimaryKey(Integer id) {
		WeixinPrizelog key = new WeixinPrizelog();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete("weixin_prizelog.deleteByPrimaryKey", key);
		return rows;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<WeixinPrizelog> queryListByFilter(Map<String, Object> filterMap) {
		return getSqlMapClientTemplate().queryForList("weixin_prizelog.queryListByFilter", filterMap);
	}

	@Override
	public Integer queryListByFilterCount(Map<String, Object> filterMap) {
		return (Integer) getSqlMapClientTemplate().queryForObject("weixin_prizelog.queryListByFilterCount", filterMap);
	}

	/**
	 * 已兑换的积分数
	 */
	@Override
	public Integer totalConvertScore(String account) {
		Integer value = (Integer) getSqlMapClientTemplate().queryForObject("weixin_prizelog.totalConvertScore", account);
		if (value == null)
			value = 0;
		return value;
	}
	
	/**
	 * 已兑换的指定奖品的数量
	 */
	@Override
	public Integer totalCountConvertScoreByPrizeid(String account, Integer prizeid) {
		WeixinPrizelog key = new WeixinPrizelog();
		key.setAccount(account);
		key.setPrizeid(prizeid);
		Integer num = (Integer) getSqlMapClientTemplate().queryForObject("weixin_prizelog.totalCountConvertScoreByPrizeid", key);
		if (num == null)
			num = 0;
		return num;
	}	
}
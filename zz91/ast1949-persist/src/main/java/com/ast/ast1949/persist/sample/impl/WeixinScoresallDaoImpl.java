package com.ast.ast1949.persist.sample.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.WeixinScoresall;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.WeixinScoresallDao;

@Component("weixinScoresallDao")
public class WeixinScoresallDaoImpl  extends BaseDaoSupport implements WeixinScoresallDao {

	public int  insert(WeixinScoresall record) {
		return (Integer)getSqlMapClientTemplate().insert("weixin_scoresall.insert", record);
	}

	public int updateByPrimaryKey(WeixinScoresall record) {
		int rows = getSqlMapClientTemplate().update("weixin_scoresall.updateByPrimaryKey", record);
		return rows;
	}

	public int updateByPrimaryKeySelective(WeixinScoresall record) {
		int rows = getSqlMapClientTemplate().update("weixin_scoresall.updateByPrimaryKeySelective", record);
		return rows;
	}

	public WeixinScoresall selectByPrimaryKey(Integer id) {
		WeixinScoresall key = new WeixinScoresall();
		key.setId(id);
		WeixinScoresall record = (WeixinScoresall) getSqlMapClientTemplate().queryForObject("weixin_scoresall.selectByPrimaryKey", key);
		return record;
	}

	public int deleteByPrimaryKey(Integer id) {
		WeixinScoresall key = new WeixinScoresall();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete("weixin_scoresall.deleteByPrimaryKey", key);
		return rows;
	}

	@Override
	public WeixinScoresall selectByAccount(String account) {
		WeixinScoresall record = (WeixinScoresall) getSqlMapClientTemplate().queryForObject("weixin_scoresall.selectByAccount", account);
		return record;
	}
}
package com.ast.ast1949.persist.sample;

import com.ast.ast1949.domain.sample.WeixinScoresall;

public interface WeixinScoresallDao {
	public int  insert(WeixinScoresall record);

	public int updateByPrimaryKey(WeixinScoresall record);

	public int updateByPrimaryKeySelective(WeixinScoresall record);

	public WeixinScoresall selectByPrimaryKey(Integer id);

	public int deleteByPrimaryKey(Integer id);

	/**
	 * 根据account查询客户积分
	 * @param account
	 * @return
	 */
	public WeixinScoresall selectByAccount(String account);
}
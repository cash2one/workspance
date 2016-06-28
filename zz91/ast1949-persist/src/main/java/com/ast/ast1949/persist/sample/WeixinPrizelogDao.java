package com.ast.ast1949.persist.sample;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.sample.WeixinPrizelog;

public interface WeixinPrizelogDao {
	public int  insert(WeixinPrizelog record);

	public int updateByPrimaryKey(WeixinPrizelog record);

	public int updateByPrimaryKeySelective(WeixinPrizelog record);

	public WeixinPrizelog selectByPrimaryKey(Integer id);

	public int deleteByPrimaryKey(Integer id);
	
	public List<WeixinPrizelog> queryListByFilter(Map<String, Object> filterMap);

	public Integer queryListByFilterCount(Map<String, Object> filterMap);	
	
	/**
	 * 已兑换的积分数
	 * @param companyId
	 * @return
	 */
	public Integer totalConvertScore(String  account);
	
	
	/**
	 * 已兑换的指定奖品的数量
	 * @param companyId
	 * @return
	 */
	public Integer totalCountConvertScoreByPrizeid(String  account,Integer prizeid);
}
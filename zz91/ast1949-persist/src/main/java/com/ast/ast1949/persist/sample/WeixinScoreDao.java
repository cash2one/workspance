package com.ast.ast1949.persist.sample;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.sample.WeixinScore;

public interface WeixinScoreDao {
	public int insert(WeixinScore record);

	public int updateByPrimaryKey(WeixinScore record);

	public int updateByPrimaryKeySelective(WeixinScore record);

	public WeixinScore selectByPrimaryKey(Integer id);

	public int deleteByPrimaryKey(Integer id);

	public List<WeixinScore> queryListByFilter(Map<String, Object> filterMap);

	public Integer queryListByFilterCount(Map<String, Object> filterMap);

	/**
	 * 可兑换的积分数
	 * 
	 * @param companyId
	 * @return
	 */
	public Integer totalAvailableScore(String account);
}
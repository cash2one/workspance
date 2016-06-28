package com.ast.ast1949.persist.sample;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.sample.WeixinPrize;

public interface WeixinPrizeDao {
	public int insert(WeixinPrize record);

	public int updateByPrimaryKey(WeixinPrize record);

	public int updateByPrimaryKeySelective(WeixinPrize record);

	public WeixinPrize selectByPrimaryKey(Integer id);

	public int deleteByPrimaryKey(Integer id);

	public List<WeixinPrize> queryListByFilter(Map<String, Object> filterMap);

	public Integer queryListByFilterCount(Map<String, Object> filterMap);
}
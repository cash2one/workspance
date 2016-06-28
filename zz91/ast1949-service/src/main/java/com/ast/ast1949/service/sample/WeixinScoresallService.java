package com.ast.ast1949.service.sample;

import com.ast.ast1949.domain.sample.WeixinScoresall;

public interface WeixinScoresallService {
	public int insert(WeixinScoresall weixinScoresall);

	public int updateByPrimaryKey(WeixinScoresall record);

	public int updateByPrimaryKeySelective(WeixinScoresall record);

	public WeixinScoresall selectByPrimaryKey(Integer id);

	public int deleteByPrimaryKey(Integer id);
}

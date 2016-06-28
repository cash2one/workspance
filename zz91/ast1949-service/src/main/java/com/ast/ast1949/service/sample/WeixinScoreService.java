package com.ast.ast1949.service.sample;

import java.util.Map;

import com.ast.ast1949.domain.sample.WeixinScore;
import com.ast.ast1949.dto.PageDto;

public interface WeixinScoreService {
	public int insert(WeixinScore weixinScore);

	public int updateByPrimaryKey(WeixinScore record);

	public int updateByPrimaryKeySelective(WeixinScore record);

	public WeixinScore selectByPrimaryKey(Integer id);

	public int deleteByPrimaryKey(Integer id);

	public PageDto<WeixinScore> queryListByFilter(PageDto<WeixinScore> page, Map<String, Object> map);

	public Integer totalAvailableScore(String  account);
}

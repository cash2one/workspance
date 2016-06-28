package com.ast.ast1949.service.sample;

import java.util.Map;

import com.ast.ast1949.domain.sample.WeixinPrizelog;
import com.ast.ast1949.dto.PageDto;

public interface WeixinPrizelogService {
	public int insert(WeixinPrizelog weixinPrizelog);

	public int updateByPrimaryKey(WeixinPrizelog record);

	public int updateByPrimaryKeySelective(WeixinPrizelog record);

	public WeixinPrizelog selectByPrimaryKey(Integer id);

	public int deleteByPrimaryKey(Integer id);

	public PageDto<WeixinPrizelog> queryListByFilter(PageDto<WeixinPrizelog> page, Map<String, Object> map);

	/**
	 * 已兑换的积分数
	 * @param companyId
	 * @return
	 */
	public Integer totalConvertScore(String  account);
}

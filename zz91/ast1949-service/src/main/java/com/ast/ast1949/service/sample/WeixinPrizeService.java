package com.ast.ast1949.service.sample;

import java.util.Map;

import com.ast.ast1949.domain.sample.WeixinPrize;
import com.ast.ast1949.dto.PageDto;

public interface WeixinPrizeService {
	public int insert(WeixinPrize weixinPrize);

	public int updateByPrimaryKey(WeixinPrize record);

	public int updateByPrimaryKeySelective(WeixinPrize record);

	public WeixinPrize selectByPrimaryKey(Integer id);

	public int deleteByPrimaryKey(Integer id);
	
	public PageDto<WeixinPrize> queryListByFilter(PageDto<WeixinPrize> page, Map<String, Object> map);

	
	/**
	 *   兑换申请
	 * @param id 奖品ID
	 * @param account 客户登录号码
	 * @param ischeck 是否需要审核  0：待审核  1：已审核通过
	 * @return -1： 奖品已兑换完   0：用户积分不足   1：兑奖申请成功
	 */
	public int  prizeApply(Integer id, String account,Integer ischeck);
}

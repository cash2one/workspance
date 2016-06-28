/**
 * @author shiqp
 * @date 2015-07-22
 */
package com.ast.ast1949.service.trust;

import com.ast.ast1949.domain.trust.TrustTrade;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;
import com.ast.ast1949.dto.trust.TrustTradeDto;

public interface TrustTradeService {
	/**
	 * 创建一条交易记录
	 * @param trustTrade
	 * @return
	 */
	public Integer createTrustTrade(TrustTrade trustTrade);
	/**
	 * 获取交易集合
	 * @param isDel
	 * @param page
	 * @param searchDto
	 * @return
	 */
	public PageDto<TrustTradeDto> queryTradeList(Integer isDel,PageDto<TrustTradeDto> page,TrustBuySearchDto searchDto);
	/**
	 * 更新交易记录的信息
	 * @param trustTrade
	 * @return
	 */
	public Integer updateTrustTradeInfo(TrustTrade trustTrade);
	/**
	 * 获取某求购的交易记录
	 * @param buyId
	 * @return
	 */
	public TrustTrade queryOneTrade(Integer buyId);
}

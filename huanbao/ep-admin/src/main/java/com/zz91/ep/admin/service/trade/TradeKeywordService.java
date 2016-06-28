/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-8-24 下午07:22:28
 */
package com.zz91.ep.admin.service.trade;

import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.dto.PageDto;

public interface TradeKeywordService {

	/**
	 * 创建一条标王信息
	 * 
	 * @param keyword
	 * @return
	 */
	public Integer createTradeKeyword(TradeKeyword keyword);

	/**
	 * 模块描述：更新标王信息
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-09-18　　　 怀欣冉　　　　　　　1.0.0　　　　　实现方法
	 */
	public Integer updateTradeKeywordById(TradeKeyword keyword);
	/**
	 * 模块描述：更该标王状态
	 * @param id 标王表编号
	 * @param status 标王暂停状态
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-09-18　　　 怀欣冉　　　　　　　1.0.0　　　　　实现方法
	 */
	public Integer updateStatusById(Integer id, Short status);

	/**
	 * @param keyword
	 * @param page
	 * @param status
	 * @param start
	 * @param end
	 */
	public PageDto<TradeKeyword> pageTradeKeyword(String keyword,
			PageDto<TradeKeyword> page, Short status, String start, String end);

	/**
	 * 模块描述 ：根据id查询标王信息
	 * @param id 标王表编号
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-09-18　　　 怀欣冉　　　　　　　1.0.0　　　　　实现方法
	 */
	public TradeKeyword queryTradekeywordById(Integer id);
}

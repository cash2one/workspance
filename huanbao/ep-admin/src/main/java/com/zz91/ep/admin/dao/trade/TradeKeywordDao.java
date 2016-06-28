/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-8-24 下午07:27:54
 */
package com.zz91.ep.admin.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.dto.PageDto;

public interface TradeKeywordDao {
	
	public final static short STATUS_Y=1;
	public final static short STATUS_N=0;
	

	public Integer insertTradeKeyword(TradeKeyword keyword);

	/**
	 * @param keyword 关键字
	 * @param page   分页对象
	 * @param status 是否暂停状态
	 * @param start  标王开始时间
	 * @param end    标王结束时间
	 */
	public List<TradeKeyword> queryTradeKeyword(String keyword, PageDto<TradeKeyword> page,
			Short status, String start, String end);

	/**
	 * @param keyword 关键字
	 * @param status  是否暂停状态
 	 * @param start   标王开始时间 
	 * @param end     标王结束时间
	 */
	public Integer queryTradeKeywordCount(String keyword, Short status,
			String start, String end);

	/** 功能(根据id更新status,更新最后更改时间)
	 * @param id 标王表编号
	 * @param status 标王暂停状态
	 */
	public Integer updateStatusById(Integer id, Short status);

	/**
	 * 功能(更新所有字段,根据ID更新)
	 * @param keyword 标王对象
	 */
	public Integer updateTradeKeyword(TradeKeyword keyword);

	/**
	 * 功能(根据id查询信息,查询所有字段)
	 * @param id 标王表编号
	 */
	public TradeKeyword queryTradeKeywordById(Integer id);
}

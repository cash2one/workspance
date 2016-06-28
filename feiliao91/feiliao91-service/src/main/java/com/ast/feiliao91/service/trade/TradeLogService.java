package com.ast.feiliao91.service.trade;

import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.trade.TradeLogDto;

public interface TradeLogService {
	
	final static Integer STATUS_0 = 0; //出帐
	final static Integer STATUS_1 = 1; //进账
			
	/**
	 * 支付订单流水号生成
	 * @param companyId
	 * @param orderNo
	 * @param money
	 * @return
	 */
	public Integer payOrder(Integer companyId,String orderNo,Float money);

	/**
	 * 买家确认收货，卖家到款
	 * @param companyId
	 * @param orderNo
	 * @param money
	 * @return
	 */
	public Integer paySucForSell(Integer companyId, String orderNo, Float money);
	
	/**
	 * 添加提现记录申请的流水
	 * @param companyId
	 * @param cashAdvanceId 对应的提现记录ID插入到备注里
	 * @param money
	 * @return
	 */
	public Integer insertCashAdvance(Integer companyId,Integer cashAdvanceId,Float money);
	
	/**
	 * 获得我的所有账单
	 * @param companyId
	 * @param page
	 * @return
	 */
	public PageDto<TradeLogDto> pageMyBill(Integer companyId,
			PageDto<TradeLogDto> page);
}

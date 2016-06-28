package com.ast.ast1949.service.sample;

import java.util.Map;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.sample.OrderBill;
import com.ast.ast1949.domain.sample.Refund;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.paychannel.OrderDto;
import com.ast.ast1949.paychannel.ReturnState;
import com.ast.ast1949.paychannel.exception.BizException;

public interface OrderBillService {
	public Integer insert(OrderBill orderBill);

	int updateByPrimaryKey(OrderBill record);

	int updateByPrimaryKeySelective(OrderBill record);

	OrderBill selectByPrimaryKey(Integer id);
	
	OrderBill selectByOrderSeq(String orderSeq);

	int deleteByPrimaryKey(Integer id);

	
	/**
	 *  样品订单查询
	 * @param companyId
	 * @param state
	 * @return
	 */
	PageDto<OrderBill> queryListBySampleId(Integer companyId,String state, PageDto<OrderBill> page, String from, String to,Integer sampleId,String isfront);
	
	
	/**
	 *  已买-卖 样品
	 * @param companyId
	 * @param state
	 * @return
	 */
	PageDto<OrderBill> queryListByCompanyId(Integer companyId,String state,  String rangeState,PageDto<OrderBill> page, String from, String to, String type);
	
	/**
	 * 已买到样品
	 * @param companyId
	 * @return
	 */
	PageDto<OrderBill>  queryBuyListByCompanyId(Integer companyId,String state, PageDto<OrderBill> page, String from, String to,String  keyword);

	/**
	 * 已买到样品统计
	 * @return
	 */	
	Map<String, Object>   queryBuyListByCompanyIdCount(Integer companyId, String from, String to,String  keyword, Map<String, Object> out);
	
	/**
	 * 已卖出样品
	 * @param companyId
	 * @return
	 */
	PageDto<OrderBill>  querySellListByCompanyId(Integer companyId,String state, PageDto<OrderBill> page, String from, String to,String  keyword);

	/**
	 * 已卖出样品统计
	 * @return
	 */
	Map<String, Object> querySellListByCompanyIdCount(Integer companyId, String from, String to,String  keyword, Map<String, Object> out);
	
	
	/**
	 * 确认订单后，保存订单
	 */
	void saveOrderBill(OrderBill orderBill);
	
	/**
	 * 即时到帐支付--回执处理
	 * @param state
	 * @return
	 */
	int dealCallback(ReturnState state)  throws BizException;

	
	/**
	 * 买家确认收货
	 * 存在资金的转移
	 * @param bill
	 * @param confirmPasswd
	 * @throws BizException 
	 */
	void confirmReceiveGoodsBybuyer(OrderDto dto, String confirmPasswd) throws BizException;

	/**
	 * 卖家确认收到退货
	 * 存在账务处理
	 * @throws BizException 
	 */
	void confirmRefundGoodRecvByseller(Refund refund, String confirmPasswd) throws BizException;

	/**
	 * 没有收到货的情况	--卖家同意退货 后将存在账务处理
	 * @param refund
	 */
	void confirmRefundNoGoodByseller(Refund refund);

	
	/**
	 * 全部虚拟账户支付
	 * @param bill
	 */
	void dealTradeNotChannel(OrderBill bill)  throws BizException;

	
	/**
	 * 已经付款订单，卖家主动关闭交易
	 * 买家的支付金额，从平台虚拟账户上转移到买家自己账户上。
	 * @param dto
	 * @param closeInfo
	 */
	void closeOrderByseller(OrderBill bill, String closeReson)  throws BizException;

	
	/**
	 * 买家申请退货
	 * @param bill
	 * @param companyId
	 * @param refundAmount
	 * @param refundType
	 * @param isflag
	 * @param refundReson
	 * @param refundDes
	 * @param refundNum
	 */
	void applayRefundBybuyer(OrderBill bill, Integer companyId, String refundAmount, String refundType, String isflag, String refundReson,
			String refundDes, Integer refundNum)  throws BizException;

	
	/**
	 * 卖家是否同意退货
	 * @param bill
	 * @param refund
	 * @param isAgree
	 * @param addressId
	 * @throws BizException 
	 */
	void agreeRefundByseller(OrderBill bill, Refund refund, String isAgree, Integer addressId) throws BizException;

	
	/**
	 * 买家确认退货发货
	 * @param bill
	 * @param refund
	 */
	void refundGoodSentBybuyer(OrderBill bill, Refund refund) throws BizException;

	/**
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	PageDto<OrderBill> queryListByFilter(PageDto<OrderBill> page, Map<String, Object> map);
	
	/**
	 * 统计被客户拿走的有效样品数
	 * @param sample_id
	 */
	public Integer sumSampleBySampleId(Integer sampleId);
	
	/**
	 * 统计当天成功支付的人数
	 * @param from
	 * @param to
	 */
	public Integer countBuyerIdByTime(String from,String to);
	
	/**
	 * 统计某个样品被多少公司申请过
	 * @param sampleId
	 */
	public Integer countBuyerIdBySampleId(Integer sampleId);
	
	/**
	 * 统计某样品没结束的订单数量
	 * @param sampleId
	 */
	public Integer countNotFinishBySampleId(Integer sampleId);
	
	/**
	 * 付款成功，增加积分,  增加客户积分总额 
	 * @param ca
	 */
	public  void increaseScore(CompanyAccount ca) ;
	
	/**
	 * 检索该公司对于这个样品的订单，判断是否未确认的订单。
	 * @param companyId
	 * @param sampleId
	 * @return true:存在 , false:不存在
	 */
	public OrderBill queryOrderExistByUser(Integer companyId,Integer sampleId);
}

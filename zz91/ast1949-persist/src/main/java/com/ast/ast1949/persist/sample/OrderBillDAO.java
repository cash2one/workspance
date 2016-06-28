package com.ast.ast1949.persist.sample;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.sample.OrderBill;
import com.ast.ast1949.dto.PageDto;

public interface OrderBillDAO {
	public Integer insert(OrderBill record);

	int updateByPrimaryKey(OrderBill record);

	int updateByPrimaryKeySelective(OrderBill record);

	OrderBill selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);
	
	/**
	 * 通过订单号查找
	 * @param orderSeq
	 * @return
	 */
	OrderBill selectByOrderSeq(String  orderSeq);
	/**
	 * 已买样品
	 * @param companyId
	 * @return
	 */
	List<OrderBill> queryBuyListByCompanyId(Integer companyId, String state,PageDto<OrderBill> page, String from, String to,String keyword,Integer sampleId);
	
	Integer queryBuyListByCompanyIdCount(Integer companyId, String state, String from, String to,String keyword,Integer sampleId);
	/**
	 * 已卖样品
	 * @param companyId
	 * @return
	 */
	List<OrderBill> querySellListByCompanyId(Integer companyId, String state,PageDto<OrderBill> page, String from, String to,String keyword);
	
	Integer querySellListByCompanyIdCount(Integer companyId, String state, String from, String to,String keyword);

	/**
	 * 已买-卖 样品
	 * @param companyId
	 * @param state
	 * @return
	 */
	List<OrderBill> queryListByCompanyId(Integer companyId, String state, String rangeState,PageDto<OrderBill> page, String from, String to, String type);

	Integer queryListByCompanyIdCount(Integer companyId, String state,  String rangeState,String from, String to, String type);

	
	/**
	 * 样品订单查询
	 * @param companyId
	 * @param state
	 * @return
	 */
	List<OrderBill> queryListBySampleId(Integer companyId, String state, PageDto<OrderBill> page, String from, String to, Integer sampleId,String isfront);
	
	Integer queryListBySampleIdCount(Integer companyId, String state, String from, String to, Integer sampleId,String isfront);

	List<OrderBill> queryListByFilter(Map<String, Object> filterMap);

	Integer queryListByFilterCount(Map<String, Object> filterMap);
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
	

}
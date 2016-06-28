package com.ast.ast1949.service.spot;

import java.util.List;

import com.ast.ast1949.domain.spot.SpotOrder;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotOrderDto;

/**
 *	author:kongsj
 *	date:2013-3-25
 */
public interface SpotOrderService {
	
	final static String STATUS_CART = "0";
	final static String STATUS_CONFIRM = "1";
	final static String STATUS_SUCCESS = "2";
	
	final static String IS_DEL = "1";
	final static String IS_NO_DEL = "0";
	/**
	 * 分页搜索订单页面 前台
	 * orderStatus订单状态：0(购物车)，1(确认购买)，2(购买成功)
	 */
	public PageDto<SpotOrderDto> pageSpotOrderForFront(Integer companyId,String orderStatus,PageDto<SpotOrderDto> page);
	
	/**
	 * (伪)删除一条订单
	 * @param id
	 * @param companyId
	 * @return
	 */
	public Integer deleteSpotOrderById(String idStr,Integer companyId);
	
	/**
	 * 新增一条订单 (加入购物车||确认购买)
	 * @param spotOrder
	 * @return
	 */
	public Integer insert(SpotOrder spotOrder);
	
	/**
	 * 确认购买 该商品
	 * @return
	 */
	public Integer confirmTransaction(String orderStatus,String idStr,Integer companyId);
	
	/**
	 * 检验是否已经加入购物车 
	 * @return true 表示存在
	 *         false 表示不存在 或者 已删除
	 */
	public boolean validateCart(Integer companyId,Integer spotId);

	public PageDto<SpotOrderDto> pageSpotOrder(SpotOrder spotOrder,PageDto<SpotOrderDto> page);
	
	/**
	 * 统计 该现货 所有的订单数量
	 * @param spotId
	 * @return
	 */
	public Integer countBySpotId(Integer spotId);
	
	/**
	 * 检索最新订单 
	 * @param size
	 * @return
	 */
	public List<SpotOrder> queryOrder(Integer size);

}

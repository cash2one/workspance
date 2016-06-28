package com.ast.feiliao91.service.goods;

import com.ast.feiliao91.domain.goods.OrderReturn;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.OrderReturnDto;

public interface OrderReturnService {
   /**
    * 插入退货信息
    */
	public Integer insert(OrderReturn orderReturn);
	
	/**
	 * 根据ID查询详细信息
	 * 
	 */
	public OrderReturn selectById(Integer id);
	
	/**
	 * 根据orderId查询详细信息
	 * 
	 */
	public OrderReturn selectByOrdId(Integer orderId);
	
    /**
     * 修改订单状态
     * @param orderReturnId
     */
	public Integer updateStatus(Integer orderReturnId,Integer status);
    /**
     * 修改退货退款申请	
     */
	public Integer updateOrdersReturn(OrderReturn orderReturn);
	/**
	 * 卖家回复退货详情
	 */
	public Integer updateOrdersReturnTwo(OrderReturn orderReturn);
    /**
     * 查看是否有这个退货单信息
     * @param id
     * @return
     */
	public Integer selectByOrderId(Integer orderId);
    /**
     * 查看详细退货信息
     * @param id
     * @return
     */
	public OrderReturn queryByOrderId(Integer orderId);
	
    /**
     * 根据id修改所有其他信息
     */
    
    public Integer updateAll(OrderReturn orderReturn);
    
    /**
     * 查询我的申请退款
     */
    public PageDto<OrderReturnDto> myRefund(PageDto<OrderReturnDto> page,Integer companyId);
    
    /**
     * 查询我的申请退款条数
     */
    public Integer myRefundCount(Integer companyId);
    
    /**
     * 查询我收到的申请退款
     */
    public PageDto<OrderReturnDto> getRefund(PageDto<OrderReturnDto> page,Integer companyId);
    /**
     * 查询我收到的申请退款的条数
     */
    public Integer getRefundCount(Integer companyId);
}

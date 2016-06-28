package com.ast.feiliao91.persist.goods;

import java.util.List;

import com.ast.feiliao91.domain.goods.OrderReturn;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.OrderReturnDto;

public interface OrderReturnDao {
	/**
	 * 插入退货信息
	 */
	public Integer insert(OrderReturn orderReturn);

	/**
	 * 查询退货信息
	 * 
	 * @param id
	 * @return
	 */
	public OrderReturn selectById(Integer id);

	/**
	 * 修改退货退款状态
	 * @param orderReturnId
	 * @return
	 */
	public Integer updateStatus(Integer orderReturnId, Integer status);
   /**
    * 修改退货退款信息
    * @param orderReturn
    * @return
    */
	public Integer updateOrdersReturn(OrderReturn orderReturn);
    /**
     * 回复退货说明
     * @param orderReturn
     * @return
     */
    public Integer updateOrdersReturnTwo(OrderReturn orderReturn);
    /**
     * 根据订单信息查看是否有退货信息
     * @param orderId
     * @return
     */
	public Integer selectByOrderId(Integer orderId);
    /**
     * 查看详细信息
     * @param orderId
     * @return
     */
	public OrderReturn queryByOrderId(Integer orderId);
    /**
     * 修改信息
     * @param orderReturn
     * @return
     */
	public Integer updateAll(OrderReturn orderReturn);
	/**
	 * 根据物流单号查询订单信息
	 */
	public OrderReturn queryByLogistics(String logisticsNo);

	  /**
     * 查询我的申请退款
     */
    public List<OrderReturn> myRefund(PageDto<OrderReturnDto> page,Integer companyId);
    
    /**
     * 查询我的申请退款条数
     */
    public Integer myRefundCount(Integer companyId);
    
    /**
     * 查询我收到的申请退款
     */
    public List<OrderReturn> getRefund(PageDto<OrderReturnDto> page,Integer companyId);
    /**
     * 查询我收到的申请退款的条数
     */
    public Integer getRefundCount(Integer companyId);
    /**
     * 根据orderId 查询　退货信息
     * @param orderId
     * @return
     */
	public OrderReturn selectByOrdId(Integer orderId);


}

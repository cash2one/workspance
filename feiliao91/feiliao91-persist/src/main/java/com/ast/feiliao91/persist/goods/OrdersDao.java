/**
 * @author shiqp
 * @date 2016-01-30
 */
package com.ast.feiliao91.persist.goods;

import java.util.List;

import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.OrdersDto;
import com.ast.feiliao91.dto.goods.OrdersSearchDto;

public interface OrdersDao {
	/**
	 * 产品成交记录列表
	 * @param page
	 * @param goodsId
	 * @return
	 */
	public List<Orders> queryOrdersByGoodsId(PageDto<OrdersDto> page, Integer goodsId);
    /**
     * 产品成交记录总条数
     * @param goodsId
     * @return
     */
	public Integer countOrdersByGoodsId(Integer goodsId);
	/**
	 * 交易量
	 * @param goodsId
	 * @return
	 */
	public Float countTradeQuality(Integer goodsId);
	/**
	 * 查看详细信息
	 * @param id
	 * @return
	 */
	public Orders selectById(Integer id);
	/**
	 * 修改订单价格
	 * @param order
	 * @return
	 */
	public Integer updatePrice(Orders order);
	/**
	 * 根据订单编号获得订单集合
	 * @param 订单编号orderNo
	 * @return List<Orders>
	 */
	public List<Orders> queryOrdersByOrderNo(String orderNo);
	/**
	 * 买家下单成功接口 ，订单状态 改为 0，
	 * @param 订单主id,买家buyCompanyId
	 * @return 
	 */
	public Integer updateStatusBuyXD(Integer id,Integer buyCompanyId,String details);
	/**
	 * 卖家审核通过接口 ，订单状态 改为 1
	 * @param 订单主id，卖家sellCompanyId
	 * @return 
	 */
	public Integer updateStatusSellPass(Integer id,Integer sellCompanyId,String details);
	/**
	 * 买家付款成功接口，订单状态 改为 2
	 * @param 订单主id，买家buyCompanyId
	 * @return 
	 */
	public Integer updateStatusBuyPaySuc(Integer id,Integer buyCompanyId,String details);
	/**
	 * 卖家已发货并提交物流单号接口，订单状态 改为 3
	 * @param 订单主id，卖家sellCompanyId，物流单号logistics_no
	 * @return 
	 */
	public Integer updateStatusSellPostGoods(Integer id,Integer sellCompanyId,String logisticsNo,String details);
	/**
	 * 买家已收货接口，根据后台任务更新物流信息表状态，若logistics_status=1，则将订单状态 改为 4
	 * @param 订单主id
	 * @return 
	 */
	public Integer updateStatusBuyGetGoods(Integer id,String details);
	/**
	 * 交易成功接口，订单状态 改为 66
	 * @param 订单主id，买家buyCompanyId
	 * @return 
	 */
	public Integer updateStatusTradeOver(Integer id,Integer buyCompanyId,String details);
	/**
	 * 更新为已读接口，detail的flag_**_no_read状态改为0 
	 * @param 订单主id，userID(买家或卖家的id)
	 * @return 
	 */
	public Integer updateHaveRead(Integer id,String details);
	
	/**
	 * 通用修改订单状态方法
	 * @param orderId
	 * @param status
	 * @return
	 */
	public Integer updateStatusByOrderNo(String orderNo,Integer status);
	
	/**
	 * 通用修改订单删除状态 买家删除，卖家删除
	 * @param orderId
	 * @param status
	 * @return
	 */
	public Integer updateUserDelByOrderNo(String orderNo, Integer buyIsDel, Integer sellIsDel);
	
	/**
	 * 创建订单
	 * @param orders
	 * @return
	 */
	public Integer insertOrders(Orders orders);
	
	public List<Orders> getDetailsForBuy(Integer companyId);
	
	public List<Orders> getDetailsForSell(Integer companyId);
	
	public 	List<Orders> queryOrdersByUser(PageDto<OrdersDto> page, OrdersSearchDto searchDto);
	
	public Integer countOrdersByUser(OrdersSearchDto searchDto);
	
	/**
	 * 更改订单 详细信息方法 通用
	 * @param order
	 * @return
	 */
	public Integer updateDetails(Orders order);
	
	/**
	 * 根据Id更新订单的details
	 * @param id
	 * @return
	 */
	public Integer updateDetailsByorderId(Integer id,String details);
	
	public List<Orders> selecJudge(Integer companyId, Integer goodsId);
	
	/**
	 * 根据物流单号查询订单信息
	 */
	public List<Orders> queryByLogistics(String logisticsNo);
	public List<Orders> OrdersByUser(OrdersSearchDto searchDto);
	public List<Orders> queryByAllByCompanyId(Integer companyId, PageDto<OrdersDto> page);
	public List<Integer> countByAllByCompanyId(Integer companyId);

	/**
	 * 根据orderNo获得主id，若涉及多个货物，只取第一个
	 * @param orderNo
	 * @return
	 */
	public Orders queryFistIdByOrderNo(String orderNo);
	
	/**
	 * 后台取订单（区别于queryOrdersByUser）
	 * @param page
	 * @param searchDto
	 * @return
	 */
	public List<Orders> queryOrdersByAdmin(PageDto<OrdersDto> page,OrdersSearchDto searchDto);
	
	/**
	 * 后台取订单个数
	 * @param searchDto
	 * @return
	 */
	public Integer countOrdersByAdmin(OrdersSearchDto searchDto);
	/**
	 * 修改订单状态
	 * @param orderId
	 * @param status
	 * @return
	 */
	public Integer updateStatus(Integer orderId, Integer status);
}

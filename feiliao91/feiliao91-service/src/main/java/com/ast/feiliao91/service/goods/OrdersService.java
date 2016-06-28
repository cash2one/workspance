/**
 * @author shiqp
 * @date 2016-01-30
 */
package com.ast.feiliao91.service.goods;


import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;

import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.OrdersDto;
import com.ast.feiliao91.dto.goods.OrdersSearchDto;

public interface OrdersService {

	final static String KEY_TIME_BUY_PAY_SUCCESS = "time_buy_pay_success"; 		// 买家付款成功时间
	final static String KEY_TIME_SELL_SEND = "time_sell_send";					// 卖家发货时间
 	final static String KEY_TIME_MAKE_SURE = "time_buy_make_sure";				// 确认收货时间
	final static String KEY_TIME_JUDGE = "time_buy_judge";						// 评价时间
	final static String KEY_SELL_POST = "sell_post";	//卖家发货信息
	final static String KEY_LOGISTICS_COMPANY = "comp";	//卖家发货的物流公司的代号
	final static String KEY_TOTAL_MONEY = "orderTotalPay"; //支付总金额
	
	final static String KEY_FLAG_PAYING = "paying";

	final static Integer STATUS_0  = 0; //等待卖家审核、买家已下单;
	final static Integer STATUS_1 = 1; //等待付款、 等待买家付款、;
	final static Integer STATUS_2 = 2; //等待卖家发货、 买家已付款、;
	final static Integer STATUS_3 = 3; //物流运输中,等待买家确认收货、;
	final static Integer STATUS_4 = 4; //货物已揽收、等待买家确认收货、;

	final static Integer STATUS_66 = 66; //交易成功、 交易成功;
	final static Integer STATUS_50 = 50; // 退款中
	final static Integer STATUS_99 = 99; // 关闭订单

	/**
	 * 产品最终页 成交记录
	 * @param page
	 * @param goodsId
	 * @return
	 */
	public PageDto<OrdersDto> pageOrdersByGoodsId(PageDto<OrdersDto> page,Integer goodsId);
	/**
	 * 交易管家 已卖出页 买入页 共用 检索数据方法，包括分页
	 * @param page
	 * @param goodsId
	 * @return
	 */
	public PageDto<OrdersDto> pageOrdersByUser(PageDto<OrdersDto> page,OrdersSearchDto searchDto);
	/**
	 * 交易量
	 * @param goodsId
	 * @return
	 */
	public Float countTradeQuality(Integer goodsId);
	
	/**
	 * 查询信息条数
	 * @param searchDto
	 * @return
	 */
	public Integer countOrdersByUser(OrdersSearchDto searchDto);
	
	/**
	 * 成交笔数
	 * @param goodsId
	 * @return
	 */
	public Integer countTradeNum(Integer goodsId);
	/**
	 * 货物详情
	 * 根据id查询
	 */
	public Orders selectById(Integer id);
	/**
	 * 修改价格
	 */
	public Integer updatePrice(Orders order);
	
	/**
	 * 卖家修改价格
	 * @param orderNo 订单编号
	 * @param pricePay 费用
	 * @param priceLogistics 运费
	 * @return
	 */
	public Integer updatePriceForSell(String orderNo,Float pricePay,Float priceLogistics);
	
	/**
	 * 货物详情
	 * 根据orderNo订单号查询
	 */
	public List<Orders> queryOrdersByOrderNo(String orderNo);
	
	/**
	 * 买家下单成功接口 ，订单状态 改为 0，detail中的卖家未读标志flag_sell_no_read设置为1,并将所买商品的数量相应减少
	 * @param 订单编号orderNo,买家buyCompanyId
	 * @return 
	 */
	public Integer updateStatusBuyXD(String orderNo,Integer buyCompanyId);
	/**
	 * 卖家审核通过接口 ，订单状态 改为 1，detail中的买家未读标志flag_buy_no_read设置为1
	 * @param 订单编号orderNo,卖家sellCompanyId
	 * @return 
	 */
	public Integer updateStatusSellPass(String orderNo,Integer sellCompanyId);
	
	/**
	 * 买家付款成功接口，订单状态 改为 2，detail中的卖家未读标志flag_sell_no_read设置为1
	 * @param 订单编号orderNo，买家buyCompanyId
	 * @return 
	 */
	public Integer updateStatusBuyPaySuc(String orderNo,Integer buyCompanyId);
	
	/**
	 * 买家点击购买
	 * 添加支付中 状态
	 * 
	 * @param 订单编号orderNo，买家buyCompanyId
	 * @return 
	 */
	public Integer updateStatusBuyPaying(String orderNo,Integer buyCompanyId);
	
	/**
	 * 返回待付款状态
	 * 
	 * @param 订单编号orderNo，买家buyCompanyId
	 * @return 
	 */
	public Integer updateStatusBuyPayingBack(String orderNo,Integer buyCompanyId);
	
	/**
	 * 卖家已发货并提交物流单号接口，订单状态 改为 3，detail中的买家未读标志flag_buy_no_read设置为1
	 * @param 订单主id，卖家sellCompanyId，物流单号logistics_no
	 * @return 
	 */
	public Integer updateStatusSellPostGoods(Integer id,Integer sellCompanyId,String logisticsNo);
	
	/**
	 * 买家已收货接口，根据后台任务更新物流信息表状态，则将订单状态 改为 4
	 * @param 订单主id
	 * @return 
	 */
	public Integer updateStatusBuyGetGoods(Integer id);
	
	/**
	 * 交易成功接口，订单状态 改为 66，，detail中的卖家未读标志flag_sell_no_read设置为1
	 * @param 订单编号orderNo，买家buyCompanyId
	 * @return 
	 */
	public Integer updateStatusTradeOver(String orderNo,Integer buyCompanyId);
	
	/**
	 * 更新为已读接口，detail的flag_**_no_read状态改为0
	 * @param 订单编号orderNo，userID(买家或卖家的id)
	 * @return 
	 */

	public Integer updateHaveRead(String orderNo,Integer userID);

	/**
	 * 创建订单信息
	 * @param id
	 * @param buyCompnayId
	 * @param attribute
	 * @param buyQuantity
	 * @param addId buyMessage
	 * @param  buyMessage
	 * @return
	 */
	public String createOrders(Integer id,Integer buyCompnayId,String attribute,Float buyQuantity,Integer addId,String buyMessage,String buyInvoiceTitle);
	/**
	 * 获取公司消息
	 * @param companyId
	 * @return
	 */
	public Integer getMessage(Integer companyId);
	
	/**
	 * 相同订单，归属一起展示，构建类方法使用于，已买的产品，已卖的产品
	 * @param page
	 * @return
	 */
	public LinkedMap buildOrderPageDto(List<OrdersDto> list);
	
	
	public Map<Integer, Integer> getMessageForSell(Integer companyId);
	
	public Map<Integer, Integer> getMessageForBuy(Integer companyId);
	/**
	 * 订单详情的展示
	 * @param out
	 * @param orderNo
	 */
	public void ordersInfo(Map<String,Object> out, String orderNo);
	/**
	 * 取消订单，并将所买商品的数量相应增加
	 * @param orderNo
	 * @param reason
	 * @return
	 */
	public Integer cancelOrder(String orderNo, String reason);
	
	/**
	 * 根据订单号检索订单详情 
	 * @param orderNo
	 * @return
	 */
	List<OrdersDto> queryOrdersDtoByOrderNo(String orderNo);
	/**
	 * 根据id更新订单的details
	 * @param id
	 * @return
	 */
	public Integer updateDetailsByorderId(Integer id,String details);
	/**
	 * 查询公司是否有此商品评价
	 * @param companyId 
	 * @param goodsId
	 * @return
	 */
	public boolean selecJudge(Integer companyId,Integer goodsId);
	
	public Integer getJudgeOrder(Integer companyId, Integer goodsId);
	
	public PageDto<OrdersDto> pageMyOrder(Integer companyId ,PageDto<OrdersDto> page);
	
	/**
	 * 购物车多条合并付款
	 * @param ids
	 * @param buyCompnayId
	 * @param addId
	 * @param buyMessage
	 * @param buyInvoiceTitle
	 * @return
	 */
	public Integer createMutiOrders(String ids, Integer buyCompnayId, Integer addId, String buyMessage, String buyInvoiceTitle,String number);
	/**
	 * 查询订单数
	 * @param searchDto
	 * @return
	 */
	public Integer countOrdersByUserTwo(OrdersSearchDto searchDto);
	
	/**
	 * 删除订单，买家删除，卖家删除。。
	 * @return
	 */
	public Integer delByOrderNo(Integer companyId,String orderNo);
	
	/**
	 * 根据orderNo获得主id，若涉及多个货物，只取第一个
	 * @param orderNo
	 * @return
	 */
	public Orders queryFistIdByOrderNo(String orderNo);
	
	/**
	 * 更新发货图片信息
	 * @param orderNo
	 * @return
	 */
	public Integer updateSellPostGoodsPic(Integer picId,Integer oId);
	
	/**
	 * 后台订单搜索（区别于pageOrdersByUser）
	 * @param page
	 * @param searchDto
	 * @return
	 */
	public PageDto<OrdersDto> pageBySearchByAdmin(PageDto<OrdersDto> page,
			OrdersSearchDto searchDto);
	
	/**
	 * 根据id修改订单状态
	 */
	
	public Integer updateStatus(Integer orderId,Integer status);
}

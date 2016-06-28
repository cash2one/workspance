/**
 * @author shiqp
 * @date 2016-01-30
 */
package com.ast.feiliao91.service.goods.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.company.CompanyService;
import com.ast.feiliao91.domain.company.Judge;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.OrderReturn;
import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.domain.goods.Picture;
import com.ast.feiliao91.domain.goods.Shopping;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.OrdersDto;
import com.ast.feiliao91.dto.goods.OrdersSearchDto;
import com.ast.feiliao91.persist.company.AddressDao;
import com.ast.feiliao91.persist.company.CompanyAccountDao;
import com.ast.feiliao91.persist.company.CompanyInfoDao;
import com.ast.feiliao91.persist.company.CompanyServiceDao;
import com.ast.feiliao91.persist.company.JudgeDao;
import com.ast.feiliao91.persist.goods.GoodsDao;
import com.ast.feiliao91.persist.goods.OrderReturnDao;
import com.ast.feiliao91.persist.goods.OrdersDao;
import com.ast.feiliao91.persist.goods.PictureDao;
import com.ast.feiliao91.persist.goods.ShoppingDao;
import com.ast.feiliao91.persist.logistics.LogisticsDao;
import com.ast.feiliao91.service.company.CompanyServiceService;
import com.ast.feiliao91.service.goods.GoodsService;
import com.ast.feiliao91.service.goods.OrderReturnService;
import com.ast.feiliao91.service.goods.OrdersService;
import com.ast.feiliao91.service.goods.PictureService;
import com.ast.feiliao91.service.trade.TradeLogService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

import net.sf.json.JSONObject;

@Component("ordersService")
public class OrdersServiceImpl implements OrdersService {
	@Resource
	private OrdersDao ordersDao;
	@Resource
	private CompanyInfoDao companyInfoDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private GoodsDao goodsDao;
	@Resource
	private LogisticsDao logisticsDao;
	@Resource
	private CompanyServiceDao companyServiceDao;
	@Resource
	private PictureDao pictureDao;
	@Resource
	private AddressDao addressDao;
	@Resource
	private PictureService pictureService;
	@Resource
	private OrderReturnDao orderReturnDao;
	@Resource
	private JudgeDao judgeDao;
	@Resource
	private GoodsService goodsService;
	@Resource
	private ShoppingDao shoppingDao;
	@Resource
	private TradeLogService tradeLogService;
	@Resource
	private OrdersService ordersService;
	@Resource
	private OrderReturnService orderReturnService;
	@Override
	public PageDto<OrdersDto> pageOrdersByGoodsId(PageDto<OrdersDto> page,
			Integer goodsId) {
		// 结果列表
		List<OrdersDto> listResult = new ArrayList<OrdersDto>();
		// 订单列表
		List<Orders> list = ordersDao.queryOrdersByGoodsId(page, goodsId);
		for (Orders order : list) {
			OrdersDto dto = new OrdersDto();
			dto.setOrders(order);
			if (order.getBuyCompanyId() != null) {
				CompanyAccount companyAccount = companyAccountDao
						.queryByCompanyId(order.getBuyCompanyId());
				if (companyAccount != null) {
					String account = companyAccount.getAccount();
					String accountWithAsterisk = getAccountWithAsterisk(account);
					dto.setAccountWithAsterisk(accountWithAsterisk);
				}
				CompanyInfo info = companyInfoDao.queryById(order
						.getBuyCompanyId());

				if (info != null) {
					dto.setInfo(info);
				}
			}
			// 规格
			String format = JSONObject.fromObject(order.getDetails())
					.get("format").toString();
			dto.setFormat(format);
			// 交易时间
			dto.setTradeTime(DateUtil.toString(order.getGmtCreated(),
					"yyyy-MM-dd HH:mm:ss"));
			// 产品信息
			Goods goods = goodsService.queryGoodById(order.getGoodsId());
			if (goods == null) {
				continue;
			}
			dto.setGoods(goods);
			listResult.add(dto);
		}
		page.setRecords(listResult);
		page.setTotalRecords(ordersDao.countOrdersByGoodsId(goodsId));
		return page;
	}

	@Override
	public Float countTradeQuality(Integer goodsId) {
		Float tQuality = ordersDao.countTradeQuality(goodsId);
		if (tQuality == null) {
			tQuality = (float) 0;
		}
		return tQuality;
	}

	@Override
	public Integer countTradeNum(Integer goodsId) {
		return goodsDao.querySuccessOrder(goodsId, null);
	}

	@Override
	public Orders selectById(Integer id) {
		return ordersDao.selectById(id);
	}

	@Override
	public Integer updatePrice(Orders order) {
		return ordersDao.updatePrice(order);
	}

	@Override
	public List<Orders> queryOrdersByOrderNo(String orderNo) {
		return ordersDao.queryOrdersByOrderNo(orderNo);
	}

	@Override
	public List<OrdersDto> queryOrdersDtoByOrderNo(String orderNo) {
		if (StringUtils.isEmpty(orderNo)) {
			return null;
		}
		List<OrdersDto> listResult = new ArrayList<OrdersDto>();
		List<Orders> list = ordersDao.queryOrdersByOrderNo(orderNo);
		for (Orders order : list) {
			OrdersDto dto = new OrdersDto();
			dto.setOrders(order);
			// 买家信息填充
			if (order.getBuyCompanyId() != null) {
				CompanyInfo info = companyInfoDao.queryById(order
						.getBuyCompanyId());
				if (info != null) {
					dto.setInfo(info);
				}
			}
			// 卖家信息填充
			if (order.getSellCompanyId() != null) {
				CompanyInfo info = companyInfoDao.queryById(order
						.getBuyCompanyId());
				if (info != null) {
					dto.setSellCompany(info);
				}
			}

			// 交易时间
			dto.setTradeTime(DateUtil.toString(order.getGmtCreated(),
					"yyyy-MM-dd HH:mm:ss"));
			if (order.getGoodsId() == null) {
				continue;
			}
			// json details 转换
			if (StringUtils.isNotEmpty(order.getDetails())) {
				JSONObject js = JSONObject.fromObject(order.getDetails());
				dto.setDetailJson(js);
			} else {
				dto.setDetailJson(new JSONObject());
			}
			// 组装商品信息
			Goods obj = goodsDao.queryById(order.getGoodsId());
			dto.setGoods(obj);
			// 检索服务 7天包退 保证金
			Integer i = companyServiceDao.queryServiceCount(obj.getCompanyId(),
					CompanyServiceService.SEVEN_DAY_SERVICE);
			if (i > 0) {
				dto.setSevenDayFlag(1);
			}
			Integer j = companyServiceDao.queryServiceCount(obj.getCompanyId(),
					CompanyServiceService.BZJ_SERVICE);
			if (j > 0) {
				dto.setBzjFlag(1);
			}
			// 封面图片
			List<Picture> plist = pictureService.queryPictureByCondition(
					obj.getId(), PictureService.TYPE_GOOD, obj.getCompanyId(),
					1);
			if (plist != null && plist.size() > 0) {
				dto.setPicAddress(plist.get(0).getPicAddress());
			}
			// 如果退款中 ，则获取退款物流号
			if (order.getStatus() != null && order.getStatus().equals(67)) {
				OrderReturn orderReturn = orderReturnDao.queryByOrderId(order
						.getId());
				if (orderReturn != null) {
					dto.setOrderReturn(orderReturn);
				}
			}
			// 是否有评价
			Judge judge = judgeDao.queryByOrderId(order.getId());
			if (judge != null) {
				dto.setJudge(judge);
			}
			listResult.add(dto);
		}
		return listResult;
	}

	@Override
	public Integer updateStatusBuyXD(String orderNo, Integer buyCompanyId) {
		if (StringUtils.isNotEmpty(orderNo) && buyCompanyId != null) {
			// 根据orderNo获得订单LIst集合
			List<Orders> orders = ordersDao.queryOrdersByOrderNo(orderNo);
			for (int i = 0; i < orders.size(); i++) {
				Integer id = orders.get(i).getId();
				Orders ods = ordersDao.selectById(id);
				String details = new String();
				if (StringUtils.isNotEmpty(ods.getDetails())) {
					JSONObject obj = JSONObject.fromObject(ods.getDetails());
					if (obj != null && obj.get("flag_sell_no_read") != null) {
						// 若数据库的detail字段有值并且存在“flag_sell_no_read”键
						obj.put("flag_sell_no_read", "1");
						details = obj.toString();
						Integer j = changeGoodsQuantity(ods.getBuyQuantity(),
								ods.getGoodsId(), 1);// 下单减货
						if (j.equals(0)) {
							// 数量不够，下单不成功
							continue;
						}
					} else {
						obj.put("flag_sell_no_read", "1");
						details = obj.toString();
					}
				} else {
					details = "{" + '"' + "flag_sell_no_read" + '"' + ":" + '"'
							+ "1" + '"' + "}";
				}
				ordersDao.updateStatusBuyXD(id, buyCompanyId, details);
			}
			// 设置当前操作用户的未读标志为已读
			doReaded(orderNo, buyCompanyId);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public Integer updateStatusSellPass(String orderNo, Integer sellCompanyId) {
		if (StringUtils.isNotEmpty(orderNo) && sellCompanyId != null) {
			// 根据orderNo获得订单LIst集合
			List<Orders> orders = ordersDao.queryOrdersByOrderNo(orderNo);
			for (int i = 0; i < orders.size(); i++) {
				Integer id = orders.get(i).getId();
				Orders ods = ordersDao.selectById(id);
				String details = new String();
				if (StringUtils.isNotEmpty(ods.getDetails())) {
					JSONObject obj = JSONObject.fromObject(ods.getDetails());
					if (obj != null && obj.get("flag_buy_no_read") != null) {
						// 若数据库的detail字段有值并且存在“flag_buy_no_read”键
						obj.put("flag_buy_no_read", "1");
						details = obj.toString();
					} else {
						obj.put("flag_buy_no_read", "1");
						details = obj.toString();
					}
				} else {
					details = "{" + '"' + "flag_buy_no_read" + '"' + ":" + '"'
							+ "1" + '"' + "}";
				}
				ordersDao.updateStatusSellPass(id, sellCompanyId, details);
			}
			// 设置当前操作用户的未读标志为已读
			doReaded(orderNo, sellCompanyId);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public Integer updateStatusBuyPaying(String orderNo, Integer buyCompanyId) {
		// 根据orderNo获得订单LIst集合
		List<Orders> list = ordersDao.queryOrdersByOrderNo(orderNo);
		// 进入 循环 更改读状态和支付时间
		Integer i = 0;
		for (Orders order : list) {
			String details = order.getDetails();
			JSONObject js = new JSONObject();
			if (StringUtils.isNotEmpty(details)) {
				js = JSONObject.fromObject(details);
			}
			js.put(KEY_FLAG_PAYING, 1);
			order.setDetails(js.toString());
			i = i + ordersDao.updateDetails(order);
		}
		return i;
	}

	@Override
	public Integer updateStatusBuyPayingBack(String orderNo,
			Integer buyCompanyId) {
		// 根据orderNo获得订单LIst集合
		List<Orders> list = ordersDao.queryOrdersByOrderNo(orderNo);
		// 进入 循环 更改读状态和支付时间
		Integer i = 0;
		for (Orders order : list) {
			String details = order.getDetails();
			JSONObject js = new JSONObject();
			if (StringUtils.isNotEmpty(details)) {
				js = JSONObject.fromObject(details);
			}
			js.remove(KEY_FLAG_PAYING);
			order.setDetails(js.toString());
			i = i + ordersDao.updateDetails(order);
		}
		return i;
	}

	@Override
	public Integer updateStatusBuyPaySuc(String orderNo, Integer buyCompanyId) {
		if (StringUtils.isNotEmpty(orderNo) && buyCompanyId != null) {
			// 根据orderNo获得订单LIst集合
			List<Orders> orders = ordersDao.queryOrdersByOrderNo(orderNo);
			Integer state = 0;
			// 总金额
			String moneyStr = "";

			// 进入 循环 更改读状态和支付时间
			for (int i = 0; i < orders.size(); i++) {
				Integer id = orders.get(i).getId();
				Orders ods = ordersDao.selectById(id);
				if (StringUtils.isEmpty(moneyStr)
						&& StringUtils.isNotEmpty(ods.getDetails())) {
					JSONObject js = JSONObject.fromObject(ods.getDetails());
					moneyStr = moneyStr + js.get(OrdersService.KEY_TOTAL_MONEY);
				}
				String details = new String();
				if (StringUtils.isNotEmpty(ods.getDetails())) {
					JSONObject obj = JSONObject.fromObject(ods.getDetails());
					if (obj != null && obj.get("flag_sell_no_read") != null) {
						// 若数据库的detail字段有值并且存在“flag_sell_no_read”键
						obj.put("flag_sell_no_read", "1");
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");// 设置日期格式
						obj.put("time_buy_pay_success", df.format(new Date()));
						details = obj.toString();
					} else {
						obj.put("flag_sell_no_read", "1");
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");// 设置日期格式
						obj.put("time_buy_pay_success", df.format(new Date()));
						details = obj.toString();
					}
				} else {
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");// 设置日期格式
					details = "{" + '"' + "flag_sell_no_read" + '"' + ":" + '"'
							+ "1" + '"' + "," + '"' + "time_buy_pay_success"
							+ '"' + ":" + '"' + df.format(new Date()) + '"'
							+ "}";
				}
				JSONObject js = JSONObject.fromObject(details);
				js.remove(KEY_FLAG_PAYING);
				state = ordersDao.updateStatusBuyPaySuc(id, buyCompanyId,
						js.toString());
			}

			// 订单支付成功添加流水号
			if (state != null && state > 0) {
				Float money = Float.valueOf(moneyStr);
				state = tradeLogService.payOrder(buyCompanyId, orderNo, money);
			}

			// 设置当前操作用户的未读标志为已读
			doReaded(orderNo, buyCompanyId);
			return state;
		} else {
			return 0;
		}
	}

	@Override
	public Integer updateStatusSellPostGoods(Integer id, Integer sellCompanyId,
			String logisticsNo) {
		if (id != null && sellCompanyId != null
				&& StringUtils.isNotEmpty(logisticsNo)) {
			Orders orders = ordersDao.selectById(id);
			if (orders != null) {
				String details = new String();
				if (StringUtils.isNotEmpty(orders.getDetails())) {
					JSONObject obj = JSONObject.fromObject(orders.getDetails());
					if (obj != null && obj.get("flag_buy_no_read") != null) {
						// 若数据库的detail字段有值并且存在“flag_buy_no_read”键
						obj.put("flag_buy_no_read", "1");
						obj.put("flag_sell_no_read", "0");// 卖家未读消息置0
						details = obj.toString();
					} else {
						obj.put("flag_buy_no_read", "1");
						obj.put("flag_sell_no_read", "0");// 卖家未读消息置0
						details = obj.toString();
					}
				} else {
					details = "{" + '"' + "flag_buy_no_read" + '"' + ":" + '"'
							+ "1" + '"' + "," + '"' + "flag_sell_no_read" + '"'
							+ ":" + '"' + "0" + '"' + "}";
				}
				return ordersDao.updateStatusSellPostGoods(id, sellCompanyId,
						logisticsNo, details);
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	@Override
	public Integer updateStatusBuyGetGoods(Integer id) {
		if (id != null) {
			Orders orders = ordersDao.selectById(id);
			if (orders != null) {
				String details = new String();
				if (StringUtils.isNotEmpty(orders.getDetails())) {
					JSONObject obj = JSONObject.fromObject(orders.getDetails());
					if (obj != null && obj.get("flag_sell_no_read") != null) {
						// 若数据库的detail字段有值并且存在“flag_sell_no_read”键
						obj.put("flag_sell_no_read", "1");
						details = obj.toString();
					}
				} else {
					details = "{" + '"' + "flag_sell_no_read" + '"' + ":" + '"'
							+ "1" + '"' + "}";
				}
				return ordersDao.updateStatusBuyGetGoods(id, details);
			} else {
				return 0;
			}
		} else {
			return 0;
		}
		// Orders orders=new Orders();
		// orders=ordersDao.selectById(id);
		// //orders.getLogisticsNo();
		// if(StringUtils.isNotEmpty(orders.getLogisticsNo())){
		// Logistics logistic =new Logistics();
		// logistic=logisticsDao.selectLogisticsByCode(orders.getLogisticsNo());
		// if(logistic.getLogisticsStatus()==1){
		// return ordersDao.updateStatusBuyGetGoods(id);
		// }else{
		// return null;
		// }
		// }else{
		// return null;
		// }
	}

	@Override
	public Integer updateStatusTradeOver(String orderNo, Integer buyCompanyId) {
		if (StringUtils.isNotEmpty(orderNo) && buyCompanyId != null) {
			// 根据orderNo获得订单LIst集合
			List<Orders> orders = ordersDao.queryOrdersByOrderNo(orderNo);
			for (int i = 0; i < orders.size(); i++) {
				Integer id = orders.get(i).getId();
				Orders ord = ordersDao.selectById(id);
				String details = new String();
				if (StringUtils.isNotEmpty(ord.getDetails())) {
					JSONObject obj = JSONObject.fromObject(ord.getDetails());
					if (obj != null && obj.get("flag_sell_no_read") != null) {
						// 若数据库的detail字段有值并且存在“flag_sell_no_read”键
						obj.put("flag_sell_no_read", "1");
						obj.put(KEY_TIME_MAKE_SURE, DateUtil.toString(
								new Date(), "yyyy-MM-dd HH:mm"));
						details = obj.toString();
					} else {
						obj.put("flag_sell_no_read", "1");
						obj.put(KEY_TIME_MAKE_SURE, DateUtil.toString(
								new Date(), "yyyy-MM-dd HH:mm"));
						details = obj.toString();
					}
				} else {
					details = "{" + '"' + "flag_sell_no_read" + '"' + ":" + '"'
							+ "1" + '"' + "}";
				}
				ordersDao.updateStatusTradeOver(id, buyCompanyId, details);
			}
			// 设置当前操作用户的未读标志为已读
			doReaded(orderNo, buyCompanyId);
			return 1;
		} else {
			return 0;
		}
	}

	// 设置当前操作用户的未读标志为已读
	public void doReaded(String orderNo, Integer userID) {
		List<Orders> orders = ordersDao.queryOrdersByOrderNo(orderNo);
		for (int i = 0; i < orders.size(); i++) {
			Integer id = orders.get(i).getId();
			Orders ord = ordersDao.selectById(id);
			String details = new String();
			if (ord.getBuyCompanyId() != null
					&& userID.equals(ord.getBuyCompanyId())
					&& StringUtils.isNotEmpty(ord.getDetails())) {
				// 若当前操作用户为买家，设置flag_buy_no_read为0
				JSONObject obj = JSONObject.fromObject(ord.getDetails());
				obj.put("flag_buy_no_read", "0");
				details = obj.toString();
				ordersDao.updateHaveRead(id, details);
			} else if (ord.getBuyCompanyId() != null
					&& userID.equals(ord.getSellCompanyId())
					&& StringUtils.isNotEmpty(ord.getDetails())) {
				// 若当前操作用户为卖家，设置flag_sell_no_read为0
				JSONObject obj = JSONObject.fromObject(ord.getDetails());
				obj.put("flag_sell_no_read", "0");
				details = obj.toString();
				ordersDao.updateHaveRead(id, details);
			}
		}
	}

	@Override
	public Integer updateHaveRead(String orderNo, Integer userID) {
		if (StringUtils.isNotEmpty(orderNo) && userID != null) {
			// 根据orderNo获得订单LIst集合
			List<Orders> orders = ordersDao.queryOrdersByOrderNo(orderNo);
			for (int i = 0; i < orders.size(); i++) {
				Integer id = orders.get(i).getId();
				Orders ord = ordersDao.selectById(id);
				String details = new String();
				if (ord.getBuyCompanyId() != null
						&& userID.equals(ord.getBuyCompanyId())
						&& StringUtils.isNotEmpty(ord.getDetails())) {
					// 若当前操作用户为买家，设置flag_buy_no_read为0
					JSONObject obj = JSONObject.fromObject(ord.getDetails());
					obj.put("flag_buy_no_read", "0");
					details = obj.toString();
					ordersDao.updateHaveRead(id, details);
				} else if (userID.equals(ord.getSellCompanyId())) {
					// 若当前操作用户为卖家，设置flag_sell_no_read为0
					JSONObject obj = JSONObject.fromObject(ord.getDetails());
					obj.put("flag_sell_no_read", "0");
					details = obj.toString();
					ordersDao.updateHaveRead(id, details);
				} else {
					return null;
				}
			}
			return null;
		} else {
			return null;
		}
	}

	// public String doReaded(String orderNo,Integer userID){
	//
	// }

	/**
	 * 订单详情 1、产品信息 2、卖方公司信息 3、买方公司信息 4、买方公司具有的服务 5、产品的图片 6、订单产品属性
	 */
	public Orders getOrders(Integer id, Integer buyCompanyId, String attribute,
			Integer addressId) {
		OrdersDto dto = new OrdersDto();
		// 1、产品信息
		Goods goods = goodsDao.queryById(id);
		if (goods == null) {
			return null;
		}
		// 2、买方公司信息
		CompanyInfo buyCompany = companyInfoDao
				.queryWithoutCheckInfoById(buyCompanyId);
		// 3、卖方公司信息
		if (goods != null && goods.getCompanyId() != null) {
			CompanyInfo sellCompany = companyInfoDao
					.queryWithoutCheckInfoById(goods.getCompanyId());
			dto.setSellCompany(sellCompany);
			// 4、买方公司具有的服务
			List<CompanyService> serlist = companyServiceDao
					.queryCompanyServiceListByCompanyId(buyCompanyId, 100);
			dto.setSerlist(serlist);
			// 5、产品的图片
			List<Picture> piclist = pictureDao.queryPictureByCondition(
					goods.getId(), PictureService.TYPE_GOOD, null, null);
			dto.setPiclist(piclist);
			if (piclist.size() > 0) {
				dto.setPicAddress(piclist.get(0).getPicAddress());
			}
		}
		// 6、订单产品属性
		dto.setFormat(attribute);
		dto.setGoods(goods);
		dto.setBuyCompany(buyCompany);
		// 收货地址详情
		Address addr = addressDao.selectAddress(addressId);
		dto.setAddress(addr);
		Gson gson = new Gson();
		// 订单信息组装
		Orders order = new Orders();
		order.setBuyCompanyId(buyCompanyId);
		order.setGoodsId(id);
		order.setOrderNo(getOrderNo(id, buyCompanyId));
		if (goods.getPrice().contains("-")) {
			String[] arr = goods.getPrice().split("-");
			order.setBuyPricePay(Float.valueOf(Float.valueOf(arr[0])
					+ Float.valueOf(arr[1])) / 2);
		} else {
			order.setBuyPricePay(Float.valueOf((goods.getPrice())));
		}
		Float fare = 0f;
		if (goods.getFare() != null) {
			fare = Float.valueOf(goods.getFare());
		}
		order.setBuyPriceLogistics(fare);// 运费
		try {
			order.setDetails(gson.toJson(dto));
		} catch (Exception e) {
			JSONObject js = JSONObject.fromObject(dto);
			order.setDetails(js.toString());
		}
		order.setSellCompanyId(goods.getCompanyId());
		return order;
	}

	public String getOrderNo(Integer goodsId, Integer buyCompanyId) {
		StringBuffer sb = new StringBuffer();
		// 年月日 xxxxxx
		sb.append(DateUtil.toString(new Date(), "yyMMdd"));
		// 随机数两位
		sb.append((10 + (int) (Math.random() * 90)));
		// 产品id、买家id
		sb.append(goodsId).append(buyCompanyId);
		return sb.toString();
	}

	@Override
	public String createOrders(Integer id, Integer buyCompnayId,
			String attribute, Float buyQuantity, Integer addId,
			String buyMessage, String buyInvoiceTitle) {
		// 订单详情
		Orders order = getOrders(id, buyCompnayId, attribute, addId);
		order.setBuyMessage(buyMessage);
		order.setBuyQuantity(buyQuantity);
		order.setBuyPriceLogistics(0f);
		order.setBuyCompanyId(buyCompnayId);
		order.setBuyInvoiceTitle(buyInvoiceTitle);
		order.setStatus(0);
		Integer i = ordersDao.insertOrders(order);
		if (i > 0) {
			return order.getOrderNo();
		}
		return null;
	}

	@Override
	public Integer createMutiOrders(String ids, Integer buyCompnayId,
			Integer addId, String buyMessage, String buyInvoiceTitle,
			String number) {
		if (StringUtils.isEmpty(ids)) {
			return 0;
		}
		Integer[] idArray = StringUtils.StringToIntegerArray(ids);
		String[] buyMessageArray = null;
		if (buyMessage.indexOf(",") != -1) {
			buyMessageArray = buyMessage.split(",");
		} else {
			buyMessageArray = new String[] { buyMessage };
		}
		Map<Integer, String> map = new HashMap<Integer, String>();
		int compIx = 0;
		Integer tempCompIx = null;
		Integer count = 0;
		String[] arr = null;
		if (number.contains(",")) {
			arr = number.split(",");
		}
		for (Integer i = 0; i < idArray.length; i++) {
			Shopping shopping = shoppingDao.queryById(idArray[i]);
			if (shopping == null) {

			}

			if (tempCompIx == null && shopping.getSellCompanyId() != null) {
				tempCompIx = shopping.getSellCompanyId();
			}
			if (tempCompIx.intValue() != shopping.getSellCompanyId().intValue()) {
				compIx++;
			}

			// 购物车是否已经删除
			if (shopping.getIsDel() != null
					&& shopping.getIsDel().intValue() == 1) {
				continue;
			}
			shopping.setNumber(arr[i]);
			// 订单详情
			Orders order = getOrders(shopping.getGoodId(), buyCompnayId,
					shopping.getAttributes(), addId);
			if (order == null) {
				continue;
			}
			// 买家留言
			try {
				order.setBuyMessage(buyMessageArray[compIx]);
			} catch (Exception e) {
				order.setBuyMessage("");
			}

			order.setBuyQuantity((Float.valueOf(shopping.getNumber())));
			order.setBuyPriceLogistics(0f);
			order.setBuyCompanyId(buyCompnayId);
			order.setBuyInvoiceTitle(buyInvoiceTitle);
			order.setStatus(0);
			// 检测是否同一个买家，如果是同一个卖家，订单号一致
			if (map.containsKey(shopping.getSellCompanyId())) {
				order.setOrderNo(map.get(shopping.getSellCompanyId()));
			}
			Integer result = ordersDao.insertOrders(order);
			if (result > 0) {
				updateStatusBuyXD(order.getOrderNo(), order.getBuyCompanyId()); // 更新消息，供卖家审核
				count++;
				map.put(shopping.getSellCompanyId(), order.getOrderNo());
				// 购买成功，订单生成成功，删除购物车
				shoppingDao.updateIsDel(idArray[i], 1);
			}
		}
		// if (i > 0) {
		// return order.getOrderNo();
		// }
		return count;
	}

	@Override
	public Integer getMessage(Integer companyId) {
		Integer i = 0;
		List<Orders> listBuy = ordersDao.getDetailsForBuy(companyId);
		for (Orders obj : listBuy) {
			String str = obj.getDetails();
			if (StringUtils.isEmpty(str)) {
				continue;
			}
			JSONObject o = JSONObject.fromObject(str);
			if (o != null && o.get("flag_buy_no_read") == null) {
				continue;
			}
			String flag = (String) o.get("flag_buy_no_read");
			if ("1".equals(flag)) {
				i++;
			}
		}
		List<Orders> listSell = ordersDao.getDetailsForSell(companyId);
		for (Orders obj : listSell) {
			String str = obj.getDetails();
			if (StringUtils.isEmpty(str)) {
				continue;
			}
			JSONObject o = JSONObject.fromObject(str);
			if (o != null && o.get("flag_sell_no_read") == null) {
				continue;
			}
			String flag = (String) o.get("flag_sell_no_read");
			if ("1".equals(flag)) {
				i++;
			}
		}
		return i;
	}

	@Override
	public Map<Integer, Integer> getMessageForSell(Integer companyId) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		List<Orders> listSell = ordersDao.getDetailsForSell(companyId);
		for (Orders obj : listSell) {
			Integer status = obj.getStatus();
			Integer i = map.get(status);
			if (i == null) {
				i = 0;
			}
			String str = obj.getDetails();
			if (StringUtils.isEmpty(str)) {
				continue;
			}
			JSONObject o = JSONObject.fromObject(str);
			if (o != null && o.get("flag_sell_no_read") == null) {
				continue;
			}
			String flag = (String) o.get("flag_sell_no_read");
			if ("1".equals(flag)) {
				i++;
			}
			map.put(status, i);
		}
		return map;
	}

	@Override
	public Map<Integer, Integer> getMessageForBuy(Integer companyId) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		List<Orders> listSell = ordersDao.getDetailsForBuy(companyId);
		for (Orders obj : listSell) {
			Integer status = obj.getStatus();
			Integer i = map.get(status);
			if (i == null) {
				i = 0;
			}
			String str = obj.getDetails();
			if (StringUtils.isEmpty(str)) {
				continue;
			}
			JSONObject o = JSONObject.fromObject(str);
			if (o != null && o.get("flag_buy_no_read") == null) {
				continue;
			}
			String flag = (String) o.get("flag_buy_no_read");
			if ("1".equals(flag)) {
				i++;
			}
			map.put(status, i);
		}
		return map;
	}

	@Override
	public PageDto<OrdersDto> pageOrdersByUser(PageDto<OrdersDto> page,
			OrdersSearchDto searchDto) {
		// 订单列表
		List<Orders> list = ordersDao.queryOrdersByUser(page, searchDto);
		page.setRecords(buildDtoList(list));
		page.setTotalRecords(ordersDao.countOrdersByUser(searchDto));
		return page;
	}

	/**
	 * 构建ordersdto list，通用接口 使用于前台展示模块 信息全
	 * 
	 * @param list
	 * @return
	 */
	private List<OrdersDto> buildDtoList(List<Orders> list) {
		List<OrdersDto> listResult = new ArrayList<OrdersDto>();

		for (Orders order : list) {
			OrdersDto dto = new OrdersDto();
			dto.setOrders(order);
			// 买家信息填充
			if (order.getBuyCompanyId() != null) {
				CompanyInfo info = companyInfoDao.queryById(order
						.getBuyCompanyId());
				if (info != null) {
					dto.setInfo(info);
				}
			}
			// 卖家信息填充
			if (order.getSellCompanyId() != null) {
				CompanyInfo info = companyInfoDao.queryById(order
						.getSellCompanyId());
				if (info != null) {
					dto.setSellCompany(info);
				}
			}

			// 交易时间
			dto.setTradeTime(DateUtil.toString(order.getGmtCreated(),
					"yyyy-MM-dd HH:mm:ss"));
			if (order.getGoodsId() == null) {
				continue;
			}
			// json details 转换
			try {
				if (StringUtils.isNotEmpty(order.getDetails())) {
					JSONObject js = JSONObject.fromObject(order.getDetails());
					dto.setDetailJson(js);
				} else {
					dto.setDetailJson(new JSONObject());
				}
			} catch (Exception e) {
				//去除details中的换行符
				if (StringUtils.isNotEmpty(order.getDetails())) {
					String newDetails = order.getDetails().replaceAll("\r|\n", "");
					JSONObject js = JSONObject.fromObject(newDetails);
					dto.setDetailJson(js);
				}else {
					dto.setDetailJson(new JSONObject());
				}
			}
			// 组装商品信息
			Goods obj = goodsDao.queryById(order.getGoodsId());
			dto.setGoods(obj);
			// 检索服务 7天包退 保证金
			Integer i = companyServiceDao.queryServiceCount(obj.getCompanyId(),
					CompanyServiceService.SEVEN_DAY_SERVICE);
			if (i > 0) {
				dto.setSevenDayFlag(1);
			}
			Integer j = companyServiceDao.queryServiceCount(obj.getCompanyId(),
					CompanyServiceService.BZJ_SERVICE);
			if (j > 0) {
				dto.setBzjFlag(1);
			}
			// 封面图片
			List<Picture> plist = pictureService.queryPictureByCondition(
					obj.getId(), PictureService.TYPE_GOOD, obj.getCompanyId(),
					1);
			if (plist != null && plist.size() > 0) {
				dto.setPicAddress(plist.get(0).getPicAddress());
			}
			// 如果退款中 ，则获取退款物流号
			if (order.getStatus() != null && order.getStatus().equals(67)) {
				OrderReturn orderReturn = orderReturnDao.queryByOrderId(order
						.getId());
				if (orderReturn != null) {
					dto.setOrderReturn(orderReturn);
				}
			}
			// 是否有评价
			Judge judge = judgeDao.queryByOrderId(order.getId());
			if (judge != null) {
				dto.setJudge(judge);
			}

			// 付款中
			if (dto.getDetailJson().get(KEY_FLAG_PAYING) != null) {
				dto.setFlagPaying(Integer.valueOf(""
						+ dto.getDetailJson().get(KEY_FLAG_PAYING)));
			}
			listResult.add(dto);
		}
		return listResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedMap buildOrderPageDto(List<OrdersDto> list) {
		if (list == null) {
			return null;
		}
		LinkedMap map = new LinkedMap();
		for (OrdersDto dto : list) {
			if (dto.getOrders() == null) {
				continue;
			}
			String orderNo = dto.getOrders().getOrderNo();
			if (StringUtils.isEmpty(orderNo)) {
				continue;
			}
			Integer count = orderReturnService.selectByOrderId(dto.getOrders().getId());
			if(count>0){
				OrderReturn or = orderReturnService.queryByOrderId(dto.getOrders().getId());
				dto.setOrderReturnStatus(or.getStatus());
			}
			List<OrdersDto> nlist = (List<OrdersDto>) map.get(orderNo);
			if (nlist == null) {
				nlist = new ArrayList<OrdersDto>();
			}
			List<Orders> li = ordersService.queryOrdersByOrderNo(orderNo);
			// 计算运费
			Float yunfei = 0f;
			for (Orders or : li) {
				if (JSONObject.fromObject(or.getDetails()).get("goods") != null) {
					JSONObject json = JSONObject.fromObject(JSONObject
							.fromObject(or.getDetails()).get("goods"));
					Float yunfei1 = 0f;
					if (json.get("fare") != null) {
						try {
							Float fare = Float.valueOf(json.get("fare")
									.toString());
							if (fare < 0) {
								fare = 0f;
							}
							yunfei1 = fare;
						} catch (Exception e) {
							yunfei1 = 0f;
						}
						yunfei = yunfei + yunfei1;
					}
				}
			}
			Float sumPrice = 0f;
			for (Orders or : li) {
				if (JSONObject.fromObject(or.getDetails()).get("goods") != null) {
					JSONObject json = JSONObject.fromObject(JSONObject
							.fromObject(or.getDetails()).get("goods"));
					if (json.get("price") != null
							&& json.get("number") != null) {
						Float pri = Float.valueOf(json.get("price")
								.toString());
						Float num = Float
								.valueOf(or.getBuyQuantity());
						if (num != null) {
							if (num < 0) {
								num = 0f;
							}
						}
						Float price = pri * num;
						sumPrice = sumPrice + price;
					}
				}
			}
			sumPrice = sumPrice + yunfei;
			java.text.DecimalFormat df = new java.text.DecimalFormat(
					"#######0.0");
			String sumPrice1 = df.format(sumPrice);
			dto.setYunfei(yunfei);
			dto.setJsTotalPay(Float.valueOf(sumPrice1));
			if (li.size() > 0) {
				if (li.get(0).getDetails() != null) {
					JSONObject js = JSONObject.fromObject(li.get(0)
							.getDetails());
					String str = "" + js.get("orderTotalPay");
					if (StringUtils.isNotEmpty(str) && !"null".equals(str)) {
						dto.setOrderTotalPay(Float.valueOf(str));
					}
					str = "" + js.get("orderFreightPay");
					if (StringUtils.isNotEmpty(str) && !"null".equals(str)) {
						Float buyPriceLogistics = Float.valueOf(str);
						dto.setOrderFreightPay(buyPriceLogistics);
					}
				}
			}
			nlist.add(dto);
			map.put(orderNo, nlist);
		}
		return map;
	}

	@Override
	public void ordersInfo(Map<String, Object> out, String orderNo) {
		// 订单号
		out.put("orderNo", orderNo);
		// 订单的所有详情
		List<Orders> list = ordersDao.queryOrdersByOrderNo(orderNo);
		out.put("list", list);
		for (Orders order : list) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = JSONObject.fromObject(order.getDetails());
			map.get("");
		}
	}

	@Override
	public Integer cancelOrder(String orderNo, String reason) {
		Integer i = -1;
		// 获取订单详情
		List<Orders> list = ordersDao.queryOrdersByOrderNo(orderNo);
		Gson gson = new Gson();
		java.lang.reflect.Type typelist = new TypeToken<OrdersDto>() {
		}.getType();
		java.lang.reflect.Type type = new TypeToken<Address>() {
		}.getType();
		for (Orders order : list) {
			OrdersDto dto = new OrdersDto();
			try {
				dto = gson.fromJson(order.getDetails(), typelist);
			} catch (Exception e) {
				JSONObject obj = JSONObject.fromObject(order.getDetails());
				dto.setBuyCompany(companyInfoDao.queryById(order
						.getBuyCompanyId()));
				dto.setSellCompany(companyInfoDao.queryById(order
						.getSellCompanyId()));
				dto.setOrders(order);
				dto.setGoods(goodsDao.queryById(order.getGoodsId()));
				Address addr = gson.fromJson(obj.get("Address").toString(),
						type);
				dto.setAddress(addr);
			}
			dto.setCancelReason(reason);
			order.setDetails(gson.toJson(dto));
			order.setStatus(99);
			// 更新退款理由
			i = ordersDao.updatePrice(order);
			// 获得下单时买的商品的数量
			Float buy_quantity = order.getBuyQuantity();
			changeGoodsQuantity(buy_quantity, order.getGoodsId(), 2);
			if (i > 0) {
				i = 0;
			} else {
				i = order.getId();
				break;
			}
		}
		return i;
	}

	@Override
	public Integer updatePriceForSell(String orderNo, Float price, Float lotic) {
		if (StringUtils.isEmpty(orderNo)) {
			return 0;
		}
		Integer i = 0;
		List<Orders> list = queryOrdersByOrderNo(orderNo);
		for (Orders obj : list) {
			String str = obj.getDetails();
			JSONObject js = new JSONObject();
			if (StringUtils.isNotEmpty(str)) {
				js = JSONObject.fromObject(str);
			}
			if (js != null) {
				if (price == null) {
					if (js.get("orderGoodsPay") == null) {
						price = getPrice(orderNo, 1);
					} else {
						price = Float.valueOf(js.get("orderGoodsPay")
								.toString());
					}
				}
				if (lotic == null) {
					if (js.get("orderFreightPay") == null) {
						lotic = getPrice(orderNo, 2);
					} else {
						lotic = Float.valueOf(js.get("orderFreightPay")
								.toString());
					}
				}
			}

			js.put("orderTotalPay", price + lotic); // 总支付
			js.put("orderGoodsPay", price); // 货品价格
			js.put("orderFreightPay", lotic); // 运费
			obj.setDetails(js.toString());
			i = ordersDao.updateDetails(obj);
			if (i > 0) {
				updateStatusSellPass(orderNo, obj.getSellCompanyId()); // 审核通过
				// 同意买家支付
			}
		}
		return i;
	}

	private Float getPrice(String orderNo, Integer type) {
		List<Orders> list = queryOrdersByOrderNo(orderNo);
		if (type.equals(1)) {
			Float price = 0f;
			for (Orders li : list) {
				Float pay = li.getBuyPricePay();
				Float num = li.getBuyQuantity();
				price = price + pay * num;
			}
			return price;
		} else {
			Float lotic = 0f;
			for (Orders li : list) {
				Float loct = 0f;
				if (li.getLogisticsNo() == null) {
					loct = 0f;
				} else {
					loct = Float.valueOf(li.getLogisticsNo());
				}
				lotic = lotic + loct;
			}
			return lotic;
		}
	}

	@Override
	public Integer updateDetailsByorderId(Integer id, String details) {
		if (StringUtils.isNotEmpty(details) && id != null) {
			return ordersDao.updateDetailsByorderId(id, details);
		} else {
			return null;
		}

	}

	@Override
	public boolean selecJudge(Integer companyId, Integer goodsId) {
		List<Orders> list = ordersDao.selecJudge(companyId, goodsId);
		if (list == null) {
			return false;
		}
		for (Orders or : list) {
			Judge judge = judgeDao.queryByOrderId(or.getId());
			if (judge == null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Integer getJudgeOrder(Integer companyId, Integer goodsId) {
		List<Orders> list = ordersDao.selecJudge(companyId, goodsId);
		if (list == null) {
			return null;
		}
		for (Orders or : list) {
			Judge judge = judgeDao.queryByOrderId(or.getId());
			if (judge == null) {
				return or.getId();
			}
		}
		return null;
	}

	/**
	 * buyQuantity为买家下单时的数量 goodsId为商品ID type=1为下单，type=2为取消订单
	 */
	public Integer changeGoodsQuantity(Float buyQuantity, Integer goodsId,
			Integer type) {
		if (type.equals(1) && type != null && goodsId != null
				&& buyQuantity != null) {
			// 下单减货
			// 获得当前货物的数量
			// String
			// oldQuantity=goodsService.queryGoodById(goodsId).getQuantity();
			String oldQuantity = goodsDao.queryGoodById(goodsId).getQuantity();
			float a = Float.parseFloat(oldQuantity);// 原来的
			float b = Float.parseFloat(buyQuantity + "");// 买了的
			if (a <= 0 || a - b < 0) {
				return 0;
			}
			String newQuantity = String.valueOf(a - b);
			goodsDao.updateGoodsQuantityByGoodsId(newQuantity, goodsId);
			return 1;
		} else if (type.equals(2) && type != null && goodsId != null
				&& buyQuantity != null) {
			String oldQuantity = goodsDao.queryGoodById(goodsId).getQuantity();
			float a = Float.parseFloat(oldQuantity);// 原来的
			float b = Float.parseFloat(buyQuantity + "");// 买了的
			String newQuantity = String.valueOf(a + b);
			goodsDao.updateGoodsQuantityByGoodsId(newQuantity, goodsId);
			return 2;
		} else {
			return 0;
		}

	}

	@Override
	public PageDto<OrdersDto> pageMyOrder(Integer companyId,
			PageDto<OrdersDto> page) {
		List<Orders> list = ordersDao.queryByAllByCompanyId(companyId, page);
		page.setRecords(buildDtoList(list));// 账单
		List<Integer> countList = ordersDao.countByAllByCompanyId(companyId);
		Integer count = 0;
		for (Integer i : countList) {
			if (i == null || i < 0) {
				i = 0;
			}
			count = count + i;
		}
		page.setTotalRecords(count);
		return page;
	}

	@Override
	public Integer countOrdersByUser(OrdersSearchDto searchDto) {
		List<Orders> list = ordersDao.OrdersByUser(searchDto);
		if (list == null) {
			return null;
		}
		Set<Object> set = new HashSet<Object>();
		for (Orders or : list) {
			set.add(or.getOrderNo());
		}
		return set.size();
	}

	@Override
	public Integer countOrdersByUserTwo(OrdersSearchDto searchDto) {
		List<Orders> list = ordersDao.OrdersByUser(searchDto);
		if (list == null) {
			return null;
		}
		return list.size();
	}

	public Integer delByOrderNo(Integer companyId, String orderNo) {
		if (companyId == null) {
			return 0;
		}
		Integer flag = 1;
		// 验证订单是否 属于买卖双方
		OrdersSearchDto searchDto = new OrdersSearchDto();
		searchDto.setBuyCompanyId(companyId);
		searchDto.setOrderNo(orderNo);
		Integer i = ordersDao.countOrdersByUser(searchDto);// 验证是买方
		if (i == null || i <= 0) {
			flag = 2;
			searchDto = new OrdersSearchDto();
			searchDto.setSellCompanyId(companyId);
			searchDto.setOrderNo(orderNo);
			i = ordersDao.countOrdersByUser(searchDto);// 验证是卖方
		}
		// 不属于买卖双方 删除失败 返回
		if (i == null || i <= 0) {
			return 0;
		}
		// 买家删除
		if (flag.intValue() == 1) {
			return ordersDao.updateUserDelByOrderNo(orderNo, 1, null);
		}
		// 卖家删除
		if (flag.intValue() == 2) {
			return ordersDao.updateUserDelByOrderNo(orderNo, null, 1);
		}
		return 0;
	}

	@Override
	public Orders queryFistIdByOrderNo(String orderNo) {
		if (StringUtils.isEmpty(orderNo)) {
			return null;
		} else {
			return ordersDao.queryFistIdByOrderNo(orderNo);
		}
	}

	@Override
	public Integer updateSellPostGoodsPic(Integer picId, Integer oId) {
		if (picId == null || oId == null) {
			return 0;
		} else {
			return pictureDao.updateSellPostGoodsPic(picId, oId);
		}
	}

	public String getAccountWithAsterisk(String account) {
		String newAccount = "";
		if (account.length() < 4 && account.length() >= 2) {
			String headAccount = account.substring(0, 1);
			String tailAccount = account.substring(account.length() - 1,
					account.length());
			newAccount = headAccount + "***" + tailAccount;
		} else {
			String headAccount = account.substring(0, 2);
			String tailAccount = account.substring(account.length() - 2,
					account.length());
			newAccount = headAccount + "***" + tailAccount;
		}
		return newAccount;
	}

	@Override
	public PageDto<OrdersDto> pageBySearchByAdmin(PageDto<OrdersDto> page,
			OrdersSearchDto searchDto) {
		// 订单列表
		List<Orders> list = ordersDao.queryOrdersByAdmin(page, searchDto);
		page.setRecords(buildDtoList(list));
		page.setTotalRecords(ordersDao.countOrdersByAdmin(searchDto));
		return page;
	}

	@Override
	public Integer updateStatus(Integer orderId, Integer status) {
		return ordersDao.updateStatus(orderId,status);
	}
}

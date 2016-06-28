/**
 * @author shiqp
 * @date 2016-02-15
 */
package com.ast.feiliao91.trade.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.domain.logistics.Logistics;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.dto.goods.OrdersDto;
import com.ast.feiliao91.service.commom.ParamService;
import com.ast.feiliao91.service.company.CompanyInfoService;
import com.ast.feiliao91.service.company.JudgeService;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.ast.feiliao91.service.goods.GoodsService;
import com.ast.feiliao91.service.goods.OrdersService;
import com.ast.feiliao91.service.logistics.LogisticsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.domain.Param;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

import net.sf.json.JSONObject;

@Controller
public class OrdersController extends BaseController {
	@Resource
	private OrdersService ordersService;
	@Resource
	private JudgeService judgeService;
	@Resource
	private LogisticsService logisticsService;
	@Resource
	private ParamService paramService;
	@Resource
	private CompanyInfoService companyInfoService;
	@Resource
	private GoodsService goodsService;

	@RequestMapping
	public ModelAndView subOrders(Map<String, Object> out,
			HttpServletRequest request, String ids, String buyMessage,
			String number) {
		String orderNo = "";
		// 登录信息
		SsoUser ssoUser = getCachedUser(request);
		do {
			String addressId = request.getParameter("addressId");
			String buyInvoiceTitle = request.getParameter("buyInvoiceTitle");
			// 多条，存在合并付款
			if (StringUtils.isNotEmpty(ids)) {
				ordersService.createMutiOrders(ids, ssoUser.getCompanyId(),
						Integer.valueOf(addressId), buyMessage,
						buyInvoiceTitle, number);
				return new ModelAndView("redirect:/order/buy.htm?status=0");
			}

			// 单挑付款，订单创建
			String id = request.getParameter("id");
			if (StringUtils.isEmpty(id)) {
				break;
			}
			String attribute = request.getParameter("attribute");
			String liuyan = request.getParameter("buyMessage");
			number = request.getParameter("number");
			orderNo = ordersService.createOrders(Integer.valueOf(id),
					ssoUser.getCompanyId(), attribute, Float.valueOf(number),
					Integer.valueOf(addressId), liuyan, buyInvoiceTitle);
			return new ModelAndView("redirect:" + "/step" + orderNo + ".htm");
		} while (false);
		return new ModelAndView("redirect:/index.htm");
	}

	@RequestMapping
	public ModelAndView step(HttpServletRequest request, Map<String, Object> out) {
		// 登录信息
		SsoUser user = getCachedUser(request);
		do {
			// 订单号
			String orderNo = request.getParameter("orderNo");
			out.put("orderNo", orderNo);
			// 运费
			float buyPriceLogistics = 0f;
			// 收获地址
			Address address = null;
			// 发票抬头
			String buyInvoiceTitle = "";
			// 买家留言
			String buyMessage = "";
			// 商家名称
			String sellName = "";
			// 取消订单类型
			String type = "";
			// 取消订单理由
			String reason = "";
			// 订单详情
			List<Orders> list = ordersService.queryOrdersByOrderNo(orderNo);
			// 计算运费
			Float yunfei = 0f;
			for (Orders or : list) {
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

			// 判断物流是否确认送达
			if (list != null) {
				Orders order = list.get(0);
				Map<String, Object> map = logisticsService
						.selectLogisticsByCode(order.getLogisticsNo(), "1");
				if (map != null) {
					out.put("state", map.get("state"));
				}
			}

			Float sumPrice = 0f;
			for (Orders or : list) {
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
			if (list.size() > 0) {
				if (list.get(0).getBuyCompanyId().equals(user.getCompanyId())) {
					out.put("operate", "buy");
				}
				if (list.get(0).getSellCompanyId().equals(user.getCompanyId())) {
					out.put("operate", "sell");
				}
				out.put("status", list.get(0).getStatus());
				out.put("ntime", DateUtil.toString(
						list.get(0).getGmtModified(), "yyyy-MM-dd HH:mm:ss"));
			} else {
				return new ModelAndView("redirect:"
						+ AddressTool.getAddress("trade")); // 订单不存在需要跳转的链接
			}
			List<OrdersDto> resultlist = new ArrayList<OrdersDto>();
			Gson gson = new Gson();
			for (Orders order : list) {
				java.lang.reflect.Type typelist = new TypeToken<OrdersDto>() {
				}.getType();
				OrdersDto dto = new OrdersDto();
				try {
					dto = gson
							.fromJson(order.getDetails().toString(), typelist);
				} catch (Exception e) {
					dto.setSellCompany(companyInfoService.queryInfoByid(order
							.getSellCompanyId()));
					dto.setBuyCompany(companyInfoService.queryInfoByid(order
							.getBuyCompanyId()));
					dto.setGoods(goodsService.queryGoodById(order.getGoodsId()));
					JSONObject js = JSONObject.fromObject(order.getDetails());
					String addressJs = js.get("Address").toString();
					String picAddress = js.get("picAddress").toString();
					dto.setAddress((Address) JSONObject.toBean(
							JSONObject.fromObject(addressJs), Address.class));
					dto.setPicAddress(picAddress);
				}
				dto.setOrders(order);

				resultlist.add(dto);
				// 运费计算
				// if(!"0".equals(String.valueOf(order.getBuyPriceLogistics()))){
				// buyPriceLogistics = buyPriceLogistics +
				// order.getBuyPriceLogistics();
				// }
				if (address == null) {
					address = dto.getAddress();
				}
				if (StringUtils.isEmpty(buyInvoiceTitle)) {
					buyInvoiceTitle = order.getBuyInvoiceTitle();
				}
				if (StringUtils.isEmpty(buyMessage)) {
					buyMessage = order.getBuyMessage();
				}
				if (StringUtils.isEmpty(sellName)) {
					sellName = dto.getSellCompany().getName();
				}
				if (StringUtils.isNotEmpty(dto.getCancelReason())) {
					if (dto.getCancelReason().contains("buy")) {
						type = "买家取消订单";
					} else if (dto.getCancelReason().contains("sell")) {
						type = "卖家取消订单";
					} else {
						type = "系统自动关闭";
					}
					reason = dto.getCancelReason().replace("buy", "")
							.replace("sell", "");
				}
				JSONObject js = JSONObject.fromObject(order.getDetails());
				String str = "" + js.get("orderTotalPay");
				if (StringUtils.isNotEmpty(str) && !"null".equals(str)) {
					out.put("cost", Float.valueOf(str));
				}
				str = "" + js.get("orderFreightPay");
				if (StringUtils.isNotEmpty(str) && !"null".equals(str)) {
					buyPriceLogistics = Float.valueOf(str);
					out.put("buyPriceLogistics", buyPriceLogistics);
				}
				// cost = cost + order.getBuyPricePay()*order.getBuyQuantity() +
				// order.getBuyPriceLogistics();
				// cost =
				// 是否评价 交易成功的情况下
				if (order.getStatus().equals(66)) {
					if (judgeService.hasJudgeByOrderId(order.getId())) {
						out.put("hasJudge", true);
					}
				}
				// 下单时间
				out.put("gmtCreated", order.getGmtCreated());
				String detailJsonStr = order.getDetails();
				if (StringUtils.isEmpty(detailJsonStr)) {
					continue;
				}
				JSONObject detailJson = JSONObject.fromObject(detailJsonStr);

				// 付款时间
				try {
					out.put("gmtPay",
							DateUtil.getDate(
									""
											+ detailJson
													.get(OrdersService.KEY_TIME_BUY_PAY_SUCCESS),
									"yyyy-MM-dd HH:mm:ss"));
				} catch (ParseException e1) {
					out.put("gmtPay", null);
				}
				// 发货时间
				try {
					out.put("gmtSend",
							DateUtil.getDate(
									""
											+ detailJson
													.get(OrdersService.KEY_TIME_SELL_SEND),
									"yyyy-MM-dd HH:mm:ss"));
				} catch (ParseException e) {
					out.put("gmtSend", null);
				}
				// 确认收货时间
				try {
					out.put("gmtSure",
							DateUtil.getDate(
									""
											+ detailJson
													.get(OrdersService.KEY_TIME_MAKE_SURE),
									"yyyy-MM-dd HH:mm"));
				} catch (ParseException e) {
					out.put("gmtSure", null);
				}
				// 评价时间
				try {
					out.put("gmtJudge", DateUtil.getDate(
							"" + detailJson.get(OrdersService.KEY_TIME_JUDGE),
							"yyyy-MM-dd HH:mm"));
				} catch (ParseException e) {
					out.put("gmtJudge", null);
				}
				// 有物流单号
				if (StringUtils.isNotEmpty(order.getLogisticsNo())) {
					Map<String, Object> logisticsMap = logisticsService
							.getLogistics(order.getLogisticsNo());
					out.put("logisticsMap", logisticsMap);
					out.put("logisticsNo", order.getLogisticsNo());
					// 获取物流公司
					JSONObject jss = (JSONObject) js
							.get(OrdersService.KEY_SELL_POST);
					if (jss != null) {
						out.put("logisticsCompany", jss.get("logisticsCompany"));
					}
				}
			}
			Integer goodId = null;
			for (Orders or : list) {
				boolean i = ordersService.selecJudge(user.getCompanyId(),
						or.getGoodsId());
				if (i) {
					goodId = or.getGoodsId();
					break;
				}
			}
			java.text.DecimalFormat df = new java.text.DecimalFormat(
					"#######0.0");
			String sumPrice1 = df.format(sumPrice);
			String yunfei1 = df.format(yunfei);
			out.put("sumPirce", sumPrice1);
			out.put("yunfei", yunfei1);
			out.put("goodId", goodId);
			out.put("resultlist", resultlist);
			out.put("buyInvoiceTitle", buyInvoiceTitle);
			out.put("buyMessage", buyMessage);
			out.put("sellName", sellName);
			out.put("type", type);
			out.put("reason", reason);
			// 物流公司选择列表
			List<Param> wuliuList = paramService.listParamByTypes("wuliu");
			if (wuliuList != null) {
				for (Param wuliu : wuliuList) {
					String s = wuliu.getValue();
					String[] s1 = s.split(",");
					wuliu.setValue(s1[0]);// 仅仅设置物流公司名称
				}
				out.put("wuliuList", wuliuList);
			}

			// 收获地址
			out.put("addr", gainAddress(address));
			ordersService.ordersInfo(out, orderNo);
			SeoUtil.getInstance().buildSeo("step", out);
		} while (false);
		return null;
	}

	/**
	 * 卖家添加发货物流信息
	 * 
	 * @param request
	 *            ,pic_str(发货图片id)
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView sellPostGoodsLogistics(Map<String, Object> out,
			String orderNo, String logisticsCompany, String logisticsNo,
			String sell_phone, String description, String pic_str)
			throws IOException {
		ExtResult rs = new ExtResult();
		// 添加物流至物流表
		if (StringUtils.isNotEmpty(logisticsCompany)
				&& StringUtils.isNotEmpty(logisticsNo)) {
			Logistics logistic = new Logistics();
			logistic.setLogisticsNo(logisticsNo);
			logistic.setLogisticsCode(logisticsCompany);
			logistic.setLogisticsStatus(0);
			logisticsService.insertLogistics(logistic);
		}
		// 若有图片，则添加图片信息
		if (StringUtils.isNotEmpty(pic_str)) {
			// target_id只取第一个orderNo的id
			Integer oId = ordersService.queryFistIdByOrderNo(orderNo).getId();
			// 分割字符串（pic_str）
			String[] picArray = pic_str.split(",");
			for (int i = 0; i < picArray.length; i++) {
				ordersService.updateSellPostGoodsPic(
						Integer.valueOf(picArray[i]), oId);
			}
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNo", orderNo);
		// 物流公司的中文
		map.put("logisticsCompany",
				paramService.getValueBylogisticsCompany(logisticsCompany, 0));
		// 物流公司电话
		map.put("logisticsCompanyPhone",
				paramService.getValueBylogisticsCompany(logisticsCompany, 1));
		map.put("logisticsNo", logisticsNo);
		map.put("sell_phone", sell_phone);
		map.put("description", description);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		JSONObject obj = JSONObject.fromObject(map);
		// 获得Orders列表（依据ordersNo）
		List<Orders> list = ordersService.queryOrdersByOrderNo(orderNo);
		for (Orders order : list) {
			String details = order.getDetails();// 原来的details
			JSONObject objOldDetails = JSONObject.fromObject(details);
			// 将发货信息put进去
			objOldDetails.put(OrdersService.KEY_SELL_POST, obj);
			objOldDetails.put(OrdersService.KEY_TIME_SELL_SEND,
					df.format(new Date()));
			objOldDetails.put(OrdersService.KEY_LOGISTICS_COMPANY,
					logisticsCompany);
			// 根据id更新Details（objOldDetails转String）
			String s = objOldDetails.toString();
			ordersService.updateDetailsByorderId(order.getId(), s);
			// 根据id更新订单状态
			ordersService.updateStatusSellPostGoods(order.getId(),
					order.getSellCompanyId(), logisticsNo);
		}
		rs.setSuccess(true);
		return printJson(rs, out);
	}

	public String gainAddress(Address address) {
		String addr = "";
		if (address.getAreaCode().length() > 16) {
			addr = CategoryFacade.getInstance().getValue(
					address.getAreaCode().substring(0, 12))
					+ "省"
					+ CategoryFacade.getInstance().getValue(
							address.getAreaCode().substring(0, 16))
					+ "市"
					+ CategoryFacade.getInstance().getValue(
							address.getAreaCode().substring(0, 20));
		}
		addr = addr + address.getAddress() + "," + address.getName();
		// 邮编
		if (StringUtils.isNotEmpty(address.getZipCode())) {
			addr = addr + "," + address.getZipCode();
		}
		// 手机号码
		if (StringUtils.isNotEmpty(address.getMobile())) {
			addr = addr + "," + address.getMobile();
		} else {
			addr = addr + "," + address.getTel();
		}
		return addr;
	}

	/**
	 * 关闭订单
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView cancelOrderTwo(HttpServletRequest request,
			Map<String, Object> out, String orderNo, String reason)
			throws IOException {

		// 订单号
		orderNo = request.getParameter("orderNo");
		ExtResult rs = new ExtResult();
		if (StringUtils.isEmpty(orderNo)) {
			rs.setSuccess(false);
			rs.setData("订单不存在");
			return printJson(rs, out);
		}
		// 取消订单理由
		reason = request.getParameter("reason");
		// 操作人
		String operate = "";
		if (StringUtils.isEmpty(reason)) {
			operate = request.getParameter("operate");
			if ("buy".equals(operate)) {
				reason = operate + "买家付款超时，订单自动关闭";
			}
		}
		Integer i = ordersService.cancelOrder(orderNo, reason);
		if (i < 1) {
			rs.setSuccess(true);
			return printJson(rs, out);
		}
		rs.setSuccess(false);
		rs.setData("关闭失败");
		return printJson(rs, out);
	}

	@RequestMapping
	public ModelAndView cancelOrder(HttpServletRequest request) {
		do {
			// 订单号
			String orderNo = request.getParameter("orderNo");
			if (StringUtils.isEmpty(orderNo)) {
				return new ModelAndView("redirect:"
						+ AddressTool.getAddress("trade"));
			}
			// 取消订单理由
			String reason = request.getParameter("reason");
			// 操作人
			String operate = "";
			if (StringUtils.isEmpty(reason)) {
				operate = request.getParameter("operate");
				if ("buy".equals(operate)) {
					reason = operate + "买家付款超时，订单自动关闭";
				}
			}
			Integer i = ordersService.cancelOrder(orderNo, reason);
			if (i < 1) {
				return new ModelAndView("redirect:"
						+ request.getHeader("Referer"));
			}
		} while (false);
		return new ModelAndView("redirect:" + AddressTool.getAddress("trade"));
	}

	@RequestMapping
	public ModelAndView delOrdersByUser(Map<String, Object> out,
			HttpServletRequest request, String orderNo) throws IOException {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		Integer i = ordersService.delByOrderNo(ssoUser.getCompanyId(), orderNo);
		if (i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}

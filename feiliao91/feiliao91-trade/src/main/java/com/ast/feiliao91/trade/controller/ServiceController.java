package com.ast.feiliao91.trade.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.company.FeedBack;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.OrderReturn;
import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.domain.trade.YfbLog;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.dto.goods.OrdersDto;
import com.ast.feiliao91.service.company.AddressService;
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.ast.feiliao91.service.company.CompanyInfoService;
import com.ast.feiliao91.service.company.CompanyServiceService;
import com.ast.feiliao91.service.company.FeedBackService;
import com.ast.feiliao91.service.goods.GoodsService;
import com.ast.feiliao91.service.goods.OrderReturnService;
import com.ast.feiliao91.service.goods.OrdersService;
import com.ast.feiliao91.service.trade.TradeLogService;
import com.ast.feiliao91.service.trade.YfbLogService;
import com.ast.feiliao91.trade.yeepay.PaymenUtils;
import com.ast.feiliao91.trade.yeepay.PaymentForOnlineService;
import com.ast.feiliao91.trade.yeepay.QueryResult;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

@Controller
public class ServiceController extends BaseController {
	@Resource
	private FeedBackService feedBackService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private OrdersService ordersService;
	@Resource
	private CompanyInfoService companyInfoService;
	@Resource
	private GoodsService goodsService;
	@Resource
	private CompanyServiceService companyServiceService;
	@Resource
	private AddressService addressService;
	@Resource
	private OrderReturnService orderReturnService;
	@Resource
	private TradeLogService tradeLogService;
	@Resource
	private YfbLogService yfbLogService;

	/**
	 * 消费者保障页面
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request) {
		SeoUtil.getInstance().buildSeo("serviceIndex", out);
		SsoUser ssoUser = getCachedUser(request);
		// 保证金
		// yfb订单记录
		List<YfbLog> list = yfbLogService.queryByCompanyId(ssoUser
				.getCompanyId());
		if (list.size() > 0) {
			for (YfbLog st : list) {
				QueryResult qr = null;
				try {
					qr = PaymentForOnlineService.queryByOrder(st.getPayOrder());
				} catch (Exception e) {
					yfbLogService.update(st.getPayOrder());
				}
				if (qr != null) {
					if (!"success".equalsIgnoreCase(qr.getRb_PayStatus())) {
						yfbLogService.update(st.getPayOrder());
					} else {
						boolean f = companyServiceService
								.validateServiceByCode(ssoUser.getCompanyId(),
										CompanyServiceService.BZJ_SERVICE);
						if (!f) {
							companyServiceService.createByCode(
									ssoUser.getCompanyId(),
									CompanyServiceService.BZJ_SERVICE);
							tradeLogService.payOrder(ssoUser.getCompanyId(),
									st.getPayOrder(), st.getMoney());
						}
						yfbLogService.update(st.getPayOrder());
					}
				}
			}
		}

		if (companyServiceService.validateServiceByCode(ssoUser.getCompanyId(),
				CompanyServiceService.BZJ_SERVICE)) {
			out.put("bzjFlag", 1);
		}

		// 7天无理由退货
		if (companyServiceService.validateServiceByCode(ssoUser.getCompanyId(),
				CompanyServiceService.SEVEN_DAY_SERVICE)) {
			out.put("sevendayFlag", 1);
		}

		return new ModelAndView();
	}

	// 开通保证金页面
	@RequestMapping
	public ModelAndView payBZJ(Map<String, Object> out,
			HttpServletRequest request, String flag) throws IOException {
		SsoUser ssoUser = getCachedUser(request);
		// 公司账户信息
		out.put("companyAccount", companyAccountService
				.queryAccountByAccount(ssoUser.getAccount()));
		// 商品名称
		out.put("GoodName", "taozaisheng--margin");
		// 订单号
		out.put("orderNo",
				String.valueOf(ssoUser.getCompanyId())
						+ System.currentTimeMillis());
		// 判断是否有支付密码
		Integer i = companyAccountService.judgePassWord(ssoUser.getCompanyId());
		out.put("i", i);
		out.put("flag", flag);
		SeoUtil.getInstance().buildSeo("payBZJ", out);
		return null;
	}

	/**
	 * 易付宝 付款成功 回调页面
	 */
	@RequestMapping
	public ModelAndView doPayOnline(Map<String, Object> out,
			HttpServletRequest request, String orderNo) {
		SsoUser ssoUser = getCachedUser(request);
		do {
			tradeLogService.payOrder(ssoUser.getCompanyId(), orderNo, 5000f);
			companyServiceService.createByCode(ssoUser.getCompanyId(),
					CompanyServiceService.BZJ_SERVICE);
		} while (false);
		return new ModelAndView("redirect:/service/index.htm");
	}

	/**
	 * 易付宝付款
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView payMoney(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response,
			String GoodName, String orderNo) throws IOException {

		StringBuffer url = new StringBuffer();
		url.append("?p2_Order=").append(orderNo);
		url.append("&p3_Amt=").append(5000);
		url.append("&p5_Pid=").append(GoodName);
		url.append("&p6_Pcat=").append(GoodName);
		url.append("&p7_Pdesc=").append(GoodName);
		url.append("&p8_Url=").append(
				AddressTool.getAddress("trade") + "/service/doPayOnline.htm");
		return new ModelAndView("redirect:http://www.zz91.com/zzpay/yb/pay.jsp"
				+ url.toString());
	}

	/**
	 * 易付宝付款记录
	 * 
	 * @throws IOException
	 */
	// 结算宝付款
	@RequestMapping
	public ModelAndView insetYfb(Map<String, Object> out,
			HttpServletRequest request, YfbLog yfbLog) throws IOException {
		SsoUser ssoUser = getCachedUser(request);
		ExtResult rs = new ExtResult();
		yfbLog.setMoney(5000f);
		yfbLog.setIsDel(0);
		do {
			if (ssoUser == null) {
				rs.setSuccess(false);
				break;
			}
			yfbLog.setCompanyId(ssoUser.getCompanyId());
			Integer i = yfbLogService.insert(yfbLog);
			if (i > 0) {
				rs.setSuccess(true);
			}
		} while (false);
		return printJson(rs, out);
	}

	// 结算宝付款
	@RequestMapping
	public ModelAndView doPay(Map<String, Object> out, String orderNo,
			HttpServletRequest request, String payPassword) {
		do {
			SsoUser ssoUser = getCachedUser(request);
			// 获取公司账户 同时确保支付密码正确
			CompanyAccount account = companyAccountService
					.queryByCompanyIdAndPayPwd(ssoUser.getCompanyId(),
							payPassword);
			// 余额不够
			if (account == null || account.getSumMoney() < 5000) {
				// 密码错误
				Integer i = 404;
				return new ModelAndView("redirect:/service/payBZJ.htm?flag="
						+ i);
			}
			// 扣款并更新
			Integer i = companyAccountService.updateSumMoney(
					account.getCompanyId(), account.getSumMoney() - 5000);
			// 记录交易流水号
			if (i > 0) {
				tradeLogService
						.payOrder(account.getCompanyId(), orderNo, 5000f);
			}
			companyServiceService.createByCode(ssoUser.getCompanyId(),
					CompanyServiceService.BZJ_SERVICE);
		} while (false);
		return new ModelAndView("redirect:/service/index.htm");
	}

	// 开通保证金
	@RequestMapping
	public ModelAndView openBZJService(Map<String, Object> out,
			HttpServletRequest request) throws IOException {
		ExtResult rs = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		companyServiceService.createByCode(ssoUser.getCompanyId(),
				CompanyServiceService.BZJ_SERVICE);
		return printJson(rs, out);
	}

	// 关闭保证金
	@RequestMapping
	public ModelAndView closeBZJService(Map<String, Object> out,
			HttpServletRequest request) throws IOException {
		ExtResult rs = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		companyServiceService.createByCode(ssoUser.getCompanyId(),
				CompanyServiceService.BZJ_SERVICE);
		return printJson(rs, out);
	}

	@RequestMapping
	public ModelAndView open7dayService(Map<String, Object> out,
			HttpServletRequest request) {
		SsoUser ssoUser = getCachedUser(request);
		companyServiceService.createByCode(ssoUser.getCompanyId(),
				CompanyServiceService.SEVEN_DAY_SERVICE);
		return new ModelAndView("redirect:/service/index.htm");
	}

	@RequestMapping
	public ModelAndView close7dayService(Map<String, Object> out,
			HttpServletRequest request) {
		SsoUser ssoUser = getCachedUser(request);
		companyServiceService.closeByCode(ssoUser.getCompanyId(),
				CompanyServiceService.SEVEN_DAY_SERVICE);
		return new ModelAndView("redirect:/service/index.htm");
	}

	@RequestMapping
	public ModelAndView complain(Map<String, Object> out,
			Integer targetCompanyId) {

		SeoUtil.getInstance().buildSeo("complain", out);
		out.put("targetCompanyId", targetCompanyId);
		return null;
	}

	@RequestMapping
	public ModelAndView feedback(Map<String, Object> out) {

		SeoUtil.getInstance().buildSeo("feedback", out);
		return null;
	}

	@RequestMapping
	public ModelAndView service(Map<String, Object> out, String targetId,
			Integer orderReturnId, String type) {
		out.put("targetId", targetId);
		out.put("orderReturnId", orderReturnId);
		out.put("type", type);
		SeoUtil.getInstance().buildSeo("service", out);
		return null;
	}

	/**
	 * 问题反馈
	 * 
	 * @param detail
	 * @param request
	 * @param out
	 * @param name
	 * @param phone
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView addfeedback(String detail, HttpServletRequest request,
			Map<String, Object> out, String name, String phone)
			throws IOException {
		SsoUser ssoUser = getCachedUser(request);
		FeedBack feedBack = new FeedBack();
		ExtResult rs = new ExtResult();
		feedBack.setCompanyId(ssoUser.getCompanyId());
		feedBack.setIsdel(0);
		feedBack.setTargetcompanyid(0);
		String detail1 = detail + "----问题反馈人:" + name + "-----联系方式:" + phone;
		feedBack.setDetail(detail1);
		Integer i = feedBackService.insert(feedBack);
		if (i > 0) {
			rs.setSuccess(true);
		}
		return printJson(rs, out);
	}

	/**
	 * 投诉卖家
	 * 
	 * @param detail
	 * @param request
	 * @param out
	 * @param name
	 * @param phone
	 * @param account
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView addComplain(String detail, HttpServletRequest request,
			Map<String, Object> out, String name, String phone,
			Integer targetCompanyId) throws IOException {
		SsoUser ssoUser = getCachedUser(request);
		FeedBack feedBack = new FeedBack();
		ExtResult rs = new ExtResult();
		feedBack.setCompanyId(ssoUser.getCompanyId());
		feedBack.setIsdel(0);
		// 被投诉人

		feedBack.setTargetcompanyid(targetCompanyId);
		feedBack.setTargetcompanyid(0);

		String detail1 = detail + "----投诉人:" + name + "-----联系方式:" + phone;
		feedBack.setDetail(detail1);
		Integer i = feedBackService.insert(feedBack);
		if (i > 0) {
			rs.setSuccess(true);
		}
		return printJson(rs, out);
	}

	/**
	 * 提交申请客服介入
	 * 
	 * @param detail
	 * @param request
	 * @param out
	 * @param name
	 * @param phone
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView addService(String detail, HttpServletRequest request,
			Map<String, Object> out, String name, String phone,
			String targetId, Integer orderReturnId, String type)
			throws IOException {
		SsoUser ssoUser = getCachedUser(request);
		FeedBack feedBack = new FeedBack();
		ExtResult rs = new ExtResult();
		feedBack.setCompanyId(ssoUser.getCompanyId());
		OrderReturn orderReturn = orderReturnService.selectById(orderReturnId);
		if (targetId == null) {
			feedBack.setTargetcompanyid(ssoUser.getCompanyId());
		} else {
			feedBack.setTargetcompanyid(Integer.valueOf(targetId));

			if (type != null) {
				if ("1".equals(type)) {
					Map<String, Object> map = getMap(orderReturn.getDetailAll());
					if (map == null) {
						map = new HashMap<String, Object>();
					}
					map.put("whoService", 1);
					orderReturn.setDetailAll(JSONObject.fromObject(map)
							.toString());
					orderReturn.setStatus(66);
					orderReturnService.updateAll(orderReturn);
				} else if ("2".equals(type)) {
					Map<String, Object> map = getMap(orderReturn.getDetailAll());
					if (map == null) {
						map = new HashMap<String, Object>();
					}
					map.put("whoService", 2);
					orderReturn.setDetailAll(JSONObject.fromObject(map)
							.toString());
					orderReturn.setStatus(66);
					orderReturnService.updateAll(orderReturn);
				}
			}
		}
		feedBack.setIsdel(0);
		String detail1 = detail + "----申请人:" + name + "-----联系方式:" + phone;
		feedBack.setDetail(detail1);
		Integer i = feedBackService.insert(feedBack);
		if (i > 0) {
			rs.setSuccess(true);
		}
		return printJson(rs, out);

	}

	/**
	 * 修改价格页面
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView replacePrice(Map<String, Object> out, Integer id,
			String code, HttpServletRequest request) {
		do {
			SsoUser user = getCachedUser(request);
			if (id == null && StringUtils.isEmpty(code)) {
				break;
			}
			if (id != null) {
				// 获取订单信息
				Orders orders = ordersService.selectById(id);
				code = orders.getOrderNo();
			}
			if (StringUtils.isNotEmpty(code)) {
				// 获取订单信息
				List<OrdersDto> list = ordersService
						.queryOrdersDtoByOrderNo(code);
				// 判断是否卖家修改价格
				if (list.size() > 0) {
					if (!list.get(0).getOrders().getSellCompanyId()
							.equals(user.getCompanyId())) {
						return new ModelAndView(
								"redirect:/order/sell.htm?status=0");
					}
				}
				// 计算运费
				Float yunfei = 0f;
				for (OrdersDto or : list) {
					if (JSONObject.fromObject(or.getOrders().getDetails()).get("goods") != null) {
						JSONObject json = JSONObject.fromObject(JSONObject
								.fromObject(or.getOrders().getDetails()).get("goods"));
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
				out.put("yunfei", yunfei);
				Float sumPrice = 0f;
				for (OrdersDto or : list) {
					if (JSONObject.fromObject(or.getOrders().getDetails()).get("goods") != null) {
						JSONObject json = JSONObject.fromObject(JSONObject
								.fromObject(or.getOrders().getDetails()).get("goods"));
						if (json.get("price") != null
								&& json.get("number") != null) {
							Float pri = Float.valueOf(json.get("price")
									.toString());
							Float num = Float
									.valueOf(or.getOrders().getBuyQuantity());
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
				out.put("sumPrice", sumPrice);
				if (list.size() > 0) {
					JSONObject js = JSONObject.fromObject(list.get(0)
							.getOrders().getDetails());
					String str = "" + js.get("orderTotalPay");
					if (StringUtils.isNotEmpty(str) && !"null".equals(str)) {
						out.put("cost", Float.valueOf(str));
					}
					str = "" + js.get("orderFreightPay");
					if (StringUtils.isNotEmpty(str) && !"null".equals(str)) {
						Float buyPriceLogistics = Float.valueOf(str);
						out.put("buyPriceLogistics", buyPriceLogistics);
					}
				}
				out.put("resultList", list);
				for (OrdersDto dto : list) {
					JSONObject obj = (JSONObject) dto.getDetailJson().get(
							"Address");
					dto.setBuyAddress(addressService.getArea(obj
							.getString("areaCode")));
					out.put("orderGoodsPay",
							JSONObject.fromObject(dto.getOrders().getDetails())
									.get("orderGoodsPay"));
					out.put("orderFreightPay",
							JSONObject.fromObject(dto.getOrders().getDetails())
									.get("orderFreightPay"));
				}
			}
		} while (false);
		SeoUtil.getInstance().buildSeo("replacePrice", out);
		return null;
	}

	/**
	 * 修改价格
	 * 
	 * @param out
	 * @param id
	 * @param pricePay
	 * @param priceLogistics
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updatePrice(Map<String, Object> out, Integer id,
			Float pricePay, Float priceLogistics, String code,Float yunfeiPrice)
			throws IOException {
		ExtResult rs = new ExtResult();
		do {
			if (pricePay != null) {
				if (pricePay < 0) {
					break;
				}
			}
			if (priceLogistics != null) {
				if (priceLogistics < 0) {
					break;
				}
			}
			if(priceLogistics==null){
				priceLogistics=yunfeiPrice;
			}
			if (StringUtils.isNotEmpty(code)) {
				Integer i = ordersService.updatePriceForSell(code, pricePay,
						priceLogistics);
				if (i > 0) {
					rs.setSuccess(true);
				}
			}
		} while (false);
		return printJson(rs, out);
	}

	/**
	 * 不修改价格直接通过审核
	 * 
	 * @param out
	 * @param id
	 * @param pricePay
	 * @param priceLogistics
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView sellPass(Map<String, Object> out, String code,
			HttpServletRequest request) throws IOException {
		do {
			SsoUser ssoUser = getCachedUser(request);
			ordersService.updateStatusSellPass(code, ssoUser.getCompanyId());
			List<Orders> orders = ordersService.queryOrdersByOrderNo(code);
			Float price = 0f;
			Float lotic = 0f;
			// 计算订单总价
			for (Orders li : orders) {
				Goods good = goodsService.queryGoodById(li.getGoodsId());
				Float pre = li.getBuyPricePay();
				Float num = li.getBuyQuantity();
				Float yunfei = 0f;
				if (good.getFare() != null) {
					if (Float.valueOf(good.getFare()) > 0) {
						yunfei = Float.valueOf(good.getFare());
					}
				}
				lotic = lotic + yunfei;
				price = price + pre * num;
			}
			// 存入　键名　orderTotalPay中
			for (Orders li : orders) {
				Orders ods = ordersService.selectById(li.getId());
				JSONObject obj = JSONObject.fromObject(ods.getDetails());
				if (obj != null) {
					obj.put("orderTotalPay", price + lotic);
					obj.put("orderGoodsPay", price);
					obj.put("orderFreightPay", lotic);
					ordersService.updateDetailsByorderId(ods.getId(),
							obj.toString());
				}
			}
		} while (false);
		return new ModelAndView("redirect:/step" + code + ".htm");
	}

	// 将json字符串转换为map类型
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(String json) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> map = new HashMap<String, Object>();
		for (Iterator<String> iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, jsonObject.get(key));
		}
		return map;
	}

}

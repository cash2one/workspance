package com.ast.feiliao91.trade.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.OrdersDto;
import com.ast.feiliao91.dto.goods.OrdersSearchDto;
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.ast.feiliao91.service.goods.OrdersService;
import com.ast.feiliao91.service.trade.TradeLogService;
import com.ast.feiliao91.service.trade.TradeService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

import net.sf.json.JSONObject;

@Controller
public class OrderController extends BaseController {

	@Resource
	private OrdersService ordersService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private TradeService tradeService;
	@Resource
	private TradeLogService tradeLogService;

	/**
	 * 我卖出的商品
	 * 
	 * @param out
	 * @param page
	 * @param searchDto
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView sell(Map<String, Object> out, PageDto<OrdersDto> page,
			OrdersSearchDto searchDto, HttpServletRequest request) {
		SeoUtil.getInstance().buildSeo("sell", out);
		SsoUser ssoUser = getCachedUser(request);
		searchDto.setSellCompanyId(ssoUser.getCompanyId());
		page.setSort("id");
		page.setDir("desc");
		page.setPageSize(8);
		Integer status=searchDto.getStatus();
		Integer isSellReady=searchDto.getIsSellRead();
		page = ordersService.pageOrdersByUser(page, searchDto);
		out.put("page", page);
		out.put("searchDto", searchDto);
		out.put("resultMap", ordersService.buildOrderPageDto(page.getRecords()));
		// out.put("countMap",
		// ordersService.getMessageForSell(ssoUser.getCompanyId()));
		searchDto.setStatus(0);
		out.put("countMapOne", ordersService.countOrdersByUser(searchDto));
		searchDto.setStatus(1);
		out.put("countMapTwo", ordersService.countOrdersByUser(searchDto));
		searchDto.setStatus(2);
		out.put("countMapThree", ordersService.countOrdersByUser(searchDto));
		searchDto.setStatus(3);
		out.put("countMapFour", ordersService.countOrdersByUser(searchDto));
		searchDto.setStatus(50);
		searchDto.setIsSellRead(null);
		out.put("countMapFix", ordersService.countOrdersByUserTwo(searchDto));
		searchDto.setStatus(status);
		searchDto.setIsSellRead(isSellReady);
		out.put("searchDto", searchDto);
		return new ModelAndView();
	}

	/**
	 * 我买到的商品
	 * 
	 * @param out
	 * @param page
	 * @param searchDto
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView buy(Map<String, Object> out, PageDto<OrdersDto> page,
			OrdersSearchDto searchDto, HttpServletRequest request) {
		SeoUtil.getInstance().buildSeo("buy", out);
		SsoUser ssoUser = getCachedUser(request);
		searchDto.setBuyIsDel(0);
		searchDto.setBuyCompanyId(ssoUser.getCompanyId());
		page.setSort("id");
		page.setDir("desc");
		page.setPageSize(8);
		Integer status=searchDto.getStatus();
		Integer jude=searchDto.getHasNoJudge();
		page = ordersService.pageOrdersByUser(page, searchDto);
		out.put("page", page);
		out.put("resultMap", ordersService.buildOrderPageDto(page.getRecords()));
		// out.put("countMap",
		// ordersService.getMessageForBuy(ssoUser.getCompanyId()));
		searchDto.setStatus(0);
		out.put("countMapOne", ordersService.countOrdersByUser(searchDto));
		searchDto.setStatus(1);
		out.put("countMapTwo", ordersService.countOrdersByUser(searchDto));
		searchDto.setStatus(2);
		out.put("countMapThree", ordersService.countOrdersByUser(searchDto));
		searchDto.setStatus(3);
		out.put("countMapFour", ordersService.countOrdersByUser(searchDto));
		searchDto.setStatus(66);
		searchDto.setHasNoJudge(1);
		out.put("countMapFive", ordersService.countOrdersByUserTwo(searchDto));
		searchDto.setStatus(status);
		searchDto.setHasNoJudge(jude);
		out.put("searchDto", searchDto);
		return new ModelAndView();
	}

	/**
	 * 付款
	 */
	@RequestMapping
	public ModelAndView pay(Map<String, Object> out, String orderNo,
			HttpServletRequest request, String flag) {
		SsoUser ssoUser = getCachedUser(request);
		// 公司账户信息
		out.put("companyAccount", companyAccountService.queryAccountByAccount(ssoUser.getAccount()));
		
		// 订单信息
		List<OrdersDto> list = ordersService.queryOrdersDtoByOrderNo(orderNo);
		if (list == null || list.size() == 0) {
			return new ModelAndView("redirect:/step" + orderNo + ".htm");
		}
		OrdersDto dto = list.get(0);
		// 订单不是待付款状态，订单不存在，订单信息有误，全部跳回订单步骤页面
		if (dto == null || dto.getOrders() == null|| dto.getOrders().getStatus() == null|| dto.getOrders().getStatus().intValue() != 1) {
			return new ModelAndView("redirect:/step" + orderNo + ".htm");
		}
		
		// 更新订单为支付中
		ordersService.updateStatusBuyPaying(orderNo, ssoUser.getCompanyId());
		
		out.put("list", list);
		out.put("orderNo", orderNo);
		if (StringUtils.isNotEmpty(flag)) {
			out.put("flag", flag);
		}
		return new ModelAndView();
	}
	
	
	/**
	 * 付款
	 */
	@RequestMapping
	public ModelAndView payMoney(Map<String, Object> out, String orderNo,
			HttpServletRequest request) {
			// 订单信息
			List<OrdersDto> list = ordersService.queryOrdersDtoByOrderNo(orderNo);
			
			if (list == null || list.size() == 0) {
				return new ModelAndView("redirect:/step" + orderNo + ".htm");
			}
			OrdersDto dto = list.get(0);
			// 订单不是待付款状态，订单不存在，订单信息有误，全部跳回订单步骤页面
			if (dto == null || dto.getOrders() == null|| dto.getOrders().getStatus() == null|| dto.getOrders().getStatus().intValue() != 1) {
				return new ModelAndView("redirect:/step" + orderNo + ".htm");
			}
			StringBuffer url = new StringBuffer();
			url.append("?p2_Order=").append(orderNo);
			url.append("&p3_Amt=").append(dto.getDetailJson().get("orderTotalPay"));
			url.append("&p5_Pid=").append(orderNo);
			url.append("&p6_Pcat=").append(dto.getGoods().getMainCategory());
			url.append("&p7_Pdesc=").append(orderNo);
			url.append("&p8_Url=").append(AddressTool.getAddress("trade")+"/order/doPayOnline"+orderNo+".htm");
		
		return new ModelAndView("redirect:http://www.zz91.com/zzpay/yb/pay.jsp"+url.toString());
	}

	/**
	 * 使用余额支付
	 */
	@RequestMapping
	public ModelAndView doPay(Map<String, Object> out, String orderNo,
			HttpServletRequest request, String payPassword) {
		do {
			SsoUser ssoUser = getCachedUser(request);
			// 公司账户信息
			out.put("companyAccount", companyAccountService.queryAccountByAccount(ssoUser.getAccount()));
			// 订单信息
			List<OrdersDto> list = ordersService
					.queryOrdersDtoByOrderNo(orderNo);
			OrdersDto dto = new OrdersDto();
			if (list.size() > 0) {
				dto = list.get(0);
			}
			if (dto == null) {
				break;
			}

			// 扣钱 添加交易流水
			Integer i = tradeService.payBySumOfMoney(orderNo, payPassword);
			// 密码错误
			if (i.intValue() == 404) {
				return new ModelAndView("redirect:/order/pay.htm?orderNo="
						+ orderNo + "&flag=" + i);
			}
			if (i > 0 && i.intValue() != 404) {
				ordersService.updateStatusBuyPaySuc(orderNo,
						ssoUser.getCompanyId());
			}
			return new ModelAndView("redirect:/order/paySuc.htm?orderNo="
					+ orderNo);
		} while (false);
		return new ModelAndView("redirect:/step" + orderNo + ".htm");
	}

	/**
	 * 支付成功
	 */
	@RequestMapping
	public ModelAndView paySuc(Map<String, Object> out, String orderNo) {
		SeoUtil.getInstance().buildSeo(out);
		if (StringUtils.isEmpty(orderNo)) {
			return new ModelAndView("redirect:" + AddressTool.getAddress("www"));
		}
		List<OrdersDto> list = ordersService.queryOrdersDtoByOrderNo(orderNo);
		out.put("list", list);

		return new ModelAndView();
	}

	/**
	 * 易付宝 付款成功 回调页面
	 */
	@RequestMapping
	public ModelAndView doPayOnline(Map<String, Object> out, String orderNo,
			HttpServletRequest request) {
		SsoUser ssoUser = getCachedUser(request);
		do {
			List<OrdersDto> list = ordersService.queryOrdersDtoByOrderNo(orderNo);
			if (list == null || list.size() == 0) {
				break;
			}
			OrdersDto dto = list.get(0);
			if (dto == null || dto.getOrders() == null
					|| dto.getOrders().getStatus() > 1) {
				break;
			}
			ordersService.updateStatusBuyPaySuc(orderNo,ssoUser.getCompanyId());
		} while (false);
		return new ModelAndView("redirect:/step" + orderNo + ".htm");
	}

	/**
	 * 确认收货
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView confirm(Map<String, Object> out, String orderNo,
			HttpServletRequest request, String pwd) throws IOException {
		SsoUser ssoUser = getCachedUser(request);
		// 判断支付密码
		CompanyAccount account = companyAccountService
				.queryByCompanyIdAndPayPwd(ssoUser.getCompanyId(), pwd);
		if (account == null) {
			Integer i = 404;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flag", i);
			return new ModelAndView("redirect:/order/recetion.htm?orderNo="
					+ orderNo + "&flag=" + i);
		}
		// 确认收获
		Integer i = ordersService.updateStatusTradeOver(orderNo,
				ssoUser.getCompanyId());
		if (i > 0) {
			// 卖家账户增加金钱，且流水号添加
			tradeService.paySucForSell(orderNo);
			return new ModelAndView("redirect:/step" + orderNo + ".htm");
		}
		return new ModelAndView("redirect:/step" + orderNo + ".htm");
	}

	/**
	 * 确认收货页面
	 */
	@RequestMapping
	public ModelAndView recetion(Map<String, Object> out, String orderNo,
			HttpServletRequest request, Integer flag) {
		SsoUser ssoUser = getCachedUser(request);

		// 公司账户信息
		out.put("companyAccount", companyAccountService
				.queryAccountByAccount(ssoUser.getAccount()));
		// 订单信息
		List<OrdersDto> list = ordersService.queryOrdersDtoByOrderNo(orderNo);
		if (list == null || list.size() == 0) {
			return new ModelAndView("redirect:/step" + orderNo + ".htm");
		}
		Integer i=companyAccountService.judgePassWord(ssoUser.getCompanyId());
		out.put("i", i);
		if (list != null && list.size() > 0) {
			out.put("dto", list.get(0));
			JSONObject obj = JSONObject.fromObject(list.get(0).getOrders()
					.getDetails());
			JSONObject co = JSONObject
					.fromObject(obj.get("Address").toString());
			String code = co.get("areaCode").toString();
			if (StringUtils.isNotEmpty(code)) {
				String address = getArea(code);
				out.put("ad", address);
			}
			// 下单时间
			out.put("gmtCreated",list.get(0).getOrders().getGmtCreated());
			// 付款时间
			try {
				out.put("gmtPay",DateUtil.getDate(""+obj.get(OrdersService.KEY_TIME_BUY_PAY_SUCCESS), "yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e1) {
				out.put("gmtPay",null);
			}
			// 发货时间
			try {
				out.put("gmtSend",DateUtil.getDate(""+obj.get(OrdersService.KEY_TIME_SELL_SEND), "yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				out.put("gmtSend",null);
			}
			// 确认收货时间
			try {
				out.put("gmtSure",DateUtil.getDate(""+obj.get(OrdersService.KEY_TIME_MAKE_SURE), "yyyy-MM-dd HH:mm"));
			} catch (ParseException e) {
				out.put("gmtSure",null);
			}
			// 评价时间
			try {
				out.put("gmtJudge",DateUtil.getDate(""+obj.get(OrdersService.KEY_TIME_JUDGE), "yyyy-MM-dd HH:mm"));
			} catch (ParseException e) {
				out.put("gmtJudge",null);
			}
			out.put("status", list.get(0).getOrders().getStatus());
			out.put("addr", co.get("address"));
			out.put("name", co.get("name"));
			out.put("mobile", co.get("mobile"));
			out.put("zipCode", co.get("zipCode"));
			out.put("price", obj.get(OrdersService.KEY_TOTAL_MONEY));
			out.put("orderFreightPay", obj.get("orderFreightPay"));
		}
		out.put("flag", flag);
		out.put("list", list);
		return new ModelAndView();
	}

	private String getArea(String areaCode) {
		String str = "";
		Integer i = 8;
		String tempCode = "";
		do {
			String fix = "";
			if (StringUtils.isEmpty(areaCode)) {
				break;
			}
			i = i + 4;
			if (areaCode.length() < i) {
				break;
			}
			tempCode = areaCode.substring(0, i);
			if (i == 12) {
				fix = "省";
			} else if (i == 16) {
				fix = "市";
			}
			str = str + CategoryFacade.getInstance().getValue(tempCode) + fix;
		} while (true);
		return str;
	}
	
	
	
	
}

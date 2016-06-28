package com.ast.feiliao91.service.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.dto.goods.OrdersDto;
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.ast.feiliao91.service.goods.OrdersService;
import com.ast.feiliao91.service.trade.TradeLogService;
import com.ast.feiliao91.service.trade.TradeService;
import com.zz91.util.lang.StringUtils;

import net.sf.json.JSONObject;

@Component("tradeService")
public class TradeServiceImpl implements TradeService{
	
	@Resource
	private OrdersService ordersService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private TradeLogService tradeLogService;
	
	@Override
	public Integer payBySumOfMoney(String orderNo,String payPassword){
		Integer i = 0;
		do {
			// 订单号必须存在
			if (StringUtils.isEmpty(orderNo)) {
				break;
			}
			List<OrdersDto> list = ordersService.queryOrdersDtoByOrderNo(orderNo);
			OrdersDto dto = new OrdersDto();
			if (list.size()>0) {
				dto = list.get(0);
			}
			if (dto==null) {
				break;
			}
			Orders order =  dto.getOrders();
			if (order==null||StringUtils.isEmpty(order.getDetails())) {
				break;
			}
			JSONObject js = JSONObject.fromObject(order.getDetails());
			String moneyStr = "" + js.get(OrdersService.KEY_TOTAL_MONEY);
			//总金额
			Float money = Float.valueOf(moneyStr);
			if (money==null) {
				break;
			}
			// 获取公司账户 同时确保支付密码正确
			CompanyAccount account =  companyAccountService.queryByCompanyIdAndPayPwd(order.getBuyCompanyId(), payPassword);
			// 余额不够 
			if (account==null||account.getSumMoney()<money) {
				// 密码错误
				i =404; 
				break;
			}
			// 扣款并更新
			i = companyAccountService.updateSumMoney(order.getBuyCompanyId(), account.getSumMoney()-money);
			
			// 记录交易流水号
			if (i>0) {
				tradeLogService.payOrder(order.getBuyCompanyId(), orderNo, money);
			}
			
		} while (false);
		return i;
	}

	@Override
	public Integer paySucForSell(String orderNo) {
		Integer i = 0;
		do {
			// 订单号必须存在
			if (StringUtils.isEmpty(orderNo)) {
				break;
			}
			List<OrdersDto> list = ordersService.queryOrdersDtoByOrderNo(orderNo);
			OrdersDto dto = new OrdersDto();
			if (list.size()>0) {
				dto = list.get(0);
			}
			if (dto==null) {
				break;
			}
			Orders order =  dto.getOrders();
			if (order==null||StringUtils.isEmpty(order.getDetails())) {
				break;
			}
			JSONObject js = JSONObject.fromObject(order.getDetails());
			String moneyStr = "" + js.get(OrdersService.KEY_TOTAL_MONEY);
			//总金额
			Float money = Float.valueOf(moneyStr);
			if (money==null) {
				break;
			}
			// 获取公司账户 同时确保支付密码正确
			CompanyAccount account =  companyAccountService.queryAccountByCompanyId(order.getSellCompanyId());
			if (account==null) {
				account = new CompanyAccount();
				account.setCompanyId(order.getSellCompanyId());
			}
			// 余额不够 
			if (account.getSumMoney()==null) {
				account.setSumMoney(0f);
			}
			// 余额增加并更新
			companyAccountService.updateSumMoney(order.getSellCompanyId(), account.getSumMoney()+money);
			
			// 记录交易流水号
			tradeLogService.paySucForSell(order.getSellCompanyId(), orderNo, money);
			
			
		} while (false);
		return i;
	}
	
}
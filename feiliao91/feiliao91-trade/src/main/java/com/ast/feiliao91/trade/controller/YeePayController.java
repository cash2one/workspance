package com.ast.feiliao91.trade.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.service.goods.OrdersService;
import com.ast.feiliao91.service.trade.TradeLogService;
import com.ast.feiliao91.trade.yeepay.PaymentForOnlineService;
import com.ast.feiliao91.trade.yeepay.QueryResult;

@Controller
public class YeePayController extends BaseController{
	
	@Resource
	private OrdersService ordersService;
	@Resource
	private TradeLogService tradeLogService;
	
	@RequestMapping
	public ModelAndView checkPaySuccessOrNot(Map<String, Object> out,String orderNo,HttpServletRequest request) throws IOException{
		ExtResult result =new  ExtResult();
		do {
			SsoUser ssoUser = getCachedUser(request); // 获得用户登录信息
			if (ssoUser==null) {
				break;
			}
			try {
				QueryResult qr = PaymentForOnlineService.queryByOrder(orderNo);	// 调用后台外理查询方法
	//			System.out.println("业务类型 [r0_Cmd:" + qr.getR0_Cmd() + "]<br>");
	//			System.out.println("查询结果 [r1_Code:" + qr.getR1_Code() + "]<br>");
	//			System.out.println("易宝支付交易流水号 [r2_TrxId:" + qr.getR2_TrxId() + "]<br>");
	//			System.out.println("支付金额 [r3_Amt:" + qr.getR3_Amt() + "]<br>");
	//			System.out.println("交易币种 [r4_Cur:" + qr.getR4_Cur() + "]<br>");
	//			System.out.println("商品名称 [r5_Pid:" + qr.getR5_Pid() + "]<br>");
	//			System.out.println("商户订单号 [r6_Order:" + qr.getR6_Order() + "]<br>");
	//			System.out.println("商户扩展信息 [r8_MP:" +  qr.getR8_MP() + "]<br>");
	//			System.out.println("支付状态 [rb_PayStatus:" +  qr.getRb_PayStatus() + "]<br>");
	//			System.out.println("已退款次数 [rc_RefundCount:" + qr.getRc_RefundCount() + "]<br>");
	//			System.out.println("已退款金额 [rd_RefundAmt:" + qr.getRd_RefundAmt() + "]<br>");
				// 支付状态为失败
				if(!"success".equalsIgnoreCase(qr.getRb_PayStatus())){
					break;
				}
				Integer i = ordersService.updateStatusBuyPaySuc(orderNo,ssoUser.getCompanyId());
				if (i>0) {
					result.setSuccess(true);
				}
			} catch(Exception e) {
				ordersService.updateStatusBuyPayingBack(orderNo, ssoUser.getCompanyId());
//				System.out.println(e.getMessage());
			}
		} while (false);
		return printJson(result, out);
	}
	
	
}

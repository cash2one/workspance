package com.ast.feiliao91.trade.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.service.goods.OrdersService;
import com.ast.feiliao91.service.goods.ShoppingService;
import com.ast.feiliao91.trade.controller.BaseController;

@Controller
public class ChinaController extends BaseController{
	@Resource
	private ShoppingService shoppingService;
	@Resource
	private OrdersService ordersService;
	@RequestMapping
	public ModelAndView top(HttpServletRequest request, Map<String, Object> out) {
		SsoUser sessionUser = getCachedUser(request);
		out.put("sessionUser", sessionUser);
		if(sessionUser != null){
			out.put("scount", shoppingService.countShoppingByCondition(sessionUser.getCompanyId(), null, null));
			out.put("mcount", ordersService.getMessage(sessionUser.getCompanyId()));
		}
		
		return new ModelAndView();
	}
}

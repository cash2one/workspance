package com.ast.feiliao91.admin.controller.admin;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.admin.controller.BaseController;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.OrdersDto;
import com.ast.feiliao91.dto.goods.OrdersSearchDto;
import com.ast.feiliao91.service.goods.OrdersService;
import com.ast.feiliao91.service.trade.CashAdvanceService;
import com.zz91.util.lang.StringUtils;

/**
 * 订单管理
 * @author zhujq
 *
 */
@Controller
public class OrdersController extends BaseController{
	@Resource
	private OrdersService ordersService;//订单接口
	@Resource
	private CashAdvanceService cashAdvanceService;//提现
	
	/**
	 * 默认页
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(Map<String,Object> out){
		return null;
	}
	
	/**
	 * 订单列表
	 * @param out
	 * @param page
	 * @param searchDto
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryList(Map<String,Object> out,PageDto<OrdersDto> page, OrdersSearchDto searchDto) throws IOException{
		if(StringUtils.isEmpty(page.getSort())){
			page.setSort("gmt_modified");
		}
		page = ordersService.pageBySearchByAdmin(page, searchDto);
		return printJson(page, out);
	}
	
	/**
	 * 该公司的卖出的产品订单
	 * @param request
	 * @param out
	 * @param companyId
	 * @return
	 */
	@RequestMapping
	public ModelAndView listOfCompanySell(HttpServletRequest request,
			Map<String, Object> out, Integer companyId) {
		out.put("companyId", companyId);
		return null;
	}
	
	/**
	 * 该公司的买到的产品订单
	 * @param request
	 * @param out
	 * @param companyId
	 * @return
	 */
	@RequestMapping
	public ModelAndView listOfCompanyBuy(HttpServletRequest request,
			Map<String, Object> out, Integer companyId) {
		out.put("companyId", companyId);
		return null;
	}
	
}

package com.zz91.ep.admin.controller.trade;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.trade.SubnetTradeRecommendService;
import com.zz91.ep.admin.service.trade.TradeRecommendService;
import com.zz91.ep.domain.trade.TradeRecommend;
import com.zz91.ep.dto.ExtResult;

/**
 * @author qizj
 * @email qizj@zz91.net
 * @version 创建时间：2012-3-7
 */
@Controller
public class TradeRecommendController extends BaseController {

	@Resource
	private TradeRecommendService tradeRecommendService;
	@Resource
	private SubnetTradeRecommendService subnetTradeRecommendService;

	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request) {
		return null;
	}

	@RequestMapping
	public ModelAndView recommendTrade(Map<String, Object> out,
			HttpServletRequest request, Integer cid, Integer targetId, Short type) {
		ExtResult result = new ExtResult();
		TradeRecommend recommend = new TradeRecommend();
		recommend.setCid(cid);
		recommend.setTargetId(targetId);
		recommend.setType(type);
		Integer i=tradeRecommendService.createRecommend(recommend);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView cancelRecommend(Map<String, Object> out,
			HttpServletRequest request, Integer targetId, Integer rid){
		ExtResult result = new ExtResult();
		Integer i=tradeRecommendService.cancelRecommend(targetId, rid);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	/**
	 * 子网推荐产品
	 * @return
	 */
	@RequestMapping
	public ModelAndView subTradeRecommend(Map<String, Object> out,HttpServletRequest request,Integer id,String type){
		ExtResult result = new ExtResult();
		Integer i=subnetTradeRecommendService.createSubnetTradeRecommend(id,type);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	/**
	 * 删除推荐产品信息
	 * @return
	 */
	@RequestMapping
	public ModelAndView cancelSubnetRecommend(Map<String, Object> out,HttpServletRequest request,Integer id){
		ExtResult result = new ExtResult();
		Integer i=subnetTradeRecommendService.deleteSubnetTradeRecommend(id);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}

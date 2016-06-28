//package com.zz91.ep.admin.controller.data;
//
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.solr.client.solrj.SolrServerException;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.zz91.ep.admin.controller.BaseController;
//import com.zz91.ep.admin.service.data.CompProfileDataService;
//import com.zz91.ep.admin.service.data.TradeBuyDataService;
//import com.zz91.ep.admin.service.data.TradeSupplyDataService;
//import com.zz91.ep.admin.service.data.impl.CompProfileDataServiceImpl;
//import com.zz91.ep.admin.service.data.impl.TradeSupplyDataServiceImpl;
//import com.zz91.ep.dto.ExtResult;
//import com.zz91.util.auth.AuthUtils;
//import com.zz91.util.auth.SessionUser;
//
///**
// * @author root
// * 
// */
//@Controller
//public class BaiduController extends BaseController{
//	@Resource
//	private CompProfileDataService compProfileDataService;
//	@Resource
//	private TradeSupplyDataService tradeSupplyDataService;
//	@Resource
//	private TradeBuyDataService tradeBuyDataService;
//	
//	@RequestMapping
//	public ModelAndView del(Map<String, Object> out,
//			HttpServletRequest request) {
//
//		return null;
//	}
//	
//	@RequestMapping
//	public ModelAndView updateData(HttpServletRequest request, Map<String, Object> out, 
//			String deltype,String keywords) throws SolrServerException{
//		ExtResult result=new ExtResult();
//		result.setSuccess(false);
//		Integer exeCount=0;
//		
//		if(deltype.equals("company")){
//			exeCount = compProfileDataService.updateCompByKeywords(keywords);
//			
//		}else if(deltype.equals("supply")){
//			SessionUser sessionUser = AuthUtils.getInstance().getSessionUser(request, null);
//			exeCount = tradeSupplyDataService.updateSupplyByKeywords(keywords,sessionUser.getAccount());
//		}
//		else if(deltype.equals("buy")){
//			SessionUser sessionUser = AuthUtils.getInstance().getSessionUser(request, null);
//			exeCount = tradeBuyDataService.updateBuyByKeywords(keywords,sessionUser.getAccount());
//		}
//		
//		if(exeCount>0) {
//			result.setSuccess(true);
//			result.setData(exeCount);
//		}
//		
//		return printJson(result, out);
//	}
//}

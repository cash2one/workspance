/**
 * @author shiqp
 * @date 2015-06-19
 * 企业自主报价后台管理 
 */
package com.ast.ast1949.web.controller.zz91.jiage;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.price.PriceOffer;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.price.PriceOfferService;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;

@Controller
public class JiageController extends BaseController{
	@Resource
	private PriceOfferService priceOfferService;
	@Resource
	private CompanyService companyService;
	@RequestMapping
	public void index(Map<String, Object> out,Integer companyId) {
		out.put("companyId", companyId);
	}
	@RequestMapping
	public ModelAndView queryPriceOffer(Map<String, Object> model,HttpServletRequest request, PageDto<PriceOffer> page, PriceOffer offer,String from,String to,String menberShip) throws IOException, ParseException {
		if(offer.getCheckStatus()!=null&&offer.getCheckStatus()==3){
			offer.setCheckStatus(null);
			offer.setIsDel(1);
		}
		if(offer.getCheckStatus()!=null&&offer.getCheckStatus()==4){
			offer.setCheckStatus(null);
		}
		if(StringUtils.isNotEmpty(from)){
			from = DateUtil.toString(DateUtil.getDate(from, "yyyy-MM-dd"), "yyyy-MM-dd");
		}
		if(StringUtils.isNotEmpty(to)){
			to = DateUtil.toString(DateUtil.getDateAfterDays(DateUtil.getDate(to, "yyyy-MM-dd"), 1), "yyyy-MM-dd");
		}
		page = priceOfferService.pageOfferByCondition(page, offer, from, to, menberShip);
		return printJson(page, model);
	}
	@RequestMapping
	public ModelAndView checkPriceOffer(Map<String, Object> model,HttpServletRequest request,Integer checkStatus,Integer offerId,String checkReason) throws IOException {
		ExtResult result = new ExtResult();
		SessionUser sessionUser = getCachedUser(request);
		String checkPerson = sessionUser.getAccount();
		Integer i = priceOfferService.updateCheckInfo(offerId, checkStatus, checkPerson, checkReason,null);
		if(i > 0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}
	
	@RequestMapping
	public ModelAndView delPriceOffer(Map<String, Object> model,HttpServletRequest request,Integer offerId) throws IOException {
		ExtResult result = new ExtResult();
		SessionUser sessionUser = getCachedUser(request);
		String checkPerson = sessionUser.getAccount();
		Integer i = priceOfferService.updateCheckInfo(offerId, null, checkPerson, null,1);
		if(i > 0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}
	
	@RequestMapping
	public ModelAndView edit(Map<String, Object> model,HttpServletRequest request,Integer offerId){
		model.put("productId", 1483885);
		model.put("offerId", offerId);
		return null;
	}
	
	@RequestMapping
	public ModelAndView init(Map<String, Object> model,HttpServletRequest request,Integer offerId) throws IOException{
		PriceOffer offer = priceOfferService.queryOfferById(offerId);
		if(offer!=null && offer.getCompanyId()!=null){
			Company company = companyService.queryCompanyById(offer.getCompanyId());
			if(company!=null){
				offer.setCompany(company);
			}
		}
		List<PriceOffer> list = new ArrayList<PriceOffer>();
		list.add(offer);
		return printJson(list, model);
	}
	@RequestMapping
	public ModelAndView detail(Map<String, Object> model,HttpServletRequest request,Integer companyId) throws IOException{
		model.put("companyId", companyId);
		return null;
	}
	
	@RequestMapping
	public ModelAndView modifiedOffer(Map<String, Object> model,HttpServletRequest request,PriceOffer offer) throws IOException{
		ExtResult result = new ExtResult();
		Integer i = 0;
		SessionUser sessionUser = getCachedUser(request);
		if(sessionUser!=null){
			offer.setCheckPerson(sessionUser.getAccount());
		}
		i = priceOfferService.updateOfferById(offer);
		if(i > 0){
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
		}
		return printJson(result, model);
	}
	
	
}

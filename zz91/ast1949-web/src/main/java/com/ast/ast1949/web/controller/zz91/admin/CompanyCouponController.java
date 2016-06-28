package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyCoupon;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyCouponDto;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyCouponService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.sms.SmsUtil;
@Controller
public class CompanyCouponController extends BaseController {
	@Resource
	private CompanyCouponService companyCouponService;
	@Resource
	private CompanyAccountService companyAccountService;
	
	
	@RequestMapping
	public ModelAndView index(Map<String, Object>out,HttpServletRequest request){
		return null;
	}
	@RequestMapping
	public ModelAndView queryCompanyCoupon(Map<String, Object>out,PageDto<CompanyCouponDto> page,String email) throws IOException{
		page=companyCouponService.queryCompanyCoupon(email,page);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView queryCompanyCouponById(Map<String, Object>out, Integer id,PageDto<CompanyCoupon> page) throws IOException{
		List<CompanyCoupon> list=new ArrayList<CompanyCoupon>();
		if(id!=null&&id.intValue()>0){
			CompanyCoupon companyCoupon=companyCouponService.queryCompanyCouponById(id);
			list.add(companyCoupon);
		}
		page.setRecords(list);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView updateCompanyCoupon(Map<String, Object>out,CompanyCoupon companyCoupon) throws IOException{
		ExtResult result=new ExtResult();
		Integer j=0;
		if(companyCoupon!=null&&companyCoupon.getId()!=null){
			 j=companyCouponService.updateCompanyCoupon(companyCoupon);
		}
		if(j!=null&&j.intValue()>0){
			result.setSuccess(true);
		}else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView openCoupon(Map<String, Object>out,Integer id) throws IOException{
		ExtResult result=new ExtResult();
		do {
			Integer i = companyCouponService.openCoupon(id);
			if (i>0) {
				result.setSuccess(true);
				CompanyCoupon cp =  companyCouponService.queryCompanyCouponById(id);
				if (cp==null||cp.reducePrice==null) {
					break;
				}
				CompanyAccount ca = companyAccountService.queryAccountByCompanyId(cp.getCompanyId());
				if (ca==null||StringUtils.isEmpty(ca.getMobile())) {
					break;
				}
				if (cp.reducePrice==500) {
					SmsUtil.getInstance().sendSms("zz91_s11_dj", ca.getMobile(), null, null,new String[]{cp.getCode()});
				}else{
					SmsUtil.getInstance().sendSms("zz91_s11_yh", ca.getMobile(), null, null,new String[]{cp.getCode()});
				}
			}
		} while (false);
		return printJson(result, out);
	}

}

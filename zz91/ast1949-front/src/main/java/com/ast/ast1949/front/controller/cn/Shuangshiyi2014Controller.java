/**
 * @author kongsj
 * @date 2014年11月4日
 * 
 */
package com.ast.ast1949.front.controller.cn;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyCoupon;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.front.controller.BaseController;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyCouponService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.sms.SmsUtil;

@Controller
public class Shuangshiyi2014Controller extends BaseController{
	
	@Resource
	private CompanyCouponService companyCouponService;
	@Resource
	private CompanyAccountService companyAccountService;
	final static Map<Integer, Integer> COUPON_PRICE_MAP = new  HashMap<Integer, Integer>();
	final static Map<Integer, String> NAME_MAP = new  HashMap<Integer, String>();
	final static Map<Integer, Integer> ORIGINAL_PRICE_MAP = new  HashMap<Integer, Integer>();
	//原始价格
	static {
		ORIGINAL_PRICE_MAP.put(1, 1500);
		ORIGINAL_PRICE_MAP.put(2, 3500);
		ORIGINAL_PRICE_MAP.put(3, 7000);
		ORIGINAL_PRICE_MAP.put(4, 5880);
		ORIGINAL_PRICE_MAP.put(5, 6000);
	}
	//优惠价格
	static {
		COUPON_PRICE_MAP.put(1, 1000);
		COUPON_PRICE_MAP.put(2, 2658);
		COUPON_PRICE_MAP.put(3, 3288);
		COUPON_PRICE_MAP.put(4, 3988);
		COUPON_PRICE_MAP.put(5, 1880);
	}
	//服务名称
	static {
		NAME_MAP.put(1, "来电宝服务");
		NAME_MAP.put(2, "再生通一年");
		NAME_MAP.put(3, "再生通两年");
		NAME_MAP.put(4, "金牌通服务");
		NAME_MAP.put(5, "黄金广告展位服务");
	}
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,HttpServletRequest request) throws ParseException{
		
		do {
			// 登陆获取 companyid 验证是否可以领优惠券
			Date now=new Date();
			
			// 页面跳转 
//			long l = 1415635200000l;
//			if (now.getTime()>l) {
//				return new ModelAndView("redirect:fday.htm");
//			}
//			long nowtime=DateUtil.getMillis(DateUtil.getDate(now, "yyyy-mm-dd"));
//			long basetime=DateUtil.getMillis(DateUtil.getDate("2014-11-11 00:00:00", "yyyy-mm-dd"));
//			if(nowtime>=basetime){
//				out.put("expireFlag", 1);
//				out.put("haveMark", 0);
//				break;
//			}
			SsoUser ssoUser = getCachedUser(request);
			if (ssoUser==null||ssoUser.getCompanyId()==null) {
				break;
			}
			Integer haveMark = companyCouponService.validateCouponHaveOrNot(ssoUser.getCompanyId());
			out.put("haveMark", haveMark);
			out.put("expireFlag", 0);
		} while (false);
		
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView getOneCoupon(Map<String, Object> out,HttpServletRequest request,Integer serviceId) throws IOException{
		ExtResult result = new ExtResult();
		do {
			// 是否登陆 登陆则获取优惠券
			SsoUser ssoUser = getCachedUser(request);
			if (ssoUser==null||ssoUser.getCompanyId()==null) {
				break;
			}
			Integer i = companyCouponService.createOneCoupon(ssoUser.getCompanyId(), serviceId);
			if (i>0) {
				result.setData(serviceId);
				result.setSuccess(true);
				CompanyAccount ca = companyAccountService.queryAccountByCompanyId(ssoUser.getCompanyId());
				// 您已成功领取ZZ91双11{0}优惠券，价值{1}元，双11当天即可登录zz91.COM使用。
				SmsUtil.getInstance().sendSms("sms_youhuiquan", ca.getMobile(), null, new Date(), new String[]{CompanyCouponService.COUPON_NAME_MAP.get(serviceId),""+CompanyCouponService.REDUCE_MAP.get(serviceId)});
			}
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView fday(Map<String, Object> out,HttpServletRequest request,String result){
		Date now=new Date();
		long l = 1415721600000l;
		do {
			// 登陆获取 companyid 验证是否可以领优惠券
			SsoUser ssoUser = getCachedUser(request);
			out.put("result", result);
		
			if (ssoUser==null||ssoUser.getCompanyId()==null) {
				break;
			}
			if (now.getTime()<l) {
				Integer haveMark = companyCouponService.validateCouponHaveOrNot(ssoUser.getCompanyId());
				out.put("haveMark", haveMark);
			}
			List<CompanyCoupon> list =companyCouponService.queryCouponByCompanyId(ssoUser.getCompanyId());
			for (CompanyCoupon obj : list) {
				if (obj.status==3) {
					out.put("result", 1);
				}
			}
		} while (false);
		if (now.getTime()>l) {
			out.put("haveMark", 0);
		}
		return new ModelAndView();
	}
	
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(date.getTime());
	}
	/**
	 * 判断这个客户是否有这个服务的优惠劵
	 * @param out
	 * @param request
	 * @param codeId 优惠劵服务id 1：来电宝 2:再生通一年 3:再生通二年 4:金牌通 5:广告服务
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView yzCoupon(Map<String, Object>out,HttpServletRequest request, String codeId) throws IOException{
		ExtResult result=new ExtResult();
		SsoUser ssoUser=getCachedUser(request);
		do{
			Date now=new Date();
			long l = 1415721600000l;
			if (now.getTime()>l) {
				result.setSuccess(false);
				break;
			}
			if (ssoUser==null||ssoUser.getCompanyId()==null) {
				result.setSuccess(false);
				break;
			}
			if(codeId==null){
				result.setSuccess(false);
				break;
			}	
			Integer id=Integer.valueOf(codeId);
			CompanyCoupon companyCoupon=companyCouponService.yzCoupon(id, ssoUser.getCompanyId());
			if (companyCoupon!=null&&companyCoupon.getCode()!=null) {
					String[] coupon = companyCoupon.getCode().split("-");
					Map<String, Object>map=new HashMap<String, Object>();
			        map.put("id", id);
					map.put("code", coupon[4]);
					result.setData(map);
					result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		}while(false);
		
		return printJson(result, out);
	}
	/**
	 * 验证这个客户的优惠劵码
	 * @param out
	 * @param request
	 * @param code	优惠劵码
	 * @param codeId  优惠劵服务id 1：来电宝 2:再生通一年 3:再生通二年 4:金牌通 5:广告服务
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView yzCounterCode(Map<String, Object>out,HttpServletRequest request,String code,String codeId) throws IOException{
		ExtResult result=new ExtResult();
		SsoUser ssoUser=getCachedUser(request);
		Map<String, Object>map=new HashMap<String, Object>();
		do{
			Date now=new Date();
			long l = 1415721600000l;
			if(codeId==null&&code==null){
				result.setSuccess(false);
				break;
			}
			Integer id=Integer.valueOf(codeId);
			if (now.getTime()>l) {
				map.put("id", id);
				//  originalPrice :1  表示是原始价格  0: 表示优惠价格
				map.put("originalPrice", 1);
				result.setData(map);
				result.setSuccess(true);
				break;
			}
			if (ssoUser==null||ssoUser.getCompanyId()==null) {
				result.setSuccess(false);
				break;
			}
			
			CompanyCoupon companyCoupon=companyCouponService.yzCoupon(id, ssoUser.getCompanyId());
			if (companyCoupon==null||companyCoupon.getCode()==null) {
				result.setSuccess(false);
				break;
			}
			String[] coupon=companyCoupon.getCode().split("-");
			if(code.equals(coupon[4])){
				// originalPrice :1  表示是原始价格  0: 表示优惠价格
				map.put("originalPrice", 0);
				map.put("id", id);
				result.setData(map);
				result.setSuccess(true);
			}else {
				result.setSuccess(false);
			}
			
			
		}while(false);
		return printJson(result, out);
	}
	/**
	 * 判断这个客户的所有优惠劵
	 * @param out
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryCoupon(Map<String, Object>out,HttpServletRequest request) throws IOException{
		ExtResult result=new ExtResult();
		SsoUser ssoUser=getCachedUser(request);
		do {
			Date now=new Date();
			long l = 1415721600000l;
			if (now.getTime()>l) {
				result.setSuccess(false);
				break;
			}
			if (ssoUser==null||ssoUser.getCompanyId()==null) {
				result.setSuccess(false);
				break;
			}
			List<CompanyCoupon>	list=companyCouponService.queryCouponByCompanyId(ssoUser.getCompanyId());
			if(list.size()<=0){
				result.setSuccess(false);
				break;
			}
			List<String> list2=new ArrayList<String>();
				for(CompanyCoupon companyCoupon :list){
					if(companyCoupon!=null&&companyCoupon.getServiceName()!=null){
						if(companyCoupon.getServiceName().equals("来电宝优惠券")){
							list2.add("1");
						}
						if(companyCoupon.getServiceName().equals("再生通一年优惠券")){
							list2.add("2");
						}
						if(companyCoupon.getServiceName().equals("再生通两年优惠券")){
							list2.add("3");
						}
						if(companyCoupon.getServiceName().equals("金牌通优惠券")){
							list2.add("4");
						}
					}
				}
			result.setData(list2);
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView egoldPay(Map<String, Object>out,HttpServletRequest request ,String codeId,String code) throws IOException, ParseException{
		SsoUser ssoUser=getCachedUser(request);
		Date now=new Date();
		long l = 1415721600000l;
		do{
			if(ssoUser==null||ssoUser.getCompanyId()==null){
				break;
			}
			if(codeId!=null){
				Integer id=Integer.valueOf(codeId);
				Integer price=0;
				if (now.getTime()>l) {
					 price=ORIGINAL_PRICE_MAP.get(id);
					 if(price==null||price.intValue()<=0){
							break;
					 }
				}else {
					 price=COUPON_PRICE_MAP.get(id);
					 if(price==null||price.intValue()<=0){
							break;
					 }
				     if(StringUtils.isNotEmpty(code)){
				    	 	CompanyCoupon companyCoupon=companyCouponService.yzCoupon(id, ssoUser.getCompanyId());
				    	 	if(companyCoupon!=null&&companyCoupon.getReducePrice()!=null){
				    	 		String[] coupon = companyCoupon.getCode().split("-");
				    	 		if(code.equals(coupon[4])){
				    	 			price=price-companyCoupon.getReducePrice();
				    	 		}
				    	 		companyCoupon.setStatus(4);
				    	 		companyCouponService.updateCompanyCoupon(companyCoupon);
				    	 	}
					}else{
						companyCouponService.createCompanyCouponByStatus(ssoUser.getCompanyId(), id);
					}
				}
				
				CompanyAccount companyAccount=companyAccountService.queryAccountByCompanyId(ssoUser.getCompanyId());
				if(companyAccount!=null){
					out.put("mobile", companyAccount.getMobile());
				}
				out.put("companyId", ssoUser.getCompanyId());
				out.put("id", id);
				out.put("price", price);
				out.put("serviceName",NAME_MAP.get(id));
				return new ModelAndView();
			}
		
		}while(false);
		return new ModelAndView("redirect:fday.htm");
	}
	@RequestMapping
	public ModelAndView payBack(Map<String, Object>out,HttpServletRequest request,HttpServletResponse response){
		SsoUser ssoUser=getCachedUser(request);
		if(ssoUser==null||ssoUser.getCompanyId()==null){
			return new ModelAndView("redirect:fday.htm?result=1");
		}
		List<CompanyCoupon> list = companyCouponService.queryCouponByCompanyId(ssoUser.getCompanyId());
		for (CompanyCoupon obj : list) {
			if (obj.type==1) {
				obj.setStatus(3);
				companyCouponService.updateCompanyCoupon(obj);
				break;
			}
		}
		return new ModelAndView("redirect:fday.htm?result=1");
	}
}

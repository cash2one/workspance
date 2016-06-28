package com.ast.ast1949.front.controller.cn;

import java.io.IOException;
import java.text.ParseException;
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
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.sms.SmsUtil;

@Controller
public class ShuangshiyiController extends BaseController{
	
	final static long START_DATE = 1447171200000l; //2015.11.11当天时间戳 
	final static String YH_FIX = "YH9685"; //优惠码前缀
	final static String DJ_FIX = "DJ8058"; //优惠码前缀
	final static Map<Integer, Integer> PRICE_MAP = new HashMap<Integer, Integer>();
	
	
	@Resource
	private CompanyCouponService companyCouponService;
	@Resource
	private CompanyAccountService companyAccountService;
	
	static {
		// 服务名称
		CompanyCouponService.COUPON_NAME_MAP.put(1, "再生通一年");
		CompanyCouponService.COUPON_NAME_MAP.put(2, "再生通两年");
		CompanyCouponService.COUPON_NAME_MAP.put(3, "金牌品牌通");
		CompanyCouponService.COUPON_NAME_MAP.put(4, "钻石品牌通");
		CompanyCouponService.COUPON_NAME_MAP.put(5, "预付订金再生通一年");
		CompanyCouponService.COUPON_NAME_MAP.put(6, "预付订金再生通两年");
		CompanyCouponService.COUPON_NAME_MAP.put(7, "预付订金金牌品牌通");
		CompanyCouponService.COUPON_NAME_MAP.put(8, "预付订金钻石品牌通");
		
		// 优惠额度
		CompanyCouponService.REDUCE_MAP.put(1, 49);
		CompanyCouponService.REDUCE_MAP.put(2, 99);
		CompanyCouponService.REDUCE_MAP.put(3, 149);
		CompanyCouponService.REDUCE_MAP.put(4, 199);
		CompanyCouponService.REDUCE_MAP.put(5, 500); //预付订金
		CompanyCouponService.REDUCE_MAP.put(6, 500); //预付订金
		CompanyCouponService.REDUCE_MAP.put(7, 500); //预付订金
		CompanyCouponService.REDUCE_MAP.put(8, 500); //预付订金
		
		
		PRICE_MAP.put(1, 2999);
		PRICE_MAP.put(2, 3699);
		PRICE_MAP.put(3, 4399);
		PRICE_MAP.put(4, 7999);
		PRICE_MAP.put(5, 100);
		PRICE_MAP.put(6, 100);
		PRICE_MAP.put(7, 100);
		PRICE_MAP.put(8, 100);
	}
	
	/**
	 * 双十一首页
	 * @param out
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,String date) throws ParseException{
		// 手动设置时间为 双十一当天
		if (StringUtils.isNotEmpty(date)&&DateUtil.getDate(date, "yyyy-MM-dd").getTime()>=1447171200000l) {
			return new ModelAndView("redirect:fday.htm");
		}
		
		// 双十一当天 
		if (new Date().getTime()>=1447171200000l) {
			return new ModelAndView("redirect:fday.htm");
		}
		
		return new ModelAndView();
	}
	
	/**
	 * 领优惠券 
	 * @param out
	 * @param request
	 * @param serviceId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getCoupon(Map<String, Object> out,HttpServletRequest request,Integer serviceId) throws IOException{
		ExtResult result = new ExtResult();
		do {
			SsoUser ssoUser = getCachedUser(request);
			if (ssoUser==null) {
				break;
			}
			CompanyCoupon companyCoupon =new CompanyCoupon();
			companyCoupon.setCompanyId(ssoUser.getCompanyId());
			companyCoupon.setServiceName(CompanyCouponService.COUPON_NAME_MAP.get(serviceId));
			companyCoupon.setReducePrice(CompanyCouponService.REDUCE_MAP.get(serviceId));
			try {
				companyCoupon.setGmtEnd(DateUtil.getDate("2015-11-12", "yyyy-MM-dd"));
			} catch (ParseException e) {
				companyCoupon.setGmtEnd(new Date());
			}
			try {
				companyCoupon.setGmtStart(DateUtil.getDate("2015-11-11", "yyyy-MM-dd"));
			} catch (ParseException e) {
				companyCoupon.setGmtStart(new Date());
			}
			if(serviceId>0&&serviceId<5){
				companyCoupon.setCode(YH_FIX + getRandomNumb());;
			}
			companyCoupon.setType(1);
			companyCoupon.setStatus(CompanyCouponService.STATUS_OPEN);
			
			Integer i = companyCouponService.createOneCoupon(companyCoupon);
			if (i>0) {
				result.setData(serviceId);
				result.setSuccess(true);
				CompanyAccount ca = companyAccountService.queryAccountByCompanyId(ssoUser.getCompanyId());
				//您已成功领取ZZ91双11{0}优惠券，价值{1}元，双11当天即可登录zz91.COM使用。
				SmsUtil.getInstance().sendSms("zz91_s11_yh", ca.getMobile(), null, new Date(), new String[]{companyCoupon.getCode()});
			}
		} while (false);
		return printJson(result, out);
	}
	
	private String getRandomNumb(){
		String str ="";
		for (int i = 0; i < 3; i++) {
			str = str + Double.valueOf(Math.random()*10).intValue();
		}
		return str;
	}
	
	@RequestMapping
	public ModelAndView egoldPay(Map<String, Object>out,HttpServletRequest request ,String codeId) throws IOException, ParseException{
		SsoUser ssoUser=getCachedUser(request);
		Date now=new Date();
		do{
			if(ssoUser==null||ssoUser.getCompanyId()==null){
				break;
			}
			if (now.getTime()>START_DATE) {
				break;
			}
			
			if(codeId!=null){
				Integer id=Integer.valueOf(codeId);
				CompanyCoupon companyCoupon =new CompanyCoupon();
				companyCoupon.setCompanyId(ssoUser.getCompanyId());
				companyCoupon.setServiceName(CompanyCouponService.COUPON_NAME_MAP.get(id));
				companyCoupon.setReducePrice(CompanyCouponService.REDUCE_MAP.get(id));
				try {
					companyCoupon.setGmtEnd(DateUtil.getDate("2015-11-12", "yyyy-MM-dd"));
				} catch (ParseException e) {
					companyCoupon.setGmtEnd(new Date());
				}
				try {
					companyCoupon.setGmtStart(DateUtil.getDate("2015-11-11", "yyyy-MM-dd"));
				} catch (ParseException e) {
					companyCoupon.setGmtStart(new Date());
				}
				companyCoupon.setCode(DJ_FIX + getRandomNumb());;
				companyCoupon.setType(1);
				companyCoupon.setStatus(CompanyCouponService.STATUS_ING);
				
				Integer i = companyCouponService.createOneCoupon(companyCoupon);
				if (i==null||i<=0) {
					break;
				}
				out.put("companyId", ssoUser.getCompanyId());
				out.put("id", id);
				out.put("price", PRICE_MAP.get(id));
				out.put("serviceName",CompanyCouponService.COUPON_NAME_MAP.get(id));
				return new ModelAndView("redirect:http://about.zz91.com/pay.html?paytype=8&name="+StringUtils.encryptUrlParameter(CompanyCouponService.COUPON_NAME_MAP.get(id))+"&money=100");
			}
		
		}while(false);
		// 付款订单创建失败。判断是否有未付款订金
		List<CompanyCoupon> list = companyCouponService.queryForMyrc(ssoUser.getCompanyId());
		out.put("result",1);
		for (CompanyCoupon obj : list) {
			if (CompanyCouponService.STATUS_ING.equals(obj.getStatus())) {
				out.put("result",0);
				out.put("companyId", ssoUser.getCompanyId());
//				out.put("id", id);
				out.put("price", 100);
				out.put("serviceName",obj.getServiceName());
				break;
			}
		}
		return new ModelAndView("redirect:http://about.zz91.com/pay.html?paytype=8&name="+StringUtils.encryptUrlParameter(out.get("serviceName").toString())+"&money=100");
	}
	
	@RequestMapping
	public ModelAndView miaosha(Map<String, Object> out) throws ParseException {
//		if (StringUtils.isEmpty(date)) {
//			date = "2015-11-02";
//		}
		Date d = DateUtil.getDate(new Date(), "yyyy-MM-dd");
		out.put("today", DateUtil.toString(d, "yyyy-MM-dd") );
		out.put("year", DateUtil.toString(d, "yyyy") );
		out.put("month", DateUtil.toString(d, "MM") );
		out.put("day", DateUtil.toString(d, "dd") );
		out.put("hour", DateUtil.toString(new Date(), "HH") );
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView mobile_index(Map<String, Object> out,String date) throws ParseException{
		// 手动设置时间为 双十一当天
		if (StringUtils.isNotEmpty(date)&&DateUtil.getDate(date, "yyyy-MM-dd").getTime()>=1447171200000l) {
			return new ModelAndView("redirect:mobile_fday.htm");
		}
		
		// 双十一当天 
		if (new Date().getTime()>=1447171200000l) {
			return new ModelAndView("redirect:mobile_fday.htm");
		}
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView mobile_miaosha(Map<String, Object> out){
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView mobile_fday(Map<String, Object> out){
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView doLogin(Map<String, Object> out,HttpServletRequest request,HttpServletResponse response) throws IOException{
		ExtResult result = new ExtResult();
		String username = request.getParameter("account");
		String password = request.getParameter("password");
		String serviceStr = request.getParameter("serviceId");
		do {
			
			SsoUser ssoUser = null;
			if (StringUtils.isEmpty(password)) {
				out.put("error", AuthorizeException.getMessage(AuthorizeException.NEED_PASS));
				break;
			}

			try {
				ssoUser = SsoUtils.getInstance().validateUser(response,username, password, null,HttpUtils.getInstance().getIpAddr(request));
			} catch (Exception e) {
				break;
			}

			if (ssoUser != null) {
				//登录日志
				LogUtil.getInstance().log(""+ssoUser.getCompanyId(), "login", HttpUtils.getInstance().getIpAddr(request),"type:'s11-login';account:'"+ssoUser.getAccount()+"'"); // 登记登陆信息
				setSessionUser(request, ssoUser);
				
				CompanyCoupon companyCoupon =new CompanyCoupon();
				companyCoupon.setCompanyId(ssoUser.getCompanyId());
				try {
					companyCoupon.setGmtEnd(DateUtil.getDate("2015-11-12", "yyyy-MM-dd"));
				} catch (ParseException e) {
					companyCoupon.setGmtEnd(new Date());
				}
				try {
					companyCoupon.setGmtStart(DateUtil.getDate("2015-11-11", "yyyy-MM-dd"));
				} catch (ParseException e) {
					companyCoupon.setGmtStart(new Date());
				}
				result.setSuccess(true);
				if (!StringUtils.isNumber(serviceStr)) {
					break;
				}
				Integer serviceId = Integer.valueOf(serviceStr);
				if(serviceId>0&&serviceId<5){
					companyCoupon.setCode(YH_FIX + getRandomNumb());
					companyCoupon.setStatus(CompanyCouponService.STATUS_OPEN);
				}else{
					companyCoupon.setCode(DJ_FIX + getRandomNumb());
					companyCoupon.setStatus(CompanyCouponService.STATUS_ING);
					result.setData(1);
				}
				companyCoupon.setServiceName(CompanyCouponService.COUPON_NAME_MAP.get(serviceId));
				companyCoupon.setReducePrice(CompanyCouponService.REDUCE_MAP.get(serviceId));
				companyCoupon.setType(1);
				
				Map<String, Object> resultMap = new HashMap<String, Object>();
				Integer i = companyCouponService.createOneCoupon(companyCoupon);
				if (i>0) {
					// 领券成功
					resultMap.put("getFlag", 1);
					CompanyAccount ca = companyAccountService.queryAccountByCompanyId(ssoUser.getCompanyId());
					//您已成功领取ZZ91双11{0}优惠券，价值{1}元，双11当天即可登录zz91.COM使用。
					SmsUtil.getInstance().sendSms("zz91_s11_yh", ca.getMobile(), null, new Date(), new String[]{companyCoupon.getCode()});
				}else{
					// 领券失败
					resultMap.put("getFlag", 0);
				}
				result.setData(resultMap);
			}
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView fday(Map<String, Object> out){
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView validateCode(String djCode,String yhCode,Map<String, Object> out,HttpServletRequest request,Integer serviceId) throws IOException{
		ExtResult result = new ExtResult();
		do {
			boolean flag = false;
			SsoUser ssoUser = getCachedUser(request);
			if (ssoUser==null) {
				break;
			}
			Integer resultMoney = PRICE_MAP.get(serviceId);
			String name = CompanyCouponService.COUPON_NAME_MAP.get(serviceId);
			CompanyCoupon djCoupon = companyCouponService.getCouponByCode(ssoUser.getCompanyId(), djCode);
			if (djCoupon!=null&&djCoupon.getServiceName().indexOf(name)!=-1) {
				resultMoney = resultMoney - djCoupon.getReducePrice();
				flag= true;
			}
			CompanyCoupon yhCoupon = companyCouponService.getCouponByCode(ssoUser.getCompanyId(), yhCode);
			if (yhCoupon!=null&&yhCoupon.getServiceName().indexOf(name)!=-1) {
				resultMoney = resultMoney - yhCoupon.getReducePrice();
				flag= true;
			}
			result.setSuccess(true);
			Map<String , Object> map =new HashMap<String, Object>();
			map.put("flag", flag);
			map.put("money", resultMoney);
			result.setData(map);
		} while (false);
		return printJson(result, out);
	}

}

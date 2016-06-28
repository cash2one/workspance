/**
 * 
 */
package com.ast.ast1949.web.controller.zz91;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneCostSvr;
import com.ast.ast1949.service.phone.PhoneCostSvrService;
import com.ast.ast1949.service.phone.PhoneCsBsService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthConst;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;

/**
 * @author root
 *
 */
@Controller
public class SaleCrmController extends BaseController{

	final static String SALE_ACCOUNT="adminsale";
	final static String SALE_ACCOUNT_PASSWORD="zj88friend";
	
	@Resource
	private PhoneCsBsService phoneCsBsService;
	@Resource
	private PhoneCostSvrService phoneCostSvrService;
	@Resource
	private PhoneService phoneService;
	
	@RequestMapping
	public ModelAndView viewInfo(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out, Integer companyId){
		
		if(companyId==null||companyId.intValue()<=0){
			return new ModelAndView("/zz91/salecrm/viewError");
		}
		
		SessionUser user=getCachedUser(request);
		if(user==null){
			user = AuthUtils.getInstance().validateUser(response, SALE_ACCOUNT, SALE_ACCOUNT_PASSWORD, AuthConst.PROJECT_CODE, AuthConst.PROJECT_PASSWORD);
			setSessionUser(request, user);
		}
		out.put("companyId", companyId);
		
		Phone phone = phoneService.queryByCompanyId(companyId);
//		PhoneCsBs phoneCsBs = phoneCsBsService.queryByCompanyId(companyId);
		// 已经过期的来电宝用户
		do {
			if (phone==null) {
				break;
			}
			PhoneCostSvr pcs = phoneCostSvrService.queryGmtZeroByCompanyId(companyId);
			if((phone.getExpireFlag()!=null&&phone.getExpireFlag().intValue()==1)||"信息已过期".equals(phone.getFrontTel())){
				if (pcs!=null&&pcs.getGmtZero()!=null) {
					out.put("bsText", "余额为零日期为:"+DateUtil.toString(pcs.getGmtZero(), "yyyy-MM-dd"));
				}
			}
		} while (false);
		
		return new ModelAndView();
	}
	
}

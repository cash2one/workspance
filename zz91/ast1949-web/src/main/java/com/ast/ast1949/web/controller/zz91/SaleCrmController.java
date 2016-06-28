/**
 * 
 */
package com.ast.ast1949.web.controller.zz91;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthConst;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;

/**
 * @author root
 *
 */
@Controller
public class SaleCrmController extends BaseController{

	final static String SALE_ACCOUNT="adminsale";
	final static String SALE_ACCOUNT_PASSWORD="zj88friend";
	
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
		
		return new ModelAndView();
	}
	
}

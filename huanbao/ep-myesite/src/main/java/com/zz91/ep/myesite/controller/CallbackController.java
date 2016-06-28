/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-10-9
 */
package com.zz91.ep.myesite.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.dto.MailArga;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.auth.ep.EpAuthUtils;
import com.zz91.util.mail.MailUtil;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-10-9
 */
@Controller
public class CallbackController extends BaseController {

    @RequestMapping
    public ModelAndView login(Map<String, Object> out, HttpServletRequest request) {
    	EpAuthUser user = EpAuthUtils.getInstance().getEpAuthUser(request, null);
    	 if ( user!= null){
     		out.put("success",true);
     		out.put("data", user.getCid());
     	}
        return null;
    }
    
    @RequestMapping
    public ModelAndView email(Map<String, Object> out,HttpServletResponse response, HttpServletRequest request, 
    		String email, String username){
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("username", username);
    		long start=System.currentTimeMillis();
    		MailUtil.getInstance().sendMail(MailArga.TITLE_REGISTER_SUCCESS, email,  MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE,
    				MailArga.TEMPLATE_REGISTER_SUCCESS_CODE, map, MailUtil.PRIORITY_HEIGHT);
    		long end=System.currentTimeMillis();
    		System.out.println(">>>>>>>>>>>>EMAIL COST TIME:"+(end-start)+" TO:"+email+" USER:"+username);

    		//发送完善信息邮件
    		//MailUtil.getInstance().sendMail(MailArga.TITLE_COMPLETE_INFO, email,  MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE,
    		//		MailArga.TEMPLATE_COMPLETE_INFO, map, MailUtil.PRIORITY_HEIGHT);
    		
    	 return new ModelAndView();
    }
    
}
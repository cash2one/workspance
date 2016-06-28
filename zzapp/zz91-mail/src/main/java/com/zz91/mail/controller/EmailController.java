package com.zz91.mail.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.mail.domain.MailInfoDomain;
import com.zz91.mail.domain.dto.ExtResult;
import com.zz91.mail.service.MailSendService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Controller
public class EmailController extends BaseController {

	@Resource
	private MailSendService mailSendService;

    @SuppressWarnings("unchecked")
    @RequestMapping
	public ModelAndView send(HttpServletRequest request,
			Map<String, Object> out, MailInfoDomain mailInfoDomain, 
			String dataMap,String gmtPostStr) {
		ExtResult result = new ExtResult();
		Integer i = 0;
		do {
			if(StringUtils.isNotEmpty(gmtPostStr)){
			    try {
                    mailInfoDomain.setGmtPost(DateUtil.getDate(gmtPostStr, "yyyy-MM-dd HH:mm:ss"));
                } catch (ParseException e) {
                }
			}else{
			    mailInfoDomain.setGmtPost(new Date());
			}
			JSONObject jSONObject = null;
			Map<String,Object> map =new HashMap<String,Object>();
			if(StringUtils.isNotEmpty(dataMap) && dataMap.startsWith("{")){
			    jSONObject = JSONObject.fromObject(dataMap);
                Set<String> jbodykey=jSONObject.keySet();
                for(String outkey: jbodykey){
                    map.put(outkey, jSONObject.get(outkey));
                }
			    mailInfoDomain.setEmailParameter(map);
			}
			
			if(StringUtils.isNotEmpty(mailInfoDomain.getAccountCode())){
				i = mailSendService.sendEmailByCode(mailInfoDomain);
				break;
			}
			if(StringUtils.isNotEmpty(mailInfoDomain.getSendName()) 
					&& StringUtils.isEmpty(mailInfoDomain.getSendPassword())){
				i = mailSendService.sendEmailByUsename(mailInfoDomain);
				break;
			}
			
			i = mailSendService.sendEmail(mailInfoDomain); //系统默认
			
		} while (false);
		
		if (i!=null && i > 0) {
			result.setSuccess(true);
		}

		return printJson(result, out);
	}

}

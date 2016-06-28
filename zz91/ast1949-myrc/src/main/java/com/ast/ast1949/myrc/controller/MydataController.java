/**
 * @author shiqp
 * @date 2014-09-10
 */
package com.ast.ast1949.myrc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.analysis.AnalysisMyrcVisitor;
import com.ast.ast1949.domain.analysis.AnalysisMyrcVisitors;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.analysis.AnalysisMyrcVisitorService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.datetime.DateUtil;

@Controller
public class MydataController extends BaseController{
	@Resource
	private AnalysisMyrcVisitorService analysisMyrcVisitorService;
	@Resource
	private InquiryService inquiryService;
	@Resource
	private PhoneLogService phoneLogService;
	@Resource
	private CompanyService companyService;
	
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,HttpServletRequest request,String from,String to,Integer flag,Integer tag,String key,PageDto<AnalysisMyrcVisitor> page) throws Exception {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		out.put("sessionUser", sessionUser);
		page.setPageSize(20);
		page.setSort("gmt_target");
		page.setDir("desc");
		//昨日数据统计
		AnalysisMyrcVisitors visitor=analysisMyrcVisitorService.getVisitorsData(sessionUser.getCompanyId(),DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), "yyyy-MM-dd"),DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), "yyyy-MM-dd 23:59:59"));
		out.put("visitor", visitor);
		//flag为0，表示昨天；为1表示今天；为2表示最近7天；为3表示最近30天
		if(flag!=null){     	
        if(flag==1){
        	from=DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), "yyyy-MM-dd");
        	to=DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), "yyyy-MM-dd 23:59:59");
        }else if(flag==2){
        	from=DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -7), "yyyy-MM-dd");
        	to=DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
        }else if(flag==3){
        	from=DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -30), "yyyy-MM-dd");
        	to=DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
        }
		}else{
			if(StringUtils.isEmpty(from)||StringUtils.isEmpty(to) ||DateUtil.getDate(from, "yyyy-MM-dd").getTime()>DateUtil.getDate(to, "yyyy-MM-dd").getTime()){
				flag=1;
				from=DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), "yyyy-MM-dd");
	        	to=DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), "yyyy-MM-dd 23:59:59");
			}else{
				from=DateUtil.toString(DateUtil.getDate(from, "yyyy-MM-dd"), "yyyy-MM-dd");
	        	to=DateUtil.toString(DateUtil.getDate(to, "yyyy-MM-dd"), "yyyy-MM-dd 23:59:59");
			}
		}
		PageDto<AnalysisMyrcVisitor> pages=new PageDto<AnalysisMyrcVisitor>();
		
        Map<String, Object> map=analysisMyrcVisitorService.getPoint(sessionUser.getCompanyId(), from, to, sessionUser.getMembershipCode());
        pages.setPageSize(20);
		pages.setSort("gmt_target");
		pages.setDir("desc");
        if(tag==null){
        	page=analysisMyrcVisitorService.getVisitorList(sessionUser.getCompanyId(),from, to,0, page);
            pages=analysisMyrcVisitorService.getVisitorList(sessionUser.getCompanyId(),from, to,1, pages);
        }else if(tag==0){
        	page=analysisMyrcVisitorService.getVisitorList(sessionUser.getCompanyId(),from, to,0, page);
        }else{
            pages=analysisMyrcVisitorService.getVisitorList(sessionUser.getCompanyId(),from, to,1, page);
        }
        List<String> keys=new ArrayList<String>();
        if(StringUtils.isNotEmpty(key)){
        	if(key.contains(",")){
        		String[] str=key.split(",");
        		for(String s:str){
        			keys.add(s);
        		}	
        	}else{
        		keys.add(key);
        	}
        }else{
        	keys.add("views");
        	key="views";
        }
        Map<Integer,Object> mapC=new HashMap<Integer,Object>();
        for(AnalysisMyrcVisitor v:page.getRecords()){
        	if(v.getCompanyId()>0){
        		Company company=companyService.queryCompanyById(v.getCompanyId());
        		if(company!=null){
        			mapC.put(v.getCompanyId(), company);
        		}
        	}
        }
        out.put("mapC", mapC);
        out.put("map", map);
        out.put("from", from);
        out.put("to", to);
        out.put("flag", flag);
        out.put("page", page);
        out.put("pages", pages);
        out.put("keys", keys);
        out.put("key", key);
		return null;
	}

}

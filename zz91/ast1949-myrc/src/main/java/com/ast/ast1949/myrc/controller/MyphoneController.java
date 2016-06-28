package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneClickLog;
import com.ast.ast1949.domain.phone.PhoneCostSvr;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.domain.phone.PhonePpcVisit;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhoneLogDto;
import com.ast.ast1949.dto.phone.PhonePpcVisitDto;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.phone.LdbLevelService;
import com.ast.ast1949.service.phone.PhoneCallClickLogService;
import com.ast.ast1949.service.phone.PhoneClickLogService;
import com.ast.ast1949.service.phone.PhoneCostSvrService;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhonePpcVisitService;
import com.ast.ast1949.service.phone.PhoneService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.lang.StringUtils;

/**
 *	author:kongsj
 *	date:2013-7-11
 */
@Controller
public class MyphoneController extends BaseController{
	
	@Resource
	private PhoneService phoneService;
	@Resource
	private PhoneLogService phoneLogService;
	@Resource
	private PhoneClickLogService phoneClickLogService;
	@Resource
	private PhoneCallClickLogService phoneCallClickLogService;
	@Resource
	private PhonePpcVisitService phonePpcVisitService;
	@Resource
	private CompanyService companyService;
	@Resource
	private PhoneCostSvrService phoneCostSvrService;
	@Resource
	private LdbLevelService ldbLevelService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
		
	@RequestMapping
	public void index(HttpServletRequest request,Map<String, Object> out){
		do {
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser==null){
				break;
			}
			Phone phone = phoneService.queryByCompanyId(ssoUser.getCompanyId());
			if(phone==null||StringUtils.isEmpty(phone.getTel())){
				break;
			}
			// 获取余额
			phone.setBalance(phoneLogService.countBalance(phone));
			out.put("phone",phone);
			String gmtend=crmCompanySvrService.queryGmtendByCompanyId(ssoUser.getCompanyId());;
		    out.put("gmtend", gmtend);
		} while (false);
		
	}
	
	@RequestMapping
	public void phoneLog(HttpServletRequest request,Map<String, Object> out,PhoneLog phoneLog,PageDto<PhoneLogDto>page){
		do {
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser==null){
				break;
			}
			Phone phone = phoneService.queryByCompanyId(ssoUser.getCompanyId());
			if(phone==null||StringUtils.isEmpty(phone.getTel())){
				break;
			}
			if(StringUtils.isEmpty(page.getDir())){
				page.setDir("desc");
			}
			if(StringUtils.isEmpty(page.getSort())){
				page.setSort("start_time");
			}
			phoneLog.setCompanyId(ssoUser.getCompanyId());
//			phoneLogService.pageList(phoneLog, page);
			phoneLogService.pageListByDto(phoneLog, page);
			// 获取余额
			phone.setBalance(phoneLogService.countBalance(phone));
			out.put("phone", phone);
			out.put("page", page);
		} while (false);
	}
	
	@RequestMapping
	public void clickLog(HttpServletRequest request,Map<String, Object> out,PageDto<PhoneClickLog> page,PhoneClickLog phoneClickLog){
		do {
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser==null){
				break;
			}
			Phone phone = phoneService.queryByCompanyId(ssoUser.getCompanyId());
			if(phone==null||StringUtils.isEmpty(phone.getTel())){
				break;
			}
			page.setSort("id");
			page.setDir("desc");
			phoneClickLog.setCompanyId(ssoUser.getCompanyId());
			page = phoneClickLogService.pageList(phoneClickLog, page);
			// 获取余额
			phone.setBalance(phoneLogService.countBalance(phone));
			out.put("phone", phone);
			out.put("page", page);
		} while (false);
	}
	@SuppressWarnings("unused")
	@RequestMapping
	public void phoneCallClickLog(HttpServletRequest request,Map<String, Object> out,PageDto<PhoneLog> page){
		page.setSort("start_time");
		page.setDir("desc");
		//标记有无付费
		Map<String, Integer> map = new HashMap<String, Integer>();
		//组
		List<PhoneLog> list=new ArrayList<PhoneLog>();
		//标记号码，即分组
		Map<String,String> mapG = new HashMap<String, String>();
		Map<String, String> maps = new HashMap<String, String>();
		Map<String, String> mapm = new HashMap<String, String>();
		Integer in=0;
		do {
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser==null){
				break;
			}
			Phone phone = phoneService.queryByCompanyId(ssoUser.getCompanyId());
			if(phone==null){
				break;
			}
			if(phone.getTel()!=null){
			page=phoneLogService.queryListByTel(phone.getTel(), page);
			}
			// 获取余额
			phone.setBalance(phoneLogService.countBalance(phone));
			out.put("page", page);
			out.put("phone", phone);
			for(PhoneLog pl:page.getRecords()){
				String lag="";
				if(!maps.containsKey(pl.getCallerId())){
					mapm.put(pl.getCallerId(), pl.getCallSn());
					maps.put(pl.getCallerId(),"group_"+in);
					mapG.put(pl.getCallerId(),"ad_"+in);
					in++;
				}
			boolean i=phoneCallClickLogService.countLogByBothTel(pl.getCallerId(),ssoUser.getCompanyId());
			if(i){
				map.put(pl.getCallerId(), 1);
			}else{
				map.put(pl.getCallerId(), 0);
			}
			}
			out.put("map", map);
			out.put("maps", maps);
			out.put("mapG", mapG);
			out.put("mapm", mapm);
		}while (false);
	}
	@RequestMapping
	public ModelAndView insertPhoneCallClickLog(HttpServletRequest request,Map<String, Object> out,String callSn,PhoneLog phoneLog) throws IOException{
		SsoUser ssoUser = getCachedUser(request);
		Integer companyId = null;
		Integer phonelag=0;
		if(ssoUser!=null){
			companyId=ssoUser.getCompanyId();
		}
		if(callSn!=null){
			phoneLog=phoneLogService.queryPhoneLogByCallSn(callSn);
			}
		if(phoneLog!=null){
		//判断有无余额
		 Phone phone=phoneService.queryByTel(phoneLog.getTel());
		// 获取插入前的余额
		 phone.setBalance(phoneLogService.countBalance(phone));
		 if(phone!=null && Float.valueOf(phone.getBalance())>=10){
		   phonelag=phoneCallClickLogService.insertLog(phoneLog.getCallerId(),companyId);
		   // 获取插入后的余额
		   phone.setBalance(phoneLogService.countBalance(phone));
		   phoneLog.setCallFee(phone.getBalance());
		   if(phonelag>0){
			   //扣费
			   phoneCostSvrService.reduceFee(0, companyId, 10f);
			   //来电宝经验信息
			   ldbLevelService.resetLevel(companyId, 10);
		   }
		 }else{
			phoneLog.setState("2"); 
		 }
		}
		return printJson(phoneLog, out);
	}
	@RequestMapping
	public void phonePpcVisit(HttpServletRequest request,Map<String, Object> out,PageDto<PhonePpcVisitDto> page){
		SsoUser ssoUser = getCachedUser(request);
		page.setSort("gmt_target");
		page.setDir("desc");
		//标记会员类型
		Map<Integer, Integer> maps = new HashMap<Integer, Integer>();
		//标记付费
		Map<Integer, Integer> mapf = new HashMap<Integer, Integer>();
		//标记公司信息
		Map<Integer, Object> mapn = new HashMap<Integer, Object>();
		do{
			if(ssoUser==null){
				break;
			}
			PhonePpcVisit phonePpcVisit=new PhonePpcVisit();
			phonePpcVisit.setTargetId(ssoUser.getCompanyId());
			page=phonePpcVisitService.pagePpcVisitList(phonePpcVisit, page);
			for(PhonePpcVisitDto ppvt:page.getRecords()){
				//会员类型
				String memberShip=companyService.queryMembershipOfCompany(ppvt.getPhonePpcVisit().getCid());
				if(memberShip.equals("10051000")){
					maps.put(ppvt.getPhonePpcVisit().getCid(), 0);
				}else{
					maps.put(ppvt.getPhonePpcVisit().getCid(), 1);
				}
				//是否付费
				Integer li=phoneClickLogService.countById(ppvt.getPhonePpcVisit().getTargetId(), ppvt.getPhonePpcVisit().getCid());
				if(li>0){
					mapf.put(ppvt.getPhonePpcVisit().getCid(),1);
				}else{
					mapf.put(ppvt.getPhonePpcVisit().getCid(),0);
				}
				//根据公司id获取公司信息
				Company company=companyService.queryCompanyById(ppvt.getPhonePpcVisit().getCid());
				if(company!=null){
					PhoneCostSvr phoneCostSvr=phoneCostSvrService.queryByCompanyId(ppvt.getPhonePpcVisit().getTargetId());
					company.setBuyDetails(String.valueOf(phoneCostSvr.getClickFee()));
					mapn.put(ppvt.getPhonePpcVisit().getCid(), company);
				}
			}
			//获取phone信息
			Phone phone = phoneService.queryByCompanyId(ssoUser.getCompanyId());
		    // 获取余额
			phone.setBalance(phoneLogService.countBalance(phone));
			out.put("phone", phone);
			out.put("maps", maps);
			out.put("mapf", mapf);
			out.put("mapn", mapn);
			out.put("page", page);
			
		}while(false);
	}
	@RequestMapping
	public ModelAndView insertClickLog(HttpServletRequest request,Map<String, Object> out,Integer targetId,Company company) throws IOException{
		SsoUser ssoUser = getCachedUser(request);
		PhoneClickLog phoneClickLog=new PhoneClickLog();
		do{
			if(ssoUser==null){
				break;
			}
			//获取自己公司id
			Integer companyId=ssoUser.getCompanyId();
			phoneClickLog.setCompanyId(companyId);
			phoneClickLog.setTargetId(targetId);
			//先扣钱
			Float i=phoneCostSvrService.reduceFee(companyId);
			Integer in=0;
			if(i>0){
				//插入扣钱记录
				phoneClickLog.setClickFee(i);
				in=phoneClickLogService.insert(phoneClickLog);
				//来电宝经验信息
				ldbLevelService.resetLevel(companyId, i);
			}
			// 获取余额
			//获取phone信息
			Phone phone = phoneService.queryByCompanyId(companyId);
		    if(phone!=null){
		    	phone.setBalance(phoneLogService.countBalance(phone));
		    }
			company=companyService.queryCompanyById(targetId);
			//标记余额
			company.setBusiness(phone.getBalance());
			company.setBusinessType(String.valueOf(i));
			if(in>0){
				//标记扣钱成功
				company.setDomain("1");
			}else{
				company.setDomain("0");
			}
		}while(false);
		return printJson(company, out);
	}
}
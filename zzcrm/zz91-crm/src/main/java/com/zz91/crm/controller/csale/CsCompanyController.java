package com.zz91.crm.controller.csale;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.crm.controller.BaseController;
import com.zz91.crm.dao.CrmCompanyDao;
import com.zz91.crm.dao.CrmSaleCompDao;
import com.zz91.crm.domain.CrmCompany;
import com.zz91.crm.domain.CrmContact;
import com.zz91.crm.domain.CrmLog;
import com.zz91.crm.domain.CrmSaleComp;
import com.zz91.crm.domain.SysLog;
import com.zz91.crm.dto.CompanySearchDto;
import com.zz91.crm.dto.CrmLogDto;
import com.zz91.crm.dto.ExtResult;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.dto.SaleCompanyDto;
import com.zz91.crm.service.CrmCompanyService;
import com.zz91.crm.service.CrmContactService;
import com.zz91.crm.service.CrmLogService;
import com.zz91.crm.service.CrmSaleCompService;
import com.zz91.crm.service.ParamService;
import com.zz91.crm.service.SysLogService;
import com.zz91.crm.util.LogConst;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-2-20 
 */
@Controller
public class CsCompanyController extends BaseController {
	
	@Resource
	private CrmCompanyService crmCompanyService;
	@Resource
	private CrmLogService crmLogService;
	@Resource
	private SysLogService sysLogService;
	@Resource
	private CrmContactService crmContactService;
	@Resource
	private CrmSaleCompService crmSaleCompService;
	@Resource
	private ParamService paramService;
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
	//搜索公司
	@RequestMapping
	public ModelAndView queryComp(Map<String, Object> out, HttpServletRequest request,
			CompanySearchDto searchdto, PageDto<SaleCompanyDto> page,String sr,Integer sortMode){
		
		crmCompanyService.timeHandle(searchdto);
		crmCompanyService.sortByField(page,null,sr, sortMode);
	
		searchdto.setRegistStatus(CrmCompanyService.REGIST_STATUS_CHECK);
		searchdto.setStatus(CrmCompanyDao.STATUS_ABLE);
		searchdto.setRepeatId(0);
		
		if(searchdto.getCtype()==null){
			searchdto.setSaleAccount(getCachedUser(request).getAccount());
			searchdto.setSaleDept(getCachedUser(request).getDeptCode());
			searchdto.setSaleType(CrmSaleCompDao.SALE_TYPE2);
		}else {
			//方便查询所有高极客户
			searchdto.setSaleType(CrmSaleCompDao.SALE_TYPE1);
		}
		
		page=crmCompanyService.searchMyCompany(searchdto, page);
		
		return printJson(page, out);
	}
	
	//一个月未回访
	@RequestMapping
	public ModelAndView notContactByMonth(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setGmtLastContactEnd(DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -30), "yyyy-MM-dd"));
		out.put("search", search);
		return null;
	}
	
	//今天安排联系
	@RequestMapping
	public ModelAndView todayContact(Map<String, Object> out, HttpServletRequest request) throws ParseException{
		CompanySearchDto search = new CompanySearchDto();
		search.setGmtNextContactStart(DateUtil.toString(new Date(), "yyyy-MM-dd"));
		search.setGmtNextContactEnd(DateUtil.toString(DateUtil.getDateAfterDays(new Date(), 1), "yyyy-MM-dd"));
		search.setSaleAccount(getCachedUser(request).getAccount());
		search.setSaleDept(getCachedUser(request).getDeptCode());
		out.put("search", search);
		return null;
	}
	
	//我的所有客户
	@RequestMapping
	public ModelAndView myAllComp(Map<String, Object> out, HttpServletRequest request) throws ParseException{
		return null;
	}
	
	//安排联系未联系
	@RequestMapping
	public ModelAndView lostComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setGmtNextContactStart(null);
		search.setGmtNextContactEnd(DateUtil.toString(new Date(), "yyyy-MM-dd"));
		out.put("search", search);
		return null;
	}
	
	//新分配未联系
	@RequestMapping
	public ModelAndView newNoContactComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setContactStatus((short)1);
		search.setContactFlag((short)0);
		search.setContactCount(0);
		out.put("search", search);
		return null;
	}
	
	//所有高级客户
	@RequestMapping
	public ModelAndView allVipComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setCtype(CrmCompanyDao.CTYPE_VIP);
		out.put("search", search);
		out.put("deptSale", initCommon(out,request));
		return null;
	}
	
	//未分配高级客户
	@RequestMapping
	public ModelAndView notAssignVipComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setCtype(CrmCompanyDao.CTYPE_VIP);
		out.put("search", search);
		return null;
	}
	
	//联系详细页面
	@RequestMapping
	public ModelAndView contactDetails(Map<String, Object> out,HttpServletRequest request,Integer id,Integer visbile,Integer disable_status,String account){
		out.put("id", id);
		out.put("visbile", visbile);
		out.put("ds", disable_status);
		out.put("account", account);
		if (!getCachedUser(request).getAccount().equals(account)){
			out.put("operate", "0");
		}
		return null;
	}
	
	//查看公司信息
	@RequestMapping
	public ModelAndView queryCompDetails(Map<String, Object> out,HttpServletRequest request,Integer id){
		List<CrmCompany> list = new ArrayList<CrmCompany>();
		CrmCompany company=crmCompanyService.queryCompanyById(id);
		list.add(company);
		return printJson(list, out);
	}
	
	//查询小记信息
	@RequestMapping
	public ModelAndView queryCrmLog(Map<String, Object> out,HttpServletRequest request,Integer cid,Short callType){
		PageDto<CrmLog> page=new PageDto<CrmLog>();
		List<CrmLog> list=crmLogService.queryCrmLogByCid(cid,callType);
		page.setRecords(list);
		return printJson(page, out);
	}
	
	//查询客户星级
	@RequestMapping
	public ModelAndView queryStarById(Map<String, Object> out,HttpServletRequest request,Integer id){
		List<CrmCompany> list = new ArrayList<CrmCompany>();
		CrmCompany company=crmCompanyService.queryStarById(id);
		list.add(company);
		return printJson(list, out);
	}
	
	//创建小记
	@RequestMapping
	public ModelAndView createCrmLog(Map<String, Object> out,HttpServletRequest request,CrmLog crmLog,Short serviceStar){
		ExtResult result=new ExtResult();
		crmLog.setSaleAccount(getCachedUser(request).getAccount());
		crmLog.setSaleDept(getCachedUser(request).getDeptCode());
		crmLog.setSaleName(getCachedUser(request).getName());
		crmLog.setStar(serviceStar);
		crmLog.setCallType((short)1);
		try {
			crmLog.setGmtNextContact(DateUtil.getDate(crmLog.getGmtNextContactStr(), "yyyy-MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (crmLog.getCid()!=null){
			Integer i=crmLogService.createCrmLog(crmLog);
			if (i!=null && i.intValue()>0){
				crmCompanyService.updateStarByCid(crmLog.getCid(), crmLog.getStar(),crmLog.getSource(),crmLog.getMaturity(),
						crmLog.getPromote(),crmLog.getSaleAccount(),crmLog.getSaleName(),crmLog.getKp(),crmLog.getCallType());
				result.setSuccess(true);
			}
		}
		else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}
	
	//查询今天所有小记信息
	@RequestMapping
	public ModelAndView queryCrmLogByToday(Map<String, Object> out,HttpServletRequest request,String account,
			String tdate,PageDto<CrmLogDto> page,Short disable,Short star,Short type){
		page=crmLogService.pageCrmLogByToday(account, tdate, page,disable,star,type);
		return printJson(page, out);
	}
	
	//今天所有小记
	@RequestMapping
	public ModelAndView todayCrmLog(Map<String, Object> out,HttpServletRequest request,String account,String tdate,
			Short disable,Short star,Short type){
		out.put("account", account);
		out.put("tdate", tdate);
		out.put("disable", disable);
		out.put("star", star);
		out.put("type", type);
		return null;
	}
	
	public void setLog(HttpServletRequest request,SysLog log){
		log.setSaleAccount(getCachedUser(request).getAccount());
		log.setSaleDept(getCachedUser(request).getDeptCode());
		log.setSaleIp(HttpUtils.getInstance().getIpAddr(request));
		log.setSaleName(getCachedUser(request).getName());
	}
	
	//更新公司信息
	@RequestMapping
	public ModelAndView updateCrmCompany(Map<String, Object> out,HttpServletRequest request,CrmCompany crmCompany){
		ExtResult result = new ExtResult();
		CrmCompany comp1=crmCompanyService.queryCrmCompById(crmCompany.getId());
		Integer i=crmCompanyService.updateCompany(crmCompany);
		if (i!=null && i.intValue()>0){
			SysLog log=new SysLog();
			log.setTargetId(crmCompany.getId());
			log.setOperation(LogConst.MODIFY_COMP_DETAILS);
			setLog(request, log);
			CrmCompany comp2=crmCompanyService.queryCrmCompById(crmCompany.getId());
			StringBuilder details= new StringBuilder("修改重要信息：");
			if (!comp2.getName().equals(comp1.getName())){
				details.append("将联系人："+comp1.getName()+"修改为"+comp2.getName()+";");
			}
			if (!comp2.getCname().equals(comp1.getCname())){
				details.append("将公司名称："+comp1.getCname()+"修改为"+comp2.getCname()+";");
			}
			if (!comp2.getMobile().equals(comp1.getMobile())){
				details.append("将手机号码："+comp1.getMobile()+"修改为"+comp2.getMobile()+";");
			}
			if (!comp2.getPhone().equals(comp1.getPhone())){
				details.append("将电话号码："+comp1.getPhone()+"修改为"+comp2.getPhone()+";");
			}
			if (!comp2.getAddress().equals(comp1.getAddress())){
				details.append("将公司地址："+comp1.getAddress()+"修改为"+comp2.getAddress()+";");
			}
			
			log.setDetails(details.toString());
			sysLogService.createSysLog(log);
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	//查看联系人
	@RequestMapping
	public ModelAndView queryContact(Map<String, Object> out,HttpServletRequest request,Integer cid){
		PageDto<CrmContact> page=new PageDto<CrmContact>();
		List<CrmContact> list=crmContactService.queryCrmContactByCid(cid);
		page.setRecords(list);
		return printJson(page, out);
	}
	
	//新增联系人
	@RequestMapping
	public ModelAndView createContact(Map<String, Object> out,HttpServletRequest request,CrmContact crmContact){
		ExtResult result=new ExtResult();
		crmContact.setSaleAccount(getCachedUser(request).getAccount());
		crmContact.setSaleDept(getCachedUser(request).getDeptCode());
		crmContact.setSaleName(getCachedUser(request).getName());
		if(crmContact.getIsKey()==null){
			crmContact.setIsKey((short)0);
		}
		Integer i=crmContactService.createCrmContact(crmContact);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	//查询客服部门人员
	public List<JSONObject> initCommon(Map<String, Object> out,HttpServletRequest request){
		String dept = getCachedUser(request).getDeptCode();
		if (StringUtils.isNotEmpty(dept) && dept.equals("10001000")) {
			dept = paramService.queryValueByKey(CrmSaleCompService.DATA_INPUT_CONFIG, CrmSaleCompService.HUANBAO_SERVICE_DEPT_CODE);
		}
		return (List<JSONObject>) AuthUtils.getInstance().queryStaffByDept(dept);
	}
	
	//分配高会给客服人员
	@RequestMapping
	public ModelAndView assign(Map<String, Object> out, HttpServletRequest request,Integer id,
		Integer cid,Short companyType,String saleDept,String saleAccount,String saleName,String cname,Short ctype){
		ExtResult result = new ExtResult();
		CrmSaleComp crmSaleComp=new CrmSaleComp();
		crmSaleComp.setId(id);
		crmSaleComp.setSaleType(CrmSaleCompDao.SALE_TYPE2);
		crmSaleComp.setStatus(CrmSaleCompService.STATUS_ABLE);
		crmSaleComp.setCid(cid);
		crmSaleComp.setCompanyType(companyType);
		crmSaleComp.setSaleAccount(saleAccount);
		crmSaleComp.setSaleDept(saleDept);
		crmSaleComp.setSaleName(saleName);
		Integer i=crmSaleCompService.createCrmSaleComp(crmSaleComp,ctype);
		if (i!=null && i.intValue()>0){
			SysLog log=new SysLog();
			log.setTargetId(cid);
			log.setOperation(LogConst.REDISTRIOUTION);
			setLog(request, log);
			log.setDetails("将高会: "+cname+" 分配给客服:"+saleName);
			sysLogService.createSysLog(log);
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}

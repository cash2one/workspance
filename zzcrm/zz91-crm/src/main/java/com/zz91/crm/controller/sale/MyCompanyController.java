package com.zz91.crm.controller.sale;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.crm.controller.BaseController;
import com.zz91.crm.dao.CrmCompanyDao;
import com.zz91.crm.dao.CrmSaleCompDao;
import com.zz91.crm.domain.CrmCompany;
import com.zz91.crm.domain.CrmContact;
import com.zz91.crm.domain.CrmLog;
import com.zz91.crm.domain.CrmRepeat;
import com.zz91.crm.domain.CrmSaleComp;
import com.zz91.crm.domain.SysLog;
import com.zz91.crm.dto.CompanySearchDto;
import com.zz91.crm.dto.CrmLogDto;
import com.zz91.crm.dto.ExtResult;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.dto.SaleCompanyDto;
import com.zz91.crm.exception.LogicException;
import com.zz91.crm.service.CrmCompanyService;
import com.zz91.crm.service.CrmContactService;
import com.zz91.crm.service.CrmLogService;
import com.zz91.crm.service.CrmRepeatService;
import com.zz91.crm.service.CrmSaleCompService;
import com.zz91.crm.service.SysLogService;
import com.zz91.crm.util.LogConst;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * @author root
 *
 */
@Controller
public class MyCompanyController extends BaseController{
	
	@Resource
	private CrmCompanyService crmCompanyService;
	@Resource
	private CrmSaleCompService crmSaleCompService;
	@Resource
	private CrmContactService crmContactService;
	@Resource
	private CrmLogService crmLogService;
	@Resource
	private SysLogService sysLogService;
	@Resource
	private CrmRepeatService crmRepeatService;
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
	//我的所有客户
	@RequestMapping
	public ModelAndView allComp(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
	//搜索公司
	@RequestMapping
	public ModelAndView queryComp(Map<String, Object> out, HttpServletRequest request,
			CompanySearchDto searchdto, PageDto<SaleCompanyDto> page,String sr,Integer sortMode){
		
		crmCompanyService.timeHandle(searchdto);
		crmCompanyService.sortByField(page,null, sr, sortMode);
		
		searchdto.setRegistStatus(CrmCompanyService.REGIST_STATUS_CHECK); 
		searchdto.setStatus(CrmCompanyDao.STATUS_ABLE);
		searchdto.setRepeatId(0);
		searchdto.setSaleAccount(getCachedUser(request).getAccount());
		searchdto.setSaleDept(getCachedUser(request).getDeptCode());
		
		page=crmCompanyService.searchMyCompany(searchdto, page);
		
		return printJson(page, out);
	}
	
	// 我今天安排联系客户
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
	
	public void setLog(HttpServletRequest request,SysLog log){
		log.setSaleAccount(getCachedUser(request).getAccount());
		log.setSaleDept(getCachedUser(request).getDeptCode());
		log.setSaleIp(HttpUtils.getInstance().getIpAddr(request));
		log.setSaleName(getCachedUser(request).getName());
	}
	
	//放入公海
	@RequestMapping
	public ModelAndView updateAutoBlack(Map<String, Object> out,HttpServletRequest request,Integer id){
		ExtResult result=new ExtResult();
		Integer i=crmSaleCompService.updateStatus(id, CrmSaleCompService.AUTO_BLOCK_FALSE);
		if (i!=null && i.intValue()>0){
			SysLog log=new SysLog();
			log.setTargetId(id);
			log.setOperation(LogConst.PUT_PUBLIC_SEA);
			setLog(request, log);
			log.setDetails("放入公海！");
			sysLogService.createSysLog(log);
			result.setSuccess(true);
		}
		else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}
	
	//更新客户类型 0 普通 1 重点
	@RequestMapping
	public ModelAndView updateCompanyType(Map<String, Object> out,HttpServletRequest request,Integer id,Short companyType){
		ExtResult result = new ExtResult();
		Integer i=crmSaleCompService.updateCompanyType(id, companyType);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	//放入废品池 
	@RequestMapping
	public ModelAndView updateDisableStatus(Map<String, Object> out,HttpServletRequest request,Integer id){
		ExtResult result = new ExtResult();
		Integer i=crmSaleCompService.updateDisableStatus(id);
		if (i!=null && i.intValue()>0){
			SysLog log=new SysLog();
			log.setTargetId(id);
			log.setOperation(LogConst.PUT_WASTE_TANK);
			setLog(request, log);
			log.setDetails("申请放入废品池！");
			sysLogService.createSysLog(log);
			result.setSuccess(true);
		}
		else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}
	
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
	
	//更新公司信息
	@RequestMapping
	public ModelAndView updateCrmCompany(Map<String, Object> out,HttpServletRequest request,CrmCompany crmCompany){
		ExtResult result = new ExtResult();
		CrmCompany comp1=crmCompanyService.queryCrmCompById(crmCompany.getId());
		if (crmCompany.getMatch()==null){
			crmCompany.setMatch((short)0);
		}
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
	
	//查询相同联系公司信息
	@RequestMapping
	public ModelAndView queryLikeCompany(Map<String, Object> out,HttpServletRequest request,Integer cid){
		PageDto<SaleCompanyDto> page=new PageDto<SaleCompanyDto>();
		List<SaleCompanyDto> list=crmCompanyService.queryCommCompanyByContact(cid, null);
		page.setRecords(list);
		return printJson(page, out);
	}
	
	//查询小记信息
	@RequestMapping
	public ModelAndView queryCrmLog(Map<String, Object> out,HttpServletRequest request,Integer cid,Short callType){
		List<CrmLog> list=crmLogService.queryCrmLogByCid(cid,callType);
		return printJson(list, out);
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
	public ModelAndView createCrmLog(Map<String, Object> out,HttpServletRequest request,CrmLog crmLog){
		ExtResult result=new ExtResult();
		crmLog.setSaleAccount(getCachedUser(request).getAccount());
		crmLog.setSaleDept(getCachedUser(request).getDeptCode());
		crmLog.setSaleName(getCachedUser(request).getName());
		crmLog.setCallType((short)0);
		try {
			crmLog.setGmtNextContact(DateUtil.getDate(crmLog.getGmtNextContactStr(), "yyyy-MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (crmLog.getCid()!=null){
			Integer i=crmLogService.createCrmLog(crmLog);
			if (i!=null && i.intValue()>0){
				crmCompanyService.updateStarByCid(crmLog.getCid(), crmLog.getStar(),crmLog.getSource(),
						crmLog.getMaturity(),crmLog.getPromote(),crmLog.getSaleAccount(),crmLog.getSaleName(),crmLog.getKp(),crmLog.getCallType());
				result.setSuccess(true);
			}
		}
		else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}
	
	//手动录入客户
	@RequestMapping
	public ModelAndView inputCrm(Map<String, Object> out,HttpServletRequest request){
		return null;
	}
	
	//手动添加新公司信息
	@RequestMapping
	public ModelAndView createCrmCompany(Map<String, Object> out,HttpServletRequest request,CrmCompany crmCompany) {
		ExtResult result = new ExtResult();
		
		if(crmCompanyService.queryMobileAndEmail(crmCompany.getMobile(), crmCompany.getEmail())){
			result.setSuccess(false);
		} else {
			crmCompany.setCtype(CrmCompanyDao.CTYPE_SELF);
			crmCompany.setSaleAccount(getCachedUser(request).getAccount());
			crmCompany.setSaleName(getCachedUser(request).getName());
			crmCompany.setInputAccount(getCachedUser(request).getAccount());
			
			Integer i=crmCompanyService.createCompany(crmCompany);
			if (i!=null && i.intValue()>0){
				CrmSaleComp crmSaleComp = new CrmSaleComp();
				crmSaleComp.setCid(i);
				crmSaleComp.setStatus(CrmSaleCompService.STATUS_ABLE);
				crmSaleComp.setSaleType(CrmSaleCompDao.SALE_TYPE1);
				crmSaleComp.setSaleAccount(getCachedUser(request).getAccount());
				crmSaleComp.setSaleDept(getCachedUser(request).getDeptCode());
				crmSaleComp.setSaleName(getCachedUser(request).getName());
				crmSaleComp.setCompanyType(CrmSaleCompService.COMPANY_TYPE_NORMAL);
				crmSaleCompService.createCrmSaleComp(crmSaleComp,(short)1);
				SysLog log=new SysLog();
				log.setTargetId(i);
				log.setOperation(LogConst.ADD_COMP_DETAILS);
				setLog(request, log);
				log.setDetails("手动添加新客户！");
				sysLogService.createSysLog(log);
				result.setSuccess(true);
			}
			else {
				result.setSuccess(false);
			}
		}

		return printJson(result, out);
	}
	
	//查询形似公司信息
	@RequestMapping
	public ModelAndView queryCrmCompany(Map<String, Object> out,HttpServletRequest request,String cname,String mobile,String phone,String email,String fax){
		List<CrmCompany> list=null;
		if (StringUtils.isNotEmpty(email) || StringUtils.isNotEmpty(mobile) 
				|| StringUtils.isNotEmpty(phone) || StringUtils.isNotEmpty(cname) || StringUtils.isNotEmpty(fax)){
			list=crmCompanyService.queryCommCompany(cname, null, email, mobile, phone,fax, null);
		}
		return printJson(list, out);
	}
	
	//我的重点客户
	@RequestMapping
	public ModelAndView myStress(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setCompanyType(CrmSaleCompService.COMPANY_TYPE_IMPORTANT);
		out.put("search", search);
		return null;
	}
	
	//我的拖单/毁单客户
	@RequestMapping
	public ModelAndView dragAndDestory(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setDragDestryStatus(CrmSaleCompService.DRAG_DESTORY_THREE);
		out.put("search", search);
		return null;
	}
	
	//公海挑入未联系客户
	@RequestMapping
	public ModelAndView notContactComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setContactStatus((short)1);
		search.setContactFlag((short)0);
		search.setContactCount(0);
		search.setBlockCount((short)5);//这里5用于比较真实值,并非是真实值
		out.put("search", search);
		return null;
	}
	
	//跟丢客户
	@RequestMapping
	public ModelAndView lostComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setGmtNextContactStart(null);
		search.setGmtNextContactEnd(DateUtil.toString(new Date(), "yyyy-MM-dd"));
		out.put("search", search);
		return null;
	}
	
	//新分配未联系客户
	@RequestMapping
	public ModelAndView newComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setContactStatus((short)1);
		search.setContactFlag((short)0);
		search.setContactCount(0);
		search.setBlockCount((short)0);
		out.put("search", search);
		return null;
	}
	
	//查询今天所有小记信息
	@RequestMapping
	public ModelAndView queryCrmLogByToday(Map<String, Object> out,HttpServletRequest request,
			String account,String tdate,PageDto<CrmLogDto> page,Short disable,Short star,Short type){
		page=crmLogService.pageCrmLogByToday(account, tdate, page,disable,star,type);
		return printJson(page, out);
	}
	
	//今天所有小记
	@RequestMapping
	public ModelAndView todayCrmLog(Map<String, Object> out,HttpServletRequest request,
			String account,String tdate,Short disable,Short star,Short type){
		out.put("account", account);
		out.put("tdate", tdate);
		out.put("disable", disable);
		out.put("star", star);
		out.put("type", type);
		return null;
	}
	
	@RequestMapping
	public ModelAndView updateSaleStatusById(Map<String, Object> out,HttpServletRequest request,Integer id){
		ExtResult result = new ExtResult();
		Integer i=crmCompanyService.updateSaleStatusById(id, CrmCompanyDao.SALE_STATUS_FALSE);
		if (i!=null && i.intValue()>0){
			SysLog log=new SysLog();
			log.setTargetId(id);
			log.setOperation(LogConst.PUT_NOT_SALE);
			setLog(request, log);
			log.setDetails("设为非销售客户！");
			sysLogService.createSysLog(log);
			result.setSuccess(true);
		}
		else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}
	
	//提交合并申请
	@RequestMapping
	public ModelAndView insertCrmRepeat(Map<String, Object> out,HttpServletRequest request,
			Integer id,String cids){
		ExtResult result = new ExtResult();
		do{
			String applyAccount=getCachedUser(request).getAccount();
			String applyName=getCachedUser(request).getName();
			String applyDept=getCachedUser(request).getDeptCode();
			
			CrmRepeat repeat=new CrmRepeat();
			repeat.setOrderId(id);
			repeat.setCid(id);
			repeat.setSaleAccount(applyAccount);
			repeat.setSaleDept(applyDept);
			repeat.setSaleName(applyName);
			repeat.setCheckStatus(CrmRepeatService.STATUS_UNCHECK);
			Integer i=crmRepeatService.createCrmRepeat(repeat);
			if (i!=null && i.intValue()>0){
				
				SysLog log=new SysLog();
				log.setTargetId(id);
				log.setOperation(LogConst.PUT_REPEAT_UNCHECK);
				setLog(request, log);
				log.setDetails("放入重复库,待审核！");
				sysLogService.createSysLog(log);
				
				if(cids==null || cids.length()<=0){
					break;
				}
				String[] cids2=cids.split(",");
				
				for (String cid:cids2) {
					CrmRepeat repeat2=new CrmRepeat();
					repeat2.setOrderId(id);
					repeat2.setCid(Integer.parseInt(cid));
					repeat2.setSaleAccount(applyAccount);
					repeat2.setSaleDept(applyDept);
					repeat2.setSaleName(applyName);
					repeat2.setCheckStatus(CrmRepeatService.STATUS_UNCHECK);
					crmRepeatService.createCrmRepeat(repeat2);
					
					SysLog log1=new SysLog();
					log1.setTargetId(Integer.parseInt(cid));
					log1.setOperation(LogConst.PUT_REPEAT_UNCHECK);
					setLog(request, log);
					log1.setDetails("放入重复库,待审核！");
					sysLogService.createSysLog(log);
				}
				result.setSuccess(true);
			}else {
				result.setSuccess(false);
			}
		
		}while(false);
		return printJson(result, out);
	}
	
	//放入未激活客户库
	@Transactional
	@RequestMapping
	public ModelAndView putNoActiveBox(Map<String, Object> out,HttpServletRequest request,Integer id){
		ExtResult result = new ExtResult();
		Integer i=crmCompanyService.updateCtypeById(id, CrmCompanyDao.CTYPE_DISACTIVE);
		Integer j=crmSaleCompService.updateStatusByCid(id);
		if (i==0 || j==0){
			result.setSuccess(false);
			throw new LogicException("同时更新失败");
		}else if(i>0 && j>0) {
			SysLog log=new SysLog();
			log.setTargetId(id);
			log.setOperation(LogConst.PUT_DISACTIVE_BOX);
			setLog(request, log);
			log.setDetails("放入未激活库！");
			sysLogService.createSysLog(log);
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	//我的付费客户
	@RequestMapping
	public ModelAndView myPay(Map<String, Object> out,HttpServletRequest request){
		CompanySearchDto search=new CompanySearchDto();
		search.setCtype(CrmCompanyDao.CTYPE_VIP);
		out.put("search", search);
		return null;
	}
}
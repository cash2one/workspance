package com.zz91.crm.controller.sale;

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
import com.zz91.crm.domain.CrmRepeat;
import com.zz91.crm.domain.CrmSaleComp;
import com.zz91.crm.domain.SysLog;
import com.zz91.crm.dto.CompanySearchDto;
import com.zz91.crm.dto.CrmRepeatDto;
import com.zz91.crm.dto.ExtResult;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.dto.SaleCompanyDto;
import com.zz91.crm.service.CrmCompanyService;
import com.zz91.crm.service.CrmRepeatService;
import com.zz91.crm.service.CrmSaleCompService;
import com.zz91.crm.service.ParamService;
import com.zz91.crm.service.SysLogService;
import com.zz91.crm.util.LogConst;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * @author root
 *
 */
@Controller
public class DeptCompanyController extends BaseController{
	
	@Resource
	private CrmCompanyService crmCompanyService;
	@Resource
	private CrmSaleCompService crmSaleCompService;
	@Resource
	private ParamService paramService;
	@Resource
	private SysLogService sysLogService;
	@Resource
	private CrmRepeatService crmRepeatService;

	@RequestMapping
	public ModelAndView index(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryComp(Map<String, Object> out, HttpServletRequest request,CompanySearchDto searchdto, 
			PageDto<SaleCompanyDto> page,String sr,Integer sortMode){
		
		crmCompanyService.timeHandle(searchdto);
		crmCompanyService.sortByField(page,null, sr, sortMode);
		
		if (searchdto.getRegistStatus() != null) {
			if (searchdto.getRegistStatus() == 2) {
				searchdto.setRegistStatus(null);
			}
		} else {
			searchdto.setRegistStatus(CrmCompanyService.REGIST_STATUS_CHECK);
		}
		searchdto.setSaleType(CrmSaleCompDao.SALE_TYPE1);
		searchdto.setSaleDept(getCachedUser(request).getDeptCode());
		if (searchdto.getStatus()==null){
			searchdto.setStatus(CrmCompanyDao.STATUS_ABLE);
		}
		if (searchdto.getRepeatId()==null){
			searchdto.setRepeatId(0);
		}
		
		page=crmCompanyService.searchMyCompany(searchdto, page);
		
		return printJson(page, out);
	}
	
	//放入公海
	@RequestMapping
	public ModelAndView updateAutoBlack(Map<String, Object> out,HttpServletRequest request,Integer id) throws Exception{
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
		return printJson(result, out);
	}
	
	public void setLog(HttpServletRequest request,SysLog log){
		String ip=HttpUtils.getInstance().getIpAddr(request);
		String account=getCachedUser(request).getAccount();
		String deptCode=getCachedUser(request).getDeptCode();
		String name=getCachedUser(request).getName();
		log.setSaleAccount(account);
		log.setSaleDept(deptCode);
		log.setSaleIp(ip);
		log.setSaleName(name);
	}
	
	//审核放入废品池(审核通过,同时更改公司类型)
	@RequestMapping
	public ModelAndView checkStatusAndUpdateCtype(Map<String, Object> out,HttpServletRequest request,Integer id,Short flag){
		ExtResult result=new ExtResult();
		Integer i=crmSaleCompService.checkDisableStatus(id, flag);
		if (i!=null && i.intValue()>0){
			SysLog log=new SysLog();
			log.setTargetId(id);
			setLog(request, log);
			if (flag==1){
				log.setOperation(LogConst.PUT_WASTE_TANK_TRUE);
				log.setDetails("放入废品池，审核通过！");
			}
			else{
				log.setOperation(LogConst.PUT_WASTE_TANK_FALSE);
				log.setDetails("放入废品池，审核不通过！");
			}
			sysLogService.createSysLog(log);
			result.setSuccess(true);
		}
		else{
			result.setSuccess(false);
		}
		return printJson(result, out);
	}
	
	//五星客户
	@RequestMapping
	public ModelAndView fiveStar(Map<String, Object> out,HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setStar(CrmSaleCompService.STAR);
		out.put("search", search);
		out.put("deptSale", initCommon(out,request));
		return null;
	}
	//部门重点客户
	@RequestMapping
	public ModelAndView deptStress(Map<String, Object> out,HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setCompanyType(CrmSaleCompService.COMPANY_TYPE_IMPORTANT);
		out.put("search", search);
		out.put("deptSale", initCommon(out,request));
		return null;
	}
	
	//部门所有客户
	@RequestMapping
	public ModelAndView deptAllComp(Map<String, Object> out, HttpServletRequest request){
		out.put("deptSale", initCommon(out,request));
		return null;
	}
	
	//废品池客户
	@RequestMapping
	public ModelAndView wasteTankComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setDisableStatus(CrmSaleCompService.WASTE_STATUS);
		out.put("search", search);
		out.put("deptSale", initCommon(out,request));
		return null;
	}
	
	//主动注册客户
	@RequestMapping
	public ModelAndView registerComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setRegisterCode(CrmCompanyDao.REGISTER_TYPE2);
		out.put("search", search);
		out.put("deptSale", initCommon(out,request));
		return null;
	}
	
	//手动录入客户
	@RequestMapping
	public ModelAndView manualEntryComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setRegisterCode(CrmCompanyDao.REGISTER_TYPE1);
		search.setRegistStatus((short)2);
		search.setCtype((short)-1);
		out.put("search", search);
		out.put("deptSale", initCommon(out,request));
		return null;
	}
	
	//一个月未联系客户
	@RequestMapping
	public ModelAndView notContactByMonth(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setGmtLastContactEnd(DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -30), "yyyy-MM-dd"));
		out.put("search", search);
		out.put("deptSale", initCommon(out,request));
		return null;
	}
	
	public List<JSONObject> initCommon(Map<String, Object> out,HttpServletRequest request){
		String dept = getCachedUser(request).getDeptCode();
		if (StringUtils.isNotEmpty(dept) && dept.equals("10001000")) {
			dept = paramService.queryValueByKey(CrmSaleCompService.DATA_INPUT_CONFIG, CrmSaleCompService.HUANBAO_DEPT_CODE);
		}
		return (List<JSONObject>) AuthUtils.getInstance().queryStaffByDept(dept);
	}
	
	//重新分配客户
	@RequestMapping
	public ModelAndView reassign(Map<String, Object> out, HttpServletRequest request,Integer id,
		Integer cid,Short companyType,String saleDept,String saleAccount,String saleName,String oldSaleName,Short flag){
		ExtResult result = new ExtResult();
		CrmSaleComp crmSaleComp=new CrmSaleComp();
		crmSaleComp.setId(id);
		crmSaleComp.setSaleType(CrmSaleCompDao.SALE_TYPE1);
		crmSaleComp.setStatus(CrmSaleCompService.STATUS_ABLE);
		crmSaleComp.setCid(cid);
		crmSaleComp.setCompanyType(companyType);
		crmSaleComp.setSaleAccount(saleAccount);
		crmSaleComp.setSaleDept(saleDept);
		crmSaleComp.setSaleName(saleName);
		//婚妈判定0
		Integer i=0;
		if (flag!=null && flag==0){
			i=crmSaleCompService.createCrmSaleComp(crmSaleComp, (short)0);
		}else{
			i=crmSaleCompService.reSetCrmSaleComp(crmSaleComp);
		}
		if (i!=null && i.intValue()>0){
			SysLog log=new SysLog();
			log.setTargetId(cid);
			log.setOperation(LogConst.REDISTRIOUTION);
			setLog(request, log);
			if (flag!=null && flag==0){
				
				log.setDetails("分配客户,给"+saleName);
			}else {
				log.setDetails("将"+oldSaleName+"的客户，重新分配给:"+saleName);
			}
			sysLogService.createSysLog(log);
			result.setSuccess(true);
		}
		else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}
	
	//审核手动录入客户
	@RequestMapping
	public ModelAndView checkRegistStatus(Map<String, Object> out, HttpServletRequest request,Integer id,String memberCode){
		ExtResult result = new ExtResult();
		Integer i=crmCompanyService.updateRegistStatus(id, CrmCompanyService.REGIST_STATUS_CHECK,memberCode);
		if (i!=null && i.intValue()>0){
			SysLog log=new SysLog();
			log.setTargetId(id);
			log.setOperation(LogConst.ADD_COMP_CHECK);
			setLog(request, log);
			log.setDetails("手动添加客户，审核通过！");
			sysLogService.createSysLog(log);
			result.setSuccess(true);
		}else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}
	
	//自动掉公海客户
	@RequestMapping
	public ModelAndView automaticSea(Map<String, Object> out,HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setCtype(CrmCompanyDao.CTYPE_PUBLIC);
		search.setAutoBlock(CrmSaleCompService.AUTO_BLOCK_TRUE);
		search.setStatus(CrmCompanyDao.STATUS_DISABLE);
		out.put("search", search);
		out.put("deptSale", initCommon(out,request));
		return null;
	}
	
	//申请合并公司
	@RequestMapping
	public ModelAndView applyMergerComp(Map<String, Object> out,HttpServletRequest request){
		return null;
	}
	
	//查询合并公司申请单
	@RequestMapping
	public ModelAndView queryMergerApply(Map<String, Object> out,HttpServletRequest request,PageDto<CrmRepeat> page){
		page=crmRepeatService.pageCrmRepeat(CrmRepeatService.STATUS_UNCHECK, page);
		return printJson(page, out);
	}
	
	//合并公司详细
	@RequestMapping
	public ModelAndView queryApplyDetails(Map<String, Object> out,HttpServletRequest request,Integer orderId){
		List<CrmRepeatDto> list=crmRepeatService.queryRepeatByOrderId(orderId, CrmRepeatService.STATUS_UNCHECK);
		return printJson(list, out);
	}
	
	//合并公司审核
	@RequestMapping
	public ModelAndView updateCheckStatus(Map<String, Object> out,HttpServletRequest request,Short CheckStatus,Integer id,Integer targetId){
		ExtResult result = new ExtResult();
		Integer i=crmRepeatService.updateCheckStatus(id, CheckStatus,targetId);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
			if(id.equals(targetId)){
				SysLog log=new SysLog();
				log.setTargetId(id);
				log.setOperation(LogConst.PUT_REPEAT_CHECK);
				setLog(request, log);
				log.setDetails("已审核,与本记录重复客户已放入重复库！");
				sysLogService.createSysLog(log);
			}else {
				SysLog log=new SysLog();
				log.setTargetId(id);
				log.setOperation(LogConst.PUT_REPEAT_CHECK);
				setLog(request, log);
				log.setDetails("放入重复库,已审核！");
				sysLogService.createSysLog(log);
			}
		}else {
			result.setSuccess(false);
			result.setData("抱歉,系统发生错误!");
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deptLost(Map<String, Object> out,HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setGmtNextContactStart(null);
		search.setGmtNextContactEnd(DateUtil.toString(new Date(), "yyyy-MM-dd"));
		out.put("search", search);
		return null;
	}
	
	@RequestMapping
	public ModelAndView hunMaImptComp(Map<String, Object> out,HttpServletRequest request){
		out.put("deptSale", initCommon(out,request));
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryHunMaComp(Map<String, Object> out,HttpServletRequest request,
			PageDto<SaleCompanyDto> page,CrmCompany company){
		page = crmCompanyService.pageComp(page,company);
		return printJson(page, out);
	}
	
}

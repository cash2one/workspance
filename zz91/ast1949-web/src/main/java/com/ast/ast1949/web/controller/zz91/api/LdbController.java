package com.ast.ast1949.web.controller.zz91.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneCostSvr;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmCsService;
import com.ast.ast1949.service.phone.PhoneCostSvrService;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Controller
public class LdbController extends BaseController {
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private CrmCsService crmCsService;
	@Resource
	private PhoneCostSvrService phoneCostSvrService;
	@Resource
	private CompanyService companyService;
	@Resource
	private PhoneService phoneService;
	@Resource
	private PhoneLogService phoneLogService;

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView open(HttpServletRequest request,
			Map<String, Object> out, String callbackCode, String callbackData,Integer isSms) {
		Map<String, Object> dataMap = (Map<String, Object>) JSONObject.toBean(
				JSONObject.fromObject(callbackData), Map.class);
		Integer companyId = (Integer) dataMap.get("companyId");
		out.put("companyId", companyId);
		out.put("companySvrId", dataMap.get("companySvrId"));
		CrmCs cs = crmCsService.queryCsOfCompany(companyId);
		if (cs != null) {
			dataMap.put("csAccount", cs.getCsAccount());
			if (StringUtils.isNotEmpty(cs.getCsAccount())) {
				dataMap.put("csName", AuthUtils.getInstance()
						.queryStaffNameOfAccount(cs.getCsAccount()));
			}
		}
		out.put("data", JSONObject.fromObject(dataMap).toString());
		out.put("isSms", isSms);
		//新开的服务code
		CrmCompanySvr svr = crmCompanySvrService.queryCompanySvrById(Integer.valueOf(String.valueOf(dataMap.get("companySvrId"))));
		if(svr!=null&&StringUtils.isNotEmpty(svr.getCrmServiceCode())&&"1011".equals(svr.getCrmServiceCode())){
			out.put("svr_code", 1);
		}
		return null;
	}

	@RequestMapping
	public ModelAndView doOpen(HttpServletRequest request,
			Map<String, Object> out, CrmCompanySvr svr, String gmtPreStartDate,
			String gmtPreEndDate, String gmtStartDate, String gmtEndDate,
			String gmtSignedDate, Integer integral, String oldCsAccount,
			String csAccount, PhoneCostSvr phoneCostSvr,String smsFee) throws IOException {

		try {
			if (StringUtils.isNotEmpty(gmtPreStartDate)) {
				svr.setGmtPreStart(DateUtil
						.getDate(gmtPreStartDate, "yyyy-M-d"));
			}
			if (StringUtils.isNotEmpty(gmtPreEndDate)) {
				svr.setGmtPreEnd(DateUtil.getDate(gmtPreEndDate, "yyyy-M-d"));
			}
			svr.setGmtSigned(DateUtil.getDate(gmtSignedDate, "yyyy-M-d"));
			svr.setGmtStart(DateUtil.getDate(gmtStartDate, "yyyy-M-d"));
			svr.setGmtEnd(DateUtil.getDate(gmtEndDate, "yyyy-M-d"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Boolean r = crmCompanySvrService.openSvr(svr, svr.getCompanyId());

		//开通更改会员类型
		companyService.updateMembershipCode(svr.getMembershipCode(), svr.getCompanyId());

		ExtResult result = new ExtResult();
		result.setSuccess(r);

		// 如果服务开通成功
		if (r) {
			if (phoneCostSvr.getFee()!=null) {
				// 服务id
				phoneCostSvr.setCrmCompanyServiceId(svr.getId());
				phoneCostSvrService.insert(phoneCostSvr);
				if(svr.getCompanyId()!=null){
					//历史充值记录
					Phone phone=phoneService.queryByCompanyId(svr.getCompanyId());
					//如果新开通的用户这不用计算总金额
					if(phone!=null){
						String amount=phoneCostSvrService.countFeeByCompanyId(svr.getCompanyId());
						
						phoneService.updateAmountByCompanyId(amount,svr.getCompanyId());
					}
				}
			}
			if (StringUtils.isNotEmpty(smsFee)) {
				phoneService.updateSmsFee(smsFee, svr.getCompanyId());
			}
		}

		// 添加指派运营人员
		crmCsService.reassign(oldCsAccount, csAccount, svr.getCompanyId());

		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView querySvrById(HttpServletRequest request,
			Map<String, Object> out, Integer id, Integer companyId)
			throws IOException {
		List<CrmCompanySvr> list = new ArrayList<CrmCompanySvr>();
		CrmCompanySvr svr = crmCompanySvrService.queryCompanySvrById(id);
		// 上一次服务时间
		if (svr.getGmtPreStart() == null) {
			CrmCompanySvr recentSvr = crmCompanySvrService.queryRecentHistory(
					svr.getCrmServiceCode(), svr.getCompanyId(), svr.getId());
			if (recentSvr != null) {
				svr.setGmtPreStart(recentSvr.getGmtStart());
				svr.setGmtPreEnd(recentSvr.getGmtEnd());
			}
		}
		list.add(svr);
		return printJson(list, out);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView close(HttpServletRequest request,
			Map<String, Object> out, String callbackCode, String callbackData) {
		Map<String, Object> dataMap = (Map<String, Object>) JSONObject.toBean(
				JSONObject.fromObject(callbackData), Map.class);
		out.put("companyId", dataMap.get("companyId"));
		out.put("companySvrId", dataMap.get("companySvrId"));
		return null;
	}

	@RequestMapping
	public ModelAndView doClose(HttpServletRequest request,
			Map<String, Object> out, Integer companyId, Integer companySvrId) {
		companyService.updateMembershipCode("10051000", companyId);
		Boolean result = crmCompanySvrService.closeSvr(companySvrId);
		if (result != null && result) {
			out.put("result", 1);
		} else {
			out.put("result", 0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView change(HttpServletRequest request,
			Map<String, Object> out, String callbackCode, String callbackData) {
		Map<String, Object> dataMap = (Map<String, Object>) JSONObject.toBean(
				JSONObject.fromObject(callbackData), Map.class);
		out.put("companyId", dataMap.get("companyId"));
		out.put("companySvrId", dataMap.get("companySvrId"));

		return null;
	}

	@RequestMapping
	public ModelAndView doChange(HttpServletRequest request,
			Map<String, Object> out, CrmCompanySvr svr, String gmtPreStartDate,
			String gmtPreEndDate, String gmtStartDate, String gmtEndDate,
			String gmtSignedDate, Integer integral) throws IOException {

		try {
			if (StringUtils.isNotEmpty(gmtPreStartDate)) {
				svr.setGmtPreStart(DateUtil
						.getDate(gmtPreStartDate, "yyyy-M-d"));
			}
			if (StringUtils.isNotEmpty(gmtPreEndDate)) {
				svr.setGmtPreEnd(DateUtil.getDate(gmtPreEndDate, "yyyy-M-d"));
			}
			svr.setGmtSigned(DateUtil.getDate(gmtSignedDate, "yyyy-M-d"));
			svr.setGmtStart(DateUtil.getDate(gmtStartDate, "yyyy-M-d"));
			svr.setGmtEnd(DateUtil.getDate(gmtEndDate, "yyyy-M-d"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Boolean r = crmCompanySvrService.updateSvr(svr, svr.getCompanyId());

		ExtResult result = new ExtResult();
		result.setSuccess(r);

		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView queryPhoneCostSvr(HttpServletRequest request,
			Map<String, Object> out, PhoneCostSvr phoneCostSvr,
			PageDto<PhoneCostSvr> page) throws IOException, ParseException {
		
		page = phoneCostSvrService.pageByAdmin(phoneCostSvr, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryCostSvrById(HttpServletRequest request,
			Map<String, Object> out,Integer id) throws IOException{
		PhoneCostSvr obj = phoneCostSvrService.queryById(id);
		if(obj==null){
			return printJson(null, out);
		}
		List<PhoneCostSvr> list =new ArrayList<PhoneCostSvr>();
		list.add(obj);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView updateSvr(HttpServletRequest request,Map<String, Object> out,Integer id,Integer companyId,Float telFee,Float clickFee) throws IOException{
		ExtResult result = new ExtResult();
		Integer i = phoneCostSvrService.updateFee(id,companyId, telFee, clickFee);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	/**
	 * 临时使用 用完即刻删除
	 * @param companyId
	 * @throws ParseException 
	 */
	@RequestMapping
	public void importPhoneToCost(String tel,String date,Map<String, Object> out) throws ParseException{
		if (StringUtils.isNotEmpty(tel)) {
			PageDto<Phone> pagePhone = new PageDto<Phone>();
			Phone phone = new Phone();
			phone.setTel(tel);
			pagePhone = phoneService.pageList(phone, pagePhone);
			for(Phone phoneObj:pagePhone.getRecords()){
				phoneService.countBalanceByAdmin(phoneObj);
			}
			out.put("page", pagePhone);
			out.put("tel", tel);
		}
		if (StringUtils.isNotEmpty(date)) {
			PageDto<PhoneLog> pagePhone = new PageDto<PhoneLog>();
			PhoneLog phoneLog = new PhoneLog();
			pagePhone.setPageSize(1000);
			phoneLog.setStartTime(DateUtil.getDate(date, "yyyy-MM-dd"));
			phoneLog.setEndTime(DateUtil.getDateAfterDays(DateUtil.getDate(date, "yyyy-MM-dd"), 1));
			pagePhone = phoneLogService.pageList(phoneLog, pagePhone);
			for(PhoneLog obj: pagePhone.getRecords()){
				PageDto<Phone> page = new PageDto<Phone>();
				Phone phone = new Phone();
				if (obj!=null&&StringUtils.isEmpty(obj.getTel())) {
					continue;
				}
				phone.setTel(obj.getTel());
				page= phoneService.pageList(phone, page);
				for(Phone phoneObj:page.getRecords()){
					phoneService.countBalanceByAdmin(phoneObj);
				}
			}
			out.put("date", date);
			out.put("page", pagePhone);
		}
	}
	
	/**
	 * 根据400号码检索 所有用过该号码的客户
	 */
	@RequestMapping
	public ModelAndView queryPhoneLibraryByTel(Map<String, Object> out,PageDto<Phone> page,String tel) throws IOException{
		Phone phone  = new Phone();
		if (StringUtils.isEmpty(tel)) {
			tel = "0";
		}
		phone.setTel(tel);
		page = phoneService.pageListForLibrary(phone, page);
		return printJson(page, out);
	}
	
	/**
	 * 关闭400号码
	 */
	@RequestMapping
	public ModelAndView closePhone(Map<String, Object> out,Integer companyId) throws IOException{
		ExtResult result = new ExtResult();
		do {
			if (companyId ==null) {
				break;
			}
			Integer i = phoneService.closePhone(companyId);
			if (i>0) {
				Phone phone = phoneService.queryByCompanyId(companyId);
				if (phone!=null) {
					result.setData(phone.getTel());
				}
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}

}

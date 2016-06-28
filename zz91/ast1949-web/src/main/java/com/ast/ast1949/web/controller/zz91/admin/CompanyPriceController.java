/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-7-29
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyPriceDTO;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.log.LogUtil;

@Controller
public class CompanyPriceController extends BaseController {

	@Autowired
	private CompanyPriceService companyPriceService;
	
	final static String CHECK_SUCCESS_OPERTION = "check_compprice_success";
	final static String CHECK_FAILURE_OPERTION = "check_compprice_failure";
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,Map<String, Object> out) {
		return null;
	}

//	@RequestMapping
//	public ModelAndView init(Integer id, Map<String, Object> out) throws IOException,
//			ParseException {
//
//		CompanyPriceDTO companyPriceDTO = companyPriceService.selectCompanyPriceById(id);
//		Date checkTime = null;
//		Date expiredTime = companyPriceDTO.getCompanyPriceDO().getExpiredTime();
//
//		if (companyPriceDTO.getCompanyPriceDO().getIsChecked() != null) {
//			if (companyPriceDTO.getCompanyPriceDO().getIsChecked().equals("0")) {
//				checkTime = companyPriceDTO.getCompanyPriceDO().getRefreshTime();
//			} else {
//				checkTime = companyPriceDTO.getCompanyPriceDO().getCheckTime();
//			}
//		}
//		Integer validTime = null;
//		if (expiredTime.equals(DateUtil.getDate("9999-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss"))) {
//			validTime = -1;
//		} else {
//			validTime = DateUtil.getIntervalDays(expiredTime, checkTime);
//		}
//		companyPriceDTO.setValidTime(validTime);
//		List<CompanyPriceDTO> list = new ArrayList<CompanyPriceDTO>();
//		list.add(companyPriceDTO);
//		PageDto pageDto = new PageDto();
//		pageDto.setRecords(list);
//		return printJson(list, out);
//
//	}

//	@RequestMapping
//	public ModelAndView update(CompanyPriceDO companyPriceDO, String validTime,
//			Map<String, Object> out) throws IOException, ParseException {
//		ExtResult result = new ExtResult();
//		if ("-1".equals(validTime)) {// 有效期为最大时间
//			companyPriceDO.setExpiredTime(DateUtil.getDate("9999-12-31 23:59:59",
//					"yyyy-MM-dd HH:mm:ss"));
//		} else {
//			Date retime = null;
//			if (companyPriceDO.getRefreshTime() != null) {
//				retime = companyPriceDO.getRefreshTime();
//			} else {
//				companyPriceDO.setRefreshTime(new Date());
//				retime = companyPriceDO.getRefreshTime();
//			}
//			Date date = DateUtil.getDateAfterDays(retime, Integer.valueOf(validTime));
//			companyPriceDO.setExpiredTime(date);
//		}
//		int i = companyPriceService.updateCompanyPrice(companyPriceDO);
//		if (i > 0) {
//			result.setSuccess(true);
//		} else {
//			result.setSuccess(false);
//		}
//		return printJson(result, out);
//
//	}

	@RequestMapping
	public ModelAndView check(HttpServletRequest request,String ids, String isChecked, Map<String, Object> out)
			throws IOException, ParseException {
		ExtResult result = new ExtResult();
		String[] entities = ids.split(",");
		int[] i = new int[entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}
		Integer impacted = companyPriceService.updateCompanyPriceCheckStatus(i, isChecked);
		if(impacted!=1){
			result.setSuccess(false);
		}else{
			result.setSuccess(true);
		}
		// 记录日志
		SessionUser sessionUser = getCachedUser(request);
		if ("0".equals(isChecked)) {
			LogUtil.getInstance().mongo(sessionUser.getAccount(), CHECK_FAILURE_OPERTION, null, "id:"+i.toString()+",date:"+DateUtil.toString(new Date(), DATE_FORMAT));
		} else {
			LogUtil.getInstance().mongo(sessionUser.getAccount(), CHECK_SUCCESS_OPERTION, null, "id:"+i.toString()+",date:"+DateUtil.toString(new Date(), DATE_FORMAT));
		}
		
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView delete(String ids, Map<String, Object> out,HttpServletRequest request) throws IOException {
		ExtResult result = new ExtResult();
		String[] entities = ids.split(",");
		Integer[] i = new Integer[entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}
		Integer impacted = companyPriceService.batchDeleteCompanyPriceById(i);
		if (impacted != 1) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
			// 记录日志
			SessionUser sessionUser = getCachedUser(request);
			LogUtil.getInstance().mongo(sessionUser.getAccount(), CHECK_FAILURE_OPERTION, null, "id:"+i.toString()+",date:"+DateUtil.toString(new Date(), DATE_FORMAT));
			
		}
		return printJson(result, out);
	}
	
	
	
	/********************以上为老代码*******************************/
	@RequestMapping
	public ModelAndView query(String title, String isChecked,String categoryCode,String isVip,
			PageDto<CompanyPriceDTO> page, Map<String, Object> out,CompanyPriceSearchDTO searchDto) throws IOException {
		if(isChecked==null){
			isChecked = "";
		}
		page = companyPriceService.pageCompanyPriceByAdmin(title, isChecked,categoryCode,isVip,searchDto, page);
		return printJson(page, out);
		
	}
	
	@RequestMapping
	public ModelAndView queryOneRecord(Integer id,Map<String, Object> out,HttpServletRequest request) throws IOException {
		CompanyPriceDTO companyPriceDTO = companyPriceService.selectCompanyPriceById(id);
		List<CompanyPriceDTO> list = new ArrayList<CompanyPriceDTO>();
		list.add(companyPriceDTO);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView move(Integer id,String categoryCode,Map<String, Object> out) throws IOException{
		Integer i = companyPriceService.updateCategory(id,categoryCode);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView update(CompanyPriceDO companyPriceDO,Map<String, Object> out) throws IOException{
		ExtResult result = new ExtResult();
		Integer i = companyPriceService.updateCompanyPriceByAdmin(companyPriceDO);
		if(i!=null && i.intValue()>0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateAndPass(HttpServletRequest request,CompanyPriceDO companyPriceDO,Map<String, Object> out) throws IOException{
		SessionUser sessionUser = getCachedUser(request);
		ExtResult result = new ExtResult();
		Integer i = companyPriceService.updateCompanyPriceByAdmin(companyPriceDO);
		companyPriceService.updateCompanyPriceCheckStatus(new int[]{companyPriceDO.getId()}, "1");
		if(i!=null && i.intValue()>0) {
			LogUtil.getInstance().mongo(sessionUser.getAccount(), CHECK_SUCCESS_OPERTION, null, "id:"+i.toString()+",date:"+DateUtil.toString(new Date(), DATE_FORMAT));
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteById(HttpServletRequest request,Map<String, Object> out,Integer id) throws IOException{
		ExtResult result = new ExtResult();
		Integer[] i = new Integer[]{id};
		Integer impacted = companyPriceService.batchDeleteCompanyPriceById(i);
		if (impacted != 1) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
			// 记录日志
			SessionUser sessionUser = getCachedUser(request);
			LogUtil.getInstance().mongo(sessionUser.getAccount(), CHECK_FAILURE_OPERTION, null, "id:"+i.toString()+",date:"+DateUtil.toString(new Date(), DATE_FORMAT));
			
		}
		return printJson(result, out);
	}
	
}

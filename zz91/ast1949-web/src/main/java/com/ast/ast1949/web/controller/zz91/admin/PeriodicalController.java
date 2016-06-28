/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-26
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.information.Periodical;
import com.ast.ast1949.domain.information.PeriodicalDetails;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.information.PeriodicalDetailsService;
import com.ast.ast1949.service.information.PeriodicalService;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.cache.MemcachedUtils;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
@Controller
public class PeriodicalController extends BaseController {

	@Autowired
	private PeriodicalService periodicalService;

	@Autowired
	private PeriodicalDetailsService periodicalDetailsService;

	@RequestMapping
	public void view(){

	}

	@RequestMapping
	public void preview(Map<String, Object> model, Integer id){
		Periodical p=periodicalService.listOnePeriodicalById(id);
		model.put("periodical",p);
		model.put("resourceUrl", MemcachedUtils.getInstance().getClient().get("baseConfig.resource_url"));
		model.put("model", "periodical");
	}

	@RequestMapping
	public ModelAndView listPeriodical(Map<String, Object> model,PageDto page) throws IOException{
//		PageDto page=new PageDto();
		page = periodicalService.pagePeriodicalWithoutSearch(page);
		return printJson(page, model);
	}

	@RequestMapping
	public ModelAndView deletePeriodical(Map<String, Object> model, Integer id) throws IOException{
		ExtResult result = new ExtResult();
		Integer[] i={id};
		if(periodicalService.batchDeletePeriodical(i)>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView	deletePeriodicalOnlyDetails(Map<String, Object> model, Integer id) throws IOException{
		ExtResult result = new ExtResult();
		Integer[] i={id};
		if(periodicalService.batchDeletePeriodicalOnlyDetails(i)>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView createPeriodical(Map<String, Object> model, Periodical periodical) throws IOException{
		ExtResult result=new ExtResult();
		if(periodicalService.createPeriodical(periodical)>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView listOnePeriodical(Map<String, Object> model, Integer id) throws IOException{
		PageDto page=new PageDto();
		List<Periodical> list=new ArrayList<Periodical>();
		list.add(periodicalService.listOnePeriodicalById(id));
		page.setRecords(list);
		return printJson(page, model);
	}

	@RequestMapping
	public ModelAndView updatePeriodical(Map<String, Object> model, Periodical periodical) throws IOException{
		ExtResult result=new ExtResult();
		if(periodicalService.updatePeriodical(periodical)>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView previewDetails(Map<String, Object> model, Integer id) throws IOException{
		PageDto page=new PageDto();
		page.setRecords(periodicalDetailsService.listPreviewDetailsByPeriodicalId(id));
		return printJson(page, model);
	}

	@RequestMapping
	public ModelAndView listPeriodicalDetails(Map<String, Object> model, Integer periodicalId, PageDto page) throws IOException{
		page=periodicalDetailsService.pageDetailsByPeriocidalId(periodicalId, page);
		return printJson(page, model);
	}

	@RequestMapping
	public ModelAndView periodicalCombo(Map<String, Object> model,PageDto page) throws IOException{
		page=periodicalService.pagePeriodicalWithoutSearch(page);
		return printJson(page, model);
	}

	@RequestMapping
	public ModelAndView changePeriodicalDetailsPagetype(Map<String, Object> model, Integer id, Integer pagetype) throws IOException{
		ExtResult result=new ExtResult();
		if(periodicalDetailsService.updatePageType(id, pagetype)>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView deletePeriodicalDetails(Map<String, Object> model, String idArray) throws IOException{
		ExtResult result=new ExtResult();
		if(periodicalDetailsService.deleteDetails(StringUtils.StringToIntegerArray(idArray))>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView updatePeriodicalDetails(Map<String, Object> model, PeriodicalDetails details) throws IOException{
		ExtResult result=new ExtResult();
		if(periodicalDetailsService.updateBaseDetails(details)>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView unzipPeriodicalDetails(Map<String, Object> model, String zipfile,Integer periodicalId) throws IOException{
		ExtResult result = new ExtResult();
		if(StringUtils.isNotEmpty(zipfile)){
			Integer i=periodicalService.unzipUploadedDetails(zipfile, periodicalId);
			if(i!=null && i>=0){
				result.setSuccess(true);
			}
		}
		return printJson(result, model);
	}

	@RequestMapping
	public void zipPeriodicalDetails(Map<String, Object> model, Integer periodicalId){

	}
}

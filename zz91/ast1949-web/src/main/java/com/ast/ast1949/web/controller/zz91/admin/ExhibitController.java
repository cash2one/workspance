/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-24
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.information.ExhibitDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.ExhibitDTO;
import com.ast.ast1949.service.information.ExhibitService;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author yuyonghui
 *
 */
@Controller
public class ExhibitController extends BaseController{

	@Autowired
	private ExhibitService exhibitService;
	
//	@RequestMapping
//	public void list(){
//		
//	}
//	
//	@RequestMapping
//	public ModelAndView add(ExhibitDO exhibitDO,Map<String, Object> out) throws IOException{
//		ExtResult result = new ExtResult();
//		int i=exhibitService.insertExhibit(exhibitDO);
//		if (i>0) {
//			result.setSuccess(true);
//		}else {
//			result.setSuccess(false);
//		}
//		return printJson(result, out);
//	}
//	@RequestMapping
//	public ModelAndView init(Integer id,Map<String, Object> out) throws IOException{
//		ExhibitDTO exhibitDTO=	exhibitService.queryExhibitById(id);
//		List<ExhibitDTO> list = new ArrayList<ExhibitDTO>();
//		list.add(exhibitDTO);
//		PageDto page = new PageDto();
//		page.setRecords(list);
//	   return printJson(page, out);	
//	}
//	@RequestMapping
//	public ModelAndView update(ExhibitDO exhibitDO,Map<String, Object> out) throws IOException{
//		ExtResult result = new ExtResult();
//		int i=exhibitService.updateExhibit(exhibitDO);
//		if (i>0) {
//			result.setSuccess(true);
//		}else {
//			result.setSuccess(false);
//		}
//		return printJson(result, out);
//	}
	
	/*************new code*******************/
	@RequestMapping
	public ModelAndView delete(Integer id,Map<String, Object> out) throws IOException{
		
		Integer i=exhibitService.deleteExhibit(id);
		
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out) throws IOException{
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView details(HttpServletRequest request, Map<String, Object> out, Integer id){
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView content(HttpServletRequest request, Map<String, Object> out, Integer id, String success){
		out.put("id", id);
		out.put("content", exhibitService.queryContent(id));
		out.put("success", success);
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryOneExhibit(HttpServletRequest request, Map<String, Object> out, 
			Integer id) throws IOException{
//		List<ExhibitDTO> exhibitList = new ArrayList<ExhibitDTO>();
//		exhibitList.add(exhibitService.queryExhibitById(id));
		return printJson(exhibitService.queryExhibitById(id), out);
	}
	
	@RequestMapping
	public ModelAndView doUpdateContent(HttpServletRequest request, Map<String, Object> out, 
			Integer id, String content) throws IOException{
		Integer i=exhibitService.updateContent(id, content);
		if(i!=null && i.intValue()>0){
			out.put("success", "1");
		}else{
			out.put("success", "0");
		}
		out.put("id", id);
		return new ModelAndView("redirect:content.htm");
	}
	
	@RequestMapping
	public ModelAndView doUpdate(HttpServletRequest request, Map<String, Object> out, 
			ExhibitDO exhibit) throws IOException{
		Integer i=exhibitService.updateExhibit(exhibit);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView doCreate(HttpServletRequest request, Map<String, Object> out, 
			ExhibitDO exhibit) throws IOException{
		Integer i=exhibitService.insertExhibit(exhibit);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
			result.setData(i);
		}
		return printJson(result, out);
	}
	
//	@RequestMapping
//	public ModelAndView doUpload(HttpServletRequest request, Map<String, Object> out) 
//			throws IOException{
//		
//		String path=MvcUpload.getModalPath(WebConst.UPLOAD_MODEL_DEFAULT);
//		String filename=UUID.randomUUID().toString();
//		ExtResult result=new ExtResult();
//		try {
//			String finalname=MvcUpload.localUpload(request, path, filename);
//			result.setSuccess(true);
//			result.setData(path.replace(MvcUpload.getDestRoot(), "")+"/"+finalname);
//		} catch (Exception e) {
//			result.setData(MvcUpload.getErrorMessage(e.getMessage()));
//		}
//		return printJson(result, out);
//	}
//	
	@RequestMapping
	public ModelAndView query(ExhibitDO exhibitDO,PageDto<ExhibitDTO> page, Map<String, Object> out) throws IOException{

		page = exhibitService.pageExhibitByAdmin(exhibitDO, page);
		
		return printJson(page, out);
	}
}

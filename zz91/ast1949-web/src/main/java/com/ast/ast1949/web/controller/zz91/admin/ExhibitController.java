/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-24
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.exhibit.Exhibitors;
import com.ast.ast1949.domain.exhibit.PreviouExhibitors;
import com.ast.ast1949.domain.information.ExhibitDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.ExhibitDTO;
import com.ast.ast1949.service.exhibit.ExhibitorsService;
import com.ast.ast1949.service.exhibit.PreviouExhibitorsService;
import com.ast.ast1949.service.information.ExhibitService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.datetime.DateUtil;

/**
 * @author yuyonghui
 *
 */
@Controller
public class ExhibitController extends BaseController{

	@Autowired
	private ExhibitService exhibitService;
	@Resource
	private ExhibitorsService exhibitorsService;
	@Resource
	private PreviouExhibitorsService previouExhibitorsService;
	
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
		content = Jsoup.clean(content, Whitelist.none().addTags("p","ul","br","li","table","th","tr","td","img","span","div").addAttributes("td","colspan","style").addAttributes("span", "style").addAttributes("img", "src"));
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
	@RequestMapping
	public ModelAndView joinExhibit(Map<String, Object>out){
		return null;
	}
	@RequestMapping
	public ModelAndView audenceExhibit(Map<String, Object>out){
		return null;
	}
	@RequestMapping
	public ModelAndView queryAllExhibitors(PageDto<Exhibitors> page,Map<String, Object>out,Exhibitors exhibitors) throws IOException{
		page.setSort("id");
		page.setDir("desc");
		page=exhibitorsService.queryAllExhibitors(page, exhibitors);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView exportData(HttpServletRequest request,HttpServletResponse response,String type)
			throws IOException, ParseException, RowsExceededException, WriteException {
		List<Exhibitors> list= exhibitorsService.queryList(type);
		response.setContentType("application/msexcel");
		OutputStream os = response.getOutputStream();
		String filename=DateUtil.toString(new Date(), "yyyy-MM-dd");
		response.setHeader("Content-disposition", "attachment; filename="+filename+".xls");// 设定输出文件
		WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄   
		WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
		ws.addCell(new Label(0,0,"公司名称"));
		ws.addCell(new Label(1,0,"负责人姓名"));
		ws.addCell(new Label(2,0,"性别"));
		ws.addCell(new Label(3,0,"职务"));
		ws.addCell(new Label(4,0,"国家和地区"));
		ws.addCell(new Label(5,0,"通讯地址"));
		ws.addCell(new Label(6,0,"邮编"));
		ws.addCell(new Label(7,0,"电话"));
		ws.addCell(new Label(8,0,"传真"));
		ws.addCell(new Label(9,0,"手机"));
		ws.addCell(new Label(10,0,"Email"));
		ws.addCell(new Label(11,0,"公司网址"));
		int i=1;
		for(Exhibitors obj:list){
			ws.addCell(new Label(0,i,obj.getCompanyName()));
			ws.addCell(new Label(1,i,obj.getName()));
			if (obj.getSex().equals("0")) {
				ws.addCell(new Label(2,i,"男"));
			}else {
				ws.addCell(new Label(2,i,"女"));
			}
			ws.addCell(new Label(3,i,obj.getJob()));
			ws.addCell(new Label(4,i,obj.getArea()));
			ws.addCell(new Label(5,i,obj.getAddress()));
			ws.addCell(new Label(6,i,obj.getAddressZip()));
			ws.addCell(new Label(7,i,obj.getTel()));
			ws.addCell(new Label(8,i,obj.getFex()));
			ws.addCell(new Label(9,i,obj.getMobile()));
			ws.addCell(new Label(10,i,obj.getEmail()));
			ws.addCell(new Label(11,i,obj.getWebsite()));
			
			i++;
		}
		//现在可以写了   
		wwb.write();
		//写完后关闭   
		wwb.close();
		//输出流也关闭吧   
		os.close(); 
		return null;
	}
	@RequestMapping
	public ModelAndView previouExhibitors(HttpServletRequest request,Map<String, Object>out){
		return null;
	}
	@RequestMapping
	public ModelAndView queryList(HttpServletRequest request,Map<String, Object>out,PageDto<PreviouExhibitors> page,PreviouExhibitors previouExhibitors) throws IOException{
		page.setSort("id");
		page.setDir("desc");
		page=previouExhibitorsService.queryList(page, previouExhibitors);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView queryPreviouExhibitorsById(Map<String, Object>out,Integer id) throws IOException{
		List<PreviouExhibitors> list=new ArrayList<PreviouExhibitors>();
		PageDto<PreviouExhibitors> page=new PageDto<PreviouExhibitors>();
		if (id!=null) {
			PreviouExhibitors previouExhibitors=previouExhibitorsService.queryPreviouExhibitorsById(id);
			list.add(previouExhibitors);
			page.setRecords(list);
		}
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView updatePreviouExhibitors(Map<String, Object>out,HttpServletRequest request,PreviouExhibitors previouExhibitors) throws IOException{
		ExtResult result=new ExtResult();
		String msg="操作失败";
		if (previouExhibitors!=null&&previouExhibitors.getId()!=null) {
			Integer i=previouExhibitorsService.updatePreviouExhibitors(previouExhibitors);
			if (i!=null&&i.intValue()>0) {
				 msg="修改成功";
				result.setData(msg);
				result.setSuccess(true);
			}
		}else {
			Integer i=previouExhibitorsService.insert(previouExhibitors);
			if (i!=null&&i.intValue()>0) {
				msg="添加成功";
				result.setData(msg);
				result.setSuccess(true);
			}
		}
		
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView delPreviouExhibtors(Map<String, Object>out,HttpServletRequest request,Integer id) throws IOException{
		ExtResult result=new ExtResult();
		if (id!=null&&id.intValue()>0) {
			Integer i=previouExhibitorsService.delPreviouExhibitors(id);
			if (i!=null&&i.intValue()>0) {
				result.setSuccess(true);
			}
		}
	  return printJson(result, out);
	}
}

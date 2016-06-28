/**
 * @author shiqp
 * @date 2016-01-18
 */
package com.ast.feiliao91.mobile.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.goods.Picture;
import com.ast.feiliao91.service.company.JudgeService;
import com.ast.feiliao91.service.goods.PictureService;
import com.zz91.util.file.MvcUpload;

import net.sf.json.JSONObject;

@Controller
public class UploadController extends BaseController{
	@Resource
	private PictureService pictureService;
	@Resource
	private JudgeService JydgeService;
	@RequestMapping
	public ModelAndView ieupload(HttpServletRequest request,Map<String, Object> out,HttpServletResponse response) throws IOException{
		String targetType = request.getParameter("targetType"); //检测报告1 产品图片2 评论图片3　认证图片4 退货图片5
		SsoUser user = getCachedUser(request);
		Map<String,String> map = new HashMap<String,String>();
		String filename = UUID.randomUUID().toString();
		String path = MvcUpload.getModalPath("feiliao91");
		String finalname = "";
		try {
			finalname = MvcUpload.localUpload(request, path, filename);
		} catch (Exception e) {
			finalname = filename;
		}
		//保存在数据库
		Picture picture = new Picture();
		picture.setCompanyId(user.getCompanyId());
		String picAddress = "/feiliao91/"+MvcUpload.getDateFolder();
		picture.setPicAddress(picAddress+"/"+finalname);
		picture.setTargetType(targetType); 
		picture.setTargetId(0);
		Integer i = pictureService.createPicture(picture);
		map.put("url", picture.getPicAddress());
		map.put("pid", i.toString()); //跟强制转化有什么区别
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView ie6upload(HttpServletRequest request,Map<String, Object> out,String destUrl) throws IOException{
		String type= request.getParameter("ptype");
		SsoUser user = getCachedUser(request);
		JSONObject job = new JSONObject();
		String filename = UUID.randomUUID().toString();
		String path = MvcUpload.getModalPath("feiliao91");
		String finalname = "";
		try {
			finalname = MvcUpload.localUpload(request, path, filename);
		} catch (Exception e) {
			finalname = filename;
		}
		//保存在数据库
		Picture picture = new Picture();
		picture.setCompanyId(user.getCompanyId());
		String picAddress = "/feiliao91/"+MvcUpload.getDateFolder();
		picture.setPicAddress(picAddress+"/"+finalname);
		picture.setTargetType(type);
		picture.setTargetId(0);
		Integer i = pictureService.createPicture(picture);
		job.put("url", picture.getPicAddress());
		job.put("pid", i.toString()); //跟强制转化有什么区别
		out.put("destUrl", destUrl);
		if (i>0) {
			out.put("success", true);
			out.put("data", job.toString());
		}
//		return printJson(job.toString(), out);
		return new ModelAndView("submitCallback");
	}
	
}

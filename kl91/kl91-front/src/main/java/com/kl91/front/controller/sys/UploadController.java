package com.kl91.front.controller.sys;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kl91.domain.company.UploadPic;
import com.kl91.domain.dto.ExtResult;
import com.kl91.front.controller.BaseController;
import com.kl91.service.company.UploadPicService;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.file.ScaleImage;
import com.zz91.util.http.HttpUtils;

@Controller
public class UploadController extends BaseController{
	
	@Resource
	private UploadPicService uploadPicService;
	
	@RequestMapping
	public ModelAndView product(HttpServletRequest request, Map<String, Object> out, 
			Integer id, String destUrl, String error, String name,UploadPic uploadPic){
		if(error==null){
			error="";
		}
		if(name==null){
			name="";
		}
		try {
			out.put("error", URLDecoder.decode(error, HttpUtils.CHARSET_UTF8));
			out.put("name", URLDecoder.decode(name, HttpUtils.CHARSET_UTF8));
		} catch (UnsupportedEncodingException e) {
		}
		
		out.put("id", id);
		out.put("destUrl", destUrl);
		out.put("data", JSONObject.fromObject(uploadPic).toString());
		return null;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping
	public ModelAndView doProduct(HttpServletRequest request, Map<String, Object> out, 
			String destUrl, Integer id, String name,UploadPic uploadPic) throws Exception{
		if(id==null){
			id=0;
		}
		
		if(destUrl==null){
			destUrl="";
		}
		
		String finalname=null;
		String error=null;
		String filename=UUID.randomUUID().toString();
		String path="/"+UploadPicService.MODEL_KL91+"/"+MvcUpload.getDateFolder();
		finalname=MvcUpload.localUpload(request, MvcUpload.getDestRoot()+path, filename);
		ScaleImage is = new ScaleImage();
		is.saveImageAsJpg(MvcUpload.getDestRoot()+path+"/"+finalname, MvcUpload.getDestRoot()+path+"/"+UploadPicService.THUMB_PREFIX+finalname, 250, 250);
		uploadPic.setThumbPath(path+"/"+UploadPicService.THUMB_PREFIX+finalname);
		uploadPic.setCid(2);
		uploadPic.setFilePath(path);
		uploadPic.setTargetType(UploadPicService.TARGETTYPE_OF_PRODUCTS);
		uploadPic.setTargetId(0);
		Integer i=uploadPicService.createUploadPic(uploadPic);
		uploadPic.setId(i);

		if(error!=null){
			out.put("id", id);
			out.put("destUrl", destUrl);
			try {
				out.put("name", URLEncoder.encode(name, HttpUtils.CHARSET_UTF8));
				out.put("error", URLEncoder.encode(error, HttpUtils.CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
			}
			return new ModelAndView("redirect:product.htm");
		}else{
			out.put("success", "1");
			out.put("data", JSONObject.fromObject(uploadPic).toString());
			out.put("destUrl", destUrl);
			return null;
		}
		
	}
	
	@RequestMapping
	public void deletePic(HttpServletRequest request, Map<String, Object> model,String ids,Integer productsId){
		
	}
	@RequestMapping
	public ModelAndView deleteOnlyPic(HttpServletRequest request, Map<String, Object> out,Integer id) throws IOException{
		Integer[] ids=new Integer[1];//picids.split(",");
		ids[0]=id;
		uploadPicService.deleteById(id);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView file(HttpServletRequest request, Map<String, Object> out, 
			Integer id, String destUrl, String error, String name,UploadPic uploadPic){
		if(error==null){
			error="";
		}
		if(name==null){
			name="";
		}
		try {
			out.put("error", URLDecoder.decode(error, HttpUtils.CHARSET_UTF8));
			out.put("name", URLDecoder.decode(name, HttpUtils.CHARSET_UTF8));
		} catch (UnsupportedEncodingException e) {
		}
		
		out.put("id", id);
		out.put("destUrl", destUrl);
		out.put("data", JSONObject.fromObject(uploadPic).toString());
		return null;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping
	public ModelAndView doFile(HttpServletRequest request, Map<String, Object> out, 
			String destUrl, Integer id, String name,UploadPic uploadPic) throws Exception{
		if(id==null){
			id=0;
		}
		
		if(destUrl==null){
			destUrl="";
		}
		
		String finalname=null;
		String error=null;
		String filename=UUID.randomUUID().toString();
		String path=UploadPicService.MODEL_KL91+"/"+MvcUpload.getDateFolder();
		String realPath =MvcUpload.getDestRoot()+ "/" + path;
		
		finalname=MvcUpload.localUpload(request, realPath, filename);
		
		ScaleImage is = new ScaleImage();
		is.saveImageAsJpg(realPath+"/"+finalname, realPath+"/"+UploadPicService.THUMB_PREFIX+finalname, 250, 250);
		
		uploadPic.setThumbPath(path+"/"+UploadPicService.THUMB_PREFIX+finalname);
		uploadPic.setCid(2);
		uploadPic.setFilePath(path+"/"+finalname);
		uploadPic.setTargetType(UploadPicService.TARGETTYPE_OF_CREDIT);
		uploadPic.setTargetId(0);
		Integer i=uploadPicService.createUploadPic(uploadPic);
		uploadPic.setId(i);

		if(error!=null){
			out.put("id", id);
			out.put("destUrl", destUrl);
			try {
				out.put("name", URLEncoder.encode(name, HttpUtils.CHARSET_UTF8));
				out.put("error", URLEncoder.encode(error, HttpUtils.CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
			}
			return new ModelAndView("redirect:file.htm");
		}else{
			out.put("success", "1");
			out.put("data", JSONObject.fromObject(uploadPic).toString());
			out.put("destUrl", destUrl);
			return null;
		}
		
	}
	
}

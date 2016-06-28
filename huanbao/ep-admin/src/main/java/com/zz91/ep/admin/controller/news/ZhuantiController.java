package com.zz91.ep.admin.controller.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.news.NewsZhuantiService;
import com.zz91.ep.admin.service.sys.ParamService;
import com.zz91.ep.admin.service.trade.PhotoService;
import com.zz91.ep.domain.news.Zhuanti;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.ZhuantiDto;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.domain.Param;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;

/**
 * 专题管理controller
 * @author 黄怀清
 * 2012.09.12
 *
 */
@Controller
public class ZhuantiController extends BaseController{
	public static final String[] WHITE_DOC={".doc",".docx",".xls", ".pdf",".jpg", ".jpeg", ".gif", ".bmp", ".png"};
	public static final String[] BLOCK_ANY={".bat", ".sh", ".exe"};
	
	@Resource
	private NewsZhuantiService zhuantiService;
	@Resource
	private ParamService paramService;
	@Resource
	private PhotoService photoService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		Map<String,String> map=new HashMap<String, String>();
		List<Param> params = paramService.queryParamByType("zhuanti_category");
		for (Param param:params) {
			map.put(param.getKey(), param.getName());
		}
		out.put("recommendType", map);
		return null;
	}
	
	/**
	 * 查询专题类别
	 * @param request
	 * @param out
	 * @param parentCode
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView zhuantiCategory(HttpServletRequest request, Map<String, Object> out) 
			throws IOException{
		List<ExtTreeDto> categoryNode = zhuantiService.queryZhuantiCategory("zhuanti_category");
		return printJson(categoryNode, out);
	}
	
	/**
	 * 根据类别查询专题列表
	 * @param request
	 * @param out
	 * @param page
	 * @param category
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryZhuanti(HttpServletRequest request, Map<String, Object> out,PageDto<ZhuantiDto> page,
			Zhuanti zhuanti) 
			throws IOException{
		page = zhuantiService.pageZhuanti(zhuanti, page);
		
		out.put("category", zhuanti.getCategory());
		return printJson(page, out);
	}
	
	/**
	 * 新增专题
	 * @param request
	 * @param out
	 * @param zhuanti
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView createZhuanti(HttpServletRequest request, Map<String, Object> out, Zhuanti zhuanti,
			String gmtPublishStr)throws IOException{
		ExtResult result=new ExtResult();
		try {
			zhuanti.setGmtPublish((DateUtil.getDate(gmtPublishStr, "yyyy-MM-dd HH:mm:ss")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Integer i=zhuantiService.create(zhuanti);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateZhuanti(HttpServletRequest request, Zhuanti zhuanti, Map<String, Object> out,
			String gmtPublishStr)throws IOException{
		ExtResult result=new ExtResult();
		try {
			zhuanti.setGmtPublish((DateUtil.getDate(gmtPublishStr, "yyyy-MM-dd HH:mm:ss")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Integer i=zhuantiService.update(zhuanti);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView recommend(HttpServletRequest request, Zhuanti zhuanti, Map<String, Object> out) 
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i=zhuantiService.recommend(zhuanti);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView attention(HttpServletRequest request, Zhuanti zhuanti, Map<String, Object> out) 
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i=zhuantiService.attention(zhuanti);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryZhuantiDetail(HttpServletRequest request, Integer id, Map<String, Object> out) 
			throws IOException{
		List<ZhuantiDto> list=new ArrayList<ZhuantiDto>();
		Zhuanti zhuanti=zhuantiService.queryZhuantiDetail(id);
		
		ZhuantiDto dto=new ZhuantiDto();
		dto.setZhuanti(zhuanti);
		dto.setCategoryName(ParamUtils.getInstance().getValue("zhuanti_category", zhuanti.getCategory()));
		dto.setGmtPublishStr(DateUtil.toString(zhuanti.getGmtPublish(),"yyyy-MM-dd HH:mm:ss"));
		list.add(dto);
		return printJson(list, out);
	}
	
	/**
	 * 上传文档(供下载)
	 * @param out
	 * @param request
	 * @param targetId
	 * @return
	 */
	@RequestMapping
    public ModelAndView uploadFile(Map<String, Object> out, HttpServletRequest request, Integer id) {
    	String filename=String.valueOf(System.currentTimeMillis());
    	String path=buildPath("news/zhuanti");
    	String uploadedFile=null;
		try {
			uploadedFile= MvcUpload.localUpload(request, UPLOAD_ROOT+path, filename,
					DEFAULT_FIELD, WHITE_DOC, BLOCK_ANY, 20*1024);//最大上传20M
		} catch (Exception e) {
			out.put("data", MvcUpload.getErrorMessage(e.getMessage()));
		}
    	if(StringUtils.isNotEmpty(uploadedFile)){
    		Photo photo=new Photo();
    		photo.setUid(0);
    		photo.setCid(0);
    		photo.setPhotoAlbumId(0);
    		photo.setPath(path+"/"+uploadedFile);
    		photo.setTargetId(id);
    		photo.setTargetType("news_zhuanti");
			Integer i=photoService.createPhoto(photo);
			if(i!=null && i.intValue()>0){
				out.put("success",true);
				out.put("data",path+"/"+uploadedFile);
			}
    	}
    	return printJson(out, out);
    }
	
	@RequestMapping
    public ModelAndView deleteZhuanti(HttpServletRequest request,Integer id,Map<String, Object> out) throws IOException{
		ExtResult result=new ExtResult();
		Integer i=zhuantiService.remove(id);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}

package com.zz91.ep.admin.controller.exhibit;

import java.io.IOException;
import java.text.ParseException;
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
import com.zz91.ep.admin.service.exhibit.ExhibitIndustryCategoryService;
import com.zz91.ep.admin.service.exhibit.ExhibitService;
import com.zz91.ep.admin.service.sys.ParamService;
import com.zz91.ep.admin.service.trade.PhotoService;
import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.domain.exhibit.ExhibitIndustryCategory;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.exhibit.ExhibitDto;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.domain.Param;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.lang.StringUtils;

/**
 * 展会管理controller
 * @author Leon
 * 2011.10.20
 *
 */
@Controller
public class ExhibitController extends BaseController {
	@Resource 
	private ExhibitService exhibitService;
	@Resource
	private ExhibitIndustryCategoryService exhibitIndustryCategoryService;
	@Resource
	private ParamService paramService;
	@Resource
	private PhotoService photoService;
	
	public static final String[] WHITE_DOC={".doc",".docx",".xls", ".pdf",".jpg", ".jpeg", ".gif", ".bmp", ".png"};
	public static final String[] BLOCK_ANY={".bat", ".sh", ".exe"};
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		Map<String,String> map=new HashMap<String, String>();
		List<Param> params = paramService.queryParamByType("exhibit_recommend_type");
		for (Param param:params) {
			map.put(param.getKey(), param.getValue());
		}
		out.put("recommendType", map);
		return null;
	}
	
	@RequestMapping
	public ModelAndView details(HttpServletRequest request, Map<String, Object> out, Integer id){
		out.put("id", id);
		return null;
	}
	
	/**
	 * 展会列表分页
	 */
	@RequestMapping
	public ModelAndView queryExhibit(HttpServletRequest request, Map<String, Object> out, 
			 String plateCategoryCode, String name, Integer pauseStatus, String recommendType,
			  PageDto<ExhibitDto> page){
		if (page.getSort()==null){
			page.setSort("gmt_publish");
			page.setDir("desc");
		}
		page = exhibitService.pageExhibitByAdmin(name, pauseStatus, plateCategoryCode,recommendType, page);
		return printJson(page, out);
	}
	/**
	 * 创建展会
	 */
	@RequestMapping
	public ModelAndView createExhibit(HttpServletRequest request, Map<String, Object> out, Exhibit exhibit, 
			String gmtStartStr, String gmtEndStr, String gmtPublishStr, String gmtSortStr) 
			throws IOException{
		ExtResult result=new ExtResult();
		if(StringUtils.isNotEmpty(gmtStartStr)&&StringUtils.isNotEmpty(gmtEndStr)
				&&StringUtils.isNotEmpty(gmtPublishStr)&&StringUtils.isNotEmpty(gmtSortStr)){
			try {
				exhibit.setGmtStart((DateUtil.getDate(gmtStartStr, "yyyy-MM-dd HH:mm:ss")));
				exhibit.setGmtEnd((DateUtil.getDate(gmtEndStr, "yyyy-MM-dd HH:mm:ss")));
				exhibit.setGmtPublish((DateUtil.getDate(gmtPublishStr, "yyyy-MM-dd HH:mm:ss")));
				exhibit.setGmtSort((DateUtil.getDate(gmtSortStr, "yyyy-MM-dd HH:mm:ss")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Integer i=exhibitService.createExhibitByAdmin(exhibit, getCachedUser(request).getAccount());
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
//		LogUtil logUtil = LogUtil.getInstance();
//		logUtil.init("web.properties");
//		logUtil.log(1, "amdin", "192.168.2.111", new Date(), "添加展会信息");
		return printJson(result, out);
	}
	/**
	 * 更新展会资讯
	 * @param request
	 * @param exhibit
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateExhibit(HttpServletRequest request, Exhibit exhibit, Map<String, Object> out,
			String gmtStartStr, String gmtEndStr, String gmtPublishStr, String gmtSortStr) 
			throws IOException{
		ExtResult result=new ExtResult();
		if(StringUtils.isNotEmpty(gmtStartStr)&&StringUtils.isNotEmpty(gmtEndStr)
				&&StringUtils.isNotEmpty(gmtPublishStr)&&StringUtils.isNotEmpty(gmtSortStr)){
			try {
				exhibit.setGmtStart((DateUtil.getDate(gmtStartStr, "yyyy-MM-dd HH:mm:ss")));
				exhibit.setGmtEnd((DateUtil.getDate(gmtEndStr, "yyyy-MM-dd HH:mm:ss")));
				exhibit.setGmtPublish((DateUtil.getDate(gmtPublishStr, "yyyy-MM-dd HH:mm:ss")));
				exhibit.setGmtSort((DateUtil.getDate(gmtSortStr, "yyyy-MM-dd HH:mm:ss")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Integer i=exhibitService.updateExhibit(exhibit);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	/**
	 * 删除展会资讯
	 */
	@Deprecated
	@RequestMapping
    public ModelAndView deleteExhibit(HttpServletRequest request,Integer id,Map<String, Object> out) throws IOException{
		ExtResult result=new ExtResult();
		Integer i=exhibitService.deleteExhibit(id);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updatePauseStatus(HttpServletRequest request, Map<String, Object> out,
			Integer id, Integer pauseStatus){
		
		Integer i=exhibitService.updateStatus(id, pauseStatus);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
//		LogUtil.getInstance().log(1000, getCachedUser(request).getAccount(), i, null, "{'pauseStatus':"+pauseStatus+"}");
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryOneExhibit(HttpServletRequest request, Map<String, Object> out,
			Integer id){
		List<ExhibitDto> list=new ArrayList<ExhibitDto>();
		Exhibit exhibit=exhibitService.queryExhibitDetailsById(id);
		
		ExhibitDto dto=new ExhibitDto();
		dto.setPlateCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_EXHIBIT, exhibit.getPlateCategoryCode()));
		dto.setIndustryCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_EXHIBIT, exhibit.getIndustryCode()));
		dto.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, exhibit.getAreaCode()));
		dto.setProviceName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_EXHIBIT, exhibit.getProvinceCode()));
		dto.setExhibit(exhibit);
		list.add(dto);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryInsdustry(HttpServletRequest request, Map<String, Object> out){
		List<ExhibitIndustryCategory> list = exhibitIndustryCategoryService.queryExhibitIndustryCategoryAll();
		return printJson(list, out);
	}
	
	/**
	 * 展会上传文件查询
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryPhotoList(HttpServletRequest request, Map<String, Object> out,
			Integer id){
		List<Photo> list=photoService.queryPhotoByTargetType("exhibit", id);
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
    	String path=buildPath("exhibit");
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
    		photo.setTargetType("exhibit");
			Integer i=photoService.createPhoto(photo);
			if(i!=null && i.intValue()>0){
				out.put("success",true);
				out.put("data",path+"/"+uploadedFile);
			}
    	}
    	return printJson(out, out);
    }
	
	/**
	 * 删除上传文件
	 * @param request
	 * @param out
	 * @param id
	 * @param path
	 * @return
	 */
	@RequestMapping
	public ModelAndView deleteUploadFile(HttpServletRequest request, Map<String, Object> out,Integer id,String path){
		ExtResult result=new ExtResult();
		Integer i=photoService.deletePhotoById(id);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

}

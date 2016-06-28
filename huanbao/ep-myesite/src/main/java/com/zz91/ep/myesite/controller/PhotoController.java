/**
 * 
 */
package com.zz91.ep.myesite.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.service.common.MyEsiteService;
import com.zz91.ep.service.mblog.MBlogInfoService;
import com.zz91.ep.service.trade.PhotoAlbumService;
import com.zz91.ep.service.trade.PhotoService;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.file.ScaleImage;
import com.zz91.util.lang.StringUtils;

@Controller
public class PhotoController extends BaseController {
	@Resource
	private PhotoService photoService;
	@Resource
	private TradeSupplyService tradeSupplyService;
	@Resource
	private PhotoAlbumService photoAlbumService;
	@Resource
	private MyEsiteService myEsiteService;
	@Resource
	private MBlogInfoService mBlogInfoService;
	
	private static String UPLOAD_URL = "huanbao/resources";  // 上传路径

	public final static String CHECK_STATUS_0 = "0";// 0:未审核
	public final static String CHECK_STATUS_1 = "1";// 1:审核通过
	public final static String CHECK_STATUS_2 = "2";// 2:审核不通过
	
	final static Map<String, Integer> PHOTO_ALBUM=new HashMap<String, Integer>();
	static {
		PHOTO_ALBUM.put("supply", -2);
		PHOTO_ALBUM.put("company", -1);
	}
	
	/**
     * 更新图片targetId
     * @param out
     * @param request
     * @return
     */
	@Deprecated
    @SuppressWarnings("static-access")
	@RequestMapping
    public ModelAndView updatePhotoCover(Map<String, Object> out, HttpServletRequest request, 
    		Integer id, String path,String type,String photos) {
        ExtResult result = new ExtResult();
        do {
        	if (id == null) {
        		break;
        	}
        	photoService.deletePhotoTargetIdById(type, id);
    		if(StringUtils.isNotEmpty(photos)) {
        		String imgIds[] = photos.split(",");
        		for (int i = 0; i < imgIds.length; i++) {
        			if (StringUtils.isNotEmpty(imgIds[i])) {
    					Integer imgid = Integer.parseInt(imgIds[i]);
    					photoService.updatePhotoTargetIdById(imgid, id, null);
					}
				}
        	}
    		if (photoService.TARGET_SUPPLY.equals(type)) {
    			if(path.contains(",")){
    				path=path.split(",")[0];
    			}
    			tradeSupplyService.updatePhotoCoverById(id, path, getCompanyId(request));
			}
    		result.setSuccess(true);
        } while (false);
        return printJson(result, out);
    }
    
    @Deprecated
    @RequestMapping
    public ModelAndView uploadMini(Map<String, Object> out, HttpServletRequest request, 
    		String photoType, Integer id, Integer max) {
    	out.put("photoType", photoType);
    	
    	out.put("maxInsert",max);
    	
    	if(id != null && id > 0) {
    		List<Photo> photos = photoService.queryPhotoByTargetType(photoType, id);
    		out.put("photos", photos);
    	}
    	out.put("albums", photoAlbumService.queryAlbumByCid(getCompanyId(request), null));
    	myEsiteService.init(out, getCachedUser(request).getCid());
    	return null;
    }
    
	@RequestMapping
	public ModelAndView promptUpload(HttpServletRequest request,
			Map<String, Object> out, String targetType, Integer targetId,
			Integer max, Integer albumId) {
		
//		EpAuthUser cachedUser = getCachedUser(request);
//		out.put("albums", photoAlbumService.queryAlbumByCid(cachedUser.getCid(), null));
		
		if(max==null){
			max=1;
		}
		
		out.put("targetType", targetType);
		out.put("targetId", targetId);
		out.put("albumId", albumId);
		out.put("max", max);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return null;
	}
	
	@RequestMapping
	public ModelAndView doPromptUpload(HttpServletRequest request, Map<String, Object> out,
			String targetType, Integer targetId, Integer albumId){
		//TODO model should be defined by caller, and the default model is DEFAULT_MODEL="huanbao"
		String filename=String.valueOf(System.currentTimeMillis());
		String destRoot = MvcUpload.getDestRootByServer(UPLOAD_URL);
		String path="/huanbao/"+MvcUpload.getDateFolder();
		if (targetType.equals("credit")){
			path = "/credit/"+MvcUpload.getDateFolder();
		}
    	
    	String uploadedFile=null;
    	String fullUri = null;
		ExtResult result = new ExtResult();
		try {
			uploadedFile = MvcUpload.localUpload(request, destRoot+path, filename);
			fullUri = path+"/"+uploadedFile;
			ScaleImage is = new ScaleImage();
			is.saveImageAsJpg(destRoot+fullUri, destRoot+fullUri, 800, 800);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	if(result.getSuccess()){
    		//保存图片信息
//    		Integer id = (int) (Math.random()*100);
    		if(albumId==null && StringUtils.isNotEmpty(targetType)){
    			albumId = PHOTO_ALBUM.get(targetType);
    		}
    		if(albumId==null){
    			albumId=0;
    		}
    		
    		Photo photo = new Photo();
    		EpAuthUser cachedUser = getCachedUser(request);
    		photo.setUid(cachedUser.getUid());
    		photo.setCid(cachedUser.getCid());
    		
    		photo.setPhotoAlbumId(albumId);
    		photo.setPath(fullUri);
    		photo.setPathThumb(fullUri);
    		photo.setTargetType(targetType);
    		if(targetId==null){
    			targetId = 0;
    		}
    		photo.setTargetId(targetId);
    		
    		//高会图片上传图片不用流入审核
			if (cachedUser != null && "10011001".equals(cachedUser.getMemberCode())) {
				photo.setCheckStatus(CHECK_STATUS_1);
			} else{
				photo.setCheckStatus(CHECK_STATUS_0);
			}
    		
			Integer id = photoService.createPhoto(photo);
			
			//修改图片或者上传图片的时候审核状态为未审核
			if (cachedUser != null && "10011001".equals(cachedUser.getMemberCode())) {
			}else{
				if(id>0){
					tradeSupplyService.updateUncheckByIdForMyesite(targetId);
				}
			}
			
    		result.setData(JSONObject.fromObject("{id:"+id+",path:'"+fullUri+"'}"));

    	}
		
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView ieupload(HttpServletRequest request, Map<String, Object> out,
			String targetType, Integer targetId, Integer albumId, String resultid){
		//TODO model should be defined by caller, and the default model is DEFAULT_MODEL="huanbao"
		out.put("resultid", resultid);
		
		String filename=String.valueOf(System.currentTimeMillis());
		String destRoot = MvcUpload.getDestRootByServer(UPLOAD_URL);
		String path="/huanbao/"+MvcUpload.getDateFolder();
		if (targetType.equals("credit")){
			path = "/credit/"+MvcUpload.getDateFolder();
		}
    	
    	String uploadedFile=null;
    	String fullUri = null;
		ExtResult result = new ExtResult();
		try {
			uploadedFile = MvcUpload.localUpload(request, destRoot+path, filename);
			fullUri = path+"/"+uploadedFile;
			ScaleImage is = new ScaleImage();
			is.saveImageAsJpg(destRoot+fullUri, destRoot+fullUri, 800, 800);
			result.setSuccess(true);
		} catch (Exception e) {
			
		}
		
    	if(result.getSuccess()){
    		//保存图片信息
//    		Integer id = (int) (Math.random()*100);
    		if(albumId==null && StringUtils.isNotEmpty(targetType)){
    			albumId = PHOTO_ALBUM.get(targetType);
    		}
    		if(albumId==null){
    			albumId=0;
    		}
    		
    		Photo photo = new Photo();
    		EpAuthUser cachedUser = getCachedUser(request);
    		photo.setUid(cachedUser.getUid());
    		photo.setCid(cachedUser.getCid());
    		
    		photo.setPhotoAlbumId(albumId);
    		photo.setPath(fullUri);
    		photo.setPathThumb(fullUri);
    		photo.setTargetType(targetType);
    		if(targetId==null){
    			targetId = 0;
    		}
    		photo.setTargetId(targetId);
    		
    		//高会图片上传图片也不用流入审核
			if (cachedUser != null && "10011001".equals(cachedUser.getMemberCode())) {
				photo.setCheckStatus(CHECK_STATUS_1);
			} else{
				photo.setCheckStatus(CHECK_STATUS_0);
			}
    		
			Integer id = photoService.createPhoto(photo);
			
    		//result.setData(JSONObject.fromObject("{id:"+id+",path:'"+fullUri+"'}"));
			out.put("pid", id);
			out.put("path", fullUri);
			myEsiteService.init(out, getCachedUser(request).getCid());
    	}
		
		return new ModelAndView();
	}
    
	@Deprecated
    @RequestMapping
    public ModelAndView doUploadPhoto(Map<String, Object> out, HttpServletRequest request, Photo photo,Integer albumId) {
    	do {
    		// 数据验证
	    	if(photo.getTargetType() == null) {
	    		out.put("data","请选择您要上传的相册！");
	    		break;
			}
	    	// 是否超出相册容量
	    	Integer max = 0;
//	    	Integer max = photoService.queryPhotosByCidCount(getCompanyId(request),albumId);
	    	if(PhotoService.TARGET_COMPANY.equals(photo.getTargetType()) && max >= 20) {
	    		out.put("data","您的公司相册图片超出上限！");
	    		break;
	    	}
	    	if(PhotoService.TARGET_SUPPLY.equals(photo.getTargetType()) && max >= 100) {
	    		out.put("data","您的产品相册图片超出上限！");
	    		break;
	    	}
	    	String filename=String.valueOf(System.currentTimeMillis());
	    	String path=buildPath("huanbao");
	    	String uploadedFile=null;
			try {
				uploadedFile = MvcUpload.localUpload(request, UPLOAD_ROOT+path, filename);
				//改为缩略图
				ScaleImage is = new ScaleImage();
				is.saveImageAsJpg(UPLOAD_ROOT+path+"/"+uploadedFile, UPLOAD_ROOT+path+"/"+uploadedFile, 800, 800);
			} catch (Exception e) {
				out.put("data", MvcUpload.getErrorMessage(e.getMessage()));
				break;
			}
			String fullPath = path+"/"+uploadedFile;
	    	if(StringUtils.isNotEmpty(uploadedFile)) {
	    		photo.setUid(getUid(request));
	    		photo.setCid(getCompanyId(request));
	    		if (albumId==null){
	    			albumId=0;
	    		}
	    		photo.setPhotoAlbumId(albumId);
	    		photo.setPath(fullPath);
	    		photo.setPathThumb(fullPath);
	    		photo.setTargetId(getCompanyId(request));
	    		try {
	    			Integer count = photoService.createPhoto(photo);
	    			if (count!=null && count > 0) {
	    				photo.setId(count);
	    				out.put("success",true);
	    				out.put("data","{id: "+count+",path: '"+fullPath+"'}");
	    			}
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
    	} while (false);
    	return null;
    }
    
    /**
     * 删除图片
     * @param out
     * @param request
     * @param id
     * @param path
     * @return
     */
	@Deprecated
    @RequestMapping
    public ModelAndView deletePhoto(Map<String, Object> out, HttpServletRequest request, Integer id, String path) {
    	ExtResult result = new ExtResult();
    	do {
    		if (id == null) {
    			break;
    		}
    		Integer count = photoService.deletePhotoById(id, UPLOAD_ROOT+path);
    		if (count != null && count > 0) {
    			result.setSuccess(true);
    		}
		} while (false);
		return printJson(result, out);
    }
    
    /**
     * 获取图片
     * @param out
     * @param request
     * @param photoType
     * @param page
     * @return
     */
	@Deprecated
    @RequestMapping
    public ModelAndView getPhotos(Map<String, Object> out, HttpServletRequest request,  PageDto<Photo> page,Integer albumId) {
    		Map<String, Object> map= new HashMap<String, Object>();
    		page = photoService.pagePhotosByCid(getCompanyId(request),page,albumId, null);
    		map.put("page", page);
    		
    	return printJson(map, out);
    }
    
    
    /****
     * 以下为新版生意管家方法代码 
     * @author qizj
     * @param out
     * @param request
     * @param id
     * @param path
     * @return
     */
    @RequestMapping
    public ModelAndView doAjaxRemove(Map<String, Object> out,HttpServletRequest request,Integer id,String path){
    	ExtResult result = new ExtResult();
    	do {
    		if (id == null) {
    			break;
    		}
    		Integer count = photoService.deletePhotoById(id, null);
    		if (count != null && count > 0) {
    			result.setSuccess(true);
    		}
		} while (false);
		return printJson(result, out);
    }
    
    @RequestMapping
    public ModelAndView doRemove(Map<String, Object> out,HttpServletRequest request,Integer id, String path, String destUrl){
//    	ExtResult result = new ExtResult();
    	out.put("success", 0);
    	do {
    		if (id == null) {
    			break;
    		}
    		Integer count = photoService.deletePhotoById(id, null);
    		if (count != null && count > 0) {
    			out.put("success", 1); //success message
    			return new ModelAndView("redirect:"+destUrl);
    		}
		} while (false);
    	myEsiteService.init(out, getCachedUser(request).getCid());
    	return new ModelAndView("redirect:"+destUrl);
    }
    
	/**
	 * @param out
	 * @param request
	 * @param targetId: if not null, setup targetId when select photo
	 * @param max
	 * @param albumId: default album
	 * @param queryTarget: if 0 or integer, only query the photos which targetId is queryTarget
	 * @return
	 */
	@RequestMapping
	public ModelAndView promptPhotos(Map<String, Object> out,
			HttpServletRequest request, Integer targetId,
			Integer max, Integer albumId, Integer queryTarget, String targetType) {

		if(max==null){
			max=1;
		}
		
		out.put("targetId", targetId);
		out.put("targetType", targetType);
		out.put("albumId", albumId);
		out.put("max", max);
		out.put("queryTarget", queryTarget);
		
		EpAuthUser cachedUser = getCachedUser(request);
		
		//query system and user album
		out.put("userAlbum", photoAlbumService.queryUserAlbum(cachedUser.getCid()));
		out.put("sysAlbum", photoAlbumService.querySystemAlbum(cachedUser.getCid()));
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView();
	}
    
	@RequestMapping
	public ModelAndView updateTargetId(Map<String, Object> out, HttpServletRequest request, 
			Integer id, Integer targetId, String targetType){
		
		photoService.updatePhotoTargetIdById(id, targetId, targetType);
		
		//高会图片上传图片不用流入审核
		EpAuthUser cachedUser = getCachedUser(request);
		if (cachedUser != null && "10011001".equals(cachedUser.getMemberCode())) {
			//高会从相册中选择图片为产品图片时，图片将置为已审核
			photoService.updateCheckStatus(id,CHECK_STATUS_1);
		} else{
			//修改图片或者上传图片的时候审核状态为未审核
			if(targetId!=null && "supply".equals(targetType)  && id !=null){
				tradeSupplyService.updateUncheckByIdForMyesite(targetId);
			}
		}
		
		ExtResult result = new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
    @RequestMapping
    public ModelAndView queryPhotos(Map<String, Object> out, 
    		HttpServletRequest request,  
    		PageDto<Photo> page, Integer albumId, Integer queryTarget) {
    	
    		page = photoService.pagePhotosByCid(getCompanyId(request),page,albumId, queryTarget);
    		
    	return printJson(page, out);
    }
    
    /**
     *用于发布微交流的图片 
     * */
	@RequestMapping
	public ModelAndView mblogPromptUpload(HttpServletRequest request,
			Map<String, Object> out, String targetType, Integer targetId,
			Integer max, Integer albumId) {
		
		EpAuthUser cachedUser = getCachedUser(request);
		MBlogInfo info= mBlogInfoService.queryInfoByInfoIdorCid(null, cachedUser.getCid());
		
		out.put("albums", photoAlbumService.queryAlbumByCid(cachedUser.getCid(), null));
		
		if(max==null){
			max=1;
		}
		if(info!=null){
			out.put("targetId", info.getId());
		}else{
			out.put("targetId", targetId);
		}
	
		out.put("targetType", targetType);
		out.put("albumId", albumId);
		out.put("max", max);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return null;
	}
	
	@RequestMapping
	public ModelAndView doMblogPromptUpload(HttpServletRequest request, Map<String, Object> out,
			String targetType, Integer targetId, Integer albumId){
		//TODO model should be defined by caller, and the default model is DEFAULT_MODEL="huanbao"
		String filename=String.valueOf(System.currentTimeMillis());
		String destRoot = MvcUpload.getDestRootByServer(UPLOAD_URL);
		String path="/huanbao/"+MvcUpload.getDateFolder();
		if (targetType.equals("credit")){
			path = "/credit/"+MvcUpload.getDateFolder();
		}
    	
    	String uploadedFile=null;
    	String fullUri = null;
		ExtResult result = new ExtResult();
		try {
			uploadedFile = MvcUpload.localUpload(request, destRoot+path, filename);
			fullUri = path+"/"+uploadedFile;
			ScaleImage is = new ScaleImage();
			is.saveImageAsJpg(destRoot+fullUri, destRoot+fullUri, 800, 800);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	if(result.getSuccess()){
    		//保存图片信息
//    		Integer id = (int) (Math.random()*100);
    		if(albumId==null && StringUtils.isNotEmpty(targetType)){
    			albumId = PHOTO_ALBUM.get(targetType);
    		}
    		if(albumId==null){
    			albumId=0;
    		}
    		
    		Photo photo = new Photo();
    		EpAuthUser cachedUser = getCachedUser(request);
    		photo.setUid(cachedUser.getUid());
    		photo.setCid(cachedUser.getCid());
    		
    		photo.setPhotoAlbumId(albumId);
    		photo.setPath(fullUri);
    		photo.setPathThumb(fullUri);
    		photo.setTargetType(targetType);
    		if(targetId==null){
    			//为微交流用户注册部分信息
    			MBlogInfo infos= mBlogInfoService.queryInfoByInfoIdorCid(null, cachedUser.getCid());
    	 		if(infos==null){
    	 			    MBlogInfo info=new MBlogInfo();
    	 	 		   info.setCid(cachedUser.getCid());
    	 	 		   info.setAccount(cachedUser.getAccount());
    	 	 		   info.setName("");
    	 	 		   Integer i= mBlogInfoService.insert(info);
    	 	 		   if(i!=null && i.intValue()>0){
    	 	 			 targetId = i;
    	 	 		   }
    	 	 		}
    	 		}    			
    		photo.setTargetId(targetId);
    		
    		//高会图片上传图片也不用流入审核
			if (cachedUser != null && "10011001".equals(cachedUser.getMemberCode())) {
				photo.setCheckStatus(CHECK_STATUS_1);
			} else{
				photo.setCheckStatus(CHECK_STATUS_0);
			}
    		
			Integer id = photoService.createPhoto(photo);
    		result.setData(JSONObject.fromObject("{id:"+id+",path:'"+fullUri+"'}"));
            
    	}
		
		return printJson(result, out);
	}
	
}

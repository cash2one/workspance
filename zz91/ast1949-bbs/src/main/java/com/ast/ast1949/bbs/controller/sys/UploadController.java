/**
 * 
 */
package com.ast.ast1949.bbs.controller.sys;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.bbs.controller.BaseController;
import com.ast.ast1949.domain.photo.PhotoAbum;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.bbs.BbsService;
import com.ast.ast1949.service.photo.PhotoAbumService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.file.ScaleImage;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * @author mays (mays@zz91.net)
 *
 * created by 2011-12-21
 */
@Controller
public class UploadController extends BaseController {
	
	final static String PIC_LOGO_TYPE = "logo";
	
	@Resource
	private BbsService bbsService;
	@Resource
	private PhotoAbumService photoAbumService;
	
	@RequestMapping
	public ModelAndView bbs(HttpServletRequest request, Map<String, Object> out, 
			String destUrl, String error, String name,String type){
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
		
		out.put("type", type);
		out.put("destUrl", destUrl);
		return null;
	}
	
	@RequestMapping
	public ModelAndView doBbs(HttpServletRequest request, Map<String, Object> out, 
			String destUrl, String type){
		
		if(destUrl==null){
			destUrl="";
		}
		
		String finalname=null;
		String error=null;
		
		String picAddress = "";
		do{
			
			// 图片上传 图片表更新
			try {
				String filename=UUID.randomUUID().toString();
				String path=MvcUpload.getModalPath("bbs");
				finalname=MvcUpload.localUpload(request, path, filename);
				
				//改为缩略图
				ScaleImage is = new ScaleImage();
				is.saveImageAsJpg(path+"/"+finalname, path+"/"+finalname, 800, 800);
				//保存图片信息
//				String suffix=name.substring(finalname.lastIndexOf("."), finalname.length());
				
				picAddress = path.replace(MvcUpload.getDestRoot()+"/", "")+"/"+finalname;
				
			} catch (Exception e) {
				error = MvcUpload.getErrorMessage(e.getMessage());
			}
		}while(false);
		
		if(PIC_LOGO_TYPE.equals(type)){
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser!=null){
				bbsService.updateBbsUserPicturePath(ssoUser.getAccount(), picAddress);
			}
		}
		
		if(error!=null){
			out.put("destUrl", destUrl);
			try {
				out.put("error", URLEncoder.encode(error, HttpUtils.CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
			}
			return new ModelAndView("redirect:bbs.htm");
		}else{
			out.put("success", "1");
			out.put("data", "{picAddress:\""+picAddress+"\"}");
			out.put("destUrl", destUrl);
			return null;
			
		}
		
	}
	
	@RequestMapping
	public ModelAndView ieupload(HttpServletRequest request,Map<String, Object>out,Integer albumId){
		
		String filename = String.valueOf(System.currentTimeMillis());
		String destRoot = MvcUpload.getDestRoot();
		String modelPath = "";
		if (albumId != null) {

			switch (albumId) {
			case 2:
				modelPath = "myrc";
				break;
			case 3:
				modelPath = "products";
				break;
			case 4:
				modelPath = "bbs";
				break;
			default:
				break;
			}
			String path = modelPath + "/" + MvcUpload.getDateFolder();
			Integer i = 0;
			String uploadedFile = null;
			String fullUri = null;

			try {
				uploadedFile = MvcUpload.localUpload(request, destRoot + "/"
						+ path, filename);
				fullUri = path + "/" + uploadedFile;
			
				ScaleImage is = new ScaleImage();
				is.saveImageAsJpg(destRoot + "/" + fullUri, destRoot + "/"
						+ fullUri, 800, 800);
		
				i = 1;
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (i == 1) {
					PhotoAbum photoAbum = new PhotoAbum();
					photoAbum.setAlbumId(albumId);
					photoAbum.setCompanyId(0);
					photoAbum.setIsDel(0);
					photoAbum.setPicAddress(fullUri);
					Integer id = photoAbumService.insert(photoAbum);
					out.put("id", id);
					out.put("path", fullUri);
			}

		}
		return null;
	}
	
	/**
	 *本地上传
	 * @param request
	 * @param out
	 * @param albumId 相册管理中的相册类型  2:公司相册 3:产品相册 4:互助相册  
	 * @param limitCount 限制上传图片的张数
	 * @param uploadFlag 1:会员资料图片上传
	 * @return
	 */
	@RequestMapping
	public ModelAndView myrcUpload(HttpServletRequest request,
			Map<String, Object> out, Integer albumId, Integer limitCount,
			Integer uploadFlag) {
		if (uploadFlag==null) {
			uploadFlag=0;
		}
		if(limitCount==null){
			limitCount=50;
		}
		out.put("uploadFlag", uploadFlag);
		// 最多上传多少张图片
		out.put("limitCount", limitCount);
		out.put("albumId", albumId);
		return null;
	}
	/**
	 * 
	 * @param request
	 * @param out
	 * @param limitCount 限制上传图片的张数
	 * @param uploadFlag  1:上传头像
	 * @param photoIds 上传图片页面中选择的图片的id
	 * @return
	 */
	@RequestMapping
	public ModelAndView doMyrcUpload(HttpServletRequest request,
			Map<String, Object> out, Integer id,
			Integer limitCount, Integer uploadFlag, String photoIds) {
		SsoUser ssoUser = getCachedUser(request);
		String path = "";
		do {
			if (ssoUser == null ||ssoUser.getCompanyId() == null) {
				break;
			}
			String[] ids = photoIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				Integer photoAbumId = 0;
				try {
					photoAbumId = Integer.valueOf(ids[i]);
				} catch (Exception e) {
					photoAbumId = 0;
				}
				if (photoAbumId == 0) {
					continue;
				}
				PhotoAbum photoAbum = photoAbumService.queryPhotoAbumById(photoAbumId);
					if (photoAbum == null||StringUtils.isEmpty(photoAbum.getPicAddress())) {
						continue;
					}
				if (0 <limitCount) {
						//上传图片补填photoabum表中的company_id
						photoAbumService.updateCompanyIdById(photoAbumId, ssoUser.getCompanyId());
						//上传头像
						if(uploadFlag==1){
							bbsService.updateBbsUserPicturePath(ssoUser.getAccount(), photoAbum.getPicAddress());
						}
						path=path+","+photoAbum.getPicAddress();
						
						
				}
			}
		} while (false);
		out.put("success", "1");
		// out.put("data", JSONObject.fromObject(pic).toString());
		out.put("data", "{picAddress:\"" + path+ "\"}");
		return null;
	}

	/**
	 * 上传图片的相册上传
	 * 
	 * @param request
	 * @param out
	 * @param limitCount 限制上传图片的张数
	 * @param uploadFlag 1:上传头像
	 * @param photoIds 上传图片页面中选择的图片的id
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView doAlbumUpload(HttpServletRequest request,Map<String, Object> out,Integer limitCount,
			 String photoIds, Integer uploadFlag) throws IOException{
		SsoUser ssoUser = getCachedUser(request);
		String path = "";
		do {
			
			if (ssoUser == null ||ssoUser.getCompanyId() == null) {
				break;
			}
			// photoPath 为上传图片的地址 photoIds 为上传图片的id（photn_abum表中的）
			if (StringUtils.isEmpty(photoIds)) {
				break;
			}
			if (ssoUser == null ||ssoUser.getCompanyId() == null) {
				break;
			}
			String[] ids = photoIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				Integer photoAbumId = 0;
				try {
					photoAbumId = Integer.valueOf(ids[i]);
				} catch (Exception e) {
					photoAbumId = 0;
				}
				if (photoAbumId == 0) {
					continue;
				}
				PhotoAbum photoAbum = photoAbumService.queryPhotoAbumById(photoAbumId);
					if (photoAbum == null||StringUtils.isEmpty(photoAbum.getPicAddress())) {
						continue;
					}
				if (0 <limitCount) {
						photoAbumService.updateCompanyIdById(photoAbumId, ssoUser.getCompanyId());
						//1:上传头像
						if(uploadFlag==1){
							bbsService.updateBbsUserPicturePath(ssoUser.getAccount(), photoAbum.getPicAddress());
						}
						
						path=path+","+photoAbum.getPicAddress();
				}
			}
		} while (false);
		out.put("success", "1");
		// out.put("data", JSONObject.fromObject(pic).toString());
		out.put("data", "{picAddress:\"" + path+ "\"}");
		return null;
	}
	/**
	 * 
	 * @param request
	 * @param out		
	 * @param albumId	上传到哪个相册中 相册管理中的相册类型  2:公司相册 3:产品相册 4:互助相册  
	 * @param groupId	选择的相册类型 
	 * @param photoPath  上传图片页面中选择的图片的地址
	 * @param page
	 * @param limitCount 限制上传图片的张数
	 * @param photoNum	上传了多少张图片
	 * @param photoIds	 上传图片页面中选择的图片的id
	 * @param uploadFlag  1:上传头像
	 * @return
	 */
	@RequestMapping
	public ModelAndView albumUpload(HttpServletRequest request,
			Map<String, Object> out, Integer albumId, String groupId,
			String photoPath, PageDto<PhotoAbum> page, Integer limitCount,
			Integer photoNum, String photoIds , Integer uploadFlag) {
		out.put("albumId", albumId);
		out.put("groupId", groupId);
		// 上传了多少张图片
		if (photoNum == null) {
			photoNum = 0;
		}
		//contextFlag 1:发布供求里详细描述正文上传， 0：发布供求里的产品图片上传
		if (uploadFlag==null) {
			uploadFlag=0;
		}
		out.put("uploadFlag", uploadFlag);
		out.put("photoNum", photoNum);
		// 图片最多上传多少张
		out.put("limitCount", limitCount);
		page.setPageSize(12);
		page.setSort("id");
		page.setDir("desc");
		Integer groupIds = 1;
		// 选择了多少张图片
		Integer photoCount = 0;
		out.put("photoPath", photoPath);
		out.put("photoIds", photoIds);
		Map<Integer, Map<String, String>> photoMap = new HashMap<Integer, Map<String, String>>();
		if (StringUtils.isNotEmpty(groupId)) {
			try {
				groupIds = Integer.valueOf(groupId);
			} catch (Exception e) {
				groupIds = 1;
			}
		}
		SsoUser ssoUser = getCachedUser(request);
		if (ssoUser != null && ssoUser.getCompanyId() != null
				&& albumId != null) {
			// groupId=1 为我的相册 2:公司相册 3:产品相册 4:互助相册
			if (groupIds == 1) {
				// 搜索出该公司所有相册类 的图片
				page = photoAbumService.queryPhotoAbumList(page, null,
						ssoUser.getCompanyId());
			} else {
				// abumList=photoAbumService.queryList(albumId,
				// ssoUser.getCompanyId());
				page = photoAbumService.queryPhotoAbumList(page, groupIds,
						ssoUser.getCompanyId());
			}
			if (StringUtils.isNotEmpty(photoPath)
					&& StringUtils.isNotEmpty(photoIds)) {
				String[] arr = photoPath.split(",");
				String[] ids = photoIds.split(",");
				photoCount = arr.length - 1;
				for (int i = 0; i < arr.length; i++) {
					if (StringUtils.isNotEmpty(arr[i])
							&& StringUtils.isNotEmpty(ids[i])) {
						Map<String, String> resultMap = new HashMap<String, String>();
						resultMap.put("picId", ids[i]);
						resultMap.put("picAdress", arr[i]);
						photoMap.put(i, resultMap);
					}
				}
			}

		}
		//已点了多少次图片
		out.put("photoCount", photoCount);
		out.put("photoMap", photoMap);
		out.put("page", page);
		return null;
	}
	@RequestMapping
	public ModelAndView updateComponyId(HttpServletRequest request,Map<String, Object>out,String ids) throws IOException{
	ExtResult result = new ExtResult();
	SsoUser ssoUser=getCachedUser(request);
	if(ssoUser!=null){
	String[] idArray=ids.split(",");
	for(String s:idArray){
		photoAbumService.updateCompanyIdById(Integer.valueOf(s), ssoUser.getCompanyId());
	}
	}
	return printJson(result, out);
	}
	//删除相册中的图片
	@RequestMapping
	public ModelAndView delPhotoAbum(HttpServletRequest request, Integer id,
			Map<String, Object> out) throws IOException {
		ExtResult result = new ExtResult();
		if (id != null && id.intValue() > 0) {
			Integer i = photoAbumService.delPhotoAbum(id);
			if (i != null && i.intValue() > 0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
}

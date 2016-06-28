package com.zz91.ep.myesite.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.PhotoAlbum;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.PhotoAlbumDto;
import com.zz91.ep.service.common.MyEsiteService;
import com.zz91.ep.service.trade.PhotoAlbumService;
import com.zz91.ep.service.trade.PhotoService;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.param.ParamUtils;

@Controller
public class PhotoAlbumController extends BaseController {

	@Resource
	private PhotoAlbumService photoAlbumService;

	@Resource
	private PhotoService photoService;
	@Resource
	private MyEsiteService myEsiteService;
	
	/**
	 * 查询所有相册
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request, Integer albumId, String success) {
		//获取三个默认相册的相册图片数量及封面图片
		EpAuthUser cachedUser = getCachedUser(request);
		
		List<PhotoAlbumDto> sysAlbum = photoAlbumService.querySystemAlbum(cachedUser.getCid());
		//获取用户自定义相册的图片数量及封面图片
		out.put("sysAlbum", sysAlbum);
		
		List<PhotoAlbumDto> userAlbum = photoAlbumService.queryUserAlbum(cachedUser.getCid());
		out.put("userAlbum", userAlbum);
		
		out.put("success", success);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView create(Map<String, Object> out,
			HttpServletRequest request, String albumName) {
		Integer num = 0;
		num = photoAlbumService.getSurplusAlbum(getCompanyId(request));
		out.put("num", num);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView doCreate(Map<String, Object> out,
			HttpServletRequest request, PhotoAlbum album) {
		
		EpAuthUser cachedUser = getCachedUser(request);
		
		album.setCid(cachedUser.getCid());
		album.setUid(cachedUser.getUid());
		
		photoAlbumService.createAlbum(album);
		myEsiteService.init(out, getCachedUser(request).getCid());
		out.put("success", -1);
		return new ModelAndView("redirect:index.htm");
	}
	
	@RequestMapping
	public ModelAndView doUpdate(Map<String, Object> out,
			HttpServletRequest request, PhotoAlbum album) {
		photoAlbumService.updateAlbumName(album.getId(), album.getName());
		out.put("success", -2);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView("redirect:index.htm");
	}
	
	@RequestMapping
	public ModelAndView update(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		out.put("albumName", photoAlbumService.queryNameById(id));
		out.put("id", id);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView();
	}
	
	/**
	 * 删除相册
	 * 
	 * @param out
	 * @param request
	 * @param albumId
	 * @return
	 */
	@RequestMapping
	public ModelAndView remove(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		EpAuthUser cachedUser=getCachedUser(request);
		photoAlbumService.deleteAlbumAndPhoto(id, cachedUser.getCid());
		
		out.put("success", -3);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView("redirect:index.htm");
		
//		ExtResult rs = new ExtResult();
//		do {
//
//			try {
//				Integer nums = photoService.queryPhotosByCidCount(
//						getCompanyId(request), albumId);
//				if (nums > 0) {
//					rs.setSuccess(false);
//					break;
//				}
//				photoAlbumService.deleteAlbumAndPhoto(albumId);
//				rs.setSuccess(true);
//			} catch (Exception e) {
//
//			}
//		} while (false);
//
//		return printJson(rs, out);
	}
	
	
	/********************************/
	
	/**
	 * 修改相册名称
	 * 
	 * @param out
	 * @param request
	 * @param name
	 * @param albumId
	 * @return
	 */
	@RequestMapping
	public ModelAndView doUpdateAlbumName(Map<String, Object> out,
			HttpServletRequest request, String newName, Integer albumId) {
		try {
			photoAlbumService.updateAlbumName(albumId, newName);
		} catch (Exception e) {

		}
		
		return new ModelAndView("redirect:index.htm");
	}

	/**
	 * 跳转修改页面
	 * 
	 * @param out
	 * @param request
	 * @param albumId
	 * @return
	 */
	@RequestMapping
	public ModelAndView updateAlbumName(Map<String, Object> out,
			HttpServletRequest request, Integer albumId) {
		try {
			out.put("oldName", photoAlbumService.queryNameById(albumId));
			out.put("albumId", albumId);
		} catch (Exception e) {

		}
		myEsiteService.init(out, getCachedUser(request).getCid());
		return null;
	}

	/**
	 * 查询某个相册的图片
	 * 
	 * @param out
	 * @param palid
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(Map<String, Object> out,
			HttpServletRequest request,Integer id, PageDto<Photo> page) {
		
		out.put("page", photoService.pagePhotosByCid(getCompanyId(request),
				page, id, null));
		
		if (id <= 0) {
			out.put("name",
					ParamUtils.getInstance().getValue("photo_album_type",
							String.valueOf(id)));
		} else {
			out.put("name", photoAlbumService.queryNameById(id));
		}
		
		EpAuthUser cachedUser = getCachedUser(request);
		Integer uploadedNum=photoService.queryPhotosByPalidCount(id, cachedUser.getCid());
		
		Integer totalAllow=20;
		if(id == -2){
			totalAllow = 100;
		}
		
		out.put("uploadedNum", uploadedNum);
		out.put("remainNum", (totalAllow-uploadedNum));
		out.put("totalAllow", totalAllow);
		myEsiteService.init(out, getCachedUser(request).getCid());
		out.put("id", id);
		
		return new ModelAndView();
		
		
//		if(albumId==-1){
//			out.put("company", 0);
//			out.put("num", (20 - photoService.queryPhotosByPalidCount(albumId,
//					getCompanyId(request))));
//		}else{
//			out.put("num", (100 - photoService.queryPhotosByPalidCount(albumId,
//					getCompanyId(request))));
//		}
//		out.put("albums", photoAlbumService.queryAlbumByCid(
//				getCompanyId(request), albumId));
//		out.put("albumId", albumId);
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		Map<String, Object> map = null;
//		for (int i = 0; i < 5; i--) {
//
//			if (albumId == i) {
//				continue;
//			}
//			String name = ParamUtils.getInstance().getValue("photo_album_type",
//					"" + i);
//			if (name == null) {
//				break;
//			}
//			map = new HashMap<String, Object>();
//			map.put("id", i);
//			map.put("name", name);
//			list.add(map);
//		}
//		out.put("defaultAlbum", list);
//		if(success!=null){
//			
//		if (success == 0) {
//			String albumName =null;
//			if(toAlbumId<1){
//				albumName = ParamUtils.getInstance().getValue("photo_album_type", toAlbumId.toString());
//			}else
//			{
//			 albumName = photoAlbumService.queryNameById(toAlbumId);
//			}
//			out.put("moveMessage", "移动失败，"+albumName+"已上传100张");
//		}else if(success == 1){
//			out.put("moveMessage", "移动失败，公司相册已上传20张");
//		}
//		}
//		if (delSta != null) {
//			out.put("moveMessage", "删除失败");
//		}
//		return null;
	}

	/**
	 * 跳转到上传页面
	 * 
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView uploadPhoto(Map<String, Object> out,
			HttpServletRequest request, Integer albumId) {
		out.put("albums", photoAlbumService.queryAlbumByCid(
				getCompanyId(request), albumId));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (int i = 0; i < 5; i--) {
			String name = ParamUtils.getInstance().getValue("photo_album_type",
					"" + i);
			if (name == null) {
				break;
			}
			map = new HashMap<String, Object>();
			map.put("id", i);
			map.put("name", name);
			list.add(map);
		}
		out.put("defaultAlbum", list);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return null;
	}

//	/**
//	 * 移动图片
//	 * 
//	 * @param map
//	 * @param request
//	 * @param albumId
//	 * @param pid
//	 * @return
//	 */
//	@RequestMapping
//	public ModelAndView movePhoto(Map<String, Object> out,
//			HttpServletRequest request, Integer albumId, Integer pid,
//			Integer toAlbumId) {
//		do {
//			String type = "supply";
//			if (toAlbumId == -1) {
//				type = "company";
//			} else if (toAlbumId == 0) {
//				type = "-1";
//			}
//			try {
//				Integer num = photoService.queryPhotosByCidCount(
//						getCompanyId(request), toAlbumId);
//				if (toAlbumId == -1) {
//					if (num >= 20) {
//						out.put("success", 1);
//						break;
//					}
//				} else {
//					if (num >= 100) {
//						out.put("success", 0);
//						break;
//					}
//				}
//				photoService.updatePhotoAlbumId(toAlbumId, pid, type);
//			} catch (Exception e) {
//
//			}
//		} while (false);
//		out.put("albumId", albumId);
//		out.put("toAlbumId", toAlbumId);
//		return new ModelAndView("redirect:list.htm");
//	}

	/**
	 * 删除图片
	 * 
	 * @param out
	 * @param request
	 * @param albumId
	 * @param pid
	 * @param type
	 * @param path
	 * @return
	 */
	@RequestMapping
	public ModelAndView deletePhoto(Map<String, Object> out,
			HttpServletRequest request, Integer pid) {
		ExtResult result = new ExtResult();
		Integer i= photoService.deletePhotoById(pid, null);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	/**
	 * 上传图片
	 * 
	 * @param out
	 * @param request
	 * @param photo
	 * @param albumId
	 * @return
	 */
//	@Deprecated
//	@RequestMapping
//	public ModelAndView doUploadPhoto(Map<String, Object> out,
//			HttpServletRequest request, Integer albumId) {
//		do {
//
//			// 数据验证
//			if (albumId == null) {
//				out.put("data", "请选择您要上传的相册！");
//				break;
//			}
//			// 是否超出相册容量
//			Integer max = photoService.queryPhotosByCidCount(
//					getCompanyId(request), albumId);
//			if (albumId == -1 && max >= 20) {
//				out.put("data", "您的公司相册图片超出上限！");
//				break;
//			}
//			if (albumId != -1 && max >= 100) {
//				out.put("data", "您的产品相册图片超出上限！");
//				break;
//			}
//			String filename = String.valueOf(System.currentTimeMillis());
//			String path = buildPath("huanbao");
//			String uploadedFile = null;
//			try {
//				uploadedFile = MvcUpload.localUpload(request, UPLOAD_ROOT
//						+ path, filename);
//				// 改为缩略图
//				ScaleImage is = new ScaleImage();
//				is.saveImageAsJpg(UPLOAD_ROOT + path + "/" + uploadedFile,
//						UPLOAD_ROOT + path + "/" + uploadedFile, 800, 800);
//			} catch (Exception e) {
//				out.put("data", MvcUpload.getErrorMessage(e.getMessage()));
//				break;
//			}
//			Photo photo = new Photo();
//			if (albumId == -1) {
//				photo.setTargetType("company");
//			} else if (albumId > 0 || albumId == -2) {
//				photo.setTargetType("supply");
//			} else {
//				photo.setTargetType("-1");
//			}
//			String fullPath = path + "/" + uploadedFile;
//			if (StringUtils.isNotEmpty(uploadedFile)) {
//				photo.setUid(getUid(request));
//				photo.setCid(getCompanyId(request));
//				photo.setPhotoAlbumId(albumId);
//				photo.setPath(fullPath);
//				photo.setPathThumb(fullPath);
//				photo.setTargetId(getCompanyId(request));
//				Integer count = photoService.createPhoto(photo);
//				if (count != null && count > 0) {
//					photo.setId(count);
//					out.put("success", true);
//					out.put("data", "{id: " + count + ",path: '" + fullPath
//							+ "'}");
//				}
//			}
//		} while (false);
//
//		return null;
//	}
	
	
}

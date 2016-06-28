package com.ast.ast1949.myrc.controller;


import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.photo.PhotoAbum;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsPicDTO;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.photo.PhotoAbumService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.file.PicMarkUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;

@Controller
public class PhotoAbumController extends BaseController {
	@Resource
	private PhotoAbumService photoAbumService;
	@Resource
	private CompanyService companyService;
	@Resource
	private ProductsPicService productsPicService;
	@Resource
	private ProductsService productsService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;

	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	final static Integer LIMIT_ZST_PIC = 5;
	final static String LIMIT_ZST_PIC_INFO = "目前系统允许再生通会员一条信息只能上传"
			+ LIMIT_ZST_PIC + "张图片";
	final static String LIMIT_LDB_PIC_INFO = "目前系统允许来电宝会员一条信息只能上传"
			+ LIMIT_ZST_PIC + "张图片";
	final static Integer LIMIT_PT_PIC = 5;
	final static String LIMIT_PT_PIC_INFO = "目前系统允许普通会员一条信息只能上传"
			+ LIMIT_ZST_PIC + "张图片";

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		SsoUser ssoUser = getCachedUser(request);

		if (ssoUser != null && ssoUser.getCompanyId() != null) {
			// 企业相册 albumId=2
			Integer companyAlbum = photoAbumService.queryPhotoAbumListCount(2,
					ssoUser.getCompanyId());
			out.put("companyAlbum", companyAlbum);
			// 相册的第一张图片
			PhotoAbum companyPhoto = photoAbumService.queryPhotoAbum(2,
					ssoUser.getCompanyId());
			out.put("companyPhoto", companyPhoto);

			// 产品相册 albumId=3
			Integer productAlbum = photoAbumService.queryPhotoAbumListCount(3,
					ssoUser.getCompanyId());
			out.put("productAlbum", productAlbum);
			// 相册的第一张图片
			PhotoAbum productPhoto = photoAbumService.queryPhotoAbum(3,
					ssoUser.getCompanyId());
			out.put("productPhoto", productPhoto);

			// 互助相册 albumId=4
			Integer bbsAlbum = photoAbumService.queryPhotoAbumListCount(4,
					ssoUser.getCompanyId());
			out.put("bbsAlbum", bbsAlbum);
			// 相册的第一张图片
			PhotoAbum bbsPhoto = photoAbumService.queryPhotoAbum(4,
					ssoUser.getCompanyId());
			out.put("bbsPhoto", bbsPhoto);
		}
		return null;
	}
	@RequestMapping
	public ModelAndView list(HttpServletRequest request,
			Map<String, Object> out, Integer albumId, PageDto<PhotoAbum> page) {
		String name = "";
		page.setSort("id");
		page.setDir("desc");
		out.put("albumId", albumId);
		switch (albumId) {
		case 2:
			name = "企业相册";
			break;
		case 3:
			name = "产品相册";
			break;
		case 4:
			name = "互助相册";
			break;
		default:
			break;
		}
		out.put("name", name);
		page.setPageSize(8);
		SsoUser ssoUser = getCachedUser(request);
		if (ssoUser != null && ssoUser.getCompanyId() != null
				&& albumId != null) {
			page = photoAbumService.queryPhotoAbumList(page, albumId,
					ssoUser.getCompanyId());
		}
		out.put("page", page);
		return null;
	}

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

	@RequestMapping
	public ModelAndView insertPhotoAbum(HttpServletRequest request,
			Map<String, Object> out, Integer albumId) throws IOException {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
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
				if (ssoUser==null||ssoUser.getCompanyId()==null||ssoUser.getCompanyId()==0) {
					return printJson(result, out);
				}
				break;
			case 4:
				modelPath = "bbs";
				break;
			case 6:
				modelPath = "yuanliao";
				break;
			default:
				break;
			}
			String path = modelPath + "/" + MvcUpload.getDateFolder();
			Integer i = 0;
			String uploadedFile = null;
			String fullUri = null;
			String physicalUrl = "";
			
			try {
				uploadedFile = MvcUpload.localUpload(request, destRoot + "/" + path, filename);
				fullUri = path + "/" + uploadedFile;
				physicalUrl = destRoot + "/" + path + "/" +uploadedFile;
				i = 1;
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (i == 1) {
				// 保存图片
				PhotoAbum photoAbum = new PhotoAbum();
				photoAbum.setAlbumId(albumId);
				photoAbum.setCompanyId(ssoUser.getCompanyId());
				photoAbum.setIsDel(0);
				photoAbum.setPicAddress(fullUri);
				Integer id = photoAbumService.insert(photoAbum);
				result.setData(JSONObject.fromObject("{id:" + id + ",path:'" + fullUri + "'}"));
				result.setSuccess(true);
				picMarkByAlbumId(albumId, physicalUrl, ssoUser);
			}

		}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView ieupload(HttpServletRequest request,Map<String, Object>out,Integer albumId){
		SsoUser ssoUser = getCachedUser(request);
//		System.out.println(1);
		String filename = String.valueOf(System.currentTimeMillis());
		String destRoot = MvcUpload.getDestRoot();
		String modelPath = "";
//		if (albumId != null&&ssoUser==null||ssoUser.getCompanyId()==null||ssoUser.getCompanyId()==0) {
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
			case 6:
				modelPath = "yuanliao";
				break;
			default:
				break;
			}
			
			
			String path = modelPath + "/" + MvcUpload.getDateFolder();
			Integer i = 0;
			String uploadedFile = null;
			String fullUri = null;
			String physicalUrl = "";
			try {
				uploadedFile = MvcUpload.localUpload(request, destRoot + "/" + path, filename);
				
//				System.out.println(2);
				
				fullUri = path + "/" + uploadedFile;
				physicalUrl = destRoot + "/" + path + "/" +uploadedFile;
				i = 1;
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			if (i == 1) {
				// 保存图片
				PhotoAbum photoAbum = new PhotoAbum();
				photoAbum.setAlbumId(albumId);
				photoAbum.setCompanyId(ssoUser.getCompanyId());
				photoAbum.setIsDel(0);
				photoAbum.setPicAddress(fullUri);
				Integer id = photoAbumService.insert(photoAbum);
				out.put("id", id);
				out.put("path", fullUri);

				// 供求打水印
				picMarkByAlbumId(albumId, physicalUrl, ssoUser);
				
//				System.out.println(3);
			}

//		}
		return null;
	}
	
	private void picMarkByAlbumId(Integer albumId,String physicalUrl,SsoUser ssoUser){
		Color lightGrey = new Color(0, 0, 0);
		if(albumId==5){
			// 根据类型写入诚信档案水印
			PicMarkUtils.pressText(physicalUrl,"ZZ91再生网诚信认证专用","simsun", Font.BOLD, 50, lightGrey, 0, 0,(float) 0.6);
			PicMarkUtils.pressText(physicalUrl,"其他使用无效","simsun", Font.BOLD, 50, lightGrey, 0, 50,(float) 0.6);
		}else{
			// 公司图片 水印
			Company company = companyService.queryCompanyById(ssoUser.getCompanyId());
			PicMarkUtils.pressText(physicalUrl, company.getName(),"simsun", Font.BOLD, 50, lightGrey, 0, 0,(float) 0.6);
			if ("10051000".equals(ssoUser.getMembershipCode())) {
				// 普会水印
				PicMarkUtils.pressText(physicalUrl,"http://www.zz91.com", "simsun",Font.BOLD, 20, lightGrey, 1, 1,(float) 1.0);
			} else if ("10051003".equals(ssoUser.getMembershipCode())) {
				// 来电宝水印
				PicMarkUtils.pressText(physicalUrl,"http://www.zz91.com/ppc/index"+ ssoUser.getCompanyId() + ".htm","simhei", Font.BOLD, 20, lightGrey, 1,1, (float) 0.8);
			} else {
				// 再生通 品牌通水印
				PicMarkUtils.pressText(physicalUrl,"http://" + company.getDomainZz91()+ ".zz91.com", "simhei",Font.BOLD, 20, lightGrey, 1, 1,(float) 1.0);
			}
		}
	}
	
	@RequestMapping
	public ModelAndView product1Abum(HttpServletRequest request,
			Map<String, Object> out, Integer albumId, String groupId,
			String photoPath, PageDto<PhotoAbum> page,Integer uploadCount) {
		out.put("albumId", albumId);
		out.put("groupId", groupId);
		//上传了多少张图片
		if(uploadCount==null){
			uploadCount=0;
		}
		out.put("uploadCount", uploadCount);
		page.setPageSize(12);
		page.setSort("id");
		page.setDir("desc");
		Integer groupIds = 1;
		Integer photoCount = 0;
		out.put("photoPath", photoPath);
		Map<Integer, String> photoMap = new HashMap<Integer, String>();
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
				// 搜索出不包含该相册类型ablumId 的图片
				page = photoAbumService.queryPagePhotoAbum(page, albumId,
						ssoUser.getCompanyId());
			} else {
				// abumList=photoAbumService.queryList(albumId,
				// ssoUser.getCompanyId());
				page = photoAbumService.queryPhotoAbumList(page, groupIds,
						ssoUser.getCompanyId());
			}
			if (StringUtils.isNotEmpty(photoPath)) {
				String[] arr = photoPath.split(",");
				photoCount = arr.length - 1;
				for (int i = 0; i < arr.length; i++) {
					if (StringUtils.isNotEmpty(arr[i])) {
						photoMap.put(i, arr[i]);
					}
				}
			}
		}

		out.put("photoCount", photoCount);
		out.put("photoMap", photoMap);
		out.put("page", page);
		return null;
	}

	@RequestMapping
	public ModelAndView doPhotoAbum(HttpServletRequest request,
			Map<String, Object> out, String photoPath, Integer albumId)
			throws IOException {
		ExtResult result = new ExtResult();
		do {
			if (albumId == null || StringUtils.isEmpty(photoPath)) {
				break;
			}

			SsoUser ssoUser = getCachedUser(request);
			if (ssoUser == null || ssoUser.getCompanyId() == null) {
				break;
			}
			String[] arr = photoPath.split(",");
			for (int i = 0; i < arr.length; i++) {
				if (StringUtils.isNotEmpty(arr[i])) {
					PhotoAbum photoAbum = new PhotoAbum();
					photoAbum.setAlbumId(albumId);
					photoAbum.setCompanyId(ssoUser.getCompanyId());
					photoAbum.setPicAddress(arr[i]);
					Integer id = photoAbumService.insert(photoAbum);
					if (id != null && id.intValue() > 0) {
						result.setSuccess(true);
					}
				}

			}
		} while (false);
		return printJson(result, out);

	}

	@RequestMapping
	public ModelAndView localUpload(HttpServletRequest request,
			Map<String, Object> out, Integer albumId) {
		out.put("albumId", albumId);
		return null;
	}
	/**
	 * 
	 * @param request
	 * @param out
	 * @param albumId 相册管理中的相册类型  2:公司相册 3:产品相册 4:互助相册  
	 * @param limitCount 限制上传图片的张数
	 * @param contextFlag 发布供求中 详细描述中插入图片 0:产品图片上传 1:描述编辑框正文插入图片
	 * @param productsId   供求id
	 * @param id		   供求图片的id product_pic 表中的id
	 * @return
	 */
	@RequestMapping
	public ModelAndView productsUpload(HttpServletRequest request,
			Map<String, Object> out, Integer albumId, Integer limitCount,
			Integer contextFlag,Integer productsId,Integer id,Integer editFlag) {
		// 1：contextFlag 为发布供求详细描述里图片上传，不需要插入到product_pic 表
		if (contextFlag == null) {
			contextFlag = 0;
		}
		if (editFlag == null) {
			editFlag = 0;
		}
		if (productsId==null) {
			productsId=0;
		}
		//editFlag 1:编辑图片中的修改标志
		out.put("editFlag", editFlag);
		out.put("contextFlag", contextFlag);
		// 最多上传多少张图片
		out.put("limitCount", limitCount);
		out.put("id", id);
		out.put("productsId", productsId);
		out.put("albumId", albumId);
		return null;
	}
	/**
	 * 
	 * @param request
	 * @param out
	 * @param limitCount 限制上传图片的张数
	 * @param contextFlag 发布供求中 详细描述中插入图片 0:产品图片上传 1:描述编辑框正文插入图片
	 * @param productsId   供求id
	 * @param id		   供求图片的id product_pic 表中的id  用于生意管家编辑图片用，limitCount=1
	 * @param photoIds 上传图片页面中选择的图片的id
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping
	public ModelAndView doProductsUpload(HttpServletRequest request,
			Map<String, Object> out, Integer id,
			Integer limitCount, Integer contextFlag, String photoIds,Integer productsId) throws ParseException {
		SsoUser ssoUser = getCachedUser(request);
		String path = "";
		String picIds = "";
		String error="";
		ProductsPicDO tPic = new ProductsPicDO();
		if (limitCount == null) {
			limitCount = 5;
		}
		// contextFlag 1:发布供求中详细描述里的正文图片上传 0:发布供求产品上传
		if (contextFlag == null) {
			contextFlag = 0;
		}
		// 获取ip地址
		String ip = HttpUtils.getInstance().getIpAddr(request);
		if (productsId == null) {
			productsId = 0;
		}
		do {
			if (ssoUser == null ||ssoUser.getCompanyId() == null) {
				break;
			}
			// photoPath 为上传图片的地址 photoIds 为上传图片的id（photn_abum表中的）
			if (StringUtils.isEmpty(photoIds)) {
				break;
			}
			// 判断id 存在侧属于修改图片，同时判断图片id是否属于productId的 存在bug，供求是否也需要判断是否属于登陆用户
			if (id != null) {
				ProductsPicDTO dto = productsPicService.queryProductPicById(id);
				if (dto == null || dto.getProductsPicDO() == null) {
					break;
				}
				if (productsId > 0
						&& !productsId.equals(dto.getProductsPicDO()
								.getProductId())) {
					error = "productId and id can not be match(图片不属于该供求)";
					break;
				}
				tPic = dto.getProductsPicDO();
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
					// contextFlag:1 为发布供求详细描述里图片上传 0：为发布供求产品图片上传
					if (contextFlag == 0) {
						ProductsPicDO pic = new ProductsPicDO();
						pic.setPicAddress(photoAbum.getPicAddress());
						pic.setProductId(productsId);
						// 相册id
						if (tPic.getAlbumId() == null) {
							pic.setAlbumId(0);
						} else {
							pic.setAlbumId(tPic.getAlbumId());
						}
						// 默认第一张图片置顶
						List<ProductsPicDO> picList = productsPicService
								.queryProductPicInfoByProductsId(productsId);
						if (picList == null || picList.size() == 0) {
							pic.setIsDefault(ProductsPicService.IS_DEFAULT);
						}
						// 审核状态
						Integer companyId = ssoUser.getCompanyId();
						if (crmCompanySvrService.validatePeriod(companyId,CrmCompanySvrService.ZST_CODE)) {
							// 高会免审核 高会限制 5 张 图片
							pic.setCheckStatus(ProductsPicService.CHECK_STATUS_PASS);
						} else if (crmCompanySvrService.validatePeriod(companyId,CrmCompanySvrService.LDB_CODE)|| crmCompanySvrService.validatePeriod(companyId,CrmCompanySvrService.LDB_FIVE_CODE)) {
							// 来电宝免审核 来电宝限制 5 张 图片
							pic.setCheckStatus(ProductsPicService.CHECK_STATUS_PASS);
						} else {
							// 普会重审 普会限制 5 张图片 超过5张执行更新
							pic.setCheckStatus(ProductsPicService.CHECK_STATUS_WAIT);
						}
						//刷新供求
						if(productsId>0){
							productsService.updateNewRefreshTimeById(productsId, ssoUser.getCompanyId());
						}
						// 插入到product_pic 表中
						if (id == null) {
							id = productsPicService.insertProductsPic(pic);
							// 插入图片日志
							LogUtil.getInstance().log("myrc","myrc-operate",ip,"{'account':'"+ ssoUser.getAccount()
													+ "','operatype_id':'3','pro_id':'"
													+ pic.getProductId()
													+ "','gmt_created':'"
													+ DateUtil.toString(new Date(),DATE_FORMAT)+ "'}", "myrc");

						} else {
							pic.setId(id);
							productsPicService.updateProductsPic(pic);
										// 修改图片日志
										LogUtil.getInstance()
												.log("myrc",
														"myrc-operate",
														ip,
														"{'account':'"
																+ ssoUser
																		.getAccount()
																+ "','operatype_id':'3','pro_id':'"
																+ pic.getProductId()
																+ "','gmt_created':'"
																+ DateUtil
																		.toString(
																				new Date(),
																				DATE_FORMAT)
																+ "'}", "myrc");
						}
						// 返回producdt_pic 表中的id
						picIds = picIds + "," + id;
					}
					photoAbumService.updateCompanyIdById(photoAbumId, ssoUser.getCompanyId());
					id = null;
					// 添加图片水印
					if (photoAbum.getIsMark()==0) {
						Company company = companyService.queryCompanyById(ssoUser.getCompanyId());
						Color lightGrey = new Color(0, 0, 0);
						if (company != null) {
							//打水印是否成功 1：成功  0:失败
							Integer markFlag=0;
							Boolean waterMark=false;
							String paths="/mnt/data/resources/"+photoAbum.getPicAddress();
							Boolean mark=PicMarkUtils.pressText(paths, company.getName(),"simsun", Font.BOLD, 50, lightGrey, 0, 0,(float) 0.6);
							if (mark) {
								markFlag=1;
							}
							if ("10051000".equals(company.getMembershipCode())) {
								waterMark=PicMarkUtils.pressText(paths,"http://www.zz91.com", "simsun",Font.BOLD, 20, lightGrey, 1, 1,(float) 1.0);
							} else if ("10051003".equals(company.getMembershipCode())) {
								waterMark=PicMarkUtils.pressText(paths,"http://www.zz91.com/ppc/index"+ company.getId() + ".htm","simhei", Font.BOLD, 20, lightGrey, 1,1, (float) 0.8);
							} else {
								waterMark=PicMarkUtils.pressText(paths,"http://" + company.getDomainZz91()+ ".zz91.com", "simhei",Font.BOLD, 20, lightGrey, 1, 1,(float) 1.0);
							}
							if (waterMark) {
								markFlag=1;
							}
							//更新photo_abum 相册管理表中是否已打水印标记  is_mark 1: 已打水印 0: 未打水印
							if (markFlag==1) {
								photoAbumService.updateIsMarkById(photoAbumId);
							}
						}
					}
					if (contextFlag==1) {
						Integer width=0;
						Integer height=0;
						try {
							File file = new File("/nmt/data/resources/"+photoAbum.getPicAddress());
							Image image = ImageIO.read(file);
							width = image.getWidth(null);
							height = image.getHeight(null);
							
						} catch (Exception e) {
							width=300;
							height=300;
						}
						if (width>600) {
							width=600;
						}
						if (height>600) {
							height=600;
						}
						path = path + ","+width+"x"+height+"/" + photoAbum.getPicAddress();
					}else {
						path = path + "," + photoAbum.getPicAddress();
					}
				}
			}
		} while (false);
		out.put("success", "1");
		// out.put("data", JSONObject.fromObject(pic).toString());
		out.put("data", "{picId:\"" + picIds + "\",picAddress:\"" + path+ "\"}");
		return null;
	}

	/**
	 * 产品信息上传图片的相册上传
	 * 
	 * @param request
	 * @param out
	 * @param limitCount 限制上传图片的张数
	 * @param contextFlag 发布供求中 详细描述中插入图片 0:产品图片上传 1:描述编辑框正文插入图片
	 * @param productsId   供求id
	 * @param id		   供求图片的id product_pic 表中的id  用于生意管家编辑图片用，limitCount=1
	 * @param photoIds 上传图片页面中选择的图片的id
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView doAlbumUpload(HttpServletRequest request,Map<String, Object> out,Integer id, Integer limitCount,
			Integer contextFlag, String photoIds,Integer productsId) throws IOException{
		SsoUser ssoUser = getCachedUser(request);
		String path = "";
		String picIds = "";
		String error = null;
		ProductsPicDO tPic = new ProductsPicDO();
		if (limitCount == null) {
			limitCount = 5;
		}
		// contextFlag 1:发布供求中详细描述里的正文图片上传 0:发布供求产品上传
		if (contextFlag == null) {
			contextFlag = 0;
		}
		// 获取ip地址
		String ip = HttpUtils.getInstance().getIpAddr(request);
		if (productsId == null) {
			productsId = 0;
		}
		do {
			
			// 判断id 存在侧属于修改图片，同时判断图片id是否属于productId的 存在bug，供求是否也需要判断是否属于登陆用户
			if (id != null) {
				ProductsPicDTO dto = productsPicService.queryProductPicById(id);
				if (dto == null || dto.getProductsPicDO() == null) {
					break;
				}
				if (productsId > 0&& !productsId.equals(dto.getProductsPicDO().getProductId())) {
					error = "productId and id can not be match(图片不属于该供求)";
					break;
				}
				tPic = dto.getProductsPicDO();
			}
			if (ssoUser == null ||ssoUser.getCompanyId() == null) {
				break;
			}
			// photoPath 为上传图片的地址 photoIds 为上传图片的id（photn_abum表中的）
			if (StringUtils.isEmpty(photoIds)) {
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
				if (0<limitCount) {
					// contextFlag:1 为发布供求详细描述里图片上传 0：为发布供求产品图片上传
					if (contextFlag == 0) {
						ProductsPicDO pic = new ProductsPicDO();
						pic.setPicAddress(photoAbum.getPicAddress());
						pic.setProductId(productsId);
						
						// 相册id
						if (tPic.getAlbumId() == null) {
							pic.setAlbumId(0);
						} else {
							pic.setAlbumId(tPic.getAlbumId());
						}

						// 默认第一张图片置顶
						List<ProductsPicDO> picList = productsPicService.queryProductPicInfoByProductsId(productsId);
						if (picList == null || picList.size() == 0) {
							pic.setIsDefault(ProductsPicService.IS_DEFAULT);
						}
						// 审核状态
						Integer companyId = ssoUser.getCompanyId();
						if (crmCompanySvrService.validatePeriod(companyId,CrmCompanySvrService.ZST_CODE)) {
							// 高会免审核 高会限制 5 张 图片
							pic.setCheckStatus(ProductsPicService.CHECK_STATUS_PASS);
						} else if (crmCompanySvrService.validatePeriod(companyId,CrmCompanySvrService.LDB_CODE)|| crmCompanySvrService.validatePeriod(companyId,CrmCompanySvrService.LDB_FIVE_CODE)) {
							// 来电宝免审核 来电宝限制 5 张 图片
							pic.setCheckStatus(ProductsPicService.CHECK_STATUS_PASS);
						} else {
							// 普会重审 普会限制 5 张图片 超过5张执行更新
							pic.setCheckStatus(ProductsPicService.CHECK_STATUS_WAIT);
						}
						// 插入到product_pic 表中
						if (id == null) {
							id = productsPicService.insertProductsPic(pic);
								// 插入图片日志
							LogUtil.getInstance().log("myrc","myrc-operate",ip,"{'account':'"+ ssoUser.getAccount()
													+ "','operatype_id':'3','pro_id':'"
													+ pic.getProductId()
													+ "','gmt_created':'"
													+ DateUtil.toString(new Date(),DATE_FORMAT)+ "'}", "myrc");

						} else {
							pic.setId(id);
							productsPicService.updateProductsPic(pic);
										// 修改图片日志
										LogUtil.getInstance()
												.log("myrc",
														"myrc-operate",
														ip,
														"{'account':'"
																+ ssoUser
																		.getAccount()
																+ "','operatype_id':'3','pro_id':'"
																+ pic.getProductId()
																+ "','gmt_created':'"
																+ DateUtil
																		.toString(
																				new Date(),
																				DATE_FORMAT)
																+ "'}", "myrc");
							}
						// 返回producdt_pic 表中的id
						picIds = picIds + "," + id;
						
					}
					id = null;
					
					if (contextFlag==1) {
						Integer width=0;
						Integer height=0;
						try {
							File file = new File("/mnt/data/resources/"+photoAbum.getPicAddress());
							Image image = ImageIO.read(file);
							width = image.getWidth(null);
							height = image.getHeight(null);
							
						} catch (Exception e) {
							width=300;
							height=300;
						}
						if (width>600) {
							width=600;
						}
						if (height>600) {
							height=600;
						}
						path = path + ","+width+"x"+height+"/" + photoAbum.getPicAddress();
					}else {
						path = path + "," + photoAbum.getPicAddress();
					}
				}
			}
		} while (false);
		out.put("success", "1");
		// out.put("data", JSONObject.fromObject(pic).toString());
		out.put("data", "{picId:\"" + picIds + "\",picAddress:\"" + path+ "\"}");
		return new ModelAndView();
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
	 * @param contextFlag 发布供求中 详细描述中插入图片 0:产品图片上传 1:描述编辑框正文插入图片
	 * @param productsId  供求id
	 * @param id		  供求图片的id product_pic 表中的id  用于生意管家编辑图片用，limitCount=1
	 * @return
	 */
	@RequestMapping
	public ModelAndView albumUpload(HttpServletRequest request,
			Map<String, Object> out, Integer albumId, String groupId,
			String photoPath, PageDto<PhotoAbum> page, Integer limitCount,
			Integer photoNum, String photoIds ,Integer contextFlag,Integer productsId,Integer id,Integer editFlag) {
		out.put("albumId", albumId);
		out.put("groupId", groupId);
		out.put("id", id);
		out.put("productsId", productsId);
		// 上传了多少张图片
		if (photoNum == null) {
			photoNum = 0;
		}
		//contextFlag 1:发布供求里详细描述正文上传， 0：发布供求里的产品图片上传
		if (contextFlag==null) {
			contextFlag=0;
		}
		
		//editFlag 1:生意管家图片编辑中的更改标志
		if (editFlag==null) {
			editFlag=0;
		}
		out.put("editFlag", editFlag);
		out.put("contextFlag", contextFlag);
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
	public ModelAndView updateComponyId(HttpServletRequest request,
			Map<String, Object> out, String ids) throws IOException {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		if (ssoUser != null) {
			String[] idArray = ids.split(",");
			for (String s : idArray) {
				photoAbumService.updateCompanyIdById(Integer.valueOf(s),
						ssoUser.getCompanyId());
			}
		}
		return printJson(result, out);
	}

}

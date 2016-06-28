/**
 * 
 */
package com.ast.ast1949.myrc.controller.sys;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyUploadFileDO;
import com.ast.ast1949.domain.photo.PhotoAbum;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.domain.products.ProductsRub;
import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsPicDTO;
import com.ast.ast1949.myrc.controller.BaseController;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CompanyUploadFileService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.photo.PhotoAbumService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsRubService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.sample.SampleService;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.file.PicMarkUtils;
import com.zz91.util.file.ScaleImage;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;

/**
 * @author mays (mays@zz91.net)
 * 
 *         created by 2011-12-21
 */
@Controller
public class UploadController extends BaseController {

	@Resource
	private ProductsPicService productsPicService;
	@Resource
	private CompanyUploadFileService companyUploadFileService;
	@Resource
	private ProductsService productsService;	
	@Resource
	CompanyService companyService;
	@Resource
	CrmCompanySvrService crmCompanySvrService;
	@Resource
	private PhoneService phoneService;
	@Resource
	private SampleService sampleService;
	@Resource
	private ProductsRubService productsRubService;
	@Resource
	private PhotoAbumService photoAbumService;
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	final static String MODEL_CREDIT_FILE = "creditfile";
	final static String MODEL_COMPANY = "myrc";
	final static Integer LIMIT_ZST_PIC = 5;
	final static String LIMIT_ZST_PIC_INFO = "目前系统允许再生通会员一条信息只能上传"
			+ LIMIT_ZST_PIC + "张图片";
	final static String LIMIT_LDB_PIC_INFO = "目前系统允许来电宝会员一条信息只能上传"
			+ LIMIT_ZST_PIC + "张图片";
	final static Integer LIMIT_PT_PIC = 5;
	final static String LIMIT_PT_PIC_INFO = "目前系统允许普通会员一条信息只能上传"
			+ LIMIT_ZST_PIC + "张图片";

	@RequestMapping
	public ModelAndView product(HttpServletRequest request,
			Map<String, Object> out, Integer productId, String destUrl,
			String error, String name, Integer id) {
		if (error == null) {
			error = "";
		}
		if (name == null) {
			name = "";
		}
		try {
			out.put("error", URLDecoder.decode(error, HttpUtils.CHARSET_UTF8));
			out.put("name", URLDecoder.decode(name, HttpUtils.CHARSET_UTF8));
		} catch (UnsupportedEncodingException e) {
		}

		out.put("productId", productId);
		out.put("destUrl", destUrl);
		out.put("id", id);
		return null;
	}

	@RequestMapping
	public ModelAndView doProduct(HttpServletRequest request,
			Map<String, Object> out, String destUrl, Integer productId,
			String name, Integer id) throws ParseException {
		//获取ip地址
		String ip=HttpUtils.getInstance().getIpAddr(request);
		if (productId == null) {
			productId = 0;
		}

		if (destUrl == null) {
			destUrl = "";
		}
		SsoUser ssoUser = getCachedUser(request);
		String finalname = null;
		String error = null;
		ProductsPicDO pic = new ProductsPicDO();
		ProductsPicDO tPic = new ProductsPicDO();
		do {
			
			// 判断id 存在侧属于修改图片，同时判断图片id是否属于productId的 存在bug，供求是否也需要判断是否属于登陆用户
			if (id != null) {
				ProductsPicDTO dto = productsPicService.queryProductPicById(id);
				if (dto == null || dto.getProductsPicDO() == null) {
					break;
				}
				if (productId > 0
						&& !productId.equals(dto.getProductsPicDO()
								.getProductId())) {
					error = "productId and id can not be match(图片不属于该供求)";
					break;
				}
				tPic = dto.getProductsPicDO();
			}

			// 图片上传 图片表更新
			try {
				String filename = UUID.randomUUID().toString();
				String path = MvcUpload
						.getModalPath(ProductsPicService.UPLOAD_MODEL);
				finalname = MvcUpload.localUpload(request, path, filename);

				// 改为缩略图
				ScaleImage is = new ScaleImage();
				is.saveImageAsJpg(path + "/" + finalname, path + "/"
						+ finalname, 800, 800);
				// 保存图片信息
				// String suffix=name.substring(finalname.lastIndexOf("."),
				// finalname.length());

				pic.setName(name);
				pic.setPicAddress(path.replace(MvcUpload.getDestRoot() + "/",
						"") + "/" + finalname);
				pic.setProductId(productId);

				// 相册id
				if (tPic.getAlbumId() == null) {
					pic.setAlbumId(0);
				} else {
					pic.setAlbumId(tPic.getAlbumId());
				}

				// 默认第一张图片置顶
				List<ProductsPicDO> picList = productsPicService
						.queryProductPicInfoByProductsId(productId);
				if (picList == null || picList.size() == 0) {
					pic.setIsDefault(ProductsPicService.IS_DEFAULT);
				}

				// 审核状态
				
				Integer companyId = ssoUser.getCompanyId();
				if (crmCompanySvrService.validatePeriod(companyId,
						CrmCompanySvrService.ZST_CODE)) {
					// 高会免审核 高会限制 5 张 图片 超过5张 自动跳出
					if (picList != null && picList.size() >= LIMIT_ZST_PIC
							&& id == null) {
						error = LIMIT_ZST_PIC_INFO;
						break;
					}
					pic.setCheckStatus(ProductsPicService.CHECK_STATUS_PASS);
				} else if (crmCompanySvrService.validatePeriod(companyId,
						CrmCompanySvrService.LDB_CODE)
						|| crmCompanySvrService.validatePeriod(companyId,
								CrmCompanySvrService.LDB_FIVE_CODE)) {
					// 来电宝免审核 来电宝限制 5 张 图片 超过5张 自动跳出
					if (picList != null && picList.size() >= LIMIT_ZST_PIC
							&& id == null) {
						error = LIMIT_LDB_PIC_INFO;
						break;
					}
					pic.setCheckStatus(ProductsPicService.CHECK_STATUS_PASS);
				} else {
					// 普会重审 普会限制 5 张图片 超过5张执行更新
					if (picList != null && picList.size() >= LIMIT_ZST_PIC
							&& id == null) {
						error = LIMIT_PT_PIC_INFO;
						break;
					}
					pic.setCheckStatus(ProductsPicService.CHECK_STATUS_WAIT);
				}
				
				if (id == null) {
					id = productsPicService.insertProductsPic(pic);
					// 插入图片日志
					LogUtil.getInstance().log("myrc","myrc-operate",ip,"{'account':'"+ ssoUser.getAccount()+ "','operatype_id':'3','pro_id':'"+ pic.getProductId() + "','gmt_created':'" + DateUtil.toString(new Date(), DATE_FORMAT) + "'}", "myrc");

				} else {
					pic.setId(id);
					productsPicService.updateProductsPic(pic);
					// 修改图片日志
					LogUtil.getInstance().log(
							"myrc",
							"myrc-operate",
							ip,
							"{'account':'"
									+ ssoUser.getAccount()
									+ "','operatype_id':'3','pro_id':'"
									+ pic.getProductId()
									+ "','gmt_created':'"
									+ DateUtil
											.toString(new Date(), DATE_FORMAT)
									+ "'}", "myrc");
				}
				if(productId!=null){
					//判断该供求违规库中是否有该条信息
					ProductsRub productsRub =productsRubService.queryRubByProductId(productId);
					if(productsRub!=null){
						//删除违规库中的供求信息
						productsRubService.deleteProductsRubByProductId(productId);
					}
				}
				pic.setId(id);
				
				//如果是样品图片则更改状态和审核理由
				Sample sample=sampleService.queryByIdOrProductId(null, productId);
				if(sample!=null&&sample.getId()!=null){
					sampleService.updateSampleForUnpassReason(sample.getId(), null);
				}
				// 添加图片水印
				Company company = companyService.queryCompanyById(companyId);
				Color lightGrey = new Color(0, 0, 0);
				if (company != null) {
					PicMarkUtils.pressText(path + "/" + finalname,
							company.getName(), "simsun", Font.BOLD, 50,
							lightGrey, 0, 0, (float) 0.6);
					if ("10051000".equals(ssoUser.getMembershipCode())) {
						PicMarkUtils.pressText(path + "/" + finalname,
								"http://www.zz91.com", "simsun", Font.BOLD, 20,
								lightGrey, 1, 1, (float) 1.0);
					} else if ("10051003".equals(ssoUser.getMembershipCode())) {
//						Color lightRed = new Color(255, 0, 0);
						// Phone phone =
						// phoneService.queryByCompanyId(company.getId());
						// PicMarkUtils.pressText(path+"/"+finalname,phone.getFrontTel(),"simhei",Font.BOLD,48,lightRed,0,50,(float)
						// 0.5);
						PicMarkUtils.pressText(
								path + "/" + finalname,
								"http://www.zz91.com/ppc/index"
										+ company.getId() + ".htm", "simhei",
								Font.BOLD, 20, lightGrey, 1, 1, (float) 0.8);
					} else {
						PicMarkUtils.pressText(path + "/" + finalname,
								"http://" + company.getDomainZz91()
										+ ".zz91.com", "simhei", Font.BOLD, 20,
								lightGrey, 1, 1, (float) 1.0);
					}
				}
			} catch (Exception e) {
				error = MvcUpload.getErrorMessage(e.getMessage());
			}
		} while (false);

		if (error != null) {
			out.put("productId", productId);
			out.put("destUrl", destUrl);
			try {
				out.put("name", URLEncoder.encode(name, HttpUtils.CHARSET_UTF8));
				out.put("error",
						URLEncoder.encode(error, HttpUtils.CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
			}
			return new ModelAndView("redirect:product.htm");
		} else {
			if (productId != null && productId > 0) {
				ProductsDO productsDO = productsService
						.queryProductsById(productId);
				String code = companyService
						.queryMembershipOfCompany(productsDO.getCompanyId());
				if ("10051000".equals(code)) {
					productsService.updateUncheckByIdForMyrc(productId);
				}
				//刷新供求
				if (ssoUser!=null&&ssoUser.getCompanyId()!=null) {
					productsService.updateNewRefreshTimeById(productId, ssoUser.getCompanyId());
				}
				
			}
			out.put("success", "1");
			// out.put("data", JSONObject.fromObject(pic).toString());
			out.put("data",
					"{id:" + pic.getId() + ",picAddress:\""
							+ pic.getPicAddress() + "\"}");
			out.put("destUrl", destUrl);
			return null;

			// if(StringUtils.isNotEmpty(destUrl)){
			// return new ModelAndView("redirect:"+destUrl);
			// }else{
			// return new
			// ModelAndView("redirect:"+request.getContextPath()+"/submitCallback.htm");
			// }
		}

	}

	@RequestMapping
	public ModelAndView company(HttpServletRequest request,
			Map<String, Object> out, String destUrl, String error, String remark) {
		if (error == null) {
			error = "";
		}
		if (remark == null) {
			remark = "";
		}
		try {
			out.put("error", URLDecoder.decode(error, HttpUtils.CHARSET_UTF8));
			out.put("name", URLDecoder.decode(remark, HttpUtils.CHARSET_UTF8));
		} catch (UnsupportedEncodingException e) {
		}

		out.put("destUrl", destUrl);
		return null;
	}

	@RequestMapping
	public ModelAndView doCompany(HttpServletRequest request,
			Map<String, Object> out, String destUrl, String remark) {
		if (destUrl == null) {
			destUrl = "";
		}

		String finalname = null;
		String error = null;
		CompanyUploadFileDO pic = new CompanyUploadFileDO();
		try {
			String filename = UUID.randomUUID().toString();
			String path = MODEL_COMPANY + "/" + MvcUpload.getDateFolder();
			finalname = MvcUpload.localUpload(request, MvcUpload.getDestRoot()
					+ "/" + path, filename);

			// 改为缩略图
			ScaleImage is = new ScaleImage();
			is.saveImageAsJpg(MvcUpload.getDestRoot() + "/" + path + "/"
					+ finalname, MvcUpload.getDestRoot() + "/" + path + "/"
					+ finalname, 800, 800);
			// 保存图片信息
			// String suffix=name.substring(finalname.lastIndexOf("."),
			// finalname.length());

			pic.setRemark(remark);
			pic.setFilename(finalname);
			pic.setFilepath(path + "/");
			pic.setCompanyId(getCachedUser(request).getCompanyId());
			pic.setFiletype(pic.getFilename().split("\\.")[1]);

			Integer id = companyUploadFileService.insertCompanyUploadFile(pic);

			pic.setId(id);
		} catch (Exception e) {
			error = MvcUpload.getErrorMessage(e.getMessage());
		}

		if (error != null) {
			out.put("destUrl", destUrl);
			try {
				out.put("error",
						URLEncoder.encode(error, HttpUtils.CHARSET_UTF8));
				out.put("remark",
						URLEncoder.encode(remark, HttpUtils.CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
			}
			return new ModelAndView("redirect:company.htm");
		} else {
			out.put("success", "1");
			out.put("data",
					"{id:" + pic.getId() + ",picAddress:\"" + pic.getFilepath()
							+ "/" + pic.getFilename() + "\"}");
			out.put("destUrl", destUrl);
			return null;
		}

	}

	@RequestMapping
	public ModelAndView creditFile(HttpServletRequest request,
			Map<String, Object> out, String destUrl, String error) {
		if (error == null) {
			error = "";
		}
		try {
			out.put("error", URLDecoder.decode(error, HttpUtils.CHARSET_UTF8));
		} catch (UnsupportedEncodingException e) {
		}

		out.put("destUrl", destUrl);
		return null;
	}

	@RequestMapping
	public ModelAndView doCreditFile(HttpServletRequest request,
			Map<String, Object> out, String destUrl) {
		if (destUrl == null) {
			destUrl = "";
		}

		String finalname = null;
		String error = null;
		String finalFilePath = "";
		// CompanyUploadFileDO pic=new CompanyUploadFileDO();
		try {
			String filename = UUID.randomUUID().toString();
			String path = MODEL_CREDIT_FILE + "/" + MvcUpload.getDateFolder();
			finalname = MvcUpload.localUpload(request, MvcUpload.getDestRoot()
					+ "/" + path, filename);

			finalFilePath = path + "/" + finalname;

			// 改为缩略图
			ScaleImage is = new ScaleImage();
			is.saveImageAsJpg(MvcUpload.getDestRoot() + "/" + path + "/"
					+ finalname, MvcUpload.getDestRoot() + "/" + path + "/"
					+ finalname, 800, 800);
			// 保存图片信息
			// String suffix=name.substring(finalname.lastIndexOf("."),
			// finalname.length());

			// pic.setFilename(finalname);
			// pic.setFilepath(path+"/");
			// pic.setCompanyId(getCachedUser(request).getCompanyId());
			// pic.setFiletype(pic.getFilename().split("\\.")[1]);
			//
			// Integer id=companyUploadFileService.insertCompanyUploadFile(pic);
			//
			// pic.setId(id);
		} catch (Exception e) {
			error = MvcUpload.getErrorMessage(e.getMessage());
		}

		if (error != null) {
			out.put("destUrl", destUrl);
			try {
				out.put("error",
						URLEncoder.encode(error, HttpUtils.CHARSET_UTF8));
				// out.put("remark", URLEncoder.encode(remark,
				// HttpUtils.CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
			}
			return new ModelAndView("redirect:creditFile.htm");
		} else {
			out.put("success", "1");
			out.put("data", "{picAddress:\"" + finalFilePath + "\"}");
			out.put("destUrl", destUrl);
			return null;
		}

	}
	
	@RequestMapping
	public ModelAndView ieupload(HttpServletRequest request,Map<String, Object>out,Integer albumId){
		SsoUser ssoUser = getCachedUser(request);
		String filename = String.valueOf(System.currentTimeMillis());
		String destRoot = MvcUpload.getDestRoot();
		String modelPath = "";
		Integer albumIdTemp = albumId;
		if (albumId != null&&ssoUser!=null&&ssoUser.getCompanyId()!=null&&ssoUser.getCompanyId()>0) {
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
			case 5:
				modelPath = "myrc";
				albumIdTemp = 2;// 资质认证类别恢复为 2,水印行为为5
				break;
			default:
				break;
			}
			String path = modelPath + "/" + MvcUpload.getDateFolder();
			Integer i = 0;
			String uploadedFile = null;
			String fullUri = null;
			String physicalUrl="";

			try {
				uploadedFile = MvcUpload.localUpload(request, destRoot + "/" + path, filename);
				fullUri = path + "/" + uploadedFile;
				ScaleImage is = new ScaleImage();
				physicalUrl = destRoot + "/" + fullUri;
				is.saveImageAsJpg(physicalUrl, physicalUrl, 800, 800);
				i = 1;
			} catch (Exception e) {
			}

			// 图片上传成功
			if (i == 1) {
				// 保存图片
				PhotoAbum photoAbum = new PhotoAbum();
				photoAbum.setAlbumId(albumIdTemp);
				photoAbum.setCompanyId(ssoUser.getCompanyId());
				photoAbum.setIsDel(0);
				photoAbum.setPicAddress(fullUri);
				Integer id = photoAbumService.insert(photoAbum);
				out.put("id", id);
				out.put("path", fullUri);

				// 写入水印
				picMarkByAlbumId(albumId, physicalUrl,ssoUser);

			}

		}
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
	
	/**
	 * 
	 * @param request
	 * @param out
	 * @param albumId 相册管理中的相册类型  2:公司相册 3:产品相册 4:互助相册  
	 * @param limitCount 限制上传图片的张数
	 * @param uploadFlag 1:会员资料图片上传
	 * @param id		   供求图片的id product_pic 表中的id
	 * @return
	 */
	@RequestMapping
	public ModelAndView myrcUpload(HttpServletRequest request,
			Map<String, Object> out, Integer albumId, Integer limitCount,
			Integer uploadFlag,Integer id) {
		if (uploadFlag==null) {
			uploadFlag=0;
		}
		SsoUser ssoUser = getCachedUser(request);
		if (ssoUser!=null) {
			out.put("memberShipCode", ssoUser.getMembershipCode());
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
	 * @param uploadFlag  1:上传会员照片
	 * @param photoIds 上传图片页面中选择的图片的id
	 * @return
	 */
	@RequestMapping
	public ModelAndView doMyrcUpload(HttpServletRequest request,
			Map<String, Object> out,
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
				}else{
					if(StringUtils.isNotEmpty(path)){
						path = path + "," +photoAbum.getPicAddress();
					}else{
						path = photoAbum.getPicAddress();
					}
				}
				if (0 <limitCount) {
//					photoAbumService.updateCompanyIdById(photoAbumId, ssoUser.getCompanyId());
//					path=path+","+photoAbum.getPicAddress();
//					Color lightGrey = new Color(0, 0, 0);
//					String paths="/usr/data/resources/"+photoAbum.getPicAddress();
//					PicMarkUtils.pressText(paths,"ZZ91再生网诚信认证专用","simsun", Font.BOLD, 50, lightGrey, 0, 0,(float) 0.6);
//					PicMarkUtils.pressText(paths,"其他使用无效","simsun", Font.BOLD, 50, lightGrey, 0, 50,(float) 0.6);
				}
				if(uploadFlag==1){
					String[] address=photoAbum.getPicAddress().split("/");
					if (address.length>=5) {
						CompanyUploadFileDO pic = new CompanyUploadFileDO();
						pic.setFilename(address[4]);
						String filePath=address[0]+"/"+address[1]+"/"+address[2]+"/"+address[3]+"/";
						pic.setFilepath(filePath);
						pic.setCompanyId(ssoUser.getCompanyId());
						pic.setFiletype(pic.getFilename().split("\\.")[1]);
						companyUploadFileService.insertCompanyUploadFile(pic);
					}
					
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
	 * @param uploadFlag  1:上传会员照片
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
			out.put("memberShipCode", ssoUser.getMembershipCode());
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
						path=path+","+photoAbum.getPicAddress();
				}
				//uploadFlag  1:上传会员照片
				if(uploadFlag==1){
					String[] address=photoAbum.getPicAddress().split("/");
					if (address.length>=5) {
						CompanyUploadFileDO pic = new CompanyUploadFileDO();
						pic.setFilename(address[4]);
						String filePath=address[0]+"/"+address[1]+"/"+address[2]+"/"+address[3]+"/";
						pic.setFilepath(filePath);
						pic.setCompanyId(ssoUser.getCompanyId());
						pic.setFiletype(pic.getFilename().split("\\.")[1]);
						companyUploadFileService.insertCompanyUploadFile(pic);
					}
					
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
	 * @param uploadFlag  1:上传会员照片
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
		//uploadFlag  1:上传会员照片
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
		if (ssoUser != null && ssoUser.getCompanyId() != null && albumId != null) {
			out.put("memberShipCode", ssoUser.getMembershipCode());
			// groupId=1 为我的相册 2:公司相册 3:产品相册 4:互助相册
			if (groupIds == 1) {
				// 搜索出该公司所有相册类 的图片
				page = photoAbumService.queryPhotoAbumList(page, null,ssoUser.getCompanyId());
			} else {
				// abumList=photoAbumService.queryList(albumId,
				// ssoUser.getCompanyId());
				page = photoAbumService.queryPhotoAbumList(page, groupIds,ssoUser.getCompanyId());
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

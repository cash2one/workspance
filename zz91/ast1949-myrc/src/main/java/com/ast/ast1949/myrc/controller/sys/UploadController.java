/**
 * 
 */
package com.ast.ast1949.myrc.controller.sys;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CompanyUploadFileDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.products.ProductsPicDTO;
import com.ast.ast1949.myrc.controller.BaseController;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CompanyUploadFileService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.file.ScaleImage;
import com.zz91.util.http.HttpUtils;

/**
 * @author mays (mays@zz91.net)
 *
 * created by 2011-12-21
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

	final static String MODEL_CREDIT_FILE="creditfile";
	final static String MODEL_COMPANY="myrc";
	final static Integer LIMIT_ZST_PIC = 5;
	final static String LIMIT_ZST_PIC_INFO = "目前系统允许再生通会员一条信息只能上传"+LIMIT_ZST_PIC+"张图片";
	final static Integer LIMIT_PT_PIC = 1;
	final static String LIMIT_PT_PIC_INFO = "目前系统允许<b>普通会员</b>一条信息只能上传"+LIMIT_PT_PIC+"张图片 <a href='http://www.zz91.com/zst/apply.html' target='_blank'>升级成为高级会员可以上传更多图片</a>";

	@RequestMapping
	public ModelAndView product(HttpServletRequest request, Map<String, Object> out, 
			Integer productId, String destUrl, String error, String name,Integer id){
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
		
		out.put("productId", productId);
		out.put("destUrl", destUrl);
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView doProduct(HttpServletRequest request, Map<String, Object> out, 
			String destUrl, Integer productId, String name,Integer id){
		if(productId==null){
			productId=0;
		}
		
		if(destUrl==null){
			destUrl="";
		}
		
		String finalname=null;
		String error=null;
		ProductsPicDO pic=new ProductsPicDO();
		ProductsPicDO tPic = new ProductsPicDO();
		do{
			
			// 判断id 存在侧属于修改图片，同时判断图片id是否属于productId的  存在bug，供求是否也需要判断是否属于登陆用户
			if(id!=null){
				ProductsPicDTO dto = productsPicService.queryProductPicById(id);
				if(dto==null||dto.getProductsPicDO()==null){
					break;
				}
				if(productId>0&&!productId.equals(dto.getProductsPicDO().getProductId())){
					error = "productId and id can not be match(图片不属于该供求)";
					break;
				}
				tPic = dto.getProductsPicDO();
			}
			
			// 图片上传 图片表更新
			try {
				String filename=UUID.randomUUID().toString();
				String path=MvcUpload.getModalPath(ProductsPicService.UPLOAD_MODEL);
				finalname=MvcUpload.localUpload(request, path, filename);
				
				//改为缩略图
				ScaleImage is = new ScaleImage();
				is.saveImageAsJpg(path+"/"+finalname, path+"/"+finalname, 800, 800);
				//保存图片信息
//				String suffix=name.substring(finalname.lastIndexOf("."), finalname.length());
				
				pic.setName(name);
				pic.setPicAddress(path.replace(MvcUpload.getDestRoot()+"/", "")+"/"+finalname);
				pic.setProductId(productId);
				
				// 相册id
				if(tPic.getAlbumId()==null){
					pic.setAlbumId(0);
				}else{
					pic.setAlbumId(tPic.getAlbumId());
				}

				// 默认第一张图片置顶
				List<ProductsPicDO> picList =  productsPicService.queryProductPicInfoByProductsId(productId);
				if(picList==null||picList.size()==0){
					pic.setIsDefault(ProductsPicService.IS_DEFAULT);
				}

				// 审核状态 
				SsoUser ssoUser = getCachedUser(request);
				Integer companyId = ssoUser.getCompanyId();
				if(crmCompanySvrService.validatePeriod(companyId, CrmCompanySvrService.ZST_CODE)){
					// 高会免审核  高会限制 5 张 图片 超过5张 自动跳出
					if(picList!=null&&picList.size()>=LIMIT_ZST_PIC&&id==null){
						error = LIMIT_ZST_PIC_INFO;
						break;
					}
					pic.setCheckStatus(ProductsPicService.CHECK_STATUS_PASS);
				}else{
					// 普会重审 普会限制 1 张图片 超过1张执行更新
					if(picList!=null&&picList.size()>=LIMIT_PT_PIC&&id==null){
						error = LIMIT_PT_PIC_INFO;
						break;
					}
					pic.setCheckStatus(ProductsPicService.CHECK_STATUS_WAIT);
				}
				if(id==null){
					id=productsPicService.insertProductsPic(pic);
				}else{
					pic.setId(id);
					productsPicService.updateProductsPic(pic);
				}
				pic.setId(id);
			} catch (Exception e) {
				error = MvcUpload.getErrorMessage(e.getMessage());
			}
		}while(false);
		
		if(error!=null){
			out.put("productId", productId);
			out.put("destUrl", destUrl);
			try {
				out.put("name", URLEncoder.encode(name, HttpUtils.CHARSET_UTF8));
				out.put("error", URLEncoder.encode(error, HttpUtils.CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
			}
			return new ModelAndView("redirect:product.htm");
		}else{
			if(productId!=null&&productId>0){
				ProductsDO productsDO=productsService.queryProductsById(productId);
				String code=companyService.queryMembershipOfCompany(productsDO.getCompanyId());
				if("10051000".equals(code)){
					productsService.updateUncheckByIdForMyrc(productId);	
				}
			}
			out.put("success", "1");
//			out.put("data", JSONObject.fromObject(pic).toString());
			out.put("data", "{id:"+pic.getId()+",picAddress:\""+pic.getPicAddress()+"\"}");
			out.put("destUrl", destUrl);
			return null;
			
//			if(StringUtils.isNotEmpty(destUrl)){
//				return new ModelAndView("redirect:"+destUrl);
//			}else{
//				return new ModelAndView("redirect:"+request.getContextPath()+"/submitCallback.htm");
//			}
		}
		
	}
	
	@RequestMapping
	public ModelAndView company(HttpServletRequest request, Map<String, Object> out, 
			 String destUrl, String error, String remark){
		if(error==null){
			error="";
		}
		if(remark==null){
			remark="";
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
	public ModelAndView doCompany(HttpServletRequest request, Map<String, Object> out, 
			String destUrl, String remark){
		if(destUrl==null){
			destUrl="";
		}
		
		String finalname=null;
		String error=null;
		CompanyUploadFileDO pic=new CompanyUploadFileDO();
		try {
			String filename=UUID.randomUUID().toString();
			String path=MODEL_COMPANY+"/"+MvcUpload.getDateFolder();
			finalname=MvcUpload.localUpload(request, MvcUpload.getDestRoot()+"/"+path, filename);
			
			//改为缩略图
			ScaleImage is = new ScaleImage();
			is.saveImageAsJpg(MvcUpload.getDestRoot()+"/"+path+"/"+finalname, MvcUpload.getDestRoot()+"/"+path+"/"+finalname, 800, 800);
			//保存图片信息
//			String suffix=name.substring(finalname.lastIndexOf("."), finalname.length());
			
			pic.setRemark(remark);
			pic.setFilename(finalname);
			pic.setFilepath(path+"/");
			pic.setCompanyId(getCachedUser(request).getCompanyId());
			pic.setFiletype(pic.getFilename().split("\\.")[1]);
			
			Integer id=companyUploadFileService.insertCompanyUploadFile(pic);
			
			pic.setId(id);
		} catch (Exception e) {
			error = MvcUpload.getErrorMessage(e.getMessage());
		}
		
		if(error!=null){
			out.put("destUrl", destUrl);
			try {
				out.put("error", URLEncoder.encode(error, HttpUtils.CHARSET_UTF8));
				out.put("remark", URLEncoder.encode(remark, HttpUtils.CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
			}
			return new ModelAndView("redirect:company.htm");
		}else{
			out.put("success", "1");
			out.put("data", "{id:"+pic.getId()+",picAddress:\""+pic.getFilepath()+"/"+pic.getFilename()+"\"}");
			out.put("destUrl", destUrl);
			return null;
		}
		
	}
	
	@RequestMapping
	public ModelAndView creditFile(HttpServletRequest request, Map<String, Object> out, 
			 String destUrl, String error){
		if(error==null){
			error="";
		}
		try {
			out.put("error", URLDecoder.decode(error, HttpUtils.CHARSET_UTF8));
		} catch (UnsupportedEncodingException e) {
		}
		
		out.put("destUrl", destUrl);
		return null;
	}
	
	@RequestMapping
	public ModelAndView doCreditFile(HttpServletRequest request, Map<String, Object> out, 
			String destUrl){
		if(destUrl==null){
			destUrl="";
		}
		
		String finalname=null;
		String error=null;
		String finalFilePath="";
//		CompanyUploadFileDO pic=new CompanyUploadFileDO();
		try {
			String filename=UUID.randomUUID().toString();
			String path=MODEL_CREDIT_FILE+"/"+MvcUpload.getDateFolder();
			finalname=MvcUpload.localUpload(request, MvcUpload.getDestRoot()+"/"+path, filename);
			
			finalFilePath=path+"/"+finalname;
			
			//改为缩略图
			ScaleImage is = new ScaleImage();
			is.saveImageAsJpg(MvcUpload.getDestRoot()+"/"+path+"/"+finalname, MvcUpload.getDestRoot()+"/"+path+"/"+finalname, 800, 800);
			//保存图片信息
//			String suffix=name.substring(finalname.lastIndexOf("."), finalname.length());
			
//			pic.setFilename(finalname);
//			pic.setFilepath(path+"/");
//			pic.setCompanyId(getCachedUser(request).getCompanyId());
//			pic.setFiletype(pic.getFilename().split("\\.")[1]);
//			
//			Integer id=companyUploadFileService.insertCompanyUploadFile(pic);
//			
//			pic.setId(id);
		} catch (Exception e) {
			error = MvcUpload.getErrorMessage(e.getMessage());
		}
		
		if(error!=null){
			out.put("destUrl", destUrl);
			try {
				out.put("error", URLEncoder.encode(error, HttpUtils.CHARSET_UTF8));
//				out.put("remark", URLEncoder.encode(remark, HttpUtils.CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
			}
			return new ModelAndView("redirect:creditFile.htm");
		}else{
			out.put("success", "1");
			out.put("data", "{picAddress:\""+finalFilePath+"\"}");
			out.put("destUrl", destUrl);
			return null;
		}
		
	}
	
}

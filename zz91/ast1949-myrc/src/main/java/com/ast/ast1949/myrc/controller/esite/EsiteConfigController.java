/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-14
 */
package com.ast.ast1949.myrc.controller.esite;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.EsiteConfigDo;
import com.ast.ast1949.domain.company.EsiteCustomTopicDo;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.myrc.controller.BaseController;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.EsiteConfigService;
import com.ast.ast1949.service.company.EsiteCustomTopicService;
import com.ast.ast1949.service.company.EsiteService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.util.AstConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.file.Upload;
import com.zz91.util.lang.StringUtils;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-14
 */
@Controller
public class EsiteConfigController extends BaseController {

	@Autowired
	EsiteService esiteService;
	@Autowired
	EsiteConfigService esiteConfigService;
	@Autowired
	EsiteCustomTopicService esiteCustomTopicService;
	@Resource
	CrmCompanySvrService crmCompanySvrService;
	@Resource
	private PhoneService phoneService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {

		SsoUser sessionUser = getCachedUser(request);

		if (sessionUser == null) {
			out.put(AstConst.ERROR_TEXT, "对不起，您没有登录，无法编辑门市部！");
			return new ModelAndView("/common/error");
		}
		
		do {
			// 判断登录myrc 用户 是否开通 商铺服务
			Boolean spFlag = false;
			spFlag = crmCompanySvrService.validateEsitePeriod(sessionUser.getCompanyId());
			if (spFlag) {
				break;
			}

			if ("10051000".equals(sessionUser.getMembershipCode())
					|| "".equals(sessionUser.getMembershipCode())) {
				out.put(AstConst.ERROR_TEXT, "对不起，您的会员级别不能编辑门市部，请升级成再生通会员！");
				return new ModelAndView("/common/error");
			}
		} while (false);

		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));

		esiteService.initBaseConfig(sessionUser.getCompanyId(), null, out);

		return null;
	}

	@RequestMapping
	public ModelAndView saveconfig(HttpServletRequest request,
			EsiteConfigDo config, String topicImgRadio,Map<String, Object> out) throws IOException {
		SsoUser sessionUser = getCachedUser(request);

		if (sessionUser == null) {
			out.put(AstConst.ERROR_TEXT, "对不起，您没有登录，无法编辑门市部！");
			return new ModelAndView("/common/error");
		}
		if(StringUtils.isEmpty(config.getBannerPic())||config.getBannerPic()==null){
			esiteConfigService.updateIsShowForHeadPic(sessionUser.getCompanyId(), 1);
		}else{
			esiteConfigService.updateIsShowForHeadPic(sessionUser.getCompanyId(), 0);
		}
		do {
			// 判断登录myrc 用户 是否开通 商铺服务 或者 百度服务
			Boolean spFlag = false;
			spFlag = crmCompanySvrService.validateEsitePeriod(sessionUser.getCompanyId());
			if (spFlag) {
				break;
			}
			
			if ("10051000".equals(sessionUser.getMembershipCode())
					|| "".equals(sessionUser.getMembershipCode())) {
				out.put(AstConst.ERROR_TEXT, "对不起，您的会员级别不能编辑门市部，请升级成再生通会员！");
				return new ModelAndView("/common/error");
			}
		} while (false);

		if (config.getId() > 0) {
			esiteConfigService.updateColumnConfigById(config);
		} else {
			esiteConfigService.insertColumnConfig(config);
		}

		return printJs("parent.location.reload();", out);
	}

	@RequestMapping
	public void selectcolor() {

	}

	@RequestMapping
	public ModelAndView resetconfig(HttpServletRequest request, String column,
			Map<String, Object> out) throws IOException {
		SsoUser sessionUser = getCachedUser(request);

		if (sessionUser == null) {
			out.put(AstConst.ERROR_TEXT, "对不起，您没有登录，无法编辑门市部！");
			return new ModelAndView("/common/error");
		}

		esiteConfigService.deleteColumnConfigByCompany(
				sessionUser.getCompanyId(), column);

		return printJs("parent.location.reload()", out);
	}

	@RequestMapping
	public ModelAndView addcustomtopic(HttpServletRequest request,
			EsiteCustomTopicDo topic, Map<String, Object> out)
			throws IOException {
		SsoUser sessionUser = getCachedUser(request);

		if (sessionUser == null) {
			out.put(AstConst.ERROR_TEXT, "对不起，您没有登录，无法编辑门市部！");
			return new ModelAndView("/common/error");
		}

		if (esiteCustomTopicService.isTopicNumOverLimit(sessionUser
				.getCompanyId())) {
			topic.setCompanyId(sessionUser.getCompanyId());
			topic.setAccount(sessionUser.getAccount());
			esiteCustomTopicService.insertTopic(topic);
		}
		return printJs("parent.location.reload()", out);
	}

	@RequestMapping
	public ModelAndView deletecustomtopic(HttpServletRequest request,
			Map<String, Object> out, Integer deleteStyleId) throws IOException {
		SsoUser sessionUser = getCachedUser(request);

		if (sessionUser == null) {
			out.put(AstConst.ERROR_TEXT, "对不起，您没有登录，无法编辑门市部！");
			return new ModelAndView("/common/error");
		}
		esiteCustomTopicService.deleteTopicById(deleteStyleId,
				sessionUser.getCompanyId());
		return printJs("parent.location.reload()", out);
	}
    @RequestMapping
    public ModelAndView doDeleteTopicImg(HttpServletRequest request,Map<String, Object>out,Integer deletePicId,String imgPath) throws IOException{
    	SsoUser sessionUser=getCachedUser(request);
    	if (sessionUser == null||sessionUser.getCompanyId()==null) {
			out.put(AstConst.ERROR_TEXT, "对不起，您没有登录，无法编辑门市部！");
			return new ModelAndView("/common/error");
		}
    	String pic=esiteConfigService.queryBannelPic(sessionUser.getCompanyId());
    	if(StringUtils.isEmpty(pic)){
    		return new ModelAndView("/common/error");
    	}
    	String leng[]=new String []{};
    	if(StringUtils.isNotEmpty(pic)&&pic.contains(",")){
    		leng=pic.split(",");
    	}
        String picString=null;
    	if(leng.length>0&&deletePicId!=null){
    		for (int i = 0; i < leng.length; i++) {
				if(leng[i].equals(imgPath)){
					continue;
				}
				if(StringUtils.isEmpty(picString)){
				    picString=leng[i];
				}else {
					picString=picString+","+leng[i];
				}
			}
    		
    	}
    	esiteConfigService.updateBannelPicByCompanyId(picString, sessionUser.getCompanyId());
    	return printJs("parent.hideTopic("+deletePicId+")", out);
    }
	@RequestMapping
	public ModelAndView uploadZt(HttpServletRequest request,
			Map<String, Object> out) throws IOException, FileUploadException {
		SsoUser ssoUser=getCachedUser(request);
		String path = Upload.DEFAULT_ROOT_FOLDER + "/myrc/esite/"
				+ Upload.getInstance().getDateFolder();
		String file = null;
		try {
			file = MvcUpload.localUpload(request, path, UUID.randomUUID()
					.toString());
		} catch (Exception e) {
			file = e.getMessage();
		}
		String str="http://img1.zz91.com/myrc/esite/"+ Upload.getInstance().getDateFolder() + "/"+file;
		String pic=esiteConfigService.queryBannelPic(ssoUser.getCompanyId());
		if(StringUtils.isEmpty(pic)||pic==null){
			esiteConfigService.updateBannelPicByCompanyId(str, ssoUser.getCompanyId());	
		}
		String leng[] = new String[]{};
		if(StringUtils.isNotEmpty(pic)&&pic.contains(",")){
			leng=pic.split(",");
		}
		if(StringUtils.isNotEmpty(pic) && pic!=null&&4>leng.length){
			esiteConfigService.updateBannelPicByCompanyId(str+","+pic, ssoUser.getCompanyId());			
		}
		if(StringUtils.isNotEmpty(pic) && pic!=null&&leng.length>=4){
			esiteConfigService.updateBannelPicByCompanyId(str+","+leng[0]+","+leng[1]+","+leng[2], ssoUser.getCompanyId());
		}
		return printJs(
				"setTimeout(function(){parent.imgCallback('"+str+"',200);parent.hideLoading();},1000)", out);
	}

	@RequestMapping
	public ModelAndView uploadZp(HttpServletRequest request,
			Map<String, Object> out) throws IOException, FileUploadException {
		String path = Upload.DEFAULT_ROOT_FOLDER + "/myrc/esite/"
				+ Upload.getInstance().getDateFolder();
		String file = null;
		try {
			file = MvcUpload.localUpload(request, path, UUID.randomUUID()
					.toString());
		} catch (Exception e) {
			file = e.getMessage();
		}
		return printJs(
				"setTimeout(function(){parent.imgCallback('http://img1.zz91.com/myrc/esite/"
						+ Upload.getInstance().getDateFolder() + "/" + file
						+ "',90);parent.hideLoading();},1000)", out);
	}

	@RequestMapping
	public ModelAndView uploadLogo(HttpServletRequest request,
			Map<String, Object> out) throws IOException, FileUploadException {
		String path = Upload.DEFAULT_ROOT_FOLDER + "/myrc/esite/"
				+ Upload.getInstance().getDateFolder();
		// String file=MvcUpload.localUpload(request, path,
		// UUID.randomUUID().toString());
		String file = null;
		try {
			file = MvcUpload.localUpload(request, path, UUID.randomUUID()
					.toString());
		} catch (Exception e) {
			file = e.getMessage();
		}

		return printJs(
				"setTimeout(function(){parent.imgCallback('http://img1.zz91.com/myrc/esite/"
						+ Upload.getInstance().getDateFolder() + "/" + file
						+ "',80);parent.hideLoading();},1000)", out);
	}

	// @RequestMapping
	// public ModelAndView uploadZt(HttpServletRequest request,
	// Map<String, Object> out) throws IOException, FileUploadException{
	// UploadDto config = new UploadDto();
	// Map<String, String> paramMap =
	// ParamUtils.getInstance().getChild("upload_config");
	//
	// config.setTmpFolder(Upload.DEFAULT_TMP_FOLDER);
	// config.setRootFolder(Upload.DEFAULT_ROOT_FOLDER);
	// config.setUploadFolder("myrc/esite"+"/"+Upload.getInstance().getDateFolder());
	// config.setAllowFileType(
	// String.valueOf(paramMap.get("allow_filetype_img")).split(","));
	// config.setSizeMax(Integer.valueOf(paramMap.get("max_size_web")));
	//
	// List<UploadResult> list = Upload.getInstance().upload(request, config);
	// if(list.size()>0){
	// return
	// printJs("setTimeout(function(){parent.imgCallback('http://img1.zz91.com/"+list.get(0).getPath()+list.get(0).getUploadedFilename()+"',200);parent.hideLoading();},1000)",
	// out);
	// }
	// return printJs("alert('发生错误，图片没有上传，请再试几次');parent.hideLoading();", out);
	// }
	//
	// @RequestMapping
	// public ModelAndView uploadZp(HttpServletRequest request,
	// Map<String, Object> out) throws IOException, FileUploadException{
	// UploadDto config = new UploadDto();
	// Map<String, String> paramMap =
	// ParamUtils.getInstance().getChild("upload_config");
	//
	// config.setTmpFolder(Upload.DEFAULT_TMP_FOLDER);
	// config.setRootFolder(Upload.DEFAULT_ROOT_FOLDER);
	// config.setUploadFolder("myrc/esite"+"/"+Upload.getInstance().getDateFolder());
	// config.setAllowFileType(
	// String.valueOf(paramMap.get("allow_filetype_img")).split(","));
	// config.setSizeMax(Integer.valueOf(paramMap.get("max_size_web")));
	//
	// List<UploadResult> list = Upload.getInstance().upload(request, config);
	// if(list.size()>0){
	// return
	// printJs("setTimeout(function(){parent.imgCallback('http://img1.zz91.com/"+list.get(0).getPath()+list.get(0).getUploadedFilename()+"',90);parent.hideLoading();},1000)",
	// out);
	// }
	// return printJs("alert('发生错误，图片没有上传，请再试几次');parent.hideLoading();", out);
	// }
	//
	// @RequestMapping
	// public ModelAndView uploadLogo(HttpServletRequest request,
	// Map<String, Object> out) throws IOException, FileUploadException{
	// UploadDto config = new UploadDto();
	// Map<String, String> paramMap =
	// ParamUtils.getInstance().getChild("upload_config");
	//
	// config.setTmpFolder(Upload.DEFAULT_TMP_FOLDER);
	// config.setRootFolder(Upload.DEFAULT_ROOT_FOLDER);
	// config.setUploadFolder("myrc/esite"+"/"+Upload.getInstance().getDateFolder());
	// config.setAllowFileType(
	// String.valueOf(paramMap.get("allow_filetype_img")).split(","));
	// config.setSizeMax(Integer.valueOf(paramMap.get("max_size_web")));
	//
	// List<UploadResult> list = Upload.getInstance().upload(request, config);
	// if(list.size()>0){
	// return
	// printJs("setTimeout(function(){parent.imgCallback('http://img1.zz91.com/"+list.get(0).getPath()+list.get(0).getUploadedFilename()+"',80);parent.hideLoading();},1000)",
	// out);
	// }
	// return printJs("alert('发生错误，图片没有上传，请再试几次');parent.hideLoading();", out);
	// }
	
	@RequestMapping
	public ModelAndView ldbindex(HttpServletRequest request,Map<String, Object>out){
		SsoUser ssoUser=getCachedUser(request);
		if (ssoUser!=null&&ssoUser.getCompanyId()!=null) {
			Phone phone=phoneService.queryByCompanyId(ssoUser.getCompanyId());
			if (phone!=null) {
				out.put("phone", phone);
			}
			out.put("companyId", ssoUser.getCompanyId());
		}
		return null;
	}
	@RequestMapping
	public ModelAndView doLdbIndex(HttpServletRequest request,Integer phoneId,String photo,Integer localUpload){
		do {
			if (phoneId==null||phoneId.intValue()<=0) {
				break;
			}
			Phone phone=new Phone();
			if (localUpload==1) {
				String path = "myrc" + "/" + MvcUpload.getDateFolder();
				String filename = String.valueOf(System.currentTimeMillis());
				Integer i = 0;
				String uploadedFile = null;
				String fullUri = null;
				String destRoot = MvcUpload.getDestRoot();

				try {
					uploadedFile = MvcUpload.localUpload(request, destRoot + "/"
							+ path, filename);
					fullUri = path + "/" + uploadedFile;
					/**
					ScaleImage is = new ScaleImage();
					is.saveImageAsJpg(destRoot + "/" + fullUri, destRoot + "/"
						+ fullUri, 800, 800);
					 **/
					i = 1;
				} catch (Exception e) {
					e.printStackTrace();
				}
				phone.setPhotoCover(fullUri);
			}else {
				phone.setPhotoCover(photo);
			}
			phone.setId(phoneId);
			Integer j=phoneService.update(phone);
		} while (false);
		return new ModelAndView("redirect:/esite/esiteconfig/ldbindex.htm");
	}
}

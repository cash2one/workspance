package com.zz91.ep.myesite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.comp.CreditFile;
import com.zz91.ep.domain.comp.IndustryChainDto;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.service.common.MyEsiteService;
import com.zz91.ep.service.common.ParamService;
import com.zz91.ep.service.comp.CompAccountService;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.comp.CreditFileService;
import com.zz91.ep.service.comp.IndustryChainService;
import com.zz91.ep.service.trade.PhotoService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.domain.Param;
import com.zz91.util.lang.StringUtils;

@Controller
public class CompanyController extends BaseController {
	
	@Resource
	private CompProfileService compProfileService;
	@Resource
	private ParamService paramService;
	@Resource
	private TradeCategoryService tradeCategoryService;
	@Resource
	private CompAccountService compAccountService;
	@Resource
	private CreditFileService creditFileService;
	@Resource
	private PhotoService photoService;
	@Resource
	private IndustryChainService industryChainService;
	@Resource MyEsiteService myEsiteService;
	
	final static Map<Integer, String> MSG_MAP=new HashMap<Integer, String>();
    static{
    	MSG_MAP.put(1, "您的操作成功了");
    	MSG_MAP.put(2, "服务器发生错误，您的操作没有生效，请重试一次");
    	MSG_MAP.put(3, "您被迫转到这个页面的原因是您的信息不完善，这极不利于您在网站上的信息推广，请完善后再做其他操作！");
    }
    

	/**
	 * 首页
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request) {
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView("redirect:updateCompany.htm");
	}

//	/**
//	 * 修改公司基本信息页
//	 * 
//	 * @param out
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping
//	public ModelAndView updateCompany(Map<String, Object> out,
//			HttpServletRequest request) {
//		// 获取公司基本信息
//		CompProfile compProfile = compProfileService
//				.getCompProfileById(getCompanyId(request));
//		out.put("compProfile", compProfile);
//		// 获取公司类型
//		List<Param> companyCategory = paramService
//				.queryParamsByType("company_industry_code");
//		out.put("companyCategory", companyCategory);
//		// 获取所处行业类型
//		List<TradeCategory> categorys = tradeCategoryService
//				.queryCategoryByParent("1000", 0, 0);
//		
//		//产业链
//		List<IndustryChainDto> industryChainList =  industryChainService.queryIndustryChainByCid(getCompanyId(request),compProfile.getAreaCode());
//		
//		out.put("industryChainList", industryChainList);
//		out.put("companyIndustry", categorys);
//		out.put("item", "company");
//		return null;
//	}

	/**
	 * 修改公司基本信息
	 * 
	 * @param out
	 * @param request
	 * @param compProfile
	 * @return
	 */
//	@RequestMapping
//	public ModelAndView doCompany(Map<String, Object> out,
//			HttpServletRequest request, CompProfile compProfile,Integer [] industryChain) {
//		ExtResult result = new ExtResult();
//		do {
//			if (compProfile.getMainBuy() == null
//					|| compProfile.getMainBuy() == 0) {
//				compProfile.setMainBuy((short) 0);
//				compProfile.setMainProductBuy(null);
//			}
//			if (compProfile.getMainSupply() == null
//					|| compProfile.getMainSupply() == 0) {
//				compProfile.setMainSupply((short) 0);
//				compProfile.setMainProductSupply(null);
//			}
//			compProfile.setId(getCompanyId(request));
//			
//			result.setSuccess(compProfileService
//						.updateBaseCompProfile(compProfile,industryChain));
//			
//		} while (false);
//		return new ModelAndView("redirect:updateCompany.htm");
//	}

//	/**
//	 * 跳转到修改联系人页面
//	 * 
//	 * @param out
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping
//	public ModelAndView updateAccount(Map<String, Object> out,
//			HttpServletRequest request) {
//		EpAuthUser cachedUser = getCachedUser(request);
//		CompAccount account = compAccountService
//				.getCompAccountByCid(cachedUser.getCid());
//		out.put("account", account);
//		out.put("item", "company");
//		return null;
//	}
//
//	/**
//	 * 修改联系人信息
//	 * 
//	 * @param out
//	 * @param request
//	 * @param account
//	 * @return
//	 */
//	@RequestMapping
//	public ModelAndView doAccount(Map<String, Object> out,
//			HttpServletRequest request, CompAccount account) {
//		do {
//			Integer count = compAccountService.updateCompAccount(account);
//			if (count != null && count > 0) {
//				return new ModelAndView("redirect:updateAccount.htm");
//			}
//		} while (false);
//		return new ModelAndView("redirect:updateAccount.htm");
//	}

//	/**
//	 * 修改密码页面
//	 * 
//	 * @param out
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping
//	public ModelAndView updatePasswd(Map<String, Object> out,
//			HttpServletRequest request, String msg) {
//		out.put("item", "company");
//		out.put("msg", msg);
//		return null;
//	}

//	/**
//	 * 修改密码
//	 * 
//	 * @param out
//	 * @param request
//	 * @param password
//	 * @param newPassword
//	 * @return
//	 */
//	@RequestMapping
//	public ModelAndView doPasswd(Map<String, Object> out,
//			HttpServletRequest request, String password, String newPassword,
//			String confPassword) {
//		do {
//
//			if (StringUtils.isEmpty(newPassword)) {
//				break;
//			}
//			if (!newPassword.equals(confPassword)) {
//				break;
//			}
//			Integer count = compAccountService.updateAccountPwd(
//					getUid(request), password, newPassword);
//			if (count != null && count > 0) {
//				return new ModelAndView("redirect:updatePasswd.htm");
//			}
//		} while (false);
//		return new ModelAndView("redirect:updatePasswd.htm");
//	}
	
//    /**
//     * 荣誉证书管理
//     * @param out
//     * @param request
//     * @return
//     */
//    @RequestMapping
//    public ModelAndView credit(Map<String, Object> out, HttpServletRequest request) {
//    	EpAuthUser cachedUser = getCachedUser(request);
//    	List<CreditFile> list = creditFileService.queryCreditFileByCid(cachedUser.getCid(), null, null);
//    	out.put("creditList", list);
//    	out.put("item", "credit");
//        return null;
//    }
    
//    /**
//     * 上传荣誉证书
//     * @param out
//     * @param request
//     * @return
//     */
//    @RequestMapping
//    public ModelAndView doUploadCredit(Map<String, Object> out, HttpServletRequest request) {
//    	String filename=String.valueOf(System.currentTimeMillis());
//    	String path=buildPath("credit");
//    	String uploadedFile=null;
//		try {
//			uploadedFile = MvcUpload.localUpload(request, UPLOAD_ROOT+path, filename);
//		} catch (Exception e) {
//			e.printStackTrace();
//			out.put("data", MvcUpload.getErrorMessage(e.getMessage()));
//		}
//    	if(StringUtils.isNotEmpty(uploadedFile)){
//			out.put("success",true);
//    		out.put("data",path+"/"+uploadedFile);
//    	}
//        return null;
//    }
    
//    /**
//     * 添加荣誉证书
//     * @param out
//     * @param request
//     * @return
//     */
//    @RequestMapping
//    public ModelAndView addCredit(Map<String, Object> out, HttpServletRequest request) {
//    	out.put("item", "credit");
//        return null;
//    }
    
    

    
//    /**
//     * 修改荣誉证书名称
//     * @param out
//     * @param request
//     * @return
//     */
//    @RequestMapping
//    public ModelAndView updateCredit(Map<String, Object> out, HttpServletRequest request, Integer id, String fileName) {
//    	EpAuthUser cachedUser = getCachedUser(request);
//    	 ExtResult result = creditFileService.updateCreditFileName(id,cachedUser.getCid(),fileName);
//        return printJson(result, out);
//    }
    
//    /**
//     * 删除荣誉证书
//     * @param out
//     * @param request
//     * @param id
//     * @return
//     */
//    @RequestMapping
//    public ModelAndView doDelete(Map<String, Object> out, HttpServletRequest request, Integer id, String path) {
//    	EpAuthUser cachedUser = getCachedUser(request);
//        ExtResult result = creditFileService.deleteCreditById(id, cachedUser.getCid(), path);
//        return printJson(result, out);
//    }
    
//    /**
//     * 门市部装修-主营产品
//     * @param out
//     * @param request
//     * @return
//     */
//    @RequestMapping
//    public ModelAndView updateMainProduct(Map<String, Object> out, HttpServletRequest request) {
//    	EpAuthUser cachedUser = getCachedUser(request);
//    	CompProfile compProfile = compProfileService.getCompProfileById(cachedUser.getCid());
//    	out.put("compProfile", compProfile);
//    	out.put("item", "decora");
//        return null;
//    }
    
//    /**
//     * 修改公司主营业务
//     * @param out
//     * @param request
//     * @param mainProductSupply
//     * @param mainProductBuy
//     * @return
//     */
//    @RequestMapping
//    public ModelAndView doUpdateMain(Map<String, Object> out, HttpServletRequest request, String mainProductSupply, String mainProductBuy) {
//    	EpAuthUser cachedUser = getCachedUser(request);
//    	compProfileService.updateMainProduct(cachedUser.getCid(),mainProductSupply,mainProductBuy);
//    	return new ModelAndView("redirect:updateMainProduct.htm");
//    }
    
//    /**
//     * 门市部装修-公司LOGO
//     * @param out
//     * @param request
//     * @return
//     */
//    @RequestMapping
//    public ModelAndView updateLogo(Map<String, Object> out, HttpServletRequest request) {
//    	EpAuthUser cachedUser = getCachedUser(request);
//    	List<Photo> logoImage = photoService.queryPhotoByTargetType(PhotoService.TARGET_LOGO, cachedUser.getCid());
//        out.put("logoImage", logoImage);
//    	out.put("item", "decora");
//        return null;
//    }
    
//    /**
//     * 门市部装修-模版更改
//     * @param out
//     * @param request
//     * @return
//     */
//    @RequestMapping
//    public ModelAndView updateStyle(Map<String, Object> out, HttpServletRequest request) {
//    	EpAuthUser cachedUser = getCachedUser(request);
//    	String cssStyle = compProfileService.queryCssStyleByCid(cachedUser.getCid());
//    	out.put("cssStyle", cssStyle);
//    	out.put("item", "decora");
//        return null;
//    }
    
//    /**
//     * 门市部装修-模版更改
//     * @param out
//     * @param request
//     * @return
//     */
//    @RequestMapping
//    public ModelAndView doUpdateStyle(Map<String, Object> out, HttpServletRequest request,String type) {
//    	EpAuthUser cachedUser = getCachedUser(request);
//    	compProfileService.updateStyle(type, cachedUser.getCid());
//    	return new ModelAndView("redirect:updateStyle.htm");
//    }
    
    
    /**************************************************/
    
    /***
     * 以下为新版生意管家方法
     * @author qizj
     */
    @RequestMapping
    public ModelAndView updateCompany(Map<String, Object> out,HttpServletRequest request,Integer info,String basePath){
    	
    	EpAuthUser cachedUser = getCachedUser(request);
    	
    	// 获取公司基本信息
		CompProfile compProfile = compProfileService
				.getCompProfileById(cachedUser.getCid());
		out.put("compProfile", compProfile);
		// 获取公司类型
		List<Param> companyCategory = paramService
				.queryParamsByType("company_industry_code");
		out.put("companyCategory", companyCategory);
		
		// 获取所处行业类型
		List<TradeCategory> companyIndustry = tradeCategoryService
				.queryCategoryByParent("1000", 0, 0);
		out.put("companyIndustry", companyIndustry);
		
		//产业链
		List<IndustryChainDto> industryChainList =  industryChainService.queryIndustryChainByCid(cachedUser.getCid(), compProfile.getAreaCode());
		out.put("industryChainList", industryChainList);
		
		//获取账户信息
		CompAccount account = compAccountService
		.getCompAccountByCid(cachedUser.getCid());
		out.put("account", account);
		
		//产品形象
		List<Photo> compImage = photoService.queryPhotoByTargetType(PhotoService.TARGET_COMPANY, cachedUser.getCid());
        out.put("compImage", compImage);
		
        out.put("msg", MSG_MAP.get(info));
        myEsiteService.init(out, getCachedUser(request).getCid());
        // 之前访问页面
        out.put("basePath", basePath);
        
    	return null;
    }
    
    /**
     * 修改公司基本信息
     * @param out
     * @param request
     * @param compProfile
     * @param industryChain
     * @return
     */
	@RequestMapping
	public ModelAndView doUpdateCompany(Map<String, Object> out,
			HttpServletRequest request, CompProfile compProfile,Integer [] industryChain) {
		EpAuthUser cachedUser = getCachedUser(request);
		do {
			if (compProfile.getMainBuy() == null
					|| compProfile.getMainBuy() == 0) {
				compProfile.setMainBuy((short) 0);
				compProfile.setMainProductBuy(null);
			}
			if (compProfile.getMainSupply() == null
					|| compProfile.getMainSupply() == 0) {
				compProfile.setMainSupply((short) 0);
				compProfile.setMainProductSupply(null);
			}
			compProfile.setId(cachedUser.getCid());
			if(compProfileService.updateBaseCompProfile(compProfile,industryChain)){
				out.put("info", 1);
				return new ModelAndView("redirect:updateCompany.htm");
			}
		} while (false);
		out.put("info", 2);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView("redirect:updateCompany.htm");
	}
	
	@RequestMapping
	public ModelAndView deletePhoto(Map<String, Object> out,
			HttpServletRequest request, Integer pid, String path) {

		photoService.deletePhotoById(pid, null);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView("redirect:updateCompany.htm");
	}
	
	/**
	 * 修改联系人信息
	 * @param out
	 * @param request
	 * @param account
	 * @return
	 */
	@RequestMapping
	public ModelAndView doUpdateAccount(Map<String, Object> out,
			HttpServletRequest request, CompAccount account) {
		do {
			Integer count = compAccountService.updateCompAccount(account);
			if (count != null && count > 0) {
				out.put("info", 1);
				return new ModelAndView("redirect:updateCompany.htm");
			}
		} while (false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		out.put("info", 2);
		return new ModelAndView("redirect:updateCompany.htm");
	}
	
    /**
     * 荣誉证书管理
     * @param out
     * @param request
     * @return
     */
    @RequestMapping
    public ModelAndView credit(Map<String, Object> out, HttpServletRequest request,Integer info) {
    	out.put("msg", MSG_MAP.get(info));
    	
    	EpAuthUser cachedUser = getCachedUser(request);
    	List<CreditFile> list = creditFileService.queryCreditFileByCid(cachedUser.getCid(), null, null);
    	
    	out.put("creditList", list);
    	myEsiteService.init(out, getCachedUser(request).getCid());
        return new ModelAndView();
    }
	
    @RequestMapping
    public ModelAndView deleteCredit(Map<String, Object> out,HttpServletRequest request,Integer id,String path){
    	EpAuthUser cachedUser = getCachedUser(request);
    	ExtResult result = creditFileService.deleteCreditById(id, cachedUser.getCid(), path);
    	if(result.getSuccess()){
    		out.put("info", 1);
    	}else{
    		out.put("info", 2);
    	}
    	myEsiteService.init(out, getCachedUser(request).getCid());
    	return new ModelAndView("redirect:credit.htm");
    }
    
	@RequestMapping
	public ModelAndView updateCredit(Map<String, Object> out,
			HttpServletRequest request, String fileName, Integer id) {
		EpAuthUser cachedUser = getCachedUser(request);
		ExtResult result = creditFileService.updateCreditFileName(id,
				cachedUser.getCid(), fileName);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(result, out);
	}
	
	/**
     * 公司证书
     * @param out
     * @param request
     * @return
     */
    @RequestMapping
    public ModelAndView createCredit(Map<String, Object> out,HttpServletRequest request,Integer info){
    	String msg=null;
    	if (info!=null && info==0){
    		msg = "友情提示:发生错误,上传荣誉证书失败!";
    	}
    	out.put("msg",msg);
    	myEsiteService.init(out, getCachedUser(request).getCid());
    	return null;
    }
    
    /**
     * 添加荣誉证书
     * @param out
     * @param request
     * @return
     */
    @RequestMapping
    public ModelAndView doCreateCredit(Map<String, Object> out, HttpServletRequest request, String picture, String fileName) {
    	EpAuthUser cachedUser = getCachedUser(request);
    	ExtResult result = creditFileService.createCreditFile(cachedUser.getCid(), getUid(request).toString(), picture, fileName);
    	myEsiteService.init(out, getCachedUser(request).getCid());
        return printJson(result, out);
    }
    
    /**
     * 门市部装修-模版更新
     * @param out
     * @param request
     * @return
     */
    @RequestMapping
    public ModelAndView doUpdateStyle(Map<String, Object> out, HttpServletRequest request,String type) {
    	EpAuthUser cachedUser = getCachedUser(request);
    	Integer i = compProfileService.updateStyle(type, cachedUser.getCid());
    	Integer info=2;
    	if(i!=null && i.intValue()>0){
    		info=1;
    	}
    	myEsiteService.init(out, getCachedUser(request).getCid());
    	return new ModelAndView("redirect:updateStyle.htm?info="+info);
    }
    
    /**
     * 门市部模板
     * @param out
     * @param request
     * @return
     */
    @RequestMapping
    public ModelAndView updateStyle(Map<String, Object> out,HttpServletRequest request,Integer info){
    	EpAuthUser cachedUser = getCachedUser(request);
    	String cssStyle = compProfileService.queryCssStyleByCid(cachedUser.getCid());
    	out.put("cssStyle", cssStyle);
    	out.put("msg", MSG_MAP.get(info));
    	myEsiteService.init(out, getCachedUser(request).getCid());
    	return null;
    }
    
    /**
     * 修改密码页面
     * @param out
     * @param request
     * @param info
     * @return
     */
    @RequestMapping
    public ModelAndView updatePassword(Map<String, Object> out,HttpServletRequest request,Integer info){

    	out.put("msg", MSG_MAP.get(info));
    	myEsiteService.init(out, getCachedUser(request).getCid());
    	return null;
    }
    
    /**
	 * 修改密码
	 * @param out
	 * @param request
	 * @return
	 */
    @RequestMapping
    public ModelAndView doUpdatePassword(Map<String, Object> out,HttpServletRequest request,String password, 
    		String newPassword,String confPassword){
    	
    	do {
			if (StringUtils.isEmpty(newPassword)) {
				break;
			}
			if (!newPassword.equals(confPassword)) {
				break;
			}
			
			Integer count = compAccountService.updateAccountPwd(
					getUid(request), password, newPassword);
			if (count != null && count > 0) {
				return new ModelAndView("redirect:updatePassword.htm?info=1");
			}
		} while (false);
    	myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView("redirect:updatePassword.htm?info=2");
    }
    
    @RequestMapping
    public ModelAndView updateLogo(Map<String, Object> out,HttpServletRequest request,Integer info){
    	EpAuthUser cachedUser = getCachedUser(request);
    	
    	out.put("msg", MSG_MAP.get(info));
    	
    	List<Photo> logoImage = photoService.queryPhotoByTargetType(PhotoService.TARGET_LOGO, cachedUser.getCid());
        out.put("logoImage", logoImage);
        myEsiteService.init(out, getCachedUser(request).getCid());
    	return null;
    }
    
    @RequestMapping
    public ModelAndView doUpdateMainProduct(Map<String, Object> out,HttpServletRequest request,String mainProductSupply,String mainProductBuy){
    	EpAuthUser cachedUser = getCachedUser(request);
    	Integer i=compProfileService.updateMainProduct(cachedUser.getCid(),mainProductSupply,mainProductBuy);
    	if (i!=null && i.intValue()>0){
    		return new ModelAndView("redirect:updateMainProduct.htm?info=1");
    	}
    	myEsiteService.init(out, getCachedUser(request).getCid());
    	return new ModelAndView("redirect:updateMainProduct.htm?info=2");
    }
    
    @RequestMapping
    public ModelAndView updateMainProduct(Map<String, Object> out,HttpServletRequest request,Integer info){
    	EpAuthUser cachedUser = getCachedUser(request);
    	CompProfile compProfile = compProfileService.getCompProfileById(cachedUser.getCid());
    	out.put("compProfile", compProfile);
    	out.put("msg", MSG_MAP.get(info));
    	myEsiteService.init(out, getCachedUser(request).getCid());
    	return null;
    }
	
	/***********************************/

}

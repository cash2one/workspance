/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-23
 */
package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyUploadFileDO;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CompanyUploadFileService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.MyrcService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.MemberRuleFacade;
import com.ast.ast1949.service.market.MarketCompanyService;
import com.ast.ast1949.service.market.MarketService;
import com.ast.ast1949.service.phone.PhoneChangeLogService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.file.FileUtils;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;

/**
 * @author Mays (x03570227@gmail.com)
 * 
 */
@Controller
public class MycompanyController extends BaseController {

	@Resource
	private CompanyService companyService;
	@Resource
	private CompanyUploadFileService companyUploadFileService;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private MyrcService myrcService;
	@Resource
	private PhoneChangeLogService phoneChangeLogService; 
	@Resource
	private MarketService marketService;
	@Resource
	private MarketCompanyService marketCompanyService;
	
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@RequestMapping
	public void updateCompany(Map<String, Object> out,
			HttpServletRequest request, Integer firstLogin,String error) throws Exception {
		// CompanyDO company = getCachedCompany(request);
		// CompanyContactsDO account=getCachedAccount(request);
		SsoUser sessionUser = getCachedUser(request);
		// 查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());

		Company company = companyService.queryCompanyById(sessionUser
				.getCompanyId());
		//查看该公司是否有简版再生通服务
		boolean i = crmCompanySvrService.validatePeriod(sessionUser.getCompanyId(), CrmCompanySvrService.JBZST_CODE);
		out.put("key", i);
		// 变更之后要重新登录才会显示最新的公司信息
		// CompanyContactsDO c =
		// companyContactsService.queryContactByAccount(sessionUser.getAccount());

		// out.put("companyContacts", c);

		CompanyAccount companyAccount = companyAccountService
				.queryAccountByAccount(sessionUser.getAccount());
		String code = "";
		String address = "";
		if (StringUtils.isEmpty(company.getAreaCode())) {
			String tel = companyAccount.getMobile();
			code = companyService.getMobileLocation(tel);
			company.setAreaCode(code);
		}
		// 地址地区中文
		if (StringUtils.isEmpty(company.getAddress())) {
			if (StringUtils.isNotEmpty(company.getAreaCode())) {
				code = company.getAreaCode();
			} else {
				String tel = companyAccount.getMobile();
				code = companyService.getMobileLocation(tel);
			}
			if (code.length() >= 8) {
				address += CategoryFacade.getInstance().getValue(
						code.substring(0, 8));
			}
			if (code.length() >= 12) {
				address += CategoryFacade.getInstance().getValue(
						code.substring(0, 12));
			}
			if (code.length() >= 16) {
				address += CategoryFacade.getInstance().getValue(
						code.substring(0, 16));
			}
			company.setAddress(address);
		}

		// 获取用户消息信息 （问答、供求、帖子、询盘 条数）
		companyService.countCompanyInfo(sessionUser.getCompanyId(), out);

		out.put("company", company);
		out.put("firstLogin", firstLogin);
		out.put("companyAccount", companyAccount);
		out.put(FrontConst.MYRC_SUBTITLE, "修改公司基本信息");
		List<Market> listMarket=marketCompanyService.queryMarketByCompanyId(sessionUser.getCompanyId());
		out.put("market", listMarket);
		//弹框的标志
		Integer flag=marketService.getBoxFlag(sessionUser.getCompanyId());
		out.put("flag", flag);
		//错误的标志
		out.put("error", error);
	}

	/**
	 * 生意管家更改公司信息
	 * 
	 * @param company
	 * @param companyAccount
	 * @param out
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView update(Company company, CompanyAccount companyAccount,
			String regEmail, Map<String, Object> out, HttpServletRequest request,Integer introductionFlag,Integer businessFlag)
			throws UnsupportedEncodingException {
		SsoUser sessionUser = getCachedUser(request);

		if (company.getId() == null
				|| company.getId().intValue() != sessionUser.getCompanyId()
						.intValue()) {
			return new ModelAndView("redirect:updateCompany.htm");
		}
		if (StringUtils.isNotEmpty(regEmail)) {
			Integer i = companyAccountService.countUserByEmail(regEmail);
			if (i != null && i.intValue() > 0) {
				return new ModelAndView("redirect:updateCompany.htm");
			} else {
				companyAccount.setEmail(regEmail);
			}
		}
		
		companyAccount.setCompanyId(sessionUser.getCompanyId());
		companyAccount.setAccount(sessionUser.getAccount());
		companyAccount.setId(sessionUser.getAccountId());

		companyAccountService.updateAccountByUser(companyAccount);

		company.setId(sessionUser.getCompanyId());

		// 公司名称自动填充
		do {
			// 公司名称为空
			if (StringUtils.isNotEmpty(company.getName())) {
				break;
			}
			// 联系人不为空
			if (StringUtils.isEmpty(companyAccount.getContact())) {
				break;
			}
			// 不包含 中文
			if (!StringUtils.isContainCNChar(companyAccount.getContact())) {
				company.setName("个体经营");
				break;
			}
			// 包含中文，长度不大于 2 个
			if (companyAccount.getContact().length() < 2) {
				company.setName("个体经营");
			}
			company.setName("个体经营（" + companyAccount.getContact() + "）");
		} while (false);
		
		do {
			if (!"10051003".equals(sessionUser.getMembershipCode())) {
				break;
			}
			if (introductionFlag==1) {
				phoneChangeLogService.createLog(PhoneChangeLogService.TYPE_COMPANY_INTRO, company.getIntroduction(),sessionUser.getCompanyId(), sessionUser.getCompanyId());
				company.setIntroduction("");
			}
			if(businessFlag==1){
				phoneChangeLogService.createLog(PhoneChangeLogService.TYPE_COMPANY_BUSINESS, company.getBusiness(),sessionUser.getCompanyId(),sessionUser.getCompanyId());
				company.setBusiness("");
			}
			
		} while (false);
		
		Integer i = companyService.updateCompanyByUser(company);
		// 修改公司资料信息日志
		//获取ip地址
		String ip=HttpUtils.getInstance().getIpAddr(request);
        if(i>0){
        	LogUtil.getInstance()
			.log("myrc",
					"myrc-operate",
					ip,
					"{'account':'"
							+ companyAccount.getAccount()
							+ "','operatype_id':'8','gmt_created':'"
							+ DateUtil
									.toString(new Date(), DATE_FORMAT)
							+ "'}","myrc");
        }
		if (i != null && i.intValue() > 0) {
			scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(company.getId(), null, "base_company", null, null,null));
			// 更新成功后， 如果 更新的中国地区没有精确到 城市 ，则跳回弹出提示
			if ((company.getAreaCode().trim().startsWith("10011000")&&company.getAreaCode().length()<16)||"10011000".equals(company.getAreaCode().trim())) {
				return new ModelAndView("redirect:updateCompany.htm?error=1");
			}
			return new ModelAndView(new RedirectView("save.htm"));
		} else {
			return null;
		}
	}

	@RequestMapping
	public void save(HttpServletRequest request, Map<String, Object> out) {
		out.put(FrontConst.MYRC_SUBTITLE, "修改成功");
	}

	@RequestMapping
	public void companyList(HttpServletRequest request, Map<String, Object> out) {
		out.put(FrontConst.MYRC_SUBTITLE, "企业简介及照片");
		out.put("resourceUrl",
				MemcachedUtils.getInstance().getClient()
						.get("baseConfig.resource_url"));
		// 左侧s菜单
		// leftMenu(request,out);
		SsoUser sessionUser = getCachedUser(request);
		// 查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());

		Company c = companyService.queryCompanyById(sessionUser.getCompanyId());
		if (c.getIntroduction() != null) {
			c.setIntroduction(c.getIntroduction().replace("\n", "<br/>"));
			c.setIntroduction(Jsoup.clean(c.getIntroduction(),
					Whitelist.basic()));
		}
		out.put("company", c);
		List<CompanyUploadFileDO> list = companyUploadFileService
				.queryByCompanyId(sessionUser.getCompanyId());
		out.put("uploadedFileList", list);
	}

	@RequestMapping
	public void updateImg(HttpServletRequest request, Map<String, Object> out) {
		out.put("resourceUrl",
				MemcachedUtils.getInstance().getClient()
						.get("baseConfig.resource_url"));
		SsoUser sessionUser = getCachedUser(request);
		out.put("company", companyService.querySimpleCompanyById(sessionUser
				.getCompanyId()));
		List<CompanyUploadFileDO> list = companyUploadFileService
				.queryByCompanyId(sessionUser.getCompanyId());
		out.put("list", list);
		CompanyUploadFileDO companyUploadFileDO = new CompanyUploadFileDO();
		if (list.size() > 0) {
			companyUploadFileDO = list.get(0);
			out.put("companyUploadFileDO", companyUploadFileDO);
		}
		// out.put("uploadModel", FrontConst.UPLOAD_MODEL_MYRC);
		out.put("companyUploadFileDO", companyUploadFileDO);
		// 查找规则
		String ruleResult = MemberRuleFacade.getInstance().getValue(
				sessionUser.getMembershipCode(), "upload_company_picture");
		// 对比结果
		if (list != null
				&& list.size() >= Integer.valueOf(ruleResult).intValue()) {
			out.put("ruleResult", ruleResult);
			out.put("mypicsize", list.size());
			out.put("canUploadStatus", false);
		} else {
			out.put("canUploadStatus", true);
		}
		Integer limitCount=Integer.valueOf(ruleResult).intValue();
		if (list.size()>0) {
			limitCount=Integer.valueOf(ruleResult).intValue()-list.size();
		}
		out.put("limitCount", limitCount);
	}

	@RequestMapping
	public void postImg(Map<String, Object> out) {
		out.put("resourceUrl",
				MemcachedUtils.getInstance().getClient()
						.get("baseConfig.resource_url"));
		out.put("uploadModel", FrontConst.UPLOAD_MODEL_MYRC);

	}

	@RequestMapping
	public ModelAndView saveImg(CompanyUploadFileDO companyUploadFileDO,
			Map<String, Object> out, HttpServletRequest request)
			throws IOException {
		// myrc 左侧菜单
		ExtResult result = new ExtResult();
		SsoUser sessionUser = getCachedUser(request);
		do {
			if (sessionUser == null) {
				result.setData("session已经失效或者你还没有登录，请重新登录后再操作！");
				break;
			}
			if (sessionUser.getMembershipCode() == null) {
				// 系统的数据有问题，一般不会出现
				result.setData("您的账户存在问题，请联系zz91.com");
				break;
			}
			// 上传图片
			companyUploadFileDO.setCompanyId(sessionUser.getCompanyId());
			companyUploadFileDO.setFiletype(companyUploadFileDO.getFilename()
					.split("\\.")[1]);
			if (companyUploadFileService
					.insertCompanyUploadFile(companyUploadFileDO) > 0) {
				result.setSuccess(true);
				scoreChangeDetailsService
						.saveChangeDetails(new ScoreChangeDetailsDo(sessionUser
								.getCompanyId(), null, "base_compain_pic",
								null, null, null));
			}
			break;
		} while (true);
		return printJson(result, out);
	}

	@RequestMapping
	public void updateIntroduction(HttpServletRequest request,
			Map<String, Object> out) {
		SsoUser sessionUser = getCachedUser(request);
		out.put("company", companyService.queryCompanyDetailById(sessionUser
				.getCompanyId()));
	}

	@RequestMapping
	public ModelAndView saveIntroduction(Company company) {
		companyService.updateIntroduction(company.getId(),
				company.getIntroduction());
		return new ModelAndView(new RedirectView("companyList.htm"));
	}

	@RequestMapping
	public ModelAndView delete(Integer id) {
		CompanyUploadFileDO file = companyUploadFileService.queryById(id);
		FileUtils.deleteFile(MvcUpload.getDestRoot() + "/" + file.getFilepath()
				+ file.getFilename());
		companyUploadFileService.deleteComapanyUploadFileById(id);
		return new ModelAndView(new RedirectView("updateImg.htm"));
	}
	@RequestMapping
	public ModelAndView enterMarket(HttpServletRequest request,Map<String, Object> out) throws IOException{
		SsoUser sessionUser = getCachedUser(request);
		Company company=companyService.queryCompanyById(sessionUser.getCompanyId());
		String address="";
		if(company.getAreaCode().length()>15){
			address=CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 12))+" "+CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 16));
		}else if(company.getAreaCode().length()==8&&!"10011000".equals(company.getAreaCode())){
			address=CategoryFacade.getInstance().getValue(company.getAreaCode());
		}else{
	    	out.put("flag", -1);
	    }
		String industry=CategoryFacade.getInstance().getValue(company.getIndustryCode());
		out.put("industry", industry);
		out.put("area", address);
		//url
		if("废旧二手设备".equals(industry)){
			industry="二手设备";
		}
		out.put("industryCode", URLEncoder.encode(industry, HttpUtils.CHARSET_UTF8));
		out.put("areaCode", URLEncoder.encode(address, HttpUtils.CHARSET_UTF8));
		//已入驻市场
		String marketList="";
		List<Market> listM=marketCompanyService.queryMarketByCompanyId(sessionUser.getCompanyId());
		for(Integer i=0;i<listM.size();i++){
			if(i<listM.size()-1){
				marketList=marketList+listM.get(i).getName()+"、";
			}else{
				marketList=marketList+listM.get(i).getName();
			}
		}
		out.put("marketList", marketList);
		return new ModelAndView();
	}
	@RequestMapping
	public ModelAndView doEnterMarket(Map<String, Object> out,HttpServletRequest request,Integer marketId,String marketList) throws IOException{
		Integer flag=-1;
		SsoUser sessionUser = getCachedUser(request);
		Map<String,Integer> map=new HashMap<String,Integer>();
		if(sessionUser==null){
			map.put("flag", flag);
			return printJson(map, out);
		}
		flag=marketService.getBoxFlag(sessionUser.getCompanyId());
		if(flag==0&&marketId!=null){
			flag=marketCompanyService.insertMarketCompany(marketId, sessionUser.getCompanyId());
		}else if(marketList!=null){
			String[] marketL=marketList.split(",");
			for(String str:marketL){
				flag=marketCompanyService.insertMarketCompany(Integer.valueOf(str), sessionUser.getCompanyId());
			}
		}
		if(marketId!=null){
			map.put("flag", flag);
			return printJson(map, out);
		}
		out.put("flag", flag);
		return printJson(map, out);
	}
	@RequestMapping
	public ModelAndView deleteMarket(HttpServletRequest request,Map<String, Object> out,Integer marketId) throws IOException{
		Map<String,Integer> map=new HashMap<String,Integer>();
		SsoUser sessionUser = getCachedUser(request);
		Integer i=marketCompanyService.updateIsQuitByBothId(marketId, sessionUser.getCompanyId(), 1);
		map.put("flag", 1);
		return printJson(map, out);
	}

}

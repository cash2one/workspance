package com.ast.ast1949.company.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneClickLog;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.analysis.AnalysisOptNumDailyService;
import com.ast.ast1949.service.company.CategoryCompanyPriceService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.EsiteNewsService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.MemberRuleFacade;
import com.ast.ast1949.service.phone.PhoneClickLogService;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.PageCacheUtil;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class CompanyController extends BaseController {

	@Resource
	private CategoryService categoryService;
	@Resource
	private CompanyService companyService;
	@Resource
	private ProductsService productsService;
	@Resource
	private AnalysisOptNumDailyService analysisOptNumDailyService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private CompanyPriceService companyPriceService;
	@Resource
	private CategoryCompanyPriceService categoryCompanyPriceService;
	@Resource
	private EsiteNewsService esiteNewsService;
	@Resource
	private PhoneService phoneService;
	@Resource
	private PhoneLogService phoneLogService; 
	@Resource
	private PhoneClickLogService phoneClickLogService;

	private final static String DEFAULT_CITY_CODE = "10011000";
	private final static String ZZ91_DOMAIN = ".zz91.com";
	
	/**
	 * 公司黄页 首页
	 * @param out
	 */
	@RequestMapping
	public void index(Map<String, Object>out){
		// 新秀榜
		out.put("xxbList", crmCompanySvrService.queryLatestOpen(7));
		// 企业报价(最新前20条企业报价)方法如下:
		out.put("companyPriceList", companyPriceService.queryCompanyPriceByRefreshTime(null, 20));
		// 企业报价子类别 废塑料
		out.put("plasticList", categoryCompanyPriceService.queryCategoryCompanyPriceByCode("10001000"));
		// 企业报价子类别 废金属
		out.put("metalList", categoryCompanyPriceService.queryCategoryCompanyPriceByCode("1001"));
		
		// 最新供求
		PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		page.setPageSize(11);
		out.put("productList", productsService.pageLHProductsBySearchEngine(new ProductsDO(), "", null, page).getRecords());
		
		PageDto<ProductsDto> pageGY = new PageDto<ProductsDto>();
		PageDto<ProductsDto> pageQG = new PageDto<ProductsDto>();
		pageGY.setPageSize(6);
		pageQG.setPageSize(6);
		ProductsDO productsDO = new ProductsDO();
		// 供应
		productsDO.setProductsTypeCode(ProductsService.PRODUCTS_TYPE_OFFER);
		out.put("gyPicList", productsService.pageLHProductsBySearchEngine(productsDO, null, true, pageGY).getRecords());
		
		// 求购
		productsDO.setProductsTypeCode(ProductsService.PRODUCTS_TYPE_BUY);
		out.put("qgPicList", productsService.pageLHProductsBySearchEngine(productsDO, null, true, pageQG).getRecords());
		
		// 企业资讯
		out.put("esiteNewsList", esiteNewsService.queryList(11));
		
		// seo tkd
		SeoUtil.getInstance().buildSeo(out);
	}

	/**
	 * 公司黄页 (原)首页面 (现)列表页
	 * 
	 * @param out
	 * @param searchName
	 *            搜索关键字
	 * @param industryCode
	 *            所属行业
	 * @param areaCode
	 *            所属地区
	 * @param gardenCode
	 *            所属园区
	 * @param p
	 *            分页号
	 * @param pc
	 *            产品分类CODE
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public void list(Map<String, Object> out, String keywords, Company company, PageDto<CompanyDto> page)
			throws UnsupportedEncodingException {
		if(!StringUtils.isEmpty(keywords)){
			keywords = URLDecoder.decode(keywords, "utf-8");
			out.put("keywords", keywords);
			out.put("keywordsEncode", URLEncoder.encode(keywords, "utf-8"));
			company.setName(keywords);
		}
		page = companyService.pageCompanyBySearchEngine(company, page);
		Integer  cpage = page.getStartIndex()/page.getPageSize()+1;
		out.put("page", page);
		if(StringUtils.isEmpty(keywords)&&StringUtils.isEmpty(company.getIndustryCode())&&StringUtils.isEmpty(company.getAreaCode())){
			out.put("flag",1);
		}
		do{
			// 省份存在 
			String aiKey = "";
			boolean flag = false;
			if(StringUtils.isNotEmpty(company.getAreaCode())){
				flag = true;
				if(company.getAreaCode().length()==16){
					aiKey += CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 12))+CategoryFacade.getInstance().getValue(company.getAreaCode());
				}
				if(company.getAreaCode().length()==12){
					aiKey += CategoryFacade.getInstance().getValue(company.getAreaCode());
				}
			}
			// 主营存在
			if(StringUtils.isNotEmpty(company.getIndustryCode())){
				flag = true;
				aiKey += CategoryFacade.getInstance().getValue(company.getIndustryCode());
			}
			if(flag){
				SeoUtil.getInstance().buildSeo("dindex", new String[]{aiKey,cpage.toString()}, new String[]{aiKey}, new String[]{aiKey}, out);
				break;
			}
			//关键字非空
			if(StringUtils.isNotEmpty(keywords)){
				SeoUtil.getInstance().buildSeo("index", new String[]{keywords,cpage.toString()}, new String[]{keywords}, new String[]{keywords}, out);
				break;
			}
			//关键字为空，当page <=1时
			if(cpage  <= 1){
				SeoUtil.getInstance().buildSeo(out);
				break;
			}else{
				SeoUtil.getInstance().buildSeo("indexwithpage",new String[]{cpage.toString()},new String[]{},new String[]{},out);
				break;
			}
		}while(false);
//		if(StringUtils.isEmpty(keywords)){
//			if(cpage  > 1){
//				//没有关键字，当page >1时
//				SeoUtil.getInstance().buildSeo("indexwithpage", new String[]{keywords,currentpage}, null, null, out);
//			}else{
//				//没有关键字，当page <=1时
//				SeoUtil.getInstance().buildSeo(out);
//			}
//		}else{
//			//如果关键字不空
//			SeoUtil.getInstance().buildSeo("index", new String[]{keywords,currentpage}, new String[]{keywords}, new String[]{}, out);
//		}
		
		//out.put("companyByLogin", companyService.queryCompanyByLoginNum(5));
		String provinceCode = company.getAreaCode();
		if(StringUtils.isNotEmpty(provinceCode)){
			if(provinceCode.length()==16){
				provinceCode = provinceCode.substring(0,12);
			}
			out.put("cityList", CategoryFacade.getInstance().getChild(provinceCode));
			out.put("provinceCode", provinceCode);
		}
		out.put("company", company);
		Map<String , String > map = new TreeMap<String, String>();
		Map<String , String > keyMap = CategoryFacade.getInstance().getChild("1000");
		for(String key:keyMap.keySet()){
			map.put(key, keyMap.get(key));
		}
		out.put("industryCodeList", map);
		out.put("areaCodeList", CategoryFacade.getInstance().getChild("10011000"));
		out.put("companyList", companyService.queryRecentZst(10));	
	}

	final static int DEFAULT_PRODUCTS_LIST = 12;

	@RequestMapping
	public ModelAndView compinfo(HttpServletRequest request, HttpServletResponse response, 
			String id, PageDto<ProductsDO> page, String a, Map<String, Object> out) {
		
		do {
			if( id==null && StringUtils.isNotEmpty(a)){
				id=String.valueOf(companyAccountService.queryCompanyIdByAccount(a));
			}
			
			if (!StringUtils.isNumber( id)) {
				out.put(AstConst.ERROR_TEXT, "对不起，没有您查看的公司信息，请检查地址是否正确！");
				break;
			}
			Company company = companyService.queryCompanyById(Integer.valueOf( id));
			if (company == null) {
				out.put(AstConst.ERROR_TEXT, "对不起，没有您查看的公司信息，请检查地址是否正确！");
				break;
			}
			if (company.getIsBlock() == null || "1".equals(company.getIsBlock())) {
				out.put(AstConst.ERROR_TEXT, "对不起，该公司已不做生意或账号被冻结！");
				break;
			}
			
			// 商铺服务会员自动跳转至门市部
			if(crmCompanySvrService.validatePeriod(company.getId(), CrmCompanySvrService.ESITE_CODE)&&StringUtils.isNotEmpty(company.getDomainZz91())){
				return new ModelAndView("redirect:http://"+company.getDomainZz91()+ZZ91_DOMAIN);
			}
			
			out.put("companyinfo", company);
			//判断是否可以查看联系信息
			boolean loginedFlag = false, viewedFlag = false,ldbFlag = false;
			SsoUser ssoUser = getCachedUser(request);
			if (ssoUser != null) {
				loginedFlag=crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), "1000");
				if(!loginedFlag){
					loginedFlag=crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), "1006");
				}
			}
			
			if(!loginedFlag){
				viewedFlag=crmCompanySvrService.validatePeriod(company.getId(), "1000");
				if(!viewedFlag){
					viewedFlag=crmCompanySvrService.validatePeriod(company.getId(), "1006");
				}
				if(!viewedFlag){
					viewedFlag=crmCompanySvrService.validatePeriod(company.getId(), CrmCompanySvrService.LDB_CODE);
				}
			}
			// 普会来电宝客户访问普会
			if(!loginedFlag && !viewedFlag){
				if(ssoUser != null&&crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), CrmCompanySvrService.LDB_CODE)){
					ldbFlag = true;
					Phone phone = phoneService.queryByCompanyId(ssoUser.getCompanyId());
					String balance = phoneLogService.countBalance(phone);
					PhoneClickLog phoneClickLog = phoneClickLogService.queryById(ssoUser.getCompanyId(), Integer.valueOf(id));
					if(phoneClickLog!=null){
						out.put("ldbFlag", 2);
					}else{
						if(StringUtils.isNotEmpty(balance)&&Double.valueOf(balance)>0){
							// 余额 大于1 
							out.put("ldbFlag", 1);
						}else{
							// 服务过期等等
							out.put("ldbFlag", 0);
						}
					}
				}
			}
			
			out.put("viewFlag", (loginedFlag || viewedFlag ||ldbFlag));
			//查找公司的供求信息（分页）
			page.setPageSize(DEFAULT_PRODUCTS_LIST);
			page.setSort("refresh_time");
			page.setDir("desc");
			page = productsService.pageProductsOfCompany(Integer.valueOf( id),page);
			
			out.put("page", page);

			//询盘初始化
			Inquiry inquiry= new Inquiry();
			inquiry.setTitle(company.getName());
			inquiry.setBeInquiredId(company.getId());
			inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_COMPANY);
			CompanyAccount account=companyAccountService.queryAdminAccountByCompanyId(Integer.valueOf( id));

			if (account != null) {
				inquiry.setReceiverAccount(account.getAccount());
			}
			out.put("inquiry", inquiry);
			
			out.put("defaultContact", account);
			
			String industryLabel=CategoryFacade.getInstance().getValue(company.getIndustryCode());
//			PageHeadDTO headDTO = new PageHeadDTO();
//			headDTO.setTopNavIndex(NavConst.COMPANY);
//			headDTO.setPageTitle(company.getName()+"_"+industryLabel+"_企业|黄页大全_${site_name}");
//			headDTO.setPageKeywords(company.getName()+","+industryLabel+"企业黄页,"+industryLabel+"公司黄页,"+industryLabel+"企业名录,"+industryLabel+"公司大全");
			String mainBussiness = StringUtils.getNotNullValue(company.getSaleDetails())
				+StringUtils.getNotNullValue(company.getBuyDetails());
			if(mainBussiness.length()<5 && company.getIntroduction()!=null){
				mainBussiness = Jsoup.clean(company.getIntroduction(), Whitelist.none());
				if(mainBussiness!=null && mainBussiness.length()>150){
					mainBussiness=mainBussiness.substring(0,150);
				}
			}
//			headDTO.setPageDescription("${site_name}"+company.getName()+",主营:"+mainBussiness);
//			setSiteInfo(headDTO, out);
			
			
			SeoUtil.getInstance().buildSeo("compinfo", 
					new String[]{company.getName(),industryLabel}, 
					new String[]{company.getName(),industryLabel}, 
					new String[]{company.getName(),mainBussiness}, out);
			
			PageCacheUtil.setNoCDNCache(response);
			
			return new ModelAndView();
		} while (false);
		return new ModelAndView("/common/error");
	}

	@Deprecated
	@RequestMapping
	public ModelAndView recommendComp(Map<String, Object> out) {

		return null;
	}

	@Deprecated
	@RequestMapping
	public ModelAndView sendRecommendComp(Map<String, Object> out, 
			String toEmail1, String toEmail2, String toEmail3) {
//		if (StringUtils.isEmail(toEmail1)) {
//			recommendDO.setToEmail(toEmail1);
//			if (sendMail(recommendDO)) {
//				recommendService.insertRecommend(recommendDO);
//			}
//		}
//		if (StringUtils.isEmail(toEmail2)) {
//			recommendDO.setToEmail(toEmail2);
//			if (sendMail(recommendDO)) {
//				recommendService.insertRecommend(recommendDO);
//			}
//		}
//		if (StringUtils.isEmail(toEmail3)) {
//			recommendDO.setToEmail(toEmail3);
//			if (sendMail(recommendDO)) {
//				recommendService.insertRecommend(recommendDO);
//			}
//		}
		return null;
	}

	@RequestMapping
	public ModelAndView getProvince(Map<String, Object> model) throws IOException {
		return printJson(JSONArray.fromObject(categoryService.child(DEFAULT_CITY_CODE)), model);
	}

//	@RequestMapping
//	public ModelAndView getGardens(String code, Map<String, Object> model) throws IOException {
//		CategoryGardenDO categoryGardenDO = new CategoryGardenDO();
//		categoryGardenDO.setAreaCode(code);
//		List<CategoryGardenDO> list = categoryGardenService
//				.queryCategoryGardenByAreaCode(categoryGardenDO);
//		return printJson(list, model);
//	}

	@RequestMapping(value = "getCity.htm", method = RequestMethod.GET)
	public ModelAndView getCity(Map<String, Object> model, String pid) throws IOException {
		JSONArray json = JSONArray.fromObject(categoryService.child(pid));
		return printJson(json, model);
	}

	@RequestMapping(value = "getCounty.htm", method = RequestMethod.GET)
	public ModelAndView getCounty(Map<String, Object> model, String cid) throws IOException {
		JSONArray json = JSONArray.fromObject(categoryService.child(cid));
		return printJson(json, model);
	}

	@RequestMapping(value = "getIndustryCodeByCode.htm", method = RequestMethod.GET)
	public ModelAndView getIndustryCodeByCode(String code, Map<String, Object> out)
			throws IOException {
		JSONArray json = JSONArray.fromObject(categoryService.queryCategoriesByPreCode(code));
		return printJson(json, out);
	}
	
	@RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request, Map<String, Object> out, 
			String success, String data){
		if(StringUtils.isEmpty(data)){
			data="{}";
		}
		try {
			data=StringUtils.decryptUrlParameter(data);
		} catch (UnsupportedEncodingException e) {
		}
		out.put("success", success);
		out.put("data", data);
		return null;
	}

	//	@RequestMapping
	//	public ModelAndView getContactInformation(HttpServletRequest request, Map<String, Object> out,
	//			String id) throws IOException {
	//		if (!StringUtils.isNumber(id)) {
	//			return printJson(null, out);
	//		} else {
	//			Boolean havePower = false;
	//			// 查找公司信息
	//			CompanyDO company = companyService.selectCompanyById(Integer.parseInt(id));
	//			if (company != null) {
	//				CompanyDO loginCompany = getCachedCompany(request);
	//				if (loginCompany != null) {
	//					// 被查看用户
	//					if (company.getMembershipCode().equals("10051001")
	//							|| company.getMembershipCode().equals("10051002")) {
	//						// 高级会员只要登录了就可以随意查看其他客户的联系方式
	//						havePower = true;
	//					} else if (company.getMembershipCode().equals("10051000")) {
	//						// 查看用户
	//						if (loginCompany.getMembershipCode().equals("10051001")
	//								|| loginCompany.getMembershipCode().equals("10051002")) {
	//							havePower = true;
	//						} else if (loginCompany.getMembershipCode().equals("10051000")
	//								&& getViewNumber() <= 200) {
	//							havePower = true;
	//						} else if (getViewNumber() <= 200) {
	//							havePower = true;
	//						} else {
	//							havePower = false;
	//						}
	//					} else {
	//						// 查看用户
	//						if (loginCompany.getMembershipCode().equals("10051001")
	//								|| loginCompany.getMembershipCode().equals("10051002")) {
	//							havePower = true;
	//						} else if (loginCompany.getMembershipCode().equals("10051000")
	//								&& getViewNumber() <= 200) {
	//							havePower = true;
	//						} else if (getViewNumber() <= 200) {
	//							havePower = true;
	//						} else {
	//							havePower = false;
	//						}
	//					}
	//				} else {
	//					// 提示未登录
	//					havePower = false;
	//				}
	//			}
	//			if (havePower) {
	//				// 统计查看次数
	////				CompanyDO loginCompany = getCachedCompany(request);
	////				CompanyContactsDO loginCompanyContacts = getCachedAccount(request);
	////				if (loginCompany != null) {
	////					StatViewDetailsDO statView = new StatViewDetailsDO();
	////					statView.setCompanyId(loginCompany.getId());
	////					if (loginCompanyContacts != null) {
	////						statView.setAccount(loginCompanyContacts.getAccount());
	////					}
	////					statView.setStatType(StatConst.STAT_CONTACT.toString());
	////					if (company != null) {
	////						statView.setToCompanyId(company.getId());
	////					}
	//////					statViewDetailsService.insertStatViewDetails(statView);
	////				}
	//
	//				CompanyContactsDO contacts = companyContactsService
	//						.getTheFirstCompanyContactsByCompanyId(Integer.parseInt(id));
	//				return printJson(contacts, out);
	//			} else {
	//				return printJson(null, out);
	//			}
	//		}
	//	}

	/**
	 * <br />
	 * 创建图片联系方式 <br />
	 * 当用户访问公司详细信息页面和供求详细页面，并在访问过程中点击查看联系方式 <br />
	 * 按钮时，系统通过这个请求将contact,(tel_country_code,tel_area_code,tel), <br />
	 * mobile,email生成图片联系方式及对应的key，并存放到缓存中（1分钟） <br />
	 * <br />
	 * 规则如下 <br />
	 * 发布者 查看者 结果 限制(每天) <br />
	 * 再生通 普通 true 无限制 <br />
	 * 再生通 再生通 true 无限制 <br />
	 * 普通 普通 false 0 <br />
	 * 普通 再生通 true 200（普通会员） <br />
	 * <br />
	 * 当结果为true时，帮助用户查找联系方式 <br />
	 * 当结果为true时，记录操作AnalysisOptNumDailyService.OPT_BE_VIEWED_CONTACT的次数 <br />
	 * 当结果为true，并且发布者为普通会员时，记录操作AnalysisOptNumDailyService.OPT_VIEW_CONTACT_PAID_FALSE的次数
	 * 
	 * @param cid
	 * @return ExtResult.success：true 表示联系方式生成成功,此时，ExtResult.data中存放的对象 是: CompanyContactsDO对象对应的Json对象
	 *         ExtResult.success：false 表示联系方式生成失败，错误原因由ExtResult.data提供
	 */
	@RequestMapping
	public ModelAndView createImageContactInfo(Map<String, Object> out, Integer cid,
			HttpServletRequest request) throws IOException {
		ExtResult result = new ExtResult();

		do {

			SsoUser ssoUser = getCachedUser(request);
//			if (ssoUser == null) {
//				result.setData("sessionTimeOut");
//				break;
//			}

//			String beViewedCompanyMembership = companyService.queryMembershipOfCompany(cid);
//			if(StringUtils.isEmpty(beViewedCompanyMembership)){
//				result.setData("companyInfoBroken");
//				break;
//			}

			boolean toViewFlag = false, beViewedFlag = false, isUnlogin=false,isLdbMember=false;
			
			if(ssoUser==null){
				isUnlogin=true;
			}
			
			if (ssoUser!=null) {
				toViewFlag=crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), "1000");
				if(!toViewFlag){
					toViewFlag=crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), "1006");
				}
				if(!toViewFlag){
					toViewFlag=crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), CrmCompanySvrService.LDB_CODE);
				}
			}

			beViewedFlag=crmCompanySvrService.validatePeriod(cid, "1000");
			if (!beViewedFlag) {
				beViewedFlag = crmCompanySvrService.validatePeriod(cid, "1006");
			}
			
			// 验证是否享有来电宝服务
//			isLdbMember = crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), CrmCompanySvrService.LDB_CODE);
			
			if (!(toViewFlag || beViewedFlag || isUnlogin||isLdbMember)) {
				result.setData("memberAuthorFailure");
				break;
			}

			//检查查看次数是否超过（如果被查看的对象是普通会员）
			if (!beViewedFlag && ssoUser!=null) {

				//限制次数
				Integer viewedNum = Integer.valueOf(MemberRuleFacade.getInstance().getValue(ssoUser.getMembershipCode(), "view_member_contacts_num"));

				//今天已查看次数
				Integer viewedTodayNum = analysisOptNumDailyService.queryOptNumByAccountToday(
						AnalysisOptNumDailyService.OPT_VIEW_CONTACT_PAID_FALSE, ssoUser.getAccount());

				if (viewedTodayNum.intValue() >= viewedNum.intValue()) {
					result.setData("overLimit");
					break;
				}

				analysisOptNumDailyService.insertOptNum(
						AnalysisOptNumDailyService.OPT_VIEW_CONTACT_PAID_FALSE, ssoUser.getAccount(), ssoUser.getCompanyId());
			}

//			CompanyAccount beViewedAccount = companyAccountService.queryAdminAccountByCompanyId(cid);
//			
			CompanyDto beViewedCompany=companyService.queryCompanyWithAccountById(cid);
			if (beViewedCompany == null) {
				result.setData("contactInfoBroken");
				break;
			}

			analysisOptNumDailyService.insertOptNum(
					AnalysisOptNumDailyService.OPT_BE_VIEWED_CONTACT,
					beViewedCompany.getAccount().getAccount(), cid);
			//将信息放在缓存中
			String tel = "";
			if (StringUtils.isNotEmpty(beViewedCompany.getAccount().getTelCountryCode())) {
				tel = beViewedCompany.getAccount().getTelCountryCode() + "-";
			}
			if (StringUtils.isNotEmpty(beViewedCompany.getAccount().getTelAreaCode())) {
				tel = tel + beViewedCompany.getAccount().getTelAreaCode() + "-";
			}
			if (StringUtils.isNotEmpty(beViewedCompany.getAccount().getTel())) {
				tel = tel + beViewedCompany.getAccount().getTel();
			}
			beViewedCompany.getAccount().setTelAreaCode(null);
			beViewedCompany.getAccount().setTelCountryCode(null);
			if(crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.LDB_CODE)){
				Phone phone = phoneService.queryByCompanyId(cid);
				if(phone!=null){
					beViewedCompany.getAccount().setTel(sendInfoToCache(phone.getTel()));
					beViewedCompany.setIsLDB(true);
				}else{
					beViewedCompany.getAccount().setTel(sendInfoToCache(tel));
				}
			}else{
				beViewedCompany.getAccount().setTel(sendInfoToCache(tel));
			}
//			beViewedCompany.getAccount().setTel(sendInfoToCache(tel));
			//beViewedCompany.getAccount().setContact(sendInfoToCache(beViewedCompany.getAccount().getContact()));
			beViewedCompany.getAccount().setMobile(sendInfoToCache(beViewedCompany.getAccount().getMobile()));
			if("1".equals(beViewedCompany.getAccount().getIsUseBackEmail()) && beViewedCompany.getAccount().getBackEmail()!=null){
				beViewedCompany.getAccount().setEmail(sendInfoToCache(beViewedCompany.getAccount().getBackEmail()));
			}else{
				beViewedCompany.getAccount().setEmail(sendInfoToCache(beViewedCompany.getAccount().getEmail()));
			}
			//beViewedCompany.getAccount().setEmail(sendInfoToCache(beViewedCompany.getAccount().getEmail()));
			

			result.setSuccess(true);
			result.setData(beViewedCompany);
		} while (false);

		return printJson(result, out);
	}

	final static int INFO_CACHE_TIME = 60;

	String sendInfoToCache(String info) {
		if (StringUtils.isNotEmpty(info)) {
			String key = UUID.randomUUID().toString();
			MemcachedUtils.getInstance().getClient().set(key, INFO_CACHE_TIME, info);
			return key;
		}
		return null;
	}

	/**
	 * <br />
	 * 获取图片联系方式 <br />
	 * 当用户访问公司详细信息页面和供求详细页面，点击了查看联系方式按钮，并成功返 <br />
	 * 回时，系统通过这个请求，分别获取同一公司信息的不同联系方式
	 * 
	 * @param type
	 *            ：联系方式类型，null或空不获取信息
	 * @param key
	 *            ：每个联系方式对应的key，null或空则不获取任何信息
	 * @return
	 */
	@RequestMapping
	public ModelAndView viewContactInfo(Map<String, Object> out, String type, String key,String color,
			HttpServletResponse response) throws IOException {

		String s = (String) MemcachedUtils.getInstance().getClient().get(key);
		if (s == null) {
			return null;
		}
		MemcachedUtils.getInstance().getClient().delete(key);

		int width = s.getBytes().length * 8 + 2;
		int height = 16;

		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, width, height);
		if("red".equals(color)){
			g2.setColor(Color.red);
		}else{
			g2.setColor(Color.black);
		}
		g2.drawString(s, 2, 13);
		ImageIO.write(bi, "jpg", response.getOutputStream());
		return null;
	}
	
	@RequestMapping
	public ModelAndView checkuser(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response, String username, String password, String randCode,
			String randCodeKey, String url, String cookieMaxAge) throws IOException {
		ExtResult extResult = new ExtResult();
		
		SsoUser ssoUser=null;
		try {
			ssoUser = SsoUtils.getInstance().validateUser(response, username, password, null, null);
		} catch (NoSuchAlgorithmException e) {
		} catch (AuthorizeException e) {
			extResult.setData(AuthorizeException.getMessage(e.getMessage()));
		}
		if(ssoUser!=null){
			setSessionUser(request, ssoUser);
			extResult.setSuccess(true);
		}else{
			extResult.setData("用户名或者密码写错了，检查下大小写是否都正确了，再试一次吧");
		}
		
		return printJson(extResult, model);
	}
	
	@RequestMapping
	public ModelAndView markToClick(HttpServletRequest request,Integer targetId,Map<String, Object>out) throws IOException{
		ExtResult result = new ExtResult();
		do {
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser==null||targetId==null){
				break;
			}
			PhoneClickLog phoneClickLog = new PhoneClickLog();
			phoneClickLog.setCompanyId(ssoUser.getCompanyId());
			phoneClickLog.setTargetId(targetId);
			// 是否 来电宝五元用户
			if(crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), CrmCompanySvrService.LDB_FIVE_CODE)){
				phoneClickLog.setClickFee(5.0f);
			}else{
				phoneClickLog.setClickFee(1.0f);
			}
			phoneClickLogService.insert(phoneClickLog);
		} while (false);
		return printJson(result, out);
	}

}
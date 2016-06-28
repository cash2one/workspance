/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-22 by liulei.
 */
package com.ast.ast1949.esite.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.EsiteNewsDo;
import com.ast.ast1949.domain.credit.CreditCustomerVoteDo;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsSeriesDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmSvrService;
import com.ast.ast1949.service.company.EsiteNewsService;
import com.ast.ast1949.service.company.EsiteService;
import com.ast.ast1949.service.company.SeoTemplatesService;
import com.ast.ast1949.service.credit.CreditCustomerVoteService;
import com.ast.ast1949.service.credit.CreditFileService;
import com.ast.ast1949.service.credit.CreditIntegralDetailsService;
import com.ast.ast1949.service.credit.CreditReferenceService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsSeriesService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.PageCacheUtil;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

/**
 * @author liulei
 * 
 */
@Controller
public class MyEsiteController extends BaseController {

	@Autowired
	private EsiteService esiteService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private EsiteNewsService esiteNewsService;
	@Autowired
	private CreditFileService creditFileService;
	@Autowired
	private CreditCustomerVoteService creditCustomerVoteService;
	@Autowired
	private CreditIntegralDetailsService creditIntegralDetailsService;
	@Autowired
	private CreditReferenceService creditReferenceService;
	@Autowired
	private CompanyAccountService companyAccountService;
	@Autowired
	private CompanyService companyService;
	@Resource
	private ProductsSeriesService productsSeriesService;
	@Resource
	private PriceService priceService;
	@Resource
	private CrmCompanySvrService   crmCompanySvrService;
	@Resource
	private SeoTemplatesService seoTemplatesService;
	
	final static Logger LOG=Logger.getLogger(MyEsiteController.class);
	final static String PRE_DOMAIN =".zz91.com"; 
	
	private void initMyPosition(String code, String title, Map<String, Object> out) {
		Map<String, Object> myposition = new HashMap<String, Object>();
		myposition.put("code", code);
		myposition.put("title", title);
		out.put("myposition", myposition);
	}

	private Integer initCompanyId(Map<String, Object> out,String domain, Integer cid){
		if(cid!=null && cid.intValue()>0){
			String member=companyService.queryMembershipOfCompany(cid);
			if(StringUtils.isNotEmpty(member) && !"10051000".equals(member)){
				return cid;
			}
			return null;
		}
		cid=esiteService.initCompanyIdFromDomain(domain);
		
		// 验证是否为 黑名单 用户
		if(companyService.validateIsBlack(cid)){
			return null;
		}
		if(seoTemplatesService.validate(cid)){
			return cid;
		}
		
		//根据标签匹配报价
		do{
			Company company=companyService.queryCompanyById(cid);
			PageDto<PriceDO> pagePrice=new PageDto<PriceDO>();
			pagePrice.setPageSize(5);
			String tags=company.getTags();
			if(tags==null || StringUtils.isEmpty(tags)){
				break;
			}
			if(tags.contains(",")){
				String[] str=tags.split(",");
				tags=str[0];
			}
			out.put("tags", tags);
		//	out.put("pagePrice", priceService.queryPricePaginationListByTitle(tags, pagePrice));
			out.put("pagePrice", priceService.pagePriceBySearchEngine(tags,null,pagePrice));
		}while(false);
		return cid;
	}

	private ModelAndView badrequest(){
		return new ModelAndView("redirect:"+AddressTool.getAddress("front"));
	}
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response,Map<String, Object> out,
			Integer cid/*,PageDto<CompanyAccountContact> page*/) throws UnsupportedEncodingException {

		cid = initCompanyId(out,request.getServerName(), cid);
		
		if (cid == null) {
			return badrequest();
		}
		
		// 新版seo判断
		if(seoTemplatesService.validate(cid)){
			set301Status(response);
			response.setHeader("Location", "http://"+companyService.queryDomainOfCompany(cid).getDomainZz91()+".zz91.net"); 
			response.setHeader("Connection", "close"); 
			return null; 
		}

//		out.put("resourceUrl", AddressTool.getAddress("resource"));

		esiteService.initBaseConfig(cid, null, out);
		// 一些特殊的配置
		initMyPosition("index", "首页", out);
		
		esiteService.initServerAddress(request.getServerName(), request
				.getServerPort(), request.getContextPath(), out);
		// 获取company对象
		Company company = (Company) out.get("company");
		if(company==null){
			return badrequest();
		}
		// 获取公司帐号对象 account
		CompanyAccount account = (CompanyAccount) out.get("account");
		if(account==null){
			return badrequest();
		}
		// 主营业务
		String business=company.getBusiness(); 
		if (business.length()>60){
			business=business.substring(0, 60)+"...";
		}
		// 搜索该公司的 最新的供求
		ProductsDO products=productsService.queryProductsByCid(cid);
		String key= company.getName();
		String title="";
		String type = "";
		if (products!=null){
			if (products.getProductsTypeCode().equals("10331000")){
				type="供应";
			}
			if (products.getProductsTypeCode().equals("10331001")){
				type="求购";
			}
			if (products.getProductsTypeCode().equals("10331002")){
				type="合作";
			}
			key = products.getTitle();
		}
		String saleDetails=company.getSaleDetails();
		String buyDetails=company.getBuyDetails();
		String titleString="";
		// Title 读取后台 主营方向(供应/求购) 两个字段信息
		if(StringUtils.isNotEmpty(saleDetails)){
			titleString=saleDetails;
		}else if(StringUtils.isEmpty(saleDetails)&& StringUtils.isNotEmpty(buyDetails)){
			titleString=buyDetails;
		}else{
			titleString=company.getName()+","+account.getContact();
		}
		// 组装titie
		title = titleString;
		// 组装keywords
		String keywords=key;
		if(StringUtils.isNotEmpty(type)){
			keywords = type+keywords;
		}
		
		// 获取公司开通服务的判断，属于2013-11-1之后还是之前
		if(getNewFlag(cid, out)){
			keywords = company.getTags();
		}
		
		// 装载页面tkd元素
		SeoUtil.getInstance().buildSeo("index",new String[]{title}, new String[]{keywords}, new String[]{company.getName(),business},out);
		//cdn time set
		setCDNTime(response);
		return new ModelAndView("index");
	}

	@RequestMapping
	public ModelAndView gsjs(HttpServletRequest request,HttpServletResponse response, Map<String, Object> out,
			Integer cid) {
		cid = initCompanyId(out,request.getServerName(), cid);
		
		if (cid == null) {
			return badrequest();
		}
		
		// 新版seo判断
		if(seoTemplatesService.validate(cid)){
			out = null ;
			return new ModelAndView("redirect:http://"+companyService.queryDomainOfCompany(cid).getDomainZz91()+".zz91.net");
		}
		
		
		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);

		initMyPosition("gsjs", "公司介绍", out);
		esiteService.initServerAddress(request.getServerName(), request
				.getServerPort(), request.getContextPath(), out);

		// 荣誉证书
		out.put("fileList", creditFileService.queryFileByCompany(cid));
		out.put("categoryMap", CategoryFacade.getInstance()
				.getChild(FILE_CATEGORY));
		
		//SEO
		Company company = (Company)out.get("company");
		CompanyAccount account=(CompanyAccount) out.get("account");
		String introduction=(String) out.get("introduction");
		
		// 普会访问 判断 访问页面的公司 是否高会(再生铜、简版再生通)已过期
		//如果为true表示还没有过期，有当前业务
//		Boolean isView = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.ZST_CODE);
//		Boolean bool = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.ESITE_CODE);
		
		String[] keywords={company.getName(),"公司介绍"};
		String[] description={introduction};
		if(company.getMembershipCode()!=CrmSvrService.PT_CODE){
			String[] titleParam={company.getName(),"公司介绍",account.getContact(),"，"+account.getMobile()}; 
			SeoUtil.getInstance().buildSeo("gsjs", titleParam, keywords, description, out);
		}else{
			String[] titleParam={company.getName(),"公司介绍",account.getContact(),""};
			SeoUtil.getInstance().buildSeo("gsjs",titleParam, keywords, description,out);
		}
		//cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView zxgq(HttpServletRequest request, HttpServletResponse response, Integer cid,
			Map<String, Object> out, PageDto page, String kw, Integer sid) {
		out.put("pageCode","all_offer_index");
		cid = initCompanyId(out,request.getServerName(), cid);
		
		if (cid == null) {
			return badrequest();
		}
		
		// 新版seo判断
				if(seoTemplatesService.validate(cid)){
					out = null ;
					return new ModelAndView("redirect:http://"+companyService.queryDomainOfCompany(cid).getDomainZz91()+".zz91.net");
				}
		
		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);

		initMyPosition("zxgq", "最新供求", out);
		esiteService.initServerAddress(request.getServerName(), request
				.getServerPort(), request.getContextPath(), out);

		try {
			kw = StringUtils.decryptUrlParameter(kw);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		page.setPageSize(8);
		//page = productsService.pageProductsWithPicByCompanyEsite(cid, kw, sid,page);
		if(crmCompanySvrService.validatePeriod(cid, CrmSvrService.CRM_SP)){
		  page=productsService.pageProductsWithPicByCompanyForSp(cid, kw,sid,page);  	
	    }else {
		  page = productsService.pageProductsWithPicByCompanyEsiteWithDetails(cid, kw, sid, page);
	    }
		
		out.put("page", page);

		out.put("sid", sid);
		// sid 存在的情况下 搜索出点击的类别中文名
		ProductsSeriesDO productsSeriesDO=new ProductsSeriesDO();
		if(sid !=null){
			productsSeriesDO=productsSeriesService.queryProductsSeriesById(sid);
			out.put("productCategory", productsSeriesDO);
		}
		out.put("kw", kw);
		
		PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_HOUR);
		
		//SEO
		Integer size;
		size=page.getStartIndex()/page.getPageSize()+1;
		Company company = (Company)out.get("company");
		CompanyAccount account=(CompanyAccount) out.get("account");
		String introduction=(String) out.get("introduction");

		// 普会访问 判断 访问页面的公司 是否高会(再生铜、简版再生通)已过期
		//如果为true表示还没有过期，有当前业务
//		Boolean isView = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.ZST_CODE);
//		Boolean bool = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.ESITE_CODE);
		
		String[] keywords={company.getName(),"最新供求"};
		String[] description={introduction};
		do{
			if(sid!=null){
				SeoUtil.getInstance().buildSeo("category",
						new String[] { productsSeriesDO.getName() ,"第"+size+"页"},new String[]{productsSeriesDO.getName()},description, out);
				break;
			}
			if(company.getMembershipCode()!=CrmSvrService.PT_CODE){
				String[] titleParam={company.getName(),"最新供求","第"+size+"页",account.getContact(),"，"+account.getMobile()};
				SeoUtil.getInstance().buildSeo("zxgq", titleParam, keywords, description, out);
			}else {
				String[] titleParam={company.getName(),"最新供求","第"+size+"页",account.getContact(),""};
				SeoUtil.getInstance().buildSeo("zxgq", titleParam, keywords, description, out);
			}
		}while(false);
		//cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView gsdt(HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> out, PageDto page, Integer cid) {
		out.put("pageCode", "newslist_index");
//		long start=0;
//		long end=0;
//		start=System.currentTimeMillis();
//		end=System.currentTimeMillis();
//		System.out.println("cost1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+(end-start));
		
		
//		start=System.currentTimeMillis();
		cid = initCompanyId(out,request.getServerName(), cid);
		
//		end=System.currentTimeMillis();
//		System.out.println("cost2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+(end-start));
		
		
		if (cid == null) {
			return badrequest();
		}
		
		// 新版seo判断
				if(seoTemplatesService.validate(cid)){
					out = null ;
					return new ModelAndView("redirect:http://"+companyService.queryDomainOfCompany(cid).getDomainZz91()+".zz91.net");
				}
		
//		start=System.currentTimeMillis();
		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);
//		end=System.currentTimeMillis();
//		System.out.println("cost3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+(end-start));

		initMyPosition("gsdt", "公司动态", out);
		esiteService.initServerAddress(request.getServerName(), request
				.getServerPort(), request.getContextPath(), out);


		page.setSort("post_time");
		page.setDir("desc");
		page.setPageSize(15);

//		start=System.currentTimeMillis();
		page = esiteNewsService.pageNewsByCompany(cid, page);
//		end=System.currentTimeMillis();
//		System.out.println("cost4>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+(end-start));
		
		out.put("page", page);
		page.getRecords();
		//SEO
		Company company = (Company)out.get("company");
		CompanyAccount account=(CompanyAccount) out.get("account");
		String introduction=(String) out.get("introduction");
		// 普会访问 判断 访问页面的公司 是否高会(再生铜、简版再生通)已过期
		//如果为true表示还没有过期，有当前业务
//		Boolean isView = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.ZST_CODE);
//		Boolean bool = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.ESITE_CODE);
		String[] keywords={company.getName(),"公司动态"};
		String[] description={introduction};
		Integer size;
		size=page.getStartIndex()/page.getPageSize()+1;
		if(company.getMembershipCode()!=CrmSvrService.PT_CODE){
			String[] titleParam={company.getName(),"公司动态","第"+size+"页",account.getContact(),"，"+account.getMobile()};
			SeoUtil.getInstance().buildSeo("gsdt", titleParam, keywords, description, out);
			
		}else{
			String[] titleParam={company.getName(),"公司动态","第"+size+"页",account.getContact(),""};
			SeoUtil.getInstance().buildSeo("gsdt", titleParam, keywords, description, out);
		}
		//cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView gsdtdetail(HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> out, Integer id, Integer cid) {
		
		cid = initCompanyId(out,request.getServerName(), cid);
		
		if (cid == null) {
			return badrequest();
		}
		
		// 新版seo判断
		if(seoTemplatesService.validate(cid)){
			out = null ;
			return new ModelAndView("redirect:http://"+companyService.queryDomainOfCompany(cid).getDomainZz91()+".zz91.net");
		}
		
		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);

		initMyPosition("gsdt", "公司动态", out);
		esiteService.initServerAddress(request.getServerName(), request
				.getServerPort(), request.getContextPath(), out);
		
		// 详细的动态信息
		EsiteNewsDo news = esiteNewsService.queryOneNewsById(id);
		// 查询上一篇文章
		EsiteNewsDo lastNews = esiteNewsService.queryLastNewsById(id,cid);
		out.put("lastNews", lastNews);
		// 查询下一篇文章
		EsiteNewsDo nextNews = esiteNewsService.queryNextNewsById(id,cid);
		out.put("nextNews", nextNews);
		
		news.setContent(news.getContent().replace("\n", "<br/>"));
		news.setContent(news.getContent().replace("<br/>", ""));
//		news.setContent(Jsoup.clean(news.getContent(), Whitelist.basic()));
		out.put("news", news);	
		String str =news.getContent().trim();
		str = StringUtils.removeHTML(str);
		if(str.length()>200){
			str = str.substring(0, 200);
		}
		String[] description={str};
		String[] titleParam={news.getTitle()};
		SeoUtil.getInstance().buildSeo("gsdtdetail", titleParam, null, description, out);
		//cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}

	final static String FILE_CATEGORY = "1040";

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView cxda(HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> out, PageDto page, Integer cid) {
		cid = initCompanyId(out,request.getServerName(), cid);
		
		if (cid == null) {
			return badrequest();
		}
		
		// 新版seo判断
		if(seoTemplatesService.validate(cid)){
			out = null ;
			return new ModelAndView("redirect:http://"+companyService.queryDomainOfCompany(cid).getDomainZz91()+".zz91.net");
		}
		
		
		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);

		initMyPosition("cxda", "诚信档案", out);
		esiteService.initServerAddress(request.getServerName(), request
				.getServerPort(), request.getContextPath(), out);

		// TODO 查找诚信档案有关的信息

		// 诚信积分
		out.put("integral_company", creditIntegralDetailsService
				.countIntegralByOperationKey(cid, "company")); // 企业身份认证
		out.put("integral_service_zst", creditIntegralDetailsService
				.countIntegralByOperationKey(cid, "service_zst")); // 再生通服务年限
		out.put("integral_customer_vote", creditIntegralDetailsService
				.countIntegralByOperationKey(cid, "customer_vote"));
		out.put("integral_credit_file", creditIntegralDetailsService
				.countIntegralByOperationKey(cid, "credit_file"));

		// 荣誉证书
		out.put("fileList", creditFileService.queryFileByCompany(cid));
		out.put("categoryMap", CategoryFacade.getInstance()
				.getChild(FILE_CATEGORY));

		// 资信参考人
		out.put("referenceList", creditReferenceService
				.queryReferenceByCompany(cid));

		// 客户评价
		out.put("vote_0", creditCustomerVoteService.countVoteNumByToCompany(
				cid, "0", "1", null)); // 统计收到的有效好评数
		out.put("vote_1", creditCustomerVoteService.countVoteNumByToCompany(
				cid, "1", "1", true)); // 统计收到的有效中评数
		out.put("vote_2", creditCustomerVoteService.countVoteNumByToCompany(
				cid, "2", "1", true)); // 统计收到的有效差评数

		CreditCustomerVoteDo vote = new CreditCustomerVoteDo();
		vote.setToCompanyId(cid);
		vote.setCheckStatus("1");
		
		page.setPageSize(10);
		
		out.put("page", creditCustomerVoteService.pageVoteByToCompany(vote, page));
		
		//SEO
		Company company = (Company)out.get("company");
		CompanyAccount account=(CompanyAccount) out.get("account");
		String introduction=(String) out.get("introduction");
		// 普会访问 判断 访问页面的公司 是否高会(再生铜、简版再生通)已过期
		//如果为true表示还没有过期，有当前业务
//		Boolean isView = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.ZST_CODE);
//		Boolean bool = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.ESITE_CODE);
		String[] keywords={company.getName(),"诚信档案"};
		String[] description={introduction};
		if(company.getMembershipCode()!=CrmSvrService.PT_CODE){
			String[] titleParam={company.getName(),"诚信档案",account.getContact(),"，"+account.getMobile()};
			SeoUtil.getInstance().buildSeo("cxda", titleParam, keywords, description, out);
			
		}else{
			String[] titleParam={company.getName(),"诚信档案",account.getContact(),""};
			SeoUtil.getInstance().buildSeo("cxda", titleParam, keywords, description, out);
		} 
		//cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView cxdavote(HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> out, Integer cid) {
		cid = initCompanyId(out,request.getServerName(), cid);
		
		if (cid == null) {
			return badrequest();
		}
		
		// 新版seo判断
		if(seoTemplatesService.validate(cid)){
			out = null ;
			return new ModelAndView("redirect:http://"+companyService.queryDomainOfCompany(cid).getDomainZz91()+".zz91.net");
		}
		
		
		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);

		initMyPosition("cxda", "发表评价", out);
		esiteService.initServerAddress(request.getServerName(), request
				.getServerPort(), request.getContextPath(), out);
		
		//cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}

	/**
	 * 从zz91主站进入的方法
	 */
	@RequestMapping
	public ModelAndView initVote(HttpServletRequest request,
			Map<String, Object> out, Integer cid) {
		out.put("company", companyService.querySimpleCompanyById(cid));
		return null;
	}

	/**
	 * 从zz91主站进入的方法
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView voteToCompany(HttpServletRequest request,
			Map<String, Object> out, CreditCustomerVoteDo vote, Integer cid)
			throws IOException {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		if (!ssoUser.getCompanyId().equals(vote.getToCompanyId())) {
			vote.setFromAccount(ssoUser.getAccount());
			vote.setFromCompanyId(ssoUser.getCompanyId());
			vote.setCheckStatus("0");
			Integer i = creditCustomerVoteService.voteToCompany(vote);
			if (i != null && i > 0) {
				result.setSuccess(true);
			}
		} else {
			result.setData("canNotVoteSelf");
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView zxly(HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> out,String a, Integer id) {
		
		id = initCompanyId(out,request.getServerName(), id);
		
		if( id==null){
			id=companyAccountService.queryCompanyIdByAccount(a);
		}
		
		// 新版seo判断
		if(seoTemplatesService.validate(id)){
			out = null ;
			return new ModelAndView("redirect:http://"+companyService.queryDomainOfCompany(id).getDomainZz91()+".zz91.net");
		}
		
		Company company = companyService.queryCompanyById(id);
		if (company == null) {
			out.put(AstConst.ERROR_TEXT, "对不起，没有您查看的公司信息，请检查地址是否正确！");
		}
		if (company.getIsBlock() == null || "1".equals(company.getIsBlock())) {
			out.put(AstConst.ERROR_TEXT, "对不起，该公司已不做生意或账号被冻结！");
		}
		
		out.put("companyinfo", company);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
		esiteService.initBaseConfig(id, null, out);

		initMyPosition("zxly", "在线留言", out);
		esiteService.initServerAddress(request.getServerName(), request
				.getServerPort(), request.getContextPath(), out);
		
		//SEO
		CompanyAccount account=(CompanyAccount) out.get("account");
		String introduction=(String) out.get("introduction");
		// 普会访问 判断 访问页面的公司 是否高会(再生铜、简版再生通)已过期
		//如果为true表示还没有过期，有当前业务
//		Boolean isView = crmCompanySvrService.validatePeriod(id, CrmCompanySvrService.ZST_CODE);
//		Boolean bool = crmCompanySvrService.validatePeriod(id, CrmCompanySvrService.ESITE_CODE);
		String[] keywords={company.getName(),"在线留言"};
		String[] description={introduction};
		if(company.getMembershipCode()!=CrmSvrService.PT_CODE){
			String[] titleParam={company.getName(),"在线留言",account.getContact(),"，"+account.getMobile()};
			SeoUtil.getInstance().buildSeo("zxly", titleParam, keywords, description, out);
			
		}else{
			String[] titleParam={company.getName(),"在线留言",account.getContact(),""};
			SeoUtil.getInstance().buildSeo("zxly", titleParam, keywords, description, out);
		}
		//cdn time set
		setCDNTime(response);
		return new ModelAndView();
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

	@RequestMapping
	public ModelAndView lxfs(HttpServletRequest request,HttpServletResponse response, Map<String, Object> out, Integer cid/*,PageDto<CompanyAccountContact> page*/) {
		cid = initCompanyId(out,request.getServerName(), cid);
		
		if (cid == null) {
			return badrequest();
		}
		
		// 新版seo判断
		if(seoTemplatesService.validate(cid)){
			out = null ;
			return new ModelAndView("redirect:http://"+companyService.queryDomainOfCompany(cid).getDomainZz91()+".zz91.net");
		}
		
		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);

		initMyPosition("lxfs", "联系方式", out);
		esiteService.initServerAddress(request.getServerName(), request
				.getServerPort(), request.getContextPath(), out);
//		CompanyAccount account = companyAccountService.queryAdminAccountByCompanyId(cid);
//		page = companyAccountContactService.pageContactByCompany(account.getAccount(), page,"0");
//		out.put("page", page);
		// TODO 以图片形式展示联系方式
//		out.put("contactInfo",companyAccountService.queryAdminAccountByCompanyId(cid));
		
		//SEO
		Company company = (Company)out.get("company");
		CompanyAccount account=(CompanyAccount) out.get("account");
		String introduction=(String) out.get("introduction");
		
		// 普会访问 判断 访问页面的公司 是否高会(再生铜、简版再生通)已过期
		//如果为true表示还没有过期，有当前业务
//		Boolean isView = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.ZST_CODE);
//		Boolean bool = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.ESITE_CODE);
		String[] keywords={company.getName(),"联系方式"};
		String[] description={introduction};
		if(company.getMembershipCode()!=CrmSvrService.PT_CODE){
			String[] titleParam={company.getName(),"联系方式",account.getContact(),"，"+account.getMobile()};
			SeoUtil.getInstance().buildSeo("lxfs", titleParam, keywords, description, out);
		}else{
			String[] titleParam={company.getName(),"联系方式",account.getContact(),""};
			SeoUtil.getInstance().buildSeo("lxfs", titleParam, keywords, description, out);
		}
		//cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView errorPage404(HttpServletRequest request, Map<String, Object> out){
		LOG.warn("[no page found!]: referer referer:"+request.getHeader("referer")+" request URI:"+request.getRequestURI());
		return null;
	}
	
	private Boolean getNewFlag(Integer cid,Map<String, Object>out){
		List<CrmCompanySvr> list = crmCompanySvrService.querySvrHistory(cid, CrmCompanySvrService.ZST_CODE);
		Date date = new Date();
		try {
			date = DateUtil.getDate("2013-11-1", "yyyy-MM-dd");
		}catch(ParseException e) {
		}
		do {
			if(list!=null&&list.size()>0){
				CrmCompanySvr crmCompanySvr = list.get(0);
				long startTime = crmCompanySvr.getGmtStart().getTime();
				if(startTime-date.getTime()>0){
					return true;
				}
			}
			list = crmCompanySvrService.querySvrHistory(cid, CrmCompanySvrService.BAIDU_CODE);
			if(list!=null&&list.size()>0){
				CrmCompanySvr crmCompanySvr = list.get(0);
				long startTime = crmCompanySvr.getGmtStart().getTime();
				if(startTime-date.getTime()>0){
					return true;
				}
			}
			
		} while (false);
		return false;
	}
	
	private void setCDNTime(HttpServletResponse response){
		// 设置cdn过期时间
		PageCacheUtil.setCDNCache(response, 60*30);
	}
	
	private void set301Status(HttpServletResponse response){
		PageCacheUtil.setStatus(response, 301);
	}
}
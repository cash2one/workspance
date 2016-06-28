/*
 * 文件名称：RootController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.esite.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.crm.CrmCompSvr;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.TradeProperty;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;
import com.zz91.ep.service.comp.CompNewsService;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.crm.CrmCompSvrService;
import com.zz91.ep.service.trade.PhotoService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.service.trade.TradeGroupService;
import com.zz91.ep.service.trade.TradePropertyService;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

/**
 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：首页默认控制类。 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 * 　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 * 
 * 
 * 流程： 1. 获取CID，所有操作以CID为基础\ domain,domain_2,cid ==> cid 2. 初始化所有页面需要的信息
 * esiteAddress,esiteCid profile, account navigate
 * 
 * 3. 实现页面功能
 */
@Controller
public class RootController extends BaseController {

	@Resource
	private TradeSupplyService tradeSupplyService;

	@Resource
	private CompProfileService compProfileService;

	@Resource
	private TradeGroupService tradeGroupService;

	@Resource
	private CompNewsService compNewsService;

	@Resource
	private TradePropertyService tradePropertyService;

	@Resource
	private CrmCompSvrService crmCompSvrService;
	@Resource
	private TradeCategoryService tradeCategoryService;
	@Resource
	private PhotoService photoService;
	/**
	 * 函数名称：index 功能描述：访问首页时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 *            　　　　　2012/08/03　　　黄怀清　　　　　　　1.0.0　　　　　增加二级域名支持
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request, Integer cid, PageDto<TradeSupply> page) {

		do {


			cid=compProfileService.initCid(request.getServerName(), cid,
		     out);


			if (cid == null) {
				break;
			}

			if (!compProfileService
					.initEsite(cid, request.getServerName(), out)) {
				break;
			}

			out.put("cid", cid);

			CompProfile profile = (CompProfile) out.get("profile");

			if (CompProfileService.PAY_MEMERCODE
					.equals(profile.getMemberCode())) {
				// 编号2 为中环通服务id号d
				CrmCompSvr svr = crmCompSvrService.queryLastSvr(cid,
						CrmCompSvrService.ZHT_SVR_ID);

				if (svr != null) {
					String bookCode = DateUtil.toString(svr.getGmtStart(),
							"yyMMdd")
							+ svr.getId().toString();
					out.put("openStr", DateUtil.toString(svr.getGmtStart(),
							"yyyy-MM-dd"));
					out.put("booKCode", bookCode);
				}

			}

			// 获取普通会员的产品列表信息
			if (CompProfileService.DEFAULT_MEMERCODE.equals(profile
					.getMemberCode())) {
				page.setLimit(10);
				page.setSort("gmt_refresh");
				page = tradeSupplyService.pageSupplyByCompany(null, null, cid,
						page);
				out.put("page", page);
			}

			// SEO
			String[] titleParam = null;
			String[] keywordsParam = null;
			String[] descriptionParam = null;

			String cname = profile.getName();
			String industryName = CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_TRADE, profile.getIndustryCode());
			industryName = industryName == null ? "" : industryName;
			String mainSupply = profile.getMainProductSupply();

			String sp = "";
			if (StringUtils.isNotEmpty(mainSupply)) {
				sp = mainSupply.length() > 20 ? mainSupply.substring(0, 20)
						: mainSupply;
			}
			keywordsParam = new String[] { cname + "", industryName,
					profile.getMainProductBuy() + mainSupply + "" };
			String details = profile.getDetailsQuery();
			if (StringUtils.isNotEmpty(details) && details.length() > 100) {
				details = details.substring(0, 100);
			}

			descriptionParam = new String[] { details + "" };
			if (CompProfileService.PAY_MEMERCODE
					.equals(profile.getMemberCode())) {
			    if (StringUtils.isEmpty(sp)) {
			        titleParam = new String[] {  cname };
			    } else {
			        titleParam = new String[] { sp + "_" + cname };
			    }
				
			} else if (crmCompSvrService.querySeoSvrCount(cid, 5) > 0) {
				titleParam = new String[] { sp + "_" + cname };
				keywordsParam = new String[] { cname, "_" + industryName + "_",
						mainSupply };
				descriptionParam = new String[] { profile.getDetailsQuery() };

			} else if (CompProfileService.DEFAULT_MEMERCODE.equals(profile
					.getMemberCode())) {
				titleParam = new String[] { cname + "_" + industryName
						+ "_中国环保网" };
			}

			SeoUtil.getInstance().buildSeo("index", titleParam, keywordsParam,
					descriptionParam, out);

			return null;
		} while (false);

		return brokenRequest();
	}

	/**
	 * 函数名称：companyInfo 功能描述：访问公司介绍页时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 *            　　　　2012/08/03　　　黄怀清　　　　　　　1.0.0　　　　　增加二级域名支持
	 */
	@RequestMapping
	public ModelAndView companyInfo(Map<String, Object> out,
			HttpServletRequest request, Integer cid) {
		do {

			cid = compProfileService.initCid(request.getServerName(), cid, out);

			if (cid == null) {
				break;
			}

			if (!compProfileService
					.initEsite(cid, request.getServerName(), out)) {
				break;
			}

			out.put("cid", cid);

			CompProfile profile = (CompProfile) out.get("profile");
			CompAccount account = (CompAccount) out.get("account");

			// SEO
			String[] titleParam = { profile.getName() + "公司介绍" };
			if (CompProfileService.PAY_MEMERCODE
					.equals(profile.getMemberCode())) {
				titleParam[0] = profile.getName() + "公司介绍_"
						+ account.getMobile();
			}
			String[] keywordsParam = { profile.getName() };
			String[] descriptionParam = {
					profile.getName(),
					profile.getMainProductBuy() + ","
							+ profile.getMainProductSupply() };
			SeoUtil.getInstance().buildSeo("companyInfo", titleParam,
					keywordsParam, descriptionParam, out);
			return null;

		} while (false);
		return brokenRequest();
	}

	/**
	 * 函数名称：companyContact 功能描述：访问公司联系方式页时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 *            　　　　2012/08/03　　　黄怀清　　　　　　　1.0.0　　　　　增加二级域名支持
	 */
	@RequestMapping
	public ModelAndView companyContact(Map<String, Object> out,
			HttpServletRequest request, Integer cid) {
		do {

			cid = compProfileService.initCid(request.getServerName(), cid, out);

			if (cid == null) {
				break;
			}

			if (!compProfileService
					.initEsite(cid, request.getServerName(), out)) {
				break;
			}

			out.put("cid", cid);

			CompProfile profile = (CompProfile) out.get("profile");
			CompAccount account = (CompAccount) out.get("account");

			// SEO
			String[] titleParam = { profile.getName() + "联系方式_"
					+ account.getMobile() };
			String[] keywordsParam = {
					CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE,
							profile.getIndustryCode()), profile.getName() };
			String[] descriptionParam = { profile.getName(),
					account.getContact(), profile.getAddress(),
					profile.getMainProductSupply() };
			if (crmCompSvrService.querySeoSvrCount(cid, 5) > 0) {
				titleParam = new String[] { profile.getName() + "联系我们" };
			}

			SeoUtil.getInstance().buildSeo("companyContact", titleParam,
					keywordsParam, descriptionParam, out);

			return null;
		} while (false);
		return brokenRequest();
	}

	/**
	 * 函数名称：products 功能描述：访问产品页时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 *            　　　　2012/08/03　　　黄怀清　　　　　　　1.0.0　　　　　增加二级域名支持
	 * @throws Exception
	 */

    @RequestMapping
	public ModelAndView products(Map<String, Object> out,
			HttpServletRequest request, Integer cid, Integer group,
			String keywords, PageDto<TradeSupply> page) throws Exception {
		do {

			cid = compProfileService.initCid(request.getServerName(), cid, out);

			if (cid == null) {
				break;
			}

			if (!compProfileService
					.initEsite(cid, request.getServerName(), out)) {
				break;
			}

			out.put("cid", cid);

			CompProfile profile = (CompProfile) out.get("profile");

			out.put("group", group);
			out.put("keywords", keywords);

			page.setLimit(10);
			page.setSort("gmt_refresh");
			page = tradeSupplyService.pageSupplyByCompany(group, keywords, cid,page);
			out.put("page", page);

			// SEO
			if (group != null) {
				String groupName = tradeGroupService.queryNameById(group);
				String[] titleParam = { groupName + "_" + groupName + "厂家",
						profile.getName() };
				if (crmCompSvrService.querySeoSvrCount(cid, 5) > 0) {
					titleParam = new String[] { profile.getName() + "产品展示",
							"中国环保网" };
				}
				String[] keywordsParam = { groupName };
				String[] descriptionParam = { profile.getName(), groupName };
				SeoUtil.getInstance().buildSeo("productsgroup", titleParam,
						keywordsParam, descriptionParam, out);

			} else {
				StringBuilder productsTitles = new StringBuilder();
				StringBuilder productsKeywords = new StringBuilder();
				int maxsize = 2;
				if (maxsize > page.getRecords().size()) {
					maxsize = page.getRecords().size();
				}
				for (int i = 0; i < maxsize; i++) {
					productsTitles.append(page.getRecords().get(i).getTitle()
							+ ",");
					productsKeywords.append(page.getRecords().get(i).getTags()
							+ ",");
				}
				if (StringUtils.isNotEmpty(profile.getMainProductSupply())) {
					if (profile.getMainProductSupply().length() > 25) {
						profile.setMainProductSupply(profile
								.getMainProductSupply().substring(0, 25));
					}
				}
				String[] titleParam = new String[]{""};
				if (StringUtils.isNotEmpty(profile.getMainProductSupply())) {
				    titleParam= new String[]{ profile.getMainProductSupply() };
				}
				
				if (crmCompSvrService.querySeoSvrCount(cid, 5) > 0) {
					titleParam = new String[] { profile.getName() };
				}
				
				String[] keywordsParam = { productsKeywords.toString() };
				String[] descriptionParam = { profile.getName(),
						productsTitles.toString() };

				SeoUtil.getInstance().buildSeo("products", titleParam,
						keywordsParam, descriptionParam, out);

			}
			return null;
		} while (false);
		return brokenRequest();
	}

	/**
	 * 函数名称：message 功能描述：访问留言页时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 *            　　　　2012/08/03　　　黄怀清　　　　　　　1.0.0　　　　　增加二级域名支持
	 */
	@RequestMapping
	public ModelAndView message(Map<String, Object> out,
			HttpServletRequest request, Integer cid,Integer flag) {
		do {
			cid = compProfileService.initCid(request.getServerName(), cid, out);

			if (cid == null) {
				break;
			}

			if (!compProfileService
					.initEsite(cid, request.getServerName(), out)) {
				break;
			}

			out.put("cid", cid);

			CompProfile profile = (CompProfile) out.get("profile");
			CompAccount account = (CompAccount) out.get("account");

			// SEO
			String industryName = CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_TRADE, profile.getIndustryCode());
			out.put("industryName", industryName);

			String[] titleParam = { profile.getName() + "留言区_" + industryName };
			String[] keywordsParam = { profile.getName(), industryName };
			String[] descriptionParam = { profile.getName(),
					profile.getMainProductSupply() };

			if (CompProfileService.PAY_MEMERCODE
					.equals(profile.getMemberCode())) {
				titleParam = new String[] { profile.getName() + "留言区_"
						+ account.getMobile() + "_" + industryName };
			}
			if (crmCompSvrService.querySeoSvrCount(cid, 5) > 0) {
				titleParam = new String[] { profile.getName() + "留言区" };
			}
			SeoUtil.getInstance().buildSeo("message", titleParam,
					keywordsParam, descriptionParam, out);
            if(flag!=null && flag==1){
            	out.put("show", 1);
            }else {	
            	out.put("show", 0);
            }
			return null;
		} while (false);
		return brokenRequest();
	}

	/**
	 * 函数名称：companyNews 功能描述：访问公司动态页时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 *            　　　　2012/08/03　　　黄怀清　　　　　　　1.0.0　　　　　增加二级域名支持
	 */
	@RequestMapping
	public ModelAndView companyNews(Map<String, Object> out,
			HttpServletRequest request, Integer cid, String keywords,
			PageDto<CompNews> page) {
		do {

			cid = compProfileService.initCid(request.getServerName(), cid, out);

			if (cid == null) {
				break;
			}

			if (!compProfileService
					.initEsite(cid, request.getServerName(), out)) {
				break;
			}

			out.put("cid", cid);

			CompProfile profile = (CompProfile) out.get("profile");
			CompAccount account = (CompAccount) out.get("account");

			// 获取文章信息
			page.setSort("gmt_publish");
			page.setDir("desc");
			page.setLimit(10);
			page = compNewsService.pageCompNewsByCid(cid,
					CompNewsService.TYPE_COMPANY_NEWS, keywords, (short) 1,
					(short) 1, (short) 1, page);
			out.put("page", page);
			// SEO
			String[] titleParam = { profile.getName() + "公司动态" };

			if (CompProfileService.PAY_MEMERCODE
					.equals(profile.getMemberCode())) {
				titleParam[0] = profile.getName() + "公司动态_"
						+ account.getMobile();
			}
			String[] keywordsParam = { profile.getName() };
			String[] descriptionParam = { profile.getName() };

			SeoUtil.getInstance().buildSeo("companyNews", titleParam,
					keywordsParam, descriptionParam, out);

			return null;
		} while (false);
		return brokenRequest();
	}

	/**
	 * 函数名称：technicalArticles 功能描述：访问技术文章页时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 *            　　　　2012/08/03　　　黄怀清　　　　　　　1.0.0　　　　　增加二级域名支持
	 */
	@RequestMapping
	public ModelAndView technicalArticles(Map<String, Object> out,
			HttpServletRequest request, Integer cid, String keywords,
			PageDto<CompNews> page) {
		do {
			cid = compProfileService.initCid(request.getServerName(), cid, out);

			if (cid == null) {
				break;
			}

			if (!compProfileService
					.initEsite(cid, request.getServerName(), out)) {
				break;
			}

			out.put("cid", cid);

			CompProfile profile = (CompProfile) out.get("profile");
			CompAccount account = (CompAccount) out.get("account");

			// 获取文章信息
			page.setSort("gmt_publish");
			page.setDir("desc");
			page.setLimit(10);
			page = compNewsService.pageCompNewsByCid(cid,
					CompNewsService.TYPE_TECHNICAL, keywords, (short) 1,
					(short) 1, (short) 1, page);

			out.put("page", page);

			out
					.put("industryName", CodeCachedUtil.getValue(
							CodeCachedUtil.CACHE_TYPE_TRADE, profile
									.getIndustryCode()));

			// SEO
			String[] titleParam = { profile.getName() + "技术文章" };

			if (CompProfileService.PAY_MEMERCODE
					.equals(profile.getMemberCode())) {
				titleParam[0] = profile.getName() + "技术文章_"
						+ account.getMobile();
			}

			String[] keywordsParam = { profile.getName() };
			String[] descriptionParam = { profile.getName() };

			SeoUtil.getInstance().buildSeo("technicalArticles", titleParam,
					keywordsParam, descriptionParam, out);

			return null;
		} while (false);
		// return new ModelAndView("resourceNotFound");
		return brokenRequest();
	}

	/**
	 * 函数名称：successStories 功能描述：访问成功案例页时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 *            　　　　2012/08/03　　　黄怀清　　　　　　　1.0.0　　　　　增加二级域名支持
	 */
	@RequestMapping
	public ModelAndView successStories(Map<String, Object> out,
			HttpServletRequest request, Integer cid, String keywords,
			PageDto<CompNews> page) {
		do {
			cid = compProfileService.initCid(request.getServerName(), cid, out);

			if (cid == null) {
				break;
			}

			if (!compProfileService
					.initEsite(cid, request.getServerName(), out)) {
				break;
			}

			out.put("cid", cid);

			CompProfile profile = (CompProfile) out.get("profile");
			CompAccount account = (CompAccount) out.get("account");

			// 获取文章信息
			page.setSort("gmt_publish");
			page.setDir("desc");
			page.setLimit(10);
			page = compNewsService.pageCompNewsByCid(cid,
					CompNewsService.TYPE_SUCCESS, keywords, (short) 1,
					(short) 1, (short) 1, page);
			out.put("page", page);
			// SEO
			String[] titleParam = { profile.getName() + "成功案例" };

			if (CompProfileService.PAY_MEMERCODE
					.equals(profile.getMemberCode())) {
				titleParam[0] = profile.getName() + "成功案例_"
						+ account.getMobile();
			}

			String[] keywordsParam = { profile.getName() };
			String[] descriptionParam = { profile.getName() };

			SeoUtil.getInstance().buildSeo("successStories", titleParam,
					keywordsParam, descriptionParam, out);

			return null;
		} while (false);
		// return new ModelAndView("resourceNotFound");
		return brokenRequest();
	}

	/**
	 * 函数名称：credit 功能描述：访问公司荣誉证书页时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 *            　　　　2012/08/03　　　黄怀清　　　　　　　1.0.0　　　　　增加二级域名支持
	 */
	@RequestMapping
	public ModelAndView credit(Map<String, Object> out,
			HttpServletRequest request, Integer cid) {
		do {
			cid = compProfileService.initCid(request.getServerName(), cid, out);

			if (cid == null) {
				break;
			}

			if (!compProfileService
					.initEsite(cid, request.getServerName(), out)) {
				break;
			}

			out.put("cid", cid);

			CompProfile profile = (CompProfile) out.get("profile");
			CompAccount account = (CompAccount) out.get("account");

			if (CompProfileService.PAY_MEMERCODE
					.equals(profile.getMemberCode())) {
				// 编号2 为中环通服务id号
				CrmCompSvr svr = crmCompSvrService.queryLastSvr(cid, 2);
				String bookCode = DateUtil
						.toString(svr.getGmtStart(), "yyMMdd")
						+ svr.getId().toString();
				out.put("openStr", DateUtil.toString(svr.getGmtStart(),
						"yyyy-MM-dd"));
				out.put("booKCode", bookCode);
			}

			// SEO
			String[] titleParam = { profile.getName() + "荣誉证书" };

			if (CompProfileService.PAY_MEMERCODE
					.equals(profile.getMemberCode())) {
				titleParam[0] = profile.getName() + "荣誉证书_"
						+ account.getMobile();
			}

			String[] keywordsParam = { profile.getName() };
			String[] descriptionParam = { profile.getName() };
			SeoUtil.getInstance().buildSeo("credit", titleParam, keywordsParam,
					descriptionParam, out);
			return null;
		} while (false);
		return brokenRequest();
	}

	/**
	 * 函数名称：newsDetails 功能描述：访问新闻最终页时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 *            　　　　2012/08/03　　　黄怀清　　　　　　　1.0.0　　　　　增加二级域名支持
	 */
	@RequestMapping
	public ModelAndView newsDetails(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		do {
			// 获取文章信息
			if (id == null) {
				break;
			}
			CompNews compNews = compNewsService.queryCompNewsById(id);
			
			
			if (compNews == null) {
				break;
			}
			if(compNews.getCheckStatus()!=1||compNews.getDeleteStatus()!=1||compNews.getPauseStatus()!=1){
				break;
			}
			//点击更新浏览数
            compNewsService.updateViewCountById(id);
            
			out.put("compNews", compNews);

			//查询上一篇
			CompNews upNews=compNewsService.queryPrevCompNewsById(id, compNews.getCid(),compNews.getCategoryCode());
			out.put("upNews", upNews);
			
			//查询下一篇
			CompNews downNews=compNewsService.queryNextCompNewsById(id, compNews.getCid(),compNews.getCategoryCode());
			out.put("downNews", downNews);
			
			Integer cid = compProfileService.initCid(request.getServerName(),
					compNews.getCid(), out);
			
			
			if (cid == null) {
				break;
			}

			if (!compProfileService
					.initEsite(cid, request.getServerName(), out)) {
				break;
			}

			out.put("id", id);
			out.put("cid", cid);

			CompProfile profile = (CompProfile) out.get("profile");
            
			// SEO
			String[] titleParam = { compNews.getTitle() };
			String[] keywordsParam = { compNews.getTitle() };
			String shortDetails = compNews.getDetails();
			if (shortDetails != null && shortDetails.length() > 100) {
				shortDetails = shortDetails.substring(0, 100);
			}
			String[] descriptionParam = { profile.getName(),
					Jsoup.clean(shortDetails, Whitelist.none()) };

			SeoUtil.getInstance().buildSeo("newsDetails", titleParam,
					keywordsParam, descriptionParam, out);

			return null;
		} while (false);
		return brokenRequest();
	}

	/**
	 * 函数名称：detail 功能描述：访问产品最终页时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView detail(Map<String, Object> out,
			HttpServletRequest request, Integer id,Integer flag)
			throws UnsupportedEncodingException {
		do {
			// 获取产品信息
			if (id == null) {
				break;
			}
			TradeSupplyDto tradeSupply = tradeSupplyService.queryDetailsById(id);
			if (tradeSupply == null) {
				break;
			}

			//搜索引擎
			SearchSupplyDto search=new SearchSupplyDto();
			PageDto<TradeSupplyNormDto> page = new PageDto<TradeSupplyNormDto>();
			page.setLimit(8);
			search.setCategoryName(tradeSupply.getCategoryName());
			out.put("list", tradeSupplyService.pageBySearchEngine(search, page).getRecords());

			Integer cid = tradeSupply.getSupply().getCid();

			cid = compProfileService.initCid(request.getServerName(), cid, out);
			
			// 判断该供求是否该域名用户所有
			if(cid!=null && tradeSupply  !=null)
			if(!cid.equals(tradeSupply.getSupply().getCid())){
				break;
			}

			if (cid == null) {
				break;
			}

			if (!compProfileService
					.initEsite(cid, request.getServerName(), out)) {
				break;
			}

			out.put("id", id);
			out.put("cid", cid);
			out.put("tradeSupply", tradeSupply);
			List<Photo> pl = photoService. queryPhotoByTargetTypePass("supply",  id);
			//图片在photo中不存在时，默认去产品的图片路径
//			if(pl == null || pl.size() ==0) {
//				if(tradeSupply.getSupply()!=null &&StringUtils.isNotEmpty(tradeSupply.getSupply().getPhotoCover())){
//					Photo p = new Photo();
//					p.setId(1);
//					p.setPath(tradeSupply.getSupply().getPhotoCover());
//					pl = new ArrayList<Photo>() ;
//					pl.add(p);
//				}
//			}
			
			out.put("picList", pl); 
			
			// Navigate of trade
			out.put("categoryPath", tradeCategoryService.buildCategoryPath(tradeSupply.getSupply().getCategoryCode()));
			
			// 查询相关类别
			if(StringUtils.isNotEmpty(tradeSupply.getSupply().getCategoryCode())){
			out.put("broCode", tradeCategoryService.queryBroCategoryByCode(
					tradeSupply.getSupply().getCategoryCode()
							.substring(
									0,
									tradeSupply.getSupply().getCategoryCode()
											.length() - 4), 100));
			}
			// 获取专业属性
			List<TradeProperty> propertys = tradePropertyService
					.queryPropertyByCategoryCode(tradeSupply.getSupply()
							.getCategoryCode());

			out.put("tradeProperty", propertys);
			out.put("propertyValueMap", tradeSupply.getPropertyValue());

			out.put("uid", tradeSupply.getSupply().getUid());

			// SEO
			if (tradeSupply.getCategoryName() != null) {
				out.put("categoryName", URLEncoder.encode(tradeSupply
						.getCategoryName(), HttpUtils.CHARSET_UTF8));
			}

			String tags = "";
			if (StringUtils.isNotEmpty(tradeSupply.getSupply().getTags())) {
				tags = tradeSupply.getSupply().getTags();
			}

			String[] titleParam = { tradeSupply.getSupply().getTitle(),
					tradeSupply.getCategoryName() };
			String[] keywordsParam = { tags };
			String description = Jsoup.clean(tradeSupply.getSupply()
					.getDetails(), Whitelist.none());
			if (StringUtils.isNotEmpty(tradeSupply.getSupply().getDetails())
					&& description.length() > 100) {
				description = description.substring(0, 100);
			}
			String[] descriptionParam = { tradeSupply.getCategoryName(),
					tradeSupply.getSupply().getTitle(), description };

			SeoUtil.getInstance().buildSeo("detail", titleParam, keywordsParam,
					descriptionParam, out);
			//用于判断询价是否发送成功
		    if(flag!=null && flag==1){
            	out.put("show", 1);
            }else {	
            	out.put("show", 0);
            }
			return null;
		} while (false);
	    TradeSupply tradeSupply=tradeSupplyService.queryOneSupplyById(id);
		if(tradeSupply!=null){
			out.put("checkStatus", tradeSupply.getCheckStatus());
			out.put("delStatus", tradeSupply.getDelStatus());
		}  
	    return brokenRequest();
	}

	/**
	 * 函数名称：resourceNotFound 功能描述：发生404错误页面。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView resourceNotFound(HttpServletRequest request,
			Map<String, Object> out) {
		// SEO初始化
		SeoUtil.getInstance().buildSeo("", null, null, null, out);
		return null;
	}

	/**
	 * 函数名称：uncaughtException 功能描述：发生异常错误页面。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView uncaughtException(HttpServletRequest request,
			Map<String, Object> out) {
		// SEO初始化
		SeoUtil.getInstance().buildSeo("", null, null, null, out);
		return null;
	}

	/******************************/
	@RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request,
			Map<String, Object> out) {

		return null;
	}

	private ModelAndView brokenRequest() {
		return new ModelAndView("resourceNotFound");
	}
}

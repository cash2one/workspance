/**
 * @author shiqp
 * @date 2015-03-07
 */
package com.ast.ast1949.market.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.market.MarketPic;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.market.MarketCompanyService;
import com.ast.ast1949.service.market.MarketPicService;
import com.ast.ast1949.service.market.MarketService;
import com.ast.ast1949.service.market.ProductMarketService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

@Controller
public class MarketController extends BaseController{
	@Resource
	private MarketService marketService;
	@Resource
	private MarketCompanyService marketCompanyService;
	@Resource
	private CompanyService companyService;
	@Resource
	private ProductMarketService productMarketService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private MarketPicService marketPicService;
    @Resource
    private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private ProductsPicService productsPicService;
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,HttpServletRequest request) {
		//再生市场数
		out.put("marketNum", marketService.countMarketByCondition(null, null, null));
		//商户数
		out.put("companyNum", marketCompanyService.countMarketCompany());
		//供求数
		out.put("productNum",marketService.sumProductNum());
		//弹框标志
		Integer flag=null;
		SsoUser sessionUser = getCachedUser(request);
		if(sessionUser!=null){
			flag=marketService.getBoxFlag(sessionUser.getCompanyId());
			if(flag==0){
				List<Market> list=marketCompanyService.queryMarketByCompanyId(sessionUser.getCompanyId());
				if(list.size()>0){
					flag=4;  //已经入驻过
				}
			}
		}
		out.put("sessionUser", sessionUser);
		out.put("flag", flag);
		SeoUtil.getInstance().buildSeo(out);
		return new ModelAndView();
	}
	@RequestMapping
	public ModelAndView enterMarket(Map<String, Object> out,HttpServletRequest request) throws IOException{
		SsoUser sessionUser = getCachedUser(request);
		Company company=companyService.queryCompanyById(sessionUser.getCompanyId());
		String address="";
		if(company.getAreaCode().length()>15){
			address=CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 12))+" "+CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 16));
		}else if(company.getAreaCode().length()==8&&!"10011000".equals(company.getAreaCode())){
			address=CategoryFacade.getInstance().getValue(company.getAreaCode());
		}else{
	    	Map<String,Integer> map=new HashMap<String,Integer>();
	    	map.put("flag", -1);
	    	return printJson(map, out);
	    }
		String industry=CategoryFacade.getInstance().getValue(company.getIndustryCode());
		if("废旧二手设备".equals(industry)){
			industry="二手设备";
		}
		out.put("industry", industry);
		out.put("area", address);
		//url
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
		Integer flag=0;
		SsoUser sessionUser = getCachedUser(request);
		Map<String,Integer> map=new HashMap<String,Integer>();
		if(sessionUser==null){
			map.put("flag", flag);
			return printJson(map, out);
		}
		flag=marketService.getBoxFlag(sessionUser.getCompanyId());
		if(marketId!=null&&flag==2){
			flag=-3;
		}
		if(flag==1||flag==3){
			flag=-1;
		}
		//公司地址（省、市）
		if(flag==0&&marketId!=null){
			Company company=companyService.queryCompanyById(sessionUser.getCompanyId());
			if(company.getAreaCode().length()>15){
				String address=CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 12))+CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 16));
				Market market=marketService.queryMarketById(marketId);
				if(!address.equals(market.getArea().replace(" ", ""))){
					flag=-3;
				}
			}
		}
		if(flag==0&&marketId!=null){
			flag=marketCompanyService.insertMarketCompany(marketId, sessionUser.getCompanyId());
		}else if(flag==0&&marketList!=null){
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
		return new ModelAndView("redirect:"+"common/remind.htm");
	}
	@RequestMapping
	public ModelAndView remind(Map<String, Object> out){
		return new ModelAndView();
	}
	@RequestMapping
	public ModelAndView remind1(Map<String, Object> out){
		return new ModelAndView();
	}
	@RequestMapping
	public ModelAndView perfect(){
		return new ModelAndView();
	}
	@RequestMapping
	public ModelAndView remind5(Map<String, Object> out){
		return new ModelAndView();
	}
	@RequestMapping
	public ModelAndView market(Map<String, Object> out,HttpServletRequest request,String provice, String category ,String industry, PageDto<Market> page,Integer flag,String keywords,Integer dir) throws UnsupportedEncodingException{
		page.setPageSize(10);
		page.setStartIndex((page.getCurrentPage()-1)*page.getPageSize());
		//产业带地图
		Map<String,String> map=CategoryFacade.getInstance().getChild("10011000");
		out.put("map", map);
		if(flag==null){
			flag=0;
		}
		if(dir==null){
			dir=0;
		}
		if(StringUtils.isEmpty(provice)||provice.length()>3){
			if(StringUtils.isNotEmpty(keywords)){
				keywords=keywords.split(",")[0];
			}
			if(StringUtils.isNotEmpty(keywords)&&!StringUtils.isContainCNChar(keywords)){
				keywords = StringUtils.decryptUrlParameter(keywords);
			}
			String addr = "";
			if(provice.length()>12){
				addr=CategoryFacade.getInstance().getValue(provice.substring(0, 12)) +" "+ CategoryFacade.getInstance().getValue(provice);
				out.put("pk", provice.substring(0, 12));
			}else{
				addr=CategoryFacade.getInstance().getValue(provice);
			}
			page=marketService.pageSearchOfMarket(addr, CATEGORY_MAP.get(category), CATEGORY_MAP.get(industry), flag, null, page,keywords,dir);
		}else{
			page=marketService.pageSearchOfMarket(HOT_MAP.get(provice), CATEGORY_MAP.get(category), CATEGORY_MAP.get(industry), flag, null, page,null,dir);
		}
		out.put("page", page);
		out.put("provice", provice);
		out.put("category", category);
		out.put("industry", industry);
		out.put("flag", flag);
		out.put("dir", dir);
		out.put("marketNum", page.getTotalRecords());
		out.put("keyword", keywords);
		String[] title=new String[2];
		String[] keyw=new String[2];
		String[] desc=new String[2];
		if(StringUtils.isEmpty(category)&&StringUtils.isEmpty(provice)&&StringUtils.isNotEmpty(industry)){
			title[0]=CATEGORY_MAP.get(industry)+"交易市场-zz91再生网";
			keyw[0]=CATEGORY_MAP.get(industry)+"交易市场";
			if("1000".equals(industry)){
				desc[0]="zz91再生网废金属交易市场汇聚废钢铁、有色金属、稀贵金属、混合与复合金属、再生金属以及废金属处理设备等废金属产业的详细交易市场信息，足不出户就能了解最新的废金属货源信息。";
			}else if("1001".equals(industry)){
				desc[0]="zz91再生网废塑料交易市场汇聚废塑料、再生颗粒、塑料助剂以及塑料加工设备等产业的详细交易市场信息，足不出户就能了解最新的废塑料货源信息。";
			}else if("1002".equals(industry)){
				desc[0]="zz91再生网废二手设备交易市场汇聚交通、机床、工程、化工、制冷、纺织、电子、电力、矿业、塑料以及印刷等行业的详细交易市场信息，足不出户就能了解最新的二手设备货源信息。";
			}
		}else if(StringUtils.isEmpty(category)&&StringUtils.isNotEmpty(provice)&&StringUtils.isNotEmpty(industry)){
			if(provice.length()>3){
				title[0]=CategoryFacade.getInstance().getValue(provice)+CATEGORY_MAP.get(industry)+"交易市场-zz91再生网";
				keyw[0]=CategoryFacade.getInstance().getValue(provice)+CATEGORY_MAP.get(industry)+"交易市场"+","+CategoryFacade.getInstance().getValue(provice)+CATEGORY_MAP.get(industry)+"市场";
				if("1002".equals(industry)){
					desc[0]="zz91再生网"+CategoryFacade.getInstance().getValue(provice)+CATEGORY_MAP.get(industry)+"交易市场汇聚交通、机床、工程、化工、制冷、纺织、电子、电力、矿业、塑料以及印刷等行业的详细交易市场信息，足不出户就能了解"+CategoryFacade.getInstance().getValue(provice)+"最新的"+CATEGORY_MAP.get(industry)+"货源信息。";
				}else if("1001".equals(industry)){
					desc[0]="zz91再生网"+CategoryFacade.getInstance().getValue(provice)+CATEGORY_MAP.get(industry)+"交易市场汇聚废塑料、再生颗粒、塑料助剂以及塑料加工设备等产业的详细交易市场信息，足不出户就能了解"+CategoryFacade.getInstance().getValue(provice)+"最新的"+CATEGORY_MAP.get(industry)+"货源信息。";
				}else if("1000".equals(industry)){
					desc[0]="zz91再生网"+CategoryFacade.getInstance().getValue(provice)+CATEGORY_MAP.get(industry)+"交易市场汇聚废钢铁、有色金属、稀贵金属、混合与复合金属、再生金属以及废金属处理设备等废金属产业的详细交易市场信息，足不出户就能了解"+CategoryFacade.getInstance().getValue(provice)+"最新的"+CATEGORY_MAP.get(industry)+"货源信息。";
				}
			}else{
				title[0]=HOT_MAP.get(provice).replace(" ", "")+CATEGORY_MAP.get(industry)+"交易市场-zz91再生网";
				keyw[0]=HOT_MAP.get(provice).replace(" ", "")+CATEGORY_MAP.get(industry)+"交易市场"+","+HOT_MAP.get(provice)+CATEGORY_MAP.get(industry)+"市场";
				if("1002".equals(industry)){
					desc[0]="zz91再生网"+HOT_MAP.get(provice).replace(" ", "")+CATEGORY_MAP.get(industry)+"交易市场汇聚交通、机床、工程、化工、制冷、纺织、电子、电力、矿业、塑料以及印刷等行业的详细交易市场信息，足不出户就能了解"+HOT_MAP.get(provice)+"最新的"+CATEGORY_MAP.get(industry)+"货源信息。";
				}else if("1001".equals(industry)){
					desc[0]="zz91再生网"+HOT_MAP.get(provice).replace(" ", "")+CATEGORY_MAP.get(industry)+"交易市场汇聚废塑料、再生颗粒、塑料助剂以及塑料加工设备等产业的详细交易市场信息，足不出户就能了解"+HOT_MAP.get(provice)+"最新的"+CATEGORY_MAP.get(industry)+"货源信息。";
				}else if("1000".equals(industry)){
					desc[0]="zz91再生网"+HOT_MAP.get(provice).replace(" ", "")+CATEGORY_MAP.get(industry)+"交易市场汇聚废钢铁、有色金属、稀贵金属、混合与复合金属、再生金属以及废金属处理设备等废金属产业的详细交易市场信息，足不出户就能了解"+HOT_MAP.get(provice)+"最新的"+CATEGORY_MAP.get(industry)+"货源信息。";
				}
			}
		}else if(StringUtils.isEmpty(provice)&&StringUtils.isNotEmpty(CATEGORY_MAP.get(category))){
			title[0]=CATEGORY_MAP.get(category)+"交易市场_"+CATEGORY_MAP.get(industry)+"交易市场-zz91再生网";
			keyw[0]=CATEGORY_MAP.get(category)+"交易市场"+","+CATEGORY_MAP.get(category)+"市场"+","+CATEGORY_MAP.get(industry)+"交易市场";
			desc[0]="zz91再生网"+CATEGORY_MAP.get(category)+"交易市场为您提供全国各地"+CATEGORY_MAP.get(category)+"集散地的信息，轻轻松松找到相应"+CATEGORY_MAP.get(category)+"商家，获取更多相关信息，尽在zz91再生网。";
		}else if(StringUtils.isNotEmpty(provice)&&StringUtils.isNotEmpty(CATEGORY_MAP.get(category))){
			if(provice.length()>3){
				title[0]=CategoryFacade.getInstance().getValue(provice)+CATEGORY_MAP.get(category)+"交易市场_"+CategoryFacade.getInstance().getValue(provice)+CATEGORY_MAP.get(industry)+"交易市场-zz91再生网";
				keyw[0]=CategoryFacade.getInstance().getValue(provice)+CATEGORY_MAP.get(category)+"交易市场,"+CategoryFacade.getInstance().getValue(provice)+CATEGORY_MAP.get(category)+"市场，"+CategoryFacade.getInstance().getValue(provice)+CATEGORY_MAP.get(industry)+"交易市场";
				desc[0]="zz91再生网"+CategoryFacade.getInstance().getValue(provice)+CATEGORY_MAP.get(category)+"交易市场为您提供"+CategoryFacade.getInstance().getValue(provice)+"地区"+CATEGORY_MAP.get(category)+"集散地的信息，轻轻松松找到相应"+CATEGORY_MAP.get(category)+"商家，获取更多相关信息，尽在zz91再生网";
			}else{
				title[0]=HOT_MAP.get(provice).replace(" ", "")+CATEGORY_MAP.get(category)+"交易市场_"+HOT_MAP.get(provice)+CATEGORY_MAP.get(industry)+"交易市场-zz91再生网";
				keyw[0]=HOT_MAP.get(provice).replace(" ", "")+CATEGORY_MAP.get(category)+"交易市场,"+HOT_MAP.get(provice)+CATEGORY_MAP.get(category)+"市场，"+HOT_MAP.get(provice)+CATEGORY_MAP.get(industry)+"交易市场";
				desc[0]="zz91再生网"+HOT_MAP.get(provice).replace(" ", "")+CATEGORY_MAP.get(category)+"交易市场为您提供"+HOT_MAP.get(provice)+"地区"+CATEGORY_MAP.get(category)+"集散地的信息，轻轻松松找到相应"+CATEGORY_MAP.get(category)+"商家，获取更多相关信息，尽在zz91再生网";
			}
		}
		if(StringUtils.isNotEmpty(keywords)){
			title[0]="废料交易市场_"+keywords+"-zz91再生网";
			keyw[0]=keywords;
			desc[0]="zz91再生网废料交易市场汇聚废金属、废塑料、二手设备等废料产业的详细交易市场信息，同时你也可以入住相应的废料交易市场，让您足不出户，就可以了解到最新的废料货源信息。";
		}
		SeoUtil.getInstance().buildSeo("market", title, keyw, desc, out);
		if(StringUtils.isNotEmpty(keywords)){
			out.put("keywords", URLEncoder.encode(keywords, HttpUtils.CHARSET_UTF8));
		}
		return new ModelAndView();
	}
	@RequestMapping
	public ModelAndView marketIndex(Map<String, Object> out,HttpServletRequest request,String words) throws HttpException, IOException{
		Market market=marketService.queryMarketByWords(words);
		out.put("market", market);
		String[] p=market.getArea().split(" ");
		out.put("province", p[0]);
		List<MarketPic> list=marketPicService.queryPicByMarketId(market.getId());
		out.put("list", list);
		out.put("industryCode", URLEncoder.encode(market.getIndustry(), HttpUtils.CHARSET_UTF8));
		out.put("provinceCode", categoryService.queryCodeByLabel(p[0]));
		if(!"二手设备".equals(market.getIndustry())){
			String newsData = "";
			Integer size = 6;
			try {
				String keywords = URLEncoder.encode(market.getIndustry(), HttpUtils.CHARSET_UTF8);
				newsData = HttpUtils.getInstance().httpGet(
						AddressTool.getAddress("pyapp")
								+ "/news/javagetnewslist_json.html?keywords="
								+ keywords + "&num=" + size,
						HttpUtils.CHARSET_UTF8);
			} catch (Exception e) {
				newsData = null;
			}

			if (StringUtils.isNotEmpty(newsData)) {
				JSONObject jo = new JSONObject();
				try {
					jo = JSONObject.fromObject(newsData);
				} catch (Exception e) {
					jo = null;
				}
				if (jo == null || (Integer) jo.get("count") < size) {
					newsData = HttpUtils
							.getInstance()
							.httpGet(
									AddressTool.getAddress("pyapp")
											+ "javagetnewslist_json.html?typeid2=155&num="
											+ size, HttpUtils.CHARSET_UTF8);
					jo = JSONObject.fromObject(newsData);
				}
				JSONArray ja = JSONArray.fromObject(jo.get("list"));
				out.put("ja", ja);
			}
		}
		String desc="";
		if(market.getIntroduction().length()>100){
			desc=market.getIntroduction().substring(0, 100);
		}else{
			desc=market.getIntroduction();
		}
		SeoUtil.getInstance().buildSeo("marketIndex", new String[]{market.getName()}, new String[]{market.getName()}, new String[]{desc}, out);
		//市场热门供应
		return new ModelAndView();
	}
	@RequestMapping
	public ModelAndView marketInfo(Map<String, Object> out,HttpServletRequest request,String words) throws HttpException, IOException{
		Market market=marketService.queryMarketByWords(words);
		out.put("market", market);
		String[] p=market.getArea().split(" ");
		out.put("province", p[0]);
		List<MarketPic> list=marketPicService.queryPicByMarketId(market.getId());
		out.put("list", list);
		out.put("areaCode", URLEncoder.encode(market.getArea(), HttpUtils.CHARSET_UTF8));
		out.put("industryCode", URLEncoder.encode(market.getIndustry(), HttpUtils.CHARSET_UTF8));
		if(!"二手设备".equals(market.getIndustry())){
			String newsData = "";
			Integer size = 6;
			try {
				String keywords = URLEncoder.encode(market.getIndustry(), HttpUtils.CHARSET_UTF8);
				newsData = HttpUtils.getInstance().httpGet(
						AddressTool.getAddress("pyapp")
								+ "/news/javagetnewslist_json.html?keywords="
								+ keywords + "&num=" + size,
						HttpUtils.CHARSET_UTF8);
			} catch (Exception e) {
				newsData = null;
			}

			if (StringUtils.isNotEmpty(newsData)) {
				JSONObject jo = new JSONObject();
				try {
					jo = JSONObject.fromObject(newsData);
				} catch (Exception e) {
					jo = null;
				}
				if (jo == null || (Integer) jo.get("count") < size) {
					newsData = HttpUtils
							.getInstance()
							.httpGet(
									AddressTool.getAddress("pyapp")
											+ "/news/javagetnewslist_json.html?keywords=&num="
											+ size, HttpUtils.CHARSET_UTF8);
					jo = JSONObject.fromObject(newsData);
				}
				JSONArray ja = JSONArray.fromObject(jo.get("list"));
				out.put("ja", ja);
			}
		}
		String desc="";
		if(market.getIntroduction().length()>100){
			desc=market.getIntroduction().substring(0, 100);
		}else{
			desc=market.getIntroduction();
		}
		SeoUtil.getInstance().buildSeo("marketInfo", new String[]{market.getName()}, new String[]{market.getName()}, new String[]{desc}, out);
		return new ModelAndView();
	}
	@RequestMapping
	public ModelAndView marketTrader(Map<String, Object> out,String words,PageDto<CompanyDto> page) throws HttpException, IOException{
		Market market=marketService.queryMarketByWords(words);
		out.put("market", market);
		page.setPageSize(10);
		page.setStartIndex((page.getCurrentPage()-1)*page.getPageSize());
		page=marketCompanyService.PageSearchCompanyByCondition(page, market.getId(), 3);
		for(CompanyDto dto:page.getRecords()){
			if(crmCompanySvrService.validatePeriod(dto.getCompany().getId(), "10001003")){
				dto.setIsZSVip(true);
			}
		}
		out.put("page", page);
		out.put("marketId", market.getId());
		String[] p=market.getArea().split(" ");
		out.put("province", p[0]);
		out.put("industryCode", URLEncoder.encode(market.getIndustry(), HttpUtils.CHARSET_UTF8));
		if(!"二手设备".equals(market.getIndustry())){
			String newsData = "";
			Integer size = 6;
			try {
				String keywords = URLEncoder.encode(market.getIndustry(), HttpUtils.CHARSET_UTF8);
				newsData = HttpUtils.getInstance().httpGet(
						AddressTool.getAddress("pyapp")
								+ "/news/javagetnewslist_json.html?keywords="
								+ keywords + "&num=" + size,
						HttpUtils.CHARSET_UTF8);
			} catch (Exception e) {
				newsData = null;
			}

			if (StringUtils.isNotEmpty(newsData)) {
				JSONObject jo = new JSONObject();
				try {
					jo = JSONObject.fromObject(newsData);
				} catch (Exception e) {
					jo = null;
				}
				if (jo == null || (Integer) jo.get("count") < size) {
					newsData = HttpUtils
							.getInstance()
							.httpGet(
									AddressTool.getAddress("pyapp")
											+ "/news/javagetnewslist_json.html?keywords=&num="
											+ size, HttpUtils.CHARSET_UTF8);
					jo = JSONObject.fromObject(newsData);
				}
				JSONArray ja = JSONArray.fromObject(jo.get("list"));
				out.put("ja", ja);
			}
		}
		String desc="";
		if(market.getIntroduction().length()>100){
			desc=market.getIntroduction().substring(0, 100);
		}else{
			desc=market.getIntroduction();
		}
		SeoUtil.getInstance().buildSeo("marketTrader", new String[]{market.getName()}, new String[]{market.getName()}, new String[]{desc}, out);
		return new ModelAndView();
	}
	@RequestMapping
	public ModelAndView marketIndustry(Map<String, Object> out,String words,PageDto<Market> page) throws UnsupportedEncodingException{
		Market market=marketService.queryMarketByWords(words);
		out.put("market", market);
		page.setPageSize(10);
		page.setStartIndex((page.getCurrentPage()-1)*page.getPageSize());
		String[] p=market.getArea().split(" ");
		out.put("province", p[0]);
		//供求要用
		if("废金属".equals(market.getIndustry())){
			out.put("industry", "1000");
		}
		if("废塑料".equals(market.getIndustry())){
			out.put("industry", "1001");
		}
		if("二手设备".equals(market.getIndustry())){
			out.put("industry", "1007");
		}
		page=marketService.pageSearchOfMarket(null, null, market.getIndustry(), 0, market.getId(), page,null,0);
		out.put("industryCode", URLEncoder.encode(market.getIndustry(), HttpUtils.CHARSET_UTF8));
		out.put("page", page);
		String desc="";
		if(market.getIntroduction().length()>100){
			desc=market.getIntroduction().substring(0, 100);
		}else{
			desc=market.getIntroduction();
		}
		SeoUtil.getInstance().buildSeo("marketIndustry", new String[]{market.getName()}, new String[]{market.getName()}, new String[]{desc}, out);
		return new ModelAndView();
	}
	@RequestMapping
	public ModelAndView marketTrade(Map<String, Object> out,String words,PageDto<ProductsDto> page,String industry,String type) throws HttpException, IOException{
		Market market=marketService.queryMarketByWords(words);
		out.put("market", market);
		if(StringUtils.isNotEmpty(type)){
			if("gy".equals(type)){
				type="10331000";
			}else{
				type="10331001";
			}
		}
		page.setPageSize(10);
		page.setStartIndex((page.getCurrentPage()-1)*page.getPageSize());
		String[] p=market.getArea().split(" ");
		out.put("province", p[0]);
		out.put("industryCode", URLEncoder.encode(market.getIndustry(), HttpUtils.CHARSET_UTF8));
		page=productMarketService.pageSearchProducts(page, market.getId(), type, industry, 1,null,null);
		for(ProductsDto dto:page.getRecords()){
			if(crmCompanySvrService.validatePeriod(dto.getCompany().getId(), "10001003")){
				dto.setIsZSVip(true);
			}
		}
		out.put("page", page);
		if(StringUtils.isNotEmpty(type)){
			if("10331000".equals(type)){
				type="gy";
			}else{
				type="qg";
			}
		}
		out.put("type", type);
		out.put("industry", industry);
		if(!"二手设备".equals(market.getIndustry())){
			String newsData = "";
			Integer size = 6;
			try {
				String keywords = URLEncoder.encode(market.getIndustry(), HttpUtils.CHARSET_UTF8);
				newsData = HttpUtils.getInstance().httpGet(
						AddressTool.getAddress("pyapp")
								+ "/news/javagetnewslist_json.html?keywords="
								+ keywords + "&num=" + size,
						HttpUtils.CHARSET_UTF8);
			} catch (Exception e) {
				newsData = null;
			}

			if (StringUtils.isNotEmpty(newsData)) {
				JSONObject jo = new JSONObject();
				try {
					jo = JSONObject.fromObject(newsData);
				} catch (Exception e) {
					jo = null;
				}
				if (jo == null || (Integer) jo.get("count") < size) {
					newsData = HttpUtils
							.getInstance()
							.httpGet(
									AddressTool.getAddress("pyapp")
											+ "/news/javagetnewslist_json.html?keywords=&num="
											+ size, HttpUtils.CHARSET_UTF8);
					jo = JSONObject.fromObject(newsData);
				}
				JSONArray ja = JSONArray.fromObject(jo.get("list"));
				out.put("ja", ja);
			}
		}
		String desc="";
		if(market.getIntroduction().length()>100){
			desc=market.getIntroduction().substring(0, 100);
		}else{
			desc=market.getIntroduction();
		}
		String[] title=new String[2];
		String[] keywords=new String[2];
		if(StringUtils.isNotEmpty(industry)&&StringUtils.isNotEmpty(type)){
			if("10331000".equals(type)){
				title[0]="供应"+CategoryProductsFacade.getInstance().getValue(industry)+"_"+market.getName()+"-zz91再生网";
				keywords[0]="供应"+CategoryProductsFacade.getInstance().getValue(industry);
			}else{
				title[0]="求购"+CategoryProductsFacade.getInstance().getValue(industry)+"_"+market.getName()+"-zz91再生网";
				keywords[0]="求购"+CategoryProductsFacade.getInstance().getValue(industry);
			}
		}else if(StringUtils.isNotEmpty(industry)&&StringUtils.isEmpty(type)){
			title[0]=CategoryProductsFacade.getInstance().getValue(industry)+"供求信息_市场名称-zz91再生网";
			keywords[0]=CategoryProductsFacade.getInstance().getValue(industry)+"供求信息";
		}else if(StringUtils.isEmpty(industry)&&StringUtils.isNotEmpty(type)){
			if("10331000".equals(type)){
				title[0]="供应信息_"+market.getName()+"-zz91再生网";
				keywords[0]="供应信息";
			}else{
				title[0]="求购信息_"+market.getName()+"-zz91再生网";
				keywords[0]="求购信息";
			}
		}else{
			title[0]="市场供求_"+market.getName()+"-zz91再生网";
			keywords[0]=market.getName()+"市场供求";
		}
		SeoUtil.getInstance().buildSeo("marketTrade",title, keywords, new String[]{desc}, out);
		return new ModelAndView();
	}
	@RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request,
			Map<String, Object> out, String success, String data) {
		if (StringUtils.isEmpty(data)) {
			data = "{}";
		}
		try {
			data = StringUtils.decryptUrlParameter(data);
		} catch (UnsupportedEncodingException e) {
		}
		SsoUser sessionUser = getCachedUser(request);
		Integer mark=0;
		if(sessionUser!=null){
			 mark=marketService.getBoxFlag(sessionUser.getCompanyId());
			 if(mark==0){
				List<Market> list=marketCompanyService.queryMarketByCompanyId(sessionUser.getCompanyId());
				if(list.size()>0){
					mark=4;  //已经入驻过
				}
			}
		}
		out.put("mark", mark);
		out.put("success", success);
		out.put("data", data);
		return null;
	}
	@RequestMapping
	public ModelAndView remind4(HttpServletRequest request,Map<String, Object> out){
		SsoUser sessionUser = getCachedUser(request);
		if(sessionUser!=null){
			List<Market> list=marketCompanyService.queryMarketByCompanyId(sessionUser.getCompanyId());
			String mlist="";
			for(Market market:list){
				mlist=mlist+market.getName()+",";
			}
			if(StringUtils.isNotEmpty(mlist)){
				out.put("market", mlist.substring(0, mlist.length()-1));
			}
		}
		return null;
	}
	@RequestMapping
	public ModelAndView remind2(){
		return null;
	}
	
	@RequestMapping
	public ModelAndView createMarket(HttpServletRequest request,Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView create_suc(){
		return null;
	} 
	@RequestMapping
	public ModelAndView remind3(){
		return null;
	}
	@RequestMapping
	public ModelAndView doCreateMarket(HttpServletRequest request,Map<String, Object> out,Market market) throws Exception{
		Map<String,Object> map =new HashMap<String,Object>();
		do{
			SsoUser sessionUser = getCachedUser(request);
			if(sessionUser == null){
				break;
			}
		    market.setCompanyId(sessionUser.getCompanyId());
		    //将市场名称转成拼音首字母集
		    String convert = "";  
	        for (int j = 0; j < market.getName().length(); j++) {  
	            char word = market.getName().charAt(j);  
	            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
	            if (pinyinArray != null) {  
	                convert += pinyinArray[0].charAt(0);  
	            } else {  
	                convert += word;  
	            }  
	        }  
	        Integer flag = 0;
	        Integer fi = 1;
	        //判断该市场拼音是否存在
	        if(StringUtils.isEmpty(convert)){
	        	break;
	        }
	        do{
	        	Market m = marketService.queryMarketByWords(convert);
	        	if(m!=null){
	        		Integer ff = fi - 1;
	        		convert = convert.substring(0, ff.toString().length());
	        		convert = convert + String.valueOf(fi);
	        		fi ++;
	        	}else{
	        		flag = 1;
	        	}
	        }while(flag!=1);
	        market.setWords(convert);
	        market.setCheckStatus(0);
		    Integer marketId=marketService.insertMarket(market);
		    if(marketId > 0){
		    	map.put("id", marketId);
		    }
			//市场图片信息
			for(String s : market.getPicIds().split(",")){
				if(StringUtils.isNotEmpty(s)&&!"0".equals(s)){
					marketPicService.updateMarketIdById(marketId, Integer.valueOf(s));
				}
			}
			//默认最先上传的图片为置顶图片
			marketPicService.markDefaultPic(marketId);
		}while(false);
		return printJson(map, out);
	}
	@RequestMapping
	public ModelAndView deleteOnlyPic(HttpServletRequest request,Map<String, Object> out, Integer id) throws IOException {
		marketPicService.delMarketPicById(id,3);
		ExtResult result = new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	/**
	 * 首页产品类目，自编
	 */
	final static Map<String, String> CATEGORY_MAP = new HashMap<String, String>();
	static {
		CATEGORY_MAP.put("1000", "废金属");
		CATEGORY_MAP.put("10001000", "废钢铁");
		CATEGORY_MAP.put("10001001", "有色金属");
		CATEGORY_MAP.put("10001010", "稀贵金属");
		CATEGORY_MAP.put("10001011", "混合金属");//混合/复合
		CATEGORY_MAP.put("10001100", "再生金属");
		CATEGORY_MAP.put("10001101", "废金属处理设备");
		CATEGORY_MAP.put("1001", "废塑料");
		CATEGORY_MAP.put("10011000", "再生颗粒");
		CATEGORY_MAP.put("10011001", "塑料助剂");
		CATEGORY_MAP.put("10011010", "废塑料加工设备");//塑料加工设备
		CATEGORY_MAP.put("1002", "二手设备");
		CATEGORY_MAP.put("100210000", "交通工具");
		CATEGORY_MAP.put("100210001", "机床设备");
		CATEGORY_MAP.put("100210010", "工程设备");
		CATEGORY_MAP.put("100210011", "化工设备");
		CATEGORY_MAP.put("100211000", "制冷设备");
		CATEGORY_MAP.put("100211001", "纺织设备");
		CATEGORY_MAP.put("100211010", "电子设备");
		CATEGORY_MAP.put("100211011", "电力设备");
		CATEGORY_MAP.put("100211100", "矿业设备");
		CATEGORY_MAP.put("100211101", "塑料设备");
		CATEGORY_MAP.put("100211110", "印刷设备");
	}
	/**
	 * 热门产业带
	 */
	final static Map<String, String> HOT_MAP = new HashMap<String, String>();
	static {
		HOT_MAP.put("1", "广东 南海");
		HOT_MAP.put("2", "山东 聊城");
		HOT_MAP.put("3", "山东 临沂");
		HOT_MAP.put("4", "浙江 永康");
		HOT_MAP.put("5", "浙江 宁波");//混合/复合
		HOT_MAP.put("6", "江苏 太仓");
		HOT_MAP.put("7", "湖南 泪罗");
		HOT_MAP.put("8", "天津 静海");
		HOT_MAP.put("9", "浙江 台州");//塑料加工设备
		HOT_MAP.put("10", "浙江 慈溪");
		HOT_MAP.put("11", "河北 文安");
		HOT_MAP.put("12", "河南 长葛");
		HOT_MAP.put("13", "安徽 界首");
		HOT_MAP.put("14", "江苏 宿迁");
		HOT_MAP.put("15", "广东 深圳");
		HOT_MAP.put("16", "河北 唐山");
		HOT_MAP.put("17", "江苏 常州");
		HOT_MAP.put("18", "湖北 武汉");
		HOT_MAP.put("19", "浙江 余姚");
		HOT_MAP.put("20", "广东 佛山");
		HOT_MAP.put("21", "河北 廊坊");
		HOT_MAP.put("22", "安徽 阜阳");
		HOT_MAP.put("23", "江苏 徐州");
		HOT_MAP.put("24", "广西 南宁");
		HOT_MAP.put("25", "江苏 苏州");
		HOT_MAP.put("26", "山东 烟台");
	}

}

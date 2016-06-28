/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23 by liulei.
 */
package com.ast.ast1949.service.company.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.EsiteColumnDo;
import com.ast.ast1949.domain.company.EsiteConfigDo;
import com.ast.ast1949.persist.analysis.AnalysisDao;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.EsiteColumnDao;
import com.ast.ast1949.persist.company.EsiteConfigDao;
import com.ast.ast1949.persist.company.EsiteCustomTopicDao;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmSvrService;
import com.ast.ast1949.service.company.EsiteConfigService;
import com.ast.ast1949.service.company.EsiteService;
import com.ast.ast1949.util.Assert;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.lang.StringUtils;

@Component("esiteService")
public class EsiteServiceImpl implements EsiteService {

	@Autowired
	private EsiteConfigDao esiteConfigDao;
	@Autowired
	private EsiteColumnDao esiteColumnDao;
	@Autowired
	private EsiteCustomTopicDao esiteCustomStyleDao;
	@Autowired
	private CompanyDAO companyDAO;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private AnalysisDao analysisDao;
	@Resource
	private EsiteConfigService esiteConfigService;
	@Resource
	CompanyAccountDao companyAccountDao;

	final static String DEFAULT_PAGE_CODE = "sy";
	final static String DEFAULT_COLUMN_CATEGORY = "0";
	final static String PROTOCOL = "http://";
	final static String PRE_DOMAIN =".zz91.com"; 
	final static String MEMCACHE_KEY_ZZ91DOMAIN = "zz91_domain_list";

	/**
	 * 如果没有配置，则使用默认配置，默认配置可以从缓存中获取
	 * 
	 * @return
	 */
	EsiteConfigDo initDefaultConfig() {
		// 需要将配置改成从配置文件或者缓存中获取，用jsonObject转换，不需要重新设置
		EsiteConfigDo config = new EsiteConfigDo();
		config.setId(0);
		config.setIsFormal("Y");
		config
				.setFlashindex("{\"filePath\":\"\",\"width\":700,\"height\":450,\"bgColor\":\"#ffffff\",\"displayed\":false}");
		config
				.setLogoPic("{\"url\":\"http://img0.zz91.com/front/images/esite/logo.gif\",\"height\":58}"); // 网站的logo，默认情况下使用zz91的logo
		config
				.setNavColumnConfig("[{'id':'gsjs','t':'公司介绍','d':true},{'id':'zxgq','t':'最新供求','d':true},{'id':'cxda','t':'诚信档案','d':true},{'id':'zxly','t':'在线留言','d':true},{'id':'lxfs','t':'联系方式','d':true}]");
		config.setIsDefault("Y");
		config.setMycolumn(DEFAULT_PAGE_CODE);
		config.setTitle("");
		config.setPosition("layout_left");
		config.setSlogan("");
		config.setSubSlogan("");
		config.setIsTransparent("Y");
		config.setPageWidth("952");
		config
				.setStyleContent("[{'cn':'bodyCont','sl':{'border-color':'#648eb6','background':'#ffffff','border-style':'solid'}},{'cn':'bodyContTitle','sl':{'background':'url(http://img0.zz91.com/front/images/esite/topic/jz1_titleBg.gif) repeat-x'}},{'cn':'imgBorder','sl':{'border-color':'#7aa0c8','border-style':'dotted'}},{'cn':'mainTextColor','sl':{'color':'#000000'}},{'cn':'titleLinkColor','sl':{'color':'#ffffff'}},{'cn':'topicLink','sl':{'color':'#27598e'}},{'cn':'headerMenuBorder','sl':{'border-color':'#cccccc','background':'url(http://img0.zz91.com/front/images/esite/topic/jz1_lmyBg.gif) repeat-x'}},{'cn':'headerMenuList','sl':{'color':'#ffffff'}},{'cn':'headerMenuLiCheck','sl':{'border-color':'#ffffff','background':'url(http://img0.zz91.com/front/images/esite/topic/jz1_lmnBg.gif) repeat-x','color':'#27598e'}},{'cn':'headerMenuBottom','sl':{'border-bottom-color':'#ffffff'}},{'cn':'topbaner','sl':{'background':'#ffffff'}},{'cn':'headTopic','sl':{'height':'200px','background':'url(http://img0.zz91.com/front/images/esite/banner/banner_8.jpg) repeat'}},{'cn':'inBg','sl':{'background':'url(http://img0.zz91.com/front/images/esite/topic/jz1_inBg.jpg) repeat'}},{'cn':'bodyBg','sl':{'background':'url(http://img0.zz91.com/front/images/esite/topic/jz1_outBg.gif) repeat'}},{'cn':'description','sl':{'padding-left':'50px','padding-top':'80px'}},{'cn':'chinaname','sl':{'font-family':'楷体_GB2312','font-size':'26px','font-weight':'bold','color':'#000000','font-style':'italic'}},{'cn':'enname','sl':{'font-family':'Verdana','font-size':'20px','font-weight':'bold','color':'#000000','font-style':'italic'}},{'cn':'topDesc','sl':{'font-family':'楷体_GB2312','font-size':'26px','font-weight':'bold','color':'#000000','font-style':'italic'}},{'cn':'bottomDesc','sl':{'font-family':'Verdana','font-size':'20px','font-weight':'bold','color':'#000000','font-style':'italic'}}]");
		config
				.setOverAlllayout("{'headerConfig':[{'id':'company_name'},{'id':'top_nav'},{'id':'theme_pic'}],'sideBarConfig':[{'id':'search_in_site','title':'供求搜索'},{'id':'friend_link','title':'友情链接'},{'id':'contact_side','title':'联系方式'}],'mainConfig':[{'id':'company_index','title':'关于我们'},{'id':'contact_index','title':'联系方式'},{'id':'all_offer_index','title':'最新供求'},{'id':'newslist_index','title':'公司动态'}]}");
		config.setDeleteStyleId("");
		config.setCover("http://img0.zz91.com/front/images/esite/p56_21.gif");
		return config;
	}

	@SuppressWarnings("unchecked")
	public void initBaseConfig(Integer companyId, String pageCode,
			Map<String, Object> out) {
		Assert.notNull(companyId, "the company can not be null!");
		if (StringUtils.isEmpty(pageCode)) {
			pageCode = DEFAULT_PAGE_CODE;
		}
		// step1 查找商铺页面对应的配置信息，如果没有则使用默认配置
		EsiteConfigDo config = esiteConfigDao.queryColumnConfigByCompanyId(companyId, pageCode);
		if (config == null) {
			// 如果没有配置，则使用默认配置
			config = initDefaultConfig();
		}
		if(config.getIsShow()==null){
			config.setIsShow(0);
		}
		//图片
		List<String> listPic=new ArrayList<String>();
		Integer listPicCount=0;
		String pic=esiteConfigService.queryBannelPic(companyId);
		if(StringUtils.isNotEmpty(pic) && pic != null){
			if(pic.contains(",")){
				String[] str=pic.split(",");
				for(int i=0;i<str.length;i++){
					listPic.add(str[i]);
				}
			}else{
				listPic.add(pic);
			}
		}
		
		listPicCount=listPic.size();
		out.put("listPicCount", listPicCount);
		out.put("listPic", listPic);
		// step3 初始化版块数据 queryDataByColumnId()
		JSONObject layoutConfig = JSONObject.fromObject(config.getOverAlllayout());

		JSONArray sideBarConfig = layoutConfig.getJSONArray("sideBarConfig");
		out.put("sideBarConfig", sideBarConfig);

		JSONArray mainConfig = layoutConfig.getJSONArray("mainConfig");
		out.put("mainConfig", mainConfig); 

		// step4 合并系统样式和用户已经存在的栏目
		Map<String, EsiteColumnDo> systemColumnsMap = esiteColumnDao
				.queryAllColumnByCategory(DEFAULT_COLUMN_CATEGORY);
		JSONArray navColumnConfigArray = JSONArray.fromObject(config
				.getNavColumnConfig());
		for (int i = 0; i < navColumnConfigArray.size(); i++) {
			Map<String, String> tmpMap = JSONObject
					.fromObject(navColumnConfigArray.get(i));
			systemColumnsMap.remove(tmpMap.get("id"));
		}
		for (String key : systemColumnsMap.keySet()) {
			EsiteColumnDo columnDefine = systemColumnsMap.get(key);
			Map<String, String> columnMap = new LinkedHashMap<String, String>();
			columnMap.put("id", columnDefine.getColumnId());
			columnMap.put("t", columnDefine.getTitle());
			columnMap.put("d", "false");
			navColumnConfigArray.add(JSONObject.fromObject(columnMap));
		}
		config.setNavColumnConfig(navColumnConfigArray.toString());
		out.put("esiteConfig", config);
		out.put("navColumnConfig", navColumnConfigArray);
		out.put("headerConfig", layoutConfig.getJSONArray("headerConfig"));
		out.put("styleContent", JSONArray.fromObject(config.getStyleContent()));
		if (config.getLogoPic() != null && config.getLogoPic().startsWith("{")) {
			out.put("logoUrl", JSONObject.fromObject(config.getLogoPic()));
		}else{
			out.put("logoUrl", "{url:'http://img0.zz91.com/front/images/esite/logo2010.gif'}");
		}

		// step5 查找公共的公司信息
		// 1.公司基本信息
		Company company = companyDAO.querySimpleCompanyById(companyId);
//		company.setIntroduction(company.getIntroduction()
//				.replace("\n", "<br/>"));
		out.put("company", company);
		// 2.公司二级域名信息

		// step6 查找用户已保存过后风格库
		out.put("esiteCustomStyleList", esiteCustomStyleDao
				.queryTopicByCompany(companyId));
		out.put("cid", companyId);
		if(crmCompanySvrService.validatePeriod(companyId, "10001001")){
			out.put("zst10001001", "1");
		}
		
		Integer visitCount=analysisDao.queryEsiteVisit(companyId);
		if(visitCount==null || visitCount<5000){
			visitCount=35108+(int)(Math.random()*10000);
		}
		out.put("visitCount", visitCount);
		
		// 公司介绍
		String introduction=companyDAO.queryDetails(companyId);
		introduction=Jsoup.clean(introduction, Whitelist.none()).replaceAll("&quot", "");
		if (introduction.length()>100){
			introduction=introduction.substring(0, 100)+"...";
		}
		out.put("introduction", introduction);
		
		// 终生会员服务 标志判断
		Boolean lifeFlag = crmCompanySvrService.validatePeriod(companyId, CrmCompanySvrService.LIFE_CODE);
		out.put("lifeFlag", lifeFlag);
		
		// 帐号信息获取
		CompanyAccount account=companyAccountDao.queryAdminAccountByCompanyId(companyId);
		out.put("account", account);
		
		// 普会访问 判断 访问页面的公司 是否高会(再生铜、简版再生通)已过期
		if(company.getMembershipCode()!=CrmSvrService.PT_CODE){
			out.put("isVIP", 1);
			//品牌通判断(金牌、银牌、钻石)
			if(CrmSvrService.PPT_GOLD_CODE.equals(company.getMembershipCode())){
				out.put("isGOLD",1);
			}
			if(CrmSvrService.PPT_SILVER_CODE.equals(company.getMembershipCode())){
				out.put("isSILVER",1);
			}
			if(CrmSvrService.PPT_DIAMOND_CODE.equals(company.getMembershipCode())){
				out.put("isDIAMOND",1);
			}
		}
	}

	public Object queryDataByColumnId(String columnId, Integer companyId) {
		// TODO 待改进
//		do {
//			if ("all_offer_index".equals(columnId)) {
//				return productsDAO.queryProductsWithPicByCompany(companyId, 8);
//			}
//			if ("contact_index".equals(columnId)) {
//				return companyAccountDao.queryAdminAccountByCompanyId(companyId);
//			}
//			if ("newslist_index".equals(columnId)) {
//				PageDto<EsiteNewsDo> page = new PageDto<EsiteNewsDo>();
//				page.setPageSize(10); // 读取数量需确定
//				page.setStartIndex(0);
//				page.setSort("gmt_created");
//				page.setDir("desc");
//				return esiteNewsDao.queryNewsByCompany(companyId, page);
//			}
//			if ("category_nav".equals(columnId)) {
//				ProductsSeriesDO productsSeriesDO = new ProductsSeriesDO();
//				productsSeriesDO.setCompanyId(companyId);
//				return productsSeriesDAO.queryProductsSeries(productsSeriesDO);
//			}
//			if ("contact_side".equals(columnId)) {
//				return companyAccountDao.queryAdminAccountByCompanyId(companyId);
//			}
//			if ("friend_link".equals(columnId)) {
//				return esiteFriendLinkDao.queryFriendLinkByCompany(companyId,
//						10);
//			}
//		} while (false);

		return null;
	}

	final static String ZZ91_DOMAIN = "zz91.com";

	@Override
	public Integer initCompanyIdFromDomain(String domain) {
		Assert.notNull(domain, "the domain can not be null");
		String d=domain;
		if(domain.endsWith("."+ZZ91_DOMAIN)){
			d=domain.substring(0, domain.lastIndexOf(".zz91.com"));
		}
		if(domain.endsWith(".zz91.net")){
			d=domain.substring(0, domain.lastIndexOf(".zz91.net"));
		}
		if(domain.endsWith(".zz9l.com")){
			d=domain.substring(0, domain.lastIndexOf(".zz9l.com"));
		}
		return getCompanyIdByDomain(d);
//		return companyDAO.queryIdByDomain(d);
	}

	public static void main(String[] args) {
		//String domain = "www.caiban.net";
		//System.out.println(domain.substring(0, domain.lastIndexOf(".zz91.com")));
	}

	@Override
	public void initServerAddress(String serverName, int port,
			String contextPath, Map<String, Object> out) {
		String esiteAddress = null;
		if (port == 80) {
			esiteAddress = PROTOCOL + serverName + contextPath;
		} else {
			esiteAddress = PROTOCOL + serverName + ":" + port + contextPath;
		}
		out.put("esiteAddress", esiteAddress);
	}

	@Override
	public void initDomain() {
		List<Map<String, Object>> list = companyDAO.queryAllDomain();
		Map<String, Integer> result = new HashMap<String, Integer>();
		for (Map<String, Object> obj : list) {
			if (""+obj.get("domain_zz91")!="null"&&StringUtils.isNotEmpty(""+obj.get("domain_zz91"))&&StringUtils.isNumber(""+obj.get("id"))) {
				result.put((""+obj.get("domain_zz91")).toLowerCase(), Integer.valueOf(""+obj.get("id")));
			}
		}
		MemcachedUtils.getInstance().getClient().set(MEMCACHE_KEY_ZZ91DOMAIN, 0, result);
	}

	@Override
	public Integer getCompanyIdByDomain(String domain) {
		Object obj = MemcachedUtils.getInstance().getClient().get(MEMCACHE_KEY_ZZ91DOMAIN);
		if (obj!=null) {
			@SuppressWarnings("unchecked")
			Map<String, Integer> result = (Map<String, Integer>) obj;
			return result.get(domain);
		}
		return null;
	}
	
	@Override
	public boolean isExistXML(Integer cid, String fileName){
		return true;
	}
	
//	@Override
//	//XmlFile
//	public SilianXML getAllSilianXML(Integer cid, String fileName){
//		SilianXML silianXML = new SilianXML();
//		MongoDBManager m = new MongoDBManager();
//		DBObject d = m.find("esite_xml","3","silian999.xml");
//		silianXML.setCid(d.get("cid").toString());
//		silianXML.setFileName(d.get("fileNmae").toString());
//		silianXML.setDomain(d.get("domain").toString());
//		@SuppressWarnings("unchecked")
//		List<String> l = (List<String>) d.get("urlList");
//		silianXML.setUrlList(l);
//		return silianXML;
//	}

}

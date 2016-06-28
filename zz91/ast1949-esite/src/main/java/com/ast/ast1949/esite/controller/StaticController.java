package com.ast.ast1949.esite.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.domain.yuanliao.YuanliaoPic;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmSvrService;
import com.ast.ast1949.service.company.EsiteService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.company.SeoTemplatesService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.service.market.MarketCompanyService;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductAddPropertiesService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsSeriesService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.sample.SampleService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.service.yuanliao.YuanLiaoService;
import com.ast.ast1949.service.yuanliao.YuanliaoPicService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.PageCacheUtil;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.SensitiveUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;
import com.zz91.util.velocity.AddressTool;

/**
 * @author root
 *
 */
@Controller
public class StaticController extends BaseController{
	
	@Resource
	private ProductsService productsService;
	@Resource
	private EsiteService esiteService;
	@Resource
	private ProductsPicService productsPicService;
	@Resource
	private ProductsSeriesService productsSeriesService;
	@Resource
	private CompanyService companyService;
	@Resource
	private PriceService priceService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private SeoTemplatesService seoTemplatesService;
	@Resource
    private ProductAddPropertiesService productAddPropertiesService;
	@Resource
    private CategoryService categoryService;
	@Resource 
	PhoneLogService phoneLogService;
	@Resource 
	PhoneService phoneService;
	@Resource
	private SampleService sampleService;
	@Resource
	private MarketCompanyService marketCompanyService;
	@Resource
	private YuanLiaoService yuanliaoService;
	@Resource
	private YuanliaoPicService yuanliaoPicService;
	
	
	@RequestMapping
	public ModelAndView products(HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> out, Integer id) throws Exception{
		Integer cid = esiteService.initCompanyIdFromDomain(request.getServerName());
		
		if (cid == null) {
			return bad404request(response);
//			return new ModelAndView("redirect:"+AddressTool.getAddress("front"));
		}
		 do {
				// 判断400客户 一元来电宝和五元来电宝
				if (!crmCompanySvrService.validatePeriod(cid,CrmCompanySvrService.LDB_CODE) &&  !crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.LDB_FIVE_CODE)) {
					break;
				}
				// 获取400客户
				Phone phone = phoneService.queryByCompanyId(cid);
				if (phone == null || StringUtils.isEmpty(phone.getTel())) {
					break;
				}
				// 获取余额
				String balance = phoneLogService.countBalance(phone);
				// 判断400客户是否欠费
				if (Float.valueOf(balance) > 0.0) {
					return new ModelAndView("redirect:http://www.zz91.com/ppc/index" + cid + ".htm");
				}

			} while (false);
		// 新版seo判断
		if(seoTemplatesService.validate(cid)){
			return new ModelAndView("redirect:http://"+request.getServerName()+"zz91.net");
		}
		
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
			//统计高会的流量
			//vipPv 代表是统计高会流量的标志 
			if(!"10051000".equals(company.getMembershipCode())&&!"10051003".equals(company.getMembershipCode()))
			{
				out.put("companyId", cid);
				out.put("vipPv", 1);
			}
			out.put("tags", tags);
			out.put("pagePrice", priceService.pagePriceBySearchEngine(tags,null,pagePrice));
		}while(false);
		
		esiteService.initBaseConfig(cid, null, out);
		
		do {
			// 获取供求信息 step1
			if (id == null) {
//				out.put(AstConst.ERROR_TEXT, "您的地址有错误,请检查一下");
				break;
			}
			// 检索本供求所属客户的的供求类别名
			out.put("productsSeriesDO", productsSeriesService.queryProductsSeriesByProudctId(id));

			ProductsDto productDetails=productsService.queryProductsDetailsById(id);
			
			//增加属性显示
			String isDel= "0";
            List<ProductAddProperties> productAddProperties = productAddPropertiesService.queryByProductId(id, isDel);
            out.put("productAddProperties", productAddProperties);

            //获取主类别,辅助类别code
            out.put("manufactureList", categoryService.queryCategoriesByPreCode("1011"));

			if (productDetails == null) {
				break;
			}
			
			// 敏感词过滤
			Map<String, Object> sensitiveMap = SensitiveUtils.getSensitiveFilter(productDetails.getProducts().getTitle());
			@SuppressWarnings("unchecked")
			Set<String> sensitiveSet = (Set<String>) sensitiveMap.get("sensitiveSet");
			if(sensitiveSet.size()>0){
				productsService.updateProductsCheckStatusByAdmin(ProductsService.CHECK_FAILD, "您发布的供求含有敏感词“" + sensitiveSet.toString() + "”，不符合我们网站的审核要求，请修改信息", "esite-check", productDetails.getProducts().getId());
				out.put(AstConst.ERROR_TEXT, "对不起，您查看的供求信息审核没有通过！");
				break;
			}

			// 查看该用户是否有商铺服务
			Boolean isSP = crmCompanySvrService.validatePeriod(cid, CrmSvrService.CRM_SP);
			if(!isSP){
				// 判断信息是否正常，（审核通过，未过期，没有暂停发布）
				if (productDetails.getProducts().getCheckStatus() == null|| !"1".equals(productDetails.getProducts().getCheckStatus())) {
					break;
				}
			}

			// 检查暂停发布情况
			// XXX 数据类型不正确，数据库中是字符型
			if (productDetails.getProducts().getIsPause() != null
					&& productDetails.getProducts().getIsPause().equals("1")) {
//				out.put(AstConst.ERROR_TEXT, "对不起，您查看的供求信息现在已经停止发布了！");
				break;
			}

			//去掉属性空格
            if (productDetails.getProducts().getAppearance() != null) {
                productDetails.getProducts().setAppearance(productDetails.getProducts().getAppearance().trim());
            }
            if (productDetails.getProducts().getColor() != null) {
                productDetails.getProducts().setColor(productDetails.getProducts().getColor().trim());
            }
            if (productDetails.getProducts().getImpurity() != null) {
                productDetails.getProducts().setImpurity(productDetails.getProducts().getImpurity().trim());
            }
            if (productDetails.getProducts().getManufacture() != null) {
                if(productDetails.getProducts().getManufacture().length() == 1) {
                    productDetails.getProducts().setManufacture("");
                } else {
                    productDetails.getProducts().setManufacture(productDetails.getProducts().getManufacture().trim());
                }
            }
            if (productDetails.getProducts().getOrigin() != null) {
                productDetails.getProducts().setOrigin(productDetails.getProducts().getOrigin().trim());
            }
            if (productDetails.getProducts().getSource() != null) {
                productDetails.getProducts().setSource(productDetails.getProducts().getSource().trim());
            }
            if (productDetails.getProducts().getSpecification() != null) {
                productDetails.getProducts().setSpecification(productDetails.getProducts().getSpecification().trim());
            }
            if (productDetails.getProducts().getUseful() != null) {
                productDetails.getProducts().setUseful(productDetails.getProducts().getUseful().trim());
            }
            if (productDetails.getProducts().getLocation() != null) {
                productDetails.getProducts().setLocation(productDetails.getProducts().getLocation().trim());
            }
			//根据主类别不同显示不同的属性
            String typeCode = productDetails.getProducts().getCategoryProductsMainCode().substring(0, 4);
            out.put("typeCode", typeCode);
            
			// 样品信息
			Sample sample = sampleService.queryByIdOrProductId(null, productDetails.getProducts().getId());
			if (sample!=null&&sample.getIsDel()==0) {
				productDetails.setSample(sample);
				// 发货地址
				String areaCode = StringUtils.isNotEmpty(sample.getAreaCode()) ? sample.getAreaCode() : "";
				if (areaCode.length() == 12) {
					out.put("sampleAreaLabel", CategoryFacade.getInstance().getValue(areaCode));
				}
				if (areaCode.length() == 16) {
					out.put("sampleAreaLabel", CategoryFacade.getInstance().getValue(areaCode.substring(0, 12)) + "  "
							+ CategoryFacade.getInstance().getValue(areaCode));
				}
			}
            
			out.put("productDetails", productDetails);

			// 获取供求图片信息 step3
			out.put("picList", productsPicService.queryProductPicInfoByProductsIdForFront(id));

			//标签
			String tags = "";
			if (productDetails.getProducts().getTags() != null) {
				tags += productDetails.getProducts().getTags();
			}
			if (productDetails.getProducts().getTagsAdmin() != null) {
				tags += "," + productDetails.getProducts().getTagsAdmin();
			}
			out.put("tagsInfoList", TagsUtils.getInstance().encodeTags(tags, "utf-8"));
			
			// 获取同公司，同类其他供求信息 
			List<ProductsDto> similarProducts= productsService.queryProductsOfCompany(cid, 5);
			out.put("similarProducts", similarProducts);

			// 分析类别导航 step8
			Map<String, String> navigationCategoryMap = new LinkedHashMap<String, String>();
			for (int i = 4; i <= productDetails.getProducts().getCategoryProductsMainCode()
					.length(); i = i + 4) {
				String code = productDetails.getProducts().getCategoryProductsMainCode()
						.substring(0, i);
				navigationCategoryMap.put(code, CategoryProductsFacade
						.getInstance().getValue(code));
			}
			out.put("navigationCategoryMap", navigationCategoryMap);

			// 初始化留言要用的信息 step9
			Inquiry inquiry = new Inquiry();
			inquiry.setTitle(productDetails.getProducts().getTitle());
			inquiry.setBeInquiredId(id);
			inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_DEFAULT);
			inquiry.setReceiverAccount(productDetails.getProducts().getAccount());
			out.put("inquiry", inquiry);


			// SEO step11
//			StringBuffer sb = new StringBuffer();
//			sb.append(productDetails.getProductsTypeLabel());
//			sb.append(productDetails.getProducts().getTitle());
//			String bigCategory = null;

//			if (productDetails.getProducts().getCategoryProductsMainCode() != null
//					&& productDetails.getProducts().getCategoryProductsMainCode().length() > 4) {
//				bigCategory = navigationCategoryMap.get(productDetails
//						.getProducts().getCategoryProductsMainCode().subSequence(0, 4));
//			}

			esiteService.initServerAddress(request.getServerName(), request
					.getServerPort(), request.getContextPath(), out);
			initMyPosition("zxgq", "最新供求", out);
			
			out.put("resourceUrl", AddressTool.getAddress("resource"));
			
			// 产业带入口
			List<Market> marketList = marketCompanyService.queryMarketByCompanyId(cid);
			if (marketList!=null&& marketList.size()>0) {
				out.put("market", marketList.get(marketList.size()-1));
			}
			
			
			//SEO
			out.put("seoflag", "esite");
			Company company=(Company)out.get("company");
			
			String details ="";
			if(StringUtils.isNotEmpty(productDetails.getProducts().getDetails())){
					details = Jsoup.clean(productDetails.getProducts().getDetails(), Whitelist.none());
				if (details.length()>60){
					details=details.substring(0, 60)+"...";
				}
			}
			
			String type = "";
			if (productDetails.getProducts().getProductsTypeCode().equals("10331000")){
				type="供应";
			}
			if (productDetails.getProducts().getProductsTypeCode().equals("10331001")){
				type="求购";
			}
			if (productDetails.getProducts().getProductsTypeCode().equals("10331002")){
				type="合作";
			}
			
			String key="";
			if (StringUtils.isNotEmpty(productDetails.getProducts().getTags())){
				key=productDetails.getProducts().getTags();
			}else {
				key=productDetails.getProducts().getTitle();
			}
			String[] titleParam={type+productDetails.getProducts().getTitle(),company.getName()};
			String[] keywords={key};
			String[] description={company.getName(),details,request.getServerName()};
			SeoUtil.getInstance().buildSeo("product", titleParam, keywords, description, out);
			return new ModelAndView();
		} while (true);
		
		return new ModelAndView("redirect:"+AddressTool.getAddress("front"));
	}
	
	//CACHE_TIME
	final static int INFO_CACHE_TIME = 60;

	/**
	 * 放入Cache中
	 * @param info
	 * @return key
	 */
	String sendInfoToCache(String info) {
		if (StringUtils.isNotEmpty(info)) {
			String key = UUID.randomUUID().toString();
			MemcachedUtils.getInstance().getClient()
					.set(key, INFO_CACHE_TIME, info);
			return key;
		}
		return null;
	}
	
	private ModelAndView bad404request(HttpServletResponse response) {
		PageCacheUtil.setStatus(response, 404);
		response.setHeader("Location", "http://www.zz91.com/404.html");
		response.setHeader("Connection", "close");
		return null;
		//		return new ModelAndView("redirect:" + AddressTool.getAddress("front"));
	}
	
	void initMyPosition(String code, String title, Map<String, Object> out) {
		Map<String, Object> myposition = new HashMap<String, Object>();
		myposition.put("code", code);
		myposition.put("title", title);
		out.put("myposition", myposition);
	}
	
	@RequestMapping
	public ModelAndView yuanliao(HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> out, Integer id){
		Integer cid = esiteService.initCompanyIdFromDomain(request.getServerName());
		if (cid == null) {
			return bad404request(response);
		}
		// 新版seo判断
		if(seoTemplatesService.validate(cid)){
			return new ModelAndView("redirect:http://"+request.getServerName()+"zz91.net");
		}
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
			//统计高会的流量
			//vipPv 代表是统计高会流量的标志 
			if(!"10051000".equals(company.getMembershipCode())&&!"10051003".equals(company.getMembershipCode()))
			{
				out.put("companyId", cid);
				out.put("vipPv", 1);
			}
			out.put("tags", tags);
			out.put("pagePrice", priceService.pagePriceBySearchEngine(tags,null,pagePrice));
		}while(false);
		esiteService.initBaseConfig(cid, null, out);
		do{
			if(id == null){
				break;
			}
			Yuanliao yuanliao = yuanliaoService.queryYuanliaoById(id);
			if(yuanliao == null){
				break;
			}
			YuanliaoDto yld = new YuanliaoDto();
			// 查看该用户是否有商铺服务
//			Boolean isSP = crmCompanySvrService.validatePeriod(cid, CrmSvrService.CRM_SP);
//			if(!isSP){
//				// 判断信息是否正常，（审核通过，未过期，没有暂停发布）
//				if (yuanliao.getCheckStatus() == null|| !"1".equals(yuanliao.getCheckStatus())) {
//					break;
//				}
//			}
			yld.setYuanliao(yuanliao);
			//货源所在地
			String address = null;
			if(StringUtils.isNotEmpty(yuanliao.getLocation())){
				if(yuanliao.getLocation().length()>12){
					address = CategoryFacade.getInstance().getValue(yuanliao.getLocation().substring(0, 12)) + CategoryFacade.getInstance().getValue(yuanliao.getLocation().substring(0, 16));
				}else if(yuanliao.getLocation().length()>8 && yuanliao.getLocation().length()<13){
					address = CategoryFacade.getInstance().getValue(yuanliao.getLocation().substring(0, 8)) + CategoryFacade.getInstance().getValue(yuanliao.getLocation().substring(0, 12));
				}else{
					address = CategoryFacade.getInstance().getValue(yuanliao.getLocation().substring(0, 8));
				}
			}
			yld.setAddress(address);
			//厂家（产地）
			if(StringUtils.isNotEmpty(yuanliao.getCategoryMainDesc())){
				yld.setCategoryMainLabel(YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryMainDesc()));
			}else{
				yld.setCategoryMainLabel(yuanliao.getCategoryAssistDesc());
			}
			//类型
			if(StringUtils.isNotEmpty(yuanliao.getType())){
				yld.setTypeLabel(CategoryFacade.getInstance().getValue(yuanliao.getType()));
			}
			//加工级别名称
			yld.setProcessLabel(getLabel(yuanliao.getProcessLevel()));
			//用途级别名称
			yld.setUsefulLabel(getLabel(yuanliao.getUsefulLevel()));
			//特性级别名称
			yld.setCharaLabel(getLabel(yuanliao.getCharaLevel()));
			//发货时间
			if(yuanliao.getSendTime() != null){
				yld.setGmtSend(DateUtil.toString(DateUtil.getDateAfterDays(yuanliao.getRefreshTime(), yuanliao.getSendTime()), "yyyy-MM-dd"));
			}
			//该原料的图片集合
			YuanliaoPic yuanliaoPic = new YuanliaoPic();
			yuanliaoPic.setYuanliaoId(id);
			yuanliao.setIsDel(0);
			yuanliao.setCheckStatus(1);
			List<YuanliaoPic> list = yuanliaoPicService.queryYuanliaoPicByYuanliaoId(yuanliaoPic, 5);
			out.put("list", list);
			
			out.put("yld", yld);
			//SEO
			out.put("seoflag", "esite");
			Company company=(Company)out.get("company");
			
			String details ="";
			if(StringUtils.isNotEmpty(yld.getYuanliao().getDescription())){
					details = Jsoup.clean(yld.getYuanliao().getDescription(), Whitelist.none());
				if (details.length()>60){
					details=details.substring(0, 60)+"...";
				}
			}
			
			String type = "";
			if (yld.getYuanliao().getYuanliaoTypeCode().equals("10331000")){
				type="供应";
			}
			if (yld.getYuanliao().getYuanliaoTypeCode().equals("10331001")){
				type="求购";
			}
			if (yld.getYuanliao().getYuanliaoTypeCode().equals("10331002")){
				type="合作";
			}
			
			String key="";
			if (StringUtils.isNotEmpty(yld.getYuanliao().getTags())){
				key=yld.getYuanliao().getTags();
			}else {
				key=yld.getYuanliao().getTitle();
			}
			String[] titleParam={type+yld.getYuanliao().getTitle(),company.getName()};
			String[] keywords={key};
			String[] description={company.getName(),details,request.getServerName()};
			SeoUtil.getInstance().buildSeo("product", titleParam, keywords, description, out);
			return new ModelAndView();
		}while(true);
		//return new ModelAndView("redirect:"+AddressTool.getAddress("front"));
		return new ModelAndView();
	}
	public String getLabel(String code){
		String label = "";
		if(StringUtils.isNotEmpty(code)){
			for(String s : code.split(",")){
				if(StringUtils.isNotEmpty(label)){
					label = label + " " + CategoryFacade.getInstance().getValue(s);
				}else{
					label = CategoryFacade.getInstance().getValue(s);
				}
			}
		}
		return label;
		
	}
}

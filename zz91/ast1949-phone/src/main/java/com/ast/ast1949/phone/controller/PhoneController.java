package com.ast.ast1949.phone.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.EsiteNewsDo;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.EsiteNewsService;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.CNToHexUtil;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;

@Controller
public class PhoneController extends BaseController {

	@Resource
	private CompanyService companyService;
	@Resource
	private PhoneService phoneService;
	@Resource
	private ProductsService productsService;
	@Resource
	private EsiteNewsService esiteNewsService;
	@Resource
	private ProductsPicService productsPicService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;

	/**
	 * 初始化
	 * @param out
	 * @param cid
	 */
	private void initOut(Map<String, Object>out,Integer cid){
		// 公司信息
		CompanyDto dto = companyService.queryCompanyDetailById(cid);
		out.put("company", dto);
		// 400号码信息
		Phone phone = phoneService.queryByCompanyId(cid);
		out.put("phone",phone);

		// 可能感兴趣的供求 8 条
		if(phone!=null){
			PageDto<ProductsDto> pPage = new PageDto<ProductsDto>();
			pPage.setPageSize(8);
			ProductsDO product = new ProductsDO();
			product.setTitle(phone.getKeywords());
			out.put("interestList", productsService.pageLHProductsBySearchEngine(product, null, true, pPage).getRecords());
		}
	}
	
	/**
	 * 400推广首页
	 * @param out
	 * @param companyId
	 */
	@RequestMapping
	public void index(Map<String, Object> out,Integer companyId) {
		initOut(out,companyId);
		// 最新供求 3 条
		out.put("productList", productsService.queryProductsOfCompany(companyId, 3));

		// 公司动态 7 条
		PageDto<EsiteNewsDo> page = new PageDto<EsiteNewsDo>();
		page.setPageSize(7);
		out.put("newsList", esiteNewsService.pageNewsByCompany(companyId, page).getRecords());
		// seo 
		ProductsDO products=productsService.queryProductsByCid(companyId);
		CompanyDto dto =  (CompanyDto) out.get("company");
		Company company = dto.getCompany();
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
			titleString="ZZ91来电宝";
		}
		// 组装titie
		title = titleString+"_"+company.getName();
		Phone phone = (Phone) out.get("phone");
		if(crmCompanySvrService.validatePeriod(companyId, CrmCompanySvrService.LDB_CODE)&&phone!=null){
			title = title+"，"+phone.getTel();
		}
		// 组装keywords
		String keywords=key;
		if(StringUtils.isNotEmpty(type)){
			keywords = type+keywords;
		}
		// 主营业务
		String business=company.getBusiness();
		if (business.length()>60){
			business=business.substring(0, 60)+"...";
		}
		SeoUtil.getInstance().buildSeo("index",new String[]{title}, new String[]{keywords}, new String[]{company.getName(),business},out);
	}

	/**
	 * 公司简介
	 * @param out
	 * @param companyId
	 */
	@RequestMapping
	public void gsjj(Map<String, Object> out,Integer companyId){
		initOut(out,companyId);
		CompanyDto dto =  (CompanyDto) out.get("company");
		Company company = dto.getCompany();
		CompanyAccount account = dto.getAccount();
		String[] keywords={company.getName(),"公司介绍"};
		// 公司介绍
		String introduction=company.getIntroduction();
		introduction=Jsoup.clean(introduction, Whitelist.none()).replaceAll("&quot", "");
		if (introduction.length()>100){
			introduction=introduction.substring(0, 100)+"...";
		}
		String[] description={introduction};
		Phone phone = (Phone) out.get("phone");
		if(crmCompanySvrService.validatePeriod(companyId, CrmCompanySvrService.LDB_CODE)&&phone!=null){
			String[] titleParam={company.getName(),"公司介绍",account.getContact(),"，"+phone.getTel()}; 
			SeoUtil.getInstance().buildSeo("gsjj",titleParam, keywords, description,out);
		}else{
			String[] titleParam={company.getName(),"公司介绍",account.getContact(),""};
			SeoUtil.getInstance().buildSeo("gsjj",titleParam, keywords, description,out);
		}
	}

	/**
	 * 公司动态
	 * @param out
	 * @param companyId
	 */
	@RequestMapping
	public void gsdt(Map<String, Object> out,Integer companyId,PageDto<EsiteNewsDo> page) {
		initOut(out,companyId);
		// 公司动态 7 条
		page.setPageSize(7);
		out.put("page", esiteNewsService.pageNewsByCompany(companyId, page));
		// seo
		CompanyDto dto =  (CompanyDto) out.get("company");
		Company company = dto.getCompany();
		CompanyAccount account = dto.getAccount();
		String[] keywords={company.getName(),"公司动态"};
		// 公司介绍
		String introduction=company.getIntroduction();
		introduction=Jsoup.clean(introduction, Whitelist.none()).replaceAll("&quot", "");
		if (introduction.length()>100){
			introduction=introduction.substring(0, 100)+"...";
		}
		String[] description={introduction};
		Integer size;
		size=page.getStartIndex()/page.getPageSize()+1;
		Phone phone = (Phone) out.get("phone");
		if(crmCompanySvrService.validatePeriod(companyId, CrmCompanySvrService.LDB_CODE)&&phone!=null){
			String[] titleParam={company.getName(),"公司动态","第"+size+"页",account.getContact(),"，"+phone.getTel()};
			SeoUtil.getInstance().buildSeo("gsdt", titleParam, keywords, description, out);
			
		}else{
			String[] titleParam={company.getName(),"公司动态","第"+size+"页",account.getContact(),""};
			SeoUtil.getInstance().buildSeo("gsdt", titleParam, keywords, description, out);
		}
	}

	/**
	 * 公司动态 详细页面
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView newsdetail(Map<String, Object> out,Integer id){
		do {
			EsiteNewsDo esiteNewsDo =  esiteNewsService.queryOneNewsById(id);
			if(esiteNewsDo==null||esiteNewsDo.getCompanyId()==null){
				break;
			}
			out.put("esiteNews", esiteNewsDo);
			out.put("lastEsiteNews", esiteNewsService.queryLastNewsById(esiteNewsDo.getId(),esiteNewsDo.getCompanyId()));// 上一条动态
			out.put("nextEsiteNews", esiteNewsService.queryNextNewsById(esiteNewsDo.getId(),esiteNewsDo.getCompanyId())); // 下一条动态
			initOut(out,esiteNewsDo.getCompanyId());
			// seo
			String str =esiteNewsDo.getContent().trim();
			str = StringUtils.removeHTML(str);
			if(str.length()>200){
				str = str.substring(0, 200);
			}
			String[] description={str};
			String[] titleParam={esiteNewsDo.getTitle()};
			SeoUtil.getInstance().buildSeo("newsdetail", titleParam, null, description, out);
			
			return null;
		} while (false);
		return new ModelAndView("redirect:http://www.zz91.com");
	}

	/**
	 * 最新供求 
	 * @param out
	 * @param companyId
	 */
	@RequestMapping
	public void zxgq(Map<String, Object> out,Integer companyId,PageDto<ProductsDto> page){
		initOut(out,companyId);
		page.setPageSize(7);
		page = productsService.pageProductsWithPicByCompanyEsiteWithDetails(companyId, null, null, page);
		out.put("page", page);
		// seo
		Integer size;
		size=page.getStartIndex()/page.getPageSize()+1;
		CompanyDto company = (CompanyDto)out.get("company");
		CompanyAccount account=company.getAccount();
		String introduction=(String) out.get("introduction");
		String[] keywords={company.getCompany().getName(),"最新供求"};
		String[] description={introduction};
		do{
			Phone phone = (Phone) out.get("phone");
			if(crmCompanySvrService.validatePeriod(companyId, CrmCompanySvrService.LDB_CODE)&&phone!=null){
				String[] titleParam={company.getCompany().getName(),"最新供求","第"+size+"页",account.getContact(),"，"+phone.getTel()};
				SeoUtil.getInstance().buildSeo("zxgq", titleParam, keywords, description, out);
			}else {
				String[] titleParam={company.getCompany().getName(),"最新供求","第"+size+"页",account.getContact(),""};
				SeoUtil.getInstance().buildSeo("zxgq", titleParam, keywords, description, out);
			}
		}while(false);
	}

	/**
	 * 最新供求详细页面
	 * @param out
	 * @param id
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping
	public ModelAndView productdetail(Map<String, Object> out, Integer id,HttpServletRequest request) throws ParseException {
		do {
			if(id==null){
				break;
			}
			ProductsDO product = productsService.queryProductsById(id);
			if(product==null||product.getCompanyId()==null){
				break;
			}
			out.put("products", product);
			// 检查过期情况
			int interval = DateUtil.getIntervalDays(new Date(),	product.getExpireTime());
			if (interval > 0) {
				out.put("expiredFlag", "1");
			}
			// 标签
			Map<String, String> map = TagsUtils.getInstance().encodeTags(product.getTags(), "utf-8");
			for(String key:map.keySet()){
				map.put(key, CNToHexUtil.getInstance().encode(key));
			}
			out.put("tagsInfoList",	map);
			// 获取供求图片信息
			List<ProductsPicDO> picList = productsPicService.queryProductPicInfoByProductsIdForFront(id);
			out.put("picList", picList);
			// 相关图片供求
			PageDto<ProductsDto> picPage = new PageDto<ProductsDto>();
			picPage.setPageSize(5);
			ProductsDO picProduct = new ProductsDO();
			picProduct.setTitle(CategoryProductsFacade.getInstance().getValue(product.getCategoryProductsMainCode()));
			out.put("similarPicList", productsService.pageLHProductsBySearchEngine(product, null, true, picPage).getRecords());
			// 相关供求
			PageDto<ProductsDto> cPage = new PageDto<ProductsDto>();
			cPage.setPageSize(12);
			ProductsDO cProduct = new ProductsDO();
			if (ProductsService.PRODUCTS_TYPE_OFFER.equals(product.getProductsTypeCode())) {
				cProduct.setProductsTypeCode(ProductsService.PRODUCTS_TYPE_BUY);
			}else if(ProductsService.PRODUCTS_TYPE_BUY.equals(product.getProductsTypeCode())){
				cProduct.setProductsTypeCode(ProductsService.PRODUCTS_TYPE_OFFER);
			}
			out.put("similarList", productsService.pageLHProductsBySearchEngine(cProduct, null, true, cPage).getRecords());
			
			// 初始化数据
			initOut(out,product.getCompanyId());
			
			//SEO
			CompanyDto dto=(CompanyDto)out.get("company");
			String details ="";
			if(StringUtils.isNotEmpty(product.getDetails())){
					details = Jsoup.clean(product.getDetails(), Whitelist.none());
				if (details.length()>60){
					details=details.substring(0, 60)+"...";
				}
			}
			
			String type = "";
			if (product.getProductsTypeCode().equals("10331000")){
				type="供应";
			}
			if (product.getProductsTypeCode().equals("10331001")){
				type="求购";
			}
			if (product.getProductsTypeCode().equals("10331002")){
				type="合作";
			}
			
			String key="";
			if (StringUtils.isNotEmpty(product.getTags())){
				key=product.getTags();
			}else {
				key=product.getTitle();
			}
			String[] titleParam={type+product.getTitle(),dto.getCompany().getName()};
			String[] keywords={key};
			String[] description={dto.getCompany().getName(),details,request.getServerName()};
			SeoUtil.getInstance().buildSeo("product", titleParam, keywords, description, out);
			return null;
		} while (false);
		return new ModelAndView("redirect:http://www.zz91.com");
	}

}

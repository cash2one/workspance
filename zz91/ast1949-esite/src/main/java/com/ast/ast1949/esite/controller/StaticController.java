/**
 * 
 */
package com.ast.ast1949.esite.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmSvrService;
import com.ast.ast1949.service.company.EsiteService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.company.SeoTemplatesService;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductAddPropertiesService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsSeriesService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.site.CategoryService;
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
	
	@RequestMapping
	public ModelAndView products(HttpServletRequest request,
			Map<String, Object> out, Integer id){
		Integer cid = esiteService.initCompanyIdFromDomain(request.getServerName());
		
		if (cid == null) {
			return new ModelAndView("redirect:"+AddressTool.getAddress("front"));
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
			String isDel= "0";
            List<ProductAddProperties> productAddProperties = productAddPropertiesService.queryByProductId(id, isDel);
            out.put("productAddProperties", productAddProperties);

            //获取主类别,辅助类别code
            out.put("manufactureList", categoryService.queryCategoriesByPreCode("1011"));

            
//			ProductDetailsDTO productDetails = productDetailsService.queryProductDetailsByProductsId(productsId);
			if (productDetails == null) {
//				out.put(AstConst.ERROR_TEXT, "您的地址有错误,请检查一下");
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
					&& productDetails.getProducts().getIsPause()) {
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
			out.put("productDetails", productDetails);

			// 获取供求图片信息 step3
			out.put("picList", productsPicService
					.queryProductPicInfoByProductsId(id));

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
			return null;
		} while (true);
		
		return new ModelAndView("redirect:"+AddressTool.getAddress("front"));
	}
	
	void initMyPosition(String code, String title, Map<String, Object> out) {
		Map<String, Object> myposition = new HashMap<String, Object>();
		myposition.put("code", code);
		myposition.put("title", title);
		out.put("myposition", myposition);
	}
}

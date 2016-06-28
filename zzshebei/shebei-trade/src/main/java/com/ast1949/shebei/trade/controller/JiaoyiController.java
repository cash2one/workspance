
package com.ast1949.shebei.trade.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast1949.shebei.domain.CategoryProducts;
import com.ast1949.shebei.domain.Company;
import com.ast1949.shebei.domain.News;
import com.ast1949.shebei.domain.Products;
import com.ast1949.shebei.dto.CodeTypeDto;
import com.ast1949.shebei.dto.PageDto;
import com.ast1949.shebei.service.CategoryProductsService;
import com.ast1949.shebei.service.CompanyService;
import com.ast1949.shebei.service.NewsService;
import com.ast1949.shebei.service.ProductsService;
import com.zz91.util.seo.SeoUtil;

/**
 * 
 * @author 陈庆林
 * 2012-7-30 下午4:32:25
 */

@Controller
public class JiaoyiController extends BaseController {

	// 默认顶级供应信息类别
	private static final String SUPPLY_TOP_CATEGORY = "1000";

	@Resource
	private CategoryProductsService categoryProductsService;
	@Resource
	private ProductsService productsService;
	@Resource
	private CompanyService companyService;
	@Resource
	private NewsService newService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out, String categoryCode, Integer cid) {
		// 查询求购信息
		List<Products> listBuy = productsService.queryProductsByType(cid,
				(short) 1, 6);
		// 查询供应信息
		List<Products> listSupply = productsService.queryProductsByType(cid,
				(short) 0, 6);
		// 查询最新加入商家
		List<Company> newCompany = companyService.queryNewestCompany(6,
				categoryCode);
		// >最新二手设备行业资讯
		List<News> news = newService.queryNewsByCategoryAndType("1000",
				(short) 0, 6, (short) 0);
		// 最新报价
		List<News> prices = newService.queryNewsByCategoryAndType("1000",
				(short) 1, 6, (short) 0);
		// 查询出类别的最高节点
		List<CategoryProducts> allFanther = categoryProductsService
				.queryAllCategorys((short) 0, SUPPLY_TOP_CATEGORY);
		List<CodeTypeDto> allCode = new ArrayList<CodeTypeDto>();
		CodeTypeDto codeTypeDto = null;
		for (int i = 0; i < allFanther.size(); i++) {
			codeTypeDto = new CodeTypeDto();
			codeTypeDto.setCategoryProducts(allFanther.get(i));
			List<CategoryProducts> allSon = categoryProductsService
					.queryAllCategorys((short) 2, allFanther.get(i).getCode());
			codeTypeDto.setListProducts(allSon);
			allCode.add(codeTypeDto);
		}
		SeoUtil.getInstance().buildSeo("tradeIndex",null,null,null, out);
		out.put("allCode", allCode);
		out.put("listBuy", listBuy);
		out.put("listSupply", listSupply);
		out.put("newCompany", newCompany);
		out.put("news", news);
		out.put("prices", prices);
		return null;
	}
	
	@RequestMapping
	public ModelAndView list(HttpServletRequest request,Map<String, Object> out , String categoryCode,PageDto<Products> page,Integer pageNum,Integer cid){
		if (pageNum == null) {
			pageNum = 1;
		}
		page.setStart((pageNum - 1) * 25);
		page.setLimit(25);
		page = productsService.pageProducts(categoryCode, cid, (short)0, page);
		out.put("theCategoryCode", categoryCode);
		if(page.getRecords().size()>0){
			out.put("page", page);
			CategoryProducts codelabel  = categoryProductsService.queryCtypeByCode(categoryCode);
			String [] label = {codelabel.getLabel()};
			String [] arr = new String[]{label[0],pageNum.toString()};
			SeoUtil.getInstance().buildSeo("list", arr, label, label, out);
			out.put("label", codelabel.getLabel());
			return null;
		}else{
			return new ModelAndView("redirect:no_search"+categoryCode+".htm");	
		}
	}
	
	@RequestMapping
	public ModelAndView no_search(HttpServletRequest request,Map<String, Object> out ,String categoryCode){
		CategoryProducts codelabel  = categoryProductsService.queryCtypeByCode(categoryCode);
		String [] label = {codelabel.getLabel()};
		SeoUtil.getInstance().buildSeo("no_search", label, label, label, out);
		out.put("label", codelabel.getLabel());
		return null;
	}
}

/**
 * 
 * @author 陈庆林
 * 2012-7-27 下午7:40:23
 */
package com.ast1949.shebei.trade.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast1949.shebei.domain.CategoryProducts;
import com.ast1949.shebei.domain.Company;
import com.ast1949.shebei.domain.News;
import com.ast1949.shebei.domain.Products;
import com.ast1949.shebei.dto.PageDto;
import com.ast1949.shebei.service.CategoryProductsService;
import com.ast1949.shebei.service.CompanyService;
import com.ast1949.shebei.service.NewsService;
import com.ast1949.shebei.service.ProductsService;
import com.zz91.util.seo.SeoUtil;

@Controller
public class GongsiController extends BaseController {

	@Resource
	private CompanyService companyService;
	@Resource
	private ProductsService productsService;
	@Resource
	private NewsService newService;
	@Resource
	private CategoryProductsService categoryProductsService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out, PageDto<Company> page,
			String categoryCode, Integer pageNum, Integer cid) {
		// 查询公司信息
		if (pageNum == null) {
			pageNum = 1;
		}
		page.setStart((pageNum - 1) * 20);
		page = companyService.pageCompanys(page, categoryCode);
		// 查询求购信息
		List<Products> listBuy = productsService.queryProductsByType(cid,
				(short)1, 6);
		// 查询供应信息
		List<Products> listSupply = productsService.queryProductsByType(cid,
				(short)0, 6);
		// 查询最新加入商家
		List<Company> newCompany = companyService.queryNewestCompany(6,
				categoryCode);
		// >最新二手设备行业资讯
		List<News> news = newService.queryNewsByCategoryAndType("1000",
				(short)0, 6, (short)0);
		// 最新报价
		List<News> prices = newService.queryNewsByCategoryAndType("1000",
				(short)1, 6, (short)0);
		out.put("page", page);
		out.put("listBuy", listBuy);
		out.put("listSupply", listSupply);
		out.put("newCompany", newCompany);
		out.put("news", news);
		out.put("prices", prices);
		//SeoUtil.getInstance().buildSeo("index", null, null, null, out);
		SeoUtil.getInstance().buildSeo("index", new String[]{pageNum.toString()}, null, null, out);
		return null;
	}

	/**
	 * 查询公司详细信息
	 * 
	 * @param request
	 * @param out
	 * @param cid
	 * @return
	 */
	@RequestMapping
	public ModelAndView companyHome(HttpServletRequest request,
			Map<String, Object> out, Integer cid, String categoryCode) {
		Company company = companyService.queryCompanyById(cid);
		// 查询求购信息
		List<Products> listBuy = productsService.queryProductsByType(cid,
				(short)1, 6);
		// 查询供应信息
		List<Products> listSupply = productsService.queryProductsByType(cid,
				(short)0, 6);
		// 查询最新加入商家
		List<Company> newCompany = companyService.queryNewestCompany(10,
				categoryCode);
		//查询公司的类别
		CategoryProducts catePro =  categoryProductsService.queryCtypeByCode(company.getCategoryCode());
		String [] description =null;
		//seo
		if(company.getMainProductSupply()!=null){
			if( company.getMainProductSupply().length()>100){
				description = new String[]{company.getMainProductSupply().substring(0,100)};
			}else{
				description = new String[]{company.getMainProductSupply()};
			}
		}else if(company.getDetails()!=null){
			String tmp =Jsoup.clean(company.getDetails(), Whitelist.none());
			if( tmp.length()>100){
				description = new String[]{tmp.substring(0,100)};
			}else{
				description = new String[]{tmp};
			}
		} 
		//如果简介为空则显示如下内容
		if(company.getDetails()==null){
			StringBuffer sb = new StringBuffer();
			sb.append("公司名称是");
			sb.append(catePro.getLabel());
			sb.append("专业供应");
			sb.append(company.getMainProductSupply());
			sb.append("的公司，");
			sb.append("本着诚信为本的经营理念，愿与新老客商建立良好合作关系！");
			company.setDetails(sb.toString());
		}
		String [] titleOrKey ={catePro.getLabel(),company.getName()}; 
		
		SeoUtil.getInstance().buildSeo("companyHome", titleOrKey, titleOrKey, description , out);
		out.put("company", company);
		out.put("listBuy", listBuy);
		out.put("listSupply", listSupply);
		out.put("newCompany", newCompany);
		return null;
	}

	/**
	 * 查询供应信息
	 * 
	 * @param request
	 * @param out
	 * @param cid
	 * @return
	 */
	@RequestMapping
	public ModelAndView supply(HttpServletRequest request,
			Map<String, Object> out, Integer cid, String categoryCode,
			PageDto<Products> page,Integer pageNum) {
		
		if (pageNum == null) {
			pageNum = 1;
		}
		page.setStart((pageNum - 1) * 5);
		// 查询公司详细信息
		Company company = companyService.queryCompanyById(cid);
		// 查询相关信息
		List<Products> listSupply = productsService.queryRelatedProducts(
				categoryCode, cid, (short)0, 10);
		// 查询供应信息列表
		page.setLimit(5);
		page = productsService.pageProducts(categoryCode, cid, (short)0, page);
		//查询公司的类别
		CategoryProducts catePro =  categoryProductsService.queryCtypeByCode(company.getCategoryCode());
		//如果简介为空则显示如下内容
		if(company.getDetails()==null){
			StringBuffer sb = new StringBuffer();
			sb.append("公司名称是");
			sb.append(catePro.getLabel());
			sb.append("专业供应");
			sb.append(company.getMainProductSupply());
			sb.append("的公司，");
			sb.append("本着诚信为本的经营理念，愿与新老客商建立良好合作关系！");
			company.setDetails(sb.toString());
		}
		//seo
		String [] title = {catePro.getLabel(),pageNum.toString(),company.getName()};
		String [] key  = {catePro.getLabel()};
		String [] description = {company.getName(),catePro.getLabel()};
		SeoUtil.getInstance().buildSeo("supply",title,key,description,out);
		out.put("listSupply", listSupply);
		out.put("company", company);
		out.put("page", page);
		return null;
	}

	/**
	 * 查询求购信息
	 * 
	 * @param request
	 * @param out
	 * @param cid
	 * @return
	 */
	@RequestMapping
	public ModelAndView buy(HttpServletRequest request,
			Map<String, Object> out, Integer cid, String categoryCode,
			PageDto<Products> page,Integer pageNum) {
		if (pageNum == null) {
			pageNum = 1;
		}
		page.setStart((pageNum - 1) * 5);
		// 查询公司详细信息
		Company company = companyService.queryCompanyById(cid);
		// 查询求购信息
		List<Products> listBuy = productsService.queryRelatedProducts(categoryCode,
				cid,(short)0,10);
		// 查询供应信息列表
		page.setLimit(5);
		page = productsService.pageProducts(categoryCode, cid, (short)1, page);
		//查询公司的类别
		CategoryProducts catePro =  categoryProductsService.queryCtypeByCode(company.getCategoryCode());
		//如果简介为空则显示如下内容
		if(company.getDetails()==null){
			StringBuffer sb = new StringBuffer();
			sb.append("公司名称是");
			sb.append(catePro.getLabel());
			sb.append("专业供应");
			sb.append(company.getMainProductSupply());
			sb.append("的公司，");
			sb.append("本着诚信为本的经营理念，愿与新老客商建立良好合作关系！");
			company.setDetails(sb.toString());
		}
		//seo
		if(pageNum==null){
			pageNum = 1;
		}
		String [] title = {catePro.getLabel(),company.getName(),String.valueOf(pageNum)};
		String [] key  = {catePro.getLabel()};
		String [] description = {company.getName(),catePro.getLabel()};
		SeoUtil.getInstance().buildSeo("buy",title,key,description,out);
		out.put("listBuy", listBuy);
		out.put("company", company);
		out.put("page", page);
		return null;
	}

	/**
	 * 公司简介
	 * 
	 * @param request
	 * @param out
	 * @param cid
	 * @return
	 */
	@RequestMapping
	public ModelAndView gsjx(HttpServletRequest request,
			Map<String, Object> out, Integer cid, String categoryCode) {
		// 查询公司详细信息
		Company company = companyService.queryCompanyById(cid);
		// 查询最新加入商家
		List<Company> newCompany = companyService.queryNewestCompany(6,
				categoryCode);
		String tmp = null;
		if(company.getDetails()!=null){
		 tmp =Jsoup.clean(company.getDetails(), Whitelist.none());
		}
		if (tmp.length()>100){
			tmp.substring(0,100);
		}
		//如果简介为空则显示如下内容
		if(company.getDetails()==null){
			StringBuffer sb = new StringBuffer();
			CategoryProducts catePro =  categoryProductsService.queryCtypeByCode(company.getCategoryCode());
			sb.append("公司名称是");
			sb.append(catePro.getLabel());
			sb.append("专业供应");
			sb.append(company.getMainProductSupply());
			sb.append("的公司，");
			sb.append("本着诚信为本的经营理念，愿与新老客商建立良好合作关系！");
			company.setDetails(sb.toString());
		}
		String title [] = {company.getName(),tmp};
		String key [] = {company.getName()};
		String description [] = {tmp};
		SeoUtil.getInstance().buildSeo("gsjx",title,key,description,out);
		out.put("company", company);
		out.put("newCompany", newCompany);
		return null;
	}

	/**
	 * 查询公司联系信息
	 * 
	 * @param request
	 * @param out
	 * @param cid
	 * @return
	 */
	@RequestMapping
	public ModelAndView contact(HttpServletRequest request,
			Map<String, Object> out, Integer cid) {
		// 查找公司名称
		String name = companyService.queryNameById(cid);
		// 公司id
		Company company = new Company();
		company.setId(cid);
		// 查找联系方式
		Company contacts = companyService.queryContactById(cid);
		// 查找公司简介
		String details = companyService.queryDeatilsById(cid);
		String title [] = {name};
		//如果简介为空则显示如下内容
		if(details==null){
			StringBuffer sb = new StringBuffer();
			CategoryProducts codelabel  = categoryProductsService.queryCtypeByCode(contacts.getCategoryCode());
			sb.append("公司名称是");
			sb.append(codelabel.getLabel());
			sb.append("专业供应");
			sb.append(contacts.getMainProductSupply());
			sb.append("的公司，");
			sb.append("本着诚信为本的经营理念，愿与新老客商建立良好合作关系！");
			details = sb.toString();
		}
		SeoUtil.getInstance().buildSeo("contact",title,null,null,out);
		out.put("contants", contacts);
		out.put("companyName", name);
		out.put("details", details);
		out.put("company", company);
		return null;
	}

	/**
	 * 查询产品详细信息
	 * 
	 * @param request
	 * @param out
	 * @param pid
	 * @return
	 */
	@RequestMapping
	public ModelAndView pdetail(HttpServletRequest request,
			Map<String, Object> out, Integer pid, String categoryCode) {

		Products product = productsService.queryProductbyId(pid);
		String code=product.getCategoryCode().length()>8? product.getCategoryCode().substring(0, 8):product.getCategoryCode();
		CategoryProducts cate  = categoryProductsService.queryCtypeByCode(code);
		out.put("cate", cate);
		// 查询公司名字
		String name = companyService.queryNameById(product.getCid());
		Company company = new Company();
		company.setId(product.getCid());
		// 查询公司其他类型供求信息
		List<Products> otherProduct = productsService.queryOtherProducts(
				product.getCid(), categoryCode, (short)0, 10);
		// 查询相关产品信息
		List<Products> realtedProduct = productsService.queryRelatedProducts(
				categoryCode, product.getCid(), (short)0, 10);
				//seo
		String [] title = {product.getTitle(),name};
		String [] key  = {product.getTitle()};
		String [] description = null;
		if(product.getDetails()!=null){
			if(product.getDetails().length()>100){
				description = new String[]{name,product.getDetails().substring(0, 100)};
			}else{
				description = new String[]{name,product.getDetails()};
			}
		}else{
			description = new String[]{""};
		}
		SeoUtil.getInstance().buildSeo("pdetail",title,key,description,out);
		out.put("product", product);
		out.put("name", name);
		out.put("company", company);
		out.put("otherProduct", otherProduct);
		out.put("realtedProduct", realtedProduct);
		return null;
	}
	
}

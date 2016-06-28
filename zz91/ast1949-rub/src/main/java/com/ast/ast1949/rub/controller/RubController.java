package com.ast.ast1949.rub.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.domain.products.ProductsRub;
import com.ast.ast1949.domain.products.ProductsViewHistory;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsRubService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.products.ProductsViewHistoryService;
import com.ast.ast1949.util.CNToHexUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;

@Controller
public class RubController extends BaseController {

	@Resource
	private ProductsRubService productsRubService;
	@Resource
	private ProductsService productsService;
	@Resource
	private ProductsPicService productsPicService;
	@Resource
	private ProductsViewHistoryService productsViewHistoryService;


	@RequestMapping
	public ModelAndView index(Map<String, Object> out, PageDto<ProductsRub> page,ProductsRub productsRub,String ptype) {
		// 初始化参数
		initOut(out);
		// 判断供求类型
		String pName = "全部";
		if("1".equals(ptype)){
			productsRub.setProductsTypeCode("10331000");
			pName="供应";
		}else if("2".equals(ptype)){
			productsRub.setProductsTypeCode("10331001");
			pName="求购";
		}
		out.put("ptype", ptype);
		
		// 滚动图片供求
		out.put("picList",productsService.queryProductsForPic(null, 40));
		
		// 首页数据
		page.setPageSize(40);
		out.put("page", productsRubService.pageRub(productsRub, page));
		
		// seo tkd
		int start = page.getStartIndex();
		int p = start/page.getPageSize()+1;
		if(p>1){
			SeoUtil.getInstance().buildSeo("index",new String[]{",第"+String.valueOf(p)+"页",pName},null,null,out);
		}else{
			SeoUtil.getInstance().buildSeo("index",new String[]{"",pName},null,null,out);
		}
		// 参数put
		out.put("productsRub", productsRub);
		
		return new ModelAndView();

	} 
	@RequestMapping
	public ModelAndView list(Map<String, Object> out,PageDto<ProductsRub> page,ProductsRub productsRub,String searchTag,String keywords,String ptype) throws UnsupportedEncodingException {
		// 初始化参数
		initOut(out);
		// 根据信息搜索列表
		String pName = "";
		String seoStr = "";
		do {
			if("1".equals(ptype)){
				productsRub.setProductsTypeCode("10331000");
				pName="供应";
				out.put("ptype", ptype);
			}else if("2".equals(ptype)){
				productsRub.setProductsTypeCode("10331001");
				pName="求购";
				out.put("ptype", ptype);
			}
			if(StringUtils.isNotEmpty(searchTag)){
				productsRub.setTags(searchTag);
				seoStr = searchTag;
				out.put("searchTag", searchTag);
				out.put("searchTagEncode", URLEncoder.encode(searchTag, "utf-8"));
				break;
			}
			if(StringUtils.isNotEmpty(keywords)){
				productsRub.setTitle(keywords);
				seoStr = keywords;
				out.put("keywords", keywords);
				out.put("keywordsEncode", URLEncoder.encode(keywords, "utf-8"));
				break;
			}
		} while (false);
		page.setPageSize(75);
		out.put("page", productsRubService.pageRub(productsRub, page));
		if(page.getRecords().size()==0){
			out.put("keywords", URLEncoder.encode(keywords, "utf-8"));
			return new ModelAndView("forward:empty.htm");
		}
		// seo tkd
		int p = page.getStartIndex()/page.getPageSize()+1;
		SeoUtil.getInstance().buildSeo("list", new String[]{seoStr,pName,String.valueOf(p)}, null, null, out);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView detail(Map<String, Object> out,PageDto<ProductsDO> page,Integer pid,HttpServletRequest request) throws UnsupportedEncodingException {
		// 初始化参数
		initOut(out);
		//信息详情
		ProductsRub productsRub=productsRubService.queryRubByProductId(pid);
		out.put("productsRub", productsRub);
		//该供求图片
		List<ProductsPicDO> picList=productsPicService.queryProductPicInfoByProductsId(pid);
		if(picList.size()>0){
			out.put("picUrl", picList.get(0).getPicAddress());
		}
		// 该商家其它信息
		ProductsDO productsDO=productsService.queryProductsWithOutDetailsById(pid);
		List<ProductsDto> similarProducts= productsService.queryProductsOfCompany(productsDO.getCompanyId(), 5);
		out.put("similarProducts", similarProducts);
		//相关供求信息
		ProductsDO products = new ProductsDO();
		PageDto<ProductsDto> tradePage = new PageDto<ProductsDto>();
		tradePage.setPageSize(5);
		products.setProductsTypeCode("10331000");
		out.put("sellList", productsService.pageProductsBySearchEngine(products, null, null, tradePage).getRecords());
		products.setProductsTypeCode("10331001");
		out.put("buyList", productsService.pageProductsBySearchEngine(products, null, null, tradePage).getRecords());
		// 当前位置 标签 url
		if(StringUtils.isNotEmpty(productsRub.getTags())){
			out.put("tagsUrl", URLEncoder.encode(productsRub.getTags(),"utf-8"));
		}
		// 获取访问历史痕迹
		String cookie = HttpUtils.getInstance().getCookie(request,ProductsViewHistoryService.HISTORY_KEY,ProductsViewHistoryService.DOMAIN);
		if (StringUtils.isNotEmpty(cookie)) {
			List<ProductsViewHistory> historyList = productsViewHistoryService.queryHistory(cookie, null);
			out.put("historyList", historyList);
		}
		//seo
		SeoUtil.getInstance().buildSeo("detail", new String[]{productsRub.getTitle()}, null, new String[]{productsRub.getTitle()}, out);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView empty(Map<String, Object> out,PageDto<ProductsDto> page,String keywords,String ptype) throws UnsupportedEncodingException{
		// 初始化参数
		initOut(out);
		
		String name="";
		if("1".equals(ptype)){
			name="供应";
		}else{
			name="求购";
		}
		out.put("ptype", ptype);
		out.put("keywords", URLDecoder.decode(keywords, "utf-8"));
		//相关供求信息
		ProductsDO products=new ProductsDO();
		page.setPageSize(10);
		products.setProductsTypeCode("10331000");
//		out.put("page", productsRubService.pageRub(prorub, page));
		out.put("sellList", productsService.pageProductsBySearchEngine(products, null, null, page).getRecords());
		products.setProductsTypeCode("10331001");
//		out.put("page1", productsRubService.pageRub(prorub, page));
		out.put("buyList", productsService.pageProductsBySearchEngine(products, null, null, page).getRecords());
		SeoUtil.getInstance().buildSeo("empty", new String[]{name,keywords}, null, null, out);
		return new ModelAndView();
	}
	
	private void initOut(Map<String, Object>out){
		// 搜索栏下热门标签
		Map<String, String> hotTags = TagsUtils.getInstance().queryTagsByHot(10, 7);
		for(String str:hotTags.keySet()){
			String valStr = CNToHexUtil.getInstance().encode(str);
			hotTags.put(str,valStr);
		}
		out.put("hotTags", hotTags);
	}
}

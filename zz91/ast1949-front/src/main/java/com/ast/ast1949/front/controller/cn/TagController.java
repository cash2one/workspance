package com.ast.ast1949.front.controller.cn;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.company.InquiryDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.front.controller.BaseController;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.util.CNToHexUtil;
import com.ast.ast1949.util.PageCacheUtil;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;
/**
 * 标签页面单页
 * @author sj
 *
 */
@Controller
public class TagController extends BaseController {

	@Resource
	private CompanyService companyService;
	@Resource
	private InquiryService inquiryService;

	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletResponse response) throws UnsupportedEncodingException {
		// 搜索栏上方寻盘显示
		List<InquiryDto> vlist = inquiryService.queryScrollInquiry();
		out.put("list", vlist);
		// seo TKD
		SeoUtil.getInstance().buildSeo("tags", out);
		// 本月热门标签
		Map<String, String> hotTags = TagsUtils.getInstance().queryTagsByHot(100, 30);
		for(String str:hotTags.keySet()){
			String valStr = CNToHexUtil.getInstance().encode(str);
			hotTags.put(str,valStr);
		}
		out.put("hotTags", hotTags);
		// 最新标签
		Map<String, String> newTags = TagsUtils.getInstance().queryTagsByTag("", "gmt_created", 100);
		for(String str:newTags.keySet()){
			String valStr = CNToHexUtil.getInstance().encode(str);
			newTags.put(str,valStr);
		}
		out.put("newTags", newTags);
		// 7日标签
		Map<String, String> sHotTags = TagsUtils.getInstance().queryTagsByHot(20, 7);
		for(String str:sHotTags.keySet()){
			String valStr = CNToHexUtil.getInstance().encode(str);
			sHotTags.put(str,valStr);
		}
		out.put("sHotTags", sHotTags);
		// 高会供求
		List<ProductsDto> list = companyService.queryVipNoSame(5,null);
		out.put("vipList", list);
		// cdn 缓存
		PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_MIN * 5);
		return new ModelAndView();
	}

//	@SuppressWarnings("static-access")
//	private Map<String, String> CNToHex(Map<String, String> map) {
//		Map<String, String> nmap = new HashMap<String, String>();
//		for (String str : map.keySet()) {
//			String val = CNToHexUtil.getInstance().encode(str);
//			nmap.put(str, val);
//		}
//		return nmap;
//	}
	// public static void main(String[] args) throws
	// UnsupportedEncodingException {
	// String str = "孔";
	// System.out.println(URLEncoder.encode(str,"Hex"));
	// }
}

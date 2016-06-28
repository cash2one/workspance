package com.ast.ast1949.front.controller.cn;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.news.NewsTech;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.InquiryDto;
import com.ast.ast1949.dto.news.NewsTechDTO;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.front.controller.BaseController;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.information.ExhibitService;
import com.ast.ast1949.service.news.NewsTechService;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class TechController extends BaseController {

	final static String URL_ENCODE = "utf-8";

	@Resource
	private ExhibitService exhibitService;
	@Resource
	private ProductsService productsService;
	@Resource
	private InquiryService inquiryService;
	@Resource
	private NewsTechService newsTechService;

	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			PageDto<ProductsDto> page, ProductsDO product, NewsTechDTO newsTech) {
		// 页头询盘
		out.put("listInquiry", getInquiry());
		// 热门技术
		out.put("techIndex", newsTechService.queryTechForIndex(newsTech, 12));
		// 广告旁边
		out.put("newTech", newsTechService.queryTechList(newsTech, 2));
		// 废塑料技术
		newsTech.setCategoryCode("1001");
		out.put("newTechplas", newsTechService.queryTechList(newsTech, 10));
		// 废金属技术
		newsTech.setCategoryCode("1000");
		out.put("newTechjinshu", newsTechService.queryTechList(newsTech, 10));
		// 废纸橡胶技术
		newsTech.setCategoryCode("1002");
		out.put("newTechpaper", newsTechService.queryTechList(newsTech, 5));
		// 其它技术
		newsTech.setCategoryCode("1003");
		out.put("newTechother", newsTechService.queryTechList(newsTech, 5));
		// 废塑料热门供求
		page.setPageSize(5);
		product.setTitle("废塑料");
		product.setProductsTypeCode("10331000");
		out.put("feiplasg",
				productsService.pageProductsBySearchEngine(product, null, null,
						page).getRecords());
		product.setProductsTypeCode("10331001");
		out.put("feiplasq",
				productsService.pageProductsBySearchEngine(product, null, null,
						page).getRecords());
		// 废金属热门供求
		product.setTitle("废金属");
		product.setProductsTypeCode("10331000");
		out.put("feimetg",
				productsService.pageProductsBySearchEngine(product, null, null,
						page).getRecords());
		product.setProductsTypeCode("10331001");
		out.put("feimetq",
				productsService.pageProductsBySearchEngine(product, null, null,
						page).getRecords());
		// 废纸热门供求
		page.setPageSize(2);
		product.setTitle("废纸橡胶");
		product.setProductsTypeCode("10331000");
		out.put("paperg",
				productsService.pageProductsBySearchEngine(product, null, null,
						page).getRecords());
		page.setPageSize(3);
		product.setProductsTypeCode("10331001");
		out.put("paperq",
				productsService.pageProductsBySearchEngine(product, null, null,
						page).getRecords());
		// 其它热门
		page.setPageSize(2);
		product.setTitle("其它废料");
		product.setProductsTypeCode("10331000");
		out.put("otherg",
				productsService.pageProductsBySearchEngine(product, null, null,
						page).getRecords());
		page.setPageSize(3);
		product.setProductsTypeCode("10331001");
		out.put("otherq",
				productsService.pageProductsBySearchEngine(product, null, null,
						page).getRecords());
		// 最新展会
		out.put("newestExhibit",
				exhibitService.queryNewestExhibit(null, null, 4));
		SeoUtil.getInstance().buildSeo("tech", out);
		return null;
	}

	@RequestMapping
	public ModelAndView list(Map<String, Object> out, String code,
			String keywords, NewsTechDTO newsTech, PageDto<NewsTech> page)
			throws UnsupportedEncodingException {
		page.setPageSize(24);
		if (StringUtils.isNotEmpty(keywords)) {
			keywords = keywords.replaceAll("astojh", "#");
			if (!StringUtils.isContainCNChar(keywords)) {
				keywords = StringUtils.decryptUrlParameter(keywords);
			}
			newsTech.setTags(keywords);
			out.put("keywords", keywords);
		}
		newsTech.setCategoryCode(code);
		page = newsTechService.pageTech(newsTech, page);
		out.put("page", page);
		out.put("listInquiry", getInquiry());
		out.put("code", code);
		out.put("cateName", CATEGORY_CODE.get(code));
		SeoUtil.getInstance().buildSeo(BUILD_SEO.get(code), out);
		return null;
	}

	@RequestMapping
	public ModelAndView detail(Map<String, Object> out, Integer id,
			NewsTechDTO newsTechDto) {
		do {
			if (id == null) {
				break;
			}
			NewsTech newsTech = newsTechService.queryById(id);
			String code = newsTech.getCategoryCode().substring(0, 4);
			if (StringUtils.isNotEmpty(code)) {
				out.put("code", code);
				out.put("cateName", CATEGORY_CODE.get(code));
			}
			// 更新文章点击量
			newsTechService.updateViewCount(id, newsTech.getViewCount() + 1);
			// 查询上一篇文章
			NewsTech onTech = newsTechService.queryOnNewsTechById(id);
			if (onTech != null
					&& newsTech.getCategoryCode().equals(
							onTech.getCategoryCode())) {
				out.put("onTech", onTech);
			}
			// 查询下一篇文章
			NewsTech downTech = newsTechService.queryDownNewsTechById(id);
			if (downTech != null
					&& newsTech.getCategoryCode().equals(
							downTech.getCategoryCode())) {
				out.put("downTech", downTech);
			}
			out.put("newsTech", newsTech);
			newsTechDto.setCategoryCode(code);
			out.put("neTech", newsTechService.queryTechList(newsTechDto, 7));
			String content = newsTech.getContent();
			if (StringUtils.isNotEmpty(content)) {
				content = Jsoup.clean(content, Whitelist.none());
				content = content.replace("&nbsp;", "").replace(" ", "");
				if (content.length() >= 120) {
					content = content.substring(0, 120);
				}
			}
			SeoUtil.getInstance().buildSeo("techdetail",
					new String[] { newsTech.getTitle() }, null,
					new String[] { content }, out);
		} while (false);
		out.put("listInquiry", getInquiry());
		return null;
	}

	private List<InquiryDto> getInquiry() {
		List<InquiryDto> list = inquiryService.queryScrollInquiry();
		return list;
	}

	final static Map<String, String> CATEGORY_CODE = new HashMap<String, String>();
	static {
		CATEGORY_CODE.put("1000", "废金属");
		CATEGORY_CODE.put("1001", "废塑料");
		CATEGORY_CODE.put("1002", "废纸与橡胶");
		CATEGORY_CODE.put("1003", "其他废料");
	}

	final static Map<String, String> BUILD_SEO = new HashMap<String, String>();
	static {
		BUILD_SEO.put("1000", "techjinshu");
		BUILD_SEO.put("1001", "techsuliao");
		BUILD_SEO.put("1002", "techzhi");
		BUILD_SEO.put("1003", "techother");
	}
}

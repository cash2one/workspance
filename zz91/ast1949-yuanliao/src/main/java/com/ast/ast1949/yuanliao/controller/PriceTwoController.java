package com.ast.ast1949.yuanliao.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.products.ProductsExpire;
import com.ast.ast1949.domain.yuanliao.CategoryYuanliao;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.service.company.CategoryCompanyPriceService;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsExpireService;
import com.ast.ast1949.service.yuanliao.CategoryYuanliaoService;
import com.ast.ast1949.service.yuanliao.YuanLiaoService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class PriceTwoController extends BaseController {
	@Resource
	private CompanyPriceService companyPriceService;
	@Resource
	private ProductsExpireService productsExpireService;
	@Resource
	private PriceService priceService;
	@Resource
	private CategoryCompanyPriceService categoryCompanyPriceService;
	@Resource
	private YuanLiaoService yuanLiaoService;
	@Resource
	private CategoryYuanliaoService categoryYuanliaoService;

	@RequestMapping
	public ModelAndView priceList(Map<String, Object> out,
			HttpServletRequest request) {

		SeoUtil.getInstance().buildSeo(out);
		return null;
	}

	@RequestMapping
	public ModelAndView comDetail(Map<String, Object> out,
			HttpServletRequest request, Integer id,String mainCode) {
		do {
			if (id == null) {
				break;
			}
			if(StringUtils.isNotEmpty(mainCode)){
				try {
					mainCode=StringUtils.decryptUrlParameter(mainCode);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			// 企业报价详情
			CompanyPriceDO companyPriceDO = companyPriceService
					.queryCompanyPriceById(id);
			if (companyPriceDO == null) {
				break;
			}
			// 过期天数
			ProductsExpire productsExpire = productsExpireService
					.queryByProductsId(companyPriceDO.getProductId());
			out.put("companyPriceDO", companyPriceDO);
			out.put("productsExpire", productsExpire);

			// 最新塑料市场价格
			List<PriceDO> list2 = priceService.querySexProvin();
			out.put("list2", list2);
//			Map<String, String> map = categoryCompanyPriceService
//					.queryAllCompanyPrice();
			// 相关类别最新报价
//			String category = null;

			if (companyPriceDO != null) {
				List<CompanyPriceDO> list3 = companyPriceService.queryByCode(
						companyPriceDO.getCategoryCompanyPriceCode(), 6);
				out.put("list3", list3);
//				category = map
//						.get(companyPriceDO.getCategoryCompanyPriceCode());
			}

			// 最新原料供求
			List<Yuanliao> list5 = yuanLiaoService.queryNewSize(6);
			out.put("list5", list5);

			// 该公司的其他公司报价
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", companyPriceDO.getId());
			param.put("companyId", companyPriceDO.getCompanyId());
			List<CompanyPriceDO> list = companyPriceService
					.queryCompanyPriceByCompanyId(param);
			out.put("list", list);
			SeoUtil.getInstance().buildSeo(out);
		} while (false);
		// 类别中的拼音，与编码之间的关系
		Map<String, String> mapMain = YuanliaoFacade.PINYIN_MAP;
		out.put("code", mainCode);
		out.put("mapMain", mapMain);
		return null;
	}

	@RequestMapping
	public ModelAndView productChild(HttpServletRequest request,
			Map<String, Object> out, String parentCode) throws IOException {
		List<CategoryCompanyPriceDO> list = categoryCompanyPriceService
				.queryCategoryCompanyPriceByCode(parentCode);
		// List<CategoryYuanliao> list = new ArrayList<CategoryYuanliao>();
		// if (map == null) {
		// return printJson(list, out);
		// }
		// for (Entry<String, String> m : map.entrySet()) {
		// CategoryYuanliao c = new CategoryYuanliao();
		// c.setCode(m.getKey());
		// c.setLabel(m.getValue());
		// list.add(c);
		// }
		return printJson(list, out);
	}
}

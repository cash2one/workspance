package com.ast.ast1949.yuanliao.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.yuanliao.YuanLiaoSearch;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.yuanliao.YuanLiaoService;
import com.zz91.util.seo.SeoUtil;

@Controller
public class ProductController extends BaseController {
	@Resource
	private CompanyService companyService;
	@Resource
	private ProductsService productsService;
	@Resource
	private YuanLiaoService yuanLiaoService;
	@Resource
	private PriceService priceService;

	@RequestMapping
	public ModelAndView index(Map<String, Object> out, YuanLiaoSearch search)
			throws Exception {
		SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String time = fomart.format(date);
		out.put("NewDate", time);
		search.setYuanliaoTypeCode("10331001");
		List<YuanliaoDto> list = yuanLiaoService.queryYuanLiaoByCondition(
				search, 11);
		out.put("list", list);
		// 查询最新塑料行情综述下面的PVC、ABS/PS、PP、HDPE、LLDPE 全国各地PP市场概况和全国各地PE市场概况
		List<PriceDO> priceDO = priceService.queryNewPrice(217);
		out.put("priceDO", priceDO);
		SeoUtil.getInstance().buildSeo("proindex", out);
		return null;
	}

}

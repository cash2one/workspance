package com.ast.ast1949.spot.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.price.PriceDTO;
import com.ast.ast1949.service.price.PriceService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.seo.SeoUtil;

/**
 * author:kongsj 
 * date:2013-6-19
 */
@Controller
public class BaogangController extends BaseController {

	@Resource
	private PriceService priceService;
	
	@RequestMapping
	public void index(Map<String, Object> out,PriceDO priceDO,PageDto<PriceDO> page) {
		page.setPageSize(8);
		out.put("page", priceService.pagePriceByType(311, null,null , page));
		SeoUtil.getInstance().buildSeo("baogang", out);
	}

	@RequestMapping
	public void detail(Map<String, Object>out,Integer id){
		PriceDTO dto = priceService.queryPriceByIdForEdit(id);
		out.put("dto", dto);
		SeoUtil.getInstance().buildSeo("baogangDetail", new String[]{dto.getPrice().getTitle(),DateUtil.toString(new Date(), "yyyy-MM-dd")}, null, null, out);
	}

}
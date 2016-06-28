package com.ast.ast1949.api.controller.fragment;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.api.controller.BaseController;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.map.MapService;
import com.ast.ast1949.service.price.PriceService;

@Controller
public class MapController extends BaseController{

	@Resource
	private PriceService  priceService;
	
	@Resource
	private MapService mapService;
	
	//网商报价
	@RequestMapping	
	public ModelAndView queryComppriceList(Map<String, Object> out,String productCode,String areaKey,String keywords) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
	    
		map.put("comppriceList", mapService.indexLatestCompanysPriceByArea(
				productCode, areaKey, 30));
		
		return printJson(map, out);
	}
	
	//最新供求商机和供求信息
	@RequestMapping	
     public ModelAndView queryProductsList(Map<String, Object> out,Integer size,String productCode,String areaKey, String keywords,PageDto<ProductsDto> pageProduct) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
	
			pageProduct.setPageSize(size);
			
			pageProduct = mapService.queryBySearchEnegine(areaKey, keywords,
					productCode, pageProduct);
			map.put("productsList", pageProduct.getRecords());
			return printJson(map, out);
			
		  
	}	
		
	//金属
	@RequestMapping	
    public ModelAndView queryPriceByTypeId(Map<String, Object> out,Integer typeId, Integer parentId,
			Integer assistTypeId, PageDto<PriceDO> page) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		 page = new PageDto<PriceDO>();
			page.setPageSize(6);
			page.setSort("gmtCreated");
			page.setDir("desc");
		
			map.put("priceDo", priceService.pagePriceByType(typeId, null, null, page)
					.getRecords());
		   
			
		   return printJson(map, out);
	}
	
}

	

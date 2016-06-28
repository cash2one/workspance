package com.ast.ast1949.service.wechat.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.wechat.WXReplyService;

@Component("wxReplyProductsService")
public class WXReplyServiceImpl implements WXReplyService{

	@Resource
	private ProductsService productsService;
	
	@Resource
	private PriceService priceService;
	
	@Override
	public List<ProductsDto> replyProducts(String keywords) {
		PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		page.setPageSize(5);
		ProductsDO product = new ProductsDO();
		if(keywords.indexOf("求购")!=-1){
			keywords = keywords.replace("求购", "");
			product.setProductsTypeCode(ProductsService.PRODUCTS_TYPE_OFFER);
		}else if(keywords.indexOf("供应")!=-1){
			keywords = keywords.replace("供应", "");
			product.setProductsTypeCode(ProductsService.PRODUCTS_TYPE_BUY);
		}
		product.setTitle(keywords);

		page = productsService.pageProductsBySearchEngine(product, null, true, page);
		if(page!=null&&page.getRecords().size()>0){
			List<ProductsDto> nlist = new ArrayList<ProductsDto>();
			for(ProductsDto dto :page.getRecords()){
				ProductsDO obj = dto.getProducts();
				obj.setDetails(Jsoup.clean(obj.getDetails(), Whitelist.none()));
				dto.setProducts(obj);
				nlist.add(dto);
			}
			return nlist;
		}
		return null;
	}

	@Override
	public List<PriceDO> replyPrice(String keywords) {
		PriceDO priceDO = new PriceDO();
		PageDto<PriceDO> page = new PageDto<PriceDO>();
		page.setPageSize(1);
		page = priceService.pagePriceBySearchEngine(keywords, priceDO, page);
		if(page!=null&&page.getRecords().size()>0){
			List<PriceDO> list =new ArrayList<PriceDO>();
			for(PriceDO obj : page.getRecords()){
				PriceDO price = new PriceDO();
				price = priceService.queryPriceByIdForEdit(obj.getId()).getPrice();
				price.setContent(Jsoup.clean(price.getContent(), Whitelist.none()).replace("&nbsp", "").substring(0,200));
				list.add(price);
			}
			return list;
		}
		return null;
	}

}

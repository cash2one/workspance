package com.ast.ast1949.service.wechat;

import java.util.List;

import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.products.ProductsDto;

public interface WXReplyService {
	public List<ProductsDto> replyProducts(String keywords);
			
	public List<PriceDO> replyPrice(String keywords);
}

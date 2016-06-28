package com.kl91.service.products;

import java.util.List;

import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.products.ProductsSearchDto;
import com.kl91.domain.dto.products.ProductsSolrDto;

public interface ProductsSolrService {

	public List<ProductsSolrDto> findIndexProducts(ProductsSearchDto searchDto,PageDto<ProductsSolrDto> page);

	public List<ProductsSolrDto> querySolrProductsByCompanyId(Integer companyId, PageDto<ProductsSolrDto> page);
	public List<ProductsSolrDto> querySolrProductsByTypeCode(ProductsSearchDto searchDto,PageDto<ProductsSolrDto> page,String typeCode);
	public List<ProductsSolrDto> queryProductsByTypeCodeAndKeywords(ProductsSearchDto searchDto,PageDto<ProductsSolrDto> page,String typeCode,String keywords);
}

/**
 * 
 */
package com.ast.ast1949.service.dataindex;

import java.util.List;

import com.ast.ast1949.domain.dataindex.ProductsIndex;
import com.ast.ast1949.dto.PageDto;

/**
 * @author root
 *
 */
public interface ProductsIndexService {

	public void buildIndex(Integer productId, String categoryCode);
	
	public Integer updateOrderby(Integer id, Float orderby);
	
	public PageDto<ProductsIndex> pageIndex(String categoryCode, PageDto<ProductsIndex> page);
	
	public Integer updateIndex(ProductsIndex index);

	public Integer removeIndex(Integer id);
	
	public List<ProductsIndex> queryProductsDateByCode(String code,Integer size);
	
}

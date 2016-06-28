/**
 * 
 */
package com.ast.ast1949.persist.dataindex;

import java.util.List;

import com.ast.ast1949.domain.dataindex.ProductsIndex;
import com.ast.ast1949.dto.PageDto;

/**
 * @author root
 *
 */
public interface ProductsIndexDao {

	public Integer insertIndex(ProductsIndex index);
	
	public Integer updateOrderBy(Integer id, Float orderby);
	
	public Integer updateIndex(ProductsIndex index);
	
	public Integer deleteById(Integer id);
	
	public List<ProductsIndex> queryIndex(String categoryCode, PageDto<ProductsIndex> page);
	
	public Integer queryIndexCount(String categoryCode);
	
}

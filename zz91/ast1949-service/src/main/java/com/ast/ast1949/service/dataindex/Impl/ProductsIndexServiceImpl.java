/**
 * 
 */
package com.ast.ast1949.service.dataindex.Impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.dataindex.ProductsIndex;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.dataindex.ProductsIndexDao;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.products.ProductsPicDAO;
import com.ast.ast1949.service.dataindex.ProductsIndexService;
import com.zz91.util.lang.StringUtils;

/**
 * @author root
 *
 */
@Component("productsIndexService")
public class ProductsIndexServiceImpl implements ProductsIndexService {

	@Resource
	private ProductsDAO  productsDAO;
	@Resource
	private ProductsPicDAO productsPicDAO;
	
	@Resource
	private ProductsIndexDao productsIndexDao;
	
	@Override
	public void buildIndex(Integer productId, String categoryCode) {
		ProductsDO products=productsDAO.queryProductsById(productId);
		ProductsIndex index=new ProductsIndex();
		index.setDataIndexCode(categoryCode);
		index.setProductsId(products.getId());
		index.setAccount(products.getAccount());
		index.setCompanyId(products.getCompanyId());
		index.setTitle(products.getTitle());
		index.setMinPrice(products.getMinPrice());
		index.setMaxPrice(products.getMaxPrice());
		index.setPriceUnit(products.getPriceUnit());
		if(StringUtils.isNumber(products.getQuantity())){
			index.setQuantity(Integer.valueOf(products.getQuantity()));
		}
		index.setQuantityUnit(products.getQuantityUnit());
		index.setTags(products.getTags());
		index.setTagsAdmin(products.getTagsAdmin());
		index.setRefreshTime(products.getRefreshTime());
		index.setRealTime(products.getRealTime());
		index.setProductsType(products.getProductsTypeCode());
		
		index.setOrderby(new BigDecimal(0));
		
		index.setPic(productsPicDAO.queryPicPathByProductId(productId));
		

		productsIndexDao.insertIndex(index);
	}

	@Override
	public PageDto<ProductsIndex> pageIndex(String categoryCode,
			PageDto<ProductsIndex> page) {
		page.setRecords(productsIndexDao.queryIndex(categoryCode, page));
		page.setTotalRecords(productsIndexDao.queryIndexCount(categoryCode));
		return page;
	}

	@Override
	public Integer removeIndex(Integer id) {
		return productsIndexDao.deleteById(id);
	}

	@Override
	public Integer updateIndex(ProductsIndex index) {
		return productsIndexDao.updateIndex(index);
	}

	@Override
	public Integer updateOrderby(Integer id, Float orderby) {
		return productsIndexDao.updateOrderBy(id, orderby);
	}

	@Override
	public List<ProductsIndex> queryProductsDateByCode(String code, Integer size) {
		if(size==null){
			size=10;
		}
		if(size>50){
			size=50;
		}
		PageDto<ProductsIndex> page = new PageDto<ProductsIndex>();
		page.setPageSize(size);
		return productsIndexDao.queryIndex(code, page);
	}

}

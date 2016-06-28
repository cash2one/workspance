package com.ast.ast1949.service.facade;

import javax.annotation.Resource;

import org.junit.Test;

import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.products.CategoryProductsService;

public class ProductsCategoryFacadeTest extends BaseServiceTestCase {
	@Resource
	private CategoryProductsService categoryProductsService;

	@Test
	public void testInitCategory() {
		CategoryProductsFacade.getInstance().init(categoryProductsService.queryAllCategoryProducts(),null);
	}

	@Test
	public void testQueryCategoryLabelByCode() {

	}

	@Test
	public void testQueryCategoryDirectChildrenMapByParentCode() {
//		Map<String, String> kk = ProductsCategoryFacade.getInstance()
//				.queryCategoryDirectChildrenMapByParentCode(null, null);
//		for (Entry<String, String> entry : kk.entrySet()) {
//			System.out.println(entry.getKey() + " : \t" + entry.getValue());
//		}
//
//		Map<String, String> kk2 = ProductsCategoryFacade.getInstance()
//				.queryCategoryDirectChildrenMapByParentCode("100210041001", "1");
//		for (Entry<String, String> entry : kk2.entrySet()) {
//			System.out.println(entry.getKey() + " : \t" + entry.getValue());
//		}

	}
}

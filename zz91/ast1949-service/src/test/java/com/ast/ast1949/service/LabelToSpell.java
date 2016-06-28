/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-1
 */
package com.ast.ast1949.service;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.service.products.CategoryProductsService;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public class LabelToSpell extends BaseServiceTestCase{

	@Autowired
	private CategoryProductsService categoryProductsService;

	@Test
	public void test_cn_to_spell() throws SQLException{
//		List<CategoryProductsDO> list = categoryProductsService.queryAllCategoryProducts();
//		GB2Alpha gb2Alpha = new GB2Alpha();
//		for(CategoryProductsDO category:list){
//			category.setCnspell(gb2Alpha.String2Alpha(category.getLabel()));
//			connection.createStatement().execute("update category_products set cnspell='"+
//					category.getCnspell().toLowerCase()+"' where id="+category.getId());
//		}
	}

}

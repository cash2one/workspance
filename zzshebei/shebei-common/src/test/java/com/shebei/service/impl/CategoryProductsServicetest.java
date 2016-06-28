package com.shebei.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.junit.Test;

import com.ast1949.shebei.domain.CategoryProducts;
import com.ast1949.shebei.service.CategoryProductsService;
import com.shebei.service.BaseServiceTestCase;

public class CategoryProductsServicetest extends BaseServiceTestCase{
	
	@Resource
	private CategoryProductsService categoryProductsService; 
	
	@Test
	public  void testQueryAllCategorys() {
		//clean();
		CategoryProducts cp = new CategoryProducts();
		cp.setId(1012);
		cp.setCode("100010031001");
		cp.setLabel("水处理器3");
		cp.setLeaf((short)1);
		cp.setTags("污水4");
		cp.setSort((short)0);
		createOneTestRecord(cp);
//		List<CategoryProducts> list = categoryProductsService.queryAllCategorys((short)1, "1000");
//		assertNotNull(list);
//		assertEquals(7, list.size());
		
	}
	
	private void clean()	{
		try {
			connection.prepareStatement("delete from category_products").execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	private Integer createOneTestRecord(CategoryProducts cp)	{
	  StringBuffer sb = new StringBuffer();
	  sb.append("insert into category_products(id,code,label,leaf,tags,sort,gmt_created,gmt_modified) values (");
	  sb.append(cp.getId());
	  sb.append(",'");
	  sb.append(cp.getCode()+"'");
	  sb.append(",'");
	  sb.append(cp.getLabel()+"'");
	  sb.append(",");
	  sb.append(cp.getLeaf());
	  sb.append(",'");
	  sb.append(cp.getTags()+"'");
	  sb.append(",");
	  sb.append(cp.getSort());
	  sb.append(",");
	  sb.append("now(),now())");	
	 try {
			connection.prepareStatement(sb.toString()).execute();
			return insertResult();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	private CategoryProducts getCategoryProducts(int cpid){
		CategoryProducts cp = new CategoryProducts();
		cp.setId(cpid);
		cp.setCode("10001001");
		cp.setLabel("污水处理器");
		cp.setLeaf((short)1);
		cp.setTags("污水");
		cp.setSort((short)0);
		return cp;
	}
	
	public void manyTestRecord(int num) {
		for (int i = 0; i < num; i++) {
			createOneTestRecord(getCategoryProducts(i));
		}
	}
	
}

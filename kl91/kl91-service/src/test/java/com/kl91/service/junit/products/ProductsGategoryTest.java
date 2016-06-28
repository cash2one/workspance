package com.kl91.service.junit.products;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.kl91.domain.products.ProductsCategory;
import com.kl91.service.junit.BaseServiceTestCase;
import com.kl91.service.products.ProductsCategoryService;

public class ProductsGategoryTest extends BaseServiceTestCase{
	
	@Resource
	private ProductsCategoryService productsCategoryService;
	
	//测试createProductsCategory
	public void test_createProductsCategory(){
		clean();
		String code = "1000";
		Integer i = productsCategoryService.createProductsCategory(getProductsCategory(), code);
		assertNotNull(i);
		assertTrue(i.intValue() > 0);
		
		ProductsCategory productsCategory = queryOne(i);
		assertNotNull(productsCategory);
		assertEquals("测试添加",productsCategory.getName());
	}
	
	public void test_queryByCode() {
		clean();
		String code = "1000";
		List<ProductsCategory> list = productsCategoryService.queryByCode(code);
		
		assertTrue(list.size() == 0);
	}
	
	public void test_update() {
		clean();
		Integer id = insert("测试更新","code");
		insert("测试更新2","code");
		insert("测试更新3","code");
		insert("测试更新4","code");
		insert("测试更新5","code");
		insert("测试更新6","code");
		insert("测试更新7","code");
		assertTrue(id.toString(), id.intValue() > 0);

		ProductsCategory productsCategory=getProductsCategory();
		productsCategory.setId(id);
		productsCategory.setName("测试更新后的数据");
		Integer i = productsCategoryService.editProductCategory(productsCategory);
		assertNotNull(i);
		assertEquals("测试更新后的数据", productsCategory.getName());
	}
	public void test_delete() {
		clean();
		Integer id1 = insert("测试删除1","code");
		Integer id2 = insert("测试删除2","code");
	
		Integer i = productsCategoryService.deleteProductsCategory(id1);
		assertNotNull(i);
		assertEquals(1, i.intValue());
		
		ProductsCategory productsCategory=queryOne(id1);
		assertNull(productsCategory);
		
		ProductsCategory productsCategory2=queryOne(id2);
		assertNotNull(productsCategory2);
	}
	
	public void test_queryOne() {
		clean();
		Integer id1 = insert("测试查询1","code");
		insert("测试查询2","code");

		ProductsCategory productsCategory=productsCategoryService.queryById(id1);
		assertNotNull(productsCategory);
		assertEquals("测试查询1",productsCategory.getName());
	}
	
	public void test_queryMaxCodeBypreCode() {
		insert("测试最大","1000");
		insert("测试最大2", "2000");
		String i =productsCategoryService.queryMaxCodeBypreCode(null);
		assertTrue(i.length()>0);
	}
	
	private Integer insert(String name,String code) {
		if(name==null){
			name="";
		}
		String sql="insert into products_category(code,name,orderby,tags,gmt_created,gmt_modified)" +
				"values('"+code+"','"+name+"',1,'tags',now(),now())";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertResult();
	}

	private ProductsCategory queryOne(Integer i) {
		ProductsCategory productsCategory = null;
		try {
			ResultSet rs = connection.prepareStatement(
					"SELECT * FROM products_category WHERE id = " + i).executeQuery();
			if (rs.next()) {
				productsCategory = new ProductsCategory(rs.getInt("id"), rs.getString("name"), 
						rs.getString("code"), rs.getInt("orderBy"), rs.getString("tags"), rs.getDate("gmt_created"),
						rs.getDate("gmt_modified"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productsCategory;
	}
	
	private ProductsCategory getProductsCategory() {
		
		return new ProductsCategory(null, "测试添加", "code", 1, "tags", new Date(), new Date());
	}
	
	public void clean() {
		try {
			connection.prepareStatement("DELETE FROM products_category").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

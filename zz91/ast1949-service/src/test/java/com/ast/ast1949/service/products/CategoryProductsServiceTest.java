/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-2
 */
package com.ast.ast1949.service.products;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.products.CategoryProductsDO;
import com.ast.ast1949.service.BaseServiceTestCase;

/**
 * @author yuyonghui
 *
 */
public class CategoryProductsServiceTest extends BaseServiceTestCase {

	@Autowired
	private CategoryProductsService categoryProductsService;

	public void test_queryCategoryProductsByCode() {
		clean();
		String code = "1000";
		List<CategoryProductsDO> list = categoryProductsService
				.queryCategoryProductsByCode(code,"0");
		assertTrue(list.size() == 0);
	}
//    public void test_queryCategoryProductsByCondition_isNull(){
//    	clean();
//    	try {
//        	categoryProductsService.queryCategoryProductsByCondition(null);
//        	fail();
//		} catch (IllegalArgumentException e) {
//             assertEquals("categoryProductsDTO is not null", e.getMessage());
//		}
//
//    }
//	public void test_queryCategoryProductsByCondition() {
//		clean();
//		CategoryProductsDTO categoryProductsDTO = new CategoryProductsDTO();
//    	CategoryProductsDO categoryProductsDO=new CategoryProductsDO();
//    	categoryProductsDO.setCode("2000");
//    	categoryProductsDO.setIsAssist("0");
//    	categoryProductsDO.setIsDel("0");
//    	categoryProductsDO.setLabel("dddaaa");
//    	categoryProductsDO.setGmtCreated(new Date());
//    	categoryProductsDO.setGmtModified(new Date());
//		categoryProductsDTO.setCategoryProductsDO(categoryProductsDO);
//	   PageDto pageDto=new PageDto();
//	 //  pageDto.s
//		categoryProductsDTO.setPageDto(pageDto);
//	List<CategoryProductsDO>	list=categoryProductsService
//				.queryCategoryProductsByCondition(categoryProductsDTO);
//	  assertTrue(list.size()==0);
//	}

	public void test_queryCategoryProductsById_isO() {
		clean();
		try {
			categoryProductsService.queryCategoryProductsById(0);

		} catch (IllegalArgumentException e) {
			// assertEquals("", actual)
		}

	}

	public void test_queryMaxCodeBypreCode() {
		clean();
    	CategoryProductsDO categoryProductsDO=new CategoryProductsDO();
    	categoryProductsDO.setCode("2000");
    	categoryProductsDO.setIsAssist("0");
    	categoryProductsDO.setIsDel("0");
    	categoryProductsDO.setLabel("dddaaa");
    	categoryProductsDO.setGmtCreated(new Date());
    	categoryProductsDO.setGmtModified(new Date());
		String i =categoryProductsService.queryMaxCodeBypreCode(null);
		assertTrue(i.length()>0);
	}
    public void test_insertCategoryProducts(){
    	clean();
    	CategoryProductsDO categoryProductsDO=new CategoryProductsDO();
    	categoryProductsDO.setCode("2000");
    	categoryProductsDO.setIsAssist("0");
    	categoryProductsDO.setIsDel("0");
    	categoryProductsDO.setLabel("dddaaa");
    	categoryProductsDO.setGmtCreated(new Date());
    	categoryProductsDO.setGmtModified(new Date());

        	String code="";
    	categoryProductsService.insertCategoryProducts(categoryProductsDO, code);
    }
    public void test_insertCategoryProducts_categoryProductsDO_isNull(){
    	clean();
    	String code="33";
    	try {
    		categoryProductsService.insertCategoryProducts(null, code);
    		fail();
		} catch (IllegalArgumentException e) {
          assertEquals("categoryProductsDO is not null", e.getMessage());
		}

    }
    public void test_insertCategoryProducts_preCode_isNull(){
    	clean();
    	CategoryProductsDO categoryProductsDO=new CategoryProductsDO();
    	categoryProductsDO.setCode("");
    	categoryProductsDO.setLabel("aaa");
    	categoryProductsDO.setIsAssist("0");
    	categoryProductsDO.setIsDel("0");
    	categoryProductsDO.setGmtCreated(new Date());
    	categoryProductsDO.setGmtModified(new Date());
    	int i=categoryProductsService.insertCategoryProducts(categoryProductsDO, null);
    	assertTrue(i>0);

    }
    public void test_updatecategoryProducts_isNull(){
         clean();
         try {
		   categoryProductsService.updatecategoryProducts(null);
		   fail();
		} catch (IllegalArgumentException e) {
        assertEquals("categoryProductsDO is not null", e.getMessage());
		}
    }
    public void test_deleteCategoryProductsById(){
       clean();
    }
	private void clean() {
		try {
			cleanupProductsAlbums();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 清除表中的数据
	protected void cleanupProductsAlbums() throws SQLException {
		connection.prepareStatement("delete from category_products").execute();
	}

}

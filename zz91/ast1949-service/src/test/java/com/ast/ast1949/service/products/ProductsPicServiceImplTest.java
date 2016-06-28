/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-26
 */
package com.ast.ast1949.service.products;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsPicDTO;
import com.ast.ast1949.service.BaseServiceTestCase;

/**
 * @author yuyonghui
 * 
 */
public class ProductsPicServiceImplTest extends BaseServiceTestCase {

	@Autowired
	private ProductsPicService productsPicService;

	public void test_queryProductsPicByCondition_isNull() {
		try {
			clean();
			productsPicService.queryProductsPicByCondition(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("productsPicDTO is not null", e.getMessage());
		}
	}

	public void test_queryProductsPicByCondition_AlbumName_isNull() {
		ProductsPicDTO productsPicDTO = new ProductsPicDTO();
	//	productsPicDTO.setAlbumName(null);
		productsPicService.queryProductsPicByCondition(productsPicDTO);
	}

	public void test_queryProductsPicByCondition_ProductTitle_isNull() {
		ProductsPicDTO productsPicDTO = new ProductsPicDTO();
		productsPicDTO.setProductTitle(null);
	List<ProductsPicDO> list=productsPicService.queryProductsPicByCondition(productsPicDTO);
		assertTrue(list.size()==0);
	}

	public void test_queryProductsPicByCondition_notNull() {
		clean();
		ProductsPicDTO productsPicDTO = new ProductsPicDTO();
		productsPicDTO.setAlbumName("aaa");
		productsPicDTO.setProductTitle("sss");
		productsPicDTO.setIsCover("2");
		PageDto pageDto = new PageDto();
		productsPicDTO.setPage(pageDto);
		ProductsPicDO productsPicDO = new ProductsPicDO();
		productsPicDTO.setProductsPicDO(productsPicDO);
	List<ProductsPicDO> list=productsPicService.queryProductsPicByCondition(productsPicDTO);
	assertTrue(list.size()==0);
	}

	public void test_getProductsPicRecordCountByCondition_isNull() {
		try {
			clean();
			productsPicService.getProductsPicRecordCountByCondition(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("productsPicDTO is not null", e.getMessage());
		}
	}

	public void test_getProductsPicRecordCountByCondition_notNull() {
		clean();
		ProductsPicDTO productsPicDTO = new ProductsPicDTO();
		productsPicDTO.setAlbumName("aaa");
		productsPicDTO.setProductTitle("sss");
		productsPicDTO.setIsCover("2");
		PageDto pageDto = new PageDto();
		productsPicDTO.setPage(pageDto);
		ProductsPicDO productsPicDO = new ProductsPicDO();
		productsPicDTO.setProductsPicDO(productsPicDO);
    int i=	  productsPicService.getProductsPicRecordCountByCondition(productsPicDTO);
	//	assertTrue(i>0);
		assertTrue(i==0);
	}
//   public void test_queryProductPicByproductId_id_isNull(){
//	   clean();
//	   List<ProductsPicDO>  list=productsPicService.queryProductPicByproductId(0);
//		assertTrue(list.size()==0);
//   }
   public void test_queryProductPicById(){
	   clean();
	   int id=1;
	   productsPicService.queryProductPicById(id);
	   
   }
   public void test_updateProductsPic(){
	   clean();
        ProductsPicDO productsPicDO=new ProductsPicDO();
        productsPicDO.setProductId(1);
        productsPicDO.setAlbumId(2);
        productsPicDO.setId(1);
        productsPicDO.setPicAddress("fdfddf");
        productsPicDO.setIsCover("0");
        productsPicDO.setIsDefault("1");
      int i=  productsPicService.updateProductsPic(productsPicDO);
        try{
			assertNotNull(i);
		//	assertTrue(i>0);
		}catch(IllegalArgumentException e){
			
		}
   }
   public void test_test_updateProductsPic_isNull(){
	   try {
		   clean();
		   productsPicService.updateProductsPic(null);
		   fail();
	} catch (IllegalArgumentException e) {
		assertEquals("productsPicDO is not null", e.getMessage());
	   }
	  
   }
   public void test_insertProductsPic_notNull(){
	   clean();
       ProductsPicDO productsPicDO=new ProductsPicDO();
       productsPicDO.setProductId(1);
       productsPicDO.setAlbumId(2);
 //      productsPicDO.setId(1);
       productsPicDO.setPicAddress("fdfddf");
       productsPicDO.setIsCover("0");
       productsPicDO.setIsDefault("1");
       int i=productsPicService.insertProductsPic(productsPicDO);
       try{
			assertNotNull(i);
			assertTrue(i>0);
		}catch(IllegalArgumentException e){
			
		}
   }
   public void test_insertProductsPic_isNull(){
	  try {
		   clean();
		   productsPicService.insertProductsPic(null);
		   fail();
		  
	} catch (IllegalArgumentException e) {
		assertEquals("productsPicDO is not null", e.getMessage());
	    }

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
		connection.prepareStatement("delete from products_pic").execute();
	}

}

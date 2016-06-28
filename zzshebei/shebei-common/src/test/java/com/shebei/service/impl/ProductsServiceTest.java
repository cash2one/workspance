package com.shebei.service.impl;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;


import com.ast1949.shebei.domain.Products;
import com.ast1949.shebei.dto.PageDto;
import com.ast1949.shebei.service.ProductsService;
import com.shebei.service.BaseServiceTestCase;


public class ProductsServiceTest extends BaseServiceTestCase{
	
	@Resource
	private ProductsService productsServices; 
	
	@Test
	public void testCreateProducts() {
		
	}

	@Test
	public void testQueryProductsByType() {
//		clean();
//		createOneTestRecord(getProducts(1003));
//		List<Products> list = productsServices.queryProductsByType(1002, (short)0, 1);
//		assertNotNull(list);
//		assertEquals(1, list.size());
	}

	@Test
	public void testPageProducts() {
		//clean();
		manyTestRecord(10);
//		PageDto<Products> page=new PageDto<Products>();
//		page.setStart(5);
//		page.setLimit(5);
//		page = productsServices.pageProducts("10001000", 1002, (short)0, page);
//		assertNotNull(page.getRecords());
//		assertEquals(2, page.getRecords().size());
//		assertEquals(7, page.getTotals().intValue());
//		
//		page.setStart(0);
//		page.setLimit(5);
//		page = productsServices.pageProducts("10001000", 1002, (short)0, page);
//		assertNotNull(page);
//		assertEquals(5, page.getRecords().size());
//		assertEquals(7, page.getTotals().intValue());
	}

	@Test
	public void testQueryProductbyId() {
//		clean();
//		createOneTestRecord(getProducts(1003));
//		Products p  = productsServices.queryProductbyId(1002);
//		assertNotNull(p);
//		assertEquals(1002,(int)p.getCid());
	}

	@Test
	public void testQueryOtherProducts() {
//		clean();
//		createOneTestRecord(getProducts(1003));
//		List<Products> list = productsServices.queryOtherProducts(1002,"10001001",(short)0,5);
//		assertNotNull(list);	
//		assertEquals(5,list.size());
	}

	@Test
	public void testQueryRelatedProducts() {
//		clean();
//		createOneTestRecord(getProducts(1003));
//		List<Products> list = productsServices.queryRelatedProducts("10001000",1003,(short)0,2);
//		assertNotNull(list);	
//		assertEquals(2,list.size());
	}

	@Test
	public void testQueryMaxGmtShow() {
//		clean();
//		createOneTestRecord(getProducts(1003));
//		Date date = productsServices.queryMaxGmtShow();
//		assertNotNull(date);
	}
	
	private void clean()	{
		try {
			connection.prepareStatement("delete from products").execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	private Integer createOneTestRecord(Products p)	{
		  StringBuffer sb = new StringBuffer();
		  sb.append("insert into products(id,cid,products_type,category_code,title,details,price,price_max,price_unit,quantity,quantity_unit," +
		  		"source,tags,area,quality,gmt_publish,gmt_refresh,gmt_expired,gmt_show,expired_day,gmt_created,gmt_modified) values(");
		  sb.append(p.getId()+",");
		  sb.append(p.getCid()+",");
		  sb.append(p.getProductsType()+",");
		  sb.append("'"+p.getCategoryCode()+"',");
		  sb.append("'"+p.getTitle()+"',");
		  sb.append("'"+p.getDetails()+"',");
		  sb.append("'"+p.getPrice()+"',");
		  sb.append("'"+p.getPriceMax()+"',");
		  sb.append("'"+p.getPriceUnit()+"',");
		  sb.append("'"+p.getQuantity()+"',");
		  sb.append("'"+p.getQuantityUnit()+"',");
		  sb.append("'"+p.getSource()+"',");
		  sb.append("'"+p.getTags()+"',");
		  sb.append("'"+p.getArea()+"',");
		  sb.append("'"+p.getQuality()+"',");
		  sb.append("now(),now(),now(),now(),");
		  sb.append("'"+p.getExpiredDay()+"',");
		  sb.append("now(),now()");
		  sb.append(")");
		  
		 		 try {
				connection.prepareStatement(sb.toString()).execute();
				return insertResult();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return null;
		}
	
	private Products getProducts(int id,int cid){
		Products p = new Products();
		p.setId(id);
		p.setCid(cid);
		p.setProductsType((short)1);
		p.setCategoryCode("10001000");
		p.setTitle("二手冰箱");
		p.setDetails("很好u哦");
		p.setPrice("200");
		p.setPriceMax("2000");
		p.setPriceUnit("美元");
		p.setQuantity("10");
		p.setQuantityUnit("100");
		p.setSource("新浪");
		p.setTags("冰箱");
		p.setArea("杭州");
		p.setQuality("八成新");
		p.setExpiredDay("0天");
		return p;
	}
	
	public void manyTestRecord(int num) {
		for (int i = 20; i < num+20; i++) {
			createOneTestRecord(getProducts(i,1));
		}
	}
}

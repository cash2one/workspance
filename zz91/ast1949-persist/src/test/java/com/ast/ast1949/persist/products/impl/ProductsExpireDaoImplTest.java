package com.ast.ast1949.persist.products.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.junit.Test;

import com.ast.ast1949.domain.products.ProductsExpire;
import com.ast.ast1949.persist.BasePersistTestCase;
import com.ast.ast1949.persist.products.ProductsExpireDao;

public class ProductsExpireDaoImplTest extends BasePersistTestCase {
	@Resource
	private ProductsExpireDao productsExpireDao;
	
	@Test
	public void test_insert() throws Exception{
		clean();
		ProductsExpire productsExpire=new ProductsExpire();
		productsExpire.setProductsId(444);
		productsExpire.setDay(10);
		int i=productsExpireDao.insert(productsExpire);
		int j=insertResult();
		assertEquals(i, j);
		ProductsExpire kw=querykw(444);
		assertEquals(productsExpire.getDay(), kw.getDay());
	}
	
	@Test
	public void test_queryByProductsId() throws Exception{
		clean();
		ProductsExpire productsExpire=new ProductsExpire();
		productsExpire.setProductsId(444);
		productsExpire.setDay(10);
		create(productsExpire);
		ProductsExpire kw=productsExpireDao.queryByProductsId(productsExpire.getProductsId());
		assertNotNull(kw);
		assertEquals(productsExpire.getDay(), kw.getDay());
	}
	
	
	private void clean() throws Exception{
		connection.prepareStatement("truncate products_expire").execute();
	}
	
	private void create(ProductsExpire kw) throws SQLException {
		String sql = "INSERT INTO products_expire(products_id,day,gmt_created,gmt_modified) "
		+ "VALUES ("+kw.getProductsId()+","+kw.getDay()+",now(),now())";
		connection.prepareStatement(sql).execute();
	}
	
	private ProductsExpire querykw(Integer productsId) throws SQLException{
		ProductsExpire kw=new ProductsExpire();
		String sql="select id,products_id,day from products_expire where products_id="+productsId;
		ResultSet rs=connection.createStatement().executeQuery(sql);
		if(rs.next()){
			kw.setId(rs.getInt(1));
			kw.setProductsId(rs.getInt(2));
			kw.setDay(rs.getInt(3));
		}
		return kw;
	}
	
	@Override
	public int insertResult() {
		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"select id from products_expire order by id desc limit 1");
			if (rs.next()) {
				
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}

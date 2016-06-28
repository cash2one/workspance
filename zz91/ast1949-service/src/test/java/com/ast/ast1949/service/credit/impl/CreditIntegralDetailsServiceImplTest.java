/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-21
 */
package com.ast.ast1949.service.credit.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.credit.CreditIntegralDetailsDo;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.credit.CreditIntegralDetailsService;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-21
 */
public class CreditIntegralDetailsServiceImplTest extends BaseServiceTestCase {

	@Autowired
	CreditIntegralDetailsService creditIntegralDetailsService;

	@Test
	public void testCountIntegralByCompany() {
		clean();
		for (int i = 0; i < 10; i++) {
			createOneRecord(oneTestRecord("key1", i));
		}
		Integer integral = creditIntegralDetailsService
				.countIntegralByCompany(1);
		assertNotNull(integral);
		assertEquals(100, integral.intValue());
	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.credit.impl.CreditIntegralDetailsServiceImpl#countIntegralByOperationKey(java.lang.Integer, java.lang.String)}
	 * .
	 */
	@Test
	public void testCountIntegralByOperationKey() {
		clean();
		for (int i = 0; i < 10; i++) {
			createOneRecord(oneTestRecord("key1", i));
		}
		Integer integral = creditIntegralDetailsService
				.countIntegralByOperationKey(1, "key1");
		assertNotNull(integral);
		assertEquals(100, integral.intValue());
	}

	@Test
	public void testSaveIntegeral() {
//		clean();
//		Integer i = creditIntegralDetailsService.saveIntegeral(1, "key2", 1);
//		assertNotNull(i);
//		assertTrue(i.intValue()>0);
//		
//		CreditIntegralDetailsDo obj= queryOneById(i);
//		assertNotNull(obj);
//		assertEquals("key2", obj.getOperationKey());
	}

	@Test
	public void testSaveIntegralOnExist() {

	}
	
	@Test
	public void testCountIntegralByParentOperationKey(){
		
	}

	/**************************************/
	public void clean() {
		try {
			connection.prepareStatement("delete from credit_integral_details")
					.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Integer createOneRecord(CreditIntegralDetailsDo detail) {
		String sql = "INSERT INTO `credit_integral_details`(`operation_key`,`related_id`,`integral`,`company_id`,`account`,`gmt_created`,`gmt_modified`) "
				+ "VALUES('"
				+ detail.getOperationKey()
				+ "',"
				+ detail.getRelatedId()
				+ ","
				+ detail.getIntegral()
				+ ","
				+ detail.getCompanyId()
				+ ",'"
				+ detail.getAccount()
				+ "',now(),now())";
		try {
			connection.prepareStatement(sql).execute();
			return insertResult();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public CreditIntegralDetailsDo oneTestRecord(String operationKey,
			Integer relatedId) {
		return new CreditIntegralDetailsDo(operationKey, relatedId, 10, 1,
				"account1", null, null);
	}

	public CreditIntegralDetailsDo queryOneById(Integer id) {
		String sql = "SELECT `operation_key`,`related_id`,`integral`,`company_id`,`account`,`gmt_created`,`gmt_modified`FROM `credit_integral_details`";
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				return new CreditIntegralDetailsDo(rs.getString(1), rs
						.getInt(2), rs.getInt(3), rs.getInt(4),
						rs.getString(5), rs.getDate(6), rs.getDate(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

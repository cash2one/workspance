/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-21
 */
package com.ast.ast1949.service.credit.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.credit.CreditIntegralDetailsDo;
import com.ast.ast1949.domain.credit.CreditReferenceDo;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.credit.CreditReferenceService;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-21
 */
public class CreditReferenceServiceImplTest extends BaseServiceTestCase {

	@Autowired
	CreditReferenceService creditReferenceService;

	@Test
	public void testCountReferenceByCompany() {
		clean();
		for (int i = 0; i < 5; i++) {
			createOneRecord(oneTestRecord());
		}

		Integer i = creditReferenceService.countReferenceByCompany(1, null);
		assertNotNull(i);
		assertEquals(5, i.intValue());
	}

	@Test
	public void testCountReferenceByCompany_checkstatus_1() {
		clean();
		for (int i = 0; i < 5; i++) {
			createOneRecord(oneTestRecord("1"));
		}

		Integer i = creditReferenceService.countReferenceByCompany(1, true);
		assertNotNull(i);
		assertEquals(5, i.intValue());
	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.credit.impl.CreditReferenceServiceImpl#deleteReferenceByCompany(java.lang.Integer, java.lang.Integer)}
	 * .
	 */
	@Test
	public void testDeleteReferenceByCompany() {
		clean();
		Integer id = createOneRecord(oneTestRecord("1"));
		Integer integralId = createOneIntegralDetails(oneIntegralDetails(
				"credit_reference", id));

		Integer i = creditReferenceService.deleteReferenceByCompany(id, 1);
		assertNotNull(i);
		assertTrue(i.intValue() > 0);

		CreditIntegralDetailsDo integralObj = queryOneIntegralDetailsById(integralId);
		assertNull(integralObj);
	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.credit.impl.CreditReferenceServiceImpl#insertReferenceByCompany(com.ast.ast1949.domain.credit.CreditReferenceDo)}
	 * .
	 */
	@Test
	public void testInsertReferenceByCompany() {
		clean();
		Integer i = creditReferenceService
				.insertReferenceByCompany(oneTestRecord());
		assertNotNull(i);
		assertTrue(i.intValue() > 0);
	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.credit.impl.CreditReferenceServiceImpl#queryReferenceByCompany(java.lang.Integer)}
	 * .
	 */
	@Test
	public void testQueryReferenceByCompany() {
		clean();
		for (int i = 0; i < 10; i++) {
			createOneRecord(oneTestRecord());
		}

		List<CreditReferenceDo> list = creditReferenceService
				.queryReferenceByCompany(1);

		assertNotNull(list);
		assertEquals(10, list.size());
	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.credit.impl.CreditReferenceServiceImpl#updateCheckStatusByAdmin(java.lang.Integer, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testUpdateCheckStatusByAdmin() {
	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.credit.impl.CreditReferenceServiceImpl#updateReferenceByCompany(com.ast.ast1949.domain.credit.CreditReferenceDo)}
	 * .
	 */
	@Test
	public void testUpdateReferenceByCompany() {
		clean();
		Integer id = createOneRecord(oneTestRecord("1"));
		Integer integralId= createOneIntegralDetails(oneIntegralDetails("credit_reference", id));
		
		CreditReferenceDo obj= new CreditReferenceDo(1, "a1", "name1", "companyName1", "address1",
				"tel1", "fax1", "email1", "details1", null, null, null, null);
		obj.setId(id);
		Integer i = creditReferenceService.updateReferenceByCompany(obj);
		assertNotNull(i);
		
		CreditReferenceDo creditReferenceDo = queryOneById(id);
		
		assertNotNull(creditReferenceDo);
		assertEquals("0", creditReferenceDo.getCheckStatus());
		assertEquals("name1", creditReferenceDo.getName());
		
		CreditIntegralDetailsDo integralDetailsDo = queryOneIntegralDetailsById(integralId);
		assertNull(integralDetailsDo);
		
	}

	/**************************************/
	public void clean() {
		try {
			connection.prepareStatement("delete from credit_reference")
					.execute();
			connection.prepareStatement("delete from credit_integral_details")
			.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Integer createOneRecord(CreditReferenceDo reference) {
		String sql = "INSERT INTO `credit_reference`(`company_id`,`account`,`name`," +
				"`company_name`,`address`,`tel`,`fax`,`email`,`details`," +
				"`check_status`,`check_person`,`gmt_created`,`gmt_modified`)"
				+ "VALUES("
				+ reference.getCompanyId()
				+ ",'"
				+ reference.getAccount()
				+ "','"
				+ reference.getName()
				+ "','"
				+ reference.getCompanyName()
				+ "','"
				+ reference.getAddress()
				+ "','"
				+ reference.getTel()
				+ "','"
				+ reference.getFax()
				+ "','"
				+ reference.getEmail()
				+ "','"
				+ reference.getDetails()
				+ "','"
				+ reference.getCheckStatus()
				+ "','"
				+ reference.getCheckPerson() + "',now(),now())";
		try {
			connection.prepareStatement(sql).execute();
			return insertResult();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public CreditReferenceDo oneTestRecord() {
		return new CreditReferenceDo(1, "a1", "name", "companyName", "address",
				"tel", "fax", "email", "details", "0", "", null, null);
	}

	public CreditReferenceDo oneTestRecord(String checkstatus) {
		return new CreditReferenceDo(1, "a1", "name", "companyName", "address",
				"tel", "fax", "email", "details", checkstatus, "", null, null);
	}

	public Integer createOneIntegralDetails(CreditIntegralDetailsDo detail) {
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

	public CreditIntegralDetailsDo oneIntegralDetails(String operationKey,
			Integer relatedId) {
		return new CreditIntegralDetailsDo(operationKey, relatedId, 10, 1,
				"account1", null, null);
	}

	public CreditIntegralDetailsDo queryOneIntegralDetailsById(Integer id) {
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

	public CreditReferenceDo queryOneById(Integer id) {
		String sql = "SELECT `company_id`,`account`,`name`,`company_name`,`address`,`tel`,`fax`,`email`,`details`,`check_status`,`check_person`,`gmt_created`,`gmt_modified` FROM `credit_reference`";
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				return new CreditReferenceDo(rs.getInt(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getString(5), rs
						.getString(6), rs.getString(7), rs.getString(8), rs
						.getString(9), rs.getString(10), rs.getString(11), rs
						.getDate(12), rs.getDate(13));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

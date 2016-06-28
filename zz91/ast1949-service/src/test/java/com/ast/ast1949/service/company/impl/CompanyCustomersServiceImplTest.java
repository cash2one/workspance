/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-29
 */
package com.ast.ast1949.service.company.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.dto.company.CompanyCustomersDTO;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.company.CompanyCustomersService;

/**
 * 
 */
public class CompanyCustomersServiceImplTest extends BaseServiceTestCase {

	@Autowired
	private CompanyCustomersService companyCustomersService;

//	public void test_queryCompanyCustomersByName() {
//		clean();
//		CompanyCustomersDTO companyCustomersDTO = new CompanyCustomersDTO();
//		//	
//		companyCustomersService
//				.queryCompanyCustomersForFront(companyCustomersDTO);
//	}

//	public void test_queryCompanyCustomersByCondition_isNull() {
//		clean();
//		try {
//			companyCustomersService.queryCompanyCustomersForFront(null);
//			fail();
//		} catch (IllegalArgumentException e) {
//			assertEquals("companyCustomersDTO is not null", e.getMessage());
//		}
//	}
//
//	public void test_queryCompanyCustomersByGroupId() {
//		clean();
//		int groupId = 1;
//		companyCustomersService.queryCompanyCustomersByGroupId(groupId);
//
//	}
//
//	public void test_queryCompanyCustomersByGroupId_isNull() {
//		clean();
//		try {
//			companyCustomersService.queryCompanyCustomersByGroupId(null);
//			fail();
//		} catch (IllegalArgumentException e) {
//			assertEquals("groupId is not null", e.getMessage());
//		}
//	}

	public void test_queryCompanyCustomersById() {

	}

	public void test_queryCompanyCustomersById_isNull() {
		clean();
		try {
			companyCustomersService.queryCompanyCustomersById(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("id is not null", e.getMessage());
		}

	}

	public void test_insertCompanyCustomers_isNull() {
		clean();
		try {
			companyCustomersService.insertCompanyCustomers(null);
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals("companyCustomersDO is not null", e.getMessage());
		}

	}
    public void test_updateCompanyCustomers(){
    	clean();
    	try {
			companyCustomersService.updateCompanyCustomers(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("companyCustomersDO is not null", e.getMessage());
		}
    }
	private void clean() {
		try {
			cleanupcompanyCustomers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 清除表中的数据
	protected void cleanupcompanyCustomers() throws SQLException {
		connection.prepareStatement("delete from company_customers").execute();
	}
}

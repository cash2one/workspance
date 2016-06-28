package com.ast.ast1949.service.company.impl;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.company.CompanyCustomersGroupDO;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.company.CompanyCustomersGroupService;

public class CompanyCustomersGroupServiceImplTest extends BaseServiceTestCase {

	@Autowired
	private CompanyCustomersGroupService companyCustomersGroupService;

	public void test_queryCompanyCustomersGroup() {
//		clean();
//		CompanyCustomersGroupDO companyCustomersGroupDO = new CompanyCustomersGroupDO();
//		companyCustomersGroupDO.setId(1);
//		companyCustomersGroupDO.setName("aaaaaa");
//		companyCustomersGroupDO.setRemark("bbb");
//		companyCustomersGroupDO.setAccount("fdfd");
//		companyCustomersGroupDO.setCompanyId(1);
//		companyCustomersGroupDO.setGmtCreated(new Date());
//		companyCustomersGroupDO.setGmtModified(new Date());
//		companyCustomersGroupService
//				.insertCompanyCustomersGroup(companyCustomersGroupDO);
//		List<CompanyCustomersGroupDO> list = companyCustomersGroupService
//				.queryCompanyCustomersGroup();
//		assertTrue(list.size() > 0);
//		assertTrue(list.size() == 1);
	}

	public void test_insertCompanyCustomersGroup() {
		clean();
		CompanyCustomersGroupDO companyCustomersGroupDO = new CompanyCustomersGroupDO();
		companyCustomersGroupDO.setId(1);
		companyCustomersGroupDO.setName("aaaaaa");
		companyCustomersGroupDO.setRemark("bbb");
		companyCustomersGroupDO.setAccount("fdfd");
		companyCustomersGroupDO.setCompanyId(1);
		companyCustomersGroupDO.setGmtCreated(new Date());
		companyCustomersGroupDO.setGmtModified(new Date());
		Integer i = companyCustomersGroupService
				.insertCompanyCustomersGroup(companyCustomersGroupDO);
		assertTrue(i > 0);
	}

	public void test_insertCompanyCustomersGroup_isNull() {
		clean();
		try {
			companyCustomersGroupService.insertCompanyCustomersGroup(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("companyCustomersGroupDO is not null", e.getMessage());
		}

	}

	public void test_updateCompanyCustomersGroup() {
		clean();
		CompanyCustomersGroupDO companyCustomersGroupDO = new CompanyCustomersGroupDO();
		companyCustomersGroupDO.setId(1);
		companyCustomersGroupDO.setName("aaaaaa");
		companyCustomersGroupDO.setRemark("bbb");
		companyCustomersGroupDO.setAccount("fdfd");
		companyCustomersGroupDO.setCompanyId(1);
		companyCustomersGroupDO.setGmtCreated(new Date());
		companyCustomersGroupDO.setGmtModified(new Date());
		companyCustomersGroupService
				.insertCompanyCustomersGroup(companyCustomersGroupDO);

		CompanyCustomersGroupDO companyCustomersGroupDO1 = new CompanyCustomersGroupDO();
		companyCustomersGroupDO1.setId(1);
		companyCustomersGroupDO1.setName("aaaaaa");
		companyCustomersGroupDO1.setRemark("bbb");
		companyCustomersGroupDO1.setAccount("fdfd");
		companyCustomersGroupDO1.setCompanyId(1);
		companyCustomersGroupDO1.setGmtCreated(new Date());
		companyCustomersGroupDO1.setGmtModified(new Date());
		Integer i = companyCustomersGroupService
				.updateCompanyCustomersGroup(companyCustomersGroupDO);
		assertTrue(i > 0);
	}

	public void test_test_updateCompanyCustomersGroup_isNull() {
		clean();
		try {
			companyCustomersGroupService.updateCompanyCustomersGroup(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("companyCustomersGroupDO is not null", e.getMessage());
		}

	}

	public void test_deleteCompanyCustomersGroupById() {
		clean();
		CompanyCustomersGroupDO companyCustomersGroupDO = new CompanyCustomersGroupDO();
		companyCustomersGroupDO.setId(1);
		companyCustomersGroupDO.setName("aaaaaa");
		companyCustomersGroupDO.setRemark("bbb");
		companyCustomersGroupDO.setAccount("fdfd");
		companyCustomersGroupDO.setCompanyId(1);
		companyCustomersGroupDO.setGmtCreated(new Date());
		companyCustomersGroupDO.setGmtModified(new Date());
		companyCustomersGroupService
				.insertCompanyCustomersGroup(companyCustomersGroupDO);

		Integer i = companyCustomersGroupService
				.deleteCompanyCustomersGroupById(companyCustomersGroupDO
						.getId());
		assertTrue(i > 0);
	}

	public void test_deleteCompanyCustomersGroupById_isNull() {
		clean();
		try {
			companyCustomersGroupService.deleteCompanyCustomersGroupById(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("id is not null", e.getMessage());
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
		connection.prepareStatement("delete from company_customers_group")
				.execute();
	}

}

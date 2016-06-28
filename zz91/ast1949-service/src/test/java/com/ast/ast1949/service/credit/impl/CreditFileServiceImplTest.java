/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-21
 */
package com.ast.ast1949.service.credit.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.credit.CreditFileDo;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.credit.CreditFileService;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-21
 */
public class CreditFileServiceImplTest extends BaseServiceTestCase {

	@Autowired
	CreditFileService creditFileService;

	@Test
	public void testDeleteFileById() {
		clean();
		Integer id = createOneRecord(oneTestRecord());

		Integer i = creditFileService.deleteFileById(id,1);
		assertNotNull(i);
		assertEquals(1, i.intValue());
	}

	@Test
	public void testInsertFileByCompany() {
//		clean();
//		creditFileService.insertFileByCompany(oneTestRecord());
	}

	@Test
	public void testQueryFileByCompany() {
		clean();
		for (int i = 0; i < 10; i++) {
			CreditFileDo o = new CreditFileDo(1, "account1", "10001000",
					"filename" + i, new Date(), new Date(), "", "", "", "", "",
					"", "0", "", null, null);
			createOneRecord(o);
		}

		List<CreditFileDo> list = creditFileService.queryFileByCompany(1);

		assertNotNull(list);
		assertEquals(10, list.size());
	}

	@Test
	public void testUpdateCheckStatusByAdmin() {
//		clean();
//		Integer id = createOneRecord(oneTestRecord());
//		Integer i = creditFileService
//				.updateCheckStatusByAdmin(id, "1", "admin");
//
//		assertNotNull(i);
//		assertEquals(1, i.intValue());
//
//		CreditFileDo o = queryOneById(id);
//		assertNotNull(o);
//		assertEquals("1", o.getCheckStatus());
//		assertEquals("admin", o.getCheckPerson());
	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.credit.impl.CreditFileServiceImpl#updateFileById(com.ast.ast1949.domain.credit.CreditFileDo)}
	 * .
	 */
	@Test
	public void testUpdateFileById() {
		clean();
		Integer id = createOneRecord(oneTestRecord());

		CreditFileDo file=new CreditFileDo(1,
				"account2", "10001001", "filename2", new Date(), new Date(),
				"", "", "", "", "", "", "0", "", null, null);
		file.setId(id);
		Integer i = creditFileService.updateFileById(file);

		assertNotNull(i);

		CreditFileDo obj = queryOneById(id);
		assertEquals("account1", obj.getAccount());
		assertEquals("10001001", obj.getCategoryCode());
		assertEquals("filename2", obj.getFileName());
	}

	/**************************************/
	public void clean() {
		try {
			connection.prepareStatement("delete from credit_file").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Integer createOneRecord(CreditFileDo file) {
		String sql="";
		try {
			sql = "INSERT INTO `credit_file`(`company_id`,`account`,`category_code`,`file_name`,`start_time`,`end_time`,`file_number`,`organization`,`tel`,`website`,`pic_name`,`introduction`,`check_status`,`check_person`,`gmt_created`,`gmt_modified`)"
				+ "VALUES("
				+ file.getCompanyId()
				+ ",'"
				+ file.getAccount()
				+ "','"
				+ file.getCategoryCode()
				+ "','"
				+ file.getFileName()
				+ "','2010-1-23 00:20:20','2011-2-23 00:20:20','"
				+ file.getFileNumber()
				+ "','"
				+ file.getOrganization()
				+ "','"
				+ file.getTel()
				+ "','"
				+ file.getWebsite()
				+ "','"
				+ file.getPicName()
				+ "','"
				+ file.getIntroduction()
				+ "','"
				+ file.getCheckStatus()
				+ "','"
				+ file.getCheckPerson()
				+ "',now(),now())";
			connection.prepareStatement(sql).execute();
			return insertResult();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public CreditFileDo oneTestRecord() {
		return new CreditFileDo(1, "account1", "10001000", "filename1",
				new Date(), new Date(), "", "", "", "", "", "", "0", "", null,
				null);
	}

	public CreditFileDo queryOneById(int id) {
		String sql = "SELECT `company_id`,`account`,`category_code`,`file_name`,`start_time`,`end_time`,`file_number`,`organization`,`tel`,`website`,`pic_name`,`introduction`,`check_status`,`check_person`,`gmt_created`,`gmt_modified` FROM `credit_file`";
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				return new CreditFileDo(rs.getInt(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getDate(5), rs
						.getDate(6), rs.getString(7), rs.getString(8), rs
						.getString(9), rs.getString(10), rs.getString(11), rs
						.getString(12), rs.getString(13), rs.getString(14), rs
						.getDate(15), rs.getDate(16));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}

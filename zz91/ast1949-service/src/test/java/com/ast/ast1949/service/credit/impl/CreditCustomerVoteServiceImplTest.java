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

import com.ast.ast1949.domain.credit.CreditCustomerVoteDo;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.credit.CreditCustomerVoteService;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-21
 */
public class CreditCustomerVoteServiceImplTest extends BaseServiceTestCase {

	@Autowired
	CreditCustomerVoteService creditCustomerVoteService;

	@Test
	public void testDeleteVoteByFromCompany() {
		clean();
		Integer i = createOneRecord(oneTestRecord());
		Integer j = creditCustomerVoteService.deleteVoteByFromCompany(i, 1);
		assertNotNull(j);
		assertEquals(1, j.intValue());
	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.credit.impl.CreditCustomerVoteServiceImpl#pageVoteByFromCompany(java.lang.Integer, java.lang.String)}
	 * .
	 */
	@Test
	public void testPageVoteByFromCompany() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.credit.impl.CreditCustomerVoteServiceImpl#pageVoteWithReplyByToCompany(java.lang.Integer, com.ast.ast1949.dto.PageDto)}
	 * .
	 */
	@Test
	public void testPageVoteByToCompany() {
//		fail("Not yet implemented");
	}

	@Test
	public void testReplyToVote() {
		clean();
		Integer id = createOneRecord(oneTestRecord());

		Integer replyid = creditCustomerVoteService.replyToVote(id, "my new reply content");
		assertNotNull(replyid);
		assertTrue(replyid > 0);
		
		CreditCustomerVoteDo obj= queryOneById(id);
		assertEquals("my new reply content", obj.getReplyContent());
	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.credit.impl.CreditCustomerVoteServiceImpl#updateCheckStatusByAdmin(java.lang.Integer, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testUpdateCheckStatusByAdmin() {
//		clean();
//		Integer id = createOneRecord(oneTestRecord());
//
//		Integer i = creditCustomerVoteService.updateCheckStatusByAdmin(id, "1",
//				"admin");
//		assertNotNull(i);
//		CreditCustomerVoteDo obj= queryOneById(id);
//		assertNotNull(obj);
//		assertEquals("1", obj.getCheckStatus());
//		assertEquals("admin", obj.getCheckPerson());
	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.credit.impl.CreditCustomerVoteServiceImpl#voteToCompany(com.ast.ast1949.domain.credit.CreditCustomerVoteDo)}
	 * .
	 */
	@Test
	public void testVoteToCompany() {
		clean();
		CreditCustomerVoteDo obj = new CreditCustomerVoteDo(1, "account1", 2,
				"1", null,null,"0", "", null, null);
		
		Integer id=creditCustomerVoteService.voteToCompany(obj);
		assertNotNull(id);
		assertTrue(id.intValue()>0);
	}

	/**************************************/
	public void clean() {
		try {
			connection.prepareStatement("delete from credit_customer_vote")
					.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Integer createOneRecord(CreditCustomerVoteDo vote) {
		String sql = "INSERT INTO `credit_customer_vote`(`from_company_id`,`from_account`,`to_company_id`,`status`,`content`,`reply_content`,`check_status`,`check_person`,`gmt_created`,`gmt_modified`)"
				+ " VALUES("
				+ vote.getFromCompanyId()
				+ ",'"
				+ vote.getFromAccount()
				+ "',"
				+ vote.getToCompanyId()
				+ ",'"
				+ vote.getStatus()
				+ "','vote content','reply content','"
				+ vote.getCheckStatus()
				+ "','"
				+ vote.getCheckPerson() + "',now(),now())";
		try {
			connection.prepareStatement(sql).execute();
			return insertResult();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public CreditCustomerVoteDo oneTestRecord() {
		CreditCustomerVoteDo o = new CreditCustomerVoteDo(1, "account1", 2,
				"1", null,null,"0", "", null, null);
		return o;
	}
	
	public CreditCustomerVoteDo queryOneById(int id) {
		String sql = "SELECT `from_company_id`,`from_account`,`to_company_id`,`status`,`content`,`reply_content`,`check_status`,`check_person`,`gmt_created`,`gmt_modified`FROM `credit_customer_vote`";
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				return new CreditCustomerVoteDo(rs.getInt(1), rs.getString(2),
						rs.getInt(3), rs.getString(4), rs
								.getString(5),rs.getString(6), rs.getString(7),
						rs.getString(8), null, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

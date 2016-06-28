/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-10
 */
package com.ast.ast1949.service.site.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.site.FeedbackDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.site.FeedbackDto;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.site.FeedbackService;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-10
 */
public class FeedbackServiceImplTest extends BaseServiceTestCase {

	@Autowired
	FeedbackService feedbackService;

	@Test
	public void testDeleteFeedbackByAdmin() {
		clean();
		Integer id = createOneRecord(oneTestRecord(1, "0", null));
		Integer impact = feedbackService.deleteFeedbackByAdmin(id);
		assertNotNull(impact);
		assertEquals(1, impact.intValue());

		FeedbackDo feedback = queryOneById(id);
		assertNull(feedback);
	}

	@Test
	public void testInsertFeedbackByMember() {
		clean();
		FeedbackDo feedback = new FeedbackDo(1, "account", "", null, "test",
				"account", null, null, null, null, null, null);
		Integer id = feedbackService.insertFeedbackByMember(feedback);
		assertNotNull(id);
		assertTrue(id.intValue() > 0);

		FeedbackDo newfeedback = queryOneById(id);
		assertNotNull(newfeedback);
		assertEquals(FeedbackService.CATEGORY_MEMBER, newfeedback.getCategory()
				.intValue());
		assertEquals(FeedbackService.STATUS_APPLY, newfeedback.getCheckStatus());

	}

	@Test
	public void testInsertFeedbackByScore() {
		clean();
		FeedbackDo feedback = new FeedbackDo(0, "test", "", null, "title",
				"content", null, null, null, null, null, null);
		Integer id = feedbackService.insertFeedbackByScore(feedback);
		assertNotNull(id);
		assertTrue(id.intValue() > 0);

		FeedbackDo newfeedback = queryOneById(id);
		assertNotNull(newfeedback);
		assertEquals(FeedbackService.CATEGORY_SCORE, newfeedback.getCategory()
				.intValue());
		assertEquals("test", newfeedback.getAccount());
		assertEquals(0, newfeedback.getCompanyId().intValue());
		assertEquals(FeedbackService.STATUS_APPLY, newfeedback.getCheckStatus());
	}

	@Test
	public void testInsertFeedbackByVIP() {
		clean();
		FeedbackDo feedback = new FeedbackDo(1, "account", "", null, "title",
				"content", null, null, null, null, null, null);
		Integer id = feedbackService.insertFeedbackByVIP(feedback);
		assertNotNull(id);
		assertTrue(id.intValue() > 0);

		FeedbackDo newfeedback = queryOneById(id);
		assertNotNull(newfeedback);
		assertEquals(FeedbackService.CATEGORY_VIP, newfeedback.getCategory()
				.intValue());
		assertEquals(FeedbackService.STATUS_APPLY, newfeedback.getCheckStatus());
	}

	@Test
	public void testNotReplyFeedback() {
		clean();
		Integer id = createOneRecord(oneTestRecord(1, "0", null));
		Integer i = feedbackService.notReplyFeedback(id, "mays");
		assertNotNull(i);
		assertEquals(1, i.intValue());

		FeedbackDo feedback = queryOneById(id);
		assertNotNull(feedback);
		assertEquals(FeedbackService.STATUS_NO_REPLY, feedback.getCheckStatus());
		assertEquals("mays", feedback.getCheckPerson());
	}

	@Test
	public void testPageFeedbackByAdmin() {
		clean();
		manyTestRecord(7, 1, "0", null);
		manyTestRecord(3, 2, "0", null);
		
		PageDto<FeedbackDto> page = new PageDto<FeedbackDto>();
		FeedbackDo feedback = new FeedbackDo();
		page.setStartIndex(0);
		page.setPageSize(5);
		feedback.setCategory(2);
		page = feedbackService.pageFeedbackByAdmin(feedback, page);
		
		assertNotNull(page.getRecords());
		assertEquals(3, page.getRecords().size());
		assertEquals(3, page.getTotalRecords().intValue());
		
		page=new PageDto<FeedbackDto>();
		feedback = new FeedbackDo();
		page.setStartIndex(0);
		page.setPageSize(5);
		feedback.setCategory(1);
		page = feedbackService.pageFeedbackByAdmin(feedback, page);
		assertNotNull(page.getRecords());
		assertEquals(5, page.getRecords().size());
		assertEquals(7, page.getTotalRecords().intValue());
		
		page=new PageDto<FeedbackDto>();
		feedback = new FeedbackDo();
		page.setStartIndex(5);
		page.setPageSize(5);
		feedback.setCategory(1);
		page = feedbackService.pageFeedbackByAdmin(feedback, page);
		assertNotNull(page.getRecords());
		assertEquals(2, page.getRecords().size());
		assertEquals(7, page.getTotalRecords().intValue());
		
	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.site.impl.FeedbackServiceImpl#pageFeedbackByCategory(java.lang.Integer, java.lang.String, com.ast.ast1949.dto.PageDto)}
	 * .
	 */
	@Test
	public void testPageFeedbackByCategory() {
		clean();
		manyTestRecord(7, 1, "0", null);
		manyTestRecord(7, 2, "0", null);
		
		PageDto<FeedbackDo> page = new PageDto<FeedbackDo>();
		page.setStartIndex(0);
		page.setPageSize(5);
		page = feedbackService.pageFeedbackByCategory(1, "", page);
		
		assertNotNull(page.getRecords());
		assertEquals(5, page.getRecords().size());
		assertEquals(7, page.getTotalRecords().intValue());
		
		page = new PageDto<FeedbackDo>();
		page.setStartIndex(5);
		page.setPageSize(5);
		page = feedbackService.pageFeedbackByCategory(1, "", page);
		
		assertNotNull(page.getRecords());
		assertEquals(2, page.getRecords().size());
		assertEquals(7, page.getTotalRecords().intValue());
	}

//	@Test
//	public void testPageFeedbackByCrm() {
//		clean();
//		manyTestRecord(7, 3, "0", "mays");
//		manyTestRecord(7, 3, "0", "noperson");
//		
//		PageDto<FeedbackDto> page = new PageDto<FeedbackDto>();
//		FeedbackDo feedback = new FeedbackDo();
//		page.setStartIndex(0);
//		page.setPageSize(5);
//		feedback.setCheckPerson("mays");
//		page = feedbackService.pageFeedbackByCrm(feedback,null, page);
//		assertNotNull(page.getRecords());
//		assertEquals(5, page.getRecords().size());
//		assertEquals(7, page.getTotalRecords().intValue());
//		
//		page=new PageDto<FeedbackDto>();
//		feedback = new FeedbackDo();
//		page.setStartIndex(5);
//		page.setPageSize(5);
//		feedback.setCheckPerson("mays");
//		page = feedbackService.pageFeedbackByCrm(feedback,null, page);
//		assertNotNull(page.getRecords());
//		assertEquals(2, page.getRecords().size());
//		assertEquals(7, page.getTotalRecords().intValue());
//		
//		page=new PageDto<FeedbackDto>();
//		feedback = new FeedbackDo();
//		page.setStartIndex(5);
//		page.setPageSize(5);
//		page = feedbackService.pageFeedbackByCrm(feedback,null, page);
//		assertNotNull(page.getRecords());
//		assertEquals(5, page.getRecords().size());
//		assertEquals(14, page.getTotalRecords().intValue());
//	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.site.impl.FeedbackServiceImpl#pageFeedbackHistoryByUser(java.lang.Integer, java.lang.String, java.lang.Integer, com.ast.ast1949.dto.PageDto)}
	 * .
	 */
	@Test
	public void testPageFeedbackHistoryByUser() {
		clean();
		manyTestRecord(7, 1, "0", null);
		manyTestRecord(7, 2, "0", null);
		
		PageDto<FeedbackDo> page = new PageDto<FeedbackDo>();
		page.setStartIndex(0);
		page.setPageSize(5);
		page = feedbackService.pageFeedbackHistoryByUser(1, null, 1, page);
		
		assertNotNull(page.getRecords());
		assertEquals(5, page.getRecords().size());
		assertEquals(7, page.getTotalRecords().intValue());
		
		page = new PageDto<FeedbackDo>();
		page.setStartIndex(5);
		page.setPageSize(5);
		page = feedbackService.pageFeedbackHistoryByUser(1, null, 1, page);
		
		assertNotNull(page.getRecords());
		assertEquals(2, page.getRecords().size());
		assertEquals(7, page.getTotalRecords().intValue());
	}

	@Test
	public void testQueryFeedbackById() {
		clean();
		Integer id = createOneRecord(oneTestRecord(1, "0", null));
		FeedbackDo feedback = feedbackService.queryFeedbackById(id);
		assertNotNull(feedback);
		assertEquals(1, feedback.getCompanyId().intValue());
		assertEquals("account", feedback.getAccount());
		assertEquals("title", feedback.getTitle());
		assertEquals("content", feedback.getContent());
		assertEquals("0", feedback.getCheckStatus());
	}

	@Test
	public void testReplyFeedback() {
		clean();
		Integer id = createOneRecord(oneTestRecord(1, "0", null));
		Integer i = feedbackService.replyFeedback(id, "reply content", "mays");
		assertNotNull(i);
		assertTrue(i.intValue()>0);
		
		FeedbackDo feedback = queryOneById(id);
		assertEquals("1", feedback.getCheckStatus());
		assertEquals("reply content", feedback.getReplyContent());
		assertEquals("mays", feedback.getCheckPerson());
		
	}

	/**************************************/
	public void clean() {
		try {
			connection.prepareStatement("delete from feedback").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Integer createOneRecord(FeedbackDo feedback) {
		String sql = "";
		try {
			sql = "INSERT INTO `feedback`(`company_id`,`account`,`email`,`category`,"
					+ "`title`,`content`,`reply_content`,`gmt_created`,`gmt_reply`,`gmt_modified`,"
					+ "`check_status`,`check_person`)"
					+ "VALUES("
					+ feedback.getCompanyId()
					+ ",'"
					+ feedback.getAccount()
					+ "','"
					+ feedback.getEmail()
					+ "',"
					+ feedback.getCategory()
					+ ",'"
					+ feedback.getTitle()
					+ "','"
					+ feedback.getContent()
					+ "','"
					+ feedback.getReplyContent()
					+ "',now(),now(),now(),'"
					+ feedback.getCheckStatus()
					+ "','"
					+ feedback.getCheckPerson() + "')";
			connection.prepareStatement(sql).execute();
			return insertResult();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public FeedbackDo oneTestRecord(Integer category, String checkStatus,
			String checkPerson) {
		return new FeedbackDo(1, "account", "email", category, "title", "content",
				"content", null, null, null, checkStatus, checkPerson);
	}

	public FeedbackDo queryOneById(int id) {
		String sql = "select `company_id`,`account`,`email`,`category`,"
				+ "`title`,`content`,`reply_content`,`gmt_created`,`gmt_reply`,"
				+ "`gmt_modified`,`check_status`,`check_person` from `feedback` "
				+ "where id=" + id;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				return new FeedbackDo(rs.getInt(1), rs.getString(2), rs
						.getString(3), rs.getInt(4), rs.getString(5), rs
						.getString(6), rs.getString(7), rs.getDate(8), rs
						.getDate(9), rs.getDate(10), rs.getString(11), rs
						.getString(12));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void manyTestRecord(int max, Integer category, String checkStatus,
			String checkPerson) {
		for(int i=0;i<max;i++){
			createOneRecord(oneTestRecord(category, checkStatus, checkPerson));
		}
	}
}

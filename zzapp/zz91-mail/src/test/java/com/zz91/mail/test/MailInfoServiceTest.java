/**
 * Project name: zz91-mail
 * File name: MailServiceTest.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.mail.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Assert;

import com.zz91.mail.domain.MailInfoDomain;
import com.zz91.mail.domain.dto.PageDto;
import com.zz91.mail.service.MailInfoService;
import com.zz91.util.datetime.DateUtil;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-11-10
 */
public class MailInfoServiceTest extends BaseServiceTestCase {
	@Resource
	private MailInfoService mailInfoService;

	// 添加
	public void test_insert_mail() {
		clear();
		try {
			Integer i = mailInfoService
					.insert(getMail("emailTitle", new Date()));
			MailInfoDomain domain = queryOne(i);
			assertTrue(i.intValue() > 0);
			assertEquals("emailTitle", domain.getEmailTitle());
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	// 修改
	public void test_update() {
		clear();
		try {
			Integer id = insert(getMail("emailTtle", new Date()));
			MailInfoDomain updateIno = getMail("newtitle", new Date());
			updateIno.setId(id);
			Integer a = mailInfoService.update(updateIno);
			assertTrue(a.intValue() > 0);

			MailInfoDomain info = queryOne(id);
			assertEquals("newtitle", info.getEmailTitle());
		} catch (ParseException e) {
		}
	}

	/***********************************************************************************/
//重新发送 更新status
	public void test_resend() {
		clear();
		try {
			Integer id=insert(getMail("emailtitle", new Date()));
			MailInfoDomain resenDomain=getMail("emailtitle", new Date());
			resenDomain.setId(id);
			Boolean a=mailInfoService.resend(id);
			assertTrue(a.booleanValue());
			
			MailInfoDomain infoDomain=queryOne(id);
			assertTrue(infoDomain.getSendStatus()==0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	//查看发送的邮件
	public void test_query_sendMail(){
		clear();
		try {
			Integer id=insert(getMail("emailtitle", new Date()));
			MailInfoDomain resenDomain=getMail("emailtitle", new Date());
			resenDomain.setId(id);
			Boolean a=mailInfoService.sendMail("title", "receiver", "content","sender");
			assertTrue(a.booleanValue());
			
			MailInfoDomain infoDomain=queryOne(id);
			assertTrue(infoDomain.getSendStatus()==2);
		} catch (ParseException e) {
		e.printStackTrace();
		}
	}

	// 查询
	public void test_queryOne() {
		clear();
		try {
			Integer id = insert(getMail("查询测试", new Date()));
			MailInfoDomain domain = mailInfoService.queryOne(id);
			assertNotNull(domain);
			assertEquals("查询测试", domain.getEmailTitle());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	// 删除
	public void test_deleteById_id_null(Integer id) {
		clear();
		Integer i = mailInfoService.deleteById(null);
		Assert.assertNotNull(i);
		Assert.assertEquals(i, 0);

	}

	public void test_deleteById_id_notnull() {
		clear();
		createOne(1);
		Integer id = createOne(2);
		createOne(3);
		Assert.assertNotNull(id);
		Integer i = mailInfoService.deleteById(id);
		Assert.assertNotNull(i);
		Assert.assertEquals(i, 1);

	}

	// 分页
	public void test_page_只测分页() throws ParseException {
		clear();
		insertMany(7, "ceshi", DateUtil.getDate("2012-04-01", "yyyy-MM-dd"));
		insertMany(3, "ceshi", DateUtil.getDate("2012-04-02", "yyyy-MM-dd"));
		insertMany(2, "ceshi", DateUtil.getDate("2012-04-03", "yyyy-MM-dd"));
		PageDto<MailInfoDomain> page = new PageDto<MailInfoDomain>();
		page.setStart(0);
		page.setLimit(5);
		page = mailInfoService.pageMail(null, null, 0, page);
		assertNotNull(page.getRecords());
		assertEquals(5, page.getRecords().size());
		assertEquals(12, page.getTotals().intValue());

		page.setStart(8);
		page.setLimit(5);
		page = mailInfoService.pageMail(null, null, 0, page);
		assertNotNull(page.getRecords());
		assertEquals(4, page.getRecords().size());
		assertEquals(12, page.getTotals().intValue());

	}

	public void test_page_测试条件_from() throws ParseException {
		clear();
		insertMany(7, "ceshi", DateUtil.getDate("2012-04-01", "yyyy-MM-dd"));
		insertMany(3, "ceshi", DateUtil.getDate("2012-04-02", "yyyy-MM-dd"));
		insertMany(2, "ceshi", DateUtil.getDate("2012-04-03", "yyyy-MM-dd"));
		PageDto<MailInfoDomain> page = new PageDto<MailInfoDomain>();
		page.setStart(0);
		page.setLimit(5);
		page = mailInfoService.pageMail(DateUtil.getDate("2012-04-01",
				"yyyy-MM-dd"), null, 0, page);
		assertNotNull(page.getRecords());
		assertEquals(5, page.getRecords().size());
		assertEquals(12, page.getTotals().intValue());

		page.setStart(8);
		page.setLimit(5);
		page = mailInfoService.pageMail(DateUtil.getDate("2012-04-01",
				"yyyy-MM-dd"), null, 0, page);
		assertNotNull(page.getRecords());
		assertEquals(4, page.getRecords().size());
		assertEquals(12, page.getTotals().intValue());

	}

	public void test_page_测试条件_to() throws ParseException {
		clear();
		insertMany(7, "ceshi", DateUtil.getDate("2012-04-01", "yyyy-MM-dd"));
		insertMany(3, "ceshi", DateUtil.getDate("2012-04-02", "yyyy-MM-dd"));
		insertMany(2, "ceshi", DateUtil.getDate("2012-04-03", "yyyy-MM-dd"));
		PageDto<MailInfoDomain> page = new PageDto<MailInfoDomain>();
		page.setStart(0);
		page.setLimit(5);
		page = mailInfoService.pageMail(null, DateUtil.getDate("2012-04-03",
				"yyyy-MM-dd"), 0, page);
		assertNotNull(page.getRecords());
		assertEquals(5, page.getRecords().size());
		assertEquals(12, page.getTotals().intValue());

		page.setStart(8);
		page.setLimit(5);
		page = mailInfoService.pageMail(null, DateUtil.getDate("2012-04-03",
				"yyyy-MM-dd"), 0, page);
		assertNotNull(page.getRecords());
		assertEquals(4, page.getRecords().size());
		assertEquals(12, page.getTotals().intValue());

	}

	public void test_page_测试条件_from_to() throws ParseException {
		clear();
		insertMany(7, "ceshi", DateUtil.getDate("2012-04-01", "yyyy-MM-dd"));
		insertMany(3, "ceshi", DateUtil.getDate("2012-04-02", "yyyy-MM-dd"));
		insertMany(2, "ceshi", DateUtil.getDate("2012-04-03", "yyyy-MM-dd"));
		PageDto<MailInfoDomain> page = new PageDto<MailInfoDomain>();
		page.setStart(0);
		page.setLimit(5);
		page = mailInfoService.pageMail(DateUtil.getDate("2012-04-01",
				"yyyy-MM-dd"), DateUtil.getDate("2012-04-03", "yyyy-MM-dd"), 0,
				page);
		assertNotNull(page.getRecords());
		assertEquals(5, page.getRecords().size());
		assertEquals(12, page.getTotals().intValue());

		page.setStart(8);
		page.setLimit(5);
		page = mailInfoService.pageMail(DateUtil.getDate("2012-04-01",
				"yyyy-MM-dd"), DateUtil.getDate("2012-04-03", "yyyy-MM-dd"), 0,
				page);
		assertNotNull(page.getRecords());
		assertEquals(4, page.getRecords().size());
		assertEquals(12, page.getTotals().intValue());
	}

	private void clear() {
		try {
			connection.prepareStatement("delete from mail_info").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Integer createOne(Integer idx) {
		int randomPriority = (int) (Math.random() * 3);
		String sql = "insert into mail_info (template_id,email_title,email_content,"
				+ "receiver_name,receiver_email,send_status,is_delete,gmt_post,"
				+ "gmt_created,gmt_modified,send_email,send_name,send_password,"
				+ "send_host,priority) "
				+ "values(1,'邮件发送测试','邮件正文<h2>小标题"
				+ idx
				+ "</h2>','receiver"
				+ idx
				+ "@example.com','receive"
				+ idx
				+ "@example.com',0,0,now(),now(),now(),'master@zz91.cn','master@zz91.cn','88888888','mail.zz91.cn','"
				+ randomPriority + "')";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertResult();
	}

	private MailInfoDomain getMail(String emailTitle, Date gmtPost)
			throws ParseException {
		return new MailInfoDomain(null, "sender", "receiver", emailTitle,
				"templateId", null, null, "content", gmtPost, 2, 0);

	}

	private void insertMany(int max, String emailTitle, Date gmtPost)
			throws ParseException {
		for (int i = 0; i < max; i++) {
			insert(getMail(emailTitle + i, gmtPost));
		}
	}

	private Integer insert(MailInfoDomain mailInfoDomain) {
		String sql = "insert into mail_info(id,send_email,receiver_email,email_title,email_content,send_status,gmt_created,gmt_modified,gmt_post,"
				+ "template_id,send_name,send_password,send_host,priority,nickname)"
				+ "values("
				+ mailInfoDomain.getId()
				+ ",'"
				+ mailInfoDomain.getSender()
				+ "','"
				+ mailInfoDomain.getReceiver()
				+ "','"
				+ mailInfoDomain.getEmailTitle()
				+ "','"
				+ mailInfoDomain.getContent()
				+ "','"
				+ mailInfoDomain.getSendStatus()
				+ "',"
				+ mailInfoDomain.getGmtCreated()
				+ ","
				+ mailInfoDomain.getGmtModified()
				+ ",'"
				+ DateUtil.toString(mailInfoDomain.getGmtPost(), "yyyy-MM-dd")
				+ "','"
				+ mailInfoDomain.getTemplateId()
				+ "','"
				+ mailInfoDomain.getSendName()
				+ "','"
				+ mailInfoDomain.getSendPassword()
				+ "','"
				+ mailInfoDomain.getSendHost()
				+ "',"
				+ mailInfoDomain.getPriority()
				+ ",'"
				+ mailInfoDomain.getNickname() + "')";

		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertResult();
	}

	private MailInfoDomain queryOne(Integer id) {
		String sql = "select id,send_email,receiver_email,email_title,template_id,gmt_created,gmt_modified,"
				+ "email_content,gmt_post,send_status,priority from mail_info where id="
				+ id;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);

			if (rs.next()) {
				return new MailInfoDomain(rs.getInt(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getString(5), rs
						.getDate(6), rs.getDate(7), rs.getString(8), rs
						.getDate(9), rs.getInt(10), rs.getInt(11));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}

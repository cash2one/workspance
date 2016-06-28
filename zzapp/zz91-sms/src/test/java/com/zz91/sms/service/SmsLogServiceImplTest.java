package com.zz91.sms.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import com.zz91.sms.domain.SmsLog;
import com.zz91.sms.service.SmsLogService;
import com.zz91.util.datetime.DateUtil;

public class SmsLogServiceImplTest extends BaseServiceTestCase {

	@Resource
	private SmsLogService smsLogService;

	// 移除
	public void test_removeById_id_null(Integer id) {
		clear();
		try{
		smsLogService.remove(null);
		fail();
		}catch(Exception e){
		assertEquals("the id can not be null", e.getMessage());
		}
	}

	public void test_removeById_id_notnull() {
		clear();
		int id1=createOne(1);
		int id2=createOne(1);
		Integer a=smsLogService.remove(id1);
		assertNotNull(a);
		assertEquals(1, a.intValue());
		
		SmsLog sLog=queryOne(id1);
		assertNull(sLog);
		
		SmsLog sLog2=queryOne(id2);
		assertNotNull(sLog2);
	}

	// 重发
	public void test_resend() throws ParseException {
		clear();
		Integer id = insert(getSms("templateCode", new Date()));
		SmsLog smsDomain = getSms("templateCode", new Date());
		smsDomain.setId(id);
		smsLogService.resend(id);
		SmsLog smsLogDomain = queryOne(id);
		assertTrue(smsLogDomain.getSendStatus() == 0);
	}

	// 分页
//	public void test_page_只测分页() throws ParseException{
//		clear();
//		insertMany(7, "ceshi", DateUtil.getDate("2012-04-01", "yyyy-MM-dd"));
//		insertMany(3, "ceshi", DateUtil.getDate("2012-04-02", "yyyy-MM-dd"));
//		insertMany(2, "ceshi", DateUtil.getDate("2012-04-03", "yyyy-MM-dd"));
//		Pager<SmsLog> page=new Pager<SmsLog>();
//		page.setStart(0);
//		page.setLimit(5);
//		page = smsLogService.pageLog(null, null, 0, page);
//		assertNotNull(page.getRecords());
//		assertEquals(5, page.getRecords().size());
//		assertEquals(12, page.getTotals().intValue());
//
//		page.setStart(8);
//		page.setLimit(5);
//		page = smsLogService.pageLog(null, null, 0, page);
//		assertNotNull(page.getRecords());
//		assertEquals(4, page.getRecords().size());
//		assertEquals(12, page.getTotals().intValue());
//
//	}
	
//	public void test_page_测试条件_from() throws ParseException {
//		clear();
//		insertMany(7, "ceshi", DateUtil.getDate("2012-04-01", "yyyy-MM-dd"));
//		insertMany(3, "ceshi", DateUtil.getDate("2012-04-02", "yyyy-MM-dd"));
//		insertMany(2, "ceshi", DateUtil.getDate("2012-04-03", "yyyy-MM-dd"));
//		Pager<SmsLog> page = new Pager<SmsLog>();
//		page.setStart(0);
//		page.setLimit(5);
//		page = smsLogService.pageLog(DateUtil.getDate("2012-04-01",
//				"yyyy-MM-dd"), null, 0, page);
//		assertNotNull(page.getRecords());
//		assertEquals(5, page.getRecords().size());
//		assertEquals(12, page.getTotals().intValue());
//
//		page.setStart(8);
//		page.setLimit(5);
//		page =  smsLogService.pageLog(DateUtil.getDate("2012-04-01",
//				"yyyy-MM-dd"), null, 0, page);
//		assertNotNull(page.getRecords());
//		assertEquals(4, page.getRecords().size());
//		assertEquals(12, page.getTotals().intValue());
//
//	}
//
//	public void test_page_测试条件_to() throws ParseException {
//		clear();
//		insertMany(7, "ceshi", DateUtil.getDate("2012-04-01", "yyyy-MM-dd"));
//		insertMany(3, "ceshi", DateUtil.getDate("2012-04-02", "yyyy-MM-dd"));
//		insertMany(2, "ceshi", DateUtil.getDate("2012-04-03", "yyyy-MM-dd"));
//		Pager<SmsLog> page = new Pager<SmsLog>();
//		page.setStart(0);
//		page.setLimit(5);
//		page =  smsLogService.pageLog(null, DateUtil.getDate("2012-04-03",
//				"yyyy-MM-dd"), 0, page);
//		assertNotNull(page.getRecords());
//		assertEquals(5, page.getRecords().size());
//		assertEquals(12, page.getTotals().intValue());
//
//		page.setStart(8);
//		page.setLimit(5);
//		page = smsLogService.pageLog(null, DateUtil.getDate("2012-04-03",
//				"yyyy-MM-dd"), 0, page);
//		assertNotNull(page.getRecords());
//		assertEquals(4, page.getRecords().size());
//		assertEquals(12, page.getTotals().intValue());
//
//	}
//
//	public void test_page_测试条件_from_to() throws ParseException {
//		clear();
//		insertMany(7, "ceshi", DateUtil.getDate("2012-04-01", "yyyy-MM-dd"));
//		insertMany(3, "ceshi", DateUtil.getDate("2012-04-02", "yyyy-MM-dd"));
//		insertMany(2, "ceshi", DateUtil.getDate("2012-04-03", "yyyy-MM-dd"));
//		Pager<SmsLog> page = new Pager<SmsLog>();
//		page.setStart(0);
//		page.setLimit(5);
//		page =  smsLogService.pageLog(DateUtil.getDate("2012-04-01",
//				"yyyy-MM-dd"), DateUtil.getDate("2012-04-03", "yyyy-MM-dd"), 0,
//				page);
//		assertNotNull(page.getRecords());
//		assertEquals(5, page.getRecords().size());
//		assertEquals(12, page.getTotals().intValue());
//
//		page.setStart(8);
//		page.setLimit(5);
//		page =  smsLogService.pageLog(DateUtil.getDate("2012-04-01",
//				"yyyy-MM-dd"), DateUtil.getDate("2012-04-03", "yyyy-MM-dd"), 0,
//				page);
//		assertNotNull(page.getRecords());
//		assertEquals(4, page.getRecords().size());
//		assertEquals(12, page.getTotals().intValue());
//	}
/***********************************************************************/
	private Integer createOne(Integer idx) {
		int randomPriority = (int) (Math.random() * 10);
		String sql = "insert into sms_log (template_code,receiver,send_status,"
				+ "gmt_send,gateway_code,priority,gmt_created,gmt_modified,content) "
				+ "values('templateCode" + idx + "','asded" + idx
				+ "',0,'2012-04-03 23:25:00','1233',0,now(),now(),'sdww"
				+ randomPriority + "')";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertResult();
	}

	private SmsLog getSms(String templateCode, Date gmtSend) {
		return new SmsLog(null, templateCode, "receiver", 0, gmtSend,
				"gatewayCode", 0, null, null, "content");
	}

	private Integer insert(SmsLog sms) {
		String sql = "insert into sms_log(template_code,receiver,send_status,gmt_send,gateway_code,priority,"
				+ "gmt_created,gmt_modified,content)values('"
				+sms.getTemplateCode()
				+"','"
				+sms.getReceiver()
				+"',"
				+sms.getSendStatus()
				+",'"
				+DateUtil.toString(sms.getGmtSend(), "yyyy-MM-dd")
				+"','"
				+sms.getGatewayCode()
				+"',"
				+sms.getPriority()
				+",now(),now(),'"
				+sms.getContent()
				+"')";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertResult();

	}

	private SmsLog queryOne(Integer id) {
		String sql = "select id,template_code,receiver,send_status,gmt_send,gateway_code,priority,"
				+ "gmt_created,gmt_modified,content from sms_log where id="
				+ id;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);

			if (rs.next()) {
				return new SmsLog(rs.getInt(1), rs.getString(2), rs
						.getString(3), rs.getInt(4), rs.getDate(5), rs
						.getString(6), rs.getInt(7), rs.getDate(8), rs
						.getDate(9), rs.getString(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void clear() {
		try {
			connection.prepareStatement("delete from sms_log").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

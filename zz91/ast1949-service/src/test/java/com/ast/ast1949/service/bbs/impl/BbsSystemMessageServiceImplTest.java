/**
 * @author kongsj
 * @date 2014年12月1日
 * 
 */
package com.ast.ast1949.service.bbs.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import com.ast.ast1949.domain.bbs.BbsSystemMessage;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.bbs.BbsSystemMessageService;

public class BbsSystemMessageServiceImplTest extends BaseServiceTestCase {
	
	@Resource
	private BbsSystemMessageService bbsSystemMessageService;
	
	public void test_updateForRead() throws Exception{
		clean();
		create();
		Integer id = queryLastInsertId();
		Integer i = bbsSystemMessageService.read(id);
		assertEquals(1, i.intValue()); // 断言是否更新成功
		
		Integer isRead = null;
		ResultSet rs=connection.createStatement().executeQuery("select is_read from bbs_system_message where id="+id);
		if(rs.next()){
			isRead = rs.getInt(1);
		}
		
		assertEquals(1, isRead.intValue()); // 断言已读状态 是否 已更新
	}
	
	public void test_pageList() throws Exception{
		clean();
		for (int i = 0; i < 10; i++) {
			create();
		}
		PageDto<BbsSystemMessage> page = new PageDto<BbsSystemMessage>();
		page.setPageSize(10);
		BbsSystemMessage bbsSystemMessage = new BbsSystemMessage();
		bbsSystemMessage.setIsRead(0);
		bbsSystemMessage.setCompanyId(0);
		page = bbsSystemMessageService.pageList(bbsSystemMessage, page);
		assertNotNull(page);
		assertEquals(10, page.getRecords().size()); // 断言是否有十条 
		assertEquals(10, page.getTotalRecords().intValue()); // 断言总条数 是否十条
		
	}
	
	public void test_getNoReadCount() throws Exception{
		clean();
		for (int i = 0; i < 10; i++) {
			create();
		}
		int count = bbsSystemMessageService.getNoReadCount(0);
		assertEquals(10, count); // 断言为 10条
		
		Integer ncount = bbsSystemMessageService.getNoReadCount(null); // 传空 
		assertNotNull(ncount); //断言不为空 
		assertEquals(0, ncount.intValue()); //断言返回0
		
	}
	
	
	private void clean() throws Exception{
		connection.prepareStatement("truncate bbs_system_message").execute();
	}
	
	private void create() throws SQLException {
		String sql = "INSERT INTO bbs_system_message(company_id,account,email,topic,content,message_time,is_read,gmt_created,gmt_modified,url) "
		+ "VALUES (0,'kongsj','kongsj@sds.com','标题','内容',now(),0,now(),now(),'http://www.zz91.com')";
		connection.prepareStatement(sql).execute();
	}
	
	private Integer queryLastInsertId() throws SQLException {
		ResultSet rs=connection.createStatement().executeQuery("select last_insert_id()");
		if(rs.next()){
			return rs.getInt(1);
		}
		return 0;
	}
	
}

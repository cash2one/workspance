/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-1
 */
package com.ast.ast1949.service.friendlink;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.site.FriendLinkDO;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.site.FriendLinkService;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
public class FriendLinkServiceImplTest extends BaseServiceTestCase {

	@Autowired
	FriendLinkService friendLinkService;

	public void test_queryFriendLinkByCondition_isNull() {
		try {
			clean();
			friendLinkService.queryFriendLinkByCondition(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("friendLinkDTO is not null", e.getMessage());
		}

	}

	public void test_getFriendLinkRecordCountByCondition_isNull() {
		clean();
		try {
			friendLinkService.getFriendLinkRecordCountByCondition(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("friendLinkDTO is not null", e.getMessage());
		}

	}

	public void test_insertFriendLink_isNull() {
		try {
			clean();
			friendLinkService.insertFriendLink(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("friendLinkDO is not null", e.getMessage());
		}

	}

	public void test_insertFriendLink_notNull() {
		clean();

		FriendLinkDO friendLinkDO = new FriendLinkDO();
		friendLinkDO.setAddTime(new Date());
		friendLinkDO.setGmtCreated(new Date());
		friendLinkDO.setGmtModified(new Date());
		friendLinkDO.setHeight("32");
		friendLinkDO.setLink("//54444");

		friendLinkDO.setLinkCategoryCode("10210000");
		friendLinkDO.setLinkName("aaaaaaaaaaaaa");
		friendLinkDO.setPicAddress("dffdfdfd");
		friendLinkDO.setShowIndex(1);
		friendLinkDO.setTextColor("fdf");
		friendLinkDO.setWidth("4343");
		int i = friendLinkService.insertFriendLink(friendLinkDO);
		assertNotNull(i);
		assertTrue(i > 0);
	}

	public void test_updateFriendLink_isNull() {
		try {
			clean();
			friendLinkService.updateFriendLink(null);
			fail();
		} catch (IllegalArgumentException e) {
			Assert.notNull("friendLinkDO is not null", e.getMessage());
		}

	}

	public void test_updateFriend_notNull() {
		clean();
		FriendLinkDO friendLinkDO = new FriendLinkDO();
	friendLinkService.updateFriendLink(friendLinkDO);
	// assertTrue(i>0);
	}

	private void clean() {
		try {
			cleanupFriendLink();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 清除表中的数据
	protected void cleanupFriendLink() throws SQLException {
		connection.prepareStatement("delete from friend_link").execute();
	}
}

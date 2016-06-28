/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-31 下午04:37:35
 */
package com.ast.ast1949.service.subscribe;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.company.SubscribeDO;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.company.SubscribeService;

/**
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class SubscribeServiceImplTest extends BaseServiceTestCase{
	@Autowired
	private SubscribeService subscribeService;

	public void test_insertSubscribe() {
		clean();
		Integer i = insertSubscribe();
		assertTrue(i > 0);
	}

	public void test_selectSubscribeById() {
		clean();
		Integer i = insertSubscribe();
		SubscribeDO subscribeDO = subscribeService.selectSubscribeById(i);
		assertTrue(subscribeDO.getId().equals(i));

	}

	public void test_querySubscribeByCompanyIdAndSubscribeType() {
		clean();
		insertSubscribe();
		List<SubscribeDO> list = subscribeService.querySubscribeByCompanyIdAndSubscribeType(1, "1");
		assertTrue(list.size() > 0);
	}

	public void test_deleteSubscribeById() {
		clean();
		Integer i = insertSubscribe();
		Integer result = subscribeService.deleteSubscribeById(i);
		assertTrue(result > 0);
	}

	public void test_updateSubscribe() {
		clean();
		Integer i = insertSubscribe();
		SubscribeDO subscribeDO = subscribeService.selectSubscribeById(i);
		subscribeDO.setAccount("aaa");
		Integer result = subscribeService.updateSubscribe(subscribeDO);
		assertTrue(result > 0);
	}

	private Integer insertSubscribe() {
		clean();
		SubscribeDO subscribeDO = new SubscribeDO();
		subscribeDO.setCompanyId(1);
		subscribeDO.setSubscribeType("1");
		Integer i = subscribeService.insertSubscribe(subscribeDO);
		return i;
	}
	private void clean() {
		try {
			cleanupSubscribe();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 清除表中的数据
	protected void cleanupSubscribe() throws SQLException {
		connection.prepareStatement("delete from subscribe").execute();
	}
}

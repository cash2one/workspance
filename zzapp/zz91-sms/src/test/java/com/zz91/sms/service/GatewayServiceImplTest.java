package com.zz91.sms.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zz91.sms.domain.Gateway;
import com.zz91.sms.service.GatewayService;
import com.zz91.sms.test.BaseTestCase;

public class GatewayServiceImplTest extends BaseTestCase {

	@Resource
	private GatewayService gatewayService;

	/*
	 * 测试添加
	 */

	public void test_insert_gateway() {
		clean();

		Integer i = gatewayService.create(getGateway());
		assertNotNull(i);
		assertTrue(i.intValue() > 0);
		
		Gateway gateway = queryOne(i);
		assertNotNull(gateway);
		assertEquals("测试添加", gateway.getCode());
	}

	/*
	 * 测试删除
	 */

	public void test_delete_gateway_notnull() {
		clean();
		int id1 = insert("删除测试", null);
		int id2 = insert("删除测试1", null);

		Integer rm = gatewayService.remove(id1);
		assertNotNull(rm);
		assertEquals(1, rm.intValue());
		
		Gateway gateway1 = queryOne(id1);
		assertNull(gateway1);
		
		Gateway gateway2 = queryOne(id2);
		assertNotNull(gateway2);
	}

	public void test_delete_gateway_null() {
		clean();
		try {
			gatewayService.remove(null);
			fail();
		} catch (Exception e) {
			assertEquals("the id can not be null!", e.getMessage());
		}

	}

	/*
	 * 测试更新
	 */

	public void test_update_gateway() {
		clean();
		Integer id = insert("测试更新", null);
		insert("测试更新2", null);
		insert("测试更新3", null);
		insert("测试更新4", null);
		insert("测试更新5", null);
		insert("测试更新6", null);
		insert("测试更新7", null);

		Gateway gateway = getGateway();
		gateway.setId(id);
		gateway.setCode("更新后的code");
		Integer i = gatewayService.update(gateway);

		assertNotNull(i);
		assertTrue(i.intValue() > 0);

		Gateway g = queryOne(id);
		assertNotNull(g);
		assertEquals("更新后的code", g.getCode());
		
	}

	public void test_update_enabled() {
		clean();
		Integer id = insert("测试启用状态更新", 0);
		gatewayService.enabled(id,"");
		
		Gateway g = queryOne(id);
		assertNotNull(g);
		assertEquals(new Integer(1), g.getEnabled());
	}

	public void test_update_disabled() {
		clean();
		Integer id = insert("测试启用状态更新", 1);
		gatewayService.disabled(id,"");
		
		Gateway g = queryOne(id);
		assertNotNull(g);
		assertTrue(g.getEnabled().intValue() == 0);
	}

	/*
	 * 测试查询
	 */
	public void test_query_gateway_by_enabled() {
		clean();

		for (int i = 0; i < 5; i++) {
			insert("测试查询" + i, 1);
		}
		for (int i = 0; i < 10; i++) {
			insert("测试查询2" + i, 0);
		}

		// 测试状态为以启用(1)的网关
		List<Gateway> enabled = gatewayService.query(1);
		assertEquals(5, enabled.size());

		for (Gateway g : enabled) {
			assertEquals(1, g.getEnabled().intValue());
		}

		// 测试状态为未启用(0)的网关
		List<Gateway> disabled = gatewayService.query(0);
		assertEquals(10, disabled.size());

		for (Gateway g : disabled) {
			assertEquals(0, g.getEnabled().intValue());
		}
	}

	public Gateway getGateway() {
		return new Gateway(null, "测试添加", "titles", 0, "serial_no",
				"serial_pas", "api_jar","api_classpath", "docs", new Date(), new Date());
	}

	public int insert(String code, Integer enabled) {
		if (code == null) {
			code = "test";
		}
		if (enabled == null) {
			enabled = 0;
		}

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO gateway ");
		sb
				.append("(code,titles,enabled,serial_no,serial_pas,api_jar,docs,gmt_created,gmt_modified) ");
		sb.append("VALUES ");
		sb.append("('" + code + "','titles',").append(enabled).append(
				",'serial_no','serial_pas','api_jar','docs',NOW(),NOW())");
		try {
			connection.prepareStatement(sb.toString()).execute();
			return getLastInsertId();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Gateway queryOne(Integer id) {
		Gateway gateway = null;
		try {
			ResultSet rs = connection.prepareStatement(
					"SELECT * FROM gateway WHERE id = " + id).executeQuery();
			if (rs.next()) {
				gateway = new Gateway(rs.getInt("id"), rs.getString("code"), rs
						.getString("titles"), rs.getInt("enabled"), rs
						.getString("serial_no"), rs.getString("serial_pas"), rs
						.getString("api_jar"),rs.getString("api_classpath"), rs.getString("docs"), rs
						.getDate("gmt_created"), rs.getDate("gmt_modified"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gateway;
	}

	public void clean() {
		try {
			connection.prepareStatement("DELETE FROM gateway").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

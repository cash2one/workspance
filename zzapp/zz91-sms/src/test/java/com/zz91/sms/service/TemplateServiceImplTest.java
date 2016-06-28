package com.zz91.sms.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zz91.sms.domain.Template;
import com.zz91.sms.service.TemplateService;
import com.zz91.sms.test.BaseTestCase;

public class TemplateServiceImplTest extends BaseTestCase {

	@Resource
	private TemplateService templateService;

	/*
	 * 测试添加
	 */

	public void test_insert_template() {
		clean();

		Integer i = templateService.create(getTemplate());
		assertNotNull(i);
		assertTrue(i.toString(), i.intValue() > 0);
		
		Template template = queryOne(i);
		assertNotNull(template);
		assertEquals("code", template.getCode());
	}

	/*
	 * 测试删除
	 */

	public void test_delete_template_notnull() {
		clean();
		int id1 = insert("测试删除1");
		int id2 = insert("测试删除2");

		Integer i = templateService.remove(id1);
		assertNotNull(i);
		assertEquals(1, i.intValue());
		
		Template template1 = queryOne(id1);
		assertNull(template1);
		
		Template template2 = queryOne(id2);
		assertNotNull(template2);
	}

	public void test_delete_template_null() {
		clean();

		try {
			templateService.remove(null);
			fail();
		} catch (Exception e) {
			assertEquals("the id can not be null!", e.getMessage());
		}
	}

	/*
	 * 测试更新
	 */

	public void test_update_template() {
		clean();
		Integer id = insert("测试更新");
		insert("测试更新2");
		insert("测试更新3");
		insert("测试更新4");
		insert("测试更新5");
		insert("测试更新6");
		insert("测试更新7");
		assertTrue(id.toString(), id.intValue() > 0);

		Template template = getTemplate();
		template.setId(id);
		template.setCode("测试更新后的数据");
		Integer i = templateService.update(template);
		assertNotNull(i);
		assertTrue(i.toString(), i.intValue() > 0);
	}

	/*
	 * 测试查询
	 */

	public void test_query_template() {
		clean();
		for (int i = 1; i <= 10; i++) {
			insert("测试查询" + i);
		}

		List<Template> tempList = templateService.query();
		assertEquals(10, tempList.size());
	}
	
	public void test_queryOne_template() {
		clean();
		Integer id1 = insert("测试查询1");
		insert("测试查询2");

		Template t = templateService.queryOne(id1);
		assertNotNull(t);
		assertEquals("测试查询1", t.getCode());
	}

	
	public Template getTemplate() {
		return new Template(null, "code", "titles", "content", "signed",
				new Date(), new Date());
	}

	public int insert(String code) {
		if (code == null) {
			code = "test";
		}

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO template ");
		sb.append("(code,titles,content,signed,gmt_created,gmt_modified) ");
		sb.append("VALUES ");
		sb.append("('" + code + "','titles','content','signed',now(),now())");
		try {
			connection.prepareStatement(sb.toString()).execute();
			return getLastInsertId();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Template queryOne(Integer id) {
		Template template = null;
		try {
			ResultSet rs = connection.prepareStatement(
					"SELECT * FROM template WHERE id = " + id).executeQuery();
			if (rs.next()) {
				template = new Template(rs.getInt("id"), rs.getString("code"),
						rs.getString("titles"), rs.getString("content"), rs
								.getString("signed"),
						rs.getDate("gmt_created"), rs.getDate("gmt_modified"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return template;
	}

	public void clean() {
		try {
			connection.prepareStatement("DELETE FROM template").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

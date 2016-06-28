/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7.
 */
package com.zz91.ads.board.service;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import com.zz91.ads.board.domain.ad.Advertiser;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.service.ad.AdvertiserService;
import com.zz91.ads.board.test.BaseTestCase;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
public class AdvertiserServiceImpTest extends BaseTestCase {
	@Resource
	private AdvertiserService service;
	
	/*
	 * 添加测试
	 */

	/**
	 * 正确添加一个广告主
	 */
	public void test_insert_advertiser() {
		clean();
		Integer im = service.insertAdvertiser(getAdvertiser());

		assertNotNull(im);
		assertTrue(im.toString(), im.intValue() > 0);
	}

	/**
	 * 添加一个name为null的广告主
	 */
	public void test_insert_advertiser_but_name_is_null() {
		clean();
		Advertiser advertiser = getAdvertiser();
		advertiser.setName(null);
		try {
			service.insertAdvertiser(advertiser);
			fail();
		} catch (Exception e) {
			assertEquals("the name must not be null", e.getMessage());
		}
	}

	/**
	 * 添加一个name为空的广告主
	 */
	public void test_insert_advertiser_but_name_is_empty() {
		clean();
		Advertiser advertiser = getAdvertiser();
		advertiser.setName("");
		try {
			service.insertAdvertiser(advertiser);
			fail();
		} catch (Exception e) {
			assertEquals("the name must not be null", e.getMessage());
		}
	}

	/**
	 * 查询广告主
	 */
	public void test_query_advertiser_by_id() {
		clean();
		int id = insert("test_query_advertiser_by_id", 1);
		Advertiser advertiser = service.queryAdvertiserById(id);

		assertNotNull(advertiser);
		assertTrue("insert fail", advertiser.getId() > 0);
		assertEquals("test_query_advertiser_by_id", advertiser.getName());
	}

	/**
	 * 测试伪删除
	 */
	public void test_sign_deleted() {
		clean();
		insert("test_sign_deleted", 1);
		int id = getLastInsertId();
		service.signDeleted(id);

		Advertiser advertiser = service.queryAdvertiserById(id);

		assertNotNull(advertiser);
		assertTrue("insert fail", advertiser.getId() > 0);
		assertEquals("Y", advertiser.getDeleted());
	}

	/**
	 * 测试更新广告主
	 */
	public void test_update_advertiser() {
		clean();
		int id = insert("test_update_advertiser", 1);
		Advertiser advertiser = service.queryAdvertiserById(id);

		assertNotNull(advertiser);
		assertTrue("insert fail", advertiser.getId() > 0);
		assertEquals("test_update_advertiser", advertiser.getName());

		advertiser.setId(id);
		advertiser.setCategory(0);
		advertiser.setName("更新测试");

		service.updateAdvertiser(advertiser);

		advertiser = service.queryAdvertiserById(id);

		assertNotNull(advertiser);
		assertTrue("update fail", advertiser.getId() > 0);
		assertEquals("更新测试", advertiser.getName());
		assertEquals("0", advertiser.getCategory().toString());

	}

	/**
	 * 测试分页查询
	 */
	public void test_page() {
		clean();
		int number = 25;
		for (int i = 0; i < number; i++) {
			if (i == 10 || i == 20) {
				insert("test_page_" + i, 100);
			} else {
				insert("test_page_" + i, i);
			}
		}
		Pager<Advertiser> pager = new Pager<Advertiser>();
		Pager<Advertiser> page = new Pager<Advertiser>();
		// 查询所有未删除的信息
		page = service.pageAdvertiserByConditions(null, false, pager);

		assertNotNull(page);
		assertNotNull(page.getRecords());
		assertEquals(20, page.getRecords().size());
		assertEquals(number, page.getTotals().intValue());

		// 查询所有已删除的信息
		page = new Pager<Advertiser>();
		page = service.pageAdvertiserByConditions(null, true, pager);
		assertEquals(0, page.getTotals().intValue());

		Advertiser advertiser = new Advertiser();
		advertiser.setName("test_page_6");
		// 查询所有name为test_page_6的未删除的记录
		page = service.pageAdvertiserByConditions(advertiser, false, pager);

		assertNotNull(page);
		assertNotNull(page.getRecords());
		assertEquals(1, page.getTotals().intValue());

		advertiser = new Advertiser();
		advertiser.setName("我不存在");
		// 查询所有name为“我不存在”的未删除的记录
		page = service.pageAdvertiserByConditions(advertiser, null, pager);

		assertNotNull(page);
		assertNotNull(page.getRecords());
		assertEquals(0, page.getTotals().intValue());

		advertiser = new Advertiser();
		advertiser.setCategory(100);
		// 查询所有Category为“100”的未删除的记录
		page = service.pageAdvertiserByConditions(advertiser, null, pager);

		assertNotNull(page);
		assertNotNull(page.getRecords());
		assertEquals(2, page.getTotals().intValue());

		advertiser = new Advertiser();
		advertiser.setName("test_page_10");
		advertiser.setCategory(100);
		// 查询所有Category为“100”的未删除的记录
		page = service.pageAdvertiserByConditions(advertiser, null, pager);

		assertNotNull(page);
		assertNotNull(page.getRecords());
		assertEquals(1, page.getTotals().intValue());
	}
	
	/**
	 * demo
	 */
	public void test_demo() {
		clean();
		int i = insert("demo", 1);
		assertTrue(i + "", i > 0);
		clean();
	}

	/**
	 * 获取广告主实例
	 * 
	 * @return
	 */
	public Advertiser getAdvertiser() {
		return new Advertiser(null, "name", "contact", "phone", "email", "remark", 0, "N",
				new Date(), new Date());
	}

	/**
	 * 插入记录-测试
	 * 
	 * @param name
	 * @param category
	 */
	public Integer insert(String name, Integer category) {
		if (name == null) {
			name = "test";
		}
		if (category == null) {
			category = 0;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO advertiser ");
		sb.append("(name,contact,phone,email,remark,category,deleted,gmt_created,gmt_modified) ");
		sb.append("VALUES ");
		sb.append("(\"").append(name).append("\",\"contact\",\"phone\",\"email\",\"remark\",")
				.append(category).append(",\"N\",NOW(),NOW())");
		try {
			connection.prepareStatement(sb.toString()).execute();
			return getLastInsertId();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 清理数据
	 */
	public void clean() {
		try {
			connection.prepareStatement("DELETE FROM advertiser").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

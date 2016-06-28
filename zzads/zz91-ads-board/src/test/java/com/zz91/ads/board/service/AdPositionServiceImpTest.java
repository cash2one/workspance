/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zz91.ads.board.domain.ad.AdPosition;
import com.zz91.ads.board.service.ad.AdPositionService;
import com.zz91.ads.board.test.BaseTestCase;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
public class AdPositionServiceImpTest extends BaseTestCase {

	@Resource
	private AdPositionService service;

	/*
	 * 测试添加
	 */

	public void test_insert_ad_position() {
		clean();
		Integer id = service.insertAdPosition(getAdPosition());

		assertNotNull(id);
		assertTrue(id.toString(), id.intValue() > 0);
	}

	/*
	 * 测试查询
	 */

	public void test_query_adposition_by_id() {
		clean();
		int id = insert("查询测试", 0);
		assertTrue(id + "", id > 0);

		AdPosition adPosition = service.queryAdPositionById(id);

		assertNotNull(adPosition);
		assertEquals("查询测试", adPosition.getName());
	}

	public void test_queryAdPositionByParentId() {
		clean();
		for (int i = 1; i <= 10; i++) {
			if(i%2==0) {
				insert("queryAdPositionByParentId", 20);
			} else {
				insert("queryAdPositionByParentId", i);
			}
		}
		
		List<AdPosition> list = service.queryAdPositionByParentId(20);
		
		assertNotNull(list);
		assertNotNull(list.size());
		assertEquals(5, list.size());
		
		for (AdPosition p : list) {
			assertNotNull(p);
			assertEquals(20, p.getParentId().intValue());
		}
	}

	// queryAdPositionNodesByParentId
	// queryAdPositionDtoById
	
	/*
	 * 测试伪删除
	 */

	public void test_sign_delete() {
		clean();
		int id = insert("sign_delete", 0);

		assertTrue(id + "", id > 0);

		Integer im = service.signDelete(id);
		assertNotNull(im);
		assertEquals(1, im.intValue());

		AdPosition adPosition = service.queryAdPositionById(id);

		assertNotNull(adPosition);
		assertEquals("Y", adPosition.getDeleted());
		assertEquals("sign_delete", adPosition.getName());
	}

	/*
	 * 测试更新
	 */

	public void test_update_ad_position() {
		clean();
		int id = insert("测试更新", 0);
		assertTrue(id + "", id > 0);

		AdPosition adPosition = getAdPosition();
		adPosition.setId(id);
		adPosition.setName("my name has be changed");
		adPosition.setSequence(2);
		Integer im = service.updateAdPosition(adPosition);

		assertNotNull(im);
		assertTrue(im.toString(), im.intValue() > 0);

		adPosition = new AdPosition();
		adPosition = service.queryAdPositionById(id);

		assertNotNull(adPosition);
		assertEquals("my name has be changed", adPosition.getName());
		assertEquals("N", adPosition.getDeleted());
	}

	/*
	 * demo
	 */

	public void test_demo() {
		clean();
		int i = insert("demo", 0);

		assertTrue(i + "", i > 0);
	}

	public AdPosition getAdPosition() {
		return new AdPosition(null, "name", 0, "url", 0, 99, 1, 0, 0, 10, 0, new Date(),
				new Date(), "N");
	}

	public int insert(String name, Integer parentId) {
		if (name == null) {
			name = "name";
		}
		if (parentId == null) {
			parentId = 0;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ad_position ");
		sb.append("(name,parent_id,request_url,delivery_style_id,sequence,payment_type,width,"
				+ "height,max_ad,has_exact_ad,gmt_created,gmt_modified,deleted) ");
		sb.append("VALUES ");
		sb.append("('" + name + "','" + parentId + "','url',0,99,1,0,0,10,0,NOW(),NOW(),'N') ");
		try {
			connection.prepareStatement(sb.toString()).execute();
			return getLastInsertId();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void clean() {
		try {
			connection.prepareStatement("DELETE FROM ad_position").execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

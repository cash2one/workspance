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

import com.zz91.ads.board.domain.ad.ExactType;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.service.ad.ExactTypeService;
import com.zz91.ads.board.test.BaseTestCase;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
public class ExactTypeServiceImpTest extends BaseTestCase {

	@Resource
	private ExactTypeService service;

	/*
	 * 测试保存
	 */

	public void test_save_exact_type() {
		clean();
		Integer id = service.saveExactType(getExactType("测试保存"));

		assertNotNull(id);
		assertTrue(id.toString(), id.intValue() > 0);
	}

	public void test_save_exact_type_with_same_name() {
		clean();
		int i = insert("测试保存");
		assertTrue(i + "", i > 0);

		Integer id = service.saveExactType(getExactType("测试保存"));

		assertNotNull(id);
		assertTrue(id.toString(), id.intValue() == i);
	}

	/*
	 * 测试删除
	 */

	public void test_delete_exact_type_by_id() {
		clean();
		int id = insert("删除测试");
		assertTrue(id + "", id > 0);

		Integer im = service.deleteExactTypeById(id);

		assertNotNull(im);
		assertEquals(1, im.intValue());
	}

	/*
	 * 测试查询
	 */

	public void test_query_exact_type_by_id() {
		clean();
		int id = insert("我的名称");
		assertTrue(id + "", id > 0);

		ExactType et = service.queryExactTypeById(id);

		assertNotNull(et);
		assertEquals("我的名称", et.getExactName());
	}

	public void test_query_exact_type_by_name() {
		clean();
		int id = insert("cool");
		assertTrue(id + "", id > 0);

		ExactType et = service.queryExactTypeByExactName("cool");

		assertNotNull(et);
		assertEquals(id, et.getId().intValue());
		assertEquals("cool", et.getExactName());
	}

	public void test_page_exact_type() {
		clean();

		for (int i = 1; i <= 25; i++) {
			insert("分页测试-" + i);
		}
		Pager<ExactType> pager = new Pager<ExactType>();
		Pager<ExactType> page = service.pageExactType(pager);

		assertNotNull(page);
		assertNotNull(page.getRecords());
		assertNotNull(page.getRecords().size());
		assertEquals(20, page.getRecords().size());
	}

	public void test_query_exact_type_by_ad_position_id() {
		cleanAll();
		//初始化数据
		insertAdPosition(null, null);// 插入广告位
		insertAdPosition(null, null);// 插入广告位
		int adPositionId = insertAdPosition(null, null);// 插入广告位
		assertTrue(adPositionId + "", adPositionId > 0);

		for (int i = 1; i <= 10; i++) {
			int exactTypeId = insert("条件-" + i);//插入精确条件
			assertTrue(exactTypeId + "", exactTypeId > 0);
			
			if(i%2==0) {
				int id = insertPositionExactType(adPositionId, exactTypeId);
				assertTrue(id + "", id > 0);
			} else {
				int id = insertPositionExactType((adPositionId-1), exactTypeId);
				assertTrue(id + "", id > 0);
			}
		}
		//初始化完毕
		
		List<ExactType> list = service.queryExactTypeByAdPositionId(adPositionId);
		
		assertNotNull(list);
		assertNotNull(list.size());
		assertEquals(5, list.size());
		
		cleanAll();
	}

	/**
	 * demo
	 */
	public void test_demo() {
		clean();
		int i = insert("demo");
		assertTrue(i + "", i > 0);
		clean();
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public ExactType getExactType(String name) {
		if (name == null) {
			name = "test";
		}
		return new ExactType(null, name, "jsFunction", "javaKey", "remark", new Date(), new Date());
	}

	/**
	 * 插入记录-测试
	 * 
	 * @param name
	 * @param category
	 */
	public Integer insert(String name) {
		if (name == null) {
			name = "test";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO exact_type ");
		sb.append("(exact_name,match_type,js_function,java_key,remark,gmt_created,gmt_modified) ");
		sb.append("VALUES ");
		sb.append("('" + name + "',0,'jsFunction','javaKey','remark',NOW(),NOW())");
		try {
			connection.prepareStatement(sb.toString()).execute();
			return getLastInsertId();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 插入广告位
	 * 
	 * @param name
	 * @param parentId
	 * @return
	 */
	public int insertAdPosition(String name, Integer parentId) {
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

	/**
	 * 插入ExactType记录
	 * 
	 * @param adPositionId
	 * @param exactTypeId
	 * @return
	 */
	public int insertPositionExactType(Integer adPositionId, Integer exactTypeId) {
		if (adPositionId == null) {
			adPositionId = 0;
		}
		if (exactTypeId == null) {
			exactTypeId = 0;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO position_exact_type ");
		sb.append("(ad_position_id,exact_type_id,gmt_created,creator) ");
		sb.append("VALUES ");
		// sb.append("('"+adPositionId+"','"+exactTypeId+"',NOW(),'creator')");
		sb.append("(").append(adPositionId).append(",").append(exactTypeId).append(
				",NOW(),'creator')");
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
			connection.prepareStatement("DELETE FROM exact_type").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 清理数据
	 */
	public void cleanAll() {
		try {
			connection.prepareStatement("DELETE FROM ad_position").execute();
			connection.prepareStatement("DELETE FROM exact_type").execute();
			connection.prepareStatement("DELETE FROM position_exact_type").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

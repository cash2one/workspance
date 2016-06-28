/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7.
 */
package com.zz91.ads.board.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zz91.ads.board.domain.ad.AdMaterial;
import com.zz91.ads.board.service.ad.AdMaterialService;
import com.zz91.ads.board.test.BaseTestCase;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
public class AdMaterialServiceImpTest extends BaseTestCase {
	@Resource
	private AdMaterialService service;

	/*
	 * 测试添加
	 */
	public void test_insert_admaterial() {
		clean();
		Integer im = service.insertAdMaterial(getAdMaterial());

		assertNotNull(im);
		assertTrue(im.toString(), im.intValue() > 0);
	}

	/**
	 * 测试删除
	 */
	public void test_delete_admaterial_by_id() {
		clean();
		Integer id = insert("delete", null);
		Integer im = service.deleteAdMaterialById(id);

		assertNotNull(im);
		assertTrue("delete fail", im.intValue() > 0);
	}

	/**
	 * 测试查询
	 */
	public void test_query_admaterial_by_adid() {
		clean();
		Integer id = 10;
		for (int i = 1; i <= 10; i++) {
			if (i % 2 == 0) {
				insert("query_admaterial_by_adid_" + i, 0);
			} else {
				insert("query_admaterial_by_adid_" + i, id);
			}
		}

		List<AdMaterial> list = service.queryAdMaterialByAdId(id);

		assertNotNull(list);
		assertNotNull(list.size());
		assertTrue("query fail,size is " + list.size(), list.size() == (10 / 2));
	}

	/**
	 * demo
	 */
	public void test_demo() {
		clean();
		int i = insert("demo", 2);
		assertTrue(i + "", i > 0);
		clean();
	}

	public AdMaterial getAdMaterial() {
		return new AdMaterial(0, 0, "name", "0", "filePath", "remark", new Date(), new Date());
	}

	public int insert(String name, Integer adId) {
		if (name == null) {
			name = "test";
		}
		if (adId == null) {
			adId = 0;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ad_material ");
		sb.append("(ad_id,name,material_type,file_path,remark,gmt_created,gmt_modified) ");
		sb.append("VALUES ");
		sb.append("('" + adId + "','" + name + "','0','file_path','remark',NOW(),NOW())");
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
			connection.prepareStatement("DELETE FROM ad_material").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

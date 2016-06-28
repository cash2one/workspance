/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.service;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import com.zz91.ads.board.domain.ad.Ad;
import com.zz91.ads.board.domain.ad.AdExactType;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdDto;
import com.zz91.ads.board.dto.ad.AdSearchDto;
import com.zz91.ads.board.service.ad.AdService;
import com.zz91.ads.board.test.BaseTestCase;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
public class AdServiceImpTest extends BaseTestCase {

	@Resource
	private AdService service;

	/*
	 * 测试添加
	 */

	public void test_insert_ad() {
		clean();
//		Integer id = service.insertAd(getAd());

//		assertNotNull(id);
//		assertTrue(id.toString(), id.intValue() > 0);
	}

	/*
	 * 测试删除
	 */

	public void test_delete_ad_by_id() {
		clean();
		int id = insert("删除测试", null, null,null);

		Integer im = service.deleteAdById(id);

		assertNotNull(im);
		assertEquals(1, im.intValue());
	}

	/*
	 * 测试查询
	 */

	public void test_qurey_ad_by_id() {
		clean();
		int id = insert("查询测试", null, null,null);
		assertTrue(id + " insert fail", id > 0);

		Ad ad = service.queryAdById(id);

		assertNotNull(ad);
		assertEquals("查询测试", ad.getAdTitle());
	}

	/*
	 * 测试分页
	 */

	public void test_page_ad_by_conditions() {
		clean();
		for (int i = 1; i <= 30; i++) {
			if (i % 2 == 0) {
				insert("分页测试" + i, null, null,null);
			} else {
				insert("广告标题" + i, null, null,null);
			}
		}

		Ad ad = new Ad();
		Pager<AdDto> pager = new Pager<AdDto>();
		Pager<AdDto> page = new Pager<AdDto>();

		page = service.pageAdByConditions(ad, new AdSearchDto(), pager);

		assertNotNull(page);
		assertNotNull(page.getTotals());
		assertEquals(30, page.getTotals().intValue());

		// TODO:测试最后一页数据
		// ad= new Ad();
		// pager = new Pager<AdDto>();
		// page = new Pager<AdDto>();
		// page = service.pageAdByConditions(ad, pager);
		//		
		// assertNotNull(page);
		// assertNotNull(page.getTotals());
		// assertEquals(15, page.getTotals().intValue());

		ad = new Ad();
		ad.setAdTitle("分页测试");
		pager = new Pager<AdDto>();
		page = new Pager<AdDto>();
		page = service.pageAdByConditions(ad, new AdSearchDto(), pager);

		assertNotNull(page);
		assertNotNull(page.getTotals());
		assertEquals(15, page.getTotals().intValue());
	}

	/*
	 * 测试更新
	 */

	public void test_save_and_check() {
		clean();
		int id = insert("保存并审核测试", "N", null,null);
		assertTrue(id + " insert fail", id > 0);

		Ad ad = getAd();
		ad.setId(id);
		ad.setAdTitle("我是更新后的标题");
		Integer im = service.saveAndCheck(ad);
		
		assertNotNull(im);
		assertTrue(id + " save and check fail", im.intValue() > 0);

		ad = null;
		ad = service.queryAdById(id);

		assertNotNull(ad);
		assertEquals("Y", ad.getReviewStatus());
		assertEquals("我是更新后的标题", ad.getAdTitle());
	}

	public void test_save_only() {
		clean();
		int id = insert("保存并审核测试", "N", null,null);
		assertTrue(id + " insert fail", id > 0);

		Ad ad = getAd();
		ad.setId(id);
		ad.setAdTitle("我是保存后的标题");
		ad.setReviewStatus(null);
		Integer im = service.saveOnly(ad);
		
		assertNotNull(im);
		assertTrue(id + " save and check fail", im.intValue() > 0);

		ad = null;
		ad = service.queryAdById(id);

		assertNotNull(ad);
		assertEquals("N", ad.getReviewStatus());
		assertEquals("我是保存后的标题", ad.getAdTitle());
	}

	public void test_pass() {
		clean();
		int id = insert("审核测试", "N", null,null);
		assertTrue(id + " insert fail", id > 0);

//		Integer im = service.pass(id);
		
//		assertNotNull(im);
//		assertTrue(id + " pass fail", im.intValue() > 0);
//
//		Ad ad = service.queryAdById(id);
//
//		assertNotNull(ad);
//		assertEquals("Y", ad.getReviewStatus());
//		assertEquals("审核测试", ad.getAdTitle());
	}

	public void test_back() {
		clean();
		int id = insert("审核测试", "Y", null,null);
		assertTrue(id + " insert fail", id > 0);

//		Integer im = service.back(id);
//		
//		assertNotNull(im);
//		assertTrue(id + " back fail", im.intValue() > 0);
//
//		Ad ad = service.queryAdById(id);
//
//		assertNotNull(ad);
//		assertEquals("N", ad.getReviewStatus());
//		assertEquals("审核测试", ad.getAdTitle());
	}

	public void test_set_online() {
		clean();
		int id = insert("上线测试", "Y", "U",null);
		assertTrue(id + " insert fail", id > 0);

		Integer im = service.setOnline(id);
		
		assertNotNull(im);
		assertTrue(id + " online fail", im.intValue() > 0);

		Ad ad = service.queryAdById(id);

		assertNotNull(ad);
		assertEquals("Y", ad.getOnlineStatus());
		assertEquals("上线测试", ad.getAdTitle());
	}

	public void test_set_offline() {
		clean();
		int id = insert("下线测试", "Y", "U",null);
		assertTrue(id + " insert fail", id > 0);

		Integer im = service.setOffline(id);
		
		assertNotNull(im);
		assertTrue(id + " offline fail", im.intValue() > 0);

		Ad ad = service.queryAdById(id);

		assertNotNull(ad);
		assertEquals("N", ad.getOnlineStatus());
		assertEquals("下线测试", ad.getAdTitle());
	}

	public void test_save_ad_by_desigber() {
		clean();
		int id = insert("保存设计测试", null, null,"U");
		assertTrue(id + " insert fail", id > 0);

		Ad ad = getAd();
		ad.setId(id);
		ad.setAdTitle("我是保存设计后的标题");
		ad.setDesignStatus(null);
//		Integer im = service.saveAdByDesigber(ad);
		
//		assertNotNull(im);
//		assertTrue(id + " save ad by desigber fail", im.intValue() > 0);

		ad = null;
		ad = service.queryAdById(id);

		assertNotNull(ad);
		assertEquals("U", ad.getDesignStatus());
		assertEquals("我是保存设计后的标题", ad.getAdTitle());
	}
	
	public void test_finish_ad_by_desigber() {
		clean();
		int id = insert("完成设计测试", null, null,"U");
		assertTrue(id + " insert fail", id > 0);

		Ad ad = getAd();
		ad.setId(id);
		ad.setAdTitle("我是完成设计后的标题");
		Integer im = service.finishAdByDesigner(ad);
		
		assertNotNull(im);
		assertTrue(id + " save ad by desigber fail", im.intValue() > 0);

		ad = null;
		ad = service.queryAdById(id);

		assertNotNull(ad);
		assertEquals("Y", ad.getDesignStatus());
		assertEquals("我是完成设计后的标题", ad.getAdTitle());
	}
	
	public void test_insert_ad_exact_type() {
		clean();
		
		Integer id = service.insertAdExactType(new AdExactType(null, 0, 0, "insert_test"));
		
		assertNotNull(id);
		assertTrue(id.toString()+" insert fail", id.intValue()>0);
	}

	public void test_appoint() {
		clean();
		int id = insert("appoint test", null, null,null);
		assertTrue(id + "", id > 0);
		
		Integer im = service.appoint(id, "rolyer");
		
		assertNotNull(im);
		assertTrue(im.toString()+" appoint fail", im.intValue()>0);
		
		Ad ad = service.queryAdById(id);
		
		assertNotNull(ad);
		assertEquals("rolyer", ad.getDesigner());
	}
	// 
	// sendToDesign
	/**
	 * demo
	 */
	public void test_demo() {
		clean();
		int i = insert("demo title", null, null,null);
		assertTrue(i + "", i > 0);
		clean();
	}

	public Ad getAd() {
		return new Ad(null, 0, 0, "adTitle", "adDescription", "adContent", "adTargetUrl", "Y",
				new Date(), null, "remark", "applicant", "reviewer", "Y", "reviewComment",
				"designer", "Y", new Date(), new Date());
	}

	public int insert(String title, String reviewStatus, String onlineStatus, String designStatus) {
		if (title == null) {
			title = "adTitle";
		}
		if (reviewStatus == null) {
			reviewStatus = "Y";
		}
		if (onlineStatus == null) {
			onlineStatus = "Y";
		}
		if (designStatus == null) {
			designStatus = "Y";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ad ");
		sb
				.append("(position_id,advertiser_id,ad_title,ad_description,ad_content,ad_target_url,online_status,gmt_start,gmt_plan_end,remark,applicant,reviewer,review_status,review_comment,designer,design_status,gmt_created,gmt_modified) ");
		sb.append("VALUES ");
		sb.append("(0,0,'" + title + "','adDescription','adContent','adTargetUrl','" + onlineStatus
				+ "',NOW(),NULL,'remark','applicant','reviewer','" + reviewStatus
				+ "','reviewComment','designer','" + designStatus + "',NOW(),NOW())");
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
			connection.prepareStatement("DELETE FROM ad").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

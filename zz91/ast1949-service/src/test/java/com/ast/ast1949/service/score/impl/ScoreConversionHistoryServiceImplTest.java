/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-1
 */
package com.ast.ast1949.service.score.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.score.ScoreConversionHistoryDo;
import com.ast.ast1949.domain.score.ScoreGoodsDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.score.ScoreConversionHistoryDto;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.score.ScoreConversionHistoryService;
import com.ast.ast1949.util.DateUtil;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-1
 */
public class ScoreConversionHistoryServiceImplTest extends BaseServiceTestCase {
	@Autowired
	ScoreConversionHistoryService scoreConversionHistoryService;

	@Test
	public void testInsertConversionByCompany() {
		clean();
		Integer id = scoreConversionHistoryService
				.insertConversionByCompany(oneTestRecord(null, 1));

		ScoreConversionHistoryDo history = queryOneById(id);
		assertNotNull(history);
		assertEquals("0", history.getStatus());
		assertEquals("companyname", history.getCompanyName());

	}

	@Test
	public void testPageConversionHistoryByCompanyId() {
		clean();
		Integer goodsid = createOneGoods(oneGoodsTestRecord());
		manyTestRecord(18, goodsid);

		PageDto<ScoreConversionHistoryDto> page = new PageDto<ScoreConversionHistoryDto>();
		page.setStartIndex(0);
		page.setPageSize(10);
		page = scoreConversionHistoryService.pageConversionHistoryByCompanyId(
				1, null, page);

		assertNotNull(page.getRecords());
		assertEquals(10, page.getRecords().size());
		assertEquals(18, page.getTotalRecords().intValue());

		page.setStartIndex(10);
		page.setPageSize(10);
		page = scoreConversionHistoryService.pageConversionHistoryByCompanyId(
				1, null, page);

		assertNotNull(page.getRecords());
		assertEquals(8, page.getRecords().size());
		assertEquals(18, page.getTotalRecords().intValue());
	}

	@Test
	public void testPageConversionHistoryByCompanyId_status_not_null() {
		clean();
		Integer goodsid = createOneGoods(oneGoodsTestRecord());
		manyTestRecord(18, goodsid);

		PageDto<ScoreConversionHistoryDto> page = new PageDto<ScoreConversionHistoryDto>();
		page.setStartIndex(0);
		page.setPageSize(10);
		page = scoreConversionHistoryService.pageConversionHistoryByCompanyId(
				1, "1", page);

		assertNotNull(page.getRecords());
		assertEquals(0, page.getRecords().size());
		assertEquals(0, page.getTotalRecords().intValue());

		page.setStartIndex(0);
		page.setPageSize(10);
		page = scoreConversionHistoryService.pageConversionHistoryByCompanyId(
				1, "0", page);

		assertNotNull(page.getRecords());
		assertEquals(10, page.getRecords().size());
		assertEquals(18, page.getTotalRecords().intValue());
	}

	@Test
	public void testPageConversionHistoryByGoodsId() {
		clean();
		manyTestRecord(18, 1);
		PageDto<ScoreConversionHistoryDto> page = new PageDto<ScoreConversionHistoryDto>();
		page.setStartIndex(0);
		page.setPageSize(10);
		page = scoreConversionHistoryService.pageConversionHistoryByGoodsId(1,
				null, page);

		assertNotNull(page.getRecords());
		assertEquals(10, page.getRecords().size());
		assertEquals(18, page.getTotalRecords().intValue());

		page.setStartIndex(10);
		page.setPageSize(10);
		page = scoreConversionHistoryService.pageConversionHistoryByGoodsId(1,
				null, page);

		assertNotNull(page.getRecords());
		assertEquals(8, page.getRecords().size());
		assertEquals(18, page.getTotalRecords().intValue());

	}

	@Test
	public void testPageConversionHistoryByGoodsId_status_notnull() {
		clean();
		manyTestRecord(18, 1);
		PageDto<ScoreConversionHistoryDto> page = new PageDto<ScoreConversionHistoryDto>();
		page.setStartIndex(10);
		page.setPageSize(10);
		page = scoreConversionHistoryService.pageConversionHistoryByGoodsId(1,
				"0", page);

		assertNotNull(page.getRecords());
		assertEquals(8, page.getRecords().size());
		assertEquals(18, page.getTotalRecords().intValue());

		page.setStartIndex(0);
		page.setPageSize(10);
		page = scoreConversionHistoryService.pageConversionHistoryByGoodsId(1,
				"1", page);

		assertNotNull(page.getRecords());
		assertEquals(0, page.getRecords().size());
		assertEquals(0, page.getTotalRecords().intValue());
	}

	@Test
	public void testPageConversionHistoryWithGoods() {
		clean();
		Integer goodsid = createOneGoods(oneGoodsTestRecord());
		manyTestRecord(18, goodsid);

		PageDto<ScoreConversionHistoryDto> page = new PageDto<ScoreConversionHistoryDto>();
		page.setStartIndex(10);
		page.setPageSize(10);
		ScoreConversionHistoryDo conversion = new ScoreConversionHistoryDo();
		page = scoreConversionHistoryService.pageConversionHistoryWithGoods(
				conversion, page);

		assertNotNull(page.getRecords());
		assertEquals(8, page.getRecords().size());
		assertEquals(18, page.getTotalRecords().intValue());
		assertEquals("goood", page.getRecords().get(1).getGoods().getName());
	}

	@Test
	public void testQueryConversionHistoryById() {
		clean();
		Integer id = createOneRecord(oneTestRecord("0", 1));
		ScoreConversionHistoryDo history = scoreConversionHistoryService
				.queryConversionHistoryById(id);
		assertNotNull(history);
		assertEquals("companyname", history.getCompanyName());
	}

	@Test
	public void testQueryRecentConversionHistory() {
		clean();
		Integer goodsid = createOneGoods(oneGoodsTestRecord());
		createOneRecord(oneTestRecord("1", goodsid));
		createOneRecord(oneTestRecord("1", goodsid));
		createOneRecord(oneTestRecord("1", goodsid));
		createOneRecord(oneTestRecord("1", goodsid));
		createOneRecord(oneTestRecord("0", goodsid));
		createOneRecord(oneTestRecord("2", goodsid));

		List<ScoreConversionHistoryDto> list = scoreConversionHistoryService
				.queryRecentConversionHistory(3, "1");
		assertNotNull(list);
		assertEquals(3, list.size());
	}

	@Test
	public void testUpdateConversionFailure() {
		clean();
		Integer id = createOneRecord(oneTestRecord("0", 1));
		Integer i = scoreConversionHistoryService.updateConversionFailure(id,
				"test remark");
		assertTrue(i.intValue() > 0);

		ScoreConversionHistoryDo conversion = queryOneById(id);
		assertEquals("2", conversion.getStatus());
		assertEquals("test remark", conversion.getRemark());

	}

	@Test
	public void testUpdateConversionSuccess() {
		fail("Not yet implemented");
	}

	/*****************************/

	public void clean() {
		try {
			connection.prepareStatement("delete from score_conversion_history")
					.execute();
			connection.prepareStatement("delete from score_goods").execute();
		} catch (SQLException e) {
		}
	}

	public Integer createOneRecord(ScoreConversionHistoryDo history) {
		String sql = "insert into `score_conversion_history`(`company_id`,`score_goods_id`,"
				+ "`conversion_category`,`company_name`,`contact_name`,`phone`,`tel`,`email`,"
				+ "`keywords`,`status`,`remark`,`gmt_created`,`gmt_modified`)"
				+ "values("
				+ history.getCompanyId()
				+ ","
				+ history.getScoreGoodsId()
				+ ",'"
				+ history.getConversionCategory()
				+ "','"
				+ history.getCompanyName()
				+ "','"
				+ history.getContactName()
				+ "','"
				+ history.getPhone()
				+ "','"
				+ history.getTel()
				+ "','"
				+ history.getEmail()
				+ "','"
				+ history.getKeywords()
				+ "','"
				+ history.getStatus()
				+ "','"
				+ history.getRemark()
				+ "',now(),now())";

		try {
			connection.prepareStatement(sql).execute();
			return insertResult();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Integer createOneGoods(ScoreGoodsDo goods) {
		String sql = "";
		sql = "insert into `score_goods`(`name`,`category`,`gmt_start`,`gmt_end`,"
				+ "`price_buy`,`price_day`,`score_buy`,`score_day`,`freight`,"
				+ "`remark`,`keywords`,`detail_url`,`is_hot`,`is_home`,`details`"
				+ ",`show_picture`,`num_conversion`,`gmt_created`,`gmt_modified`)"
				+ "values('"
				+ goods.getName()
				+ "',"
				+ goods.getCategory()
				+ ",'"
				+ DateUtil.toString(goods.getGmtStart(), "yyyy-MM-dd")
				+ "','"
				+ DateUtil.toString(goods.getGmtEnd(), "yyyy-MM-dd")
				+ "',"
				+ goods.getPriceBuy()
				+ ","
				+ goods.getPriceDay()
				+ ","
				+ goods.getScoreBuy()
				+ ","
				+ goods.getScoreDay()
				+ ",'"
				+ goods.getFreight()
				+ "','"
				+ goods.getRemark()
				+ "','"
				+ goods.getKeywords()
				+ "','"
				+ goods.getDetailUrl()
				+ "','"
				+ goods.getIsHot()
				+ "','"
				+ goods.getIsHome()
				+ "','"
				+ goods.getDetails()
				+ "','"
				+ goods.getShowPicture()
				+ "',"
				+ goods.getNumConversion() + ",now(),now())";
		try {
			connection.prepareStatement(sql).execute();
			return insertResult();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ScoreGoodsDo oneGoodsTestRecord() {
		Date d = new Date();
		return new ScoreGoodsDo("goood", 0, DateUtil.getDateAfterDays(d, -30),
				DateUtil.getDateAfterDays(d, 30), 1000, 30, 10000, 7, "0", "",
				"", "", "N", "N", "", "", 0, null, null);
	}

	public ScoreConversionHistoryDo queryOneById(Integer id) {
		String sql = "select `company_id`,`score_goods_id`,`conversion_category`,"
				+ "`company_name`,`contact_name`,`phone`,`tel`,`email`,`keywords`,"
				+ "`status`,`remark`,`gmt_created`,`gmt_modified` "
				+ "from `score_conversion_history` where id=" + id;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				return new ScoreConversionHistoryDo(rs.getInt(1), rs.getInt(2),
						rs.getString(3), rs.getString(4), rs.getString(5), rs
								.getString(6), rs.getString(7),
						rs.getString(8), rs.getString(9), rs.getString(10), rs
								.getString(11), rs.getDate(12), rs.getDate(13));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ScoreConversionHistoryDo oneTestRecord(String status, Integer goodsid) {
		return new ScoreConversionHistoryDo(1, goodsid, "0", "companyname",
				"contactName", "phone", "tel", "email", "keyword", status,
				"remark", null, null);
	}

	public void manyTestRecord(int num, Integer goodsid) {
		for (int i = 0; i < num; i++) {
			createOneRecord(oneTestRecord("0", goodsid));
		}
	}
}

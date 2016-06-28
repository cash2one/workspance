/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-1
 */
package com.ast.ast1949.service.score.impl;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.score.ScoreConversionHistoryDo;
import com.ast.ast1949.domain.score.ScoreGoodsDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.score.ScoreGoodsService;
import com.ast.ast1949.util.DateUtil;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-1
 */
public class ScoreGoodsServiceImplTest extends BaseServiceTestCase {

	@Autowired
	ScoreGoodsService scoreGoodsService;

	@Test
	public void testInsertGoods() {
		clean();
		Integer id = scoreGoodsService.insertGoods(oneTestRecord(0));

		ScoreGoodsDo goods = queryOneById(id);
		assertNotNull(goods);
		assertEquals("goood", goods.getName());

	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.score.impl.ScoreGoodsServiceImpl#pageScoreGoodsByCategory(java.lang.Integer, com.ast.ast1949.dto.PageDto)}
	 * .
	 */
	@Test
	public void testPageScoreGoodsByCategory_category_null() {
		clean();
		manyTestRecord(7, 0);
		manyTestRecord(7, 1);

		PageDto<ScoreGoodsDo> page = new PageDto<ScoreGoodsDo>();
		page.setStartIndex(5);
		page.setPageSize(5);
		page = scoreGoodsService.pageScoreGoodsByCategory(null, page);
		assertNotNull(page.getRecords());

		assertEquals(5, page.getRecords().size());
		assertEquals(14, page.getTotalRecords().intValue());

	}

	@Test
	public void testPageScoreGoodsByCategory_category_notnull() {
		clean();
		manyTestRecord(7, 0);
		manyTestRecord(7, 1);

		PageDto<ScoreGoodsDo> page = new PageDto<ScoreGoodsDo>();
		page.setStartIndex(5);
		page.setPageSize(5);
		page = scoreGoodsService.pageScoreGoodsByCategory(1, page);

		assertNotNull(page.getRecords());
		assertEquals(2, page.getRecords().size());
		assertEquals(7, page.getTotalRecords().intValue());
	}

	@Test
	public void testQueryHotScoreGoods() {
		clean();
		createOneRecord(oneTestRecord(true, null, null));
		createOneRecord(oneTestRecord(true, null, null));
		createOneRecord(oneTestRecord(true, null, null));
		createOneRecord(oneTestRecord(true, null, null));
		createOneRecord(oneTestRecord(true, null, null));
		List<ScoreGoodsDo> list = scoreGoodsService.queryHotScoreGoods(4);
		assertNotNull(list);
		assertEquals(4, list.size());
	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.score.impl.ScoreGoodsServiceImpl#queryIndexScroeGoods(java.lang.Integer)}
	 * .
	 */
	@Test
	public void testQueryIndexScroeGoods() {
		clean();
		createOneRecord(oneTestRecord(null, true, null));
		createOneRecord(oneTestRecord(null, true, null));
		createOneRecord(oneTestRecord(null, true, null));
		createOneRecord(oneTestRecord(null, true, null));
		createOneRecord(oneTestRecord(null, true, null));
		List<ScoreGoodsDo> list = scoreGoodsService.queryIndexScroeGoods(4);
		assertNotNull(list);
		assertEquals(4, list.size());
	}

	@Test
	public void testQueryMostConversionGoods() {
		clean();
		createOneRecord(oneTestRecord(null, null, 0));
		createOneRecord(oneTestRecord(null, null, 10));
		createOneRecord(oneTestRecord(null, null, 20));
		createOneRecord(oneTestRecord(null, null, 30));
		createOneRecord(oneTestRecord(null, null, 40));
		
		List<ScoreGoodsDo> list = scoreGoodsService.queryMostConversionGoods(3);
		assertNotNull(list);
		assertEquals(3, list.size());
		ScoreGoodsDo goods = list.get(0);
		assertEquals(40, goods.getNumConversion().intValue());
	}

//	@Test
//	public void testQueryScoreGoodsByCategory() {
//		clean();
//		createOneRecord(oneTestRecord(0));
//		createOneRecord(oneTestRecord(0));
//		createOneRecord(oneTestRecord(0));
//		createOneRecord(oneTestRecord(0));
//		createOneRecord(oneTestRecord(1));
//		createOneRecord(oneTestRecord(1));
//		createOneRecord(oneTestRecord(1));
//		createOneRecord(oneTestRecord(1));
//		
//		List<ScoreGoodsDo> list = scoreGoodsService.queryScoreGoodsByCategory(null, 10);
//		assertNotNull(list);
//		assertEquals(8, list.size());
//		
//		list = scoreGoodsService.queryScoreGoodsByCategory(0, 10);
//		assertNotNull(list);
//		assertEquals(4, list.size());
//		
//		list = scoreGoodsService.queryScoreGoodsByCategory(1, 3);
//		assertNotNull(list);
//		assertEquals(3, list.size());
//	}

	/**
	 * Test method for
	 * {@link com.ast.ast1949.service.score.impl.ScoreGoodsServiceImpl#updateGoodsById(com.ast.ast1949.domain.score.ScoreGoodsDo)}
	 * .
	 */
	@Test
	public void testUpdateGoodsById() {
		clean();
		Integer id = createOneRecord(oneTestRecord(0));
		ScoreGoodsDo goods = queryOneById(id);
		goods.setId(id);
		goods.setName("new goods name");
		goods.setIsHot("Y");
		goods.setIsHome("Y");
		Integer i = scoreGoodsService.updateGoodsById(goods);
		assertEquals(1, i.intValue());
		ScoreGoodsDo newgoods = queryOneById(id);
		assertEquals("new goods name", newgoods.getName());
		assertEquals("Y", newgoods.getIsHot());
		assertEquals("Y", newgoods.getIsHome());
		
	}

	/****************************/
	public void clean() {
		try {
			connection.prepareStatement("delete from score_goods").execute();
		} catch (SQLException e) {
		}
	}

	public Integer createOneRecord(ScoreGoodsDo goods) {
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

	public ScoreGoodsDo oneTestRecord(Integer category) {
		Date d = new Date();
		return new ScoreGoodsDo("goood", category, DateUtil.getDateAfterDays(d,
				-30), DateUtil.getDateAfterDays(d, 30), 1000, 30, 10000, 7,
				"0", "", "", "", "N", "N", "", "", 0, null, null);
	}

	public ScoreGoodsDo oneTestRecord(Boolean hot, Boolean index, Integer num) {
		String hotStr = "N", indexStr = "N";
		if (hot != null && hot) {
			hotStr = "Y";
		}
		if (index != null && index) {
			indexStr = "Y";
		}
		Date d = new Date();
		if (num == null) {
			num = 0;
		}
		return new ScoreGoodsDo("goood", 0, DateUtil.getDateAfterDays(d, -30),
				DateUtil.getDateAfterDays(d, 30), 1000, 30, 10000, 7, "0", "",
				"", "", hotStr, indexStr, "", "", num, null, null);
	}

	public void manyTestRecord(int num, Integer category) {
		if (category == null) {
			category = 0;
		}

		for (int i = 0; i < num; i++) {
			createOneRecord(oneTestRecord(category));
		}
	}

	public ScoreGoodsDo queryOneById(Integer id) {
		String sql = "select `name`,`category`,`gmt_start`,`gmt_end`,`price_buy`,"
				+ "`price_day`,`score_buy`,`score_day`,`freight`,`remark`,`keywords`,"
				+ "`detail_url`,`is_hot`,`is_home`,`details`,`show_picture`,"
				+ "`num_conversion`,`gmt_created`,`gmt_modified` from  `score_goods` where id="
				+ id;

		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				return new ScoreGoodsDo(rs.getString(1), rs.getInt(2), rs
						.getDate(3), rs.getDate(4), rs.getInt(5), rs.getInt(6),
						rs.getInt(7), rs.getInt(8), rs.getString(9), rs
								.getString(10), rs.getString(11), rs
								.getString(12), rs.getString(13), rs
								.getString(14), rs.getString(15), rs
								.getString(16), rs.getInt(17), rs.getDate(18),
						rs.getDate(19));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
}

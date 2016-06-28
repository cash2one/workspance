/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-1
 */
package com.ast.ast1949.service.score.impl;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.score.ScoreSummaryDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.score.ScoreSummaryDto;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.score.ScoreSummaryService;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-1
 */
public class ScoreSummaryServiceImplTest extends BaseServiceTestCase {

	@Autowired
	ScoreSummaryService scoreSummaryService;
	
	@Test
	public void testPageSummaryByCondictions() {
		clean();
		for(int i=0;i<18;i++){
			createOneRecord(oneTestRecord(i+1,10*i));
		}
		
		PageDto<ScoreSummaryDto > page = new PageDto<ScoreSummaryDto>();
		page.setStartIndex(10);
		page.setPageSize(10);
		ScoreSummaryDto summary = new ScoreSummaryDto();
		page = scoreSummaryService.pageSummaryByCondictions(summary,page);

		assertEquals(8, page.getRecords().size());
		assertEquals(18, page.getTotalRecords().intValue());
	}

	@Test
	public void testQueryMostOfUserScore() {
		clean();
		createOneRecord(oneTestRecord(1,100));
		createOneRecord(oneTestRecord(2,200));
		createOneRecord(oneTestRecord(3,300));
		createOneRecord(oneTestRecord(4,400));
		List<ScoreSummaryDto> list = scoreSummaryService.queryMostOfUserScore(3);
		assertNotNull(list);
		assertEquals(3, list.size());
		assertEquals(400, list.get(0).getSummary().getScore().intValue());
	}

	@Test
	public void testQuerySummaryByCompanyId() {
		clean();
		createOneRecord(oneTestRecord(1,1000));
		ScoreSummaryDo summary = scoreSummaryService.querySummaryByCompanyId(1);
		assertNotNull(summary);
		assertEquals(1000, summary.getScore().intValue());
	}

	/********************/
	public void clean() {
		try {
			connection.prepareStatement("delete from score_summary").execute();
		} catch (SQLException e) {
		}
	}

	public Integer createOneRecord(ScoreSummaryDo summary) {
		String sql = "insert into `score_summary`(`company_id`,`score`,`sub_category`,"
				+ "`gmt_created`,`gmt_modified`)"
				+ "values("
				+ summary.getCompanyId()
				+ ","
				+ summary.getScore()
				+ ",'"
				+ summary.getSubCategory() + "',now(),now())";
		try {
			connection.prepareStatement(sql).execute();
			return insertResult();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ScoreSummaryDo oneTestRecord(Integer companyid,Integer score){
		return new ScoreSummaryDo(companyid, score, "", null, null);
	}

}

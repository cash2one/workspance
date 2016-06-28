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

import com.ast.ast1949.domain.score.ScoreExchangeRulesDo;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.score.ScoreExchangeRulesService;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-1
 */
public class ScoreExchangeRulesServiceImplTest extends BaseServiceTestCase {

	@Autowired
	ScoreExchangeRulesService scoreExchangeRulesService;
	
	@Test
	public void testQueryRulesByPreCode() {
		clean();
		for(int i=0;i<10;i++){
			createOneRecord(oneTestRecord("test_"+i));
		}
		createOneRecord(oneTestRecord("notest_0"));
		createOneRecord(oneTestRecord("notest_1"));
		createOneRecord(oneTestRecord("notest_2"));
		List<ScoreExchangeRulesDo> list = scoreExchangeRulesService.queryRulesByPreCode("test");
		
		assertNotNull(list);
		assertEquals(10, list.size());
	}

	/********************/
	public void clean() {
		try {
			connection.prepareStatement("delete from score_exchange_rules")
					.execute();
		} catch (SQLException e) {
		}
	}

	public Integer createOneRecord(ScoreExchangeRulesDo rules) {
		String sql = "insert into `score_exchange_rules`(`rules_code`,"
				+ "`name`,`score`,`score_max`,`cycle_day`,`remark`,"
				+ "`gmt_created`,`gmt_modified`)" + "values('"
				+ rules.getRulesCode() + "','" + rules.getName() + "',"
				+ rules.getScore() + "," + rules.getScoreMax() + ","
				+ rules.getCycleDay() + ",'" + rules.getRemark()
				+ "',now(),now())";
		try {
			connection.prepareStatement(sql).execute();
			return insertResult();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ScoreExchangeRulesDo oneTestRecord(String rulesCode) {
		return new ScoreExchangeRulesDo(rulesCode, "test", 10, 0, 0, "", null,
				null);
	}
}

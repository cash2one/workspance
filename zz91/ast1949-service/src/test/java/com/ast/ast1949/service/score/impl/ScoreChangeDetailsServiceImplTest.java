/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-1
 */
package com.ast.ast1949.service.score.impl;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-1
 */
public class ScoreChangeDetailsServiceImplTest extends BaseServiceTestCase {

	@Autowired
	private ScoreChangeDetailsService scoreChangeDetailsService;

	@Test
	public void testPageChangeDetailsByCompanyId() {
		clean();
		create(18,10);
		PageDto<ScoreChangeDetailsDo> page = new PageDto<ScoreChangeDetailsDo>();
		page.setPageSize(10);
		page.setStartIndex(0);
		page = scoreChangeDetailsService.pageChangeDetailsByCompanyId(1, null,
				page);
		assertNotNull(page);
		assertEquals(10, page.getRecords().size());
		assertEquals(18, page.getTotalRecords().intValue());
		
		page.setPageSize(10);
		page.setStartIndex(10);
		page = scoreChangeDetailsService.pageChangeDetailsByCompanyId(1, null,
				page);
		assertNotNull(page);
		assertEquals(8, page.getRecords().size());
		assertEquals(18, page.getTotalRecords().intValue());
	}
	
	@Test
	public void testPageChangeDetailsByCompanyId_isPlus_not_null(){
		
		clean();
		create(12, 10);
		create(10, -10);
		PageDto<ScoreChangeDetailsDo> page = new PageDto<ScoreChangeDetailsDo>();
		page.setPageSize(10);
		page.setStartIndex(10);
		page = scoreChangeDetailsService.pageChangeDetailsByCompanyId(1, true,
				page);
		
		assertNotNull(page);
		assertEquals(2, page.getRecords().size());
		assertEquals(12, page.getTotalRecords().intValue());
	}

	@Test
	public void testSaveChangeDetails() {
		fail("Not yet implemented");
	}

	/**
	 * 创建数据
	 * 
	 * @return
	 */
	public void create(int num, int score) {
		String sql = "";
		try {
			for (int i = 0; i < num; i++) {
				sql = "INSERT INTO score_change_details(company_id,name,rules_code,score,related_id,remark,gmt_created,gmt_modified) "
						+ "VALUES(1,'名称" + num
						+ "','rule_code_" + num
						+ "',"+score+",0,'这是备注" + num + "',NOW(),NOW())";
				connection.prepareStatement(sql)
						.execute();
			}

		} catch (SQLException e) {
		}
	}

	/**
	 * 清理数据
	 */
	public void clean() {
		try {
			connection.prepareStatement("DELETE FROM score_change_details")
					.execute();
		} catch (SQLException e) {
		}
	}
}

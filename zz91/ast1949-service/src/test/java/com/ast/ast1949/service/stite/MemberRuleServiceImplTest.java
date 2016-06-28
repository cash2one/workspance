/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23
 */
package com.ast.ast1949.service.stite;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.site.MemberRuleService;

/**
 * @author yuyonghui
 * 
 */
public class MemberRuleServiceImplTest extends BaseServiceTestCase {
	@Autowired
	private MemberRuleService memberRuleService;

//	public void test_queryMemberRuleByCondition() {
//		clean();
//		MemberRuleDO memberRuleDO = new MemberRuleDO();
//		memberRuleDO.setMembershipCode("1006");
//		memberRuleService.queryMemberRuleByCondition(memberRuleDO);
//	}
//
//	public void test_queryMemberRuleByCondition_MembershipCode_isNull() {
//		clean();
//		MemberRuleDO memberRuleDO = new MemberRuleDO();
//		try {
//			memberRuleService.queryMemberRuleByCondition(memberRuleDO);
//			fail();
//		} catch (IllegalArgumentException e) {
//			assertEquals("membershipCode is not null", e.getMessage());
//		}
//	}

	public void clean() {
		try {
			cleanMemberRuleTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void cleanMemberRuleTable() throws SQLException {
		connection.prepareStatement("delete from member_rule").execute();
	}
}

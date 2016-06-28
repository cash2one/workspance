/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23
 */
package com.ast.ast1949.persist.site.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.site.MemberRuleDO;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.site.MemberRuleDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
@Component("memberRuleDAO")
public class MemberRuleDAOImpl extends BaseDaoSupport implements MemberRuleDAO {
	private static String sqlPreFix = "memberRule";

	@SuppressWarnings("unchecked")
	public List<MemberRuleDO> queryMemberRuleByCondition(MemberRuleDO memberRuleDO) {
		Assert.notNull(memberRuleDO.getMembershipCode(), "memberShipCode is not null");
		return getSqlMapClientTemplate().queryForList("memberRule.queryMemberRuleByCondition",
				memberRuleDO);
	}

	@Override
	public void clearAllData() {
		clearAllData(addSqlKeyPreFix(sqlPreFix, "clearAllData"));
	}

	@Override
	public Integer deleteMemberRuleById(Integer id) {
		return delete(addSqlKeyPreFix(sqlPreFix, "deleteMemberRuleById"), id);
	}

	@Override
	public Integer insertMemberRule(MemberRuleDO rule) {
		rule.setSqlKey(addSqlKeyPreFix(sqlPreFix, "insertMemberRule"));
		return insert(rule);
	}

	@Override
	public List<MemberRuleDO> queryAllMemberRuleByType(String memberTypeCode) {
		return (List<MemberRuleDO>) getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryAllMemberRuleByType"), memberTypeCode);
	}

	@Override
	public MemberRuleDO queryMemberRuleByTypeAndCode(String memberTypeCode, String ruleCode) {
		Map paramMap=new HashMap();
		paramMap.put("memberTypeCode", memberTypeCode);
		paramMap.put("ruleCode", ruleCode);
		return (MemberRuleDO) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryMemberRuleByTypeAndCode"), paramMap);
	}

	@Override
	public List<MemberRuleDO> queryMemberRuleList() {
		return (List<MemberRuleDO>) queryAll(addSqlKeyPreFix(sqlPreFix, "queryMemberRuleList"));
	}

	@Override
	public Integer updateMemberRule(MemberRuleDO rule) {
		rule.setSqlKey(addSqlKeyPreFix(sqlPreFix, "updateMemberRule"));
		return update(rule);
	}

}

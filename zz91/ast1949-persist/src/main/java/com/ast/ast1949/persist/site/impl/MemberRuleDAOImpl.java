/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23
 */
package com.ast.ast1949.persist.site.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.site.MemberRuleDO;
import com.ast.ast1949.persist.BaseDaoSupportMultipleDataSource;
import com.ast.ast1949.persist.site.MemberRuleDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
@Component("memberRuleDAO")
public class MemberRuleDAOImpl extends BaseDaoSupportMultipleDataSource implements MemberRuleDAO {
	private static String sqlPreFix = "memberRule";
//	@Resource
//	private BaseDaoSupportMultipleDataSource baseDaoSupportMultipleDataSource;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberRuleDO> queryMemberRuleList() {
		try {
			return (List<MemberRuleDO>) getSqlMapClient2().queryForList(addSqlKeyPreFix(sqlPreFix, "queryMemberRuleList"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer updateMemberRule(MemberRuleDO rule) {
		rule.setSqlKey(addSqlKeyPreFix(sqlPreFix, "updateMemberRule"));
		return update(rule);
	}

}

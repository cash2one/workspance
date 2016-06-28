/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23
 */
package com.ast.ast1949.service.site.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.site.MemberRuleDO;
import com.ast.ast1949.persist.site.MemberRuleDAO;
import com.ast.ast1949.service.site.MemberRuleService;

/**
 * @author yuyonghui
 *
 */
@Component("memberRuleService")
public class MemberRuleServiceImpl implements MemberRuleService {

	@Autowired
	private MemberRuleDAO memberRuleDAO;

//	public List<MemberRuleDO> queryMemberRuleByCondition(
//			MemberRuleDO memberRuleDO) {
//		Assert.notNull(memberRuleDO.getMembershipCode(),
//				"membershipCode is not null");
//		if (memberRuleDO.getMembershipCode() != null) {
//			return memberRuleDAO.queryMemberRuleByCondition(memberRuleDO);
//		} else {
//			return null;
//		}
//
//	}
//
//	public String listOneResultByAccount(String account, String operation) {
//		CompanyAccount companyAccount = null;
//		if (StringUtils.isEmail(account)) {
//			companyAccount = companyService
//					.selectCompanyIdByEmail(account);
//
//		} else {
//			companyAccount = companyAccountService.queryAccountByAccountOrEmail(account);
//		}
//
//		String membership = companyService.queryMembershipOfCompany(companyAccount.getCompanyId());
//		MemberRuleDO memberRuleDO = new MemberRuleDO();
//		if (StringUtils.isNotEmpty(membership)) {
//			memberRuleDO.setMembershipCode(membership);
//		}else{
//			memberRuleDO.setMembershipCode(AstConst.COMMON_MEMBERSHIP_CODE);
//		}
//		memberRuleDO.setOperationCode(operation);
//		List<MemberRuleDO> list = memberRuleDAO
//				.queryMemberRuleByCondition(memberRuleDO);
//		String results = "";
//		for (MemberRuleDO mRuleDO : list) {
//			results = mRuleDO.getResults();
//		}
//		return results;
//	}

//	public String listOneResultByMemberShip(String memberShip, String operation) {
//		MemberRuleDO memberRuleDO = new MemberRuleDO();
//		if (memberShip != null) {
//			memberRuleDO.setMembershipCode(memberShip);
//		}
//		memberRuleDO.setOperationCode(operation);
//		List<MemberRuleDO> list = memberRuleDAO
//				.queryMemberRuleByCondition(memberRuleDO);
//		String results = "";
//		for (MemberRuleDO mRuleDO : list) {
//			results = mRuleDO.getResults();
//		}
//		return results;
//	}

//	@Override
//	public String queryMemberRuleByTypeAndCode(String memberTypeCode, String ruleCode) {
//		Assert.notNull(memberTypeCode, "会员类型编码不能为空");
//		Assert.notNull(ruleCode, "查询的规则编码不能为空");
//		MemberRuleDO memberRuleDO=memberRuleDAO.queryMemberRuleByTypeAndCode(memberTypeCode, ruleCode);
//		if(memberRuleDO!=null&&StringUtils.isNotEmpty(memberRuleDO.getResults())){
//			return memberRuleDO.getResults();
//		}
//		return null;
//	}

	@Override
	public List<MemberRuleDO> queryMemberRuleList() {
		return memberRuleDAO.queryMemberRuleList();
	}
}

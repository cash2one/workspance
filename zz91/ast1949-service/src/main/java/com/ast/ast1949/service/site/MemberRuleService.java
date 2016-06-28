/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23
 */
package com.ast.ast1949.service.site;

import java.util.List;

import com.ast.ast1949.domain.site.MemberRuleDO;

/**
 * @author yuyonghui
 * 
 */
public interface MemberRuleService {

	/**
	 * 根据会员类型和规则类型获取规则
	 * 
	 * @param memberTypeCode
	 * @param paramCode
	 * @return 规则内容
	 */
//	public String queryMemberRuleByTypeAndCode(String memberTypeCode, String ruleCode);
	
	public List<MemberRuleDO> queryMemberRuleList();
	/**
	 * 根据 membershipCode option optionCode 查询
	 * 
	 * @param memberRuleDO
	 *            membershipCode 不能为空 option optionCode
	 * @return 结果集
	 */
//	public List<MemberRuleDO> queryMemberRuleByCondition(MemberRuleDO memberRuleDO);

	/**
	 * 
	 * @param account
	 *            按登录
	 * @param operation
	 *            操作编号
	 * @return results
	 */
//	public String listOneResultByAccount(String account, String operation);

//	/**
//	 * 
//	 * @param memberShip
//	 *            会员类型
//	 * @param operation
//	 *            操作编号
//	 * @return
//	 */
//	public String listOneResultByMemberShip(String memberShip, String operation);
}

/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23
 */
package com.ast.ast1949.persist.site;

import java.util.List;

import com.ast.ast1949.domain.site.MemberRuleDO;

/**
 * @author yuyonghui
 * 
 */
public interface MemberRuleDAO {

	/**
	 * 新增信息
	 * 
	 * @param rule
	 * @return
	 */
	Integer insertMemberRule(MemberRuleDO rule);

	/**
	 * 更新信息
	 * 
	 * @param rule
	 * @return
	 */
	Integer updateMemberRule(MemberRuleDO rule);

	/**
	 * 删除参数
	 * 
	 * @param id
	 * @return
	 */
	Integer deleteMemberRuleById(Integer id);

	/**
	 * 根据会员类型和规则类型获取规则
	 * 
	 * @param memberTypeCode
	 * @param paramCode
	 * @return
	 */
	MemberRuleDO queryMemberRuleByTypeAndCode(String memberTypeCode, String ruleCode);

	/**
	 * 查询所有参数
	 * 
	 * @return
	 */
	List<MemberRuleDO> queryMemberRuleList();

	/**
	 * 清除所有信息
	 */
	void clearAllData();

	/**
	 * 查询指定会员类型的所有参数
	 * 
	 * @param memberTypeCode
	 * @return
	 */
	List<MemberRuleDO> queryAllMemberRuleByType(String memberTypeCode);

	/**
	 * 根据 membershipCode option optionCode 查询
	 * 
	 * @param memberRuleDO
	 *            membershipCode 不能为空 option optionCode
	 * @return 结果集
	 */
	public List<MemberRuleDO> queryMemberRuleByCondition(MemberRuleDO memberRuleDO);
}

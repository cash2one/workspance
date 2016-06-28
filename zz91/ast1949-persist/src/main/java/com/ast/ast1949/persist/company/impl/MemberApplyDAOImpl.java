/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-17
 */
package com.ast.ast1949.persist.company.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.MemberApplyDO;
import com.ast.ast1949.dto.company.MemberApplyDTO;
import com.ast.ast1949.persist.company.MemberApplyDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("memberApplyDAO")
public class MemberApplyDAOImpl extends SqlMapClientDaoSupport implements MemberApplyDAO {

	public Integer insertMemberApply(MemberApplyDO memberApplyDO) {
		Assert.notNull(memberApplyDO, "the object of MemberApplyDO must not be null");
		Assert.notNull(memberApplyDO.getMembershipCode(), "the MembershipCode must not be null");
		Assert.notNull(memberApplyDO.getUsername(), "the UserName must not be null");
		Assert.notNull(memberApplyDO.getTel(), "the Tel must not be null");
		Assert.notNull(memberApplyDO.getEmail(), "the Email must not be null");
		Assert.notNull(memberApplyDO.getEmail(), "the Email must not be null");

		return (Integer) getSqlMapClientTemplate().insert("memberApply.insertMemberApply",memberApplyDO);
	}

	public Integer countMemberApplyList(MemberApplyDTO memberApplyDTO) {
		Assert.notNull(memberApplyDTO, "the object of memberApplyDTO must not be null");
		return (Integer) getSqlMapClientTemplate().queryForObject("memberApply.countMemberApplyList",memberApplyDTO);
	}

	@SuppressWarnings("unchecked")
	public List<MemberApplyDTO> queryMemberApplyList(MemberApplyDTO memberApplyDTO) {
		Assert.notNull(memberApplyDTO, "the object of memberApplyDTO must not be null");
		return getSqlMapClientTemplate().queryForList("memberApply.queryMemberApplyList", memberApplyDTO);
	}

	public Integer updateProcessStatusById(Map<String, Object> param) {
		Assert.notNull(param, "the param must not be null");
		return getSqlMapClientTemplate().update("memberApply.updateProcessStatusById", param);
	}
}

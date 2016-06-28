/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-17
 */
package com.ast.ast1949.service.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.dto.company.MemberApplyDTO;
import com.ast.ast1949.persist.company.MemberApplyDAO;
import com.ast.ast1949.service.company.MemberApplyService;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("memberApplyService")
public class MemberApplyServiceImpl implements MemberApplyService {
	@Autowired
	private MemberApplyDAO memberApplyDAO;

//	public Integer insertMemberApply(MemberApplyDO memberApplyDO) {
//
//		Assert.notNull(memberApplyDO, "the object of MemberApplyDO must not be null");
//		Assert.notNull(memberApplyDO.getMembershipCode(), "the MembershipCode must not be null");
//		Assert.notNull(memberApplyDO.getUsername(), "the username must not be null");
//		Assert.notNull(memberApplyDO.getTel(), "the Tel must not be null");
//		Assert.notNull(memberApplyDO.getEmail(), "the Email must not be null");
//		Assert.notNull(memberApplyDO.getEmail(), "the Email must not be null");
//
//		memberApplyDO.setProcessStatus(AstConst.PROCESS_STATUS_NO);
//		memberApplyDO.setGmtCreated(new Date());
//		memberApplyDO.setGmtModified(new Date());
//
//		return memberApplyDAO.insertMemberApply(memberApplyDO);
//	}

	public Integer countMemberApplyList(MemberApplyDTO memberApplyDTO) {
		return memberApplyDAO.countMemberApplyList(memberApplyDTO);
	}

	public List<MemberApplyDTO> queryMemberApplyList(
			MemberApplyDTO memberApplyDTO) {
		return memberApplyDAO.queryMemberApplyList(memberApplyDTO);
	}

	public Integer updateProcessStatusById(String processStatus,
			String processPerson, String remark, Integer id) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(processStatus, "the processStatus must not be null");
		
		Map<String, Object> param =new HashMap<String, Object>();
		param.put("id", id);
		param.put("processStatus", processStatus);
		param.put("processPerson", processPerson);
		param.put("remark", remark);
		
		return memberApplyDAO.updateProcessStatusById(param);
	}


}

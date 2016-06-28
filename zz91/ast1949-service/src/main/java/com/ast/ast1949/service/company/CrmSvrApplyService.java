package com.ast.ast1949.service.company;

import com.ast.ast1949.domain.company.CrmServiceApply;


public interface CrmSvrApplyService {
 
	/**
	 * 根据服务开通组查找开通申请单
	 *
	 *  
	 */
	public CrmServiceApply queryApplyByGroup(String applyGroup);
	/**
	 * 更新开通申请，
	 *
	 *  
	 */
	public Integer updateApply(CrmServiceApply apply);
	
}

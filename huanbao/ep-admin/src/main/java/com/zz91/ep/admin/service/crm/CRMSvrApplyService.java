package com.zz91.ep.admin.service.crm;

import com.zz91.ep.domain.crm.CrmCompSvr;
import com.zz91.ep.domain.crm.CrmSvrApply;

public interface CRMSvrApplyService {

	/**
	 * 提交服务申请
	 * @param companyId
	 * @param split
	 * @param apply
	 * @param crmCompSvr 
	 * @return
	 */
	public Boolean applySvr(Integer companyId, String[] svrCodes, CrmSvrApply apply, CrmCompSvr crmCompSvr);

	/**
	 * 通过服务组号查询服务信息
	 * @param applyGroup
	 * @return
	 */
	public CrmSvrApply queryApplyByGroup(String applyGroup);

	/**
	 * 分配服务申请给指定客服
	 * @param id
	 * @param account
	 * @return
	 */
	public Integer assignApplyToCs(Integer id, String account);
}

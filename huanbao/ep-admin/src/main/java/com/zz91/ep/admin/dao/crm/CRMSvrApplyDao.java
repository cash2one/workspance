package com.zz91.ep.admin.dao.crm;

import com.zz91.ep.domain.crm.CrmCsComp;
import com.zz91.ep.domain.crm.CrmSvrApply;

/**
 * 
 * @author leon
 * 2011-09-16
 */
public interface CRMSvrApplyDao {

	/**
	 * 新增服务开通申请单
	 * @param apply
	 * @return
	 */
	public Integer insertApply(CrmSvrApply apply);

	/**
	 * 通过组号查询申请信息
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
	public Integer assignApplyToCs(CrmCsComp crmCsComp);

	/**
	 * 查询是否存在公司已分配客服的记录
	 * @param id
	 * @return
	 */
	public Integer queryCompWithCs(Integer id);

	/**
	 * 更新删除状态(用于重新分配)
	 * @param id
	 * @return
	 */
	public Integer updateDelStatus(Integer id);
}
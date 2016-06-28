package com.zz91.ep.admin.service.crm;

import java.util.List;

import com.zz91.ep.domain.crm.CrmCompSvr;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.crm.CrmCompSvrDto;

public interface CRMCompSvrService {

	/**
	 * 查询公司申请的服务信息
	 * @param svrCode
	 * @param applyStatus
	 * @param page
	 * @return
	 */
	public PageDto<CrmCompSvrDto> pageApplyCompany(String svrCode, String applyStatus,
			PageDto<CrmCompSvrDto> page,String account);

	/**
	 * 查询公司历史服务
	 * @param companyId
	 * @return
	 */
	public List<CrmCompSvrDto> queryApplySvrHistory(Integer companyId);

	/**
	 * 查看申请组中已经开通的服务个数
	 * @param applyGroup
	 * @return
	 */
	public Integer countOpenedApplyByGroup(String applyGroup);

	/**
	 * 查询服务组所包含的服务
	 * @param applyGroup
	 * @return
	 */
	public List<CrmCompSvrDto> queryApplyByGroup(String applyGroup);

	/**
	 * 查询该服务历史开通记录
	 * @param companyId
	 * @param crmSvrId
	 * @return
	 */
	public List<CrmCompSvr> querySvrHistory(Integer companyId, Integer crmSvrId);

	/**
	 * 开通服务
	 * @param crmCompSvr
	 * @return
	 */
	public Integer openCrmCompSvr(CrmCompSvr crmCompSvr);

	public Boolean openSvr(CrmCompSvr svr, Integer cid);

	public CrmCompSvr queryRecentHistory(Integer crmSvrId, Integer cid, Integer id);

	public CrmCompSvr queryCompSvrById(Integer id);

	public Boolean closeSvr(Integer companySvrId);

	public CrmCompSvr queryRecentHistory(String svrCode, Integer companyId,
			Integer companySvrId);

	public CrmCompSvr queryCompanySvrById(Integer companySvrId);

	public Boolean openZhtSvr(CrmCompSvr svr, String domainZz91, String membershipCode);

	public Boolean refusedApply(String applyGroup);

	/**
	 * 关闭SEO服务
	 * @param cid
	 * @param companySvrId
	 * @return
	 */
	public Boolean closeSeoSvr(Integer cid, Integer companySvrId);


}

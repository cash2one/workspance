package com.zz91.ep.admin.dao.crm;

import java.util.List;

import com.zz91.ep.domain.crm.CrmCompSvr;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.crm.CrmCompSvrDto;

/**
 * 
 * @author leon
 * EP系统公司拥有服务操作的DAO，负责对公司服务的操作
 * 2011-09-16
 */
public interface CRMCompSvrDao {

	/**
	 * 通过公司ID查询公司所拥有的服务
	 * @param companyId
	 * @return
	 */
	public int[] queryCrmSvrIdByCid(Integer companyId);

	public Integer insertCompSvr(CrmCompSvr companySvr);

	public List<CrmCompSvrDto> queryApplyCompany(Integer crmSvrId,
			String applyStatus, PageDto<CrmCompSvrDto> page, String account);

	public Integer queryApplyCompanyCount(Integer crmSvrId, String applyStatus, String account);

	public List<CrmCompSvr> queryApplySvrHistory(Integer companyId);

	public Integer countOpenedApplyByGroup(String applyGroup);

	public List<CrmCompSvrDto> queryApplyByGroup(String applyGroup);

	public List<CrmCompSvr> querySvrHistory(Integer companyId,
			Integer crmSvrId);

	public Integer updateCrmCompSvr(CrmCompSvr crmCompSvr);

	public CrmCompSvr queryRecentHistory(Integer crmSvrId, Integer cid,
			Integer id);

	public CrmCompSvr queryCompanySvrById(Integer companySvrId);

	public Integer refusedApplyUpdateApplyStatus(String applyGroup, Short applyStatusFailure);

	/**
	 * 更新申请状态(服务关闭)
	 * @param companySvrId
	 * @param status
	 * @return
	 */
	public Integer updateSvrStatusById(Integer companySvrId,
			Short status);

}

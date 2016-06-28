package com.zz91.ep.admin.service.crm;

import java.util.List;

import com.zz91.ep.domain.crm.CrmSvr;
import com.zz91.ep.dto.ExtTreeDto;

public interface CRMSvrService {
	/**
	 * 查询所有服务类型
	 * @return
	 */
	public List<CrmSvr> queryCrmSvr();

	/**
	 * 创建服务类型
	 * @param CrmSvr
	 * @param parentCode 
	 * @return
	 */
	public Integer createCrmSvr(CrmSvr CrmSvr, String parentCode);

	/**
	 * 删除角色信息 先删除角色权限关联，再删除角色
	 */
	public Integer deleteCrmSvr(String code);

	/**
	 * 更新服务类型
	 * @param CrmSvr
	 * @return
	 */
	public Integer updateCrmSvr(CrmSvr CrmSvr);

	/**
	 * 更新服务类型包含的权限
	 * @param roleId
	 * @param rightId
	 * @param checked
	 * @return
	 */
	public Integer updatecrmSvrRight(Integer crmSvrId, Integer crmRightId,
			Boolean checked);

	public List<ExtTreeDto> queryRightTreeNode(String parentCode,
			Integer crmSvrId);

	public List<ExtTreeDto> queryCrmSvrNode(String parentCode);

	public CrmSvr queryOneCrmSvr(String code);

	public String queryOpenApi(Integer crmSvrId);

	public String queryCloseApi(Integer crmSvrId);
}

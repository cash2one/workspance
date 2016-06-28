package com.zz91.ep.admin.service.crm;

import java.util.List;

import com.zz91.ep.domain.crm.CrmMember;
import com.zz91.ep.dto.ExtTreeDto;

public interface CRMMemberService {
	/**
	 * 查询所有会员类型
	 * @return
	 */
	public List<CrmMember> queryCrmMember();

	/**
	 * 创建会员类型
	 * @param crmMember
	 * @param parentCode 
	 * @return
	 */
	public Integer createCrmMember(CrmMember crmMember, String parentCode);

	/**
	 * 删除角色信息 先删除角色权限关联，再删除角色
	 */
	public Integer deleteCrmMember(String crmMemberCode);

	/**
	 * 更新会员类型
	 * @param crmMember
	 * @return
	 */
	public Integer updateCrmMember(CrmMember crmMember);

	/**
	 * 更新会员类型包含的权限
	 * @param roleId
	 * @param rightId
	 * @param checked
	 * @return
	 */
	public Integer updateCrmMemberRight(String crmMemberCode, Integer crmRightId,
			Boolean checked);

	public List<ExtTreeDto> queryRightTreeNode(String parentCode, String memberCode);

	public List<ExtTreeDto> queryCrmMemberNode(String parentCode);

	public CrmMember queryOneCrmMember(String memberCode);
	
	public List<CrmMember> queryChildMembers(String parentCode);
}

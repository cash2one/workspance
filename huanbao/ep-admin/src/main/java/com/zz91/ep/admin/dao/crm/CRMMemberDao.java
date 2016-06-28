package com.zz91.ep.admin.dao.crm;

import java.util.List;

import com.zz91.ep.domain.crm.CrmMember;
/**
 * 会员信息DAO
 * @author leon
 *
 */
public interface CRMMemberDao {
	/**
	 * 查询会员列表
	 */
	public List<CrmMember> queryCrmMemberList();
	
	/**
	 * 插入会员对象
	 * @param CrmMember
	 * @return
	 */
	public Integer insertCrmMember(CrmMember crmMember);
	
	/**
	 * 通过会员code删除会员
	 * @param CrmMemberId
	 * @return
	 */
	public Integer deleteCrmMember(String crmMemberCode);

	/**
	 * 通过会员code删除会员所拥有的权限
	 */
	public Integer deleteCrmRightOfCrmMember(String crmMemberCode);

	/**
	 * 插入中间表crm_member_right
	 * @param CrmMemberId
	 * @param rightId
	 * @return
	 */
	public Integer insertCrmMemberCrmRight(String crmMemberCode, Integer crmRightId);

	/**
	 * 删除中间表记录crm_member_right
	 * @param crmMemberId
	 * @param rightId
	 * @return
	 */
	public Integer deleteCrmMemberCrmRight(String crmMemberCode, Integer rightId);

	/**
	 * 通过会员code查询所有权限id列表
	 * @param crmMemberId
	 * @return
	 */
	public List<Integer> queryCrmRightIdOfCrmMember(String crmMemberCode);

	/**
	 * 更新会员信息
	 * @param CrmMember
	 * @return
	 */
	public Integer updateCrmMember(CrmMember crmMember);

	public List<CrmMember> queryCrmMemberChild(String parentCode);

	public Integer countCrmMemberChild(String code);

	public String queryMaxCodeOfChild(String parentCode);

	public CrmMember queryOneCrmMember(String memberCode);
	
	public String queryNameByCode(String memberCode);
}

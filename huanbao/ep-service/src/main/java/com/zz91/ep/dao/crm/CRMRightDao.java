package com.zz91.ep.dao.crm;

import java.util.List;

import com.zz91.ep.domain.crm.CrmRight;

/**
 * 
 * @author leon
 * EP系统所有权限操作的DAO，负责对权限的增删改查操作
 * 2011-09-16
 */
public interface CRMRightDao {
	/**
	 * 根据父权限code查询子节点
	 */
	public List<CrmRight> queryChildRight(String parentCode);
	/**
	 * 查询父类所属子节点总数
	 * @param parentCode
	 * @return
	 */
	public Integer countChildRight(String parentCode);
	/**
	 * 创建权限信息，code根据类别生成
	 * code生成规则：同级类别最大code＋1
	 */
	public Integer createRight(CrmRight right);
	/**
	 *  查询子节点最大code值
	 */
	public String queryMaxCodeOfChild(String parentCode);
	/**
	 * 更新权限基本信息，不更新code
	 */
	public Integer updateRight(CrmRight right);
	/**
	 * 删除自己及其所有子类别
	 */
	public Integer deleteRightByCode(String code);
	/**
	 * 删除会员权限
	 */
	public Integer deleteCrmSvrRightByCode(String code);
	/**
	 * 删除服务权限
	 */
	public Integer deleteMemberRightByCode(String code);
	/**
	 * 查询单个权限节点
	 * @param code
	 * @return
	 */
	public CrmRight queryOneRight(String code);
	/**
	 * 增加权限节点
	 * @param right
	 * @return
	 */
	public Integer insertRight(CrmRight right);
	/**
	 * 通过权限code查询权限ID
	 * @param code
	 * @return
	 */
	public Integer queryIdByCode(String code);
	/**
	 * 通过权限code查询权限名称
	 * @param code
	 * @return
	 */
	public String queryNameByCode(String code);
	/**
	 * 通过权限ID数组查询权限列表
	 * @param crmRightIds
	 * @return
	 */
	public List<CrmRight> queryCrmRightListBycrmRightIdArray(
			List<Integer> crmRightIds);
	/**
	 * 通过公司ID查询公司所拥有服务的权限
	 * @param companyId
	 * @return
	 */
	public List<String> queryCrmRightListBycompanyId(Integer companyId, String parentRight);
	/**
	 * 通过会员code查询会员类型所拥有的权限
	 * @param memberCode
	 * @return
	 */
	public List<String> queryCrmRightListByMemberCode(String memberCode, String parentRight);
}

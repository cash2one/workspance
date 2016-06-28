package com.zz91.ep.admin.dao.crm;

import java.util.List;

import com.zz91.ep.domain.crm.CrmSvr;

/**
 * 
 * @author leon
 * EP系统公司拥有服务操作的DAO，负责对公司服务的操作
 * 2011-09-16
 */
public interface CRMSvrDao {
	/**
	 * 查询服务列表
	 */
	public List<CrmSvr> queryCrmSvrList();
	
	/**
	 * 插入平台服务对象
	 * @param crmSvr
	 * @return
	 */
	public Integer insertCrmSvr(CrmSvr crmSvr);
	
	/**
	 * 通过服务ID删除平台服务
	 * @param crmSvrId
	 * @return
	 */
	public Integer deleteCrmSvr(String code);

	/**
	 * 通过服务ID删除服务所拥有的权限
	 */
	public Integer deleteCrmRightOfCrmSvr(String code);

	/**
	 * 插入中间表crm_svr_right
	 * @param crmSvrId
	 * @param rightId
	 * @return
	 */
	public Integer insertCrmSvrCrmRight(Integer crmSvrId, Integer crmRightId);

	/**
	 * 删除中间表记录crm_svr_right
	 * @param crmSvrId
	 * @param rightId
	 * @return
	 */
	public Integer deleteCrmSvrCrmRight(Integer crmSvrId, Integer rightId);

	/**
	 * 通过平台服务ID查询所有权限ID列表
	 * @param crmSvrId
	 * @return
	 */
	public List<Integer> queryCrmRightIdOfCrmSvr(Integer crmSvrId);

	/**
	 * 更新平台服务信息
	 * @param crmSvr
	 * @return
	 */
	public Integer updateCrmSvr(CrmSvr crmSvr);

	public List<CrmSvr> queryCrmSvrChild(String parentCode);

	public Integer countCrmSvrChild(String code);

	public CrmSvr queryOneCrmSvr(String code);

	public String queryMaxCodeOfChild(String parentCode);

	public Integer queryIdByCode(String svrCode);

	public String querySvrNameById(Integer crmSvrId);

	public String queryCloseApi(Integer crmSvrId);

	public String queryOpenApi(Integer crmSvrId);
}
package com.ast.ast1949.service.company;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.CrmServiceApply;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CrmCompanySvrDto;




public interface CrmCompanySvrService {

	public static final String ZST_CODE="1000";
	public static final String JBZST_CODE="1006";
	public static final String BAIDU_CODE="10001002";
	public static final String LIFE_CODE="10001003";
	public static final String ESITE_CODE="10001004";
	public static final String CX_CODE="10001005";
	public static final String LDB_CODE = "1007"; // 来点宝1元服务
	public static final String LDB_FIVE_CODE = "1008"; // 来点宝5元服务
	
	
	
	/**
	 * 服务开通申请
	 * 需要生成服务申请组（apply_group）
	 * 将公司开通的服务申请保存到crm_company_service，状态（status）都是0，表示开通申请
	 * 将开通单信息保存到crm_service_apply，其中到款金额，到款时间，销售人员必需填写
	 */
	public Boolean applySvr(Integer companyId, String[] svrCodeArr, CrmServiceApply apply, String svrgroup);
	/**
	 * 查找公司已经开通的服务
	 * expired：表示是否过期，true表示已过期，false表示未过期，null表示全部
	 */
	public List<CrmCompanySvrDto> queryCompanySvr(Integer companyId, Boolean expired);
	/**
	 * 查找某个服务的所有申请公司，查找时必需保持申请状态为0
	 *
	 *  
	 */
	public PageDto<CrmCompanySvrDto> pageApplyCompany(String svrCode, String applyStatus,String email,Integer companyId, PageDto<CrmCompanySvrDto> page);
	
	/**
	 * 根据开通服务组查找待开通
	 */
	public List<CrmCompanySvrDto> queryApplyByGroup(String applyGroup);
	/**
	 * 查找某公司某服务的历史开通情况
	 */
	public List<CrmCompanySvr> querySvrHistory(Integer companyId, String svrCode);
	/**
	 * 开通服务，做两件事
	 * 1.将对应组的服务开通状态更新成开通
	 * 2.更新服务开通单
	 */
	public Boolean openSvr(CrmCompanySvr svr, Integer companyId);
	
	public CrmCompanySvr queryCompanySvrById(Integer id);
	
	public Integer updateSvrById(CrmCompanySvr svr);
	
	public Integer countOpenedApplyByGroup(String applyGroup);
	
	public Boolean refusedApply(String applyGroup);
	
	public CrmCompanySvr queryZstSvr(Integer companyId);
	
	public CrmCompanySvr queryRecentHistory(String svrCode, Integer companyId, Integer companySvrId);

	public Boolean openZstSvr(CrmCompanySvr svr, String domainZz91, String membershipCode);
	
	public Boolean openSPSvr(CrmCompanySvr svr, String domainZz91);
	
	public Boolean closeSvr(Integer companySvrId);
	
	public boolean validatePeriod(Integer companyId, String svrCode);
	
	public boolean validateEsitePeriod(Integer companyId);
	
	public Map<String, Map<String, Integer>> monthExpiredCountBySvrCode(Date start, Date end,String code);

	public void createCsProfile(Integer companyId);

	public Boolean updateZstSvr(CrmCompanySvr svr, String domainZz91, String membershipCode);

	public Boolean updateSvr(CrmCompanySvr svr, Integer companyId);

	public Boolean updateSPSvr(CrmCompanySvr svr, String domainZz91);
	
	/**
	 * 搜索最新的开通 服务
	 * @param size
	 * @return
	 */
	public List<CrmCompanySvrDto> queryLatestOpen(Integer size);
	/**
	 * 判断是否为来电宝（一元或者五元）
	 * @param companyId
	 * @param svrLDBCode1
	 * @param svrLDBCode2
	 * @return
	 */
	public boolean validateLDB(Integer companyId, String svrLDBCode1,String svrLDBCode2);
		
	/**
	 * 查找公司未过期的服务
	 * @param companyId
	 * @return
	 */
	public List<CrmCompanySvr> querySvrByCompanyId(Integer companyId);
	/**
	 * 查找公司未过期服务的服务结束时间
	 * @param companyId
	 * @return
	 */
	public String queryGmtendByCompanyId(Integer companyId);
    /**
     * 查找公司是否开通过会员服务zhengrp
     * @param companyId
     * @param svrCode
     * @return
     */
	public Integer validatehistory(Integer companyId, String svrCode);
}

 

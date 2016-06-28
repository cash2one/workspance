package com.ast.ast1949.persist.company;

import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisCsRenewalDto;
import com.ast.ast1949.dto.company.CrmCompanySvrDto;



public interface CrmCompanySvrDao {
 
	public Integer insertCompanySvr(CrmCompanySvr companySvr);
	/**
	 * 查找结果包含服务开通历史和服务名称
	 */
	public List<CrmCompanySvrDto> queryCompanySvr(Integer companyId, Date expiredDate);
	/**
	 * 查找某个服务的所有申请公司
	 * 返回的信息包含公司基本信息
	 *
	 *  
	 */
	public List<CrmCompanySvrDto> queryApplyCompany(String svrCode, String applyStatus,Integer companyId, PageDto<CrmCompanySvrDto> page);
	
	public Integer queryApplyCompanyCount(String svrCode, String applyStatus,Integer companyId);
	
	/**
	 * 根据开通服务组查找待开通的所有客户信息
	 * 返回值包含服务名称
	 */
	public List<CrmCompanySvrDto> queryApplyByGroup(String applyGroup);
	/**
	 * 查找某公司某服务的历史开通情况
	 */
	public List<CrmCompanySvr> querySvrHistory(Integer companyId, String svrCode);
	/**
	 *  
	 */
	public Integer updateSvrStatusByGroup(String applyGroup, String status);
	
	/**
	 * 更新基本的服务申请信息
	 * 允许更新的字段:gmt_pre_start,gmt_pre_end,gmt_signed,gmt_start,gmt_end,category,remark
	 * @param svr
	 * @return
	 */
	public Integer updateBaseSvr(CrmCompanySvr svr);
	
	public CrmCompanySvr queryCompanySvrById(Integer id);
	
	public Integer countSvrYears(Integer companyId, String svrCode);
	
	public Integer countApplyByGroup(String applyGroup, String applyStatus);
	
	public List<CrmCompanySvr> queryRecentSvr(Integer companyId, String svrCode, Date expiredDate);
	
	public CrmCompanySvr queryRecentHistory(String svrCode, Integer companyId,
			Integer companySvrId);
	
	public Integer updateSvrStatusById(Integer svrId, String status);
	
	public Integer sumYear(Integer companyId, String svrCode);
	
	public Integer period(Integer companyId, String svrCode);
	
	@Deprecated
	public List<AnalysisCsRenewalDto> monthExpiredCount(Date start, Date end);
	
	/**
	 * 根据服务code 统计过期服务的客户数量
	 * @param start
	 * @param end
	 * @param code
	 * @return
	 */
	public List<AnalysisCsRenewalDto> monthExpiredCountBySvrCode(Date start, Date end,String code);
	
	/**
	 * 检索最新再生通服务开通 company_id
	 * @param size
	 * @return
	 */
	public List<Integer> queryLatestOpen(Integer size);

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
	public List<Date> queryGmtendByCompanyId(Integer companyId);
    /**
     * 查找公司是否开通过会员服务zhengrp
     * @param companyId
     * @param svrCode
     * @return
     */
	public Integer history(Integer companyId, String svrCode);
}
 

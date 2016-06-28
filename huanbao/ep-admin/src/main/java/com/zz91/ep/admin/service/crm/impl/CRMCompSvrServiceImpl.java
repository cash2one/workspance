package com.zz91.ep.admin.service.crm.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.record.formula.functions.False;
import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.comp.CompProfileDao;
import com.zz91.ep.admin.dao.crm.CRMCompSvrDao;
import com.zz91.ep.admin.dao.crm.CRMSvrDao;
import com.zz91.ep.admin.service.crm.CRMCompSvrService;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.crm.CrmCompSvr;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.crm.CrmCompSvrDto;
import com.zz91.util.Assert;
import com.zz91.util.lang.StringUtils;
@Component("crmCompSvrService")
public class CRMCompSvrServiceImpl implements CRMCompSvrService {

	@Resource private CRMCompSvrDao crmCompSvrDao;
	@Resource private CRMSvrDao crmSvrDao;
	@Resource private CompProfileDao compProfileDao;
	private final static Short APPLY_STATUS_SUCCESS = 1;
	private final static Short APPLY_STATUS_FAILURE = 2;
	private final static Short APPLY_STATUS_CLOSE = 3;
	private final static String MEMBER_CODE_VIP = "10011001";

	@Override
	public PageDto<CrmCompSvrDto> pageApplyCompany(String svrCode,
			String applyStatus, PageDto<CrmCompSvrDto> page,String account) {
		Integer crmSvrId = crmSvrDao.queryIdByCode(svrCode);
		page.setRecords(crmCompSvrDao.queryApplyCompany(crmSvrId, applyStatus, page,account));
		page.setTotals(crmCompSvrDao.queryApplyCompanyCount(crmSvrId, applyStatus,account));
		return page;
	}

	@Override
	public List<CrmCompSvrDto> queryApplySvrHistory(Integer companyId) {
		Assert.notNull(companyId, "the companyId can not be null");
		List<CrmCompSvr> list = crmCompSvrDao.queryApplySvrHistory(companyId);
		List<CrmCompSvrDto> CrmCompSvrDtoList = new ArrayList<CrmCompSvrDto>();
		String svrName="";
		for (CrmCompSvr crmCompSvr : list) {
			CrmCompSvrDto crmCompSvrDto = new CrmCompSvrDto();
			svrName = crmSvrDao.querySvrNameById(crmCompSvr.getCrmSvrId());
			crmCompSvrDto.setCrmCompSvr(crmCompSvr);
			crmCompSvrDto.setSvrName(svrName);
			CrmCompSvrDtoList.add(crmCompSvrDto);
		}
		return CrmCompSvrDtoList;
	}

	@Override
	public Integer countOpenedApplyByGroup(String applyGroup) {
		return crmCompSvrDao.countOpenedApplyByGroup(applyGroup);
	}

	@Override
	public List<CrmCompSvrDto> queryApplyByGroup(String applyGroup) {
		Assert.notNull(applyGroup, "the applyGroup can not be null");
		return crmCompSvrDao.queryApplyByGroup(applyGroup);
	}

	@Override
	public List<CrmCompSvr> querySvrHistory(Integer companyId, Integer crmSvrId) {
		Assert.notNull(companyId, "the companyId can not be null");
		Assert.notNull(crmSvrId, "the crmSvrId can not be null");
		return crmCompSvrDao.querySvrHistory(companyId, crmSvrId);
	}

	@Override
	public Integer openCrmCompSvr(CrmCompSvr crmCompSvr) {
		Assert.notNull(crmCompSvr, "the crmCompSvr can not be null");
		return crmCompSvrDao.updateCrmCompSvr(crmCompSvr);
	}

	@Override
	public Boolean closeSvr(Integer companySvrId) {
		Integer integer=crmCompSvrDao.updateSvrStatusById(companySvrId, APPLY_STATUS_CLOSE);
		if(integer!=null && integer.intValue()>0){
			return true;
		}
		return false;
	}


	/**
	 * 开通普通服务,如短信服务
	 * 逻辑:
	 * 1.查询历史开通记录,判断是新开通还是续开通
	 * 2.更新服务开通状态
	 */
	@Override
	public Boolean openSvr(CrmCompSvr crmCompSvr, Integer cid) {
		Boolean isNewSigned = this.isNewSigned(crmCompSvr);
		if(isNewSigned)
			crmCompSvr.setSignedStatus((short)0);
		else
			crmCompSvr.setSignedStatus((short)1);
		crmCompSvr.setApplyStatus(APPLY_STATUS_SUCCESS);
		if(crmCompSvr.getSaleCategory().intValue()==0) {
			crmCompSvr.setMemberCode(MEMBER_CODE_VIP);
		} else {
			crmCompSvr.setMemberCode("");
		}
		Integer status = this.openCrmCompSvr(crmCompSvr);
		if(status>0) {
			return true;
		}
		return false;
	}

	@Override
	public CrmCompSvr queryCompSvrById(Integer id) {
		Assert.notNull(id, "id不能为空");
		return crmCompSvrDao.queryCompanySvrById(id);
	}

	@Override
	public CrmCompSvr queryRecentHistory(Integer crmSvrId, Integer cid,
			Integer id) {
		Assert.notNull(crmSvrId, "crmSvrId不能为空");
		Assert.notNull(cid, "cid不能为空");
		Assert.notNull(id, "id不能为空");
		return crmCompSvrDao.queryRecentHistory(crmSvrId, cid, id);
	}

	@Override
	public CrmCompSvr queryRecentHistory(String svrCode, Integer companyId,
			Integer companySvrId) {
		Integer crmSvrId = null;
		if(StringUtils.isNotEmpty(svrCode)) {
			crmSvrId = crmSvrDao.queryIdByCode(svrCode);
		}
		return this.queryRecentHistory(crmSvrId, companyId, companySvrId);
	}

	@Override
	public CrmCompSvr queryCompanySvrById(Integer companySvrId) {
		Assert.notNull(companySvrId, "companySvrId不能为空");
		return crmCompSvrDao.queryCompanySvrById(companySvrId);
	}

	/**
	 * 开通中环通服务
	 * 逻辑:
	 * 1.查询历史开通记录,判断是新开通还是续开通
	 * 2.更新服务开通状态
	 * 3.更新公司表中会员类型以及环保网中的二级域名
	 */
	@Override
	public Boolean openZhtSvr(CrmCompSvr crmCompSvr, String domain,
			String memberCode) {
		Boolean isNewSigned = this.isNewSigned(crmCompSvr);
		if(isNewSigned)
			crmCompSvr.setSignedStatus((short)0);
		else
			crmCompSvr.setSignedStatus((short)1);
		crmCompSvr.setApplyStatus(APPLY_STATUS_SUCCESS);
		Integer status = this.openCrmCompSvr(crmCompSvr);
		if(status>0) {
			CompProfile compProfile = new CompProfile();
			compProfile.setId(crmCompSvr.getCid());
			compProfile.setMemberCode(memberCode);
			compProfile.setDomainTwo(domain);
			Integer count = compProfileDao.openZhtWithUpdateMemberCodeAndDomainTwo(compProfile);
			if(count>0)
				return true;
		}
		return false;
	}

	public Boolean isNewSigned(CrmCompSvr crmCompSvr) {
		//TODO 完成验证是否是新签约功能
		
		return true;
	}

	@Override
	public Boolean refusedApply(String applyGroup) {
		if(crmCompSvrDao.refusedApplyUpdateApplyStatus(applyGroup, APPLY_STATUS_FAILURE)>0)
			return true;
		return false;
	}

	@Override
	public Boolean closeSeoSvr(Integer cid, Integer companySvrId) {
		Boolean flag=false;
		Boolean r=this.closeSvr(companySvrId);
		if(r){
			Integer count=compProfileDao.closeSeoSvr(cid);
			if (count!=null && count.intValue()>0){
				flag=true;
			}
		}
		return flag;
	}
	
}

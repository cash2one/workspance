package com.zz91.ep.admin.service.crm.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zz91.ep.admin.dao.comp.CompAccountDao;
import com.zz91.ep.admin.dao.crm.CRMCompSvrDao;
import com.zz91.ep.admin.dao.crm.CRMSvrApplyDao;
import com.zz91.ep.admin.dao.crm.CRMSvrDao;
import com.zz91.ep.admin.service.crm.CRMSvrApplyService;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.crm.CrmCompSvr;
import com.zz91.ep.domain.crm.CrmCsComp;
import com.zz91.ep.domain.crm.CrmSvrApply;
import com.zz91.util.Assert;
import com.zz91.util.auth.AuthUtils;

@Component("crmSvrApplyService")
public class CRMSvrApplyServiceImpl implements CRMSvrApplyService {

	@Resource
	private CRMSvrApplyDao crmSvrApplyDao;
	@Resource
	private CompAccountDao compAccountDao;
	@Resource
	private CRMSvrDao crmSvrDao;
	@Resource
	private CRMCompSvrDao crmCompSvrDao;
	
	/**
	 * 业务逻辑:
	 * 1.首先将提交申请单的信息插入到服务申请表里
	 * 2.然后在公司服务表里插入服务信息,此时公司服务属于待开通状态
	 */
	@Transactional
	@Override
	public Boolean applySvr(Integer companyId, String[] svrCodes,
			CrmSvrApply apply, CrmCompSvr crmCompSvr) {
		Assert.notNull(apply.getGmtIncome(),
				"the apply.gmtIncome can not be null");
		Assert.notNull(apply.getSaleStaff(),
				"the apply.saleStaff can not be null");
		Assert.notNull(apply.getAmount(), "the apply.amount can not be null");
		String applyGroup = UUID.randomUUID().toString();
		apply.setApplyGroup(applyGroup);
		apply.setTargetCid(companyId);
		CompAccount compAccount = compAccountDao.queryCompAccountByCid(companyId);
		apply.setTargetEmail(compAccount.getEmail());
		apply.setTargetAccount(compAccount.getAccount());
		Integer i = crmSvrApplyDao.insertApply(apply);
		if(i>0) {
			
			for (String svrCode : svrCodes) {
				crmCompSvr.setApplyGroup(applyGroup);
				crmCompSvr.setCid(companyId);
				crmCompSvr.setSignedStatus((short)0);
				crmCompSvr.setApplyStatus((short)0);
				crmCompSvr.setSaleCategory((short)0);
				crmCompSvr.setMemberCode("");
				crmCompSvr.setCrmSvrId(crmSvrDao.queryIdByCode(svrCode));
				crmCompSvr.setRemark(apply.getRemark());
				crmCompSvrDao.insertCompSvr(crmCompSvr);
			}
		}
		return true;
	}

	@Override
	public CrmSvrApply queryApplyByGroup(String applyGroup) {
		Assert.notNull(applyGroup, "the applyGroup can not be null");
		return crmSvrApplyDao.queryApplyByGroup(applyGroup);
	}

	@Override
	public Integer assignApplyToCs(Integer id, String account) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(account, "the account can not be null");
		Integer i=crmSvrApplyDao.queryCompWithCs(id);
		if (i!=null && i.intValue()>0 ){
			crmSvrApplyDao.updateDelStatus(i);
		}
		CrmCsComp crmCsComp = new CrmCsComp();
		crmCsComp.setCid(id);
		crmCsComp.setDelStatus(0);
		crmCsComp.setSaleStaff(account);
		crmCsComp.setSaleStaffName(AuthUtils.getInstance().queryStaffNameOfAccount(account));
		return crmSvrApplyDao.assignApplyToCs(crmCsComp);
	}

}

package com.ast.ast1949.service.trust.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.domain.trust.TrustBuyLog;
import com.ast.ast1949.domain.trust.TrustCrm;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.trust.TrustBuyDao;
import com.ast.ast1949.persist.trust.TrustBuyLogDao;
import com.ast.ast1949.persist.trust.TrustCrmDao;
import com.ast.ast1949.service.trust.TrustBuyLogService;
import com.ast.ast1949.service.trust.TrustCrmService;
import com.zz91.util.lang.StringUtils;

@Component("trustBuyLogService")
public class TrustBuyLogServiceImpl implements TrustBuyLogService{

	@Resource
	private TrustBuyLogDao trustBuyLogDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private TrustCrmDao trustCrmDao;
	@Resource
	private TrustBuyDao trustBuyDao;
	
	@Override
	public Integer createLog(TrustBuyLog trustBuyLog) {
		if (StringUtils.isEmpty(trustBuyLog.getContent())) {
			return 0;
		}
		if (trustBuyLog.getBuyId()==null) {
			return 0;
		}
		Integer i = trustBuyLogDao.insert(trustBuyLog);
		if (i<=0) {
			return 0;
		}
		TrustBuy obj = trustBuyDao.queryById(trustBuyLog.getBuyId());
		if (obj!=null&&obj.getCompanyId()!=null&&obj.getCompanyId()!=0&&trustBuyLog.getGmtNextVisit()!=null) {
			TrustCrm tc = trustCrmDao.queryByCompanyId(obj.getCompanyId());
			if (tc!=null) {
				i = trustCrmDao.updateContact(obj.getCompanyId(), trustBuyLog.getGmtNextVisit()); //更新最后联系时间
				i = trustCrmDao.updateStar(obj.getCompanyId(), trustBuyLog.getStar()); //更新星级
			}else{
				if (obj.getCompanyId() ==null||obj.getCompanyId()==0) {
					return 0;
				}
				TrustCrm validateObj =  trustCrmDao.queryByCompanyId(obj.getCompanyId());
				if (validateObj!=null) {
					return 0;
				}
				TrustCrm trustCrm = new TrustCrm();
				trustCrm.setCompanyId(obj.getCompanyId());
				String crmAccount = trustBuyLog.getTrustAccount();
				crmAccount = crmAccount.substring(crmAccount.indexOf("(")+1, crmAccount.indexOf(")"));
				trustCrm.setCrmAccount(crmAccount);
				trustCrm.setGmtNextVisit(trustBuyLog.getGmtNextVisit());
				trustCrm.setGmtLastVisit(new Date());
				trustCrm.setStar(trustBuyLog.getStar());
				trustCrm.setIsPublic(TrustCrmService.NO_PUBLIC);
				trustCrm.setIsRubbish(TrustCrmService.NO_RUBBISH);
				i = trustCrmDao.insert(trustCrm);
			}
		}
		return i;
	}

	@Override
	public PageDto<TrustBuyLog> pageLog(TrustBuyLog trustBuyLog,PageDto<TrustBuyLog> page) {
		if (trustBuyLog.getBuyId()!=null&&trustBuyLog.getBuyId()==0) {
			trustBuyLog.setBuyId(null);
		}
		page.setTotalRecords(trustBuyLogDao.queryCountByCondition(trustBuyLog));
		List<TrustBuyLog> list = trustBuyLogDao.queryByCondition(trustBuyLog, page);
		for (TrustBuyLog obj : list) {
			if (obj.getOfferCompanyId()!=null) {
				obj.setOfferCompanyName(companyDAO.queryCompanyById(obj.getOfferCompanyId()).getName());
			}
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public Integer countLog(Integer companyId) {
		if (companyId==null||companyId==0) {
			return 0;
		}
		TrustBuyLog trustBuyLog = new TrustBuyLog();
		trustBuyLog.setCompanyId(companyId);
		return trustBuyLogDao.queryCountByCondition(trustBuyLog);
	}
}

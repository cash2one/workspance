package com.ast.ast1949.service.trust.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustCompanyLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.trust.TrustCompanyLogDao;
import com.ast.ast1949.persist.trust.TrustCrmDao;
import com.ast.ast1949.service.trust.TrustCompanyLogService;
import com.zz91.util.lang.StringUtils;

@Component("trustCompanyLogService")
public class TrustCompanyLogServiceImpl implements TrustCompanyLogService {

	@Resource
	private TrustCompanyLogDao trustCompanyLogDao;
	@Resource
	private TrustCrmDao trustCrmDao;
	@Resource
	private CompanyDAO companyDAO; 
	
	@Override
	public Integer createLog(TrustCompanyLog trustCompanyLog) {
		if (StringUtils.isEmpty(trustCompanyLog.getContent())) {
			return 0;
		}
		if (trustCompanyLog.getCompanyId()==null) {
			return 0;
		}
		Integer i = trustCompanyLogDao.insert(trustCompanyLog);
		if (i<=0) {
			return 0;
		}
		i = trustCrmDao.updateContact(trustCompanyLog.getCompanyId(), trustCompanyLog.getGmtNextVisit()); //更新最后时间
		i = trustCrmDao.updateStar(trustCompanyLog.getCompanyId(), trustCompanyLog.getStar()); // 更新星级
		return i;
	}

	@Override
	public PageDto<TrustCompanyLog> pageLog(TrustCompanyLog trustCompanyLog, PageDto<TrustCompanyLog> page) {
		if (trustCompanyLog.getCompanyId()!=null&&trustCompanyLog.getCompanyId()==0) {
			trustCompanyLog.setCompanyId(null);
		}
		page.setTotalRecords(trustCompanyLogDao.queryCountByCondition(trustCompanyLog));
		List<TrustCompanyLog> list = trustCompanyLogDao.queryByCondition(trustCompanyLog, page);
		for (TrustCompanyLog obj : list) {
			if (obj.getCompanyId()!=null) {
				obj.setCompanyName(companyDAO.queryCompanyById(obj.getCompanyId()).getName());
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
		TrustCompanyLog trustCompanyLog =new TrustCompanyLog();
		trustCompanyLog.setCompanyId(companyId);
		return trustCompanyLogDao.queryCountByCondition(trustCompanyLog);
	}

}

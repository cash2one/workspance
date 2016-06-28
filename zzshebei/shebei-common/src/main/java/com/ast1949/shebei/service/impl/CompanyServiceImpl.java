package com.ast1949.shebei.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.ast1949.shebei.dao.CompanyDao;
import com.ast1949.shebei.domain.Company;
import com.ast1949.shebei.dto.PageDto;
import com.ast1949.shebei.service.CompanyService;

@Component("companyService")
public class CompanyServiceImpl implements CompanyService {
	
	@Resource
	private CompanyDao companyDao;
	@Override
	public Integer createCompany(Company company) {
		
		return null;
	}

	@Override
	public Company queryCompanyById(Integer companyId) {
		
		return companyDao.queryCompanyById(companyId);
	}

	@Override
	public PageDto<Company> pageCompanys(PageDto<Company> page ,String categoryCode) {
		List<Company> records = companyDao.queryCompanys(page,categoryCode);
		page.setRecords(records);
		page.setTotals(companyDao.queryCompanyCount(categoryCode));
		return page;
	}

	@Override
	public String queryDeatilsById(Integer companyId) {
		return companyDao.queryDeatilsById(companyId);
	}

	@Override
	public Company queryContactById(Integer companyId) {
		return companyDao.queryContactById(companyId);
	}

	@Override
	public List<Company> queryNewestCompany(Integer size, String categoryCode) {
		return companyDao.queryNewestCompany(size, categoryCode);
	}

	@Override
	public String queryNameById(Integer companyId) {
		return companyDao.queryNameById(companyId);
	}

	@Override
	public Date queryMaxGmtShow() {
		return companyDao.queryMaxGmtShow();
	}

	@Override
	public Integer queryCompIdByAccount(String account) {
		return companyDao.queryCompIdByAccount(account);
	}

}

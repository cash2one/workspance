package com.kl91.service.company.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.kl91.domain.company.Company;
import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.company.CompanySearchDto;
import com.kl91.persist.company.CompanyDao;
import com.kl91.service.company.CompanyService;

@Component("companyService")
public class CompanyServiceImpl implements CompanyService {

	@Resource
	private CompanyDao companyDao;

	@Override
	public Integer createCompany(Company company) {
		return companyDao.insert(company);
	}

	@Override
	public Integer editCompany(Company company) {
		return companyDao.update(company);
	}

	@Override
	public List<Company> queryMostPublish(Integer size) {

		return companyDao.queryMostCompanyByNumPass(size);
	}

	@Override
	public Company queryById(Integer id) {
		return companyDao.queryById(id);
	}

	@Override
	public String queryDomainById(Integer id) {
		return companyDao.queryDomainById(id);
	}

	@Override
	public String queryIntroductionById(Integer id) {
		return companyDao.queryIntroductionById(id);
	}

	public Integer updateNumPassById(Integer companyId, Integer count) {

		return companyDao.updatePassNumById(companyId, count);
	}

	@Override
	public PageDto<Company> queryCompanyFromSolr(PageDto<Company> page,
			CompanySearchDto searchDto) {

		return null;
	}

	@Override
	public Integer validateAccount(HttpServletRequest request, String account,
			String pwd) {
		do {
			// 验证帐号
			Company company = companyDao.validateAccount(account, pwd);
			if (company != null) {
				return company.getId();
			}
			
			// 邮箱验证
			company = companyDao.validateEmail(account, pwd);
			if (company != null) {
				return company.getId();
			}

		} while (false);
		return null;
	}

	public Integer countUserByAccount(String account) {
		return companyDao.countUserByAccount(account);
	}

	@Override
	public Company queryByEmail(String email) {
		return companyDao.queryByEmail(email);
	}

	@Override
	public Integer updatePwdById(String pwd, Integer id) {
		return companyDao.updatePwdById(pwd, id);
	}

	@Override
	public Company queryByMobile(String mobile) {
		return companyDao.queryByMobile(mobile);
	}

	@Override
	public Integer updateCompanyByMyrc(String contact, String mobile,
			Integer sex, String email, Integer id) {
		return companyDao.updateCompanyByMyrc(contact, mobile, sex, email, id);
	}

	@Override
	public Company queryByAccount(String account) {
		return companyDao.queryByAccount(account);
	}
}

package com.kl91.persist.company;

import java.util.List;

import com.kl91.domain.company.Company;
import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.company.CompanyDto;

public interface CompanyDao {
	public Integer insert(Company company);

	public Integer update(Company company);

	public Company queryById(Integer id);

	public String queryDomainById(Integer id);

	public String queryIntroductionById(Integer id);

	public List<Company> queryCompany(CompanyDto dto, PageDto<Company> page);

	public Integer updatePassNumById(Integer companyId, Integer count);
	
	public List<Company> queryMostCompanyByNumPass(Integer size);
	
	public Company validateAccount(String account, String pwd);
	
	public Company validateEmail(String email, String pwd);
	
	public Company queryByEmail(String email);
	
	public Integer updatePwdById(String pwd,Integer id);

	public Integer countUserByAccount(String account);
	
	public Company queryByMobile(String mobile);
	
	public Company queryByAccount(String account);
	
	public Integer updateCompanyByMyrc(String contact,String mobile,Integer sex,String email,Integer id);
}

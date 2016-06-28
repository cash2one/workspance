package com.ast.ast1949.persist.phone;

import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneCostSvr;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhoneDto;

/**
 * author:kongsj date:2013-7-3
 */
public interface PhoneDao {
	public Phone queryById(Integer id);

	public Integer insert(Phone phone);

	public Integer update(Phone phone);

	public List<Phone> queryList(Phone phone, PageDto<Phone> page);

	public Integer queryListCount(Phone phone);
	
	public Integer updateAmountAndBalance(Integer id,String amount,String balance);
	
	public Integer countByAccount(String account);
	
	public Integer countByCompanyId(Integer companyId);
	
	@Deprecated
	public Integer deleteById(Integer id);
	
	public Phone queryByCompanyId(Integer companyId);
	
	public Phone queryByTel(String tel);
	
	public List<PhoneDto> queryAllList(Phone phone,CompanyAccount companyAccount,PhoneCostSvr phoneCostSvr, PageDto<PhoneDto> page,Company company,float laveFrom,float laveTo,String csAccount,Date svrFroms,Date svrTos);
	
	public List<PhoneDto> queryAllBsList(Phone phone,CompanyAccount companyAccount,PhoneCostSvr phoneCostSvr, PageDto<PhoneDto> page,Company company,float laveFrom,float laveTo,String csAccount,String from,String to);
	
	public List<PhoneDto> queryAllListl(Phone phone,CompanyAccount companyAccount,PhoneCostSvr phoneCostSvr, PageDto<PhoneDto> page,Company company,float laveFrom,float laveTo,String csAccount);

	public String queryAllPhoneAmount();
	
	public Integer queryListCountByAdmin(Phone phone,CompanyAccount companyAccount,PhoneCostSvr phoneCostSvr,Company company);

	public Integer queryAllListCount(Phone phone,CompanyAccount companyAccount,PhoneCostSvr phoneCostSvr,Company company,float laveFrom,float laveTo,String csAccount);
	
	public Integer queryAllBsListCount(Phone phone,CompanyAccount companyAccount,PhoneCostSvr phoneCostSvr,Company company,float laveFrom,float laveTo,String csAccount,String from,String to);
	
	public Integer updateSmsFee(String smsFee, Integer companyId);
	
	public Integer updateAmountByCompanyId(String amount,Integer companyId );
	
	public Integer queryCompanyIdByTel(String tel);
	
	public String querytelByCompanyId(Integer companyId);

	public Integer updateClose(String frontTel, Integer companyId);
	
	public List<PhoneDto> pagePhoneCallFee(PageDto<PhoneDto> page,String from,String to);
	
}

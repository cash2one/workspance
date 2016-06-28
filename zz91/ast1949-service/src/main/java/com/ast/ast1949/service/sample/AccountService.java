package com.ast.ast1949.service.sample;

import java.math.BigDecimal;
import java.util.Map;

import com.ast.ast1949.domain.sample.Account;
import com.ast.ast1949.dto.PageDto;

public interface AccountService {
	Integer  insert(Account account);

	int updateByPrimaryKey(Account record);

	int updateByPrimaryKeySelective(Account record);

	Account selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	Account selectByCompanyId(Integer companyId);
	
	/**
	 *  公司的账户信息
	 * @param companyId
	 * @param state
	 * @return
	 */
	PageDto<Account> queryListByFilter(PageDto<Account> page, Map<String, Object> filterMap);

	
	/**
	 *  人工调账
	 * @param account
	 * @param preamount
	 */
	void updateAccount(Account account, BigDecimal preamount);
	
}

package com.ast.ast1949.service.sample.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.Account;
import com.ast.ast1949.domain.sample.Accountseq;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.sample.AccountDAO;
import com.ast.ast1949.persist.sample.AccountseqDAO;
import com.ast.ast1949.service.sample.AccountseqService;

@Component("accountseqService")
public class AccountseqServiceImpl implements AccountseqService {

	@Resource
	private AccountseqDAO accountseqDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private AccountDAO accountDAO;

	@Override
	public Integer insert(Accountseq record) {
		do {
			if (record.getAccountId() == null) {
				break;
			}
			Account account = accountDAO.selectByPrimaryKey(record.getAccountId());
			if (account == null) {
				break;
			}
			record.setCompanyId(account.getCompanyId());
			return accountseqDao.insert(record);
		} while (false);
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Accountseq record) {
		return accountseqDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Accountseq record) {
		return accountseqDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public Accountseq selectByPrimaryKey(Integer id) {
		return accountseqDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return accountseqDao.deleteByPrimaryKey(id);
	}

	@Override
	public PageDto<Accountseq> queryListByFilter(PageDto<Accountseq> page,
			Map<String, Object> filterMap) {
		if (page.getSort() == null) {
			page.setSort("id");
		}

		filterMap.put("page", page);
		page.setTotalRecords(accountseqDao.queryListByFilterCount(filterMap));
		List<Accountseq> list = accountseqDao.queryListByFilter(filterMap);
		for (Accountseq obj : list) {
			Account account = accountDAO.selectByPrimaryKey(obj.getAccountId());
			if (account == null) {
				continue;
			}
			String name = companyDAO.queryCompanyNameById(account
					.getCompanyId());
			obj.setCompanyName(name);
			obj.setCompanyId(account.getCompanyId());
		}
		page.setRecords(list);
		return page;
	}
}

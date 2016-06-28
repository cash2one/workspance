package com.ast.ast1949.service.sample.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.sample.Account;
import com.ast.ast1949.domain.sample.Accountseq;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.paychannel.TransType;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.sample.AccountDAO;
import com.ast.ast1949.persist.sample.AccountseqDAO;
import com.ast.ast1949.service.sample.AccountService;
import com.zz91.util.lang.StringUtils;

@Component("accountService")
public class AccountServiceImpl implements AccountService {

	@Resource
	private AccountDAO accountDao;
	
	@Resource
	private AccountseqDAO accountseqDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private CompanyAccountDao companyAccountDao;

	@Override
	public Integer insert(Account account) {
		return accountDao.insert(account);
	}

	@Override
	public int updateByPrimaryKey(Account record) {
		return accountDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Account record) {
		return accountDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public Account selectByPrimaryKey(Integer id) {
		return accountDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return accountDao.deleteByPrimaryKey(id);
	}

	@Override
	public Account selectByCompanyId(Integer companyId) {
		 return accountDao.selectByCompanyId(companyId);
	}

	@Override
	public PageDto<Account> queryListByFilter(PageDto<Account> page, Map<String, Object> filterMap) {
		if(page.getSort()==null){
			page.setSort("id");
		}
		
		filterMap.put("page", page);
		page.setTotalRecords(accountDao.queryListByFilterCount(filterMap));
		List<Account> list = accountDao.queryListByFilter(filterMap);
		for (Account obj : list) {
			String name = companyDAO.queryCompanyNameById(obj.getCompanyId());
			if (StringUtils.isNotEmpty(name)) {
				obj.setCompanyName(name);
			}
			CompanyAccount account = companyAccountDao.queryAccountByCompanyId(obj.getCompanyId());
			if (account!=null) {
				obj.setCompanyAccount(account.getAccount());
			}
		}
		page.setRecords(list);
		return page;
	}

	
	@Override
	public void updateAccount(Account account, BigDecimal preamount) {
		
		/**
		 * 是否存在调账
		 */
		if(account.getAmount().compareTo(preamount) !=0 ){
			Accountseq seq = new Accountseq(); 
			seq.setAccountId(account.getId());
			seq.setChangeType(TransType.ADJUST_ACC);
			seq.setPreamount(preamount);
			seq.setAmount(account.getAmount());
			
			if(account.getAmount().compareTo(preamount) ==1 ){
				seq.setNote("人工调账--增");
				seq.setSeqflag("0");
				seq.setChangeAmount(account.getAmount().subtract(preamount));
			}else{
				seq.setNote("人工调账--减");
				seq.setSeqflag("1");
				seq.setChangeAmount(preamount.subtract(account.getAmount()));
			}
			seq.setCreateTime(new Date());
			accountseqDao.insert(seq);
		}
		
		account.setLastupdateTime(new Date());
		accountDao.updateByPrimaryKeySelective(account);
	}
}

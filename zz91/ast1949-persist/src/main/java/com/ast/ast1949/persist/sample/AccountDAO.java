package com.ast.ast1949.persist.sample;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.sample.Account;

public interface AccountDAO {
	Integer insert(Account record) ;

	int updateByPrimaryKey(Account record) ;

	int updateByPrimaryKeySelective(Account record) ;

	Account selectByPrimaryKey(Integer id) ;
	
	Account selectByCompanyId(Integer id) ;

	int deleteByPrimaryKey(Integer id) ;
	
	List<Account> queryListByFilter(Map<String, Object> filterMap);

	Integer queryListByFilterCount(Map<String, Object> filterMap);

}
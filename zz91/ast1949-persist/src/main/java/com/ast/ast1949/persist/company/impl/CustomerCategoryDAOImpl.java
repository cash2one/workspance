/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-31
 */
package com.ast.ast1949.persist.company.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CustomerCategoryDO;
import com.ast.ast1949.persist.company.CustomerCategoryDAO;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("customerCategoryDAO")
public class CustomerCategoryDAOImpl extends SqlMapClientDaoSupport implements CustomerCategoryDAO {

//	public Integer countCustomerCategoryByName(String name) {
//		return (Integer) getSqlMapClientTemplate().queryForObject("customerCategory.countCustomerCategoryByName", name);
//	}

	public Integer deleteCustomerCategory(Integer id) {
		return getSqlMapClientTemplate().delete("customerCategory.deleteCustomerCategory",id);
	}

	public Integer insertCustomerCategory(CustomerCategoryDO customerCategory) {
		return (Integer) getSqlMapClientTemplate().insert("customerCategory.insertCustomerCategory", customerCategory);
	}

	public CustomerCategoryDO queryCustomerCategoryById(Integer id) {
		return (CustomerCategoryDO) getSqlMapClientTemplate().queryForObject("customerCategory.queryCustomerCategoryById", id);
	}

	@SuppressWarnings("unchecked")
	public List<CustomerCategoryDO> queryCustomerCategoryByParentId(Integer id) {
		return getSqlMapClientTemplate().queryForList("customerCategory.queryCustomerCategoryByParentId", id);
	}

	public Integer updateCustomerCategory(CustomerCategoryDO customerCategory) {
		return getSqlMapClientTemplate().update("customerCategory.updateCustomerCategory", customerCategory);
	}
	
}

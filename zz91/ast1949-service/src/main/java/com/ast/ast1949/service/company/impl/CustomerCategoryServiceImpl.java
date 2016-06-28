/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-31
 */
package com.ast.ast1949.service.company.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CustomerCategoryDO;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.persist.company.CustomerCategoryDAO;
import com.ast.ast1949.service.company.CustomerCategoryService;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("customerCategoryService")
public class CustomerCategoryServiceImpl implements CustomerCategoryService {

	@Autowired
	private CustomerCategoryDAO customerCategoryDAO;
	
//	public Integer countCustomerCategoryByName(String name) {
//		Assert.notNull(name,"the name must not be null");
//		
//		return customerCategoryDAO.countCustomerCategoryByName(name);
//	}

	public Integer deleteCustomerCategory(Integer id) {
		Assert.notNull(id,"the id must not be null");
		
		return customerCategoryDAO.deleteCustomerCategory(id);
	}

	public Integer insertCustomerCategory(CustomerCategoryDO customerCategory) {
		Assert.notNull(customerCategory,"the object of customerCategory must not be null");
		if(customerCategory.getParentId()==null||customerCategory.getParentId()<=0){
			customerCategory.setParentId(0);
		}
		return customerCategoryDAO.insertCustomerCategory(customerCategory);
	}

	public CustomerCategoryDO queryCustomerCategoryById(Integer id) {
		Assert.notNull(id,"the id must not be null");
		
		return customerCategoryDAO.queryCustomerCategoryById(id);
	}

	public List<CustomerCategoryDO> queryCustomerCategoryByParentId(Integer id) {
		Assert.notNull(id,"the id must not be null");
		
		return customerCategoryDAO.queryCustomerCategoryByParentId(id);
	}

	public List<ExtTreeDto> queryExtTreeChildNodeByParentId(Integer id) throws IllegalArgumentException {
		Assert.notNull(id, "The parent id must not be null.");
		List<CustomerCategoryDO> customerCategory = queryCustomerCategoryByParentId(id);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
		for(CustomerCategoryDO n:customerCategory){
			ExtTreeDto node = new ExtTreeDto();
			node.setId("node-"+String.valueOf(n.getId()));
			node.setLeaf(false);
			
			node.setText(n.getName());
			node.setData(n.getId().toString());
			treeList.add(node);
		}
		
		return treeList;
	}

	public Integer updateCustomerCategory(CustomerCategoryDO customerCategory) {
		Assert.notNull(customerCategory,"the object of customerCategory must not be null");
		Assert.notNull(customerCategory.getId(),"the id must not be null");
		
		if(customerCategory.getParentId()==null||customerCategory.getParentId()<=0){
			customerCategory.setParentId(0);
		}
		
		return customerCategoryDAO.updateCustomerCategory(customerCategory);
	}

}

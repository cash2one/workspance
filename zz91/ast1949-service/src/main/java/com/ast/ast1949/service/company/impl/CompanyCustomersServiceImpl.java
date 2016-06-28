/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-29
 */
package com.ast.ast1949.service.company.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyCustomersDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyCustomersDTO;
import com.ast.ast1949.persist.company.CompanyCustomersDao;
import com.ast.ast1949.service.company.CompanyCustomersService;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
@Component("companyCustomersService")
public class CompanyCustomersServiceImpl implements CompanyCustomersService {

	@Autowired
	private CompanyCustomersDao companyCustomersDao;

//	public List<CompanyCustomersDTO> queryCompanyCustomersForFront(
//			CompanyCustomersDTO companyCustomersDTO) {
//		Assert.notNull(companyCustomersDTO, "companyCustomersDTO is not null");
//		return companyCustomersDao
//				.queryCompanyCustomersForFront(companyCustomersDTO);
//	}

//	public List<CompanyCustomersDO> queryCompanyCustomersByGroupId(
//			Integer groupId) {
//		Assert.notNull(groupId, "groupId is not null");
//		return companyCustomersDao.queryCompanyCustomersByGroupId(groupId);
//	}
	public Integer insertCompanyCustomers(CompanyCustomersDO companyCustomersDO) {
        Assert.notNull(companyCustomersDO, "companyCustomersDO is not null");
		return companyCustomersDao.insertCompanyCustomers(companyCustomersDO);
	}
   
	public CompanyCustomersDO queryCompanyCustomersById(Integer id) {
       Assert.notNull(id, "id is not null");
		return companyCustomersDao.queryCompanyCustomersById(id);
	}

	public Integer updateCompanyCustomers(CompanyCustomersDO companyCustomersDO) {
          Assert.notNull(companyCustomersDO, "companyCustomersDO is not null");
		return companyCustomersDao.updateCompanyCustomers(companyCustomersDO);
	}

//	public Integer deleteCompanyCustomersById(Integer id) {
//         Assert.notNull(id, "id is not null");
//		return companyCustomersDao.deleteCompanyCustomersById(id);
//	}

	public Integer batchUpdateGroupById(String ids,Integer companyCustomersGroupId) {
		String[] str = ids.split(",");
		Integer[] i=new Integer[str.length];
		for(int ii=0;ii<str.length;ii++){
			i[ii] = Integer.valueOf(str[ii]);
		}
		companyCustomersDao.updateGroupById(i, companyCustomersGroupId);
		return null;
	}

//	public Integer queryCompanyCustomersRecordCount(CompanyCustomersDTO companyCustomersDTO) {
//
//		return companyCustomersDao.queryCompanyCustomersRecordCount(companyCustomersDTO);
//	}

	public Integer batchDeleteCustomerById(Integer[] entities) {

		return companyCustomersDao.batchDeleteCompanyCustomersById(entities);
	}

//	public Integer insertCompanyToCustomers(CompanyDO companyDO,CompanyContactsDO companyContactsDO) {
//		// 
//		CompanyCustomersDO companyCustomersDO=new CompanyCustomersDO();
//		
//		companyCustomersDO.setName(companyContactsDO.getContact());
//		companyCustomersDO.setAccount(companyCustomersDO.getAccount());
//		companyCustomersDO.setPosition(companyContactsDO.getPosition());
//		companyCustomersDO.setSex(companyContactsDO.getSex());
//		companyCustomersDO.setEmail(companyContactsDO.getEmail());
//		
//		companyCustomersDO.setTelCountryCode(companyContactsDO.getTelCountryCode());
//		companyCustomersDO.setTelAreaCode(companyContactsDO.getTelAreaCode());
//		companyCustomersDO.setTel(companyContactsDO.getTel());
//		companyCustomersDO.setFaxCountryCode(companyContactsDO.getFax());
//		companyCustomersDO.setMobile(companyContactsDO.getMobile());
//		companyCustomersDO.setFaxAreaCode(companyContactsDO.getFaxAreaCode());
//		companyCustomersDO.setFax(companyContactsDO.getFax());
//		companyCustomersDO.setAccount(companyContactsDO.getAccount());
//		
//		companyCustomersDO.setCompanyId(companyDO.getId());
//		companyCustomersDO.setCompanyContactsId(companyContactsDO.getId());
//		companyCustomersDO.setCompany(companyDO.getName());
//		if (companyDO!=null&&companyDO.getAreaCode().length()>16) {
//			companyCustomersDO.setAreaCode((companyCustomersDO.getAreaCode().substring(0, companyCustomersDO.getAreaCode().length()-4)));
//		}else {
//			companyCustomersDO.setAreaCode(companyDO.getAreaCode());
//		}
//		companyCustomersDO.setForeignCity(companyDO.getForeignCity());
//		companyCustomersDO.setAddress(companyContactsDO.getAddress());
//		companyCustomersDO.setPostCode(companyContactsDO.getZip());
//		return companyCustomersDao.insertCompanyCustomers(companyCustomersDO);
//	}

//	public List<CompanyCustomersDO> queryCompanyCustomersByCompanyId(
//			Integer companyId) {
//		Assert.notNull(companyId, "The companyId must not be null");
//		return companyCustomersDao.queryCompanyCustomersByCompanyId(companyId);
//	}

	@Override
	public List<CompanyCustomersDO> queryCompanyCustomersForImportByInquiry(Integer companyId) {
		Assert.notNull(companyId, "The companyId must not be null");
		return companyCustomersDao.queryCompanyCustomersForImportByInquiry(companyId);
	}

	@Override
	public void insertCompanyCustomersForImport(String importAccount,
			List<CompanyCustomersDO> companyCustomerList) {
		Assert.notNull(importAccount, "The account must not be null");
		Assert.notNull(companyCustomerList, "The companyCustomerList must not be null");
		for(CompanyCustomersDO customer:companyCustomerList){
			customer.setAccount(importAccount);
			companyCustomersDao.insertCompanyCustomers(customer);
		}
	}

	@Override
	public Integer updateCustomersGroup(Integer newGroupId,Integer companyCustomersId) {
		return companyCustomersDao.updateCustomersGroup(newGroupId,companyCustomersId);
	}

	@Override
	public PageDto<CompanyCustomersDTO> queryCompanyCustomerListByCompanyIdAndGroupId(CompanyCustomersDO customer,PageDto page) {
		Assert.notNull(customer.getCompanyId(), "The companyId must not be null");
		return companyCustomersDao.queryCompanyCustomerListByCompanyIdAndGroupId(customer,page);
	}
}

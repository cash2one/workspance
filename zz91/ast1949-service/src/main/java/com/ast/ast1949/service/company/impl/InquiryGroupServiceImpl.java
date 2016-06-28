/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-2
 */
package com.ast.ast1949.service.company.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.InquiryGroup;
import com.ast.ast1949.persist.company.InquiryGroupDao;
import com.ast.ast1949.service.company.InquiryGroupService;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("inquiryGroupService")
public class InquiryGroupServiceImpl implements InquiryGroupService {
	
	@Autowired
	private InquiryGroupDao inquiryGroupDao;
	
	public List<InquiryGroup> queryGroupOfCompany(Integer companyId) {
		Assert.notNull(companyId, "the companyId can not be null");
		return inquiryGroupDao.queryGroupOfCompany(companyId, null);
	}

	@Override
	public List<InquiryGroup> queryGroupOfAccount(String account) {
		Assert.notNull(account, "the account can not be null");
		return inquiryGroupDao.queryGroupOfCompany(null, account);
	}

	@Override
	public List<InquiryGroup> querySystemGroup() {
		return inquiryGroupDao.querySystemGroup();
	}

	@Override
	public Integer createGroup(InquiryGroup group) {
		Assert.notNull(group.getCompanyId(), "the companyId can not be null");
		Assert.notNull(group.getAccount(), "the account can not be null");
		Assert.notNull(group.getName(), "the group name can not be null");
		return inquiryGroupDao.insertGroup(group);
	}

	@Override
	public Integer updateGroup(InquiryGroup group) {
		Assert.notNull(group.getAccount(), "the account can not be null");
		Assert.notNull(group.getName(), "the group name can not be null");
		return inquiryGroupDao.updateGroup(group);
	}

	@Override
	public Integer deleteGroup(Integer id, String account) {
		Assert.notNull(account, "the account can not be null");
		Assert.notNull(id, "the id can not be null");
		return inquiryGroupDao.deleteGroup(id, account);
	}

	@Override
	public String queryName(Integer id) {
		
		return inquiryGroupDao.queryName(id);
	}
		
//	@Autowired
//	private InquiryGroupDao inquiryGroupDao;
//	@Autowired
//	private InquiryService inquiryService;
//
//	public Integer deleteInquiryGroupById(Integer id) {
//		Assert.notNull(id, "the id must not be null");
//		Integer im = inquiryGroupDao.deleteInquiryGroupById(id);
//		if(im.intValue()>0) {
//			inquiryService.setInquiryUngroupedByGroupId(id);
//		}
//		return im;
//	}
//
//	public Integer insertInquiryGroup(InquiryGroupDO inquiryGroupDO) {
//		Assert.notNull(inquiryGroupDO, "the object of inquiryGroupDO must not be null");
//		inquiryGroupDO.setGmtCreated(new Date());
//		inquiryGroupDO.setGmtModified(new Date());
//		
//		return inquiryGroupDao.insertInquiryGroup(inquiryGroupDO);
//	}
//
//	public List<InquiryGroupDO> queryInquiryGroupListByCompanyId(Integer companyId) {
//		Assert.notNull(companyId, "the companyId must not be null");
//		return inquiryGroupDao.queryInquiryGroupListByCompanyId(companyId);
//	}
//
////	public InquiryGroupDO queryInquiryGroupById(Integer id) {
////		Assert.notNull(id, "the id must not be null");
////		return inquiryGroupDao.queryInquiryGroupById(id);
////	}
//
//	public Integer updateInquiryGroup(InquiryGroupDO inquiryGroupDO) {
//		Assert.notNull(inquiryGroupDO, "the object of inquiryGroupDO must not be null");
//		inquiryGroupDO.setGmtModified(new Date());
//		return inquiryGroupDao.updateInquiryGroup(inquiryGroupDO);
//	}
//
//	public Integer batchSetInquiryGroup(String inquiryArray,
//			String inquiryGroupId) {
//		if(inquiryArray==null||"".equals(inquiryArray)){
//			return 0;
//		}else {
//			String[] str = inquiryArray.split(",");
//			int[] i=new int[str.length];
//			for(int ii=0;ii<str.length;ii++){
//				i[ii] = Integer.valueOf(str[ii]);
//			}
//			
//			return inquiryGroupDao.batchSetInquiryGroup(i, inquiryGroupId);
//		}
//	}
//
//	public List<InquiryGroupDO> queryCustomizecInquiryGroupListByCompanyId(
//			Integer companyId) {
//		Assert.notNull(companyId, "the companyId must not be null");
//		return inquiryGroupDao.queryCustomizecInquiryGroupListByCompanyId(companyId);
//	}
}

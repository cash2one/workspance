package com.zz91.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.crm.dao.CrmContactDao;
import com.zz91.crm.domain.CrmContact;
import com.zz91.crm.service.CrmContactService;
import com.zz91.util.Assert;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-12-13 
 */
@Component("crmContactService")
public class CrmContactServiceImpl implements CrmContactService {
	
	@Resource
	private CrmContactDao crmContactDao;

	@Override
	public Integer createCrmContact(CrmContact crmContact) {
		Assert.notNull(crmContact.getCid(),"the cid can not be null");
		Assert.notNull(crmContact.getSex(),"the sex can not be null");
		Assert.notNull(crmContact.getIsKey(),"the is_key can not be null");
		return crmContactDao.createCrmContact(crmContact);
	}

	@Override
	public List<CrmContact> queryCrmContactByCid(Integer cid) {
		Assert.notNull(cid, "the cid can not be null");
		return crmContactDao.queryCrmContactByCid(cid);
	}

}

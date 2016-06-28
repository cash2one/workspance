package com.zz91.crm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zz91.crm.dao.BaseDao;
import com.zz91.crm.dao.CrmContactDao;
import com.zz91.crm.domain.CrmContact;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-12-13 
 */
@Component("crmContactDao")
public class CrmContactDaoImpl extends BaseDao implements CrmContactDao {

	final static String SQL_PRIFIX="crmContact";
	
	@Override
	public Integer createCrmContact(CrmContact crmContact) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PRIFIX, "createCrmContact"), crmContact);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmContact> queryCrmContactByCid(Integer cid) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryCrmContactByCid"), cid);
	}
}

package com.zz91.sms.dao.impl;

import com.zz91.sms.dao.BaseDaoSupport;
import com.zz91.sms.dao.SmsSendDao;

public class SmsSendDaoImpl extends BaseDaoSupport implements SmsSendDao {

	final static String SQL_PREFIX = "smsLog";

	@Override
	public Integer SendStringSms(String smsDetails) {

		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "SendStringSms"), smsDetails);
	}

}

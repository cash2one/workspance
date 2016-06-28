package com.zz91.mail.dao.impl;

import org.springframework.stereotype.Repository;

import com.zz91.mail.dao.BaseDao;
import com.zz91.mail.dao.MailSendDao;

@Repository("mailSendDao")
public class MailSendDaoImpl extends BaseDao implements MailSendDao {

	final static String SQL_PREFIX = "mailInfoDomain";
	@Override
	public Integer SendStringMail(String mailDetails) {
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "SendStringMail"), mailDetails);
	}

}

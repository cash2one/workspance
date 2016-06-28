/**
 * Project name: zz91-mail
 * File name: MailInfoDaoImpl.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.mail.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.zz91.mail.dao.BaseDao;
import com.zz91.mail.dao.MailInfoDao;
import com.zz91.mail.domain.MailInfoDomain;
import com.zz91.mail.domain.dto.PageDto;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-11-10
 */
@Repository("mailInfoDao")
public class MailInfoDaoImpl extends BaseDao implements MailInfoDao {

    final static String SQL_PREFIX = "mailInfo";

    @Override
    public MailInfoDomain selectById(Integer id) {
        return (MailInfoDomain) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "selectById"), id);
    }

    @Override
    public Integer insert(MailInfoDomain mto) {
        return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertMail"), mto);
    }

    @Override
    public Integer update(MailInfoDomain mto) {
        return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "update"), mto);
    }

    @Override
    public List<MailInfoDomain> selectMailQueue(Map<String, Object> map) {
        @SuppressWarnings("unchecked")
        List<MailInfoDomain> list = getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "selectMailQueue"), map);
        return list;
    }

    @Override
    public List<MailInfoDomain> queryMailForSend(Integer i) {
        @SuppressWarnings("unchecked")
        List<MailInfoDomain> list = getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryMailForSend"), i);
        return list;
    }

	@Override
	public Integer updateSendStatus(Integer id, Integer status) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("sendStatus", status);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateSendStatus"), root);
	}

	@Override
	public Integer recoverStatus(Integer fromStatus, Integer toStatus) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("fromStatus", fromStatus);
		root.put("toStatus", toStatus);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "recoverStatus"), root);
	}

	/********************************/
	@Override
	public MailInfoDomain queryOne(Integer id) {
		Assert.notNull(id, "The id can not be null");
		return (MailInfoDomain) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOne"), id);
	}

    @Override
    public Integer deleteById(Integer id) {   	
        return (Integer) getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteById"), id);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<MailInfoDomain> queryMail(Date from, Date to, Integer priority,
			PageDto<MailInfoDomain> page) {
		Map<String,Object> list=new HashMap<String,Object>();
		list.put("from", from);
		list.put("to", to);
		list.put("priority", priority);
		list.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX,"queryMail"),list);
	}

	@Override
	public Integer queryMailCount(Date from,
			Date to, Integer priority) {
		Map<String,Object>list=new HashMap<String,Object>();
		list.put("from", from);
		list.put("to", to);
		list.put("priority", priority);	
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX,"queryMailCount"),list);
	}


}

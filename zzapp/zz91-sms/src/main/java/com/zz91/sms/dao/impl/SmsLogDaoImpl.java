package com.zz91.sms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.sms.dao.BaseDaoSupport;
import com.zz91.sms.dao.SmsLogDao;
import com.zz91.sms.domain.SmsLog;
import com.zz91.sms.dto.Pager;

@Repository("smsLogDao")
public class SmsLogDaoImpl extends BaseDaoSupport implements SmsLogDao {

	final static String SQL_PREFIX = "smsLog";

	@Override
	public Integer delete(Integer id) {
		return (Integer) getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PREFIX, "deleteById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SmsLog> queryLog(String from, String to, Integer sendStatus,
			String receiver, String gatewayCode, Integer priority,
			String content, String templateCode, Pager<SmsLog> page) {
		Map<String, Object> list = new HashMap<String, Object>();
		list.put("from", from);
		list.put("to", to);
		list.put("sendStatus", sendStatus);
		list.put("receiver", receiver);
		list.put("gatewayCode", gatewayCode);
		list.put("priority", priority);
		list.put("content", content);
		list.put("templateCode", templateCode);
		list.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "querySmsLog"), list);
	}

	@Override
	public Integer queryLogCount(String from, String to, String receiver, String gatewayCode, Integer priority,
			String content, String templateCode, Integer sendStatus) {
		Map<String, Object> list = new HashMap<String, Object>();
		list.put("from", from);
		list.put("to", to);
		list.put("receiver", receiver);
		list.put("gatewayCode", gatewayCode);
		list.put("priority", priority);
		list.put("content", content);
		list.put("templateCode", templateCode);
		list.put("sendStatus", sendStatus);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "querySmsLogCount"), list);
	}

	@Override
	public Integer updateStatus(Integer id, Integer sendStatus) {
		Map<String, Object> list = new HashMap<String, Object>();
		list.put("id", id);
		list.put("sendStatus", sendStatus);
		return (Integer) getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateSendStatus"), list);
	}

	@Override
	public Integer insert(SmsLog sms) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertSms"), sms);
	}

	@Override
	public List<SmsLog> querySmsSend(Integer i) {
		@SuppressWarnings("unchecked")
		List<SmsLog> list = getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "querySmsSend"), i);
		return list;
	}

	@Override
	public Integer recoverStatus(Integer fromStatus, Integer toStatus) {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("fromStatus", fromStatus);
		rootMap.put("toStatus", toStatus);
		return (Integer) getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "recoverStatus"), rootMap);
	}

}

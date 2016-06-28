package com.zz91.sms.dao;

import java.util.List;

import com.zz91.sms.domain.SmsLog;
import com.zz91.sms.dto.Pager;

public interface SmsLogDao {

	public Integer delete(Integer id);

	public Integer updateStatus(Integer id, Integer sendStatus);

	public List<SmsLog> queryLog(String from, String to, Integer sendStatus,
			String receiver, String gatewayCode, Integer priority,
			String content, String templateCode, Pager<SmsLog> page);

	public Integer queryLogCount(String from, String to, String receiver,
			String gatewayCode, Integer priority, String content, String templateCode,
			Integer sendStatus);

	public Integer insert(SmsLog sms);

	public List<SmsLog> querySmsSend(Integer i);

	public Integer recoverStatus(Integer fromStatus, Integer toStatus);

}

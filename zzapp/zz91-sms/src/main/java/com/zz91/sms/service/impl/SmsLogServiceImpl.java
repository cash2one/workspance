package com.zz91.sms.service.impl;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Component;

import com.zz91.sms.dao.SmsLogDao;
import com.zz91.sms.dao.TemplateDao;
import com.zz91.sms.domain.SmsLog;
import com.zz91.sms.domain.Template;
import com.zz91.sms.dto.Pager;
import com.zz91.sms.service.SmsLogService;
import com.zz91.util.Assert;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Component("smsLogService")
public class SmsLogServiceImpl implements SmsLogService {

	@Resource
	private SmsLogDao smsLogDao;
	@Resource
	private TemplateDao templateDao;

	@Override
	public Pager<SmsLog> pageLog(String from, String to, Integer sendStatus,
			String receiver, String gatewayCode, Integer priority,
			String content, String templateCode, Pager<SmsLog> page) {
		if (page.getSort() == null) {
			page.setSort("gmt_send");
		}
		if (page.getDir() == null) {
			page.setDir("desc");
		}

		page.setRecords(smsLogDao.queryLog(from, to, sendStatus, receiver, gatewayCode, priority, content, templateCode, page));
		page.setTotals(smsLogDao.queryLogCount(from, to, receiver, gatewayCode, priority, content, templateCode, sendStatus));
		return page;
	}

	@Override
	public Integer remove(Integer id) {
		Assert.notNull(id, "the id can not be null!");
		return smsLogDao.delete(id);
	}

	@Override
	public void resend(Integer id) {
		smsLogDao.updateStatus(id, SEND_READY);
	}

	@Override
	public List<SmsLog> queryLogs(Integer i) {
		return smsLogDao.querySmsSend(i);
	}

	@Override
	public boolean shutdownRecovery(Integer fromStatus, Integer toStatus) {
		if (fromStatus == null || toStatus == null) {
			return false;
		}
		if (fromStatus.intValue() == toStatus.intValue()) {
			return false;
		}
		Integer i = smsLogDao.recoverStatus(fromStatus, toStatus);
		if (i != null) {
			return true;
		}
		return false;
	}

	@Override
	public Integer updateSuccess(Integer smsId, Integer sendStatus) {
		return smsLogDao.updateStatus(smsId, sendStatus);
	}

	@Override
	public Integer create(SmsLog sms) {
		
		if(sms.getGatewayCode()==null){
			sms.setGatewayCode("");
		}
		if(sms.getPriority()==null){
			sms.setPriority(0);
		}
		if(StringUtils.isNotEmpty(sms.getGmtSendStr())){
			Date date=null;
			try {
				date=DateUtil.getDate(sms.getGmtSendStr(), "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			sms.setGmtSend(date);
		}
		if(sms.getGmtSend()==null){
			sms.setGmtSend(new Date());
		}
		if(sms.getSendStatus()==null){
			sms.setSendStatus(0);
		}
		if(StringUtils.isNotEmpty(sms.getTemplateCode())){
			sms.setContent(buildSmsContent(sms.getTemplateCode(), sms.getSmsParameter()));		
		}
		if(StringUtils.isEmpty(sms.getContent())){
			return 0;
		}
		
		return smsLogDao.insert(sms);
	}

	private String buildSmsContent(String code, String smsParameter) {
		
		JSONArray obj = JSONArray.fromObject(smsParameter);
		
		if(code==null){
			return String.format("{0}", obj.toArray());
		}
		
		Template template=templateDao.queryTemplateByCode(code);
		if(template==null){
			return String.format("{0}模板不存在", obj.toArray());
		}
		String[] str = coverJsonArrayToStringArray(obj);
		MessageFormat descriptionFormat = new MessageFormat(template.getContent());
		return descriptionFormat.format(str)+template.getSigned();
	}

	private String[] coverJsonArrayToStringArray(JSONArray obj){
		String[] str = new String[obj.size()];
		for(int i=0;i<obj.size();i++){
			str[i] = obj.getString(i);
		}
		return str;
	}
}

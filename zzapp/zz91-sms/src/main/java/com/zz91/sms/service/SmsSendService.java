package com.zz91.sms.service;


import com.zz91.sms.domain.SmsLog;


public interface SmsSendService {
	
	public Integer sendSms(SmsLog sms);
	
	public Integer sendSmsByCode(SmsLog sms);

}

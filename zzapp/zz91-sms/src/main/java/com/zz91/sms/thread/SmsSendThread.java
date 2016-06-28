package com.zz91.sms.thread;

import org.springframework.stereotype.Service;

import com.zz91.sms.common.ZZSms;
import com.zz91.sms.domain.SmsLog;
import com.zz91.sms.service.GatewayService;
import com.zz91.sms.service.SmsLogService;
import com.zz91.util.lang.StringUtils;

@Service
public class SmsSendThread extends Thread {
	
	final static String DEFAULT_GATEWAY = "yuexinYX";

	private SmsLog smsLog;
	private SmsLogService smsLogService;

	public SmsSendThread() {

	}

	public SmsSendThread(SmsLog smsLog, SmsLogService smsLogService) {
		this.smsLog = smsLog;
		this.smsLogService = smsLogService;
	}

	@Override
	public void run() {

		ZZSms sms = (ZZSms) GatewayService.CACHE_GATEWAY.get(smsLog.getGatewayCode());
		Integer sendStatus = SmsLogService.SEND_FAILURE;
		do {
			// 判空 取默认网关code
			if (sms == null) {
				sms = getDefault();
			}
			
			// 判断长度
			if(StringUtils.isNotEmpty(smsLog.getReceiver())&&smsLog.getReceiver().length()!=11){
				smsLogService.updateSuccess(smsLog.getId(), SmsLogService.SEND_FAILURE);
				break;
			}

			// 判断号码为纯数字
			if(!StringUtils.isNumber(smsLog.getReceiver())){
				smsLogService.updateSuccess(smsLog.getId(), SmsLogService.SEND_FAILURE);
				break;
			}
			
			// 判断是否发送短信
			if (ControlThread.DEBUG) {
				System.out.println("=======调试状态短信发送:send mobile(发送目标):"+smsLog.getReceiver()+" ; send message(发送内容):"+smsLog.getContent());
			}else{
				sendStatus = sms.send(smsLog.getReceiver(), smsLog.getContent());
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			smsLogService.updateSuccess(smsLog.getId(), sendStatus);
		} while (false);
	}

	private ZZSms getDefault() {
		ZZSms obj = null;
		obj = (ZZSms) GatewayService.CACHE_GATEWAY.get(DEFAULT_GATEWAY);
		return obj;
	}
	public static void main(String[] args) {
		String str="13410899887/13590448959";
		if(str.length()>11){
			System.out.println(str.substring(0, 11));
		}
	}
}

package com.zz91.sms.thread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.sms.domain.SmsLog;
import com.zz91.sms.service.SmsLogService;

@Service
public class SmsScanThread extends Thread {

	public final static Map<String, Long> map = new ConcurrentHashMap<String, Long>();

	public static boolean runSwitch = true;
	@Resource
	private SmsLogService smsLogService;

	public SmsScanThread() {

	}

	@SuppressWarnings("static-access")
	public void run() {
		while (runSwitch) {
			int queueSize = ControlThread.mainPool.getQueue().size();
			if (queueSize <= 50) {
				List<SmsLog> smsList = smsLogService.queryLogs(50);
				if (smsList != null && smsList.size() > 0) {
					for (SmsLog smsLog : smsList) {
						smsLogService.updateSuccess(smsLog.getId(),smsLogService.SEND_PROCESS);
						ControlThread.excute(new SmsSendThread(smsLog,smsLogService));
					}
				}
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {

			}
		}
	}
}

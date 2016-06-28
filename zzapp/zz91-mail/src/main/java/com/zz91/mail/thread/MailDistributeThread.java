/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-10-13
 */
package com.zz91.mail.thread;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.mail.domain.MailInfoDomain;
import com.zz91.mail.service.MailInfoService;
import com.zz91.mail.service.MailSendService;

/**
 * 邮件分发线程，将内存中的邮件分发给线程池
 * 
 * @author mays (mays@zz91.net)
 * 
 */
@Service
public class MailDistributeThread extends Thread {

	public static boolean runSwitch = true;

	// private long waringValue = 90; // 警戒值,当超过警戒值,可以发出警告

	@Resource
	MailSendService mailSendService;
	@Resource
	MailInfoService mailInfoService;

	public MailDistributeThread() {

	}

	// 最快每一秒往线程里增加10封待发送邮件
	public void run() {
		while (runSwitch) {

			MailInfoDomain mailInfo = MailScanThread.mailQueue.peek();

			if (mailInfo != null) {
				int queueSize = ControlThread.mainPool.getQueue().size();
				if (queueSize < 60) {
					ControlThread.excute(new MailSendThread(
							MailScanThread.mailQueue.poll(), mailInfoService,
							mailSendService));
				} 
				else if (queueSize == 60
						&& ControlThread.mainPool.getActiveCount() < ControlThread.mainPool
								.getMaximumPoolSize()) {
					ControlThread.excute(new MailSendThread(
							MailScanThread.mailQueue.poll(), mailInfoService,
							mailSendService));
				} 
				else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		String tmp = "abdsfsdf@zz91.com";
		System.out.println(tmp.substring(tmp.indexOf("@") + 1, tmp.length()));
	}

}

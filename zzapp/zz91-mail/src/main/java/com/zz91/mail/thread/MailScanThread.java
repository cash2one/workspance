/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-10-13
 */
package com.zz91.mail.thread;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.mail.domain.MailInfoDomain;
import com.zz91.mail.service.MailInfoService;
import com.zz91.mail.service.MailSendService;

/**
 * 邮件队列扫描线程，用于扫描数据库中待发送邮件信息
 * 
 * @author mays (mays@zz91.net)
 * 
 */
@Service
public class MailScanThread extends Thread {

	public final static Queue<MailInfoDomain> mailQueue = new ArrayBlockingQueue<MailInfoDomain>(100);
	//邮件发送计划，用来避免同一域的邮件在同一时间段内多次发送
	public final static Map<String, Long> mailPlan = new ConcurrentHashMap<String, Long>();

	public static boolean runSwitch = true;

	// private long waringValue = 90; // 警戒值,当超过警戒值,可以发出警告

	@Resource
	MailSendService mailSendService;
	@Resource
	MailInfoService mailInfoService;

	public MailScanThread() {

	}

	// 最快每一秒往线程里增加10封待发送邮件
	public void run() {
		while (runSwitch) {
			if (mailQueue.size() < 50) {
				List<MailInfoDomain> mailInfoList = mailInfoService
						.queryMailForSend(50);
				if (mailInfoList != null && mailInfoList.size() > 0) {
					for (MailInfoDomain mailInfo : mailInfoList) {
						mailQueue.add(mailInfo);
						mailInfoService.updateSending(mailInfo.getId());
					}
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * 得到同一域的邮件计划发送时间
	 * @param domain
	 * @param inteval
	 * @param now
	 * @return
	 */
	public synchronized static long getPlanSendTime(String domain, long inteval, long now){
		Long lst=mailPlan.get(domain);
		if(lst==null){
			lst=0l;
		}
		
		if(now>lst.longValue()){
			mailPlan.put(domain, now+inteval);
		}else{
			lst=lst+inteval;
			mailPlan.put(domain, lst);
		}
		return lst;
	}
}

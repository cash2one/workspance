/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-10-13
 */
package com.ast.ast1949.check.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

/**
 * 控制线程,用来启动和监控邮件发送
 * 
 * @author Leon
 * 
 */
@Service
public class ControlThread extends Thread {

	public static CheckRunThreadPool mainPool; // 行为跟踪线程池

	private int corePoolSize = 2; // 池中最小线程数量：2
	private int maximumPoolSize = 10; // 同时存在的最大线程数量：10
	private long keepAliveTime = 5; // 线程空闲保持时间：5秒
	private int workQueueSize = 60; // 工作队列最大值:100

	private static long numTask = 0; // 已处理数量
	private static long totalTime = 0; // 总处理时间
	private static int numQueue = 0; // 队列线程数量
	private static int activeThread=0;
	
	private static long lastMonitorTime=0;

	public static boolean runSwitch = true;

//	private long waringValue = 50; // 警戒值,当超过警戒值,可以发出警告
//	@Resource
//	MailSendService mailSendService;
//	@Resource
//	MailInfoService mailInfoService;

	public ControlThread(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, int workQueueSize) {
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveTime = keepAliveTime;
		this.workQueueSize = workQueueSize;

		ControlThread.mainPool = new CheckRunThreadPool(corePoolSize,
				maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(workQueueSize),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public ControlThread() {
		ControlThread.mainPool = new CheckRunThreadPool(corePoolSize,
				maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(workQueueSize),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public static void excute(Runnable command) {
		mainPool.execute(command);
	}

	// 每一秒钟检查一次处理状态
	public void run() {
		while (runSwitch) {
			
			numTask = mainPool.getNumTask();
			totalTime = mainPool.getTotalTime();
			numQueue = mainPool.getQueue().size();
			activeThread=mainPool.getActiveCount();

//			System.out.println("总处理量: "+
//					numTask+"  总处理时间: "+
//					totalTime+"ms  队列中: "+
//					numQueue+"  缓存队列："+
//					MailScanThread.mailQueue.size()+"  活动线程数："+
//					activeThread+" 最大线程数："+mainPool.getLargestPoolSize());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		}

	}

	public static long getNumTask() {
		return numTask;
	}

	public static long getTotalTime() {
		return totalTime;
	}

	public static int getNumQueue() {
		return numQueue;
	}
	
	public static int getActiveThread(){
		return activeThread;
	}
	
	public static long getLastMonitorTime(){
		return lastMonitorTime;
	}
	
	public static void setLastMonitorTime(){
		lastMonitorTime=System.currentTimeMillis();
	}
}

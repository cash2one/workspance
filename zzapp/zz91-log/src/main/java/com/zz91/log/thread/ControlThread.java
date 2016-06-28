/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-10-13
 */
package com.zz91.log.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 控制线程,用来启动和监控行为跟踪器
 * 
 * @author Leon
 * 
 */
@SuppressWarnings("unused")
public class ControlThread extends Thread {

	private static TrackingThreadPool mainPool; // 行为跟踪线程池

	private int corePoolSize = 2; // 池中最小线程数量：2
	private int maximumPoolSize = 10; // 同时存在的最大线程数量：10
	private long keepAliveTime = 5; // 线程空闲保持时间：5秒
	private int workQueueSize = 100; // 工作队列最大值:100

	private long numTask = 0; // 已处理数量
	private long totalTime = 0; // 总处理时间
	private int numQueue = 0; // 队列线程数量

	private long waringValue = 10; // 警戒值,当超过警戒值,可以发出警告

	public ControlThread(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, int workQueueSize) {
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveTime = keepAliveTime;
		this.workQueueSize = workQueueSize;

		ControlThread.mainPool = new TrackingThreadPool(corePoolSize, maximumPoolSize,
				keepAliveTime, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(workQueueSize),
				new ThreadPoolExecutor.AbortPolicy());
	}

	public ControlThread() {
		ControlThread.mainPool = new TrackingThreadPool(corePoolSize, maximumPoolSize,
				keepAliveTime, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(workQueueSize),
				new ThreadPoolExecutor.AbortPolicy());
	}

	public static void excute(Runnable command) {
		mainPool.execute(command);
	}

	// 每一秒钟检查一次处理状态
	public void run() {
		do {
			this.numTask = mainPool.getNumTask();
			this.totalTime = mainPool.getTotalTime();
			this.numQueue = mainPool.getQueue().size();
			//System.out.println("总处理量: "+numTask+"  总处理时间: "+totalTime+"ms   队列中: "+numQueue);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		} while (true);

	}

}

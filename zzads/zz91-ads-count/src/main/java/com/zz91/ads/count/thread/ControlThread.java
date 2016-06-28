/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-12-2
 */
package com.zz91.ads.count.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 控制线程,用来启动和监控行为跟踪器
 * 
 * @author root
 * 
 *         created on 2010-12-2
 */
@SuppressWarnings("unused")
public class ControlThread extends Thread {

	private static TrackingThreadPool mainPool; // 行为跟踪线程池

	private int corePoolSize = 2; // 池中最小线程数量：2
	private int maximumPoolSize = 12; // 同时存在的最大线程数量：10
	private long keepAliveTime = 5; // 线程空闲保持时间：5秒
	private int workQueueSize = 200; // 工作队列最大值:200

	private static long numTask = 0; // 已处理数量
	private static long totalTime = 0; // 总处理时间
	private static int numQueue = 0; // 队列线程数量
	private static int activeThread=0;

	private long waringValue = 10; // 警戒值,当超过警戒值,可以发出警告
	
	private static long lastMonitorTime=0;
	
	public static boolean RUNNING = true;
	
	public ControlThread(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, int workQueueSize) {
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveTime = keepAliveTime;
		this.workQueueSize = workQueueSize;

		ControlThread.mainPool = new TrackingThreadPool(corePoolSize, maximumPoolSize,
				keepAliveTime, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(workQueueSize),
				new ThreadPoolExecutor.DiscardPolicy());
	}

	public ControlThread() {
		ControlThread.mainPool = new TrackingThreadPool(corePoolSize, maximumPoolSize,
				keepAliveTime, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(workQueueSize),
				new ThreadPoolExecutor.DiscardPolicy());
	}

	public static void excute(Runnable command) {
		mainPool.execute(command);
	}

	// 每一秒钟检查一次处理状态
	public void run() {
		while(RUNNING) {
			
			
			if(mainPool==null){
				break;
			}
			
			numTask = mainPool.getNumTask();
			totalTime = mainPool.getTotalTime();
			numQueue = mainPool.getQueue().size();
			activeThread=mainPool.getActiveCount();
			
			//System.out.println("总处理量: "+numTask+"  总处理时间: "+totalTime+"ms   队列中: "+numQueue+" 工作线程："+activeThread);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		} ;

	}
	
	public static long getLastMonitorTime(){
		return lastMonitorTime;
	}
	
	public static void setLastMonitorTime(){
		lastMonitorTime=System.currentTimeMillis();
	}

	/**
	 * @return the numTask
	 */
	public static long getNumTask() {
		return numTask;
	}

	/**
	 * @return the totalTime
	 */
	public static long getTotalTime() {
		return totalTime;
	}

	/**
	 * @return the numQueue
	 */
	public static int getNumQueue() {
		return numQueue;
	}

	/**
	 * @return the activeThread
	 */
	public static int getActiveThread() {
		return activeThread;
	}
	
	public static void shutdown(){
		RUNNING=false;
		mainPool.shutdown();
	}
	
}

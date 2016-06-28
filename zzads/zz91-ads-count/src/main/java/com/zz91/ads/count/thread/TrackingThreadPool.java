/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-12-2
 */
package com.zz91.ads.count.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

/**
 * @author root
 * 
 *         created on 2010-12-2
 */
public class TrackingThreadPool extends ThreadPoolExecutor {
	
	private Logger LOG = Logger.getLogger(TrackingThreadPool.class);

	public TrackingThreadPool(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				handler);
	}

	private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	private final AtomicLong numTasks = new AtomicLong();
	private final AtomicLong totalTime = new AtomicLong();

	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		startTime.set(System.nanoTime());
	}

	protected void afterExecute(Runnable r, Throwable t) {
		try {
			numTasks.incrementAndGet();
			long endTime = System.nanoTime();
			long taskTime = endTime - startTime.get();
			totalTime.addAndGet(taskTime);
		} finally {
			super.afterExecute(r, t);
		}
	}

	protected void terminated() {
		try {
			LOG.info("tracking shutdown...   total task:" + numTasks.get()
					+ "  total time:" + totalTime.get());
		} finally {
			super.terminated();
		}
	}

	/**
	 * 获取总处理时间,单位纳秒
	 */
	public long getTotalTime() {
		return totalTime.get();
	}

	/**
	 * 获取总处理量(处理成功的数量)
	 */
	public long getNumTask() {
		return numTasks.get();
	}
}

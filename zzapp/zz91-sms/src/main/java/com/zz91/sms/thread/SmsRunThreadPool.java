package com.zz91.sms.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

public class SmsRunThreadPool extends ThreadPoolExecutor {

	private Logger LOG = Logger.getLogger(SmsRunThreadPool.class);

	public SmsRunThreadPool(int corePoolSize, int maximumPoolSize,
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

	public long getTotalTime() {
		return totalTime.get();
	}

	public long getNumTask() {
		return numTasks.get();
	}
}

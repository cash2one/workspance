/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-10-13
 */
package com.ast.ast1949.check.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

/**
 * @author Leon
 * 
 */
public class CheckRunThreadPool extends ThreadPoolExecutor {

	private Logger LOG = Logger.getLogger(CheckRunThreadPool.class);

	public CheckRunThreadPool(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				handler);
//		this.threads = new HashMap<String, Thread>();
	}

	private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	private final AtomicLong numTasks = new AtomicLong();
	private final AtomicLong totalTime = new AtomicLong();
//	private Map<String, Thread> threads;

	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		startTime.set(System.nanoTime());
	}

//	public void execute(Runnable command) {
//		super.execute(command);
//		if (command instanceof Thread) {
//			Thread t = (Thread) command;
//			String key = t.getName();
//			this.threads.put(key, t);
//		}
//	}

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

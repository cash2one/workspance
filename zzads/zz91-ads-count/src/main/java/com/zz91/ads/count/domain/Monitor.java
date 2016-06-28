/**
 * 
 */
package com.zz91.ads.count.domain;

import java.io.Serializable;

/**
 * @author root
 *
 */
public class Monitor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String time;
	private Long numTask;
	private String totalTime;
	private Integer numQueue;
	private Integer activeThread;

	public Monitor() {
		super();
	}


	public Monitor(String time, Long numTask, String totalTime, Integer numQueue,
			Integer activeThread) {
		super();
		this.time = time;
		this.numTask = numTask;
		this.totalTime = totalTime;
		this.numQueue = numQueue;
		this.activeThread = activeThread;
	}


	/**
	 * @return the numTask
	 */
	public Long getNumTask() {
		return numTask;
	}


	/**
	 * @param numTask the numTask to set
	 */
	public void setNumTask(Long numTask) {
		this.numTask = numTask;
	}


	/**
	 * @return the numQueue
	 */
	public Integer getNumQueue() {
		return numQueue;
	}

	/**
	 * @param numQueue the numQueue to set
	 */
	public void setNumQueue(Integer numQueue) {
		this.numQueue = numQueue;
	}

	/**
	 * @return the activeThread
	 */
	public Integer getActiveThread() {
		return activeThread;
	}

	/**
	 * @param activeThread the activeThread to set
	 */
	public void setActiveThread(Integer activeThread) {
		this.activeThread = activeThread;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}


	/**
	 * @return the totalTime
	 */
	public String getTotalTime() {
		return totalTime;
	}


	/**
	 * @param totalTime the totalTime to set
	 */
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	
	
}

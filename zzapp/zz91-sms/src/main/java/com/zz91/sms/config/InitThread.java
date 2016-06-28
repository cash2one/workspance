package com.zz91.sms.config;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.sms.thread.ControlThread;
import com.zz91.sms.thread.SmsScanThread;

@Component("initThread")
public class InitThread {
	
	
	
	@Resource
	private SmsScanThread smsScanThread;
	@Resource
	private ControlThread controlThread;
	
	public void startup(){
//		controlThread.start();
//		smsScanThread.start();
	}
	
	public void shutdown(){
		ControlThread.shutdown();
		SmsScanThread.runSwitch=false;
	}

}

/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-10-13
 */
package com.ast.ast1949.check.thread;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ast.ast1949.service.products.ProductsAutoCheckService;
import com.ast.ast1949.service.products.ProductsService;

/**
 * 邮件分发线程，将内存中的邮件分发给线程池
 * 
 * @author mays (mays@zz91.net)
 * 
 */
@Service
public class CheckDistributeThread extends Thread {

	public static boolean runSwitch = true;

	// private long waringValue = 90; // 警戒值,当超过警戒值,可以发出警告
	
	@Resource
	private ProductsAutoCheckService productsAutoCheckService;
	@Resource
	private ProductsService productsService;


	public CheckDistributeThread() {

	}

	// 最快每一秒往线程里增加10封待发送邮件
	public void run() {
		while (runSwitch) {

			Integer productId = CheckScanThread.checkQueue.peek();

			if (productId != null) {
				ControlThread.excute(new CheckSendThread(CheckScanThread.checkQueue.poll(), productsAutoCheckService,productsService));
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		
	}

}

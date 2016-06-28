/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-10-13
 */
package com.ast.ast1949.check.thread;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ast.ast1949.domain.products.ProductsAutoCheck;
import com.ast.ast1949.service.products.ProductsAutoCheckService;

/**
 * 邮件队列扫描线程，用于扫描数据库中待发送邮件信息
 * 
 * @author mays (mays@zz91.net)
 * 
 */
@Service
public class CheckScanThread extends Thread {

	public final static Queue<Integer> checkQueue = new ArrayBlockingQueue<Integer>(100);

	public static boolean runSwitch = true;

	// private long waringValue = 90; // 警戒值,当超过警戒值,可以发出警告

	@Resource
	private ProductsAutoCheckService productsAutoCheckService;

	public CheckScanThread() {
		
	}

	public void run() {
		while (runSwitch) {
			if (checkQueue.size() < 50) {
				List<ProductsAutoCheck> list = productsAutoCheckService.queryCheckBySize(50);
				for(ProductsAutoCheck obj : list){
					checkQueue.add(obj.getId());
					productsAutoCheckService.updateByStatus(obj.getId(), ProductsAutoCheckService.CHECKING);
				}
			}

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			}
		}
	}

}

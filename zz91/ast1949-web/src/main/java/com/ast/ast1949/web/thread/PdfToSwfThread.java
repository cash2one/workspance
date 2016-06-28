package com.ast.ast1949.web.thread;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ast.ast1949.domain.download.DownloadInfo;
import com.ast.ast1949.persist.download.DownloadInfoDao;
import com.zz91.util.file.FileUtils;
import com.zz91.util.file.SwfUtils;

/**
 *	author:kongsj
 *	date:2013-7-10
 */
public class PdfToSwfThread extends Thread{
	
	DownloadInfoDao downloadInfoDao;
	
	private static Queue<String> FILE_URL=new ConcurrentLinkedQueue<String>();
	
	private final static String DATA_URL = "/mnt/data/resources";
	
	final static long INTERVAL = 30000;
	
	public PdfToSwfThread(DownloadInfoDao downloadInfoDao){
		this.downloadInfoDao = downloadInfoDao;
	}
	
	@Override
	public void run() {
		while (true) {
			String fileUrl=FILE_URL.poll();
			if(fileUrl!=null){
				String sourceFilePath=DATA_URL + fileUrl;
				SwfUtils.convertFileToSwf(sourceFilePath, sourceFilePath.replaceAll(".pdf", "")+"/%.swf");
				
				DownloadInfo obj = this.downloadInfoDao.queryByFileUrl(fileUrl);
				if(obj!=null){
					Long l = FileUtils.getFileSize(DATA_URL+obj.getFileUrl());
					obj.setSize(String.valueOf(l/1000));
					this.downloadInfoDao.update(obj);
				}else{
					FILE_URL.add(fileUrl);
					try {
						Thread.sleep(INTERVAL);
					} catch (InterruptedException e) {
					}
				}
				
			}else{
				try {
					Thread.sleep(INTERVAL);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public static void addfile(String file){
		FILE_URL.add(file);
	}

}

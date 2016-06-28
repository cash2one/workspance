package com.zz91.ep.service.stat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.httpclient.NameValuePair;
import com.zz91.util.http.HttpUtils;
/**
 * 项目名称：中国环保网
 * 模块编号：统计分析Service层
 * 模块描述：统计分析相关数据操作实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-08-10　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
 */
public class LogService implements Runnable{
	
	//测试版,连接没写为可配置
	private static String LOGSERVERHOST="http://localhost:580/zz91-log";
	
	/*
	 * 日志队列,测试版,此同步方式可能不是线程安全的,后续优化,
	 * 加队列是为了在日志异常等情况下不影响页面反应速度
	 */
	public static BlockingQueue<String> logsQueue = new LinkedBlockingQueue<String>();

	@Override
	public void run() {
		while(true){
			try {
				String paramStr = logsQueue.take();
	        	NameValuePair[] param=new NameValuePair[]{
	    				new NameValuePair("logData", paramStr)
	    				};
	        	
	        	HttpUtils.getInstance().httpPost(LOGSERVERHOST+"/log", param, HttpUtils.CHARSET_UTF8);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
	}
}

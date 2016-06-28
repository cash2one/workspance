package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.httpclient.NameValuePair;

import com.zz91.util.http.HttpUtils;
import com.zz91.util.log.LogUtil;

public class TestLogs implements Runnable{

	public static List<String> targets= new ArrayList<String>(); 
	public static  int counts=0;
	@Override
	public void run() {
		while(true){
			String host = "http://test.huanbao.com:8080/trade"+targets.get(new Random().nextInt(10));
			try {
				NameValuePair[] param=new NameValuePair[]{
						new NameValuePair("ip",new String("iii"+new Random().nextInt(1000000))),
						new NameValuePair("user",new String("黄怀清"+new Random().nextInt(1000000))),
						new NameValuePair("url",new String("iii"+new Random().nextInt(100000))),
						new NameValuePair("cid",host),
						new NameValuePair("start","0"),
						new NameValuePair("limit","20"),
						new NameValuePair("size","20"),
						new NameValuePair("pageNum","1")
				};
				
				HttpUtils.getInstance().httpPost(host
						, param, HttpUtils.CHARSET_UTF8);
				counts++;
				System.out.println(counts);
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
	}
	
	public static void main(String[] args) {

		TestLogs.targets.add("/buy/list.htm");
		TestLogs.targets.add("/buy/detail.htm");
		TestLogs.targets.add("/supply/index.htm");
		TestLogs.targets.add("/zdbuy/list.htm");
		TestLogs.targets.add("/zdbuy/detail.htm");
		TestLogs.targets.add("/newbq.htm");
		TestLogs.targets.add("/newb.htm");
		TestLogs.targets.add("/zw.htm");
		TestLogs.targets.add("/newg.htm");
		TestLogs.targets.add("/cp.htm");
		//TestLogs.targets.add("/bq.htm");
		TestLogs.targets.add("/index.htm");

		
		for (int i = 0; i < 1000; i++) {
			Thread thr=new Thread(new TestLogs());
			thr.start();
			System.out.println(thr);
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
}

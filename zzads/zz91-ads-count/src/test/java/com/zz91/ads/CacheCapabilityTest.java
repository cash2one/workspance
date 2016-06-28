/**
 * 
 */
package com.zz91.ads;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.TimeHelper;

/**
 * @author root
 *
 */
public class CacheCapabilityTest {

	public static void main(String[] args) throws HttpException, IOException {
		long start=System.currentTimeMillis();
		for(int i=0;i<20000;i++){
			HttpUtils.getInstance().httpGet("http://localhost:580/ads/show?p=25", HttpUtils.CHARSET_UTF8);
			HttpUtils.getInstance().httpGet("http://localhost:580/ads/show?p=24&lang=%E4%B8%AD%E6%96%87&pricecode=1000&keywords=%E6%B5%8B%E8%AF%95", HttpUtils.CHARSET_UTF8);
		}
		long end=System.currentTimeMillis();
		System.out.println(end);
		System.out.println("总时间："+TimeHelper.formatTime(end-start));
	}
}

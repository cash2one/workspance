package com.zz91.ep.admin.util;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class SolrUpdateUtils {
	
	public static final String TRADE_SUPPLY = "tradesupply";
	public static final String COMPANY = "company";
	public static final String DELTA_IMPORT = "?command=delta-import";
	public static final String FULL_IMPORT = "?command=full-import";
	/**
	 *  String url = TRADE_SUPPLY;
		//验证是否在更新如果在更新，传null验证是否正在更新中
		if (!runUpdateSolr(url,null)) {
			if (!runUpdateSolr(url,"?command=full-import")) {
				System.out.println("更新成功.......");
			} else {
				System.out.println("更新失败.......");
			}
		} else {
			System.out.println("已经在更新.......");
		}
	 * @param type TRADE_SUPPLY  COMPANY
	 * @param commond null DELTA_IMPORT FULL_IMPORT
	 * @return
	 * @throws IOException
	 */
    public static boolean runUpdateSolr (String type,String commond) throws IOException{
    	boolean result  = false;
    	String command = "http://localhost:8081/solr/"+type+"/dataimport"+(commond==null?"":commond);
    	// 构建httpClient实例
    	HttpClient httpClient = new HttpClient();
    	// 创建get方法
    	GetMethod getMethod = new GetMethod(command);
    	// 使用系统默认的恢复策略
    	getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
    	try {
        	int status = httpClient.executeMethod(getMethod);
        	if (status == HttpStatus.SC_OK) {
        		result = true;
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	finally {
    		//释放链接
    		getMethod.releaseConnection();
    	}
    	return result;
    }
}

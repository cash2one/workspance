package com.zz91.gateway;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import com.zz91.sms.common.ZZSms;
import com.zz91.util.http.HttpUtils;

public class YueXinYXGateway implements ZZSms {

	final static String ACCOUNT = "asto";
	final static String PASSWORD = "123456";

	final static String BALANCE_URL = "http://mt.549k.com/getUser.do?Account="
			+ ACCOUNT + "&Password=" + PASSWORD;
	final static String SEND_URL = "http://mt.549k.com/send.do";

	/**
	 * 9001：获取成功 0001：帐号为空 0002：密码为空 0003：帐号验证不通过 8001：其他异常
	 */
	@Override
	public Object balance() {
		// http://mt.549k.com/getUser.do?Account=test&Password=test
		// {"money":"1189.40","code":"9001","suffix":"【悦信无线】","companyname":"悦信无线科技","summ":11894}
		do {

			String result = "";
			try {
				result = HttpUtils.getInstance().httpGet(BALANCE_URL,
						HttpUtils.CHARSET_UTF8);
			} catch (HttpException e) {
			} catch (IOException e) {
			}
			if (!result.startsWith("{")) {
				break;
			}
			JSONObject js = JSONObject.fromObject(result);
			if ("9001".equals(js.get("code"))) {
				return js.get("summ");
			} else {
				return js.get("code");
			}
		} while (false);
		return null;
	}

	/**
	 * 悦信短信发送 9001：发送成功 0001：帐号为空 0002：密码为空 1003：发送号码为空 1004：发送内容为空 0003：帐号验证不通过
	 * 1009：其他异常 8001：其他异常
	 */
	@Override
	public Integer send(String mobile, String content) {
		// http://mt.549k.com/send.do?Account=test&Password=test&Mobile=13316812345&Content=test&Exno=0
		do {
			String result = "";
//			NameValuePair[] data = {
//					new NameValuePair("account", ACCOUNT),
//					new NameValuePair("password", PASSWORD),
//					new NameValuePair("mobile", mobile),
//					new NameValuePair("content",content),
//					new NameValuePair("exno", "0")};
			try {
//				result = HttpUtils.getInstance().httpPost(SEND_URL, data, HttpUtils.CHARSET_UTF8);
//				result = HttpUtils.getInstance().httpGet(SEND_URL+"?Account="+ACCOUNT+"&Password="+PASSWORD+"&Mobile="+mobile+"&Content="+URLEncoder.encode(content, HttpUtils.CHARSET_UTF8)+"&Exno=0", HttpUtils.CHARSET_UTF8);
				result = httpClientHtml(SEND_URL+"?Account="+ACCOUNT+"&Password="+PASSWORD+"&Mobile="+mobile+"&Content="+URLEncoder.encode(content, HttpUtils.CHARSET_UTF8)+"&Exno=0", HttpUtils.CHARSET_UTF8);
			} catch (HttpException e) {
				break;
			} catch (IOException e) {
				break;
			} catch (URISyntaxException e) {
				break;
			}
			
			if (!result.startsWith("{")) {
				break;
			}
			
			JSONObject js = JSONObject.fromObject(result);
			if ("9001".equals(js.get("code"))) {
				return SUCCESS;
			} else {
				return FAILURE;
			}
		} while (false);
		return FAILURE;
	}
	
	public String httpClientHtml(String url, String strCharset)
			throws URISyntaxException, ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpProtocolParams
				.setUserAgent(
						httpclient.getParams(),
						"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");
		String charset = strCharset;
		HttpGet httpget = new HttpGet();
		String content = "";
		httpget.setURI(new java.net.URI(url));
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 使用EntityUtils的toString方法，传递默认编码，在EntityUtils中的默认编码是ISO-8859-1
			content = EntityUtils.toString(entity, charset);
			httpget.abort();
			httpclient.getConnectionManager().shutdown();
		}
		return content;
	}

	public static void main(String[] args) {
		YueXinYXGateway gateway = new YueXinYXGateway();
		System.out.println(gateway.balance());
		System.out.println(gateway.send("13666651091", "您正在取回密码，验证码：58de5，该验证码1小时内有效。工作人员不会向您索取验证码，切勿泄露给他人【ZZ91再生网】"));
	}
}

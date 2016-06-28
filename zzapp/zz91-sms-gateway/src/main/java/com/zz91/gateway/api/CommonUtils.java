package com.zz91.gateway.api;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

public class CommonUtils {

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

}

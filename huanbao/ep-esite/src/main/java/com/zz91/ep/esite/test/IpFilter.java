/**
 * 
 */
package com.zz91.ep.esite.test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;

/**
 * @author root
 * 
 */
public class IpFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest rq, ServletResponse rp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) rq;
		HttpServletResponse response = (HttpServletResponse) rp;
		String time = DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss SSS");
		String ip = HttpUtils.getInstance().getIpAddr(request);
		String url = request.getRequestURL().toString()+"?"+request.getQueryString();;
		String agent = request.getHeader("user-agent");
		loggerInfo(ip, time, url, agent);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	private void loggerInfo(String ip, String time, String url, String agent) {
		FileWriter fw = null;
		try {
			// 第二个参数 true 表示写入方式是追加方式
			fw = new FileWriter("/usr/logs/esite_access.log", true); 
			fw.write(time + " : " + ip + " : " + url + " : " + agent + "\r\n");
		} catch (Exception e) {
			System.out.println("书写日志发生错误：" + e.toString());
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
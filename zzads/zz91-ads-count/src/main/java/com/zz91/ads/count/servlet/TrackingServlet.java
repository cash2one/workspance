/**
 * 
 */
package com.zz91.ads.count.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz91.ads.count.domain.Monitor;
import com.zz91.ads.count.thread.ControlThread;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.http.IP2Long;
import com.zz91.util.lang.TimeHelper;

/**
 * 用于管理页面缓存功能的状态
 * 
 * 配置： <servlet> <servlet-name>cacheFragmentServlet</servlet-name>
 * <servlet-class
 * >com.zz91.util.velocity.CacheFragmentManagerServlet</servlet-class>
 * </servlet> <servlet-mapping>
 * <servlet-name>cacheFragmentServlet</servlet-name>
 * <url-pattern>/fragment/manager.servlet</url-pattern> </servlet-mapping>
 * 
 * @author root
 * 
 */
public class TrackingServlet extends HttpServlet {

	private final static Queue<Monitor> secondQueue = new ArrayBlockingQueue<Monitor>(
			100);

	private static final long serialVersionUID = 1L;
	static Set<Long> excludeIP = new HashSet<Long>();

	public TrackingServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest rq, HttpServletResponse rp)
			throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) rq;

		String ip = HttpUtils.getInstance().getIpAddr(request);
		Long ipl = IP2Long.ipToLong(ip);
		if (!excludeIP.contains(ipl)) {
			return;
		}
		
		String stop=request.getParameter("stop");

		HttpServletResponse response = (HttpServletResponse) rp;

		response.setContentType("text/html;charset=utf-8");

		long now = System.currentTimeMillis();
		if ((now - ControlThread.getLastMonitorTime()) > 1000) {
			Monitor monitor = new Monitor(DateUtil.toString(new Date(),
					"HH:mm:ss"), ControlThread.getNumTask(), TimeHelper
					.formatTime(ControlThread.getTotalTime() / 1000000),
					ControlThread.getNumQueue(), ControlThread
							.getActiveThread());

			int size = secondQueue.size();
			if (size < 100) {
				secondQueue.add(monitor);
			} else {
				secondQueue.poll();
				secondQueue.add(monitor);
			}
		}

		Monitor[] array = new Monitor[100];
		array = secondQueue.toArray(array);
		StringBuffer sb = new StringBuffer();
		
		sb.append("<html><head><title>广告日志监控</title>");
		if(!"1".equals(stop)){
			sb.append("<meta http-equiv='refresh' content='1; url='"+request.getContextPath()+request.getRequestURI()+"'>");
		}
		sb.append("</head>");
		sb.append("</body>");
		if("1".equals(stop)){
			sb.append("<br /><a href='?stop=0' >自动刷新</a><br /><br />");
		}else{
			sb.append("<br /><a href='?stop=1' >停止刷新</a><br /><br />");
		}
		sb.append("<table wdith='100%' border='1' >");
		sb.append("<tr>");
		sb.append("<th>编号</th>");
		sb.append("<th>总处理量</th>");
		sb.append("<th>总处理时间</th>");
		sb.append("<th>队列中</th>");
		sb.append("<th>工作线程</th>");
		sb.append("</tr>");
		for (int i =  array.length ; i>0 ; i--) {
			Monitor m = array[i-1];
			if(m==null){
				continue ;
			}
			sb.append("<tr>");
			sb.append("<td>").append(i).append("</td>");
			sb.append("<td>").append(m.getNumTask()).append("</td>");
			sb.append("<td>").append(m.getTotalTime()).append("</td>");
			sb.append("<td>").append(m.getNumQueue()).append("</td>");
			sb.append("<td>").append(m.getActiveThread()).append("</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		sb.append("</html>");

		PrintWriter pw = response.getWriter();
		pw.write(sb.toString());
		pw.close();
	}
	
	public void init(ServletConfig config) throws ServletException {
		String whiteIP = config.getInitParameter("excludeIP");
		if (whiteIP != null) {
			String[] ipArr = whiteIP.split(",");
			for (String ip : ipArr) {
				if (ip.contains("-")) {
					String[] ipRang = ip.split("-");
					Long rangFrom = IP2Long.ipToLong(ipRang[0]);
					Long rangTo = IP2Long.ipToLong(ipRang[1]);
					for (long i = rangFrom; i <= rangTo; i++) {
						excludeIP.add(rangFrom + i);
					}
				} else {
					excludeIP.add(IP2Long.ipToLong(ip));
				}
			}
		}
	}
}

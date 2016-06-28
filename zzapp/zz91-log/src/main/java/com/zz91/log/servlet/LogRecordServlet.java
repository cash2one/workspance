package com.zz91.log.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zz91.log.util.MongoUtil;




/**
 * 项目名称：日志统计
 * 模块描述：接收日志数据servlet
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-08-09　　　黄怀清　　　　　　　1.0.0　　　　　原插入文本改为mongDB
 */
@SuppressWarnings("serial")
public class LogRecordServlet extends HttpServlet {
	
	public static int NUM_LOG=0;
	
	final static Logger LOG= Logger.getLogger(LogReadServlet.class);
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		Map<String,Object> map =new HashMap<String, Object>();
		if (request.getParameter("appCode")!=null) {
			map.put("appCode", request.getParameter("appCode"));
		}
		if (request.getParameter("operator")!=null) {
			map.put("operator", request.getParameter("operator").toString());
		}
		if (request.getParameter("operation")!=null) {
			map.put("operation", request.getParameter("operation").toString());
		}
		if (request.getParameter("ip")!=null) {
			map.put("ip", request.getParameter("ip").toString());
		}
		if (request.getParameter("time")!=null) {
			map.put("time", request.getParameter("time").toString());
		}
		if (request.getParameter("data")!=null) {
			if(request.getParameter("data").startsWith("{")){
				map.put("data", JSONSerializer.toJSON(request.getParameter("data")));
			}else{
				map.put("data", request.getParameter("data").toString());
			}
		}
		
		try {
			
			DBObject dbo = new BasicDBObject();
			dbo.putAll(map);
			MongoUtil.getInstance().dbc.save(dbo);
			
//			if(!LogThread.logsQueue.offer(map)){
//				LOG.debug("insert error:full!");
//			}
			
		} catch (Exception e) {
			LOG.error("mongo日志记录出错:"+e.getMessage());
		}
	}

}

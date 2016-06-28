package com.zz91.log.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.zz91.log.domain.LogInfo;
import com.zz91.util.lang.StringUtils;




@SuppressWarnings("serial")
public class LogServerServlet extends HttpServlet {
	
	public static int NUM_LOG=0;
	
	final static Logger LOG= Logger.getLogger("com.zz91.log4z");

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		LogInfo log=new LogInfo();
		log.setAppCode(request.getParameter("appCode"));
		log.setOperator(request.getParameter("operator"));
		log.setOperation(request.getParameter("operation"));
		log.setIp(request.getParameter("ip"));
		String time=request.getParameter("time");
		if(time!=null){
			log.setTime(Long.valueOf(time));
		}
		String data=request.getParameter("data");
		if(data!=null){
			log.setData(StringUtils.decryptUrlParameter(data));
		}
		
		LOG.info(JSONObject.fromObject(log).toString());
		NUM_LOG++;
		
		//输入流
//		ObjectInputStream in = new ObjectInputStream(request.getInputStream());
//		LogInfo logInfo = null;
//		try {
//			logInfo = (LogInfo) in.readObject();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		in.close();
//		
//		if(logInfo!=null){
//			ControlThread.excute(new LogThread(logInfo));
//		}
//		
//		
//		boolean isSuccess = false;
//		
//		//输出流
//		response.setContentType("application/octet-stream");
//		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//		ObjectOutputStream out = new ObjectOutputStream(byteOut);
//		if(isSuccess) {
//			logInfo.setMessage("success");
//		} else {
//			logInfo.setMessage("failure");
//		}
//		out.writeObject(logInfo);
//		
//		out.flush();
//		byte[] buf = byteOut.toByteArray();
//		response.setContentLength(buf.length);
//		ServletOutputStream servletOut = response.getOutputStream();
//		
//		servletOut.write(buf);
//		servletOut.close();
		
	}

}

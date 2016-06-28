/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-10-13
 */
package com.zz91.log.service;


import java.util.Date;

import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.log.LogInfo;

/**
 * 
 * @author Leon
 *
 */
public class LogService {
	
	private static LogService _instance = null;
	

	private LogService() {

	}

	public synchronized static LogService getInstance() {
		if (_instance == null) {
			_instance = new LogService();
		}
		return _instance;
	}
	
	public boolean insertLog(LogInfo logInfo) {
		boolean flag = false;
		logInfo.setMessage(buildMsg(logInfo));
		String sql = buildSql(logInfo);
		
		DBUtils.insertUpdate("zzlog", sql);
		
		return flag;
	}
	
	private String buildSql(LogInfo logInfo) {
		StringBuffer sqlBuffer = new StringBuffer("");
		sqlBuffer.append("insert into log_info (app_code,operator,operation,operator_ip,result,gmt_operate,message,note,gmt_created,gmt_modified) values(");
		sqlBuffer.append("'"+logInfo.getAppCode()+"'"+",");
		sqlBuffer.append("'"+logInfo.getOperator()+"'"+",");
		sqlBuffer.append(logInfo.getOperation()+",");
		sqlBuffer.append("'"+logInfo.getOperatorIp()+"'"+",");
		sqlBuffer.append(logInfo.getResult()+",");
		sqlBuffer.append("'"+DateUtil.toString(logInfo.getGmtOperate(), "yyyy-MM-dd hh:mm:ss")+"'"+",");
		sqlBuffer.append("'"+logInfo.getMessage()+"'"+",");
		sqlBuffer.append("'"+logInfo.getNote()+"'"+",");
		sqlBuffer.append("now(),");
		sqlBuffer.append("now()"+")");
		return sqlBuffer.toString();
	}

	private String buildMsg(LogInfo logInfo) {
		StringBuffer msgBuffer = new StringBuffer("");
		msgBuffer.append("日志信息: ");
		msgBuffer.append("IP为" + logInfo.getOperatorIp());
		msgBuffer.append("的用户" + logInfo.getOperator() + "在");
		msgBuffer.append(logInfo.getGmtOperate().toString());
		msgBuffer.append("基于应用系统代码为:" + logInfo.getAppCode());
		msgBuffer.append("做了" + logInfo.getOperation() + "操作");
		msgBuffer.append("操作结果：" + logInfo.getResult());
		msgBuffer.append("备注信息为：" + logInfo.getNote());
		return msgBuffer.toString();
	}

	public static void main(String[] args) {
		LogService logService = LogService.getInstance();
		logService.insertLog(new LogInfo(null, "", "", 2, "192.168.1.1", 1, new Date(), "aaa", "ssss", new Date(), new Date()));
	}
	
}

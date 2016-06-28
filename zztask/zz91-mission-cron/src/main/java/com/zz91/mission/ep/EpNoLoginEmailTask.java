package com.zz91.mission.ep;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.mail.MailUtil;

/**
 * 环保30天未登陆用户发邮件提醒
 * @author kongsj
 *
 */
public class EpNoLoginEmailTask implements ZZTask{

	final static String DB = "ep";

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		Date toDate = DateUtil.getDateAfterDays(baseDate, -30);
		String to = DateUtil.toString(toDate, "yyyy-MM-dd");
		Date fromDate = DateUtil.getDateAfterDays(toDate, -1);
		String from = DateUtil.toString(fromDate, "yyyy-MM-dd");
		String sql = "select email,account from comp_account where gmt_login >= '"+from+"' and gmt_login < '"+to+"'";
		final Map<String,Object> emailMap = new HashMap<String,Object>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					emailMap.put(rs.getString(1),rs.getString(2));
				}
			}
		});
		
		// 发布邮件
		if(emailMap.size()>0){
			sendEmail(emailMap);
		}
		
		return true;
	}
	
	/**
	 * 发布邮件方法实现
	 * @param emailMap
	 */
	private void sendEmail(Map<String,Object> emailMap){
		for (String email : emailMap.keySet()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", emailMap.get(email));
			MailUtil.getInstance().sendMail("尊敬的环保网用户，您好，您已经有三十天没有登录中国环保网了", email, "ep", "ep-out30day-login", map, MailUtil.PRIORITY_TASK);
		}
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}
	
	public static void main(String[] args) {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		EpNoLoginEmailTask obj = new EpNoLoginEmailTask();
		try {
			obj.exec(DateUtil.getDate("2013-11-8", "yyyy-MM-dd"));
		} catch (ParseException e) {
		} catch (Exception e) {
		}
	}
}

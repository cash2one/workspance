package com.zz91.mission.zz91;

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

/**
 * author:kongsj date:2013-7-13
 */
public class SystemInquiryYLTask implements ZZTask {

	public final static String DB = "ast";

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		Integer i=0;
		do {
			
			String sql = "SELECT company_id,account FROM  `company_account` WHERE gmt_last_login >=  '2015-9-1' AND gmt_last_login < '2016-1-1'";
			final Map<Integer,String> map = new HashMap<Integer,String>();
			// 组装发布的信息
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						// 检测帐号是否带 slyl，如果有则表示为导入数据
						if (rs.getString(2).indexOf("slyl")==-1) {
							map.put(rs.getInt(1),rs.getString(2));
						}
					}
				}
			});
			String insertSql ="";
			for (Integer id:map.keySet()) {
				// 检索该公司是否已经获得推送，获得推送，不再推送
				sql="select id from inquiry where be_inquired_type='1' and sender_account='admin' and receiver_account= '"+map.get(id)+"' and send_time>'"+"2015-11-01"+"'";
				final Integer [] result = new Integer[1];
				DBUtils.select(DB, sql, new IReadDataHandler() {
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while (rs.next()) {
							result[0] = rs.getInt(1);
						}
					}
				});
				if (result[0]!=null&&result[0]>0) {
					continue;
				}
				
				insertSql = "INSERT INTO inquiry (" + "title,"
					+ "content," + "be_inquired_type," + "be_inquired_id,"
					+ "inquired_type," + "sender_account,"
					+ "receiver_account," + "batch_send_type,"
					+ "is_rubbish," + "send_time," + "gmt_created,"
					+ "gmt_modified" + ") " + "VALUES" + "('塑料原料频道重磅上线，打造塑料交易全新平台','<a href=\\'http://slyl.zz91.com/\\'><img src=\\'http://img0.zz91.com/zz91/polymer/images/slylad.jpg\\' /><a/>',1,"
					+ id + ",3,'admin','"
					+ map.get(id) + "',0,0,now(),now(),now())";
				DBUtils.insertUpdate(DB, insertSql);
				i++;
				System.out.println("公司帐号："+map.get(id));
			}
		} while (false);
		return true;
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

	public static void main(String[] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		SystemInquiryYLTask sit = new SystemInquiryYLTask();
		sit.exec(DateUtil.getDate("2013-7-15", "yyyy-MM-dd"));
	}

}

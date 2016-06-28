/**
 * @author kongsj
 * @date 2014年12月2日
 * 
 */
package com.zz91.mission.huzhu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

/**
 * 回复 或者 回答 整合为一条系统消息
 * 
 * @author sj
 * 
 */
public class ReplySysMessageTask implements ZZTask {

	final static String DB = "ast";

	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		long halfHourLong = baseDate.getTime() - (30 * 60 * 1000);
		Date halfDate = new Date(halfHourLong);
		// 统计出当前时间半小时前 的 回帖 或 回答
		String sql = "select bbs_post_id,account from bbs_post_reply where gmt_created >='"
				+ DateUtil.toString(halfDate, "yyyy-MM-dd HH:mm:ss")
				+ "' and gmt_created <= '"
				+ DateUtil.toString(baseDate, "yyyy-MM-dd HH:mm:ss")+"'";
		final Map<Integer, HashSet<String>> accountMap = new HashMap<Integer, HashSet<String>>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					if (rs.getString(1) == null || rs.getString(2) == null) {
						continue;
					}
					HashSet<String> accountSet = accountMap.get(rs.getString(1));
					if (accountSet == null) {
						accountSet = new HashSet<String>();
						accountSet.add(rs.getString(2));
					}
					accountMap.put(rs.getInt(1), accountSet);
				}
			}
		});
		// 根据回帖 或者 回答 组装正文
		final List<Map<String, String>> insertList = new ArrayList<Map<String, String>>();
		for (final Integer postId : accountMap.keySet()) {
			sql = "select bbs_post_category_id,company_id,account,title from bbs_post where id = "
					+ postId;
			final Map<String, String> insertMap = new HashMap<String, String>();
			insertMap.put("postId", postId.toString());// bbs_id
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						if (rs.getString(1) != null) {
							String title = rs.getString(4);
							if (title.length() > 25) {
								title = title.substring(0, 25) + "......";
							}
							// 问题
							if (rs.getInt(1) == 1) {
								insertMap.put("content", "你的问题《" + title
										+ "》有了" + accountMap.get(postId).size()
										+ "个新答案");
							}
							// 帖子
							if (rs.getInt(1) == 2) {
								insertMap.put("content", "你的帖子《" + title
										+ "》有了" + accountMap.get(postId).size()
										+ "个新回帖");
							}

							insertMap.put("account", rs.getString(3));
							insertMap.put("companyId", rs.getString(2));
						}
					}
				}

			});

			sql = "select email from company_account where company_id = "
					+ insertMap.get("companyId");
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						insertMap.put("email", rs.getString(1));
					}
				}
			});

			insertList.add(insertMap);
		}

		// insert 进入bbs_system_message
		insert(insertList);

		return true;
	}

	private void insert(List<Map<String, String>> list) {
		for (Map<String, String> map : list) {
			String sql = "INSERT INTO `bbs_system_message` "
					+ "(`company_id`, `account`, `email`, `topic`, `content`, `message_time`, `is_read`, `gmt_created`, `gmt_modified`, `url`)"
					+ " VALUES "
					+ "("
					+ map.get("companyId")
					+ ",'"
					+ map.get("account")
					+ "','"
					+ map.get("email")
					+ "', '', '"
					+ map.get("content")
					+ "', now(), 0, now(), now(), 'http://huzhu.zz91.com/viewReply"
					+ map.get("postId") + ".htm')";
			DBUtils.insertUpdate(DB, sql);
		}

	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}
	
	public static void main(String[] args) throws ParseException {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		ReplySysMessageTask obj = new ReplySysMessageTask();
		Date date = DateUtil.getDate("2014-11-27", "yyyy-MM-dd");
		DateUtil.getDateAfterDays(date, -1);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 17);
		c.set(Calendar.MINUTE, 0);
		try {
			obj.exec(c.getTime());
		} catch (Exception e) {
		}
	}

}

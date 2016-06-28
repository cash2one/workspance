package com.zz91.mission.myrc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

/**
 * 给长期已过期的普会留言
 * 
 * @author root 刷新时间为2014-01-01以前的为长期
 */
public class SysExpireProductInqueryTask implements ZZTask {
	private final static String LOG_DATE_FORMAT = "yyyy-MM-dd";
	private final static String DB = "ast";

	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String targetDate = DateUtil.toString(new Date(), LOG_DATE_FORMAT);
		// 获取总数
		int max = queryMaxSize(targetDate);
		int limit = 1000;
		for (int i = 0; i < (max / limit + 1); i++) {
			queryCompanyId(i, limit, targetDate);
		}
		return true;
	}

	private void queryCompanyId(int i, int limit, String targetDate) throws Exception {

		final Map<String, String> root = new HashMap<String, String>();
		// 获取帐号
		final Map<String, String> accountMap = new HashMap<String, String>();
		// 获取查询条件的供求（不区分普会和高会）
		final Map<Integer, Map<String, String>> maps = new HashMap<Integer, Map<String, String>>();
		// 获取是普会发布的供求信息（最终需要的数据map）
		final Map<Integer, Map<String, String>> map = new HashMap<Integer, Map<String, String>>();
		DBUtils.select("ast",
				"select id ,company_id,title from products  where check_status='1' and is_del='0' and is_pause='0' and refresh_time>'2014-01-01' and expire_time<'"
						+ targetDate + "' order by id desc limit " + (i * limit) + "," + limit,
				new IReadDataHandler() {
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while (rs.next()) {
							Map<String, String> resultMap = new HashMap<String, String>();
							resultMap.put("id", rs.getString(1));
							resultMap.put("companyId", rs.getString(2));
							resultMap.put("title", rs.getString(3));
							maps.put(rs.getInt(1), resultMap);
						}
					}
				});
		// 判断发布该供求客户是否为普会 (排除百度优化，高会,来电宝)
		for (final Integer id : maps.keySet()) {
			if (root.get(maps.get(id).get("companyId")) == null) {
				DBUtils.select(DB, "select id from crm_company_service where  apply_status=1 and gmt_end>'" + targetDate
						+ "' and company_id= " + maps.get(id).get("companyId"), new IReadDataHandler() {
							@Override
							public void handleRead(ResultSet rs) throws SQLException {
								if (!rs.next()) {
									// 将root中companyId 值置为0 表示是普会
									root.put(maps.get(id).get("companyId"), "0");
									map.put(id, maps.get(id));
								}
								while (rs.next()) {
									List<Integer> list = new ArrayList<Integer>();
									list.add(rs.getInt(1));
									if (list != null && list.size() <= 0) {
										// 将root中companyId 值置为0 表示是普会
										root.put(maps.get(id).get("companyId"), "0");
										map.put(id, maps.get(id));
									} else {
										root.put(maps.get(id).get("companyId"), "1");
									}
								}
							}
						});
			} else if (root.get(maps.get(id).get("companyId")).equals("0")) {
				map.put(id, maps.get(id));
			}

		}
		for (Integer j : map.keySet()) {
			putAccountMap(map.get(j).get("companyId"), accountMap);
			sysInquiry(map.get(j), accountMap.get(map.get(j).get("companyId")));
		}

	}

	/**
	 * 将留言信息插入到inquiry 表中
	 * 
	 * @param productMap
	 * @param account
	 *            发布供求信息的帐号
	 */
	private void sysInquiry(Map<String, String> productMap, String account) {

		String title = "您发布的信息【" + productMap.get("title") + "】已过期";
		String content = "您发布的信息【" + productMap.get("title")
				+ "】为保障您的供求信息能继续获得客户的关注，请您尽快前往生意管家商机管理-管理供求-已过期列表页刷新过期信息";
		String receiverAccount = account;
		String insertSql = "INSERT INTO inquiry (" + "title," + "content," + "be_inquired_type," + "be_inquired_id,"
				+ "inquired_type," + "sender_account," + "receiver_account," + "batch_send_type," + "is_rubbish,"
				+ "send_time," + "gmt_created," + "gmt_modified" + ") " + "VALUES" + "('" + title + "','" + content
				+ "',1," + productMap.get("companyId") + ",1,'admin','" + receiverAccount + "',0,0,now(),now(),now())";
		DBUtils.insertUpdate(DB, insertSql);

	}

	/**
	 * 获取发布供求信息客户的帐号
	 * 
	 * @param companyId
	 * @param accountMap
	 */
	private void putAccountMap(final String companyId, final Map<String, String> accountMap) {
		String sql = "select account from company_account where company_id=" + companyId;
		if (accountMap.get(companyId) == null) {
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						accountMap.put(companyId, rs.getString(1));
					}
				}
			});
		}
	}

	/**
	 * 获取满足查询添加的所有供求总数
	 * 
	 * @param targetDate
	 * @return
	 * @throws Exception
	 */
	private int queryMaxSize(String targetDate) throws Exception {
		int max = 0;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection(DB);
			rs = conn.createStatement().executeQuery(
					"select count(0) from products where check_status='1' and is_del='0' and is_pause='0' and refresh_time<'2014-01-01' and expire_time<'"
							+ targetDate + "'");
			while (rs.next()) {
				max = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Exception("更新失败 Exception:" + e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e2) {
				throw new Exception("数据库连接没有关闭 Exception:" + e2.getMessage());
			}
		}
		return max;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		long start = System.currentTimeMillis();
		SysExpireProductInqueryTask sysExpireProductInqueryTask = new SysExpireProductInqueryTask();
		sysExpireProductInqueryTask.exec(DateUtil.getDate("2016-3-18 12:12:12", "yyyy-MM-dd"));
		long end = System.currentTimeMillis();
		System.out.println("共耗时：" + (end - start));
	}
}

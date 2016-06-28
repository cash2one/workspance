package com.zz91.mission.analysis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

/**
 * 小计统计采购星级
 * 
 * @author Administrator
 *
 */
public class AnalysisTrustlogTask implements ZZTask {
	final static String DATE_FORMAT = "yyyy-MM-dd";
	final static Set<Object> set = new HashSet<Object>();

	public boolean init() throws Exception {
		return false;
	}

	/**
	 * 将trust_buy_log表和trust_company_log中查询的客户数按星级数据插入到表analysis_trust_log
	 * 
	 * @author Administrator
	 *
	 */
	public boolean exec(Date baseDate) throws Exception {
		long targetDate = DateUtil.getTheDayZero(baseDate, -1);
		Date startDate = new Date(targetDate * 1000);
		StringBuffer sql = new StringBuffer();
		sql.append("select trust_account from trust_buy_log");
		sql.append(" where gmt_created>='").append(DateUtil.toString(startDate, DATE_FORMAT)).append(" 00:00:00'");
		sql.append(" and gmt_created<'").append(DateUtil.toString(startDate, DATE_FORMAT)).append(" 23:59:59'");
		// 查询表中的账号
		DBUtils.select("ast", sql.toString(), new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet namelist) throws SQLException {
				while (namelist.next()) {
					set.add(namelist.getObject(1));
				}

			}
		});
		// 遍历有多少人
		for (Iterator<Object> iterator = set.iterator(); iterator.hasNext();) {
			final List<Object> list = new ArrayList<Object>();
			// 查询每星级的条数
			String name = (String) iterator.next();
			for (int f = 1; f <= 5; f++) {
				final Set<Object> set1 = new HashSet<Object>();
				final Set<Object> set2 = new HashSet<Object>();
				// 查询语句
				StringBuffer sql2 = new StringBuffer();
				sql2.append("select buy_id,offer_company_id from trust_buy_log");
				sql2.append(" where gmt_created>='").append(DateUtil.toString(startDate, DATE_FORMAT))
						.append(" 00:00:00'");
				sql2.append(" and gmt_created<'").append(DateUtil.toString(startDate, DATE_FORMAT))
						.append(" 23:59:59'");
				sql2.append("and trust_account ='" + name + "' and star='" + f + "'");
				// 查询采购星级
				DBUtils.select("ast", sql2.toString(), new IReadDataHandler() {
					public void handleRead(ResultSet buy) throws SQLException {
						while (buy.next()) {
							String flag="";
							if(buy.getObject(1)!=null){
								flag=buy.getObject(1).toString();
							}
							if(buy.getObject(2)!=null){
								flag=flag+buy.getObject(2).toString();
							}
							set1.add(flag);
						}
					}
				});
				list.add(set1.size());
				// 查询公司星级
				StringBuffer sql3 = new StringBuffer();
				sql3.append("select company_id,trust_account from trust_company_log");
				sql3.append(" where gmt_created>='").append(DateUtil.toString(startDate, DATE_FORMAT))
						.append(" 00:00:00'");
				sql3.append(" and gmt_created<'").append(DateUtil.toString(startDate, DATE_FORMAT))
						.append(" 23:59:59'");
				sql3.append("and trust_account ='" + name + "' and star='" + f + "'");
				DBUtils.select("ast", sql3.toString(), new IReadDataHandler() {
					public void handleRead(ResultSet com) throws SQLException {
						while (com.next()) {
							String flag="";
							if(com.getObject(1)!=null){
								flag=com.getObject(1).toString();
							}
							if(com.getObject(2)!=null){
								flag=flag+com.getObject(2).toString();
							}
							set1.add(flag);
						}
					}
				});
				list.add(set2.size());
			}
			// 截取账号
			int start = name.indexOf("(");
			int last = name.lastIndexOf(")");
			String findname = name.substring(start + 1, last);

			final SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			// 按姓名依次向表中插入数据
			String sqlInsert = "Insert into analysis_trust_log values('" + 0 + "','" + findname + "','"
					+ DateUtil.toString(startDate, DATE_FORMAT) + "','" + format.format(new Date()) + "','"
					+ format.format(new Date()) + "','" + list.get(0) + "','" + list.get(1) + "','" + list.get(2)
					+ "','" + list.get(3) + "','" + list.get(4) + "','" + list.get(5) + "','" + list.get(6) + "','"
					+ list.get(7) + "','" + list.get(8) + "','" + list.get(9) + "')";
			DBUtils.insertUpdate("ast", sqlInsert);

		}
		return true;
	}

	// 清除当前日期的信息
	public boolean clear(Date baseDate) throws Exception {
		long targetDate = DateUtil.getTheDayZero(baseDate, -1);
		Date startDate = new Date(targetDate * 1000);
		String sql = "delete from analysis_trust_log where gmt_target='" + DateUtil.toString(startDate, DATE_FORMAT)
				+ "'";
		DBUtils.insertUpdate("ast", sql);
		return true;
	}

	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		AnalysisTrustlogTask k = new AnalysisTrustlogTask();
		Date baseDate = new Date();
		k.clear(baseDate);
		k.exec(baseDate);
	}
}

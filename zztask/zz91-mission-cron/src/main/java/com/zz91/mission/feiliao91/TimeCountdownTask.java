package com.zz91.mission.feiliao91;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class TimeCountdownTask implements ZZTask {
	private final static String DB = "feiliao91";

	@Override
	public boolean init() throws Exception {
		return false;
	}

	// 超过10天　系统自动打款
	@Override
	public boolean exec(Date baseDate) throws Exception {
		String date = DateUtil.toString(
				DateUtil.getDateAfterDays(baseDate, -20), "yyyy-MM-dd");
		String sql = "select id,details,status from orders where gmt_created>"
				+ date;
		final List<Integer> resultMap = new ArrayList<Integer>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Integer id = rs.getInt(1);
					String detail = rs.getString(2);
					Integer status = rs.getInt(3);
					if (status == 4) {
						if (JSONObject.fromObject(detail).get("deliveTime") != null) {
							String time = JSONObject.fromObject(detail)
									.get("deliveTime").toString();
							long oldTime = 0;
							try {
								oldTime = DateUtil.getMillis(DateUtil.getDate(
										time, "yyyy-MM-dd HH:mm:ss"));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							long newTime = System.currentTimeMillis();
							if (newTime > oldTime) {
								if ((newTime - oldTime) >= (10 * 24 * 60 * 60 * 1000)) {
									resultMap.add(id);
								}
							}
						}
					}
				}
			}
		});

		if (resultMap != null) {
			for (final Integer id : resultMap) {
				String sql2 = "select details,sell_company_id,order_no from orders where id="
						+ id;
				DBUtils.select(DB, sql2, new IReadDataHandler() {
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while (rs.next()) {
							String detail = rs.getString(1);
							Integer sellCompanyId = rs.getInt(2);
							String orderNo = rs.getString(3);
							if (JSONObject.fromObject(detail).get(
									"orderTotalPay") != null) {
								payMoney(
										sellCompanyId,
										JSONObject.fromObject(detail).get(
												"orderTotalPay"), id, orderNo);
							}
						}
					}
				});
			}
		}
		return true;
	}

	private void payMoney(final Integer sellCompanyId, final Object object,
			final Integer id, final String orderNo) {
		String sql3 = "select sum_money from company_account where company_id="
				+ sellCompanyId;
		if (sellCompanyId != null) {
			DBUtils.select(DB, sql3, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						Float money = rs.getFloat(1);
						money = money + Float.valueOf(object.toString());
						SetMoney(sellCompanyId, money);
						updateStatus(id);
						insertTradeLog(sellCompanyId, money, 1, orderNo);
					}
				}
			});
		}
	}

	// 钱的流水账信息
	private void insertTradeLog(Integer company, Float money, Integer status,
			String orderNo) {
		String sql = "insert into trade_log(company_id,status,money,gmt_created,gmt_modified,remark) values("
				+ company
				+ ","
				+ status
				+ ","
				+ money
				+ ",now(),now(),'"
				+ orderNo + "')";
		DBUtils.insertUpdate(DB, sql);
	}

	private void updateStatus(Integer id) {
		String sql5 = "update orders set gmt_modified=now(),status=66 where id="
				+ id;
		DBUtils.insertUpdate(DB, sql5);
	}

	private void SetMoney(Integer sellCompanyId, Float money) {
		String sql4 = "update company_account set gmt_modified=now(),sum_money="
				+ money + "where company_id=" + sellCompanyId;
		DBUtils.insertUpdate(DB, sql4);
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	public static void main(String[] args) throws Exception {

		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		long start = System.currentTimeMillis();

		TimeCountdownTask task = new TimeCountdownTask();
		task.exec(new Date());

		long end = System.currentTimeMillis();
		System.out.println("共耗时：" + (end - start));
	}

}

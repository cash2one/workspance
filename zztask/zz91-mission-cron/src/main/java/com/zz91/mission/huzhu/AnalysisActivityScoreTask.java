package com.zz91.mission.huzhu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class AnalysisActivityScoreTask implements ZZTask {

	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		AnalysisActivityScoreTask aast = new AnalysisActivityScoreTask();
		aast.clear(new Date());
		aast.exec(new Date());

	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		// String time = DateUtil.toString(baseDate, "yyyy-MM-dd");
		String startTime = "2014-05-04";
		String endTime = "2014-05-17";
		Map<Integer, Integer> map = getCompanyId(startTime, endTime);
		// 获取到的积分score
		Integer score = 0;
		// 获取到的帐号company_aacount
		String account = "";
		String companyName = "";
		for (Integer key : map.keySet()) {
			// 获取注册时间,获取年份
			Integer inyear = getInYear(getRegTime(key));
			// 判断年份
			if (inyear > 0) {
				score = 100 * inyear;
			} else {
				score = getbbsScore(key, startTime) * map.get(key);
			}
			account = getCompanyAccount(key);
			companyName = getCompanyName(key);
			if (score > 0) {

				System.out.println(account + " " + key + " " + companyName
						+ " " + score);
				// 插入日志
				insertLog(key, score, account);
			}
		}
		return true;
	}

	// 日志内容
	public void insertLog(Integer companyId, Integer score, String account) {
		String remark = "老客户积分馈赠活动";
		String sql = "INSERT INTO bbs_score (bbs_post_id,qa_post_id,bbs_reply_id,qa_reply_id,company_id,post_type,reply_type,score,remark,company_account,gmt_created,gmt_modified)"
				+ "VALUE(0,0,0,0,'"
				+ companyId
				+ "',0,0,'"
				+ score
				+ "','"
				+ remark + "','" + account + "',now(),now())";
		// System.out.println(sql);
		DBUtils.insertUpdate("ast", sql);
	}

	// 获取公司名
	public String getCompanyName(Integer companyId) {
		String sql = "select name from company where id='" + companyId + "'";
		final List<String> list = new ArrayList<String>();
		DBUtils.select("ast", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}
		});
		String str = list.get(0);
		return str;
	}

	// 获取companyAccount
	public String getCompanyAccount(Integer companyId) {
		String sql = "select account from company_account where company_id='"
				+ companyId + "'";
		final List<String> list = new ArrayList<String>();
		DBUtils.select("ast", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}
		});
		String str = list.get(0);
		return str;
	}

	// 未满年的，获取公司自注册以来的所有积分
	public Integer getbbsScore(Integer companyId, String time) {
		String sql = "select sum(score) from bbs_score where company_id='"
				+ companyId + "' and gmt_created<'" + time + "'";
		final List<Integer> list = new ArrayList<Integer>();
		DBUtils.select("ast", sql, new IReadDataHandler() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getInt(1));
				}
			}
		});
		Integer sum = 0;
		if (list.size() != 0) {
			sum = list.get(0);
			if (sum < 0) {
				return 0;
			}
		}
		return sum;
	}

	// 获取公司的注册时间
	public String getRegTime(Integer companyId) {
		String sql = "select regtime from company where id='" + companyId + "'";
		final List<String> list = new ArrayList<String>();
		DBUtils.select("ast", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}
		});
		String st = list.get(0).replace(".0", "");
		return st;
	}

	// 查询活动期间发贴成功的companyId
	public Map<Integer, Integer> getCompanyId(String startTime, String endTime) {
		String sql = "select company_id,count(company_id) from bbs_post where post_time>='"
				+ startTime
				+ "' and post_time<='"
				+ endTime
				+ "' and check_status=1 and is_del=0 and (bbs_post_category_id>11 or bbs_post_category_id<11)  group by company_id";
		// final List<Map<String, Integer>> list = new ArrayList<Map<String,
		// Integer>>();
		final Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		DBUtils.select("ast", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					if (rs.getInt(1) == 981408 || rs.getInt(1) == 860208
							|| rs.getInt(1) == 976082 || rs.getInt(1) == 644995
							|| rs.getInt(1) == 799451 || rs.getInt(1) == 840501) {
					} else {
						map.put(rs.getInt(1), rs.getInt(2));
					}
				}
			}
		});
		return map;
	}

	// 计算客户注册到活动满几年
	public Integer getInYear(String time) throws ParseException {
		// String begin = "2013-12-01";
		String after = "2014-01-01";
		Integer[] beginTime = getYMD(time);
		Integer[] afterTime = getYMD(after);
		if (afterTime[2] - beginTime[2] < 0) {
			// 30天的小月
			if (afterTime[1] == 4 || afterTime[1] == 6 || afterTime[1] == 9
					|| afterTime[1] == 11) {
				afterTime[2] = afterTime[2] + 30;
				afterTime[1] = afterTime[1] - 1;

			} else if (afterTime[1] == 2) {
				// 判断闰年与平年的2月的天数
				if ((afterTime[0] % 4 == 0 && afterTime[0] % 1 != 0)
						|| (afterTime[0] % 400 == 0)) {
					// 29天的2月
					afterTime[2] = afterTime[2] + 29;
					afterTime[1] = afterTime[1] - 1;
				} else {
					// 28天的2月
					afterTime[2] = afterTime[2] + 28;
					afterTime[1] = afterTime[1] - 1;
				}

			} else {
				// 31天的大月
				afterTime[2] = afterTime[2] + 31;
				afterTime[1] = afterTime[1] - 1;

			}
			if (afterTime[1] == 0) {
				afterTime[1] = 12;
				afterTime[0] = afterTime[0] - 1;
			}
			Integer in = afterTime[0] - beginTime[0];
			return in;
		} else {
			if (afterTime[1] - beginTime[1] < 0) {
				afterTime[1] = afterTime[1] + 12;
				afterTime[0] = afterTime[0] - 1;
			}
			Integer in = afterTime[0] - beginTime[0];
			return in;
		}
	}

	// 获取年、月、日
	public Integer[] getYMD(String time) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = format.parse(time);
		Calendar c = Calendar.getInstance();
		c.setTime(d1);
		Integer[] in = new Integer[3];
		in[0] = c.get(Calendar.YEAR);
		in[1] = c.get(Calendar.MONTH) + 1;
		in[2] = c.get(Calendar.DATE);
		return in;
	}

	// 删除老客户积分馈赠活动所得的积分
	public void deleteScore() {
		String remark = "老客户积分馈赠活动";
		String sql = "delete from bbs_score where remark='" + remark + "'";
		DBUtils.insertUpdate("ast", sql);
		// System.out.println(sql);

	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		deleteScore();
		return true;
	}

}

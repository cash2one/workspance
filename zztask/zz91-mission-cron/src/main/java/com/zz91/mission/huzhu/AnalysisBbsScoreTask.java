package com.zz91.mission.huzhu;

import java.sql.ResultSet;
import java.sql.SQLException;
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

public class AnalysisBbsScoreTask implements ZZTask {

	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		AnalysisBbsScoreTask abst = new AnalysisBbsScoreTask();
		Date date = DateUtil.getDate(new Date(), "yyyy-MM-dd");
		abst.exec(date);

	}

	// 查出昨天哪些公司的积分发生了变化，并检索出这些公司的companyId
	public List<Integer> findCompanyScore(String startdDate, String endDate) {
		final List<Integer> list = new ArrayList<Integer>();
		final Set<Integer> set = new HashSet<Integer>();
		String sql = "select company_id from bbs_score where gmt_created>='"
				+ startdDate + "' and gmt_created<'" + endDate + "'";
		DBUtils.select("ast", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					set.add(rs.getInt(1));
				}
			}

		});
		// set只是为了过滤重复元素
		for (Iterator<Integer> it = set.iterator(); it.hasNext();) {
			list.add(it.next());
		}

		return list;
	}

	// 把积分发生变化的公司，进行积分统计
	public Integer caculateScore(Integer companyId) {
		String sql = "select sum(score),company_id from bbs_score where company_id='"
				+ companyId + "'";
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

	// 统计好的积分更新到bbs_user_profile
	public void updateIntegral(Integer integral, Integer companyId) {
		String sql = "update bbs_user_profiler set integral='" + integral
				+ "' where  company_id='" + companyId + "' ";
		DBUtils.insertUpdate("ast", sql);
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		// 获取前一天的时间
		String startdDate = DateUtil.toString(
				DateUtil.getDateAfterDays(baseDate, -1), "yyyy-MM-dd");
		String endDate = DateUtil.toString(baseDate,"yyyy-MM-dd");
		// 获取公司结果集
		List<Integer> list = findCompanyScore(startdDate, endDate);
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				int companyId = list.get(i);
				updateIntegral(caculateScore(companyId), companyId);
			}
		} 
		return true;
		
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}

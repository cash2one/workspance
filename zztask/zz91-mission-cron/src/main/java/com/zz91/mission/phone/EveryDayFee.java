package com.zz91.mission.phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class EveryDayFee implements ZZTask{
	
	final static String DB = "ast";

	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		// TODO 先使用一个月为一单位，上线后，考虑改为一天一查
		String to = DateUtil.toString(baseDate, "yyyy-MM-01");
		Date tempDate = DateUtil.getDateAfterMonths(baseDate, -1);
		String from = DateUtil.toString(tempDate, "yyyy-MM-01");
		// 获取所有上月有话单的来电宝用户
		final Map<Integer, Object> companyMap = new HashMap<Integer, Object>();
		String sql = "select company_id from phone_log where start_time >='" + from+"' and start_time <'"+to+"'" ;
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					companyMap.put(rs.getInt(1), "");
				}
			}
		});
		// 循环一个月
		Date date = DateUtil.getDate(from, "yyyy-MM-dd");
		while (date.getTime()<DateUtil.getDate(to, "yyyy-MM-dd").getTime()) {
			// 计算每个公司的话费
			for (Integer companyId :companyMap.keySet()) {
				float yestoday = getYestodayFee(companyId, date);
				float today = getTodayFee(companyId, date);
				// 必须是今日消费大于昨日，不然出大事了
				if (yestoday >=today) {
					continue;
				}
				// 计算费用和统计日期 出现了消费
				countFee(companyId,yestoday,today,date);
			}
			date = DateUtil.getDateAfterDays(date, 1);
		}
		
		return true;
	}
	
	private void countFee(Integer companyId,float yestoday,float today,Date date){
		String sql = "SELECT id,fee FROM `phone_cost_service` where company_id = "+companyId+"";
		final Map<Integer, Float> resultMap= new LinkedHashMap<Integer, Float>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					resultMap.put(rs.getInt(1),rs.getFloat(2));
				}
			}
		});
		
//		float cost = today - yestoday;
		float cfee = 0f;
		int tempId = 0;
		for (Integer id : resultMap.keySet()) {
			cfee = cfee + resultMap.get(id);
			// 新开通用户，第一条记录 记下 消费日期
			if (yestoday==0f) {
				sql = "update phone_cost_service set gmt_modified=now(),gmt_renew = '"+DateUtil.toString(date, "yyyy-MM-dd")+"' where id = "+id;
				DBUtils.insertUpdate(DB, sql);
				break;
			}
			// 今日消费大于余下金额，启用最新的充值
			if (cfee>yestoday&&cfee<today) {
				sql = "update phone_cost_service set gmt_modified=now(), gmt_zero = '"+DateUtil.toString(date, "yyyy-MM-dd")+"' where id = "+id;
				DBUtils.insertUpdate(DB, sql);
				tempId = id;
				continue;
			}
			// 上一条记录标零， 下一条记录开始使用
			if (tempId>0) {
				sql = "update phone_cost_service set gmt_modified=now(),gmt_renew = '"+DateUtil.toString(date, "yyyy-MM-dd")+"' where id = "+id;
				DBUtils.insertUpdate(DB, sql);
				break;
			}
		}
	}
	
	// 获取昨日的消费
	private float getYestodayFee(Integer companyId,Date date){
		final float result[] = new float[]{0f,0f,0f};
		// 到昨日的所有电话消费
		String sql = "select sum(call_fee) from phone_log where state = 1 and UNIX_TIMESTAMP(start_time) < UNIX_TIMESTAMP('"+DateUtil.toString(date, "yyyy-MM-dd")+"') and company_id="+companyId;
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					result[0] = rs.getFloat(1);
				}
			}
		});
		
		// 到昨日的查看未接电话费用
		sql = "SELECT sum(click_fee) from  phone_call_click_fee where company_id = "+companyId+" and UNIX_TIMESTAMP(gmt_created) < UNIX_TIMESTAMP('"+DateUtil.toString(date, "yyyy-MM-dd")+"')";
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					result[1] = rs.getFloat(1);
				}
			}
		});
		
		// 到昨日的所有点击查看联系方式费用
		sql = "select sum(click_fee) from phone_click_log where company_id = "+companyId +" and UNIX_TIMESTAMP(gmt_created) < UNIX_TIMESTAMP('"+DateUtil.toString(date, "yyyy-MM-dd")+"')";
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					result[2] = rs.getFloat(1);
				}
			}
		});
		
		return result[0]+result[1]+result[2];
	}
	
	// 获取今日的消费
	private float getTodayFee(Integer companyId,Date date){
		final float result[] = new float[]{0f,0f,0f};
		// 到今日的所有电话消费
		String sql = "select sum(call_fee) from phone_log where state = 1 and UNIX_TIMESTAMP(start_time) < UNIX_TIMESTAMP('"+DateUtil.toString(DateUtil.getDateAfterDays(date, 1), "yyyy-MM-dd")+"') and company_id="+companyId;
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					result[0] = rs.getFloat(1);
				}
			}
		});
		
		// 到昨日的查看未接电话费用
		sql = "SELECT sum(click_fee) from  phone_call_click_fee where company_id = "+companyId+" and UNIX_TIMESTAMP(gmt_created) < UNIX_TIMESTAMP('"+DateUtil.toString(DateUtil.getDateAfterDays(date, 1), "yyyy-MM-dd")+"')";
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					result[1] = rs.getFloat(1);
				}
			}
		});
		
		// 到昨日的所有点击查看联系方式费用
		sql = "select sum(click_fee) from phone_click_log where company_id = "+companyId +" and UNIX_TIMESTAMP(gmt_created) < UNIX_TIMESTAMP('"+DateUtil.toString(DateUtil.getDateAfterDays(date, 1), "yyyy-MM-dd")+"')";
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					result[2] = rs.getFloat(1);
				}
			}
		});
		return result[0]+result[1]+result[2];
	}
	

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}
	
	public static void main(String [] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init("file:/mnt/tools/config/db/db-zztask-jdbc.properties");
		long start=System.currentTimeMillis();
		EveryDayFee obj=new EveryDayFee();
		obj.exec(DateUtil.getDate("2016-06-01", "yyyy-MM-dd"));
		long end=System.currentTimeMillis();
		System.out.println("共耗时："+(end-start));
	}

}

package com.zz91.mission.zz91;

import java.math.BigDecimal;
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
import com.zz91.util.lang.StringUtils;

public class RecoverNewLDBTimeTask implements ZZTask{
	final static String DB = "ivr";
	public static void main(String[] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		RecoverNewLDBTimeTask task = new RecoverNewLDBTimeTask();
		task.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}
	@Override
	public boolean exec(Date baseDate) throws Exception {
		// 搜出时间段的所有记录
		List<Map<String, String>> list = getRecord();
		for (Map<String, String> map : list) {
			Date time = DateUtil.getDate(map.get("startDate"),
					"yyyy-MM-dd HH:mm:ss");
			//标识
			String callSn= map.get("caller") + String.valueOf(time.getTime());
			// 通话结束时间为空的话，补接听
			if (StringUtils.isEmpty(map.get("endDate"))) {
				map.put("endDate", map.get("startDate"));
			}
			//通话开始时间，未接用发起通话的开始时间（startDate），已接用应答时间（answerDate）
			if(StringUtils.isNotEmpty(map.get("answerDate"))){
				map.put("startDate", map.get("answerDate"));
			}
			
		    //重新计算通话时间，通话费用
			// 检索400号码
			String tel = getTel(map.get("exten"));
				map.put("exten", tel);
				// tel不为空
				if (StringUtils.isNotEmpty(tel)) {
				// 获取公司id
				Integer companyId = getCompanyId(tel);
				if (companyId == null) {
					companyId = 0;
				}
				// 获取每分钟的话费，并算话费
				String fee = getTelFee(companyId);
				double callFee = 0;
				// 已接电话，J计算分钟数
				Date start = DateUtil.getDate(map.get("startDate"),
						"yyyy-MM-dd HH:mm:ss");
				Date end = DateUtil.getDate(map.get("endDate"),
						"yyyy-MM-dd HH:mm:ss");
				long t = end.getTime() - start.getTime();
				Integer timeS = (int) (t / 1000);
				if (timeS % 60 == 0) {
					timeS = timeS / 60;
				} else {
					timeS = timeS / 60 + 1;
				}
				Double f = Double.valueOf(timeS);
				if (StringUtils.isNotEmpty(fee)) {
					callFee = Double.valueOf(fee) * f;
				} else {
					callFee = 4.9 * f;
				}
				BigDecimal db = new BigDecimal(callFee);
				db = db.setScale(2, BigDecimal.ROUND_HALF_UP);
				map.put("billable", String.valueOf(db));
			//更新
				update(map.get("billable"),map.get("startDate"),callSn);
			}
		}
		return true;
	}
	// 获取每分钟的话费
		public String getTelFee(Integer companyId) {
			String sql = "select tel_fee from phone_cost_service where company_id="
					+ companyId + " and is_lack = '0' order by id asc limit 1";
			final List<String> list = new ArrayList<String>();
			DBUtils.select("ast", sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						list.add(rs.getString(1));
					}
				}
			});
			if (list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		}
	// 根据400号码检索公司id
	public Integer getCompanyId(String tel) {
		String sql = "select company_id from phone where tel='" + tel + "'";
		final List<Integer> list = new ArrayList<Integer>();
		DBUtils.select("ast", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getInt(1));
				}
			}
		});
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	// 根据called检索400号码
		public String getTel(String called) {
			String sql = "select tel from phone_library where called='" + called
					+ "'";
			final List<String> list = new ArrayList<String>();
			DBUtils.select("ast", sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						list.add(rs.getString(1));
					}
				}
			});
			if (list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		}
	//更新
	public void update(String fee,String answerTime,String callSn){
		String sql="update phone_log set call_fee='"+fee+"',start_time='"+answerTime+"' where call_sn='"+callSn+"' ";
		DBUtils.insertUpdate("ast", sql);
		System.out.println(sql);
	}
	//获取到铁通400有史以来所有的通话记录
	public List<Map<String, String>> getRecord() {
		String state="ANSWERED";
		String sql = "SELECT caller,startDate,endDate,disPosition,exten,answerDate,called FROM T_CDR where disPosition='"+state+"'";
		final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("caller", rs.getString(1));
					map.put("startDate", rs.getString(2));
					map.put("endDate", rs.getString(3));
					map.put("disPosition", rs.getString(4));
					map.put("exten", rs.getString(5));
					map.put("answerDate", rs.getString(6));
					map.put("called", rs.getString(7));
					list.add(map);
				}
			}
		});
		for(int i=0;i<list.size();i++){
			if(StringUtils.isEmpty(list.get(i).get("called"))){
				list.remove(i);
			}
		}
		return list;
	}

	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}

package com.zz91.mission.front;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONObject;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.log.LogUtil;

/**
 * CS客户自动掉公海处理任务
 * 
 * @author kongsj
 * 
 */
public class AutoOutCRM implements ZZTask {

	private static final String DB = "ast";
	private static final String DBS="zzwork";
	private static final Integer SIZE = 50;
	private static final String ONE_MONTH_NOVISIT = "一个月未联系，自动掉公海";
	private static final String THREE_MONTH_ISEXPIRED = "过期三个月，自动掉公海";

	// final static List<Map<String, Object>> OUT_LIST = new
	// ArrayList<Map<String, Object>>();

	@Override
	public boolean init() throws Exception {
		return false;
	}

//	public int  quit() {
//		
//		
//	}
	
	
	@Override
	public boolean exec(Date baseDate) throws Exception {
		// 一个月未联系客户自动掉公海
		Date omDate = DateUtil.getDateAfterDays(baseDate, -31);
		oneMonthOut(omDate);

		// 过期三个月自动掉公海
		Date tmDate = DateUtil.getDateAfterDays(baseDate, -91);
		threeMonthOut(tmDate);

		// if(OUT_LIST.size()>0){
		// Map<String, Object> dataMap=new HashMap<String, Object>();
		// dataMap.put("logList", OUT_LIST);
		// String date = DateUtil.toString(new Date(), "yyyy年MM月dd日");
		// dataMap.put("date",date);
		// MailUtil.getInstance().sendMail("[CRM]"+date+"掉公海的公司以及客服信息",
		// "zz91.crm.auto.out@asto.mail", null,null, "zz91",
		// "zz91-crm-auto-out", dataMap, MailUtil.PRIORITY_TASK);
		// }
		return true;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	private Integer getTotal(Date baseDate, String sql) {
		final Integer[] total = { 0 };
		DBUtils.select(DB, sql, new IReadDataHandler() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					total[0] = rs.getInt(1);
				}
			}
		});
		return total[0];
	}

	private Integer getEnd(Integer total) {
		Integer end = 0;
		if (total % SIZE == 0) {
			end = total / SIZE;
		} else {
			end = total / SIZE + 1;
		}
		return end;
	}

	private void outPub(List<Integer> list, final Integer i) {
		final List<Map<String, Object>> logList = new ArrayList<Map<String, Object>>();
		for (final Integer companyId : list) {
			// 检索CRM库，详细信息
			String sql = "SELECT cs_account,gmt_visit,gmt_next_visit_phone,gmt_next_visit_email FROM crm_cs where company_id ="
					+ companyId;
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("companyId", companyId);
						map.put("account", rs.getString(1));
						map.put("gmtVisit", rs.getString(2));
						map.put("gmtNextVisitPhone", rs.getString(3));
						map.put("gmtNextVisitEmail", rs.getString(4));
						if (i == 1) {
							map.put("summary", ONE_MONTH_NOVISIT);
						} else if (i == 3) {
							map.put("summary", THREE_MONTH_ISEXPIRED);
						}
						logList.add(map);
					}
				}
			});	
		}
		for (Map<String, Object> logMap : logList) {
			JSONObject js = JSONObject.fromObject(logMap);
			LogUtil.getInstance().mongo("system", "auto_out_pub",
					"127.0.0.1", js.toString());
			// OUT_LIST.add(js);
			// 记录掉公海
			logOut(js.getString("companyId"),js.getString("account"));
			
			//丢公海
			DBUtils.insertUpdate(DB, "delete FROM crm_cs where company_id="
					+ js.getString("companyId"));
		}
		
	}

	/**
	 * 离职人员公海客户排除
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Integer> Thejudge( List<String> lista,Map map) {
		// 查询离职员工数组list
		final List<String> list = new ArrayList<String>();
		for (final String account : lista) {
			String sql = "select account from staff  where status=2  and  account='"
					+ account + "'";
			DBUtils.select(DBS, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						list.add(rs.getString(1));
					}
				}
			});
		}
		// 离职客服下关联的客户数组
		final List<Integer> lists = new ArrayList<Integer>();
		for (final String account : list) {
			String sqls = "select company_id from crm_cs  where cs_account ='"
					+ account + "'";
			DBUtils.select(DB, sqls, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						lists.add(rs.getInt(1));
					}
				}
			});
		}
		// 离职客服一月未联系客户去除map内除去离职人员，求得需掉公海
		List<Integer> listt = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
		 Collection<String> properties2 = map.values();
			   while(properties2.contains(list.get(i))){
			   properties2.remove(list.get(i));
		}
		}
		//将map的key(company_id)给予单独listt
		 Iterator it = map.keySet().iterator();
	        while (it.hasNext()) {
	            Integer key = (Integer) it.next();
	            listt.add(key);
	        }
		return listt;
	}
	
	private void oneMonthOut(Date date) {
		// 查询一个月未联系客户
		final List<Integer> list = new ArrayList<Integer>();
		// 新增存放员工account数组
		final List<String> lista = new ArrayList<String>();
		//MAP取值适用
		final  Map<Integer,String> map =new HashMap<Integer,String> ();
		String sql = "select c.company_id , cs.cs_account from crm_cs_profile c left join crm_cs cs on c.company_id=cs.company_id inner join company co on c.company_id =co.id and co.membership_code='10051000' where '"
				+ DateUtil.toString(date, "yyyy-MM-dd") + "' >= cs.gmt_visit";
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
//					list.add(rs.getInt(1));
//					lista.add(rs.getString(2));
					map.put(rs.getInt(1), rs.getString(2));
				}
			}
		});
		// 排除
		List<Integer> listc = new ArrayList<Integer>();
		for (Integer li : list) {
			if (!getJbzst(li)) {
				listc.add(li);
			}
		}
		
//		DBUtils.select(DB, sql, new IReadDataHandler() {
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				while (rs.next()) {
//				}
//			}
//		});
		for(int i =0;i< listc.size();i++){
		}
		// 剔除查询到数组中重复用于离职人员查询
		HashSet<String> h = new HashSet<String>(lista);
		lista.clear();
		lista.addAll(h);
		// 离职人员公海排除方法调用
		List<Integer> listt = Thejudge(lista,map);
		// 遍历 companyId搜索cs账户信息，记录日志，并执行该客户掉公害
		outPub(listt, 1);
	}

	private void threeMonthOut(Date date) {
		String sql = "select count(0) from crm_company_service cs inner join company c on cs.company_id = c.id and c.membership_code = '10051000' where cs.crm_service_code='1000' and cs.gmt_end='"
				+ DateUtil.toString(date, "yyyy-MM-dd") + "'";
		Integer total = getTotal(date, sql);
		Integer end = getEnd(total);
		for (int i = 1; i <= end; i++) {
			// 查询过期三个月客户
			final List<Integer> list = new ArrayList<Integer>();
			sql = "select cs.company_id from crm_company_service cs inner join company c on cs.company_id = c.id and c.membership_code = '10051000' where cs.crm_service_code='1000' and cs.gmt_end='"
					+ DateUtil.toString(date, "yyyy-MM-dd")
					+ "' and apply_status=1 limit "
					+ (i - 1)
					* SIZE
					+ ","
					+ SIZE;
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						list.add(rs.getInt(1));
					}
				}
			});
			// 遍历 companyId搜索cs账户信息，记录日志，并执行该客户掉公害
			outPub(list, 3);
		}
	}

	/**
	 * 记录掉公海日志
	 */
	private void logOut(String companyId,String oldCsAccount) {
		String sql = "INSERT INTO crm_out_log"
				+ "(company_id,old_csAccount,operator,status,gmt_created,gmt_modified)"
				+ "VALUES (" + companyId + ",'"+oldCsAccount+"',0,0,now(),now())";
		DBUtils.insertUpdate(DB, sql);
	}

	/**
	 * 
	 * 判断该公司有无简版再生通服务
	 */
	public boolean getJbzst(Integer companyId) {

		String sql = "select count(*) from crm_company_service where company_id='"
				+ companyId
				+ "' and crm_service_code='1006' and gmt_end>='"
				+ DateUtil.toString(new Date(), "yyyy-MM-dd") + "'";
		final List<Integer> list = new ArrayList<Integer>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getInt(1));
				}
			}

		});
		if (list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
//		DBPoolFactory.getInstance().init(
//				"file:/home/zhengruipeng/dev/zztask/zz91-mission-cron/target/test-classes/db-zztask-jdbc.properties");
		LogUtil.getInstance().init("web.properties");
		AutoOutCRM task = new AutoOutCRM();
		task.exec(DateUtil.getDate("2016-12-30", "yyyy-MM-dd"));
	}
}

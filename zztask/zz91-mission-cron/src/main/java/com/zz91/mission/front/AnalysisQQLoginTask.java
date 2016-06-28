package com.zz91.mission.front;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

/**
 * @author kongsj
 * @date 2012-12-20
 */
public class AnalysisQQLoginTask implements ZZTask {

	private final static String DB = "ast";

	private static String LOG_FILE = "/mnt/data/log4z/run.";
	private final static String LOG_DATE_FORMAT = "yyyy-MM-dd";

	private final static String APP_CODE="appCode";
	private final static String APP_CODE_VALUE="zz91";
	private final static String OPERATION = "operation";
	private final static String OPERATOR = "operator";
	private final static String OPERATOR_VALUE = "qq_login";

	@Override
	public boolean clear(Date baseDate) throws Exception {
		String targetDate = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), LOG_DATE_FORMAT);
		return DBUtils.insertUpdate(DB,"delete from analysis_log where gmt_target='" + targetDate + "' and operator='"+OPERATOR_VALUE+"' ");
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String targetDate = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), LOG_DATE_FORMAT);
		
		// 统计数据库中真实绑定、注册的数据
		analysisRegData(baseDate);

		Map<String, Integer> resultMap = new HashMap<String, Integer>();

		BufferedReader br = new BufferedReader(new FileReader(LOG_FILE+ targetDate));

		String line;
		Integer num = null;
		while ((line = br.readLine()) != null) {
			JSONObject jobj = JSONObject.fromObject(line);
			do{
				// 是否是qq登录 
				if (!OPERATOR_VALUE.equalsIgnoreCase(jobj.getString(OPERATOR))) {
					break;
				}
				// 是否是zz91
				if(!APP_CODE_VALUE.equalsIgnoreCase(jobj.getString(APP_CODE))){
					break;
				}
				String opertionName = jobj.getString(OPERATION);
				num = resultMap.get(opertionName);
				if (num == null) {
					resultMap.put(opertionName, 1);
				} else {
					resultMap.put(opertionName, ++num);
				}
			}while(false);
		}
		br.close();
		for (String operatorName : resultMap.keySet()) {
			saveToDB(operatorName, resultMap.get(operatorName), targetDate);
		}
		return true;
	}
	
	private void saveToDB(String operationName, Integer loginCount,
			String targetDate) {
		String sql = "insert into analysis_log(operator, operation, log_total, gmt_target, gmt_created, gmt_modified) values('"
				+ OPERATOR_VALUE
				+ "','"
				+ operationName
				+ "',"
				+ loginCount
				+ ", '" + targetDate + "',  now(),now())";
		DBUtils.insertUpdate(DB, sql);
	}
	
	private void analysisRegData(Date targetDate){
		String bindName = "qq_login_bind_db";
		String regName = "qq_login_register_db";
		
		
		String to = DateUtil.toString(targetDate, "yyyy-MM-dd");
		String from = DateUtil.toString(DateUtil.getDateAfterDays(targetDate, -1), "yyyy-MM-dd");
		String sql = "SELECT count(0) FROM `company` "
				+ "where regfrom_code in(10031028,10031033,10031034,10031036) and gmt_created >= '"+from+"' and '"+to+"' > gmt_created";
		final Set<Integer> bind = new HashSet<Integer>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					bind.add(rs.getInt(1));
				}
			}
		});
		int countBind = 0;
		for (int i:bind) {
			countBind = i;
		}
		saveToDB(bindName, countBind, from);
		
		
		
		sql = "SELECT count(0) FROM `company` "
				+ "where regfrom_code in(10031026,10031031,10031032,10031035) and gmt_created >= '"+from+"' and '"+to+"' > gmt_created";
		final Set<Integer> reg = new HashSet<Integer>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					reg.add(rs.getInt(1));
				}
			}
		});
		int countReg = 0;
		for (int i:reg) {
			countReg = i;
		}
		saveToDB(regName, countReg, from);
	}

	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");

		long start = System.currentTimeMillis();
		AnalysisQQLoginTask analysis = new AnalysisQQLoginTask();

		AnalysisQQLoginTask.LOG_FILE = "/usr/data/log4z/zz91/run.";
		analysis.clear(DateUtil.getDate("2012-12-31", "yyyy-MM-dd"));
		analysis.exec(DateUtil.getDate("2012-12-31", "yyyy-MM-dd"));
		long end = System.currentTimeMillis();
		System.out.println("共耗时：" + (end - start));
	}

}

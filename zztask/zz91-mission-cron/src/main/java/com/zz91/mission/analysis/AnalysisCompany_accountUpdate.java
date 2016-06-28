package com.zz91.mission.analysis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class AnalysisCompany_accountUpdate implements ZZTask {

	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		final String DATE_FORMAT = "yyyy-MM-dd";
		final List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		long targetDate = DateUtil.getTheDayZero(baseDate, -1);
		Date startDate = new Date(targetDate * 1000);
		StringBuffer sql = new StringBuffer();
		sql.append("select company_id,gmt_last_login,num_login from company_account");
		sql.append(" where gmt_last_login>='").append(DateUtil.toString(startDate, DATE_FORMAT)).append(" 00:00:00'");
		sql.append(" and gmt_last_login<'").append(DateUtil.toString(startDate, DATE_FORMAT)).append(" 23:59:59'");
		DBUtils.select("ast", sql.toString(), new IReadDataHandler() {
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Map<String,String> map = new HashMap<String,String>();
					map.put("company_id", rs.getString(1));
					map.put("gmt_last_login", rs.getString(2));
					map.put("num_login", rs.getString(3));
					list.add(map);
				}
			}
		});
		for(Map<String,String> map : list){
			if("0".equals(map.get("num_login"))){
				continue;
			}
			update(map);
		}
		return true;
	}
	public void update(Map<String,String> map){
		String sql = "update crm_cs_profile set gmt_last_login='" + map.get("gmt_last_login") + "' ,num_login= '"+ map.get("num_login") +"'  where company_id=" + map.get("company_id");
		DBUtils.insertUpdate("ast", sql);
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	public static void main(String[] args) {
		DBPoolFactory.getInstance().init("file:C:\\properties\\db-zztask-jdbc.properties");
		Date baseDate = new Date();
		AnalysisCompany_accountUpdate ts = new AnalysisCompany_accountUpdate();
		try {
			ts.exec(baseDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.zz91.mission.analysis;

import java.io.BufferedReader;
import java.io.FileReader;
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

import net.sf.json.JSONObject;

/**
 * 更新访问记录 analysis_phone_optimization: page_calling page_last
 * @author kongsj
 *
 */
public class AnalysisPhoneYHView implements ZZTask{
	final static String DB ="ast";
	final static String LOG_FILE = "/mnt/data/log4z/run.";

	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String targetDate = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), "yyyy-MM-dd");
		// 获取日志信息
		BufferedReader br = new BufferedReader(new FileReader(LOG_FILE+ targetDate));
		String line=null;;
//		JSONArray ja = new JSONArray();
		Map<String,List<String>> ipMap = new HashMap<String, List<String>>();
		do {
			while ((line = br.readLine()) != null) {
				JSONObject obj = JSONObject.fromObject(line);
				if (!"400".equals(obj.get("appCode"))) {
					continue;
				}
				//{"appCode":"400","data":"{\"date\":\"2015-12-11 20:50:15\",\"time\":1449838215038,\"url\":\"http://www.zz91.com/ppc/productdetail1468271.htm\"}","ip":"211.91.177.83","operation":"access","operator":"phone_400","time":1449838215038}
				String ip = obj.getString("ip");
				List<String> list = ipMap.get(ip);
				if (list==null) {
					list =new ArrayList<String>();
				}
				String data = obj.getString("data");
				JSONObject dataJson = JSONObject.fromObject(data);
				list.add(dataJson.getString("url"));
				ipMap.put(ip, list);
//				ja.add(obj);
//				System.out.println(obj);
//				ipSet.add(obj.getString("ip"));
			}
//			System.out.println(ja.size());
//			for (String str:ipMap.keySet()) {
//				System.out.println(str+" : "+ipMap.get(str));
//			}
		} while (false);
		br.close();
		
		// 检索今日优化进来的ip
		String sql = "select id,ip,phone_log_id from analysis_phone_optimization where gmt_target='"+ targetDate+"'";
		final Map<Integer, Map<String , String >> idMap = new HashMap<Integer, Map<String , String >>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Map<String , String > map=new HashMap<String, String>();
					map.put("ip", rs.getString(2));
					map.put("phone_log_id", rs.getString(3));
					idMap.put(rs.getInt(1), map);
				}
			}
		});
		
		Map<Integer, String> resultMap = new HashMap<Integer, String>();
		for (Integer id :idMap.keySet()) {
			Map<String, String> map = idMap.get(id);
			List<String> list = ipMap.get(map.get("ip"));
			if (list!=null&&list.size()>0) {
				resultMap.put(id, list.get(list.size()-1));
			}
		}
		
		// 更新地址
		for (Integer id :resultMap.keySet()) {
			update(id,resultMap.get(id));
		}
		
		return true;
	}
	
	private void update(Integer id,String url){
		String sql = "update analysis_phone_optimization set gmt_modified=now(),page_last='"+url+"' where id = "+id;
		DBUtils.insertUpdate(DB, sql);
		
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}
	
	public static void main(String[] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		AnalysisPhoneYHView obj =new AnalysisPhoneYHView();
		obj.exec(DateUtil.getDate("2015-12-12", "yyyy-MM-dd"));
	}
}
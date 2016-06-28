package com.zz91.mission.analysis;

import java.sql.ResultSet;
import java.sql.SQLException;
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

public class AnalysisPhoneLogTask implements ZZTask {
	final static String DB = "ast";
	final static Map<String, Map<String,Object>> RESULT = new HashMap<String, Map<String,Object>>();
	@Override
	public boolean exec(Date baseDate) throws Exception {
		String from=DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1),"yyyy-MM-dd");
		String to=DateUtil.toString(baseDate,"yyyy-MM-dd");
		String date=DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), "yyyy-MM-dd 00:00:00");
		//已接电话
		Map<String,Map<String,Object>> mapYJ=getYijieTel(from,to);
		Integer companyId=null;
		String  startTime=null;
		String[] dive={};
		Integer hour=0;
		List<String> list=new ArrayList<String>();
		//判断在该段时间内有无被展示
		for(String str:mapYJ.keySet()){
			companyId=(Integer) mapYJ.get(str).get("companyId");
			startTime= (String) mapYJ.get(str).get("startTime");
			//获取小时
			dive=startTime.split(" ");
			dive=dive[1].split(":");
			hour=Integer.valueOf(dive[0]);
			list=getAdposition(hour,date,companyId);
			for(String li:list){
				Map<String,Object> map=new HashMap<String,Object>();
				String string=li+"-"+companyId;
				if(RESULT.keySet().contains(string)){
					Integer telC=(Integer) RESULT.get(string).get("telCount");
					telC=telC+1;
					RESULT.remove(string);
					map.put("companyId", companyId);
					map.put("adposition", li);
					map.put("telCount", telC);
					RESULT.put(string, map);
				}else{
					map.put("companyId", companyId);
					map.put("adposition", li);
					map.put("telCount", 1);
					RESULT.put(string, map);
				}
			}
		}
			//未接电话
			Map<String,Map<String,Object>> mapWJ=getWeiJieTel(from,to);
			for(String stg:mapWJ.keySet()){
				companyId=(Integer) mapWJ.get(stg).get("companyId");
				startTime= (String) mapWJ.get(stg).get("startTime");
				//获取小时
				dive=startTime.split(" ");
				dive=dive[1].split(":");
				hour=Integer.valueOf(dive[0]);
				 list=getAdposition(hour,date,companyId);
				 for(String li:list){
					Map<String,Object> map=new HashMap<String,Object>();
					String string=li+"-"+companyId;
					if(RESULT.keySet().contains(string)){
						Integer telC=(Integer) RESULT.get(string).get("telCount");
						telC=telC+1;
						RESULT.remove(string);
						map.put("companyId", companyId);
						map.put("adposition", li);
						map.put("telCount", telC);
						RESULT.put(string, map);
					}else{
						map.put("companyId", companyId);
						map.put("adposition", li);
						map.put("telCount", 1);
						RESULT.put(string, map);
					}
				}
			}
		 for(String re:RESULT.keySet()){
			insert(RESULT.get(re).get("adposition"),RESULT.get(re).get("companyId"),RESULT.get(re).get("telCount"),date); 
		 }
		return true;
	}
	//获取phone_log中的前一天的所有已接号码
	public Map<String,Map<String,Object>> getYijieTel(String from,String to){
		String sql="select company_id,start_time,call_sn from phone_log where start_time>='"+from+"' and start_time<'"+to+"' and state='1'";
		final Map<String,Map<String,Object>> map=new HashMap<String,Map<String,Object>>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Map<String,Object> maps=new HashMap<String,Object>();
					maps.put("companyId", rs.getInt(1));
					maps.put("startTime", rs.getString(2));
					map.put(rs.getString(3), maps);
				}
			}
		});
		return map;
	}
	//获取phone_log中的前一天的所有未接号码
	public Map<String,Map<String,Object>> getWeiJieTel(String from,String to){
		String sql="select distinct(company_id),min(start_time),call_sn from phone_log where start_time>='"+from+"' and start_time<'"+to+"' and state='0' and company_id!='0' group by company_id";
		final Map<String,Map<String,Object>> map=new HashMap<String,Map<String,Object>>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Map<String,Object> maps=new HashMap<String,Object>();
					maps.put("companyId", rs.getInt(1));
					maps.put("startTime", rs.getString(2));
					map.put(rs.getString(3), maps);
				}
			}
		});
		return map;
	}
	//搜出该号码所代表的公司，该时段有无在某广告位进行展示
	public List<String> getAdposition(Integer hour,String date,Integer companyId){
		String sql="select adposition from analysis_ppc_adlog where company_id='"+companyId+"' and phour='"+hour+"' and pdate='"+date+"'";
		final List<String> list=new ArrayList<String>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}
		});
		return list;
	}
	//结果集插入数据库
	public void insert(Object adposition,Object companyId,Object telCount,String date){
		String sql="insert into analysis_phone_log (adposition,company_id,tel_count,gmt_target,gmt_created,gmt_modified)"+ "values"+"('"+adposition+"','"+companyId+"','"+telCount+"','"+date+"',now(),now())";
		System.out.println(sql);
		DBUtils.insertUpdate(DB, sql);
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
	public static void main(String[] args) throws Exception {
		 DBPoolFactory.getInstance().init(
	                "file:/usr/tools/config/db/db-zztask-jdbc.properties");
		 AnalysisPhoneLogTask ads=new AnalysisPhoneLogTask();
		 ads.exec(new Date());
		 System.out.println("123");
	}

}

package com.zz91.mission.zz91;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

/**
 * 补全phone_log表单中的地区字段，省，市
 * @author shiqp
 *
 */
public class SynPhoneLogForAddressTask implements ZZTask{
	final static String DB = "ast";
	final static String DBS = "zzother";
	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		SynPhoneLogForAddressTask addressTask = new SynPhoneLogForAddressTask();
		addressTask.exec(DateUtil.getDate("2014-09-01", "yyyy-MM-dd"));
	}
	@Override
	public boolean exec(Date baseDate) throws Exception {
		List<Map<String,String>> list=getPhoneLog();
		Map<String,String> map=new HashMap<String,String>();
		for(Map<String,String> li:list){
			if(!li.get("caller_id").startsWith("010")&&(li.get("caller_id").startsWith("1")||li.get("caller_id").startsWith("01"))){
				 map=getAddressByPhone(li.get("caller_id"));
			}else{
				map=getAddressByTel(li.get("caller_id"));
			}
			insertPhoneLog(map,li.get("id"));
			//System.out.println(li.get("id")+" , "+li.get("caller_id")+","+map.get("province")+" "+map.get("city"));
		}
		return true;
	}
    //获取provice和city为null的记录
	public List<Map<String,String>> getPhoneLog(){
		String sql="select id,caller_id from phone_log where call_sn!='0' and caller_id!='0' and (province is null or city is null)";
		final List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Map<String,String> map=new HashMap<String,String>();
					map.put("id", rs.getString(1));
					map.put("caller_id", rs.getString(2));
					list.add(map);
				}

			}
		});
		return list;
	}
	//手机号码获取来电地区
	public Map<String,String> getAddressByPhone(String mobile){
		if(mobile.startsWith("0")){
			mobile=mobile.substring(1);
		}
		mobile=mobile.substring(0, 7);
		String sql="select province,city from mobile_number where numb="+mobile+"";
		final Map<String,String> map=new HashMap<String,String>();
		DBUtils.select(DBS, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					map.put("province", rs.getString(1));
					map.put("city", rs.getString(2));
				}

			}
		});
		if(map.keySet().size()==0){
			map.put("province", "未知");
			map.put("city", "未知");
		}
		return map;
	}
	//电话号码获取获取来电地区
	public Map<String,String> getAddressByTel(String tel){
		final Map<String,String> map=new HashMap<String,String>();
		do{
			if(!StringUtils.isNumeric(tel)){
				break;
			}
			if(tel.startsWith("010") || tel.startsWith("02")){
				tel=tel.substring(0, 3);
			}else{
				tel=tel.substring(0, 4);
			}
			String sql="select province,city from tel_guoneinumb where number="+tel+"";
			DBUtils.select(DBS, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						map.put("province", rs.getString(1));
						map.put("city", rs.getString(2));
						break;
					}

				}
			});
		}while(false);
		if(map.keySet().size()==0){
			map.put("province", "未知");
			map.put("city", "未知");
		}
		return map;
	}
	//省、市更新进phone_log表中
	public void insertPhoneLog(Map<String,String> map,String id){
		String sql="update phone_log set province='"+map.get("province")+"',city='"+map.get("city")+"' where id="+id;
		DBUtils.insertUpdate(DB, sql);
	}
	
	@Override
	public boolean clear(Date baseDate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}

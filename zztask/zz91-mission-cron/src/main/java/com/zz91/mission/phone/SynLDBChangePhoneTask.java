package com.zz91.mission.phone;

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

public class SynLDBChangePhoneTask implements ZZTask{
	final static String DB = "ast";
	@Override
	public boolean exec(Date baseDate) throws Exception {
		final Map<String,String> mapTel=new HashMap<String,String>();
		final Map<String,Integer> mapIdTel = new HashMap<String,Integer>();
//		mapTel.put("4008099717", "4007450070");
//		mapTel.put("4001698879-1001", "4007450071");
		//旧——>新
//		mapTel.put("4007450601", "4007450987");
		String sql = "select id,tel_from,tel_to from phone_number_change_log where status = 1 limit 0,10";
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					mapTel.put(rs.getString(2), rs.getString(3));
					mapIdTel.put(rs.getString(2), rs.getInt(1));
				}
			}
		});
		for(String tel:mapTel.keySet()){
			List<Integer> listId=getId(tel);
			for(Integer id:listId){
				update(id,mapTel.get(tel));
			}
			Integer id = mapIdTel.get(tel);
			sql ="update phone_number_change_log set status = 2,gmt_modified=now() where id = " + id;
			DBUtils.insertUpdate(DB, sql);
		}
		return true;
	}

	public static void main(String[] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init(
				"file:/mnt/tools/config/db/db-zztask-jdbc.properties");
		SynLDBChangePhoneTask obj = new SynLDBChangePhoneTask();
		obj.exec(DateUtil.getDate("2015-01-05", "yyyy-MM-dd"));
		System.out.println("ye");
	}
	//获取对应400号码的id
	public List<Integer> getId(String tel){
		String sql="select id from phone_log where tel='"+tel+"'";
		final List<Integer> list=new ArrayList<Integer>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getInt(1));
				}
			}
		});
		return list;
	}
	// 铁通400转号码
	public void update(Integer id,String newTel){
		String sql="update phone_log set tel='"+newTel+"' where id="+id;
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

}

package com.zz91.mission.zz91;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class SynLDBInsertCompanyId implements ZZTask{
	final static String DB = "ast";

	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		List<String> list=getPhone();
		for(String li:list){
			Integer companyId=getCompanyId(li);
			update(li,companyId);
		}
		
		return true;
	}
	//搜出所有的7月月租的记录的400号码
	public List<String> getPhone(){
		String str="7月租费";
		String sql="select tel from phone_log where caller_id='"+str+"'";
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
	//根据tel搜出公司id
	public Integer getCompanyId(String tel){
		String sql="select company_id from phone where tel='"+tel+"'";
		final List<Integer> list=new ArrayList<Integer>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
				    list.add(rs.getInt(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}
		return 0;
	}
	//companyId插入数据库
	public void update(String tel,Integer companyId){
		String str="7月租费";
		String sql="update phone_log set company_id='"+companyId+"' where tel='"+tel+"' and caller_id='"+str+"'";
		DBUtils.insertUpdate(DB, sql);
	}
	@Override
	public boolean clear(Date baseDate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	public static void main(String[] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		SynLDBInsertCompanyId obj = new SynLDBInsertCompanyId();
		obj.exec(DateUtil.getDate("2014-08-02", "yyyy-MM-dd"));
	}
}

package com.zz91.mission.zz91;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class SynCompanyIdToPhoneTask implements ZZTask{
	final static String DB = "ast";
	@Override
	public boolean exec(Date baseDate) throws Exception {
		List<Integer> list=getCompanyId();
		for(Integer in:list){
			String mobile=getMobile(in);
			if(StringUtils.isNotEmpty(mobile)){
				update(mobile,in);
			}
		}
		return true;
	}
	//获取phone的companyId
	public List<Integer> getCompanyId(){
		String sql="select company_id from phone";
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
	//搜出对应公司id的mobile
	public String getMobile(Integer companyId){
		String sql="select mobile from company_account where company_id='"+companyId+"'";
		final List<String> list=new ArrayList<String>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
				   list.add(rs.getString(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	//更新mobile
	public void update(String mobile,Integer companyId){
		String sql="update phone set mobile='"+mobile+"' where company_id='"+companyId+"'";
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
	public static void main(String[] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		SynCompanyIdToPhoneTask obj = new SynCompanyIdToPhoneTask();
		obj.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd HH:mm:ss"));

	}
}

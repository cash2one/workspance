package com.zz91.mission.phone;

import java.sql.Connection;
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

public class UpdatephoneLostCusTask implements ZZTask {
    private final static String LOG_DATE_FORMAT = "yyyy-MM-dd";
    private final static String DB="ast";
    final Map<Integer, Integer>map=new HashMap<Integer, Integer>();
	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		 
		String targetDate=DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), LOG_DATE_FORMAT);
		int max=queryMaxSize(targetDate);
		int limit=1000;
		for(int i=0;i<(max/limit+1);i++){
			queryCompanyId(i,limit,targetDate);
		}
		return true;
	}
	private void queryCompanyId(int i,int limit,String targetDate)throws Exception{
		
		final List<Integer>list=new ArrayList<Integer>();
		DBUtils.select("ast", "select company_id from crm_cs  where  gmt_next_visit_phone='"+targetDate+"' order by id desc limit "+(i*limit)+","+limit, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					list.add(rs.getInt(1));
				}
			}
		});
		for (final Integer companyId:list) {
			DBUtils.select(DB, "select id from phone where company_id= "+companyId, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while(rs.next()){
						map.put(companyId, rs.getInt(1));
					
					}
							
				}
			});
			if(map.get(companyId)!=null&&map.get(companyId)>0){
				insertPhoneLostCus(companyId,targetDate);
			}
		}
	}
	private void insertPhoneLostCus(Integer companyId,String targetDate){
		String sql="insert into phone_lost_customer (company_id,gmt_target,gmt_created,gmt_modified) values ("+companyId+",'"+targetDate+"',now(),now())";
		DBUtils.insertUpdate(DB, sql);
	}
	private int queryMaxSize(String targetDate) throws Exception{
		int max=0;
		Connection conn=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection(DB);
			rs=conn.createStatement().executeQuery("select count(*) from crm_cs where gmt_next_visit_phone='"+targetDate+"'");
			while(rs.next()){
				max=rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Exception("更新失败 Exception:"+e.getMessage());
		}finally{
			try {
				if(conn!=null){
					conn.close();
				}
				if(rs!=null){
					rs.close();
				}
			} catch (Exception e2) {
				throw new Exception("数据库连接没有关闭 Exception:"+e2.getMessage());
			}
		}
		return max;
		
	}
	@Override
	public boolean clear(Date baseDate) throws Exception {
		String targetDate=DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), LOG_DATE_FORMAT);
		return DBUtils.insertUpdate(DB, "delete from phone_lost_customer where gmt_target='"+targetDate+"'");
		
	}
	public static void main(String [] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		long start=System.currentTimeMillis();
		UpdatephoneLostCusTask updatephoneLostCusTask=new UpdatephoneLostCusTask();
		updatephoneLostCusTask.clear(DateUtil.getDate("2014-10-21 12:12:12", LOG_DATE_FORMAT));
		updatephoneLostCusTask.exec(DateUtil.getDate("2014-10-21 12:12:12", LOG_DATE_FORMAT));
		long end=System.currentTimeMillis();
		System.out.println("共耗时："+(end-start));
	}

}

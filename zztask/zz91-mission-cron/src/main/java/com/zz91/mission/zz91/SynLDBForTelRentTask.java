package com.zz91.mission.zz91;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

/**
 * 计算每一个用户的分机月租
 * @author fangchao
 *
 */

public class SynLDBForTelRentTask implements ZZTask{

	final static String DB = "ast";
	
	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		final String toDate = DateUtil.toString(baseDate, "yyyy-MM-dd");//执行时间
		
		final String toDateMonths = DateUtil.toString(baseDate, "yyyy.MM");//执行时间
		
		final String tragetDate = DateUtil.toString(DateUtil.getDateAfterMonths(baseDate, -1), "yyyy-MM-dd");//统计月租
		
		final String tragetDateString = DateUtil.toString(DateUtil.getDateAfterMonths(baseDate, -1), "yyyy.MM");//统计月租
		
		// 检索所有分机号码
		String sql = "select tel,gmt_open from phone where  LENGTH(tel) >10 and gmt_open <'"+toDate+"'";
		final Map<String,String> telMap = new HashMap<String,String>();
		
		final Map<String,String> maps = new HashMap<String,String>();
		
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					if(telMap.get(rs.getString(1))!=null){
						
						telMap.put(rs.getString(1),tragetDateString);
					}else{
						
						telMap.put(rs.getString(1),tragetDateString);
					}
				}
			}
		}); 
		
		final Map<String,String> nexttelMap = new HashMap<String,String>(); //用于存放下月的月租的
		
		
		//根据tel和时间检查每个月分机的月租是否存在
		for(final String tel:telMap.keySet()){
			//查询是否重复
			//得到年和月组装
			sql = "select count(0) from phone_log where tel = '"+ tel+"' and call_sn = '"+telMap.get(tel)+"月租'";
			
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while(rs.next()){
						if(Integer.valueOf(rs.getString(1))>0){
							nexttelMap.put(tel, toDateMonths);
							continue;
						}else{
							maps.put(tel, telMap.get(tel));
							nexttelMap.put(tel, toDateMonths);
						}
					}
				}
			});
			
		}
		
		//添加月租
		for (String  tel : maps.keySet()) {
				saveToDB(tel, maps.get(tel)+"月租",tragetDate);//上个月的月租	
		}
		//添加到下月月租
		for (String  tel : nexttelMap.keySet()) {
			saveToDB(tel, toDateMonths+"月租",toDate);//本月租一起统计
		}
		return true;
	}


	@Override
	public boolean init() throws Exception {
		return false;
	}
	
	
   private void saveToDB(String tel,String rentDate,String toDate) {
        String insertSql = "insert into phone_log (caller_id,tel,call_fee,start_time,end_time,gmt_created,gmt_modified,call_sn,state)"
                + "values" + "('0','"+tel+"','0','"+toDate+"','"+toDate+"',now(),now(),'"+rentDate+"','1')"; 
        DBUtils.insertUpdate(DB, insertSql);
    }
	
	
	public static void main(String[] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		SynLDBForTelRentTask obj = new SynLDBForTelRentTask();
		obj.exec(DateUtil.getDate("2013-10-01", "yyyy-MM-dd"));
		System.out.println(new Date());
		
	}

}

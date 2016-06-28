package com.zz91.mission.zz91;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

/**
 * 来电宝月账单计算任务
 * @author kongsj
 *
 */

public class SynLDBMonthlyFeeTask implements ZZTask{

	final static String DB = "ast";
	
	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		// 检索出 符合条件的 400号码
		Date analysisDate = DateUtil.getDateAfterMonths(baseDate, -1);
		String from = DateUtil.toString(baseDate,"yyyy-MM-01");
		final String to = DateUtil.toString(baseDate,"yyyy-MM-01");
		String sql = "select tel from phone where gmt_open < '"+to+"'";
		final Set<String> telSet =new HashSet<String>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					telSet.add(rs.getString(1));
				}
			}
		});
		
		// 检验月租是否已经添加过 添加过，则不加
		Calendar c = Calendar.getInstance();
		c.setTime(analysisDate);
		String key =(c.get(Calendar.MONTH)+ 1) + "月租费";
		final Set<String> resultSet =new HashSet<String>();
		for (final String tel : telSet) {
			sql = "select count(0) from phone_log where caller_id ='"+key+"' and tel = '"+tel+"' and start_time = '"+DateUtil.toString(baseDate, "yyyy-MM-dd")+"'";
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while(rs.next()){
						if(rs.getInt(1)<1){
							resultSet.add(tel);
						}
					}
				}
			});
		}
		
		for(String tel:resultSet){
			// 检索出服务开通的起始时间
			
			sql = "select gmt_open,company_id from phone where tel = '"+tel+"'";
			final Set<Integer> daySet = new HashSet<Integer>();
			final Integer[] companyArry = new Integer[1];
			Integer dayLenth=null; // 月租费用
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while(rs.next()){
						String gmtCreatedStr = rs.getString(1);
						companyArry[0] = rs.getInt(2);
						try {
							Integer i = DateUtil.getIntervalDays(DateUtil.getDate(gmtCreatedStr, "yyyy-MM-dd"),DateUtil.getDate(to, "yyyy-MM-dd"));
							if(Math.abs(i)<30){
								daySet.add(Math.abs(i));
							}
						} catch (ParseException e) {
							continue;
						}
					}
				}
			});
			if(daySet.size()>0){
				for(Integer i:daySet){
					dayLenth = i;
				}
			}
			
			if(dayLenth==null){
				dayLenth = 30;
			}
			if(hasService(companyArry[0])){
				if (hasMoney(companyArry[0])) {
					// 执行不扣月费 记录insert
					sql = "INSERT INTO `phone_log`"
							+ "(`caller_id`,`tel`,`call_fee`,`start_time`,`end_time`,`gmt_created`,`gmt_modified`,`call_sn`,`state`,company_id)"
							+ "VALUES"
							+ "('"+key+"','"+tel+"',0,'"+from+"','"+from+"',now(),now(),0,'1','"+companyArry[0]+"')";
					DBUtils.insertUpdate(DB, sql);
					resetLevel(companyArry[0],0);
					
				}
			}else{
				if (hasMoney(companyArry[0])) {
					// 执行月租计算插入
					sql = "INSERT INTO `phone_log`"
						+ "(`caller_id`,`tel`,`call_fee`,`start_time`,`end_time`,`gmt_created`,`gmt_modified`,`call_sn`,`state`,`company_id`)"
						+ "VALUES"
						+ "('"+key+"','"+tel+"','"+dayLenth+"','"+from+"','"+from+"',now(),now(),0,'1','"+companyArry[0]+"')";
					DBUtils.insertUpdate(DB, sql);
					resetLevel(companyArry[0],dayLenth);
				}
			}
		}

		return true;
	}
	public void resetLevel(Integer companyId,Integer callFee){
		 double jinyan=0;
		 if(companyId>0){
         	Map<String,String> jingYan=getJingYan(companyId);
         	if(jingYan.size()>0){
         		jinyan=Double.valueOf(jingYan.get("phone_cost"))+callFee;
         		BigDecimal jy = new BigDecimal(jinyan);
         		jy = jy.setScale(2, BigDecimal.ROUND_HALF_UP);
         		if(jinyan>=Math.pow(2,(Integer.valueOf(jingYan.get("level")))+1)*1000){
         			updateJingYan(companyId,String.valueOf(jy),Integer.valueOf(jingYan.get("level"))+1);
         		}else{
         			updateJingYan(companyId,String.valueOf(jy),Integer.valueOf(jingYan.get("level")));
         		}
         	}else{
         		if(callFee>0){
         			inseryJingyan(companyId,String.valueOf(callFee));
         		}
         	}
         }
	}
	//插入新的来电宝经验信息
	public void inseryJingyan(Integer companyId,String cost){
		String sql = "insert into ldb_level(company_id,level,phone_cost,gmt_created,gmt_modified)"
				+ "VALUES('"+companyId+"','1','" + cost + "',now(),now())";
		DBUtils.insertUpdate("ast", sql);
		
	}
	//更新来电宝经验信息
	public void updateJingYan(Integer companyId,String cost,Integer level){
		String sql="update ldb_level set phone_cost='"+cost+"',level='"+level+"',gmt_modified=now() where company_id='"+companyId+"'";
		DBUtils.insertUpdate("ast", sql);
	}
	//获取来电宝经验信息
	public Map<String,String> getJingYan(Integer companyId){
		String sql="select phone_cost,level from ldb_level where company_id='"+companyId+"'";
		final Map<String,String> map=new HashMap<String,String>();
		DBUtils.select("ast", sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)throws SQLException {
				while(rs.next()){
					map.put("phone_cost", rs.getString(1));
					map.put("level", rs.getString(2));
				}
			}
			
		});
		return map ;
	}
    //判断该400号码有无来电宝无月租服务
	public boolean hasService(Integer companyId){
		String sql="select count(*) from crm_company_service where crm_service_code='1010' and company_id="+companyId+" and apply_status='1'";
		final Integer[] i=new Integer[1];
		DBUtils.select(DB, sql, new IReadDataHandler() {

			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while(rs.next()){
					i[0]=rs.getInt(1);
				}
			}
		});
		if(i[0]>0){
			return true;
		}else{
			return false;
		}
	}
	//判断还有没有费用 即是否400过期
	public boolean hasMoney(Integer companyId){
		String sql="select sum(lave) from phone_cost_service where company_id="+companyId;
		final float[] i=new float[1];
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while(rs.next()){
					i[0]=rs.getInt(1);
				}
			}
		});
		if(i[0]>0){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		SynLDBMonthlyFeeTask task= new SynLDBMonthlyFeeTask();
		task.exec(DateUtil.getDate("2014-11-01", "yyyy-MM-dd"));
		System.out.println(1);
		
	}
	
}

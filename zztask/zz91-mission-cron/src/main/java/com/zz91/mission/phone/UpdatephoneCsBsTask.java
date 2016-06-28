package com.zz91.mission.phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;

public class UpdatephoneCsBsTask implements ZZTask {

	    private final static String LOG_DATE_FORMAT = "yyyy-MM-dd";
	    private final static String DB="ast";
	    final List<Integer>list=new ArrayList<Integer>();
	    final List<Integer> list1= new ArrayList<Integer>();
	    final List<Integer> list3= new ArrayList<Integer>();
	    final List<Integer> list4= new ArrayList<Integer>();
	    final Map<Integer, Integer>root1=new HashMap<Integer, Integer>();
	    final Map<Integer, Integer>root2=new HashMap<Integer, Integer>();
		@Override
		public boolean init() throws Exception {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean exec(Date baseDate) throws Exception {
			
			String nowDate=DateUtil.toString(new Date(), LOG_DATE_FORMAT);
			String afterDate=DateUtil.toString(DateUtil.getDateAfterMonths(new Date(), 3), LOG_DATE_FORMAT);
			//一元来电宝服务到期前三个月客户
			String sql="";
			sql="select company_id from crm_company_service  ccm where ccm.crm_service_code='1007'and ccm.apply_status='1' and ccm.gmt_end>'"+nowDate+"' and ccm.gmt_end<'"+afterDate+"'";
			queryOneCompanyId(sql,afterDate);
			
			//五元来电宝服务到期前三个月客户
			sql="select company_id from crm_company_service  ccm where ccm.crm_service_code='1008'and ccm.apply_status='1' and ccm.gmt_end>'"+nowDate+"' and ccm.gmt_end<'"+afterDate+"'";
			queryFiveCompanyId(sql,afterDate);
			
			//余额小于200客户
		    sql="select company_id from phone_cost_service group by company_id having sum(lave)<200";
			queryLave(sql);
			
			//去掉重复的company_id
			Set<Integer> companyIdSet=new HashSet<Integer>(list);
			//插入数据库
			for(Integer companyId :companyIdSet){
				insertPhoneCsBs(companyId);
			}
			return true;
		}
		
		private  void queryOneCompanyId(String sql,String afterDate )throws Exception{
			
			
			DBUtils.select("ast", sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while(rs.next()){
						list3.add(rs.getInt(1));
					}
				}
			});
			
			for( final Integer companyId :list3){
				DBUtils.select(DB, "select id from crm_company_service where gmt_end>'"+afterDate+"' and company_id="+companyId, new IReadDataHandler() {
					
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while(rs.next()){
						 root1.put(companyId, rs.getInt(1));
						}
					}
				});
				if(root1.get(companyId)==null ||root1.get(companyId)==0){
					list.add(companyId);
				}
				
			}
		}
        private  void queryFiveCompanyId(String sql,String afterDate)throws Exception{
			
			
			DBUtils.select("ast", sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while(rs.next()){
						list4.add(rs.getInt(1));
					}
				}
			});
			
			for( final Integer companyId :list4){
				DBUtils.select(DB, "select id from crm_company_service where gmt_end>'"+afterDate+"' and company_id="+companyId, new IReadDataHandler() {
					
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while(rs.next()){
						 root2.put(companyId, rs.getInt(1));
						}
					}
				});
				if(root2.get(companyId)==null ||root2.get(companyId)==0){
					list.add(companyId);
				}
				
			}
		}
       private void queryLave(String sql)throws Exception{
			
			DBUtils.select("ast", sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while(rs.next()){
						list1.add(rs.getInt(1));
					}
				}
			}); 
			//判断这个company_id 是否在phone这张表中
			for( final Integer companyId :list1){
				 final Map<String, String>map=new HashMap<String, String>();
				DBUtils.select(DB, "select id from phone where expire_flag=0 and company_id="+companyId, new IReadDataHandler() {
					
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while(rs.next()){
						 map.put("id", rs.getString(1));
						}
					}
				});
				if(StringUtils.isNotEmpty(map.get("id"))){
						list.add(companyId);
				}
				
			}
		}
     
		private void insertPhoneCsBs(Integer companyId){
			String sql="insert into phone_cs_bs (company_id,gmt_target,gmt_created,gmt_modified) values ("+companyId+",now(),now(),now())";
			DBUtils.insertUpdate(DB, sql);
		}
		@Override
		public boolean clear(Date baseDate) throws Exception {
			return DBUtils.insertUpdate(DB, "delete from phone_cs_bs");
			
		}
		public static void main(String [] args) throws ParseException, Exception {
			DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
			long start=System.currentTimeMillis();
			UpdatephoneCsBsTask updatephoneCsBsTask=new UpdatephoneCsBsTask();
			updatephoneCsBsTask.clear(DateUtil.getDate("2014-10-30 12:12:12", LOG_DATE_FORMAT));
			updatephoneCsBsTask.exec(DateUtil.getDate("2014-10-30 12:12:12", LOG_DATE_FORMAT));
			long end=System.currentTimeMillis();
			System.out.println("共耗时："+(end-start));
		}

    
}

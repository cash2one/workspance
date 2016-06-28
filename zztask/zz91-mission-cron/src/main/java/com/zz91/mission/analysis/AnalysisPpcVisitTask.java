package com.zz91.mission.analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;

public class AnalysisPpcVisitTask implements ZZTask{
	private final static String DB = "ast";
	private static String LOG_FILE = "/usr/data/log4z/zz91Analysis/run.";
	private final static String LOG_DATE_FORMAT = "yyyy-MM-dd";
	
    private final static String URL = "url";
    private final static String ACCOUNT = "account";
    private final static String DATA = "date";
    private final static String COMPANYID = "companyId";
    
	@SuppressWarnings("resource")
	@Override
	public boolean exec(Date baseDate) throws Exception {
		String targetDate = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), LOG_DATE_FORMAT);
		 BufferedReader br = new BufferedReader(new FileReader(LOG_FILE+ targetDate));
		 String line;
	     while ((line = br.readLine()) != null) {
	        	JSONObject job=JSONObject.fromObject(JSONObject.fromObject(line).getString("data"));
	        	String str=job.getString(URL);
	        	do{
	        		if(!str.contains("http://www.zz91.com/ppc/")){
	        			break;
	        		}
	        		if(StringUtils.isEmpty(job.getString(ACCOUNT))){
	        			break;
	        		}
	        		if(!job.has("companyId") || StringUtils.isEmpty(job.getString(COMPANYID))){
	        			break;
	        		}
	        		String account=job.getString(ACCOUNT).replace(" ", "");
	        		Integer cid=getCompanyIdByAccount(account);
	        		Integer targetId=Integer.valueOf(job.getString("companyId"));
	        		if(targetId.equals(cid) || cid==0){
	        			break;
	        		}
	        		String date=DateUtil.toString(new Date(job.getLong(DATA)), "yyyy-MM-dd HH:mm:ss");
	        		//插入日志
	        		saveToDB(targetId,cid,date);
	        	}while(false);
	        }
		return true;
	}
	public void saveToDB(Integer targetId,Integer cid,String gmtTarget){
		String sql = "insert into analysis_ppc_visit (target_id,cid,gmt_target,gmt_created,gmt_modified)"
                + "values" + "('"+targetId+"','"+cid+"','"+gmtTarget+"',now(),now())"; 
        DBUtils.insertUpdate(DB, sql);
	}
	//根据帐号获取公司id
	public Integer getCompanyIdByAccount(String account){
		String sql="select company_id from company_account where account='"+account+"'";
		final List<Integer> list=new ArrayList<Integer>();
		DBUtils.select(DB, sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while(rs.next()){
					list.add(rs.getInt(1));
				}
			}});
		if(list.size()>0){
			return list.get(0);
		}else{
			return 0;	
		}
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
		 DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
	       long start = System.currentTimeMillis();
	       AnalysisPpcVisitTask analysisPpcVisitTask=new AnalysisPpcVisitTask();
	       analysisPpcVisitTask.exec(DateUtil.getDate("2014-07-25", "yyyy-MM-dd"));
	       long end = System.currentTimeMillis();
	       System.out.println("共耗时：" + (end - start));
	}

}

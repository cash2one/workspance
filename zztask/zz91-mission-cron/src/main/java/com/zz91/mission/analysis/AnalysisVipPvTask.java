package com.zz91.mission.analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;

public class AnalysisVipPvTask implements ZZTask {
	
	private final static String DB = "ast";
	private static String LOG_FILE = "/usr/data/log4z/run.";
	private final static String LOG_DATE_FORMAT = "yyyy-MM-dd";
    private final static String appCode = "appCode";
    private final static String appCode_VALUE = "zz91Analysis";
    private final static String IP = "ip";
    private final static String DATA = "data";
	
	@Override
	public boolean clear(Date baseDate) throws Exception {
		String targetDate = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), LOG_DATE_FORMAT);
        return DBUtils.insertUpdate(DB,"delete from analysis_vip_pv where gmt_target='"+targetDate+"' ");
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String targetDate = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), LOG_DATE_FORMAT);
        BufferedReader br = new BufferedReader(new FileReader(LOG_FILE+ targetDate));
        final Map<Integer, Integer> resultMap = new HashMap<Integer, Integer>();
        final Map<Integer, Set<String>> maps = new HashMap<Integer, Set<String>>();
        Set<String> ipSet = new HashSet<String>();
       
        String line;
        while ((line = br.readLine()) != null) {
        	JSONObject	jobj=JSONObject.fromObject(line);
            do{
            	if(!appCode_VALUE.equalsIgnoreCase(jobj.getString(appCode))){
            		break;
            	}
                String data = jobj.getString(DATA);
                
				// 获取ip
				String ip = jobj.getString(IP);
				if(StringUtils.isEmpty(ip)){
					break;
				}
				//获取vipPv 高会流量标志 值1表示是高会流量  
				if(data.indexOf("vipPv") !=-1 ){
				
	                JSONObject jb = JSONObject.fromObject(data);
	                Integer vipPv=Integer.valueOf(jb.get("vipPv").toString());
	                if(vipPv.compareTo(1)!=0)
	                {
	                	break;
	                }
	                if(data.indexOf("companyId") !=-1 ){
	  
	                Integer cid = Integer.valueOf(jb.get("companyId").toString());
		              //统计pv
						Integer pv = resultMap.get(cid);
						if(resultMap.get(cid)!=null){
							resultMap.put(cid, ++pv);
						}else{
							resultMap.put(cid, 1);
						}
	            
	    				//统计uv
	    				ipSet=maps.get(jb.getInt("companyId"));
	    				if(ipSet==null){
	    					ipSet=new HashSet<String>();
	    				}
	    				ipSet.add(ip);
	    				maps.put(jb.getInt("companyId"), ipSet);
	                }
		         }
            }while(false);
        }
        br.close();
        for (Integer id : resultMap.keySet()) {
        	saveToDB(id, resultMap.get(id), maps.get(id),targetDate);
		}
        return true;
		
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}
	//插入语句
   private void saveToDB(Integer cid,Integer pv,Set<String> ipSet,String targetDate ) {
	   
	   String insertSql = "insert into analysis_vip_pv (cid,pv,uv,gmt_target,gmt_created,gmt_modified)"
               + "values" + "('"
                       +cid+"','"+pv+"','"+ipSet.size()+"','"+targetDate+"',now(),now())"; 
        DBUtils.insertUpdate(DB, insertSql);
    }
   public static void main(String[] args) throws ParseException, Exception {
	   DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
       long start = System.currentTimeMillis();
       AnalysisVipPvTask analysisVipPvTask=new AnalysisVipPvTask();
       analysisVipPvTask.clear(DateUtil.getDate("2014-07-17", "yyyy-MM-dd"));
       analysisVipPvTask.exec(DateUtil.getDate("2014-07-17", "yyyy-MM-dd"));
       long end = System.currentTimeMillis();
       System.out.println("共耗时：" + (end - start));
 
   }
   
   
}
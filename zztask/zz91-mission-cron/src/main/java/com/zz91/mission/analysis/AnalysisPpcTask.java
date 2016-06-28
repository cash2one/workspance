package com.zz91.mission.analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONObject;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;

public class AnalysisPpcTask implements ZZTask {
	
	private final static String DB = "ast";
	private static String LOG_FILE = "/usr/data/log4z/run.";
	private final static String LOG_DATE_FORMAT = "yyyy-MM-dd";
	
    private final static String OPERATION = "operation";
    private final static String OPERATION_VALUE = "log";
    private final static String OPERATOR = "operator";
    private final static String OPERATOR_VALUE = "admin";
    private final static String IP = "ip";
    private final static String DATA = "data";
	
	@Override
	public boolean clear(Date baseDate) throws Exception {
		String targetDate = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), LOG_DATE_FORMAT);
        return DBUtils.insertUpdate(DB,"delete from analysis_ppc_log where gmt_target='" + targetDate + "' ");
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
                // 是否是门市部
                if(!OPERATION_VALUE.equalsIgnoreCase(jobj.getString(OPERATION))){
                    break;
                }
                // 是否是门市模块
                if (!OPERATOR_VALUE.equalsIgnoreCase(jobj.getString(OPERATOR))) {
                    break;
                }
                String data = jobj.getString(DATA);
                JSONObject jb = JSONObject.fromObject(data);
                if (jb.get("url")==null) {
					break;
				}
                String url = jb.get("url").toString();
                if (url.indexOf("http://www.zz91.com/ppc")==-1) {
					break;
				}
				// 获取ip
				String ip = jobj.getString(IP);
				if(StringUtils.isEmpty(ip)){
					break;
				}
				
				if (jb.get("companyId")==null) {
					break;
				}
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
				
            }while(false);
        }
        br.close();
        for (Integer id : resultMap.keySet()) {
        	saveToDB(id, resultMap.get(id), maps.get(id), targetDate);
		}
        
        return true;
		
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

   private void saveToDB(Integer cid,Integer pv,Set<String> ipSet,String targetDate) {
        String insertSql = "insert into analysis_ppc_log (cid,pv,uv,gmt_target,gmt_created,gmt_modified)"
                + "values" + "('"
                        +cid+"','"+pv+"','"+ipSet.size()+"','"+targetDate+"',now(),now())"; 
        DBUtils.insertUpdate(DB, insertSql);
    }

   public static void main(String[] args) throws ParseException, Exception {
	   DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
       long start = System.currentTimeMillis();
       AnalysisPpcTask analysisPpcTask=new AnalysisPpcTask();
       analysisPpcTask.clear(DateUtil.getDate("2014-07-25", "yyyy-MM-dd"));
       analysisPpcTask.exec(DateUtil.getDate("2014-07-25", "yyyy-MM-dd"));
       
       long end = System.currentTimeMillis();
       System.out.println("共耗时：" + (end - start));
 
   }
}

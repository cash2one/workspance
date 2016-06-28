package com.zz91.mission.analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.util.Date;

import net.sf.json.JSONObject;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.pool.DBPoolFactory;

public class AnalysisAutoCaijiTask implements ZZTask {

    private final static String DB = "ast";

    private static String LOG_FILE = "/usr/data/log4z/run.";
    private final static String LOG_DATE_FORMAT = "yyyy-MM-dd";

    private final static String OPERATION = "operation";
    private final static String OPERATION_VALUE = "price_caiji";
    private final static String OPERATOR = "operator";
    private final static String OPERATOR_VALUE = "caiji-auto";
    private final static String DATA = "data";
   

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String targetDate = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -0), LOG_DATE_FORMAT);
        BufferedReader br = new BufferedReader(new FileReader(LOG_FILE+ targetDate));

        String line;
        while ((line = br.readLine()) != null) {
            JSONObject jobj = JSONObject.fromObject(line);
            do{
                // 是否 自动采集报价
                if(!OPERATION_VALUE.equalsIgnoreCase(jobj.getString(OPERATION))){
                    break;
                }
                // 是否 自动采集模块
                if (!OPERATOR_VALUE.equalsIgnoreCase(jobj.getString(OPERATOR))) {
                    break;
                }
                String data = jobj.getString(DATA);
                data = data.replace("href='", "href=\"");
                data = data.replace("' target='", "\" target=\"");
                data = data.replace("'>", "\">");
                JSONObject jb = JSONObject.fromObject(data);
                String title = jb.get("title").toString();
                String type = jb.get("type").toString();
                String url = jb.get("url").toString();
                /*String log = "{'defaultTime':'10:30','catchTime':'10:35'}";*/
                String log = data;
                log = log.replace("'", "\\'");
                saveToDB(title,type,url,log,targetDate);
            }while(false);
        }
        br.close();
       
        return true;
    }

    private void saveToDB(String title,String type,String url ,String log , String targetDate) {
        String insertSql = "insert into analysis_caiji_log (title,type,url,log,gmt_target,gmt_created,gmt_modified)"
                + "values" + "('"
                        +title+"','"+type+"','"+url+"','"+log+"',"
                        + "'"+targetDate+"',now(),now())"; 
        DBUtils.insertUpdate(DB, insertSql);
    }
    @Override
    public boolean clear(Date baseDate) throws Exception {
        String targetDate = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), LOG_DATE_FORMAT);
        return DBUtils.insertUpdate(DB,"delete from analysis_caiji_log where gmt_target='" + targetDate + "' ");
    }

    public static void main(String[] args) throws ParseException, Exception {
        DBPoolFactory.getInstance().init(
                "file:/usr/tools/config/db/db-zztask-jdbc.properties");

        long start = System.currentTimeMillis();
        AnalysisAutoCaijiTask analysisAutoCaijiTask = new AnalysisAutoCaijiTask();

        analysisAutoCaijiTask.clear(DateUtil.getDate("2013-12-24", "yyyy-MM-dd"));
        analysisAutoCaijiTask.exec(DateUtil.getDate("2013-12-24", "yyyy-MM-dd"));
        long end = System.currentTimeMillis();
        System.out.println("共耗时：" + (end - start));

    }
}

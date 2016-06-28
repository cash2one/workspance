package com.zz91.mission.caiji;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.mission.domain.subscribe.Price;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiFeiSlLateCommsTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> DISCUSSEVENING_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        DISCUSSEVENING_MAP.put("charset", "utf-8");
        DISCUSSEVENING_MAP.put("url", "http://www.rdqh.com/pinglun.aspx");// 采集地址
        DISCUSSEVENING_MAP.put("contentLink", "http://www.rdqh.com");
        DISCUSSEVENING_MAP.put("listStart", "<div class=\"t3\"></div>"); // 列表页开头
        DISCUSSEVENING_MAP.put("listEnd", "上一页"); // 列表页结尾
        DISCUSSEVENING_MAP.put("contentStart", "<div id=\"boxvideo\" style=\"text-align: center\">");
        DISCUSSEVENING_MAP.put("contentEnd", "本报告");
        DISCUSSEVENING_MAP.put("split", "<div class=\"tt\">");
        DISCUSSEVENING_MAP.put("splitLink", "\'");
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 34;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content = "";
            String strContent = "";
            // 检查日期是否对应今天
            String formatDate = DateUtil.toString(baseDate, "yyyy-M-d");
            String newDate = DateUtil.toString(baseDate, "MM月dd日");
            List<String> list = new ArrayList<String>();
            url = DISCUSSEVENING_MAP.get("url");
            
            content = HttpUtils.getInstance().httpGet(url,
			        DISCUSSEVENING_MAP.get("charset"),20000,20000);
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中证网-废塑料晚评"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.cohttp://www.rdqh.com/pinglun.aspxm/web/zz91/auto/caiji/caiji_cs_discussEvening.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料晚评("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "中证网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废塑料晚评自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(DISCUSSEVENING_MAP.get("listStart"));
            Integer end = content.indexOf(DISCUSSEVENING_MAP.get("listEnd"));
            String result = content.substring(start, end);
            String [] str = result.split(DISCUSSEVENING_MAP.get("split"));
            
            
            
            for (String s:str) {
                if(s.indexOf(formatDate)!=-1){
                    if (s.contains("塑料") || s.contains("连塑") || s.contains("pvc") || s.contains("PVC") || s.contains("pta") || s.contains("PTA") || s.contains("LLDPE")) {
                        list.add(s);
                    }
                }
            }
            //对标题中不含关键字的正文进行分析
            for (String s:str) {
                if(s.indexOf(formatDate)!=-1){
                    if (!s.contains("塑料") || !s.contains("连塑") || !s.contains("pvc") || !s.contains("PVC") || !s.contains("pta") || !s.contains("PTA") || !s.contains("LLDPE")) {
                    	 String linkStr = operate.getAlink(s);
                         String[] alink = linkStr.split(DISCUSSEVENING_MAP.get("splitLink"));
                         if(alink.length>0){
                        	 String resultLink = DISCUSSEVENING_MAP.get("contentLink") + alink[1];
                             String resultContent = HttpUtils.getInstance().httpGet(resultLink, DISCUSSEVENING_MAP.get("charset"));
                             if(resultContent.contains("郑州PTA")||resultContent.contains("PVC")||resultContent.contains("连塑")){
                            	 list.add(s);
                             }
                         }
                    }
                }
            }
            
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中证网-废塑料晚评"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_cs_discussEvening.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料晚评("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "中证网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废塑料晚评自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            for (String strList:list) {
                strContent += strList;
            }
            int flag = 0;
            //标题没有抓取信息标签而内容有的情况
            if (!strContent.contains("连塑") && !strContent.contains("塑料")) {
                Map<String,String> mapContent = operate.httpClientAllContent(DISCUSSEVENING_MAP, formatDate , "连塑");
                if (mapContent != null) {
                    String resultTitle = mapContent.get("resultTitle");
                    String resultContent = mapContent.get(resultTitle);
                    resultTitle = newDate + "塑料市场晚间评论：" + resultTitle.substring(5, resultTitle.length());
                    resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("div","p","ul","li","br","table","th","tr","td").addAttributes("td","rowspan"));
                    if(resultContent.contains("免责声明") || resultContent.contains("免责声明：")){
                        resultContent = resultContent.replace("免责声明：", "");
                        resultContent = resultContent.replace("免责声明", "");
                    }
                    Price price = new Price();
                    String typeName = operate.queryTypeNameByTypeId(typeId);
                    
                    price.setTitle(resultTitle);
                    price.setTypeId(typeId);
                    price.setTags(typeName);
                    price.setContent(resultContent);
                    price.setGmtOrder(baseDate);
                    
                    //执行插入 
                    operate.doInsert(price,false);
                    flag++;
                } else {
                    //提示报错，并写入日志
                    flagFailure++;
                }
            }
            if (list.size() < 3) {
                flagFailure = 3 - list.size() +flag;
                if (flagFailure < 3) {
                    errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                            + "<font color='#FF0000'>中证网-废塑料晚评"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                            + "<br />";
                }
            }
            for (String link:list) {
                String linkStr = operate.getAlink(link);
                String[] alink = linkStr.split(DISCUSSEVENING_MAP.get("splitLink"));
                if(alink.length>0){
                    String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                    String resultLink = DISCUSSEVENING_MAP.get("contentLink") + alink[1];
                    String resultContent ="";
                    resultContent = HttpUtils.getInstance().httpGet(resultLink, DISCUSSEVENING_MAP.get("charset"));
                    Integer cStart = resultContent.indexOf(DISCUSSEVENING_MAP.get("contentStart"));
                    Integer cEnd = resultContent.indexOf(DISCUSSEVENING_MAP.get("contentEnd"));
                    resultContent = resultContent.substring(cStart, cEnd-1);
                    resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("div","p","ul","li","br","table","th","tr","td").addAttributes("td","rowspan"));
                    if(resultContent.contains("免责声明") || resultContent.contains("免责声明：")){
                        resultContent = resultContent.replace("免责声明：", "");
                        resultContent = resultContent.replace("免责声明", "");
                    }
                    if(resultContent.contains("郑州PTA")||resultTitle.contains("PTA")){
                    	resultTitle=newDate + "PTA市场晚间评论：" + resultTitle.substring(5, resultTitle.length());
                    }else if(resultContent.contains("PVC")||resultTitle.contains("PVC")){
                    	resultTitle=newDate + "PVC市场晚间评论：" + resultTitle.substring(5, resultTitle.length());
                    }else if(resultContent.contains("连塑")||resultTitle.contains("塑料")||resultTitle.contains("连塑")){
                    	resultTitle=newDate + "塑料市场晚间评论：" + resultTitle.substring(5, resultTitle.length());
                    }
                    Price price = new Price();
                    String typeName = operate.queryTypeNameByTypeId(typeId);
                    
                    price.setTitle(resultTitle);
                    price.setTypeId(typeId);
                    price.setTags(typeName);
                    price.setContent(resultContent);
                    price.setGmtOrder(baseDate);
                    
                    Integer flg = operate.doInsert(price,false);
                    //执行插入并判断插入是否成功
                    /*if (flg != null && flg > 0) {
                        LogUtil.getInstance().log(
                                "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                        + "中证网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    } else*/ if (flg == null){
                        errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                       /* LogUtil.getInstance().log(
                                "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                        + "中证网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                        flagFailure++;
                    }
                }
               
            }
        } while (false);
        if (flagFailure != 0){
            String content = "废塑料晚评"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_cs_discussEvening.htm?typeId="+typeId +"'><font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料晚评("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "中证网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "废塑料晚评自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料晚评("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "中证网</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }
    
    public static void main(String[] args) {
        
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiFeiSlLateCommsTask js=new CaijiFeiSlLateCommsTask();
        try {
            js.exec(DateUtil.getDate("2014-12-23", "yyyy-MM-dd"));
            System.out.println("123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

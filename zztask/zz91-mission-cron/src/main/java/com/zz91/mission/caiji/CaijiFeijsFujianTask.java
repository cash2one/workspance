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
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiFeijsFujianTask implements ZZTask{

    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        CaiJiCommonOperate operate = new CaiJiCommonOperate();
        final Map<String, String> FEIJS_SCRAP_MAP = new HashMap<String, String>();
        // httpget 使用的编码 不然会有乱码
        FEIJS_SCRAP_MAP.put("charset", "gb2312");
        // 采集地址
        FEIJS_SCRAP_MAP.put("url", "http://www.zgfjjs.com/baojia.asp?kindid=&shengid=&key=%B9%FA%C4%DA%B7%CF%CC%FA%BC%DB%B8%F1");
        FEIJS_SCRAP_MAP.put("contentUrl", "http://www.zgfjjs.com/");
        FEIJS_SCRAP_MAP.put("listStart", "<table width=\"700\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"); // 列表页开头
        FEIJS_SCRAP_MAP.put("listEnd", "<table width=\"730\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"); // 列表页结尾
        
        FEIJS_SCRAP_MAP.put("contentStart", "<td width=\"678\" height=\"300\" align=\"left\" valign=\"top\" class=\"black14-2\"><P>");
        FEIJS_SCRAP_MAP.put("contentEnd", "返回前页");
        //判断自动抓取是否成功
        int flagFailure = 0 ;
        Integer typeId = 42;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content ="";
            url = FEIJS_SCRAP_MAP.get("url");
			content = HttpUtils.getInstance().httpGet(url, FEIJS_SCRAP_MAP.get("charset"));
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中国废旧金属网-废铁"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijs_scrap.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废铁("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "中国废旧金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废铁自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(FEIJS_SCRAP_MAP.get("listStart"));
            Integer end = content.indexOf(FEIJS_SCRAP_MAP.get("listEnd"));
            String result = content.substring(start, end);
            String[] str = result.split("<tr>");

            // 检查日期是否对应今天
            String formatDate = DateUtil.toString(baseDate, "yyyy-M-d");
            String format = DateUtil.toString(baseDate, "MM月dd日");
            List<String> list = new ArrayList<String>();
            
            for (String s : str) {
                if (s.indexOf(formatDate) != -1) {
                    list.add(s);
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中国废旧金属网-废铁"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijs_scrap.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废铁("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "中国废旧金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废铁自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            String typeName = operate.queryTypeNameByTypeId(typeId);
            
            String resultContent ="";
            String resultTitle=format+"福建地区废铁价格";
            String resultCon="";
            for(String aStr:list){
            	       String[] alink = aStr.split("]");
            	       String[] strs=alink[1].split("\"");
            	       String link=FEIJS_SCRAP_MAP.get("contentUrl")+strs[1];
                       resultContent = operate.httpClientHtml(link, FEIJS_SCRAP_MAP.get("charset"));
                        if (StringUtils.isEmpty(resultContent)) {
                            continue;
                        }
                        int resultStart = resultContent.indexOf(FEIJS_SCRAP_MAP.get("contentStart"));
                        int resultEnd = resultContent.indexOf(FEIJS_SCRAP_MAP.get("contentEnd"));
                        resultContent = resultContent.substring(resultStart, resultEnd);
                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("div","p","ul","br","li","table","th","tr","td").addAttributes("td","rowspan"));
                        String[] results=resultContent.split("<tr>");
                        List<String> listf = new ArrayList<String>();
                        for(String res:results){
                        	if(res.contains("福建地区")||res.contains("品种")){
                        		listf.add(res);
                        	}
                        }
                        for(int i=0;i<listf.size();i++){
                        	resultCon+="<tr>"+listf.get(i);
                        }
                        resultContent="<table>"+resultCon+"</table>";
                        Price price = new Price();
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
                                            + "中国废旧金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                        } else */if (flg == null){
                            errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                    + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                            /*LogUtil.getInstance().log(
                                    "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                            + "中国废旧金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                            flagFailure++;
                        }
            }
        } while (false);
        if (flagFailure != 0){
            String content = "废铁"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijs_scrap.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废铁("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "中国废旧金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "废铁自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废铁("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "中国废旧金属网</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
        
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }
    
    public static void main(String[] args) {
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiFeijsFujianTask csLog=new CaijiFeijsFujianTask();
        try {
            csLog.exec(DateUtil.getDate("2014-07-17", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

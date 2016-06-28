package com.zz91.mission.caiji;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.mission.domain.subscribe.Price;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiFeiSlEarlyCommsTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> DISCUSS_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        DISCUSS_MAP.put("charset", "gb2312");
        DISCUSS_MAP.put("url", "http://www.cs.com.cn/qhsc/zzqh/");// 采集地址
        DISCUSS_MAP.put("contentLink", "http://www.cs.com.cn/qhsc/zzqh/");
        DISCUSS_MAP.put("listStart", "<!-- 左侧 -->"); // 列表页开头
        DISCUSS_MAP.put("listEnd", "<!-- 翻页 -->"); // 列表页结尾
        DISCUSS_MAP.put("contentStart", "<!-- 正文 -->");
        DISCUSS_MAP.put("contentEnd", "<!-- 附件列表 -->");
        DISCUSS_MAP.put("split", "<li>");
        DISCUSS_MAP.put("splitLink", "\"");
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
            String content ="";
            try {
                url = DISCUSS_MAP.get("url");
                content = operate.httpClientHtml(url, DISCUSS_MAP.get("charset"));
            } catch (HttpException e) {
                content = null;
            } catch (IOException e) {
                content = null;
            }
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中证网-废塑料早评"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_cs_discussMorning.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料早评("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "中证网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废塑料早评自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(DISCUSS_MAP.get("listStart"));
            Integer end = content.indexOf(DISCUSS_MAP.get("listEnd"));
            String result = content.substring(start, end);
            String [] str = result.split(DISCUSS_MAP.get("split"));
            
            String formatDate = DateUtil.toString(baseDate, "MM-dd");
            String newDate = DateUtil.toString(baseDate, "MM月dd日");
            List<String> list = new ArrayList<String>();
            for (String s:str) {
                if(s.indexOf(formatDate)!=-1){
                    if (s.contains("连塑") || s.contains("pvc") || s.contains("PVC") || s.contains("PTA") || s.contains("pta")) {
                        list.add(s);
                    }
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中证网-废塑料早评"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_cs_discussMorning.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料早评("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "中证网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废塑料早评自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 3) {
                flagFailure = 3 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中证网-废塑料早评"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料早评("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "中证网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            }
            for (String link:list) {
                Pattern pattern = Pattern.compile("<a(?:\\s+.+?)*?\\s+href=\"([^\"]*?)\".*?>(.*?)</a>");
                Matcher matcher = pattern.matcher(link);
                String linkStr = "";
                if(matcher.find()){
                    linkStr = matcher.group();
                }
                if(StringUtils.isEmpty(linkStr)){
                    continue;
                }
                
                String[] alink = linkStr.split(DISCUSS_MAP.get("splitLink"));
                if(alink.length>0){
                    String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                    if (resultTitle.contains("连塑")) {
                        resultTitle = newDate + "塑料市场早间评论：" + resultTitle;
                    } else if (resultTitle.contains("pvc") || resultTitle.contains("PVC")) {
                        resultTitle = newDate + "PVC市场早间评论：" + resultTitle;
                    } else if (resultTitle.contains("PTA") || resultTitle.contains("pta")) {
                        resultTitle = newDate + "PTA市场早间评论：" + resultTitle;
                    }
                    String resultLink = DISCUSS_MAP.get("url") + alink[1].substring(2, alink[1].length());
                    String resultContent ="";
                    try {
                        resultContent = operate.httpClientHtml(resultLink, DISCUSS_MAP.get("charset"));
                    } catch (HttpException e) {
                    } catch (IOException e) {
                    }
                    Integer cStart = resultContent.indexOf(DISCUSS_MAP.get("contentStart"));
                    Integer cEnd = resultContent.indexOf(DISCUSS_MAP.get("contentEnd"));
                    resultContent = resultContent.substring(cStart, cEnd-1);
                    resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("div","p","br").addAttributes("td","rowspan"));
                    Price price = new Price();
                    String typeName = operate.queryTypeNameByTypeId(typeId);
                    
                    price.setTitle(resultTitle);
                    price.setTypeId(typeId);
                    price.setTags(typeName);
                    price.setContent(resultContent);
                    price.setGmtOrder(baseDate);
                    
                    urlmap.put(resultTitle, url);

                    Integer flg = operate.doInsert(price,false);
                   //执行插入并判断插入是否成功
                   /*if (flg != null && flg > 0) {
                       LogUtil.getInstance().log(
                               "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                       + "中证网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                   } else*/ if (flg == null){
                       errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                               + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                       /*LogUtil.getInstance().log(
                               "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                       + "中证网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                       flagFailure++;
                   }
                }
               
            }
        } while (false);
        if (flagFailure != 0){
            String content = "废塑料早评"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_cs_discussMorning.htm?typeId="+typeId +"'><font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料早评("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "中证网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "废塑料早评自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料早评("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
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
        CaijiFeiSlEarlyCommsTask js=new CaijiFeiSlEarlyCommsTask();
        try {
            js.exec(DateUtil.getDate("2014-2-20", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

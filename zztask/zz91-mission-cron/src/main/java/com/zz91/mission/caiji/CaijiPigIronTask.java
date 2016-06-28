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

public class CaijiPigIronTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> IRON_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        IRON_MAP.put("charset", "utf-8");
        IRON_MAP.put("url", "http://info.1688.com/tags_list/v6-l13649.html");// 采集地址
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 66;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content ="";
            try {
                url = IRON_MAP.get("url");
                content = operate.httpClientHtml(url , IRON_MAP.get("charset"));
            } catch (HttpException e) {
                content = null;
            } catch (IOException e) {
                content = null;
            }
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>阿里巴巴-生铁"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_albb_iron.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'生铁("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "生铁自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf("<dl class=\"li23 listcontent1\">");
            Integer end = content.indexOf("<div class=\"cl\"></div>");
            String result = content.substring(start, end);
            String [] str = result.split("<li>");
            String format=null;
            // 检查日期是否对应今天
            //String format = DateUtil.toString(new Date(), "yyyy-MM-09");
           // String format = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), "MM月dd日");
            if(DateUtil.toString(DateUtil.getFirstDateThisWeek(), "yyyy-MM-dd").equals(DateUtil.toString(baseDate, "yyyy-MM-dd"))){
            	format = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -3), "MM月dd日");
            }else{
            	format = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), "MM月dd日");
            }
            List<String> list = new ArrayList<String>();
            for (String s:str) {
                if(s.indexOf(format)!=-1){
                    if (s.contains("全国炼钢生铁价格行情")||s.contains("全国铸造生铁价格行情")) {
                        list.add(s);
                    }
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>凡宇资讯网-生铁"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_albb_iron.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'生铁("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "凡宇资讯网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "生铁自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 2) {
                flagFailure = 2 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>凡宇资讯网-生铁"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
            }
            String resultTitle="";
            String resultContent ="";
            for (String key:list) {
                Pattern pattern = Pattern.compile("<a(?:\\s+.+?)*?\\s+href=\"([^\"]*?)\".+>(.*?)</a>");
                Matcher matcher = pattern.matcher(key);
                String linkStr = "";
                if(matcher.find()){
                    linkStr = matcher.group();
                }
                if(StringUtils.isEmpty(linkStr)){
                    break;
                }
                resultTitle= Jsoup.clean(linkStr, Whitelist.none());
                String[] alink = linkStr.split("\"");
                String resultLink = "http://info.1688.com/"+alink[1];
                try {
                     resultContent = operate.httpClientHtml(resultLink, IRON_MAP.get("charset"));
                } catch (HttpException e) {
                } catch (IOException e) {
                }
                Integer cStart = resultContent.indexOf("<p class=\"d-p\">");
                Integer cEnd = resultContent.indexOf("<span class=\"editor\">");
                resultContent = resultContent.substring(cStart, cEnd);
                resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("div","p","ul","li","br","table","th","tr","td").addAttributes("td","rowspan"));
                resultContent = resultContent.replace("<td>趋势图</td>", "");
                Price price = new Price();
                String typeName = operate.queryTypeNameByTypeId(typeId);
                price.setTitle(resultTitle);
                price.setTypeId(typeId);
                price.setTags(typeName);
                price.setContent(resultContent);
                if(DateUtil.toString(DateUtil.getFirstDateThisWeek(), "yyyy-MM-dd").equals(DateUtil.toString(baseDate, "yyyy-MM-dd"))){
                	price.setGmtOrder(DateUtil.getDateAfterDays(baseDate, -3));
                }else{
                	price.setGmtOrder(DateUtil.getDateAfterDays(baseDate, -1));
                }
                price.setGmtCreated(DateUtil.getDateAfterDays(baseDate, -1));
                urlmap.put(resultTitle, url);
                Integer flg = operate.doInsert(price,true);
                   //执行插入并判断插入是否成功
                   if (flg != null && flg > 0) {
                       LogUtil.getInstance().log(
                               "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                       + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                   } else if (flg == null){
                       errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                               + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                      LogUtil.getInstance().log(
                               "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                       + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                       flagFailure++;
                   }
            }
        } while (false);
        if (flagFailure != 0){
            String content = "生铁"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_albb_iron.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'生铁("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "生铁自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'生铁("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {

        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiPigIronTask js=new CaijiPigIronTask();
        try {
            js.exec(DateUtil.getDate("2014-11-04", "yyyy-MM-dd"));
           // System.out.println("123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

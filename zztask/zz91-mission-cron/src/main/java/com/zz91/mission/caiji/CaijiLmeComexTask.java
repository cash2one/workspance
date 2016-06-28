package com.zz91.mission.caiji;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.mission.domain.subscribe.Price;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiLmeComexTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();
    
    final static Map<String, String> METALSCRAP_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        METALSCRAP_MAP.put("charset", "GB2312");
        // 采集地址
        METALSCRAP_MAP.put("url", "http://www.metalscrap.com.cn/chinese/news.asp");
        
        METALSCRAP_MAP.put("listStart", "<td width=\"366\" valign=\"top\">"); // 列表页开头
        METALSCRAP_MAP.put("listEnd", "<td width=\"362\" valign=\"top\">"); // 列表页结尾
        METALSCRAP_MAP.put("contentStart", "<td height=\"20\"><hr style=\"color:#CCCCCC;\" /></td>"); // 内容开头
        METALSCRAP_MAP.put("contentEnd", "  <table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin:30px 0px\">");
        METALSCRAP_MAP.put("titleStart", "<td width=\"724\" valign=\"top\">"); // 标题开头
        METALSCRAP_MAP.put("titleEnd", "<!-- Baidu Button BEGIN -->");  
        
    }


    @Override
    public boolean init() throws Exception {
        return false;
    }


    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 216;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content ="";
            try {
                url = METALSCRAP_MAP.get("url");
                content = operate.httpClientHtml(url, METALSCRAP_MAP.get("charset"));
            } catch (HttpException e) {
                content = null;
            } catch (IOException e) {
                content = null;
            }
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>金属废料资源网-LME/COMEX/预测"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_metalscrap.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'LME/COMEX/预测("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "金属废料资源网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "LME/COMEX/预测自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(METALSCRAP_MAP.get("listStart"));
            Integer end = content.indexOf(METALSCRAP_MAP.get("listEnd"));
            String result = content.substring(start, end);
            String [] str = result.split("<tr>");
            
            // 检查日期是否对应今天
            String format = DateUtil.toString(baseDate, "yyyy-MM-dd");
            String newDate = DateUtil.toString(baseDate, "MM月dd日");
            List<String> list = new ArrayList<String>();
            for (String s:str) {
                if(s.indexOf(format)!=-1){
                    if (s.contains("LME市场报道：")
                            || s.contains("COMEX市场报道：")) {
                        list.add(s);
                    }
                    if (s.contains("国内废铅锌")
                            || s.contains("国内废不锈钢")) {
                        list.add(s);
                    }
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>金属废料资源网-LME/COMEX/预测"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_metalscrap.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'LME/COMEX/预测("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "金属废料资源网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "LME/COMEX/预测自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 2) {
                flagFailure = 2 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>金属废料资源网-LME/COMEX/预测"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
            }
           
            for (String link:list) {
                String linkStr = operate.getAlink(link);
                String[] alink = linkStr.split("\'");
                if(alink.length>0){
                    String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                    if (resultTitle.contains("废铅锌")) {
                        resultTitle = newDate + "废铅锌市场分析预测";
                    } else if (resultTitle.contains("国内废不锈钢")) {
                        resultTitle = newDate + "废不锈钢市场分析预测";
                    }
                    String resultLink = "http://www.metalscrap.com.cn/chinese/" + alink[1];
                    String resultContent ="";
                    try {
                        resultContent = operate.httpClientHtml(resultLink, METALSCRAP_MAP.get("charset"));
                    } catch (HttpException e) {
                    } catch (IOException e) {
                    }
                    if (resultTitle.contains("...")) {
                        Integer tStart = resultContent.indexOf(METALSCRAP_MAP.get("titleStart"));
                        Integer tEnd = resultContent.indexOf(METALSCRAP_MAP.get("titleEnd"));
                        String tResult = resultContent.substring(tStart, tEnd);
                            String[] title1 = tResult.split("</font>");
                            String[] title2 = title1[0].split("<font size=\"4\" style=\"line-height:25px\">");
                            resultTitle = title2[1];
                    }
                    Integer cStart = resultContent.indexOf(METALSCRAP_MAP.get("contentStart"));
                    Integer cEnd = resultContent.indexOf(METALSCRAP_MAP.get("contentEnd"));
                    resultContent = resultContent.substring(cStart, cEnd);
                    resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p","br").addAttributes("td","rowspan"));
                    if (resultContent.contains("<p><br />")) {
                        resultContent = resultContent.replace("<p><br />", "<p>");
                    }
                    if (resultTitle.contains("COMEX市场报道")) {
                        resultContent = resultContent.replace("<BR>", "</p><p>");
                        resultContent = resultContent.replace("<br />", "</p><p>");
                    }
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
                                       + "金属废料资源网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                   } else*/ if (flg == null){
                       errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                               + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                      /* LogUtil.getInstance().log(
                               "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                       + "金属废料资源网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                       flagFailure++;
                   }
                }
               
            }
        } while (false);
        if (flagFailure != 0) {
            String content = "LME/COMEX/预测"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_metalscrap.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'LME/COMEX/预测("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "金属废料资源网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "LME/COMEX/预测自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        }  else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'LME/COMEX/预测("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "金属废料资源网</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }


    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {
        
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiLmeComexTask js=new CaijiLmeComexTask();
        try {
            js.exec(DateUtil.getDate("2015-06-25", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
